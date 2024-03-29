package com.pdv.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pdv.dto.PagamentoDTO;
import com.pdv.entities.Pagamento;
import com.pdv.services.PagamentoService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/pagamento")
public class PagamentoResource {

	@Autowired
	private PagamentoService pagamentoService;

	@GetMapping()
	public ResponseEntity<List<Pagamento>> findAll() {
		List<Pagamento> pagamentos = pagamentoService.findAll();
		return ResponseEntity.ok().body(pagamentos);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Pagamento> findById(@PathVariable Long id) {
		Pagamento pagamento = pagamentoService.findById(id);
		return ResponseEntity.ok().body(pagamento);
	}
	
	@GetMapping(value = "/vendaId/{vendaId}")
	public ResponseEntity<List<?>> findByIdVenda(@PathVariable Long vendaId) {
		List<PagamentoDTO> pagamentos = pagamentoService.findByVendaId(vendaId);
		return ResponseEntity.ok().body(pagamentos);
	}

	@PostMapping
	public ResponseEntity<?> insert(@RequestBody Pagamento pagamento) {
		
		PagamentoDTO  dto =pagamentoService.insert(pagamento);
		if(dto==null) {
			return (ResponseEntity<?>) ResponseEntity.badRequest();
		}
		return ResponseEntity.ok(dto);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Pagamento> update(@PathVariable Long id, @RequestBody Pagamento pagamento) {
		pagamento = pagamentoService.update(id, pagamento);
		return ResponseEntity.ok().body(pagamento);
	}
}
