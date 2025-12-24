package com.prueba.PruebaTecSupermercado.repository;

import com.prueba.PruebaTecSupermercado.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto,Long> {

    //BUSCAR PRODUCTO POR NOMBRE
    Optional<Producto> findByNombre(String nombre);
}
