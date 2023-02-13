package com.example.demo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the habla database table.
 * 
 */
@Entity
@Table(name="habla")
@NamedQuery(name="Habla.findAll", query="SELECT h FROM Habla h")
public class Habla implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name="id_usuario")
	private int idUsuario;

	//bi-directional many-to-one association to Idioma
	@ManyToOne
	@JoinColumn(name="id_idioma")
	private Idioma idioma;

	public Habla() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Idioma getIdioma() {
		return this.idioma;
	}

	public void setIdioma(Idioma idioma) {
		this.idioma = idioma;
	}

}