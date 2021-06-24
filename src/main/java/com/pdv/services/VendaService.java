package com.pdv.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public List<Venda> findAll() {
		return vendaRepository.findAll();
	}

	public Venda findById(Long id) {
		Optional<Venda> venda = vendaRepository.findById(id);
		return venda.get();
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
		List<ItemVenda>listItensVenda = itemVendaRepository.listItensVendaByVendaId(vendaId);
		double soma = listItensVenda.stream().mapToDouble(ItemVenda::getSubTotal).sum();
		Optional<Venda> v = vendaRepository.findById(vendaId);
		v.get().setValorTotal(soma);
		v.get().setSubTotal(soma);
		vendaRepository.save(v.get());
	}

}
