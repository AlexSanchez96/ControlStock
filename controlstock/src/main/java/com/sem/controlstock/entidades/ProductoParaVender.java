package com.sem.controlstock.entidades;


public class ProductoParaVender extends Producto{
    //ATRIBUTOS
    private Float cantidadVendida;
    
    //CONSTRUCTORES

    public ProductoParaVender(Float cantidadVendida, String id, String nombre, Float cantidad, String descripcion, Float precio, Proveedor proveedor) {
        super(id, nombre, cantidad, descripcion, precio, proveedor);
        this.cantidadVendida = cantidadVendida;
    }
    
    public ProductoParaVender(Float cantidadVendida, String nombre, Float cantidad, String descripcion, Float precio, Proveedor proveedor) {
        super(nombre, cantidad, descripcion, precio, proveedor);
        this.cantidadVendida = cantidadVendida;
    }
    
    //GETTER

    public Float getCantidadVendida() {
        return cantidadVendida;
    }

    //METODOS
    public void aumentarCantidadVendida(){
        this.cantidadVendida++;
    }
    
    public Float getTotal(){
        return this.getPrecio() * this.getCantidadVendida();
    }
    
}
