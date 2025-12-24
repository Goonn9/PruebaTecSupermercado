package com.prueba.PruebaTecSupermercado.controller;

import com.prueba.PruebaTecSupermercado.dto.ProductoDTO;
import com.prueba.PruebaTecSupermercado.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> getProductos(){

        return ResponseEntity.ok(productoService.traerProductos());

    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<ProductoDTO> getProductosByNombre(@PathVariable String nombre){

        return ResponseEntity.ok(productoService.traerProductoPorNombre(nombre));

    }

    @PostMapping
    public ResponseEntity<ProductoDTO> postProducto(@RequestBody ProductoDTO productoDTO){
       ProductoDTO creado =  productoService.crearProducto(productoDTO);

       return ResponseEntity.created(URI.create("/api/productos" + creado.getId())).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> putProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDTO){

        return ResponseEntity.ok(productoService.actualizaProducto(id, productoDTO));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id){

        productoService.eliminarProducto(id);

        return ResponseEntity.noContent().build();

    }

}
