package com.prueba.PruebaTecSupermercado.service;

import com.prueba.PruebaTecSupermercado.dto.ProductoDTO;

import java.util.List;

public interface IProductoService {

    List<ProductoDTO> traerProductos();
    ProductoDTO traerProductoPorNombre(String nombre);
    ProductoDTO crearProducto(ProductoDTO productoDTO);
    ProductoDTO actualizaProducto(Long id,  ProductoDTO productoDTO);
    void eliminarProducto(Long id);


}
