package com.pdv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.pdv.entities.Produto;

@Service
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
