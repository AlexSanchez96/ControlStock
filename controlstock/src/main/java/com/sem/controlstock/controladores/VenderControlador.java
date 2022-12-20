package com.sem.controlstock.controladores;

import com.sem.controlstock.entidades.Cliente;
import com.sem.controlstock.entidades.Producto;
import com.sem.controlstock.entidades.ProductoParaVender;
import com.sem.controlstock.entidades.ProductoVendido;
import com.sem.controlstock.entidades.Proveedor;
import com.sem.controlstock.entidades.Venta;
import com.sem.controlstock.repositorios.ClienteRepositorio;
import com.sem.controlstock.repositorios.ProductoRepositorio;
import com.sem.controlstock.repositorios.ProductoVendidoRepositorio;
import com.sem.controlstock.repositorios.ProveedorRepositorio;
import com.sem.controlstock.repositorios.VentaRepositorio;
import com.sem.controlstock.servicios.ClienteServicio;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
@RequestMapping(path = "/vender")
public class VenderControlador {
    
    @Autowired
    private ProductoRepositorio productoRepositorio;
    @Autowired
    private ProveedorRepositorio proveedorRepositorio;
    @Autowired
    private VentaRepositorio ventaRepositorio;
    @Autowired
    private ProductoVendidoRepositorio productoVendidoRepositorio;
    @Autowired
    private ClienteRepositorio clienteRepositorio;
    @Autowired
    private ClienteServicio clienteServicio;
    
    private ArrayList<ProductoParaVender> obtenerCarrito(HttpServletRequest request){
        ArrayList<ProductoParaVender> carrito = (ArrayList<ProductoParaVender>) request.getSession().getAttribute("carrito");
        
        if (carrito == null) {
            
            carrito = new ArrayList<>(); 
        }
        
        return carrito;
    }
    
    private void guardarCarrito(ArrayList<ProductoParaVender> carrito, HttpServletRequest request){
        request.getSession().setAttribute("carrito", carrito);
    }
    
    @GetMapping("/registrar")
    public String registrarVenta(ModelMap modelo, HttpServletRequest request){
        modelo.addAttribute("producto", new Producto());
        float total = 0;
        ArrayList<ProductoParaVender> carrito = this.obtenerCarrito(request);
        for (ProductoParaVender p : carrito) {
            total += p.getTotal();
        }
        
        modelo.addAttribute("total", total);
        
        List<Cliente> clientes = clienteServicio.listarClientes();
        modelo.addAttribute("clientes", clientes);
        return "vender.html";
    }
    
    //AGREGAR UN PRODUCTO AL PEDIDO
    
    @PostMapping("/agregar")
    public String agregarAlCarrito(@ModelAttribute Producto producto, HttpServletRequest request, RedirectAttributes redirectAttrs){
    
        ArrayList<ProductoParaVender> carrito = this.obtenerCarrito(request);
        Producto productoBuscadoPorNombre = productoRepositorio.buscarPorNombre(producto.getNombre());
        
        if (productoBuscadoPorNombre == null) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "El producto con ese nombre no existe")
                    .addFlashAttribute("clase", "warning");
            
            return "redirect:/vender/registrar";
        }
        
        if (productoBuscadoPorNombre.sinExistencia()) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "El producto está agotado")
                    .addFlashAttribute("clase", "warning");
            
            return "redirect:/vender/registrar";
        }
        
        boolean encontrado = false;
        
        for (ProductoParaVender productoParaVenderActual : carrito) {
            if (productoParaVenderActual.getNombre().equals(productoBuscadoPorNombre.getNombre())) {
                productoParaVenderActual.aumentarCantidadVendida();
                encontrado = true;
                break;
            }
        }
        
        if (!encontrado) {
            carrito.add(new ProductoParaVender(1f, productoBuscadoPorNombre.getId(), productoBuscadoPorNombre.getNombre(), productoBuscadoPorNombre.getCantidad(), "Producto Vendido", productoBuscadoPorNombre.getPrecio(), productoBuscadoPorNombre.getProveedor()));
        }
        
        this.guardarCarrito(carrito, request);
        
        return"redirect:/vender/registrar";
    }
    
    //QUITAR UN PRODUCTO AL PEDIDO
    @PostMapping("/quitar/{indice}")
    public String quitarDelCarrito(@PathVariable int indice, HttpServletRequest request){
        ArrayList<ProductoParaVender> carrito = this.obtenerCarrito(request);
        //Si carrito existe y tiene elementos se invoca al método remove
        
        if(carrito != null && carrito.size() > 0 && carrito.get(indice) != null){
            carrito.remove(indice);
            this.guardarCarrito(carrito, request);
        }
        
        return "redirect:/vender/registrar";
    }
    
    //CANCELAR VENTA
    private void limpiarCarrito(HttpServletRequest request){
        //para limpiar la venta guardamos un array vacio
        this.guardarCarrito(new ArrayList<>(), request);
    }
    
    @GetMapping("/limpiar")
    public String cancelarVenta(HttpServletRequest request, RedirectAttributes redirectAttrs){
        this.limpiarCarrito(request);
        redirectAttrs
                    .addFlashAttribute("mensaje", "Venta cancelada")
                    .addFlashAttribute("clase", "info");
        
        return "redirect:/vender/registrar";
    }
    
    //TERMINAR VENTA
    @PostMapping("/terminar")
    public String terminarVenta(HttpServletRequest request, RedirectAttributes redirectAttrs, String idCliente){
        ArrayList<ProductoParaVender> carrito = this.obtenerCarrito(request);
        
        //Si no hay carrito o está vacio, regresamos
        if (carrito == null || carrito.size() <= 0) {
            return "redirect:/vender/registrar";
        }
        
        Cliente cliente = clienteRepositorio.findById(idCliente).get();
        
        Venta venta = ventaRepositorio.save(new Venta(cliente));
        
        //recorrer el carrito
        for (ProductoParaVender productoParaVender : carrito) {
            //obtener el producto desde la bd
            Producto p = productoRepositorio.findById(productoParaVender.getId()).orElse(null);
            if(p == null) continue; 
            //si es nulo lo ingnoramos lo siguiente con continue
            //restamos cantidad existente
            p.restarExistencia(productoParaVender.getCantidadVendida());
            //guardamos en la bd
            productoRepositorio.save(p);
            //creamos un nuevo producto que se guardara con la venta
            ProductoVendido productoVendido = new ProductoVendido(productoParaVender.getCantidadVendida(), productoParaVender.getPrecio(), productoParaVender.getNombre(), productoParaVender.getProveedor().getNombre(), venta);
            //guardamos
            productoVendidoRepositorio.save(productoVendido);
        }
        
        //limpiamos el carrito
        this.limpiarCarrito(request);
        
        //venta exitosa
        redirectAttrs
            .addFlashAttribute("mensaje", "Venta realizada correctamente")
            .addFlashAttribute("clase", "success");
        
        return "redirect:/vender/registrar";
        
    }
    
}
