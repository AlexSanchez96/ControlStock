package com.sem.controlstock.controladores;

import com.sem.controlstock.entidades.Cliente;
import com.sem.controlstock.entidades.Producto;
import com.sem.controlstock.excepciones.MiException;
import com.sem.controlstock.servicios.ClienteServicio;
import com.sem.controlstock.servicios.PedidoServicio;
import com.sem.controlstock.servicios.ProductoServicio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/pedido")
public class PedidoControlador {
    
    @Autowired
    private PedidoServicio pedidoServicio;
    @Autowired
    private ClienteServicio clienteServicio;
    @Autowired
    private ProductoServicio productoServicio;
    
    @GetMapping("/registrar")
    public String registrar(ModelMap modelo){
        
        List<Cliente> clientes = clienteServicio.listarClientes();
        List<Producto> productos = productoServicio.listarProductos();
        
        modelo.addAttribute("clientes", clientes);
        modelo.addAttribute("productos", productos);
        
        return "pedido_form.html";
    }
    
    @PostMapping("/registro")
    public String registro(Float descuento, Float subtotal,
            Float iva, Float total, String idCliente, ModelMap modelo){
        try {
            pedidoServicio.crearPedido(descuento, subtotal, iva, total, idCliente);
            modelo.put("exito", "el producto fue cargado correctamente");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "pedido_form.html";
        }
        
        return "pedido_form.html";
    }
    
    @GetMapping("/registrar_producto")
    public String registrarProducto(ModelMap modelo){
        
        List<Producto> productos = productoServicio.listarProductos();
        
        modelo.addAttribute("productos", productos);
        
        return "agregar_producto";
    }
    
    @PostMapping("/registro_producto")
    public String registroProducto(List<String> idProductos, ModelMap modelo){
        List<String> productos = new ArrayList();
        
        for (String idProducto : idProductos) {
            productos.add(idProducto);
        }
        
        System.out.println(productos);
        
        return "pedido_form.html";
    }
    
}
