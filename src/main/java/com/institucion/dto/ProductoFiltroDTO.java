package com.institucion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoFiltroDTO {

    private String nombre;
    private String clave;
    private BigDecimal precioMin;
    private BigDecimal precioMax;
}