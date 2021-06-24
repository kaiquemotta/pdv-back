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

import com.pdv.entities.Venda;
import com.pdv.services.VendaService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/venda")
public class VendaResource {
	
    @Autowired
    private VendaService vendaService;

    @GetMapping()
    public ResponseEntity<List<Venda>> findAll(){
        List<Venda> categorias = vendaService.findAll();
        return ResponseEntity.ok().body(categorias);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Venda> findById(@PathVariable Long id){
    	Venda venda = vendaService.findById(id);
        return ResponseEntity.ok().body(venda);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody Venda venda){
        venda = vendaService.insert(venda);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(venda.getId()).toUri();
        System.out.println(venda.toString());
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Venda> update(@PathVariable Long id, @RequestBody Venda venda){
        venda = vendaService.update(id, venda);
        return ResponseEntity.ok().body(venda);
    }

}
