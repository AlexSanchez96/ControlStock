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
public class Pedido {
    //ATRIBUTOS
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private Float descuento;
    private Float subtotal;
    private Float iva;
    private Float total;
    
    @Temporal(TemporalType.DATE)
    private Date alta;
    
    @ManyToOne
    private Cliente cliente;
    
    //CONSTRUCTOR
    public Pedido() {
    }
    
    //GETTERS
    public String getId() {
        return id;
    }

    public Float getDescuento() {
        return descuento;
    }

    public Float getSubtotal() {
        return subtotal;
    }

    public Float getIva() {
        return iva;
    }

    public Float getTotal() {
        return total;
    }

    public Date getAlta() {
        return alta;
    }

    public Cliente getCliente() {
        return cliente;
    }
    
    //SETTERS

    public void setId(String id) {
        this.id = id;
    }

    public void setDescuento(Float descuento) {
        this.descuento = descuento;
    }

    public void setSubtotal(Float subtotal) {
        this.subtotal = subtotal;
    }

    public void setIva(Float iva) {
        this.iva = iva;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public void setAlta(Date alta) {
        this.alta = alta;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    
    
}
