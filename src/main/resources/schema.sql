-- ============================================================
-- Script de creación de tablas
-- Módulo de Gestión de Productos Institucionales
-- Base de Datos: MariaDB
-- ============================================================

CREATE DATABASE IF NOT EXISTS productos_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE productos_db;

CREATE TABLE IF NOT EXISTS usuario (
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    username    VARCHAR(80)  NOT NULL,
    password    VARCHAR(255) NOT NULL,
    nombre      VARCHAR(150) NOT NULL,
    activo      TINYINT(1)   NOT NULL DEFAULT 1,
    CONSTRAINT pk_usuario      PRIMARY KEY (id),
    CONSTRAINT uq_usuario_name UNIQUE (username)
);

CREATE TABLE IF NOT EXISTS producto (
    id              BIGINT        NOT NULL AUTO_INCREMENT,
    folio           VARCHAR(40)   NOT NULL,
    clave           VARCHAR(10)   NOT NULL,
    nombre          VARCHAR(200)  NOT NULL,
    precio          DECIMAL(14,2) NOT NULL,
    activo          TINYINT(1)    NOT NULL DEFAULT 1,
    fecha_registro  DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    usuario_auditor VARCHAR(100)  NOT NULL,
    CONSTRAINT pk_producto       PRIMARY KEY (id),
    CONSTRAINT uq_producto_folio UNIQUE (folio),
    CONSTRAINT uq_producto_clave UNIQUE (clave),
    CONSTRAINT chk_precio        CHECK (precio >= 0)
);

CREATE INDEX idx_producto_nombre ON producto (nombre);
CREATE INDEX idx_producto_clave  ON producto (clave);
CREATE INDEX idx_producto_precio ON producto (precio);

INSERT INTO usuario (username, password, nombre, activo) VALUES
('admin', 'admin123', 'Administrador del Sistema', 1);