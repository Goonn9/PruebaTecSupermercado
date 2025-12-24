package com.prueba.PruebaTecSupermercado.repository;

import com.prueba.PruebaTecSupermercado.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SucursalRepository extends JpaRepository <Sucursal, Long> {

    //BUSCAR SUCURSAL POR NOMBRE
    Optional<Sucursal> findByNombre(String nombre);

}
