package com.pdv.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pdv.entities.ItemVenda;

@Repository
public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {
	
	@Query(value = "SELECT * FROM tb_item_venda WHERE venda_id = :vendaId",nativeQuery = true)
	List<ItemVenda>listItensVendaByVendaId(@Param("vendaId")Long vendaId);

}
