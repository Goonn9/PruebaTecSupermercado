package com.prueba.PruebaTecSupermercado.service;

import com.prueba.PruebaTecSupermercado.dto.VentaDTO;

import java.util.List;

public interface IVentaService {

    List<VentaDTO> traerVentas();
    VentaDTO crearVenta(VentaDTO ventaDto);
    VentaDTO actualizaVenta(Long id,  VentaDTO ventaDto);
    void eliminarVenta(Long id);

}
