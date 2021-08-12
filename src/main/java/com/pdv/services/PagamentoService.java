package com.pdv.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public Pagamento insert(Pagamento pagamento) {
		Caixa c = caixaService.findById(caixaService.getIdUltimoAbertoNow());
		pagamento.setDataPagamento(LocalDateTime.now());
		pagamento.setCaixa(c);
		Venda venda = vendaService.findById(pagamento.getIdVenda());
		pagamento.setVenda(venda);
		return pagamentoRepository.save(pagamento);
	}

	public Pagamento update(Long id, Pagamento pagamento) {
		Optional<Pagamento> newCategoria = pagamentoRepository.findById(id);

//        newCategoria.get().setNome(categoria.getNome());
		return pagamentoRepository.save(newCategoria.get());
	}

	public List<Pagamento> findByVendaId(Long vendaId) {
		return pagamentoRepository.findByIdVenda(vendaId);
	}
}
