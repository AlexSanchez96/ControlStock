package com.sem.controlstock.servicios;

import com.sem.controlstock.entidades.Usuario;
import com.sem.controlstock.enumeraciones.Rol;
import com.sem.controlstock.excepciones.MiException;
import com.sem.controlstock.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UsuarioServicio implements UserDetailsService{
    @Autowired
    UsuarioRepositorio usuarioRepositorio;
    
    @Transactional
    public void crearUsuario(String nombre, String password, String password2) throws MiException{
        validar(nombre, password, password2);
        Usuario usuario = new Usuario();
        
        usuario.setNombre(nombre);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setRol(Rol.USER);
        
        usuarioRepositorio.save(usuario);
    }
    
    public List<Usuario> listarUsuarios(){
        List<Usuario> usuarios = new ArrayList();
        
        usuarios = usuarioRepositorio.findAll();
        
        return usuarios;
        
    }
    
    public void modificarUsuario(String id, String nombre, String password, String password2) throws MiException{
        validar(nombre, password, password2);
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            
            usuario.setNombre(nombre);
            usuario.setPassword(password);
            
            usuarioRepositorio.save(usuario);
        }
    }
    
    private void validar(String nombre, String password, String password2) throws MiException{
        if (nombre == null || nombre.isEmpty()) {
            throw new MiException("El Nombre no puede ser nulo");
        }
        if (password == null || password.isEmpty() || password.length() <= 5) {
            throw new MiException("La constraseña no puede estar vacia y debe tener más de 5 dígitos");
        }
        
        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String nombre) throws UsernameNotFoundException {
        //AUTENTICAR UN USUARIO
        //Busca un usuario de nuestro dominio y lo transforma en un usuario del dominio de SpringSecurity 
        Usuario usuario = usuarioRepositorio.buscarPorNombre(nombre);
        if (usuario != null) {
            
            List<GrantedAuthority> permisos = new ArrayList(); //hacer lista de permisos
            
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+ usuario.getRol().toString()); //agregar el permiso del usuario a la variable p
            
            permisos.add(p); //agregar la variable p a la lista
            
            //lamada para atrapar el usuario y guardarlo en una session web
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            
            //una vez atrapada la solicitud http, lo guardamos en un objeto de la interfaz httpsession
            HttpSession session = attr.getRequest().getSession(true);
            
            //setiamos los atributos de la session que querramos pasar
            session.setAttribute("usuariosession", usuario);
            
            return new User(usuario.getNombre(), usuario.getPassword(), permisos);
        }else{    
            return null;
        }
    }
}
