package com.pdv.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pdv.dto.PagamentoDTO;
import com.pdv.entities.Caixa;
import com.pdv.entities.Pagamento;
import com.pdv.entities.Venda;
import com.pdv.repositories.PagamentoRepository;

@Service
public class PagamentoService {

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private CaixaService caixaService;

	@Autowired
	VendaService vendaService;

	public List<Pagamento> findAll() {
		return pagamentoRepository.findAll();
	}

	public Pagamento findById(Long id) {
		Optional<Pagamento> objCategoria = pagamentoRepository.findById(id);
		return objCategoria.get();
	}

	public PagamentoDTO insert(Pagamento pagamento) {
		Caixa c = caixaService.findById(caixaService.getIdUltimoAbertoNow());
		pagamento.setDataPagamento(LocalDateTime.now());
		pagamento.setCaixa(c);
		Venda venda = vendaService.findById(pagamento.getIdVenda());
		pagamento.setVenda(venda);
		pagamento = pagamentoRepository.save(pagamento);

		PagamentoDTO dto = new PagamentoDTO();
		dto.setId(pagamento.getId());
		dto.setIdModoPagamento(pagamento.getModoPagamento().getId());
		dto.setValorPagamento(pagamento.getValorPagamento());
		dto.setIdVenda(pagamento.getIdVenda());
		dto.setQuantidadeParcela(pagamento.getQuantidadeParcela());
		dto.setDataPagamento(pagamento.getDataPagamento());
		dto.setTroco(pagamento.getTroco());
		dto.setModoPagamentoDescricao(pagamento.getModoPagamento().getDescricao());
		return dto;
	}

	public Pagamento update(Long id, Pagamento pagamento) {
		Optional<Pagamento> newCategoria = pagamentoRepository.findById(id);

//        newCategoria.get().setNome(categoria.getNome());
		return pagamentoRepository.save(newCategoria.get());
	}

	public List<PagamentoDTO> findByVendaId(Long vendaId) {
		List<PagamentoDTO> pagamentosDTO = new ArrayList<PagamentoDTO>();
		List<Pagamento> pagamentos = pagamentoRepository.findByIdVenda(vendaId);
		pagamentos.forEach(pagamento -> {
			PagamentoDTO dto = new PagamentoDTO();
			dto.setId(pagamento.getId());
			dto.setIdModoPagamento(pagamento.getModoPagamento().getId());
			dto.setValorPagamento(pagamento.getValorPagamento());
			dto.setIdVenda(pagamento.getIdVenda());
			dto.setQuantidadeParcela(pagamento.getQuantidadeParcela());
			dto.setDataPagamento(pagamento.getDataPagamento());
			dto.setTroco(pagamento.getTroco());
			dto.setModoPagamentoDescricao(pagamento.getModoPagamento().getDescricao());
			pagamentosDTO.add(dto);
		});
		return pagamentosDTO;
	}
}
