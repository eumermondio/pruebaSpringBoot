package com.example.demo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the idioma database table.
 * 
 */
@Entity
@Table(name="idioma")
@NamedQuery(name="Idioma.findAll", query="SELECT i FROM Idioma i")
public class Idioma implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String nombre;

	//bi-directional many-to-one association to Habla
	@OneToMany(mappedBy="idioma")
	private List<Habla> hablas;

	public Idioma() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Habla> getHablas() {
		return this.hablas;
	}

	public void setHablas(List<Habla> hablas) {
		this.hablas = hablas;
	}

	public Habla addHabla(Habla habla) {
		getHablas().add(habla);
		habla.setIdioma(this);

		return habla;
	}

	public Habla removeHabla(Habla habla) {
		getHablas().remove(habla);
		habla.setIdioma(null);

		return habla;
	}

}