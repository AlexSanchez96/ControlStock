package com.sem.controlstock.repositorios;

import com.sem.controlstock.entidades.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, String>{
    //METODOS INTRINSECOS
    @Query("SELECT p FROM Producto p WHERE p.nombre = :nombre")
    public Producto buscarPorNombre(@Param("nombre") String nombre);
    
    @Query("SELECT p FROM Producto p WHERE p.proveedor.nombre = :nombre")
    public List<Producto> buscarPorProveedor(@Param ("nombre") String nombre);
    
    Producto findFirstByNombre(String nombre);
}
