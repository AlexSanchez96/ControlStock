package com.sem.controlstock.controladores;

import com.sem.controlstock.entidades.Usuario;
import com.sem.controlstock.servicios.UsuarioServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PortalControlador {
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    
    @GetMapping("/")
    public String login(@RequestParam(required = false) String error, ModelMap modelo){
        if (error != null) {
            modelo.put("error", "Usuario o Contraseña invalidas");
        }
        return "login.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String index(ModelMap modelo, HttpSession session){
        Usuario logeado = (Usuario) session.getAttribute("usuariosession");
        
        if (logeado.getRol().toString().equals("ADMIN")) {
            List <Usuario> usuarios = usuarioServicio.listarUsuarios();
            modelo.addAttribute("usuarios", usuarios);
            return "panelusuario.html";
        }
        
        
        return "index.html"; 
    }
    
}
