package com.institucion.controller;

import com.institucion.dto.ProductoFiltroDTO;
import com.institucion.dto.ProductoRequestDTO;
import com.institucion.dto.ProductoResponseDTO;
import com.institucion.service.ProductoService;
import com.institucion.service.ReporteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ReporteService reporteService;

    /*
     * CONSULTAR CON FILTROS Y PAGINACION
     */
    @GetMapping("/listar")
    public ResponseEntity<Page<ProductoResponseDTO>> filtrar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String clave,
            @RequestParam(required = false) BigDecimal precioMin,
            @RequestParam(required = false) BigDecimal precioMax,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        ProductoFiltroDTO filtro = new ProductoFiltroDTO(nombre, clave, precioMin, precioMax);
        return ResponseEntity.ok(productoService.filtrar(filtro, page, size));
    }

    /*
     * CREAR PRODUCTO
     */
    @PostMapping("/crear")
    public ResponseEntity<ProductoResponseDTO> crear(
            @Valid @RequestBody ProductoRequestDTO request,
            Authentication authentication) {
        return ResponseEntity.ok(productoService.crear(request, authentication.getName()));
    }

    /*
     * ACTUALIZAR PRODUCTO
     */
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProductoRequestDTO request,
            Authentication authentication) {
        return ResponseEntity.ok(productoService.actualizar(id, request, authentication.getName()));
    }

    /*
     * GENERAR REPORTE EXCEL
     */
    @GetMapping("/reporte")
    public ResponseEntity<Map<String, Object>> reporte(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String clave,
            @RequestParam(required = false) BigDecimal precioMin,
            @RequestParam(required = false) BigDecimal precioMax) throws Exception {
        ProductoFiltroDTO filtro = new ProductoFiltroDTO(nombre, clave, precioMin, precioMax);
        return ResponseEntity.ok(reporteService.generarReporteExcel(filtro));
    }
}