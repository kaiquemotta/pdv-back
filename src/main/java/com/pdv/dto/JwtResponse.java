package com.pdv.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {

	private String jwt;
	private Long id;
	private String userName;
	private String email;
	private List<String>roles;
	
	
	public JwtResponse(String jwt, Long id, String userName, String email, List<String> roles) {
		super();
		this.jwt = jwt;
		this.id = id;
		this.userName = userName;
		this.email = email;
		this.roles = roles;
	}
	
	
	
}
