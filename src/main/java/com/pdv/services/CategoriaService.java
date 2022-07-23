package com.pdv.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pdv.entities.Categoria;
import com.pdv.repositories.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;


    public List<Categoria> findAll() {
       return categoriaRepository.findAll();
    }

    public Categoria findById(Integer id) {
        Optional<Categoria> objCategoria = categoriaRepository.findById(id);
        return objCategoria.get();
    }

    public Categoria insert(Categoria objCategoria) {
        return categoriaRepository.save(objCategoria);
    }

    public Categoria update(Integer id, Categoria categoria) {
        Optional<Categoria> newCategoria = categoriaRepository.findById(id);

        newCategoria.get().setNome(categoria.getNome());
        newCategoria.get().setImpressora(categoria.getImpressora());

        return categoriaRepository.save(newCategoria.get());
    }
}
