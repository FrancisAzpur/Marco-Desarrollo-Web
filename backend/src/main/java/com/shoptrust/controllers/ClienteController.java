package com.shoptrust.controllers;

import com.shoptrust.models.Cliente;
import com.shoptrust.services.ClienteService;
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
 * Controlador para gestión de Clientes
 */
@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    /**
     * Mostrar lista de clientes (Vista HTML)
     */
    @GetMapping
    public String listarClientes(Model model) {
        List<Cliente> clientes = clienteService.obtenerTodosClientes();
        model.addAttribute("clientes", clientes);
        model.addAttribute("cliente", new Cliente());
        return "pages/clientes";
    }

    /**
     * API REST: Obtener todos los clientes
     */
    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<List<Cliente>> obtenerTodosClientes() {
        return ResponseEntity.ok(clienteService.obtenerTodosClientes());
    }

    /**
     * API REST: Obtener clientes activos
     */
    @GetMapping("/api/activos")
    @ResponseBody
    public ResponseEntity<List<Cliente>> obtenerClientesActivos() {
        return ResponseEntity.ok(clienteService.obtenerClientesActivos());
    }

    /**
     * API REST: Buscar cliente por ID
     */
    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.buscarPorId(id);
        return cliente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * API REST: Crear nuevo cliente
     */
    @PostMapping("/api")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    public ResponseEntity<Map<String, Object>> crearCliente(@Valid @RequestBody Cliente cliente) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Verificar si ya existe un cliente con ese documento
            if (clienteService.existePorDocumento(cliente.getDocumentoIdentidad())) {
                response.put("success", false);
                response.put("message", "Ya existe un cliente con ese documento de identidad");
                return ResponseEntity.badRequest().body(response);
            }

            Cliente nuevoCliente = clienteService.guardar(cliente);
            response.put("success", true);
            response.put("message", "Cliente creado exitosamente");
            response.put("data", nuevoCliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al crear el cliente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * API REST: Actualizar cliente existente
     */
    @PutMapping("/api/{id}")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    public ResponseEntity<Map<String, Object>> actualizarCliente(
            @PathVariable Long id,
            @Valid @RequestBody Cliente cliente) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<Cliente> clienteExistente = clienteService.buscarPorId(id);
            
            if (clienteExistente.isEmpty()) {
                response.put("success", false);
                response.put("message", "Cliente no encontrado");
                return ResponseEntity.notFound().build();
            }

            cliente.setIdCliente(id);
            Cliente clienteActualizado = clienteService.guardar(cliente);
            
            response.put("success", true);
            response.put("message", "Cliente actualizado exitosamente");
            response.put("data", clienteActualizado);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar el cliente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * API REST: Eliminar (desactivar) cliente
     */
    @DeleteMapping("/api/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> eliminarCliente(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<Cliente> cliente = clienteService.buscarPorId(id);
            
            if (cliente.isEmpty()) {
                response.put("success", false);
                response.put("message", "Cliente no encontrado");
                return ResponseEntity.notFound().build();
            }

            clienteService.eliminar(id);
            response.put("success", true);
            response.put("message", "Cliente eliminado exitosamente");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar el cliente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
