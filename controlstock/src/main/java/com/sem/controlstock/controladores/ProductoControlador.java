package com.sem.controlstock.controladores;

import com.sem.controlstock.entidades.Producto;
import com.sem.controlstock.entidades.Proveedor;
import com.sem.controlstock.excepciones.MiException;
import com.sem.controlstock.servicios.ProductoServicio;
import com.sem.controlstock.servicios.ProveedorServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        
        return "producto_form_1";
        
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre,
            @RequestParam(required = false) Float cantidad, @RequestParam String descripcion,
            @RequestParam(required = false) Float precio, @RequestParam String idProveedor,
            ModelMap modelo){
        
        try {
            productoServicio.crearProducto(nombre, cantidad, descripcion, precio, idProveedor);
            
            modelo.put("exito", "el producto fue cargado correctamente");
        } catch (MiException ex) {
            
            List<Proveedor> proveedores = proveedorServicio.listarProveedores();
            modelo.addAttribute("proveedores", proveedores);
            
            modelo.put("error", ex.getMessage());
            return "producto_form_1";
        }
        return "index.html";
    
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        
        List<Producto> productos = productoServicio.listarProductos();
        
        modelo.addAttribute("productos", productos);
        
        return "productos_list.html";
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        List<Proveedor> proveedores = proveedorServicio.listarProveedores();
        
        modelo.addAttribute("proveedores", proveedores);
        
        modelo.put("producto", productoServicio.getOne(id));
        
        return "producto_modificar.html";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, Float cantidad,
            String descripcion, Float precio, String idProveedor, ModelMap modelo){
        
        try {
            List<Proveedor> proveedores = proveedorServicio.listarProveedores();
            modelo.addAttribute("proveedores", proveedores);
            
            productoServicio.modificarProducto(id, nombre, cantidad, descripcion, precio, idProveedor);
            modelo.put("exito", "El producto fue cargado correctamente");
            return "redirect:../lista";
        } catch (MiException ex) {
            List<Proveedor> proveedores = proveedorServicio.listarProveedores();
           
            modelo.put("error", ex.getMessage());
            
            modelo.addAttribute("proveedores", proveedores);
            return "producto_modificar.html";
        }
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo){       
        modelo.put("producto", productoServicio.getOne(id));
        productoServicio.elminarProducto(id);
        modelo.put("mensaje", "El producto fue eliminado correctamente");
        modelo.put("clase", "success");
        
        return "redirect:/producto/lista";
    }
    
    
    
}
