package com.sem.controlstock.controladores;

import com.sem.controlstock.entidades.Usuario;
import com.sem.controlstock.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminControlador {
    @Autowired
    private UsuarioServicio usuarioServicio;
       
    @GetMapping("/panelusuario")
    public String panelUsuario(ModelMap modelo){
        List <Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);
        return "panelusuario.html";
    }
}
