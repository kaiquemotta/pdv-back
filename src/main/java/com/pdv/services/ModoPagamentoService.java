package com.pdv.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pdv.entities.Categoria;
import com.pdv.repositories.CategoriaRepository;

@Service
public class ModoPagamentoService {

    @Autowired
    private CategoriaRepository modoPagamentoRepository;


    public List<Categoria> findAll() {
       return modoPagamentoRepository.findAll();
    }

    public Categoria findById(Integer id) {
        Optional<Categoria> objCategoria = modoPagamentoRepository.findById(id);
        return objCategoria.get();
    }

    public Categoria insert(Categoria objCategoria) {
        return modoPagamentoRepository.save(objCategoria);
    }

    public Categoria update(Integer id, Categoria categoria) {
        Optional<Categoria> newCategoria = modoPagamentoRepository.findById(id);

        newCategoria.get().setNome(categoria.getNome());
        return modoPagamentoRepository.save(newCategoria.get());
    }
}
