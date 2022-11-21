package com.sem.controlstock.servicios;

import com.sem.controlstock.entidades.Proveedor;
import com.sem.controlstock.excepciones.MiException;
import com.sem.controlstock.repositorios.ProveedorRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProveedorServicio {
    
    @Autowired
    ProveedorRepositorio proveedorRepositorio;
    
    @Transactional
    public void crearProveedor(String nombre, String email, String lugar, String telefono) throws MiException{
        
        validar(nombre, email, lugar, telefono);
        
        Proveedor proveedor = new Proveedor();
        
        proveedor.setNombre(nombre);
        proveedor.setEmail(email);
        proveedor.setLugar(lugar);
        proveedor.setTelefono(telefono);
        
        proveedorRepositorio.save(proveedor);
    }
    
    public List<Proveedor> listarProveedores(){
        List<Proveedor> proveedores = new ArrayList();
        
        proveedores = proveedorRepositorio.findAll();
        
        return proveedores;
    }
    
    public void modificarProveedor(String id, String nombre, String email, String telefono, String lugar) throws MiException{
        
        validar(nombre, email, lugar, telefono);
        
        Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);
        
        if (respuesta.isPresent()) {
            Proveedor proveedor = respuesta.get();
            
            proveedor.setNombre(nombre);
            proveedor.setEmail(email);
            proveedor.setTelefono(telefono);
            proveedor.setLugar(lugar);
            
            proveedorRepositorio.save(proveedor);
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
        
        if (telefono == null || telefono.isEmpty()) {
            throw new MiException("El telefono no puede ser nulo");
        }
        
    }
}
