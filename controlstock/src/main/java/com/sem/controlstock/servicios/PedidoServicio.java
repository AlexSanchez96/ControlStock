package com.sem.controlstock.servicios;

import com.sem.controlstock.entidades.Cliente;
import com.sem.controlstock.entidades.Pedido;
import com.sem.controlstock.excepciones.MiException;
import com.sem.controlstock.repositorios.ClienteRepositorio;
import com.sem.controlstock.repositorios.PedidoRepositorio;
import com.sem.controlstock.repositorios.ProductoRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoServicio {
    
    @Autowired
    private PedidoRepositorio pedidoRepositorio;
    @Autowired
    private ClienteRepositorio clienteRepositorio;
    
    
    @Transactional
    public void crearPedido(Float descuento, Float subtotal, Float iva, Float total, String idCliente) throws MiException{
        
        validar(total, idCliente);
        
        Cliente cliente = clienteRepositorio.findById(idCliente).get();
        Pedido pedido = new Pedido();
        
        pedido.setDescuento(descuento);
        pedido.setSubtotal(subtotal);
        pedido.setIva(iva);
        pedido.setTotal(total);
        
        pedido.setAlta(new Date());
        
        pedido.setCliente(cliente);
        
        pedidoRepositorio.save(pedido);
    }
    
    public List<Pedido> listarPedidos(){
        List<Pedido> pedidos = new ArrayList();
        
        pedidos = pedidoRepositorio.findAll();
        
        return pedidos;
    }
    
    @Transactional
    public void modificarLibro(String id, Float descuento, Float subtotal, Float iva, Float total, String idCliente) throws MiException{
        
        validar(total, idCliente);
        
        Optional<Pedido> respuesta = pedidoRepositorio.findById(id);
        Optional<Cliente> respuestaCliente = clienteRepositorio.findById(idCliente);
        
        Cliente cliente = new Cliente();
        
        if (respuestaCliente.isPresent()) {
            cliente = respuestaCliente.get();
        }
        
        if(respuesta.isPresent()){
            Pedido pedido = respuesta.get();
            
            pedido.setDescuento(descuento);
            pedido.setSubtotal(subtotal);
            pedido.setIva(iva);
            pedido.setTotal(total);
            pedido.setCliente(cliente);
            
            pedidoRepositorio.save(pedido);
        }
    }
    
    public void validar(Float total, String idCliente) throws MiException{
        
        if(total == null){
            throw new MiException("El total no puede ser nulo");
        }
        
        if(idCliente == null || idCliente.isEmpty()){
            throw new MiException("El cliente no puede ser nulo");
        }
    }
}
