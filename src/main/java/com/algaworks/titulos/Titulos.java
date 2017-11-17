package com.algaworks.titulos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.model.Titulo;

//Interface que extende JPA Repository que tem métodos do CRUD:
public interface Titulos extends JpaRepository <Titulo, Long>{

}
