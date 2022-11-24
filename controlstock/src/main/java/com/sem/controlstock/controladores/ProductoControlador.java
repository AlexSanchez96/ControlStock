package com.sem.controlstock.controladores;

import com.sem.controlstock.entidades.Proveedor;
import com.sem.controlstock.excepciones.MiException;
import com.sem.controlstock.servicios.ProductoServicio;
import com.sem.controlstock.servicios.ProveedorServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
    public String registrar(ModelMap modelo){
        List<Proveedor> proveedores = proveedorServicio.listarProveedores();
        
        modelo.addAttribute("proveedores", proveedores);
        
        return "producto_form.html";
        
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String id, @RequestParam String nombre,
            @RequestParam(required = false) Float cantidad, @RequestParam String descripcion,
            @RequestParam(required = false) Float precio, @RequestParam String idProveedor,
            ModelMap modelo){
        
        try {
            productoServicio.crearProducto(id, nombre, cantidad, descripcion, precio, idProveedor);
            
            modelo.put("exito", "el producto fue cargado correctamente");
        } catch (MiException ex) {
            
            List<Proveedor> proveedores = proveedorServicio.listarProveedores();
            modelo.addAttribute("proveedores", proveedores);
            
            modelo.put("error", ex.getMessage());
            return "producto_form.html";
        }
        return "index.html";
    
    }
    
}
