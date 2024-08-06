package com.luismejia.webapp.biblioteca.service;

import java.util.List;
import com.luismejia.webapp.biblioteca.model.Cliente;

public interface IClienteService {
    
    public List<Cliente> listarClientes(Cliente cliente);

    public Cliente guardarCliente(Cliente cliente);

    public Cliente buscarClientePorDPI(Long dpi);

    public void eliminarCliente(Cliente cliente);
}
