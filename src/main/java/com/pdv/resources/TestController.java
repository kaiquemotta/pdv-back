package com.pdv.resources;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.pdv.services.CategoriaService;

import lombok.Getter;
import lombok.Setter;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

	@Autowired
	private AmqpTemplate queueSender;
	@Autowired
	private CategoriaService categoriaService;

	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}

	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public String userAccess() {
		return "User Content.";
	}

	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public String moderatorAccess() {
		return "Moderator Board.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}

	@GetMapping(value = "/mensa")
	public String send() throws JsonProcessingException {

		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setHeader("ultima", "sim");
		Usuario u = new Usuario();
		u.setNome("kaiueq");
		u.setTelefone("123");
		u.setEmail("123");
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(u);
		Message message = new Message(json.getBytes(), messageProperties);
	
		queueSender.convertAndSend("ExchangeUsuario", "criarUsuario",json);
		return "ok. done";
	}
	
	@Getter
	@Setter
	public class Usuario {
	    private String nome;
	    private String telefone;
	    private String email;
	}

}