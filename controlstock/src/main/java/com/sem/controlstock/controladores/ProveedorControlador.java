package com.sem.controlstock.controladores;

import com.sem.controlstock.entidades.Proveedor;
import com.sem.controlstock.excepciones.MiException;
import com.sem.controlstock.servicios.ProveedorServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/proveedor")
public class ProveedorControlador {
    
    @Autowired
    private ProveedorServicio proveedorServicio;
    
    @GetMapping("/registrar")
    public String registrar(){
        return "proveedor_form.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String email, @RequestParam String lugar,
            @RequestParam String telefono, ModelMap modelo){
        
        try {
            proveedorServicio.crearProveedor(nombre, email, lugar, telefono);
            modelo.put("exito", "El proveedor fue cargado correctamente");
        } catch (MiException ex) {
            
            modelo.put("error", ex.getMessage());
            return "proveedor_form.html";
        }
        
        return "index.html";
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        
        List<Proveedor> proveedores = proveedorServicio.listarProveedores();
        
        modelo.addAttribute("proveedores", proveedores);
        
        return "proveedor_list.html";
        
    }
    
    
}
