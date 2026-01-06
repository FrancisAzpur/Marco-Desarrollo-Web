package com.shoptrust.controllers;

import com.shoptrust.models.*;
import com.shoptrust.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProveedorService proveedorService;

    /**
     * Mostrar vista de ventas
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    public String mostrarVentas(Model model) {
        model.addAttribute("ventas", ventaService.obtenerTodasVentas());
        model.addAttribute("clientes", clienteService.obtenerClientesActivos());
        model.addAttribute("productos", productoService.obtenerProductosActivos());
        return "pages/ventas";
    }

    /**
     * Obtener todas las ventas (API)
     */
    @GetMapping("/api")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    @ResponseBody
    public ResponseEntity<List<Venta>> listarVentas() {
        return ResponseEntity.ok(ventaService.obtenerTodasVentas());
    }

    /**
     * Obtener venta por ID (API)
     */
    @GetMapping("/api/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    @ResponseBody
    public ResponseEntity<?> obtenerVenta(@PathVariable Long id) {
        return ventaService.obtenerVentaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtener detalles de una venta (API)
     */
    @GetMapping("/api/{id}/detalles")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    @ResponseBody
    public ResponseEntity<List<DetalleVenta>> obtenerDetalles(@PathVariable Long id) {
        return ResponseEntity.ok(ventaService.obtenerDetallesVenta(id));
    }

    /**
     * Crear nueva venta (API)
     */
    @PostMapping("/api")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    @ResponseBody
    public ResponseEntity<?> crearVenta(@RequestBody VentaRequest request, Authentication authentication) {
        try {
            // Crear objeto Venta
            Venta venta = new Venta();
            venta.setMetodoPago(request.getMetodoPago());
            venta.setEstado("COMPLETADA");
            
            // Buscar usuario autenticado
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(request.getIdUsuario());
            venta.setUsuario(usuario);

            // Si hay cliente, asignarlo
            if (request.getIdCliente() != null) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(request.getIdCliente());
                venta.setCliente(cliente);
            }

            // Crear detalles
            List<DetalleVenta> detalles = request.getDetalles().stream()
                    .map(d -> {
                        DetalleVenta detalle = new DetalleVenta();
                        
                        Producto producto = new Producto();
                        producto.setIdProducto(d.getIdProducto());
                        detalle.setProducto(producto);
                        
                        detalle.setCantidad(d.getCantidad());
                        detalle.setPrecioUnitario(d.getPrecioUnitario());
                        
                        return detalle;
                    })
                    .toList();

            // Crear venta con detalles
            Venta ventaCreada = ventaService.crearVenta(venta, detalles);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Venta creada exitosamente");
            response.put("venta", ventaCreada);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("success", "false");
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * Anular venta (API)
     */
    @PatchMapping("/api/{id}/anular")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<?> anularVenta(@PathVariable Long id) {
        try {
            Venta ventaAnulada = ventaService.anularVenta(id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Venta anulada exitosamente");
            response.put("venta", ventaAnulada);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("success", "false");
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * Obtener ventas por rango de fechas (API)
     */
    @GetMapping("/api/reportes/por-fecha")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    @ResponseBody
    public ResponseEntity<?> ventasPorFecha(
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin) {
        try {
            LocalDateTime inicio = LocalDate.parse(fechaInicio).atStartOfDay();
            LocalDateTime fin = LocalDate.parse(fechaFin).atTime(23, 59, 59);
            
            List<Venta> ventas = ventaService.obtenerVentasPorFechas(inicio, fin);
            
            BigDecimal total = ventas.stream()
                    .filter(v -> !"ANULADA".equals(v.getEstado()))
                    .map(Venta::getTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            Map<String, Object> response = new HashMap<>();
            response.put("ventas", ventas);
            response.put("total", total);
            response.put("cantidad", ventas.size());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * Obtener ventas del día (API)
     */
    @GetMapping("/api/reportes/hoy")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    @ResponseBody
    public ResponseEntity<?> ventasHoy() {
        LocalDateTime hoy = LocalDateTime.now();
        BigDecimal totalHoy = ventaService.calcularVentasDia(hoy);
        
        Map<String, Object> response = new HashMap<>();
        response.put("fecha", hoy.toLocalDate());
        response.put("total", totalHoy);
        
        return ResponseEntity.ok(response);
    }

    /**
     * DTO para recibir datos de venta
     */
    public static class VentaRequest {
        private Long idUsuario;
        private Long idCliente;
        private String metodoPago;
        private List<DetalleRequest> detalles;

        public Long getIdUsuario() { return idUsuario; }
        public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

        public Long getIdCliente() { return idCliente; }
        public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }

        public String getMetodoPago() { return metodoPago; }
        public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

        public List<DetalleRequest> getDetalles() { return detalles; }
        public void setDetalles(List<DetalleRequest> detalles) { this.detalles = detalles; }
    }

    public static class DetalleRequest {
        private Long idProducto;
        private Integer cantidad;
        private BigDecimal precioUnitario;

        public Long getIdProducto() { return idProducto; }
        public void setIdProducto(Long idProducto) { this.idProducto = idProducto; }

        public Integer getCantidad() { return cantidad; }
        public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

        public BigDecimal getPrecioUnitario() { return precioUnitario; }
        public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }
    }
}
