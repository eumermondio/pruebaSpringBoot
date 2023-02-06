package com.example.demo.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Usuaria;

@Repository
public interface UsuariaRepositorio extends JpaRepository<Usuaria, Serializable> {

	@Bean
	public abstract List<Usuaria> findAll();

	public abstract Usuaria findById(int id);

	public abstract List<Usuaria> findByNombre(String userName);

	public abstract Usuaria findByUsernameAndPass(String userName, String pass);

	@Transactional
	public abstract void deleteById(int id);

	@Transactional
	public abstract Usuaria save(Usuaria i);

}