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
import com.pdv.reports.VendaPDF;
import com.pdv.services.VendaService;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
 
import javax.servlet.http.HttpServletResponse;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
 
import com.lowagie.text.DocumentException;
 
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/venda")
public class VendaResource {
	
    @Autowired
    private VendaService vendaService;

    
    @GetMapping()
    public ResponseEntity<List<Venda>> findAll(){
        List<Venda> vendas = vendaService.findAll();
        return ResponseEntity.ok().body(vendas);
    }
    
    @GetMapping(value = "/emAberto")
    public ResponseEntity<List<Venda>> findAllVendaAbertas(){
        List<Venda> vendas = vendaService.findVendasEmAberto();
        return ResponseEntity.ok().body(vendas);
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
    

    @PutMapping(value = "finaliza/{id}")
    public ResponseEntity<Venda> finalizaVenda(@PathVariable Long id){
     Venda    venda = vendaService.finalizaVenda(id);
        return ResponseEntity.ok().body(venda);
    }
    
    @GetMapping("/vendas/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        //caso queira realiazr download, apenas descomentar essa linha
        //response.setHeader(headerKey, headerValue);
         
        List<Venda> vendas = vendaService.findAll();
         
        VendaPDF exporter = new VendaPDF(vendas);
        exporter.export(response);
         
    }
}
