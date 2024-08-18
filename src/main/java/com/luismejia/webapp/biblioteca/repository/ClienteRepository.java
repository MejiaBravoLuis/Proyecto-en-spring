package com.luismejia.webapp.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luismejia.webapp.biblioteca.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
    
}
