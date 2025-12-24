package com.prueba.PruebaTecSupermercado.service;

import com.prueba.PruebaTecSupermercado.dto.SucursalDTO;

import java.util.List;

public interface ISucursalService {

    List<SucursalDTO> traerSucursales();
    SucursalDTO traerSucursalPorNombre(String nombre);
    SucursalDTO crearSucursal(SucursalDTO sucursalDto);
    SucursalDTO actualizaSucursal(Long id,  SucursalDTO sucursalDto);
    void eliminarSucursal(Long id);

}
