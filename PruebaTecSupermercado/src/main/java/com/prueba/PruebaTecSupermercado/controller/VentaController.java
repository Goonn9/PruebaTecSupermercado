package com.prueba.PruebaTecSupermercado.controller;

import com.prueba.PruebaTecSupermercado.dto.VentaDTO;
import com.prueba.PruebaTecSupermercado.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<VentaDTO>> getVentas(){

        return ResponseEntity.ok(ventaService.traerVentas());

    }

    @PostMapping
    public ResponseEntity<VentaDTO> postVenta(@RequestBody VentaDTO ventaDTO){

        VentaDTO ventaCreada = ventaService.crearVenta(ventaDTO);

        return ResponseEntity.created(URI.create("/api/ventas/" + ventaCreada.getId())).body(ventaCreada);

    }

    @PutMapping("/{id}")
    public VentaDTO putVenta(@PathVariable Long id, @RequestBody VentaDTO ventaDTO){

        //Actualiza fecha, estado, idSucursal, total y reemplaza el detalle

        return ventaService.actualizaVenta(id, ventaDTO);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenta(@PathVariable Long id){

        ventaService.eliminarVenta(id);

        return ResponseEntity.noContent().build();

    }

}
