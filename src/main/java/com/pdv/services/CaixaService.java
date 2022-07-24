package com.pdv.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pdv.dto.CaixaDTO;
import com.pdv.entities.Caixa;
import com.pdv.entities.Pagamento;
import com.pdv.repositories.CaixaRepository;
import com.pdv.repositories.PagamentoRepository;


@Service
public class CaixaService {
	@Autowired
	private CaixaRepository caixaRepository;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	public List<?> findAll() {
		
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        List<Caixa>listCaixa = caixaRepository.findAll();
        List<CaixaDTO>listDTO= new ArrayList<CaixaDTO>();
        
        listCaixa.forEach(a->{
        	CaixaDTO dto = new CaixaDTO();
        	dto.setId(a.getId());
        	dto.setAberto(a.isAberto());
        	dto.setNome(a.getNome());
        	dto.setValorAbertura(a.getValorAbertura());
        	dto.setValorFechamento(a.getValorFechamento());
        	dto.setDataAbertura(a.getDataAbertura().format(formatter));
        	dto.setDataFechamento(a.getDataFechamento() != null ? a.getDataFechamento().format(formatter): null);
        	listDTO.add(dto);
        });
		return listDTO;
	}

	public Caixa findById(Long id) {
		Optional<Caixa> objCategoria = caixaRepository.findById(id);
		return objCategoria.get();
	}

	public Caixa insert(Caixa caixa) {

		if (verificaAbertoNow()) {
			caixa.setDataAbertura(LocalDateTime.now());
			caixa.setAberto(true);
		} else {
			return null;
		}

		return caixaRepository.save(caixa);
	}

	private boolean verificaAbertoNow() {
		List<Caixa> caixas = caixaRepository.findByAbertoAndDataAberturaBetween(true, getInicio(),
				getInicio().plusHours(23).plusMinutes(59));
		if (caixas.size() >= 1) {
			return false;
		}
		return true;
	}

	private LocalDateTime getInicio() {
		LocalDateTime localDateTime5 = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(),
				LocalDateTime.now().getDayOfMonth(), 00, 00, 00);
		return localDateTime5;
	}

	public Caixa update(Long id, Caixa caixa) {
		Optional<Caixa> newCaixa = caixaRepository.findById(id);

		newCaixa.get().setNome(caixa.getNome());
		newCaixa.get().setIdUsuario(caixa.getIdUsuario());
		newCaixa.get().setValorAbertura(caixa.getValorAbertura());
		newCaixa.get().setValorFechamento(caixa.getValorFechamento());
		//
		newCaixa.get().setValorFechamentoDinheiro(caixa.getValorFechamentoDinheiro());
		newCaixa.get().setValorFechamentoPix(caixa.getValorFechamentoPix());
		newCaixa.get().setValorFechamentoCartaoCredito(caixa.getValorFechamentoCartaoCredito());
		newCaixa.get().setValorFechamentoCartaoDebito(caixa.getValorFechamentoCartaoDebito());
		newCaixa.get().setValorFechamentoConsignado(caixa.getValorFechamentoConsignado());
		//
		newCaixa.get().setDiferencaDinheiro(caixa.getDiferencaDinheiro());
		newCaixa.get().setDiferencaPix(caixa.getDiferencaPix());
		newCaixa.get().setDiferencaCartaoCredito(caixa.getDiferencaCartaoCredito());
		newCaixa.get().setDiferencaCartaoDebito(caixa.getDiferencaCartaoDebito());

		newCaixa.get().setDiferencaConsignado(caixa.getDiferencaConsignado());
		//
		newCaixa.get().setValorPagamentoDinheiro(caixa.getValorPagamentoDinheiro());
		newCaixa.get().setValorPagamentoPix(caixa.getValorPagamentoPix());
		newCaixa.get().setValorPagamentoCartaoCredito(caixa.getValorPagamentoCartaoCredito());
		newCaixa.get().setValorPagamentoCartaoDebito(caixa.getValorPagamentoCartaoDebito());

		newCaixa.get().setValorPagamentoConsignado(caixa.getValorPagamentoConsignado());
		
		newCaixa.get().setAberto(false);
		newCaixa.get().setDataFechamento(LocalDateTime.now());


		return caixaRepository.save(newCaixa.get());
	}
	
	public Long getIdUltimoAbertoNow() {
		List<Caixa> caixas = caixaRepository.findByAbertoAndDataAberturaBetween(true, getInicio(),
				getInicio().plusHours(23).plusMinutes(59));
		return caixas.get(0).getId();
	}
	
	public Caixa getFechamentoCaixa() {
		List<Caixa> caixas = caixaRepository.findByAbertoAndDataAberturaBetween(true, getInicio(),
				getInicio().plusHours(23).plusMinutes(59));
		if(caixas.size()!=0) {
			Caixa caixa = caixas.get(0);
	
			double troco = caixa.getPagamentos().stream().filter(c -> c.getModoPagamento().getId()==1).mapToDouble(f -> f.getTroco()).sum();
			double valorPagamentoDinheiro = caixa.getPagamentos().stream().filter(c -> c.getModoPagamento().getId()==1).mapToDouble(f -> f.getValorPagamento()).sum();

	   
	    caixa.setValorPagamentoDinheiro(valorPagamentoDinheiro -troco);
	    caixa.setValorPagamentoPix(caixa.getPagamentos().stream().filter(c -> c.getModoPagamento().getId()==2).mapToDouble(f -> f.getValorPagamento()).sum());
	    caixa.setValorPagamentoCartaoCredito(caixa.getPagamentos().stream().filter(c -> c.getModoPagamento().getId()==3).mapToDouble(f -> f.getValorPagamento()).sum());
	    caixa.setValorPagamentoCartaoDebito(caixa.getPagamentos().stream().filter(c -> c.getModoPagamento().getId()==4).mapToDouble(f -> f.getValorPagamento()).sum());
	    caixa.setValorPagamentoConsignado(caixa.getPagamentos().stream().filter(c -> c.getModoPagamento().getId()==5).mapToDouble(f -> f.getValorPagamento()).sum());
	    
	    caixa.setValorFechamentoDinheiro(caixa.getValorFechamentoDinheiro() == null? 0 :caixa.getValorFechamentoDinheiro());
	    caixa.setValorFechamentoPix(caixa.getValorFechamentoPix() == null? 0 :caixa.getValorFechamentoPix());
	    caixa.setValorFechamentoCartaoCredito(caixa.getValorFechamentoCartaoCredito() == null? 0 :caixa.getValorFechamentoCartaoCredito());
	    caixa.setValorFechamentoCartaoDebito(caixa.getValorFechamentoCartaoDebito() == null? 0 :caixa.getValorFechamentoCartaoDebito());
	    
		caixa.setValorFechamento(caixa.getPagamentos().stream().mapToDouble(f ->  f.getValorPagamento()).sum()-troco);

		return caixa;
		}else {
			return null;
		}
	}

	public List<Pagamento> pagamentosDia() {
		return pagamentoRepository.findByDataPagamento(LocalDateTime.now());
	}
}
