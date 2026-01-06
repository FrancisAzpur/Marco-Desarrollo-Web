package com.shoptrust.controllers;

import com.shoptrust.models.Producto;
import com.shoptrust.services.ProductoService;
import com.shoptrust.services.CategoriaService;
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
 * Controlador para gestión de Productos
 */
@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    /**
     * Mostrar lista de productos (Vista HTML)
     */
    @GetMapping
    public String listarProductos(Model model) {
        List<Producto> productos = productoService.obtenerTodosProductos();
        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categoriaService.obtenerCategoriasActivas());
        model.addAttribute("producto", new Producto());
        return "pages/productos";
    }

    /**
     * API REST: Obtener todos los productos
     */
    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<List<Producto>> obtenerTodosProductos() {
        return ResponseEntity.ok(productoService.obtenerTodosProductos());
    }

    /**
     * API REST: Obtener productos activos
     */
    @GetMapping("/api/activos")
    @ResponseBody
    public ResponseEntity<List<Producto>> obtenerProductosActivos() {
        return ResponseEntity.ok(productoService.obtenerProductosActivos());
    }

    /**
     * API REST: Buscar producto por ID
     */
    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Producto> buscarPorId(@PathVariable Long id) {
        Optional<Producto> producto = productoService.buscarPorId(id);
        return producto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * API REST: Buscar productos con stock bajo
     */
    @GetMapping("/api/stock-bajo")
    @ResponseBody
    public ResponseEntity<List<Producto>> obtenerProductosStockBajo() {
        return ResponseEntity.ok(productoService.obtenerProductosStockBajo());
    }

    /**
     * API REST: Crear nuevo producto
     */
    @PostMapping("/api")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    public ResponseEntity<Map<String, Object>> crearProducto(@Valid @RequestBody Producto producto) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Verificar si ya existe un producto con ese código
            if (productoService.existePorCodigo(producto.getCodigoProducto())) {
                response.put("success", false);
                response.put("message", "Ya existe un producto con ese código");
                return ResponseEntity.badRequest().body(response);
            }

            Producto nuevoProducto = productoService.guardar(producto);
            response.put("success", true);
            response.put("message", "Producto creado exitosamente");
            response.put("data", nuevoProducto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al crear el producto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * API REST: Actualizar producto existente
     */
    @PutMapping("/api/{id}")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    public ResponseEntity<Map<String, Object>> actualizarProducto(
            @PathVariable Long id,
            @Valid @RequestBody Producto producto) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<Producto> productoExistente = productoService.buscarPorId(id);
            
            if (productoExistente.isEmpty()) {
                response.put("success", false);
                response.put("message", "Producto no encontrado");
                return ResponseEntity.notFound().build();
            }

            producto.setIdProducto(id);
            Producto productoActualizado = productoService.guardar(producto);
            
            response.put("success", true);
            response.put("message", "Producto actualizado exitosamente");
            response.put("data", productoActualizado);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar el producto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * API REST: Eliminar (desactivar) producto
     */
    @DeleteMapping("/api/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> eliminarProducto(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<Producto> producto = productoService.buscarPorId(id);
            
            if (producto.isEmpty()) {
                response.put("success", false);
                response.put("message", "Producto no encontrado");
                return ResponseEntity.notFound().build();
            }

            productoService.eliminar(id);
            response.put("success", true);
            response.put("message", "Producto eliminado exitosamente");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar el producto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
