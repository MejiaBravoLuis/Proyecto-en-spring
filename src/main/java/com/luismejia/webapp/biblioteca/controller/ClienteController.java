package com.luismejia.webapp.biblioteca.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luismejia.webapp.biblioteca.model.Cliente;
import com.luismejia.webapp.biblioteca.service.ClienteService;

@Controller
@RestController
@RequestMapping("")
public class ClienteController{
    @Autowired
    ClienteService clienteService;

    @GetMapping("/clientes")
    public List<Cliente> listarClientes(){
        return clienteService.listarClientes();
    }

    @GetMapping("/cliente")
    public ResponseEntity<Cliente> buscarClientePorId(@PathVariable Long dpi){
        try {
            Cliente cliente = clienteService.buscarClientePorDPI(dpi);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/cliente")
    public ResponseEntity<Map<String,String >> agregarCliente(@RequestBody Cliente cliente) {
        Map<String,String> response = new HashMap<>();
        try {
            clienteService.guardarCliente(cliente);
            response.put("message","Cliente agregado con exito" );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message" ,"error" );
            response.put("err" ,"No se ha agregado el Cliente" );
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/cliente")
    public ResponseEntity <Map<String, String>> editarCliente(@PathVariable Long dpi, @RequestBody Cliente clienteNuevo){
        Map<String,String> response = new HashMap<>();
        try {
            Cliente cliente = clienteService.buscarClientePorDPI(dpi);
            cliente.setNombre(clienteNuevo.getNombre());
            cliente.setApellido(clienteNuevo.getApellido());
            cliente.setTelefono(clienteNuevo.getTelefono());
            clienteService.guardarCliente(cliente);
            response.put("message", "Se he modificado correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message" ,"error" );
            response.put("err" ,"No se ha modificado con exito" );
            return ResponseEntity.badRequest().body(response);
        }


    }

    @DeleteMapping("/cliente")
    public ResponseEntity<Map<String, String>> eliminarCliente(@PathVariable Long dpi){
        Map<String, String> response = new HashMap<>();
        try {
            Cliente cliente = clienteService.buscarClientePorDPI(dpi);

            clienteService.eliminarCliente(cliente);
            response.put("message", "Se ha elimnado con exito");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("message" ,"error" );
            response.put("err" ,"No se ha eliminado con exito" );
            return ResponseEntity.badRequest().body(response);
        }
    }
    
}
