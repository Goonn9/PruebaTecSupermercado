package com.prueba.PruebaTecSupermercado.controller;

import com.prueba.PruebaTecSupermercado.dto.SucursalDTO;
import com.prueba.PruebaTecSupermercado.service.SucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @GetMapping
    public ResponseEntity<List<SucursalDTO>> getSucursales(){

        return ResponseEntity.ok(sucursalService.traerSucursales());

    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<SucursalDTO> getSucursalByNombre(@RequestParam String nombre){

        return ResponseEntity.ok(sucursalService.traerSucursalPorNombre(nombre));

    }

    @PostMapping
    public ResponseEntity<SucursalDTO> postSucursal(@RequestBody SucursalDTO sucursalDTO){

        SucursalDTO sucursalCreated = sucursalService.crearSucursal(sucursalDTO);

        return ResponseEntity.created(URI.create("/api/sucursales" + sucursalCreated.getId())).body(sucursalCreated);

    }

    @PutMapping("/{id}")
    public ResponseEntity<SucursalDTO> putSucursal(@PathVariable Long id, @RequestBody SucursalDTO sucursalDTO){

        return ResponseEntity.ok(sucursalService.actualizaSucursal(id, sucursalDTO));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SucursalDTO> deleteSucursal(@PathVariable Long id){

        sucursalService.eliminarSucursal(id);

        return ResponseEntity.noContent().build();

    }





}
