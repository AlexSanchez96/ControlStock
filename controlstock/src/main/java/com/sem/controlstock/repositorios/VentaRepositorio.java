package com.sem.controlstock.repositorios;

import com.sem.controlstock.entidades.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepositorio extends JpaRepository<Venta, Integer>{
    
}
