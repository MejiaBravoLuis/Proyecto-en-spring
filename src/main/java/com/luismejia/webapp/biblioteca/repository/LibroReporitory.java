package com.luismejia.webapp.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luismejia.webapp.biblioteca.model.Libro;

public interface LibroReporitory extends JpaRepository<Libro, Long>{
    
}
