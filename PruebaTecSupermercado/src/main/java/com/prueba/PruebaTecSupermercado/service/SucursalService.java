package com.prueba.PruebaTecSupermercado.service;

import com.prueba.PruebaTecSupermercado.dto.SucursalDTO;
import com.prueba.PruebaTecSupermercado.exception.NotFoundException;
import com.prueba.PruebaTecSupermercado.mapper.Mapper;
import com.prueba.PruebaTecSupermercado.model.Sucursal;
import com.prueba.PruebaTecSupermercado.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SucursalService implements ISucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;

    @Override
    public List<SucursalDTO> traerSucursales() {
        return sucursalRepository.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public SucursalDTO traerSucursalPorNombre(String nombre) {

        Sucursal sucursal = sucursalRepository.findByNombre(nombre)
                .orElseThrow(() -> new NotFoundException("Sucursal no encontrada con nombre : " + nombre));

        return Mapper.toDTO(sucursal);

    }

    @Override
    public SucursalDTO crearSucursal(SucursalDTO sucursalDto) {

        Sucursal sucursal = Sucursal.builder()
                .nombre(sucursalDto.getNombre())
                .direccion(sucursalDto.getDireccion())
                .build();
        return Mapper.toDTO(sucursalRepository.save(sucursal));
    }

    @Override
    public SucursalDTO actualizaSucursal(Long id, SucursalDTO sucursalDto) {

        Sucursal sucursal = sucursalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sucursal no encontrado"));

        sucursal.setNombre(sucursalDto.getNombre());
        sucursal.setDireccion( sucursalDto.getDireccion());

        return Mapper.toDTO(sucursalRepository.save(sucursal));
    }

    @Override
    public void eliminarSucursal(Long id) {

        //buscamos si existe el producto en la bbdd
        if(!sucursalRepository.existsById(id)){
            throw new NotFoundException("Sucursal no encontrado");
        }
        sucursalRepository.deleteById(id);

    }
}
