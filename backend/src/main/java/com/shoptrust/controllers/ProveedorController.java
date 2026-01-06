package com.shoptrust.controllers;

import com.shoptrust.models.Proveedor;
import com.shoptrust.services.ProveedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controlador para gestión de Proveedores
 */
@Controller
@RequestMapping("/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    /**
     * Mostrar lista de proveedores (Vista HTML)
     */
    @GetMapping
    public String listarProveedores(Model model) {
        List<Proveedor> proveedores = proveedorService.obtenerTodosProveedores();
        model.addAttribute("proveedores", proveedores);
        model.addAttribute("proveedor", new Proveedor());
        return "pages/proveedores";
    }

    /**
     * API REST: Obtener todos los proveedores
     */
    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<List<Proveedor>> obtenerTodosProveedores() {
        return ResponseEntity.ok(proveedorService.obtenerTodosProveedores());
    }

    /**
     * API REST: Obtener proveedores activos
     */
    @GetMapping("/api/activos")
    @ResponseBody
    public ResponseEntity<List<Proveedor>> obtenerProveedoresActivos() {
        return ResponseEntity.ok(proveedorService.obtenerProveedoresActivos());
    }

    /**
     * API REST: Buscar proveedor por ID
     */
    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Proveedor> buscarPorId(@PathVariable Long id) {
        Optional<Proveedor> proveedor = proveedorService.buscarPorId(id);
        return proveedor.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * API REST: Crear nuevo proveedor
     */
    @PostMapping("/api")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    public ResponseEntity<Map<String, Object>> crearProveedor(@Valid @RequestBody Proveedor proveedor) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Verificar si ya existe un proveedor con ese RUC
            if (proveedorService.existePorRuc(proveedor.getRuc())) {
                response.put("success", false);
                response.put("message", "Ya existe un proveedor con ese RUC");
                return ResponseEntity.badRequest().body(response);
            }

            Proveedor nuevoProveedor = proveedorService.guardar(proveedor);
            response.put("success", true);
            response.put("message", "Proveedor creado exitosamente");
            response.put("data", nuevoProveedor);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al crear el proveedor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * API REST: Actualizar proveedor existente
     */
    @PutMapping("/api/{id}")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    public ResponseEntity<Map<String, Object>> actualizarProveedor(
            @PathVariable Long id,
            @Valid @RequestBody Proveedor proveedor) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<Proveedor> proveedorExistente = proveedorService.buscarPorId(id);
            
            if (proveedorExistente.isEmpty()) {
                response.put("success", false);
                response.put("message", "Proveedor no encontrado");
                return ResponseEntity.notFound().build();
            }

            proveedor.setIdProveedor(id);
            Proveedor proveedorActualizado = proveedorService.guardar(proveedor);
            
            response.put("success", true);
            response.put("message", "Proveedor actualizado exitosamente");
            response.put("data", proveedorActualizado);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar el proveedor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * API REST: Eliminar (desactivar) proveedor
     */
    @DeleteMapping("/api/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> eliminarProveedor(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<Proveedor> proveedor = proveedorService.buscarPorId(id);
            
            if (proveedor.isEmpty()) {
                response.put("success", false);
                response.put("message", "Proveedor no encontrado");
                return ResponseEntity.notFound().build();
            }

            proveedorService.eliminar(id);
            response.put("success", true);
            response.put("message", "Proveedor eliminado exitosamente");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar el proveedor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
