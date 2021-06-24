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

import com.pdv.entities.Categoria;
import com.pdv.services.CategoriaService;
import com.pdv.services.ModoPagamentoService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/modoPagamento")
public class ModoPagamentoResource {

	 @Autowired
	    private ModoPagamentoService modoPagamentoService;

	    @GetMapping()
	    public ResponseEntity<List<Categoria>> findAll(){
	        List<Categoria> categorias = modoPagamentoService.findAll();
	        return ResponseEntity.ok().body(categorias);
	    }

	    @GetMapping(value = "/{id}")
	    public ResponseEntity<Categoria> findById(@PathVariable Integer id){
	        Categoria categoria = modoPagamentoService.findById(id);
	        return ResponseEntity.ok().body(categoria);
	    }

	    @PostMapping
	    public ResponseEntity<Void> insert(@RequestBody Categoria objCategoria){
	        objCategoria = modoPagamentoService.insert(objCategoria);
	        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objCategoria.getId()).toUri();
	        return ResponseEntity.created(uri).build();
	    }

	    @PutMapping(value = "/{id}")
	    public ResponseEntity<Categoria> update(@PathVariable Integer id, @RequestBody Categoria categoria){
	        categoria = modoPagamentoService.update(id, categoria);
	        return ResponseEntity.ok().body(categoria);
	    }
}
