# API Examen Producto - Backend

## Descripción
Módulo de Gestión de Productos Institucionales desarrollado con Spring Boot 3.2.5 y Java 17.

## Tecnologías
- Java 17
- Spring Boot 3.2.5
- Spring Security + JWT
- Spring Data JPA + Hibernate
- MariaDB
- Apache POI (Excel)
- Swagger / OpenAPI 3
- Lombok
- Maven

## Requisitos previos
- Java 17 instalado
- MariaDB instalado y corriendo en puerto 3306
- Maven instalado

## Configuración de Base de Datos
1. Abrir DBeaver o HeidiSQL
2. Ejecutar el script `src/main/resources/schema.sql`
3. Esto creará la base de datos `productos_db` con las tablas y el usuario admin

## Configuración del proyecto
Abrir `src/main/resources/application.properties` y ajustar:

spring.datasource.url=jdbc:mariadb://localhost:3306/productos_db
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD

## Ejecución
1. Clonar o descomprimir el proyecto
2. Abrir en Spring Tools Suite o IntelliJ
3. Ejecutar como Spring Boot Application
4. El servidor inicia en `http://localhost:8080`

## Endpoints
| Método | URL | Descripción | Auth |
|--------|-----|-------------|------|
| POST | /api/auth/login | Iniciar sesión y obtener token JWT | No |
| GET | /api/productos/listar | Listar productos con filtros y paginación | Sí |
| POST | /api/productos/crear | Crear nuevo producto | Sí |
| PUT | /api/productos/actualizar/{id} | Actualizar producto existente | Sí |
| GET | /api/productos/reporte | Generar reporte Excel en Base64 | Sí |

## Autenticación
Todos los endpoints excepto `/api/auth/login` requieren token JWT en el header:

Authorization: Bearer {token}

## Usuario por defecto
| Campo | Valor |
|-------|-------|
| Username | admin |
| Password | admin123 |

## Documentación Swagger
Con el proyecto corriendo acceder a:

http://localhost:8080/swagger-ui.html

## Estructura del proyecto

src/main/java/com/institucion/
├── config/          # Configuración de seguridad y CORS
├── controller/      # Controladores REST
├── dto/             # Objetos de transferencia de datos
├── entity/          # Entidades JPA
├── exception/       # Manejo global de errores
├── repository/      # Repositorios JPA
├── security/        # JWT y filtros de seguridad
└── service/         # Lógica de negocio