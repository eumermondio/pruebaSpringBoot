package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Usuaria;
import com.example.demo.repositorio.TipoUsuarioRepositorio;
import com.example.demo.repositorio.UsuariaRepositorio;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UsuariaController {

	@Autowired
	UsuariaRepositorio usuRep;
	@Autowired
	TipoUsuarioRepositorio usuTipo;

	@GetMapping("/usuarios")
	public List<DTO> findAll() {
		List<DTO> usuarios = new ArrayList<DTO>();
		List<Usuaria> usrs = usuRep.findAll();
		for (Usuaria u : usrs) {
			DTO dto = new DTO();
			dto.put("nombre", u.getNombre());
			dto.put("fecha_nac", u.getFechaNac().toString());
			usuarios.add(dto);
		}
		return usuarios;
	}

}
