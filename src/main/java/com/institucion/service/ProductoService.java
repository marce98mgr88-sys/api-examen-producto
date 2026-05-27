package com.institucion.service;

import com.institucion.dto.ProductoFiltroDTO;
import com.institucion.dto.ProductoRequestDTO;
import com.institucion.dto.ProductoResponseDTO;
import com.institucion.entity.Producto;
import com.institucion.exception.ResourceNotFoundException;
import com.institucion.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    /*
     * CONSULTAR CON FILTROS Y PAGINACION
     */
    public Page<ProductoResponseDTO> filtrar(ProductoFiltroDTO filtro, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Producto> productos = productoRepository.filtrar(
                filtro.getNombre(),
                filtro.getClave(),
                filtro.getPrecioMin(),
                filtro.getPrecioMax(),
                pageable);
        return productos.map(this::toResponseDTO);
    }

    /*
     * CREAR PRODUCTO
     */
    public ProductoResponseDTO crear(ProductoRequestDTO request, String usuarioAuditor) {
        Producto producto = new Producto();
        producto.setFolio(generarFolio());
        producto.setClave(request.getClave());
        producto.setNombre(request.getNombre());
        producto.setPrecio(request.getPrecio());
        producto.setActivo(request.getActivo() != null ? request.getActivo() : true);
        producto.setUsuarioAuditor(usuarioAuditor);
        return toResponseDTO(productoRepository.save(producto));
    }

    /*
     * ACTUALIZAR PRODUCTO
     */
    public ProductoResponseDTO actualizar(Long id, ProductoRequestDTO request, String usuarioAuditor) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
        producto.setClave(request.getClave());
        producto.setNombre(request.getNombre());
        producto.setPrecio(request.getPrecio());
        producto.setActivo(request.getActivo() != null ? request.getActivo() : producto.getActivo());
        producto.setUsuarioAuditor(usuarioAuditor);
        return toResponseDTO(productoRepository.save(producto));
    }

    /*
     * GENERAR FOLIO AUTOMATICO
     */
    private String generarFolio() {
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
        return "PROD-" + fecha;
    }

    /*
     * CONVERTIR ENTIDAD A RESPONSE DTO
     */
    private ProductoResponseDTO toResponseDTO(Producto producto) {
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId(producto.getId());
        dto.setFolio(producto.getFolio());
        dto.setClave(producto.getClave());
        dto.setNombre(producto.getNombre());
        dto.setPrecio(producto.getPrecio());
        dto.setActivo(producto.getActivo());
        dto.setFechaRegistro(producto.getFechaRegistro());
        dto.setUsuarioAuditor(producto.getUsuarioAuditor());
        return dto;
    }
}