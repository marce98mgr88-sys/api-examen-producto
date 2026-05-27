package com.institucion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponseDTO {

    private Long id;
    private String folio;
    private String clave;
    private String nombre;
    private BigDecimal precio;
    private Boolean activo;
    private LocalDateTime fechaRegistro;
    private String usuarioAuditor;
}