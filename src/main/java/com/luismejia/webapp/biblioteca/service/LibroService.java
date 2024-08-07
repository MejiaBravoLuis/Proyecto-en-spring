package com.luismejia.webapp.biblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luismejia.webapp.biblioteca.model.Libro;
import com.luismejia.webapp.biblioteca.repository.LibroReporitory;

@Service
public class LibroService implements ILibroService {

    @Autowired
    LibroReporitory libroReporitory;

    @Override
    public List<Libro> listarLibros() {
        return libroReporitory.findAll();
    }

    @Override
    public Libro buscarLibroPorId(Long id) {
        return libroReporitory.findById(id).orElse(null);
    }

    @Override
    public Libro guardarLibro(Libro libro) {
        return libroReporitory.save(libro);
    }

    @Override
    public void eliminarLibro(Libro libro) {
        libroReporitory.delete(libro);
    }

}
