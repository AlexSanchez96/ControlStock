package com.sem.controlstock.controladores;

import com.sem.controlstock.entidades.Cliente;
import com.sem.controlstock.excepciones.MiException;
import com.sem.controlstock.servicios.ClienteServicio;
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
            @RequestParam String lugar, @RequestParam String telefono,  ModelMap modelo){
        
        try {
            clienteServicio.crearCliente(nombre, email, direccion, lugar, telefono);
            modelo.put("mensaje", "El cliente fue cargado correctamente");
            modelo.put("clase", "success");
            
        } catch (MiException ex) {
            
            modelo.put("error", ex.getMessage());
            return "cliente_form.html";
        }
        return "index.html";
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
            String direccion, String lugar, String telefono, ModelMap modelo){
        
        try {
            clienteServicio.modificarCliente(id, nombre, email, direccion, lugar, telefono);
            modelo.put("exito", "El cliente fue modificado correctamente");
            return "redirect:../lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "cliente_modificar.html";
        }
    }
}
