package com.sem.controlstock.entidades;


import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;


@Entity
public class Producto {
    //ATRIBUTOS
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
    private Float cantidad;
    private String descripcion;
    private Float precio;
    
    @Temporal(TemporalType.DATE)
    private Date alta;
    
    @ManyToOne
    private Proveedor proveedor;
    
    
    //CONSTRUCTORES
    public Producto() {
    }

    public Producto(String id, String nombre, Float cantidad, String descripcion, Float precio, Proveedor proveedor) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.precio = precio;
        this.proveedor = proveedor;
    }

    public Producto(String nombre, Float cantidad, String descripcion, Float precio, Proveedor proveedor) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.precio = precio;
        this.proveedor = proveedor;
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

    //METODOS
    
    public boolean sinExistencia(){
        
        return this.cantidad <= 0;
    }
    
    public void restarExistencia(Float cantidad){
        this.cantidad -= cantidad;
    }
 
    
}
