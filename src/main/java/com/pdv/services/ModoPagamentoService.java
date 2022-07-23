package com.pdv.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pdv.entities.ModoPagamento;
import com.pdv.repositories.ModoPagamentoRepository;

@Service
public class ModoPagamentoService {

	@Autowired
	private ModoPagamentoRepository modoPagamentoRepository;

	public List<ModoPagamento> findAll() {
		return modoPagamentoRepository.findAll();
	}

	public ModoPagamento findById(Long id) {
		Optional<ModoPagamento> objCategoria = modoPagamentoRepository.findById(id);
		return objCategoria.get();
	}

	public ModoPagamento insert(ModoPagamento modoPagamento) {
	//	modoPagamento.setPorcentagemDesconto(modoPagamento.getPorcentagemDesconto() / 100);
		return modoPagamentoRepository.save(modoPagamento);
	}

	public ModoPagamento update(Long id, ModoPagamento modoPamento) {
		Optional<ModoPagamento> newModoPagamento = modoPagamentoRepository.findById(id);

		newModoPagamento.get().setDescricao(modoPamento.getDescricao());
		newModoPagamento.get().setPorcentagemDesconto(modoPamento.getPorcentagemDesconto());
		
		return modoPagamentoRepository.save(newModoPagamento.get());
	}
}
