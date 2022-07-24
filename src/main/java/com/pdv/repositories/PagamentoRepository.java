package com.pdv.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pdv.entities.Caixa;
import com.pdv.entities.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long>  {

	
	List<Pagamento> findByIdVenda(Long idVenda);
	
	public List<Pagamento> findByDataPagamento(LocalDateTime dataPagamento);
	
	public List<Pagamento> findByCaixa(Caixa caixa);


}
