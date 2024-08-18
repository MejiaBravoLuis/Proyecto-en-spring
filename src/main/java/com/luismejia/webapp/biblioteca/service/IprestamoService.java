package com.luismejia.webapp.biblioteca.service;

import java.util.List;

import com.luismejia.webapp.biblioteca.model.Prestamo;

public interface IprestamoService {

    public List<Prestamo> listarPrestamos();

    public Prestamo buscarPrestamoPorId(Long id);

    public Prestamo guardarPrestamo(Prestamo prestamo);

    public void eliminarPrestamo(Prestamo prestamo);

    public boolean validacionPrestamoVigente(Long dpi); 
}
