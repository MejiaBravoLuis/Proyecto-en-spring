package com.luismejia.webapp.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luismejia.webapp.biblioteca.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
