package com.luismejia.webapp.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luismejia.webapp.biblioteca.model.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long>{

}
