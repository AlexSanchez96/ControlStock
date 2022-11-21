package com.sem.controlstock.servicios;

import com.sem.controlstock.entidades.Cliente;
import com.sem.controlstock.excepciones.MiException;
import com.sem.controlstock.repositorios.ClienteRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServicio {
    
    @Autowired
    ClienteRepositorio clienteRepositorio;
    
    @Transactional
    public void crearCliente(String nombre, String email, String direccion, String lugar, String telefono) throws MiException{
        
        validar(nombre, email, lugar, telefono);
        
        Cliente cliente = new Cliente();
        
        cliente.setNombre(nombre);
        cliente.setEmail(email);
        cliente.setDireccion(direccion);
        cliente.setLugar(lugar);
        cliente.setTelefono(telefono);
        
        clienteRepositorio.save(cliente);
    }
    
    public List<Cliente> listarClientes(){
        
        List<Cliente> clientes = new ArrayList();
        clientes = clienteRepositorio.findAll();
        
        return clientes;
    }
    
    public void modificarCliente(String id, String nombre, String email, String direccion, String lugar, String telefono) throws MiException{
        
        validar(nombre, email, lugar, telefono);
        
        Optional<Cliente> respuesta = clienteRepositorio.findById(id);
        
        if (respuesta.isPresent()) {
            Cliente cliente = respuesta.get();
            
            cliente.setNombre(nombre);
            cliente.setEmail(email);
            cliente.setDireccion(direccion);
            cliente.setLugar(lugar);
            cliente.setTelefono(telefono);
            
            clienteRepositorio.save(cliente);
        }
    }
    
    private void validar(String nombre, String email, String lugar, String telefono) throws MiException{
        if (nombre == null || nombre.isEmpty()) {
            throw new MiException("El nombre no puede ser nulo ni estar vacio");
        }
        
        if (email == null || email.isEmpty()) {
            throw new MiException("El email no puede ser nulo ni estar vacio");
        }
        
        if (lugar == null || lugar.isEmpty()) {
            throw new MiException("El lugar no puede ser nulo ni estar vacio");
        }
        
        if (telefono == null) {
            throw new MiException("El telefono no puede ser nulo");
        }
        
    }
}
