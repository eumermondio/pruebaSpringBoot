package com.example.demo.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Usuaria;
import com.example.demo.UsuarioTipo;

@Repository

public interface TipoUsuarioRepositorio extends JpaRepository<UsuarioTipo, Serializable> {
	@Bean
	public abstract List<UsuarioTipo> findAll();

	public abstract UsuarioTipo findById(int id);

	@Transactional
	public abstract void deleteById(int id);

	@Transactional
	public abstract Usuaria save(Usuaria tu);

}