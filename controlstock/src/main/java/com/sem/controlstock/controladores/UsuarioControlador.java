package com.sem.controlstock.controladores;

import com.sem.controlstock.excepciones.MiException;
import com.sem.controlstock.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    
    @GetMapping("/registrar")
    public String registrar(){
        return "registro.html";
    }
    
    //atravez de un metodo Post va a viajar el parametro nombre por la siguiente url
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String password, String password2, ModelMap modelo){
        //RequestParam indica que es un parametro requerido y que llega cuandos e ejecute el formulario
        try {
            usuarioServicio.crearUsuario(nombre, password, password2); //guardamos el parametro traido por el metodo post al metodo crear usuario para persistirlo en la base de datos
            modelo.put("exito", "El usuario fue subido CORRECTAMENTE");
            return "redirect:../";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "registro.html";
        }
       
    }
    
}
