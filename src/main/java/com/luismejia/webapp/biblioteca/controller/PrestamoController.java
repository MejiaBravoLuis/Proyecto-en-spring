package com.luismejia.webapp.biblioteca.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luismejia.webapp.biblioteca.model.Libro;
import com.luismejia.webapp.biblioteca.model.Prestamo;
import com.luismejia.webapp.biblioteca.service.LibroService;
import com.luismejia.webapp.biblioteca.service.PrestamoService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RestController
@RequestMapping("")
@CrossOrigin(value = "http://127.0.0.1:5500")
public class PrestamoController {

    @Autowired
    PrestamoService prestamoService;

    @Autowired
    LibroService libroService;

    @GetMapping("/prestamos") 
    public ResponseEntity<?> listarPrestamos(){
        Map<String, String> response = new HashMap<>();
        try {
            return ResponseEntity.ok(prestamoService.listarPrestamos());
        } catch (Exception e) {
            response.put("message: ", "Error");
            response.put("err", "No se encontró una lista de libros");
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/prestamo")
    public ResponseEntity<Map<String, String>> agregarPrestamo(@RequestBody Prestamo prestamo){
        Map<String, String> response = new HashMap<>();

    try {
        
        if (prestamo.getLibros().size() > 3) {
            response.put("message", "No puedes pedir más de 3 libros por préstamo.");
            return ResponseEntity.badRequest().body(response);
        }
        
        for (Libro libro : prestamo.getLibros()) {
            Libro libroActual = libroService.buscarLibroPorId(libro.getId());
            if (libroActual == null || !libroActual.getDisponibilidad()) {
                response.put("message", "El libro " + libro.getNombre() + " no está disponible.");
                return ResponseEntity.badRequest().body(response);
            }
        }

        if (prestamoService.validacionPrestamoVigente(prestamo.getCliente().getDpi())) {
            response.put("message", "El cliente ya con tiene un préstamo vigente.");
            return ResponseEntity.badRequest().body(response);
        }

        for (Libro libro : prestamo.getLibros()) {
            libro.setDisponibilidad(false);
            libroService.guardarLibro(libro);  
        }

        prestamoService.guardarPrestamo(prestamo);
        response.put("message", "Préstamo creado con éxito.");
        return ResponseEntity.ok(response);
        
    } catch (Exception e) {
        e.printStackTrace();
        response.put("message", "Error");
        response.put("err", "No se ha agregado el préstamo con éxito.");
        return ResponseEntity.badRequest().body(response);
    }
}

@PutMapping("/prestamo")
public ResponseEntity<Map<String, String>> editarPrestamo(@RequestParam Long id, @RequestBody Prestamo prestamoNuevo) {
    Map<String, String> response = new HashMap<>();
    try {
        Prestamo prestamo = prestamoService.buscarPrestamoPorId(id);
        prestamo.setFechaPrestamo(prestamoNuevo.getFechaPrestamo());
        prestamo.setFechaDevolucion(prestamoNuevo.getFechaDevolucion());
        prestamo.setVigencia(prestamoNuevo.getVigencia());
        prestamo.setEmpleado(prestamoNuevo.getEmpleado());
        prestamo.setCliente(prestamoNuevo.getCliente());
        if (prestamoNuevo.getLibros() != null && prestamoNuevo.getLibros().size() > 3) {
            throw new IllegalArgumentException("No se pueden prestar más de 3 libros por préstamo.");
        }
        prestamo.setLibros(prestamoNuevo.getLibros());
        prestamoService.guardarPrestamo(prestamo);
        response.put("message", "Se ha modificado correctamente");
        return ResponseEntity.ok(response);
    } catch (IllegalArgumentException e) {
        response.put("message", "Error");
        response.put("err", e.getMessage());
        return ResponseEntity.badRequest().body(response);
    } catch (Exception e) {
        response.put("message", "Error");
        response.put("err", "No se ha modificado con éxito");
        return ResponseEntity.badRequest().body(response);
    }
}

 
    @DeleteMapping("/prestamo")
    public ResponseEntity<Map<String, String>> eliminarPrestamo(@RequestParam Long id){
        Map<String, String> response = new HashMap<>();
        try {
            Prestamo prestamo = prestamoService.buscarPrestamoPorId(id);
 
            prestamoService.eliminarPrestamo(prestamo);
            response.put("message", "Se ha elimnado con exito");
            return ResponseEntity.ok(response);
 
        } catch (Exception e) {
            response.put("message" ,"error" );
            response.put("err" ,"No se ha eliminado con exito" );
            return ResponseEntity.badRequest().body(response);
        }
    }

}

