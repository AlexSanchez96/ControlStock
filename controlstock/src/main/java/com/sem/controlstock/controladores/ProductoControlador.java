package com.sem.controlstock.controladores;

import com.sem.controlstock.excepciones.MiException;
import com.sem.controlstock.servicios.ProductoServicio;
import com.sem.controlstock.servicios.ProveedorServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/producto")
public class ProductoControlador {
    
    @Autowired
    private ProveedorServicio proveedorServicio;
    
    @Autowired
    private ProductoServicio productoServicio;
    
    @GetMapping("/registrar")
    public String registrar(){
        
        return "producto_form.html";
        
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String id, @RequestParam String nombre,
            @RequestParam(required = false) Float cantidad, @RequestParam String descripcion,
            @RequestParam Float precio, @RequestParam String idProveedor){
        
        try {
            productoServicio.crearProducto(id, nombre, cantidad, descripcion, precio, idProveedor);
        } catch (MiException ex) {
            Logger.getLogger(ProductoControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "producto_form.html";
        }
        return "index.html";
    
    }
    
}
