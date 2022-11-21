package com.sem.controlstock.entidades;


import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class Producto {
    //ATRIBUTOS
    @Id
    private String id;
    private String nombre;
    private Float cantidad;
    private String descripcion;
    private Float precio;
    
    @Temporal(TemporalType.DATE)
    private Date alta;
    
    @ManyToOne
    private Proveedor proveedor;
    
    //CONSTRUCTOR
    public Producto() {
    }
    
    //GETTES
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Float getCantidad() {
        return cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Float getPrecio() {
        return precio;
    }

    public Date getAlta() {
        return alta;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    

    
    
    
    //SETTERS
    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCantidad(Float cantidad) {
        this.cantidad = cantidad;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public void setAlta(Date alta) {
        this.alta = alta;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    

    
    
    
}
