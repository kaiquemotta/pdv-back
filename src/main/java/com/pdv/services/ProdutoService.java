package com.pdv.services;

import com.pdv.entities.Produto;
import com.pdv.repositories.ProdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;


    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    public Produto findById(Integer id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        return produto.get();
    }

    public Produto insert(Produto objProduto) {

        objProduto.setCategoria(objProduto.getCategoria());
        objProduto.getCategoria().getProdutos().add(objProduto);

        //System.out.println("");
        return produtoRepository.save(objProduto);
    }

    public Produto update(Integer id, Produto produto) {
        Optional<Produto> newProduto = produtoRepository.findById(id);

        newProduto.get().setNome(produto.getNome());
        newProduto.get().setPreco(produto.getPreco());
        newProduto.get().setCategoria(produto.getCategoria());

        return produtoRepository.save(newProduto.get());
    }

    public void delete(Integer id) {
        produtoRepository.deleteById(id);
    }
}
