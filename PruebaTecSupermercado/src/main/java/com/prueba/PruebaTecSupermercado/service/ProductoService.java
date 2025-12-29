package com.prueba.PruebaTecSupermercado.service;

import com.prueba.PruebaTecSupermercado.dto.ProductoDTO;
import com.prueba.PruebaTecSupermercado.exception.NotFoundException;
import com.prueba.PruebaTecSupermercado.mapper.Mapper;
import com.prueba.PruebaTecSupermercado.model.Producto;
import com.prueba.PruebaTecSupermercado.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService implements IProductoService{

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<ProductoDTO> traerProductos() {
        return productoRepository.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public ProductoDTO traerProductoPorNombre(String nombre) {

        Producto producto = productoRepository.findByNombre(nombre)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con nombre : " + nombre));

        return Mapper.toDTO(producto);
    }

    @Override
    public ProductoDTO crearProducto(ProductoDTO productoDTO) {
        Producto prod = Producto.builder()
                .nombre(productoDTO.getNombre())
                .categoria(productoDTO.getCategoria())
                .precio(productoDTO.getPrecio())
                .cantidad(productoDTO.getCantidad())
                .build();
        return Mapper.toDTO(productoRepository.save(prod));
    }

    @Override
    public ProductoDTO actualizaProducto(Long id, ProductoDTO productoDTO) {

        //buscamos si existe el producto en la bbdd
        Producto p = productoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));

        p.setNombre(productoDTO.getNombre());
        p.setCategoria(productoDTO.getCategoria());
        p.setCantidad(productoDTO.getCantidad());
        p.setPrecio(productoDTO.getPrecio());
        return Mapper.toDTO(productoRepository.save(p  ));
    }

    @Override
    public void eliminarProducto(Long id) {

        //buscamos si existe el producto en la bbdd
        if(!productoRepository.existsById(id)){
            throw new NotFoundException("Producto no encontrado");
        }
        productoRepository.deleteById(id);

    }

}
