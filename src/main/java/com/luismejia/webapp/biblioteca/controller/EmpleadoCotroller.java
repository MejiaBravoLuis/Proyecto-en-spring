package com.luismejia.webapp.biblioteca.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luismejia.webapp.biblioteca.model.Empleado;
import com.luismejia.webapp.biblioteca.service.EmpleadoService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;



@Controller
@RestController
@RequestMapping("")
public class EmpleadoCotroller {
    @Autowired
    EmpleadoService empleadoService;

    @GetMapping("/empleados")
    public ResponseEntity<List<Empleado>> listarEmpleados() {
        try {
            return ResponseEntity.ok(empleadoService.listarEmpleados());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/empleado")
    public ResponseEntity <Empleado> buscarEmpleadoPorId(@RequestParam long id) {
        try {
            return ResponseEntity.ok(empleadoService.buscarEmpleadoPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    

    @PostMapping("/empleado")
    public ResponseEntity<Map<String,String>> agregarEmpleado(@RequestBody Empleado empleado) {
        Map<String,String> response = new HashMap<>();

        try {
            if (!empleadoService.verificacionDpiDuplicado(empleado)) {
                empleadoService.guardarEmpleado(empleado);
                response.put("message", "Se ha creado con exito");
                return ResponseEntity.ok(response);
            }else{
                response.put("message", "error");
                response.put("err", "El dpi se encuentra duplicado");
                return ResponseEntity.badRequest().body(response);
            } 
            
            
        } catch (Exception e) {
            response.put("message" ,"error" );
            response.put("err" ,"No se ha agregado con exito" );
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/empleado")
    public ResponseEntity <Map<String, String>> editarEmpleado(@RequestParam Long id, @RequestBody Empleado empleadoNuevo) {
        Map<String,String> response = new HashMap<>();
        try {
            Empleado empleado = empleadoService.buscarEmpleadoPorId(id);
            empleado.setApellido(empleadoNuevo.getApellido());
            empleado.setDireccion(empleadoNuevo.getDireccion());
            empleado.setDpi(empleado.getDpi());
            empleado.setNombre(empleadoNuevo.getNombre());
            empleado.setTelefono(empleadoNuevo.getTelefono());
            if (!empleadoService.verificacionDpiDuplicado(empleado)) {
                empleadoService.guardarEmpleado(empleado);
                response.put("message", "Empelado editado con éxito");
                return ResponseEntity.ok(response);
            }else{
                response.put("message", "error");
                response.put("err", "El dpi se encuentra duplicado");
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (Exception e) {
            response.put("message" ,"error" );
            response.put("err" ,"No se ha podido modificar" );
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/empleado")
    public ResponseEntity<Map<String, String>> eliminarEmpleado(@RequestParam Long id){
        Map<String, String> response = new HashMap<>();
        try {
            Empleado empleado = empleadoService.buscarEmpleadoPorId(id);

            empleadoService.eliminarEmpleado(empleado);
            response.put("message", "Se ha elimnado con exito");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("message" ,"error" );
            response.put("err" ,"No se ha eliminado con exito" );
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    
}
