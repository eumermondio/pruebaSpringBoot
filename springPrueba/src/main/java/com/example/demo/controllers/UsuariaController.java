package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
			dto.put("pass", u.getPass());
			dto.put("username", u.getUsername());
			usuarios.add(dto);
		}
		return usuarios;
	}

	@GetMapping("/obtener/{id}")
	public DTO getUsuario(@PathVariable("id") int id) {
		Usuaria u = usuRep.findById(id);
		DTO dto = new DTO();
		dto.put("nombre", u.getNombre());
		dto.put("fecha_nac", u.getFechaNac().toString());
		dto.put("pass", u.getPass());
		dto.put("username", u.getUsername());
		return dto;
	}

	@PostMapping(path = "/crear", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void autenticaUsuario(@RequestBody DatosAltaUsuario u, HttpServletRequest request) {
		usuRep.save(new Usuaria(u.id, null, u.fechaNac, DatatypeConverter.parseBase64Binary(u.img), u.nombre, u.pass,
				u.username, usuTipo.findById(u.rol)));
	}

	static class DatosAltaUsuario {
		int id;
		Date fechaElim;
		Date fechaNac;
		String img;
		// private byte[] img;
		String nombre;
		String username;
		String pass;
		int rol;

		public DatosAltaUsuario(int id, Date fechaElim, Date fechaNac, String img, String nombre, String username,
				String pass, int rol) {
			super();
			this.id = id;
			this.fechaElim = fechaElim;
			this.fechaNac = fechaElim;
			this.img = img;
			this.nombre = nombre;
			this.username = username;
			this.pass = pass;
			this.rol = rol;
		}
	}
}
