package com.example.demo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the usuario_tipo database table.
 * 
 */
@Entity
@Table(name="usuario_tipo")
@NamedQuery(name="UsuarioTipo.findAll", query="SELECT u FROM UsuarioTipo u")
public class UsuarioTipo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String rol;

	//bi-directional many-to-one association to Usuaria
	@OneToMany(mappedBy="usuarioTipo")
	private List<Usuaria> usuarias;

	public UsuarioTipo() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRol() {
		return this.rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public List<Usuaria> getUsuarias() {
		return this.usuarias;
	}

	public void setUsuarias(List<Usuaria> usuarias) {
		this.usuarias = usuarias;
	}

	public Usuaria addUsuaria(Usuaria usuaria) {
		getUsuarias().add(usuaria);
		usuaria.setUsuarioTipo(this);

		return usuaria;
	}

	public Usuaria removeUsuaria(Usuaria usuaria) {
		getUsuarias().remove(usuaria);
		usuaria.setUsuarioTipo(null);

		return usuaria;
	}

}