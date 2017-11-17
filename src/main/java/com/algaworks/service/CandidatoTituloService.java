package com.algaworks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algaworks.model.Titulo;
import com.algaworks.titulos.Titulos;

@Service
public class CandidatoTituloService {
	@Autowired
	private Titulos titulos;
	
	public void salvar(Titulo titulo) {
		
		try {
		// Interface que extende JPA Repository e que possui métodos de CRUD:
		titulos.save(titulo);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data inválido!");
		}
	}

	public void excluir(Long id) {
		// Interface que extende JPA Repository e que possui métodos de CRUD:
		titulos.delete(id);		
	}
}
