package com.pdv.resources;

import java.io.IOException;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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

import com.lowagie.text.DocumentException;
import com.pdv.entities.Caixa;
import com.pdv.entities.Pagamento;
import com.pdv.reports.FechamentoCaixaPDF;
import com.pdv.services.CaixaService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/caixa")
public class CaixaResource {

	@Autowired
	private CaixaService caixaService;

	@GetMapping()
	public ResponseEntity<List<Caixa>> findAll() {
		List<Caixa> caixas = caixaService.findAll();
		return ResponseEntity.ok().body(caixas);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Caixa> findById(@PathVariable Long id) {
		Caixa caixa = caixaService.findById(id);
		return ResponseEntity.ok().body(caixa);
	}

	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody Caixa objCaixa) {
		objCaixa = caixaService.insert(objCaixa);
		if (objCaixa == null) {
			return ResponseEntity.badRequest().body(null);
		}
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objCaixa.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Caixa> update(@PathVariable Long id, @RequestBody Caixa caixa) {
		caixa = caixaService.update(id, caixa);
		return ResponseEntity.ok().body(caixa);
	}

	@GetMapping(value = "/caixaFechamento")
	public ResponseEntity<Caixa> findByFechamento() {
		Caixa caixa = caixaService.getFechamentoCaixa();
		return ResponseEntity.ok().body(caixa);
	}

	@GetMapping("/caixa/export/pdf")
	public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
		// caso queira realiazr download, apenas descomentar essa linha
		// response.setHeader(headerKey, headerValue);

		List<Pagamento> vendas = caixaService.pagamentosDia();

		FechamentoCaixaPDF exporter = new FechamentoCaixaPDF(vendas);
		exporter.export(response);

	}

	@GetMapping("/caixa/download/pdf")
	public void downloadPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
		// caso queira realiazr download, apenas descomentar essa linha
		response.setHeader(headerKey, headerValue);

		List<Pagamento> vendas = caixaService.pagamentosDia();

		FechamentoCaixaPDF exporter = new FechamentoCaixaPDF(vendas);
		exporter.export(response);

	}

}
