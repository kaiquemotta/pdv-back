package com.pdv.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pdv.entities.Caixa;

@Repository
public interface CaixaRepository extends JpaRepository<Caixa, Long> {

	public List<Caixa> findByAbertoAndDataAberturaBetween(boolean aberto, LocalDateTime inicio,
			LocalDateTime dataFinal);

}
