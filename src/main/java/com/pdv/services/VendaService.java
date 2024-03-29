package com.pdv.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.pdv.dto.ItemVendaDTO;
import com.pdv.dto.ItemVendaDTOMessage;
import com.pdv.dto.PagamentoDTO;
import com.pdv.dto.PagamentoDTOMessage;
import com.pdv.dto.VendaDTO;
import com.pdv.dto.VendaDTOMessage;
import com.pdv.entities.ItemVenda;
import com.pdv.entities.Venda;
import com.pdv.repositories.ItemVendaRepository;
import com.pdv.repositories.VendaRepository;

@Service
public class VendaService {
	@Autowired
	private VendaRepository vendaRepository;

	@Autowired
	private ItemVendaRepository itemVendaRepository;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private Queue queue;

	public List<Venda> findAll() {
		return vendaRepository.findAll();
	}

	public Venda findById(Long id) {
		Optional<Venda> venda = vendaRepository.findById(id);
		VendaDTO dto = vendaToDTO(venda.get());
		return venda.get();
	}

	private VendaDTO vendaToDTO(Venda venda) {
		List<ItemVendaDTO> itensVenda = new ArrayList<>();
		List<PagamentoDTO> pagamentos = new ArrayList<>();
		VendaDTO dto = new VendaDTO();
		dto.setDataCriacaoVenda(venda.getDataCriacaoVenda());
		dto.setDataFechamentoVenda(venda.getDataFechamentoVenda());
		dto.setNomeComanda(venda.getNomeComanda());
		dto.setSubTotal(venda.getSubTotal());
		dto.setValorTotal(venda.getValorTotal());
		dto.setFinalizada(venda.isFinalizada());
		venda.getItensVenda().forEach(item -> {
			ItemVendaDTO itemVenda = new ItemVendaDTO();
			item.setId(item.getId());
			item.setIdProduto(item.getIdProduto());
			item.setIdVenda(item.getIdVenda());
			item.setQuantidade(item.getQuantidade());
			item.setSubTotal(item.getSubTotal());
			item.setVenda(item.getVenda());
			itensVenda.add(itemVenda);
		});
		venda.getPagamentos().forEach(pagamento -> {
			PagamentoDTO pagamentoDTO = new PagamentoDTO();
			pagamentoDTO.setId(pagamento.getId());
			pagamentoDTO.setIdModoPagamento(pagamento.getModoPagamento().getId());
			pagamentoDTO.setValorPagamento(pagamento.getValorPagamento());
			pagamentoDTO.setIdVenda(pagamento.getIdVenda());
			pagamentoDTO.setQuantidadeParcela(pagamento.getQuantidadeParcela());
			pagamentoDTO.setDataPagamento(pagamento.getDataPagamento());
			pagamentoDTO.setTroco(pagamento.getTroco());
			pagamentoDTO.setModoPagamentoDescricao(pagamento.getModoPagamento().getDescricao());
			pagamentos.add(pagamentoDTO);
		});
		dto.setItensVenda(itensVenda);
		dto.setPagamentos(pagamentos);
		return dto;
	}

	private VendaDTOMessage vendaToVendaDTO(Venda venda) {
		List<ItemVendaDTOMessage> itensVenda = new ArrayList<>();
		List<PagamentoDTOMessage> pagamentos = new ArrayList<>();
		
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


		VendaDTOMessage dto = new VendaDTOMessage();
		dto.setDataCriacaoVenda(venda.getDataCriacaoVenda().format(formatter));
		dto.setDataFechamentoVenda(venda.getDataFechamentoVenda().format(formatter));
		dto.setNomeComanda(String.valueOf(venda.getNomeComanda()));
		dto.setSubTotal(String.valueOf(venda.getSubTotal()));
		dto.setValorTotal(String.valueOf(venda.getValorTotal()));
		dto.setFinalizada(String.valueOf(venda.isFinalizada()));
		venda.getItensVenda().forEach(item -> {
			ItemVendaDTOMessage itemVenda = new ItemVendaDTOMessage();
			itemVenda.setId(String.valueOf(item.getId()));
			itemVenda.setIdProduto(String.valueOf(item.getIdProduto()));
			itemVenda.setIdVenda(String.valueOf(item.getIdVenda()));
			itemVenda.setQuantidade(String.valueOf(item.getQuantidade()));
			itemVenda.setSubTotal(String.valueOf(item.getSubTotal()));
			itemVenda.setProdutoPreco(String.valueOf(item.getProduto().getPreco()));
			itensVenda.add(itemVenda);
		});
		venda.getPagamentos().forEach(pagamento -> {
			PagamentoDTOMessage pagamentoDTO = new PagamentoDTOMessage();
			pagamentoDTO.setId(String.valueOf(pagamento.getId()));
			pagamentoDTO.setIdModoPagamento(String.valueOf(pagamento.getModoPagamento().getId()));
			pagamentoDTO.setValorPagamento(String.valueOf(pagamento.getValorPagamento()));
			pagamentoDTO.setIdVenda(String.valueOf(pagamento.getIdVenda()));
			pagamentoDTO.setQuantidadeParcela(String.valueOf(pagamento.getQuantidadeParcela()));
			pagamentoDTO.setDataPagamento(pagamento.getDataPagamento().format(formatter));
			pagamentoDTO.setTroco(String.valueOf(pagamento.getTroco()));
			pagamentoDTO.setModoPagamentoDescricao(pagamento.getModoPagamento().getDescricao());
			pagamentos.add(pagamentoDTO);
		});
		dto.setItensVenda(itensVenda);
		dto.setPagamentos(pagamentos);

		return dto;
	}

	public Venda insert(Venda venda) {
		boolean cria = verificaVendas(venda);
		if (!cria) {
			venda.setDataCriacaoVenda(LocalDateTime.now());
			venda.setSubTotal(0D);
			venda.setValorTotal(0D);
			venda.setNomeComanda(venda.getNomeComanda().trim());
			return vendaRepository.save(venda);
		} else {
			return null;
		}
	}

	private boolean verificaVendas(Venda venda) {
		Long quantidade = vendaRepository.getQuantidadeComandaEmAbertoInterval7(venda.getNomeComanda().trim());
		quantidade = quantidade == null ? 0 : quantidade;
		return quantidade >= 1 ? true : false;
	}

	public Venda update(Long id, Venda venda) {
		Optional<Venda> novaVenda = vendaRepository.findById(id);
		novaVenda.get().setValorTotal(venda.getValorTotal());
		novaVenda.get().setSubTotal(venda.getSubTotal());
		novaVenda.get().setFinalizada(venda.isFinalizada());
		novaVenda.get().setDataFechamentoVenda(venda.isFinalizada() ? LocalDateTime.now() : null);
		return vendaRepository.save(novaVenda.get());
	}

	public void recalculaValorTotalVenda(Long vendaId) {
		List<ItemVenda> listItensVenda = itemVendaRepository.listItensVendaByVendaId(vendaId);
		double soma = listItensVenda.stream().mapToDouble(ItemVenda::getSubTotal).sum();
		Optional<Venda> v = vendaRepository.findById(vendaId);
		v.get().setValorTotal(soma);
		v.get().setSubTotal(soma);
		vendaRepository.save(v.get());
	}

	public Venda finalizaVenda(Long id) {
		Optional<Venda> v = vendaRepository.findById(id);
		Venda venda = v.get();
		venda.setDataFechamentoVenda(LocalDateTime.now());
		venda.setFinalizada(true);
		vendaRepository.save(venda);
		this.send(venda);

		return venda;
	}

	public void send(Venda v) {
		VendaDTOMessage dto = vendaToVendaDTO(v);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			String json = ow.writeValueAsString(dto);
			System.out.println(json);
			rabbitTemplate.convertAndSend(this.queue.getName(), json);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Venda> findVendasEmAberto() {
		List<Venda> vendas = vendaRepository.findByFinalizadaFalse();
		return vendas;
	}

	public List<Venda> findVendasFinalizasDia() {
		List<Venda> vendas = vendaRepository.findByFinalizadaTrue();
		return vendas;
	}

}
