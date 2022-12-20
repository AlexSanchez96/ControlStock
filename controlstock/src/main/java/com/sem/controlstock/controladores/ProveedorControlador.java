package com.sem.controlstock.controladores;

import com.sem.controlstock.entidades.Proveedor;
import com.sem.controlstock.excepciones.MiException;
import com.sem.controlstock.servicios.ProveedorServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
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
            @RequestParam String telefono, ModelMap modelo, RedirectAttributes redirectAttrs){
        
        try {
            proveedorServicio.crearProveedor(nombre, email, lugar, telefono);
            redirectAttrs
            .addFlashAttribute("mensaje", "Proveedor cargado correctamente")
            .addFlashAttribute("clase", "success");
        } catch (MiException ex) {
            
            modelo.put("error", ex.getMessage());
            return "proveedor_form.html";
        }
        
        return "redirect:/proveedor/lista";
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        
        List<Proveedor> proveedores = proveedorServicio.listarProveedores();
        
        modelo.addAttribute("proveedores", proveedores);
        
        return "proveedor_list.html";    
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        
        modelo.put("proveedor", proveedorServicio.getOne(id));
        
        return "proveedor_modificar.html";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, String email,
            String lugar, String telefono, ModelMap modelo, RedirectAttributes redirectAttrs){
        
        try {
            proveedorServicio.modificarProveedor(id, nombre, email, telefono, lugar);
            redirectAttrs
            .addFlashAttribute("mensaje", "Proveedor modificado correctamente")
            .addFlashAttribute("clase", "success");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "proveedor_modificar.html";
        }
        return "redirect:/proveedor/lista";
    }
    
}
