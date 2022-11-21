package com.sem.controlstock.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Cliente {
    //ATRIBUTOS
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
    private String email;
    private String direccion;
    private String lugar;
    private String telefono;
    //@OneToMany
    //private List<Pedidos> pedidos;
    
    //CONSTRUCTOR
    public Cliente() {
    }
    
    //GETTERS
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getLugar() {
        return lugar;
    }

    public String getTelefono() {
        return telefono;
    }
    
    //SETTERS
    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
}
