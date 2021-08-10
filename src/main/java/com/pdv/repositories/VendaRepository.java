package com.pdv.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pdv.entities.Venda;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

	@Query(value = "SELECT COUNT(id) FROM tb_venda  WHERE finalizada = 0 and nome_comanda = :nomeComanda and data_criacao_venda > (now() - INTERVAL 7 DAY)  GROUP BY nome_comanda HAVING COUNT(nome_comanda) >= 1", nativeQuery = true)
	Long getQuantidadeComandaEmAbertoInterval7(@Param("nomeComanda") String nomeComanda);
	
	public List<Venda>findByFinalizadaFalse();
	
}
