package com.institucion.service;

import com.institucion.dto.ProductoFiltroDTO;
import com.institucion.dto.ProductoResponseDTO;
import com.institucion.entity.Producto;
import com.institucion.repository.ProductoRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReporteService {

    @Autowired
    private ProductoRepository productoRepository;

    public Map<String, Object> generarReporteExcel(ProductoFiltroDTO filtro) throws Exception {

        // Traer productos segun filtros
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        List<Producto> productos = productoRepository.filtrar(
                filtro.getNombre(),
                filtro.getClave(),
                filtro.getPrecioMin(),
                filtro.getPrecioMax(),
                pageable).getContent();

        // Crear workbook en memoria
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Productos");

        // Encabezados
        Row header = sheet.createRow(0);
        String[] columnas = {"ID", "Folio", "Clave", "Nombre", "Precio", "Activo", "Fecha Registro", "Usuario Auditor"};
        for (int i = 0; i < columnas.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(columnas[i]);
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
            cell.setCellStyle(style);
        }

        // Datos
        int rowNum = 1;
        for (Producto p : productos) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(p.getId());
            row.createCell(1).setCellValue(p.getFolio());
            row.createCell(2).setCellValue(p.getClave());
            row.createCell(3).setCellValue(p.getNombre());
            row.createCell(4).setCellValue(p.getPrecio().doubleValue());
            row.createCell(5).setCellValue(p.getActivo() ? "Activo" : "Inactivo");
            row.createCell(6).setCellValue(p.getFechaRegistro() != null ? p.getFechaRegistro().toString() : "");
            row.createCell(7).setCellValue(p.getUsuarioAuditor());
        }

        // Autoajustar columnas
        for (int i = 0; i < columnas.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Convertir a Base64 en memoria
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        String base64 = Base64.getEncoder().encodeToString(outputStream.toByteArray());

        // Respuesta
        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "Reporte generado correctamente");
        response.put("fileName", "productos.xlsx");
        response.put("fileBase64", base64);
        return response;
    }
}