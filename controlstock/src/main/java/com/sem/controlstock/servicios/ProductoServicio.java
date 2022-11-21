package com.sem.controlstock.servicios;

import com.sem.controlstock.entidades.Producto;
import com.sem.controlstock.entidades.Proveedor;
import com.sem.controlstock.excepciones.MiException;
import com.sem.controlstock.repositorios.ProductoRepositorio;
import com.sem.controlstock.repositorios.ProveedorRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoServicio {
    
    //instancia de productoRepositorio
    @Autowired
    private ProductoRepositorio productoRepositorio;
    @Autowired
    private ProveedorRepositorio proveedorRepositorio;
    
    @Transactional
    public void crearProducto(String id, String nombre, Float cantidad, String descripcion, Float precio, String idProveedor) throws MiException{
        
        
        validar(id, nombre, cantidad, descripcion, precio, idProveedor);
        
        Proveedor proveedor = proveedorRepositorio.findById(idProveedor).get();
        Producto producto = new Producto();
        
        producto.setId(id);
        producto.setNombre(nombre);
        producto.setCantidad(cantidad);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        
        producto.setAlta(new Date());
        
        producto.setProveedor(proveedor);
        
        
        
        productoRepositorio.save(producto); 
    }
    
    public List<Producto> listarProductos(){
        
        List<Producto> productos = new ArrayList();
        
        productos = productoRepositorio.findAll(); //rellena en el arraylist con todos los podructos en productoRepositorio
        
        return productos;
    }
    
    public void modificarLibro(String id, String nombre, Float cantidad, String descripcion, Float precio, String idProveedor) throws MiException{
        
        validar(id, nombre, cantidad, descripcion, precio, idProveedor);
        
        Optional<Producto> respuesta = productoRepositorio.findById(id);
        Optional<Proveedor> respuestaProveedor = proveedorRepositorio.findById(idProveedor);
        
        Proveedor proveedor = new Proveedor();
        
        if (respuestaProveedor.isPresent()) {
            proveedor = respuestaProveedor.get();
        }
        
        if (respuesta.isPresent()) {
            
            Producto producto = respuesta.get();
            
            producto.setNombre(nombre);
            producto.setCantidad(cantidad);
            producto.setDescripcion(descripcion);
            producto.setPrecio(precio);
            
            producto.setProveedor(proveedor);
            
            productoRepositorio.save(producto);
            
        }
    }
    
    private void validar(String id, String nombre, Float cantidad, String descripcion, Float precio, String idProveedor) throws MiException{
        
        if (id == null) {
            throw new MiException("El id no puede ser nulo");
        }
        
        if (nombre == null || nombre.isEmpty()) {
            throw new MiException("El nombre no puede ser nulo ni estar vacio");
        }
        
        if (cantidad == null) {
            throw new MiException("La cantidad del producto no puede ser nula");
        }
          
        if (precio == null) {
            throw new MiException("El precio del producto no puede ser nulo");
        }
        
        if (idProveedor == null || idProveedor.isEmpty()) {
            throw new MiException("El proveedor no puede estár nulo ni estar vacio");
        }   
    }
}
