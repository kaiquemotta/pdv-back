package com.pdv.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {

	private Long id;

	private String username;
	
	private String email;
	
	private String password;
	
	private Set<String> role;
	
	
}
