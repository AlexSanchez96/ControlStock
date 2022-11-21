package com.sem.controlstock.repositorios;

import com.sem.controlstock.entidades.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProveedorRepositorio extends JpaRepository<Proveedor, String>{
    
}
