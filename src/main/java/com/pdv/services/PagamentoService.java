package com.pdv.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pdv.entities.Pagamento;
import com.pdv.repositories.PagamentoRepository;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;


    public List<Pagamento> findAll() {
       return pagamentoRepository.findAll();
    }

    public Pagamento findById(Long id) {
        Optional<Pagamento> objCategoria = pagamentoRepository.findById(id);
        return objCategoria.get();
    }

    public Pagamento insert(Pagamento pagamento) {
        return pagamentoRepository.save(pagamento);
    }

    public Pagamento update(Long id, Pagamento pagamento) {
        Optional<Pagamento> newCategoria = pagamentoRepository.findById(id);

//        newCategoria.get().setNome(categoria.getNome());
        return pagamentoRepository.save(newCategoria.get());
    }
}
