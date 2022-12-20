package com.sem.controlstock;

import com.sem.controlstock.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadWeb extends WebSecurityConfigurerAdapter{
    
    @Autowired
    public UsuarioServicio usuarioservicio;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        //cuando se registra un usuario, lo autenticamos a traves del metodo userDetailService 
        auth.userDetailsService(usuarioservicio)
                .passwordEncoder(new BCryptPasswordEncoder()); //una vez autenticado le pasamos el encriptador de contraseñas
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                        .antMatchers("/admin/*").hasRole("ADMIN")
                        .antMatchers("/css/*", "/js/*", "/img/*", "/**")
                        .permitAll()
                .and().formLogin()
                        .loginPage("/") //url donde se encuentra el form
                        .loginProcessingUrl("/logincheck") //cual va a ser la url con la cual spring auntentificara un user
                        .usernameParameter("nombre") //credenciales
                        .passwordParameter("password")
                        .defaultSuccessUrl("/inicio") //si todo sale bien devuelve esta vista
                        .permitAll()
                .and().logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                .and().csrf()
                        .disable();
    }
    
}
