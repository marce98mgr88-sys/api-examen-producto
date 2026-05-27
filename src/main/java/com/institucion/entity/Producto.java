package com.institucion.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "producto")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Producto {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "folio")
	private String folio;

	@Column(name = "clave")
	private String clave;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "precio")
	private BigDecimal precio;

	@Column(name = "activo")
	private Boolean activo;

	@Column(name = "fecha_registro")
	private LocalDateTime fechaRegistro;

	@Column(name = "usuario_auditor")
	private String usuarioAuditor;

	@PrePersist
	public void prePersist() {
		this.fechaRegistro = LocalDateTime.now();
		if (this.activo == null) {
			this.activo = true;
		}
	}
}