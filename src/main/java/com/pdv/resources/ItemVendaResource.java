package com.pdv.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pdv.dto.ItemVendaDTO;
import com.pdv.entities.ItemVenda;
import com.pdv.services.ItemVendaService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/itemVenda")
public class ItemVendaResource {

	@Autowired
	private ItemVendaService itemVendaService;

	@PostMapping
	public ResponseEntity<List<ItemVendaDTO>> insert(@RequestBody List<ItemVenda> itensVenda) {
		List<ItemVendaDTO>listItensVendaDTO = itemVendaService.insertItens(itensVenda);
		return ResponseEntity.ok(listItensVendaDTO);
	}

	@GetMapping(value = "/venda/{id}")
	public ResponseEntity<List<ItemVendaDTO>> findById(@PathVariable Long id) {
		List<ItemVendaDTO> itemVenda = itemVendaService.listItemVendafindByIdVenda(id);

		return ResponseEntity.ok().body(itemVenda);
	}

	@DeleteMapping(value = "/{itemVendaId}/venda/{vendaId}")
	public ResponseEntity<Void> delete(@PathVariable Long itemVendaId, @PathVariable Long vendaId) {
		itemVendaService.delete(itemVendaId, vendaId);
		return ResponseEntity.noContent().build();
	}

}
