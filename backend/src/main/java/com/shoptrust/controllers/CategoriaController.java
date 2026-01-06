package com.shoptrust.controllers;

import com.shoptrust.models.Categoria;
import com.shoptrust.services.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controlador para gestión de Categorías
 */
@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    /**
     * Mostrar lista de categorías (Vista HTML)
     */
    @GetMapping
    public String listarCategorias(Model model) {
        List<Categoria> categorias = categoriaService.obtenerTodasCategorias();
        model.addAttribute("categorias", categorias);
        model.addAttribute("categoria", new Categoria());
        return "pages/categorias";
    }

    /**
     * API REST: Obtener todas las categorías
     */
    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<List<Categoria>> obtenerTodasCategorias() {
        return ResponseEntity.ok(categoriaService.obtenerTodasCategorias());
    }

    /**
     * API REST: Obtener categorías activas
     */
    @GetMapping("/api/activas")
    @ResponseBody
    public ResponseEntity<List<Categoria>> obtenerCategoriasActivas() {
        return ResponseEntity.ok(categoriaService.obtenerCategoriasActivas());
    }

    /**
     * API REST: Buscar categoría por ID
     */
    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Categoria> buscarPorId(@PathVariable Long id) {
        Optional<Categoria> categoria = categoriaService.buscarPorId(id);
        return categoria.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * API REST: Crear nueva categoría
     */
    @PostMapping("/api")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    public ResponseEntity<Map<String, Object>> crearCategoria(@Valid @RequestBody Categoria categoria) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Verificar si ya existe una categoría con ese nombre
            if (categoriaService.existePorNombre(categoria.getNombreCategoria())) {
                response.put("success", false);
                response.put("message", "Ya existe una categoría con ese nombre");
                return ResponseEntity.badRequest().body(response);
            }

            Categoria nuevaCategoria = categoriaService.guardar(categoria);
            response.put("success", true);
            response.put("message", "Categoría creada exitosamente");
            response.put("data", nuevaCategoria);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al crear la categoría: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * API REST: Actualizar categoría existente
     */
    @PutMapping("/api/{id}")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    public ResponseEntity<Map<String, Object>> actualizarCategoria(
            @PathVariable Long id,
            @Valid @RequestBody Categoria categoria) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<Categoria> categoriaExistente = categoriaService.buscarPorId(id);
            
            if (categoriaExistente.isEmpty()) {
                response.put("success", false);
                response.put("message", "Categoría no encontrada");
                return ResponseEntity.notFound().build();
            }

            categoria.setIdCategoria(id);
            Categoria categoriaActualizada = categoriaService.guardar(categoria);
            
            response.put("success", true);
            response.put("message", "Categoría actualizada exitosamente");
            response.put("data", categoriaActualizada);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar la categoría: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * API REST: Eliminar (desactivar) categoría
     */
    @DeleteMapping("/api/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> eliminarCategoria(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<Categoria> categoria = categoriaService.buscarPorId(id);
            
            if (categoria.isEmpty()) {
                response.put("success", false);
                response.put("message", "Categoría no encontrada");
                return ResponseEntity.notFound().build();
            }

            categoriaService.eliminar(id);
            response.put("success", true);
            response.put("message", "Categoría eliminada exitosamente");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar la categoría: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * API REST: Activar categoría
     */
    @PatchMapping("/api/{id}/activar")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> activarCategoria(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            categoriaService.activar(id);
            response.put("success", true);
            response.put("message", "Categoría activada exitosamente");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al activar la categoría: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
