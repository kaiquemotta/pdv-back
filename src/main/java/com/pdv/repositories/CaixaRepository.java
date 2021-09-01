package com.pdv.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pdv.dto.RelatorioCaixaDTO;
import com.pdv.entities.Caixa;

@Repository
public interface CaixaRepository extends JpaRepository<Caixa, Long> {

	public List<Caixa> findByAbertoAndDataAberturaBetween(boolean aberto, LocalDateTime inicio,
			LocalDateTime dataFinal);

	@Query("select p.id_venda, p.valor_pagamento, p.quantidade_parcela, mp.descricao from tb_pagamento p inner join tb_modo_pagamento mp on mp.id = p.modo_pagamento_id where p.data_pagamento = now();")
	public List<RelatorioCaixaDTO> findPagamentosCaixaNow();

}
