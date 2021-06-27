package com.pdv.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pdv.entities.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long>  {

	
	List<Pagamento> findByIdVenda(Long idVenda);
}
