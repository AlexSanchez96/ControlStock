package com.sem.controlstock.entidades;

import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Venta {
    //ATRIBUTOS 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Temporal(TemporalType.DATE)
    private Date alta;
    
    @ManyToOne
    private Cliente cliente;
    
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private Set<ProductoVendido> productos;
    
    //CONSTRUCTOR

    public Venta(Cliente cliente) {
        this.cliente = cliente;
        this.alta = new Date();
    }
    
    
    //GETTERS
    
    public Integer getId() {
        return id;
    }

    public Date getAlta() {
        return alta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Set<ProductoVendido> getProductos() {
        return productos;
    }
    
    //SETTERS
    
    public void setId(Integer id) {
        this.id = id;
    }

    public void setAlta(Date alta) {
        this.alta = alta;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setProductos(Set<ProductoVendido> productos) {
        this.productos = productos;
    }
    
    
    //METODOS
    
    public Float getTotal(){
        Float total = 0f;
        for (ProductoVendido productoVendido : this.productos) {
            total += productoVendido.getTotal();
        }
        return total;
    }
    
}
