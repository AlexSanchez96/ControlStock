package com.sem.controlstock.controladores;

import com.sem.controlstock.entidades.Cliente;
import com.sem.controlstock.excepciones.MiException;
import com.sem.controlstock.servicios.ClienteServicio;
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
@RequestMapping("/cliente")
public class ClienteControlador {
    
    @Autowired
    private ClienteServicio clienteServicio;
    
    @GetMapping("/registrar")
    public String registrar(){
        return "cliente_form.html"; 
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String email, @RequestParam String direccion,
            @RequestParam String lugar, @RequestParam String telefono,  ModelMap modelo, RedirectAttributes redirectAttrs){
        
        try {
            clienteServicio.crearCliente(nombre, email, direccion, lugar, telefono);
            redirectAttrs
            .addFlashAttribute("mensaje", "Cliente cargado correctamente")
            .addFlashAttribute("clase", "success");
            
        } catch (MiException ex) {
            
            modelo.put("error", ex.getMessage());
            return "cliente_form.html";
        }
        return "redirect:/cliente/lista";
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        
        List<Cliente> clientes = clienteServicio.listarClientes();
        
        modelo.addAttribute("clientes", clientes);
        
        return "cliente_list.html";    
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        
        modelo.put("cliente", clienteServicio.getOne(id));
        
        return "cliente_modificar.html";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, String email,
            String direccion, String lugar, String telefono, ModelMap modelo, RedirectAttributes redirectAttrs){
        
        try {
            clienteServicio.modificarCliente(id, nombre, email, direccion, lugar, telefono);
            redirectAttrs
            .addFlashAttribute("mensaje", "Cliente modificado correctamente")
            .addFlashAttribute("clase", "success");

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "cliente_modificar.html";
        }
        
        return "redirect:/cliente/lista";
    }
}
