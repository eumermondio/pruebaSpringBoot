package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Usuaria;
import com.example.demo.jwtSecurity.AutenticadorJWT;
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
			dto.put("id", u.getId());
			if (u.getFechaElim() != null) {
				dto.put("fecha_elim", u.getFechaElim().toString());
			}
			dto.put("nombre", u.getNombre());
			if (u.getFechaNac() != null) {
				dto.put("fecha_nac", u.getFechaNac().toString());
			}
			dto.put("imagen", u.getImg());
			dto.put("pass", u.getPass());
			dto.put("username", u.getUsername());
			if (u.getUsuarioTipo() != null) {
				dto.put("id_tipo_usuario", u.getUsuarioTipo().getId());
			}
			usuarios.add(dto);
		}
		return usuarios;
	}

	@GetMapping("/usuario/{id}")
	public DTO getUsuario(@PathVariable("id") int id) {
		Usuaria u = usuRep.findById(id);
		DTO dto = new DTO();
		if (u != null) {
			dto.put("nombre", u.getNombre());
			dto.put("fecha_nac", u.getFechaNac().toString());
			dto.put("pass", u.getPass());
			dto.put("username", u.getUsername());
		} else {
			dto.put("error", "not-found");
		}
		return dto;
	}

	@PostMapping(path = "/crear", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void cambiaUsuario(@RequestBody DatosAltaUsuario u, HttpServletRequest request) {
		usuRep.save(new Usuaria(u.id, u.fechaElim, u.fechaNac, DatatypeConverter.parseBase64Binary(u.img), u.nombre,
				u.pass, u.username, usuTipo.findById(u.rol)));
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
			this.fechaNac = fechaNac;
			this.img = img;
			this.nombre = nombre;
			this.username = username;
			this.pass = pass;
			this.rol = rol;
		}
	}

	@PutMapping(path = "/usuario/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void autenticaUsuario(@RequestBody DatosAltaUsuario u, HttpServletRequest request,
			@PathVariable("id") int id) {
		Usuaria usuarioAct = new Usuaria(id, null, u.fechaNac, DatatypeConverter.parseBase64Binary(u.img), u.nombre,
				u.pass, u.username, usuTipo.findById(u.rol));
		usuRep.save(usuarioAct);
	}

	@DeleteMapping("/usuario/{id}")
	public void borrarUsuario(@PathVariable("id") int id) {
		if (usuRep.findById(id) != null) {
			usuRep.deleteById(id);
		}
	}

	////////// Controlador de autenticacion //////////
	@PostMapping(path = "/autentica", consumes = MediaType.APPLICATION_JSON_VALUE)
	public DTO autentitcaUsuario(@RequestBody DatosAutenticacionUsuario datos, HttpServletRequest request,
			HttpServletResponse response) {

		DTO dto = new DTO();
		dto.put("result", "fail");

		Usuaria usuAutenticado = usuRep.findByUsernameAndPass(datos.username, datos.pass);

		// si existe el usuario y los datos son correctos, devolveremos un success y
		// todos los datos del usuario
		if (usuAutenticado != null) {
			dto.put("result", "success");

			// devolvemos jwt que usaremos de ahora en adelante en las cabeceras para
			// identificar al usuario
			dto.put("jwt", AutenticadorJWT.codificaJWT(usuAutenticado));

			// Prueba cookie. Quita el request y el response
			Cookie cook = new Cookie("jwt", AutenticadorJWT.codificaJWT(usuAutenticado));
			cook.setMaxAge(-1);
			response.addCookie(cook);
			//
		}

		return dto;
	}

	static class DatosAutenticacionUsuario {
		String username;
		String pass;

		public DatosAutenticacionUsuario(String username, String pass) {
			super();
			this.username = username;
			this.pass = pass;
		}

	}

	// Controlador devuevle el usuario autenticado

	@GetMapping("/quieneres")
	public DTO getAutenticado(HttpServletRequest request) {
		DTO dtoUsuaria = new DTO();
		Cookie[] c = request.getCookies();
		dtoUsuaria.put("result", "fail");
		int idUsuarioAutenticado = -1;
		for (Cookie co : c) {
			if (co.getName().equals("jwt"))
				// dtoUsuaria.put("id",
				idUsuarioAutenticado = AutenticadorJWT.getIdUsuarioDesdeJWT(co.getValue());
		}
		// identificamos usuario por jwt de cabecera recibida

		// int idUsuarioAutenticado =
		// AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request);

		Usuaria u = usuRep.findById(idUsuarioAutenticado);
		// dtoUsuaria.put("idfound", idUsuarioAutenticado);
		// si existe el usuario y los datos son correctos, devolveremos un success y
		// todos los datos del usuario
		if (u != null) {
			dtoUsuaria.put("result", "ok");
			dtoUsuaria.put("nombre", u.getNombre());
			dtoUsuaria.put("fecha_nac", u.getFechaNac().toString());
			if (u.getFechaElim() != null)
				dtoUsuaria.put("fecha_elim", u.getFechaElim().toString());
			else
				dtoUsuaria.put("fecha_elim", new Date(0));
			if (u.getUsuarioTipo() != null) {
				dtoUsuaria.put("rol", u.getUsuarioTipo().getRol());
			} else {
				dtoUsuaria.put("rol", "No tiene");
			}
		}

		return dtoUsuaria;
	}

}
