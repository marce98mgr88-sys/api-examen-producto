package com.institucion.repository;

import com.institucion.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query("SELECT p FROM Producto p WHERE "
            + "(:nombre IS NULL OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) AND "
            + "(:clave IS NULL OR LOWER(p.clave) LIKE LOWER(CONCAT('%', :clave, '%'))) AND "
            + "(:precioMin IS NULL OR p.precio >= :precioMin) AND "
            + "(:precioMax IS NULL OR p.precio <= :precioMax)")
    Page<Producto> filtrar(
            @Param("nombre") String nombre,
            @Param("clave") String clave,
            @Param("precioMin") BigDecimal precioMin,
            @Param("precioMax") BigDecimal precioMax,
            Pageable pageable);
}