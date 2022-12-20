package com.sem.controlstock.repositorios;

import com.sem.controlstock.entidades.ProductoVendido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoVendidoRepositorio extends JpaRepository<ProductoVendido, Integer>{
    
}
