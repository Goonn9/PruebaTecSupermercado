package com.prueba.PruebaTecSupermercado.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SucursalDTO {

    private Long id;
    private String nombre;
    private String direccion;
}
