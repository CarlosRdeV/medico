package com.wtf.medico.entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class User {

	@Id
	private Integer id;
	private String nombre;
	private String password;
	
}
