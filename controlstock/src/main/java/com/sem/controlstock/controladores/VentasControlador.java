package com.sem.controlstock.controladores;

import com.sem.controlstock.repositorios.VentaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ventas")
public class VentasControlador {
    @Autowired
    VentaRepositorio ventaRepositorio;
    
    @GetMapping("/lista")
    public String mostrarVentas(ModelMap modelo){
        modelo.addAttribute("ventas", ventaRepositorio.findAll());
        return "ventas_list.html";
    }
}
