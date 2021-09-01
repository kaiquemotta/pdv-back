package com.pdv.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public List<Caixa> findAll() {
		return caixaRepository.findAll();
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
		newCaixa.get().setAberto(false);
		newCaixa.get().setDataFechamento(LocalDateTime.now());
		newCaixa.get().setDiferencaAvista(caixa.getDiferencaAvista());
		newCaixa.get().setDiferencaCartao(caixa.getDiferencaCartao());
		newCaixa.get().setValorFechamento(caixa.getValorFechamento());
		newCaixa.get().setValorFechamentoAvista(caixa.getValorFechamentoAvista());
		newCaixa.get().setValorFechamentoCartao(caixa.getValorFechamentoCartao());

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
		
	    Caixa caixa = caixas.get(0);
	    
	    caixa.setValorFechamento(caixa.getPagamentos().stream().mapToDouble(f -> f.getValorPagamento()).sum());
	    caixa.setValorFechamentoAvista(caixa.getPagamentos().stream().filter(c -> c.getModoPagamento().isaVista()).mapToDouble(f -> f.getValorPagamento()).sum());
	    caixa.setValorFechamentoCartao(caixa.getPagamentos().stream().filter(c -> !c.getModoPagamento().isaVista()).mapToDouble(f -> f.getValorPagamento()).sum());
	    
		return caixa;
	}

	public List<Pagamento> pagamentosDia() {
		return pagamentoRepository.findByDataPagamento(LocalDateTime.now());
	}
}
