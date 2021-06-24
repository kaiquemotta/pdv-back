package com.pdv.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pdv.dto.ItemVendaDTO;
import com.pdv.entities.ItemVenda;
import com.pdv.entities.Produto;
import com.pdv.entities.Venda;
import com.pdv.repositories.ItemVendaRepository;

@Service
public class ItemVendaService {

	@Autowired
	private ItemVendaRepository itemRepository;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private VendaService vendaService;

	@Transactional()
	public List<ItemVendaDTO> insertItens(List<ItemVenda> itensVenda) {
		Venda v = null;
		double soma = 0;
		for (ItemVenda itenVenda : itensVenda) {
			v = new Venda();
			Produto p = produtoService.findById(itenVenda.getIdProduto());
			itenVenda.setSubTotal(p.getPreco() * itenVenda.getQuantidade());
			itenVenda.setProduto(p);
			itenVenda.setVenda(v);
			v.setId(itenVenda.getIdVenda());
			soma += itenVenda.getSubTotal();

		}
		v.setSubTotal(soma);
		v.setValorTotal(soma);
		vendaService.update(v.getId(), v);
		List<ItemVenda> listItens = itemRepository.saveAll(itensVenda);
		List<ItemVendaDTO> listaDTO = new ArrayList<ItemVendaDTO>();
		for (ItemVenda iv : listItens) {
			ItemVendaDTO dto = new ItemVendaDTO();
			dto.setId(iv.getId());
			dto.setIdVenda(iv.getIdVenda());
			dto.setProdutoNome(iv.getProduto().getNome());
			dto.setProdutoPreco(iv.getProduto().getPreco());
			dto.setQuantidade(iv.getQuantidade());
			dto.setSubTotal(iv.getSubTotal());
			dto.setIdProduto(iv.getProduto().getId());
			listaDTO.add(dto);
		}
		return listaDTO;

	}

	public List<ItemVendaDTO> listItemVendafindByIdVenda(Long id) {
		List<ItemVenda> itens = itemRepository.listItensVendaByVendaId(id);
		List<ItemVendaDTO> listaDTO = new ArrayList<ItemVendaDTO>();
		for (ItemVenda v : itens) {
			ItemVendaDTO dto = new ItemVendaDTO();
			dto.setId(v.getId());
			dto.setIdVenda(v.getIdVenda());
			dto.setProdutoNome(v.getProduto().getNome());
			dto.setProdutoPreco(v.getProduto().getPreco());
			dto.setQuantidade(v.getQuantidade());
			dto.setSubTotal(v.getSubTotal());
			dto.setIdProduto(v.getProduto().getId());
			listaDTO.add(dto);
		}
		return listaDTO;
	}

	public void delete(Long itemVendaID, Long vendaId) {
		itemRepository.deleteById(itemVendaID);
		vendaService.recalculaValorTotalVenda(vendaId);

	}

}
