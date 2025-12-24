package com.prueba.PruebaTecSupermercado.service;

import com.prueba.PruebaTecSupermercado.dto.DetalleVentaDTO;
import com.prueba.PruebaTecSupermercado.dto.VentaDTO;
import com.prueba.PruebaTecSupermercado.exception.NotFoundException;
import com.prueba.PruebaTecSupermercado.mapper.Mapper;
import com.prueba.PruebaTecSupermercado.model.DetalleVenta;
import com.prueba.PruebaTecSupermercado.model.Producto;
import com.prueba.PruebaTecSupermercado.model.Sucursal;
import com.prueba.PruebaTecSupermercado.model.Venta;
import com.prueba.PruebaTecSupermercado.repository.ProductoRepository;
import com.prueba.PruebaTecSupermercado.repository.SucursalRepository;
import com.prueba.PruebaTecSupermercado.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VentaService implements IVentaService{

    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private SucursalRepository sucursalRepository;

    @Override
    public List<VentaDTO> traerVentas() {

        List<Venta> ventas = ventaRepository.findAll();
        List<VentaDTO> ventasDTO = new ArrayList<>();

        VentaDTO ventaDTO;
        for(Venta venta : ventas){
            ventaDTO = Mapper.toDTO(venta);
            ventasDTO.add(ventaDTO);
        }

        return ventasDTO;

    }

    @Override
    public VentaDTO crearVenta(VentaDTO ventaDto) {

        //VALIDACIONES
        if(ventaDto == null) throw new RuntimeException("VentaDTO es null");
        if(ventaDto.getIdSucursal() == null) throw new RuntimeException("Debe indicar la sucursal");
        if(ventaDto.getDetalle() == null || ventaDto.getDetalle().isEmpty()) throw new RuntimeException("Debe incluir al menos un producto");

        //BUSCAR LA SUCURSAL

        Sucursal sucursal = sucursalRepository.findById(ventaDto.getIdSucursal()).orElse(null);
        if(sucursal == null){
            throw new RuntimeException("Sucursal no encontrado");
        }

        //CREAR LA VENTA

        Venta venta = new Venta();

        venta.setFecha(ventaDto.getFecha());
        venta.setEstado(ventaDto.getEstado());
        venta.setSucursal(sucursal);
        venta.setTotal(ventaDto.getTotal());

        //LISTA DE DETALLES

        List<DetalleVenta> detalles = new ArrayList<>();
        Double totalCalculado = 0.0;

        for(DetalleVentaDTO detDTO : ventaDto.getDetalle()){
            //BUSCAMOS PRODUCTO POR NOMBRE
            Producto producto = productoRepository.findByNombre(detDTO.getNombreProducto()).orElse(null);

            if(producto == null){
                throw new RuntimeException("Producto no encontrado" + detDTO.getNombreProducto());
            }

            //CREAR DETALLE
            DetalleVenta detalle = new DetalleVenta();
            detalle.setProducto(producto);
            detalle.setPrecio(detDTO.getPrecio());
            detalle.setCantProducto(detDTO.getCantProducto());
            detalle.setVenta(venta);

            detalles.add(detalle);
            totalCalculado = totalCalculado + (detDTO.getPrecio() * detDTO.getCantProducto());
        }

        //SETEAMOS LA LISTA DE DETALLES DE VENTA
        venta.setDetalle(detalles);

        //GUARDAMOS EN LA BBDD
        venta = ventaRepository.save(venta);

        //MAPEO DE SALIDA
        VentaDTO ventaSalida = Mapper.toDTO(venta);

        return ventaSalida;

    }

    @Override
    public VentaDTO actualizaVenta(Long id, VentaDTO ventaDto) {

        //BUSCAR SI EXISTE LA VENTA
        Venta venta = ventaRepository.findById(id).orElse(null);
        if(venta == null){
            throw new RuntimeException("Venta no encontrada");
        }

        if(ventaDto.getFecha() != null){
            venta.setFecha(ventaDto.getFecha());
        }
        if(ventaDto.getEstado() != null){
            venta.setEstado(ventaDto.getEstado());
        }
        if(ventaDto.getTotal() != null){
            venta.setTotal(ventaDto.getTotal());
        }
        if(ventaDto.getIdSucursal() != null){
            Sucursal suc = sucursalRepository.findById(ventaDto.getIdSucursal()).orElse(null);
            if(suc == null){
                throw new NotFoundException("Sucursal no encontrado");
            }
            venta.setSucursal(suc);
        }

        ventaRepository.save(venta);

        VentaDTO ventaSalida = Mapper.toDTO(venta);

        return ventaSalida;

    }

    @Override
    public void eliminarVenta(Long id) {

        Venta venta = ventaRepository.findById(id).orElse(null);
        if(venta == null){
            throw new RuntimeException("Venta no encontrada");
        }
        ventaRepository.delete(venta);

    }
}
