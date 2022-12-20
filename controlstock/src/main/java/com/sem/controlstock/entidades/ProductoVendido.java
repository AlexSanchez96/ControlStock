package com.sem.controlstock.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class ProductoVendido {
    //ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Float cantidadVendida, precio;
    private String nombre, proveedor;
    
    @ManyToOne
    @JoinColumn
    private Venta venta;
    
    //CONSTRUCTOR

    public ProductoVendido(Float cantidadVendida, Float precio, String nombre, String proveedor, Venta venta) {
        this.cantidadVendida = cantidadVendida;
        this.precio = precio;
        this.nombre = nombre;
        this.proveedor = proveedor;
        this.venta = venta;
    }

    public ProductoVendido() {
    }
    
    //GETTERS
    public Integer getId() {
        return id;
    }

    public Float getCantidadVendida() {
        return cantidadVendida;
    }

    public Float getPrecio() {
        return precio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getProveedor() {
        return proveedor;
    }

    public Venta getVenta() {
        return venta;
    }
    
    //SETTERS
    public void setId(Integer id) {
        this.id = id;
    }

    public void setCantidadVendida(Float cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }
    
    //METODOS
    public Float getTotal(){
        return this.cantidadVendida * this.precio;
    }
}
