package com.pdv.resources;

import java.net.URI;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pdv.entities.ModoPagamento;
import com.pdv.services.ModoPagamentoService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/modoPagamento")
public class ModoPagamentoResource {

	 @Autowired
	    private ModoPagamentoService modoPagamentoService;

	    @GetMapping()
	    public ResponseEntity<List<ModoPagamento>> findAll(){
	        List<ModoPagamento> categorias = modoPagamentoService.findAll();
	        return ResponseEntity.ok().body(categorias);
	    }

	    @GetMapping(value = "/{id}")
	    public ResponseEntity<ModoPagamento> findById(@PathVariable Long id){
	    	ModoPagamento modoPagamento = modoPagamentoService.findById(id);
	        return ResponseEntity.ok().body(modoPagamento);
	    }

	    @PostMapping
	    public ResponseEntity<Void> insert(@RequestBody ModoPagamento modoPagamento){
	        modoPagamento = modoPagamentoService.insert(modoPagamento);
	        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(modoPagamento.getId()).toUri();
	        return ResponseEntity.created(uri).build();
	    }

	    @PutMapping(value = "/{id}")
	    public ResponseEntity<ModoPagamento> update(@PathVariable Long id, @RequestBody ModoPagamento modoPagamento){
	        modoPagamento = modoPagamentoService.update(id, modoPagamento);
	        return ResponseEntity.ok().body(modoPagamento);
	    }
}
