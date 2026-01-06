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
@RequestMapping("/compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private ProductoService productoService;

    /**
     * Mostrar vista de compras
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    public String mostrarCompras(Model model) {
        model.addAttribute("compras", compraService.obtenerTodasCompras());
        model.addAttribute("proveedores", proveedorService.obtenerProveedoresActivos());
        model.addAttribute("productos", productoService.obtenerProductosActivos());
        return "pages/compras";
    }

    /**
     * Obtener todas las compras (API)
     */
    @GetMapping("/api")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    @ResponseBody
    public ResponseEntity<List<Compra>> listarCompras() {
        return ResponseEntity.ok(compraService.obtenerTodasCompras());
    }

    /**
     * Obtener compra por ID (API)
     */
    @GetMapping("/api/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    @ResponseBody
    public ResponseEntity<?> obtenerCompra(@PathVariable Long id) {
        return compraService.obtenerCompraPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtener detalles de una compra (API)
     */
    @GetMapping("/api/{id}/detalles")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    @ResponseBody
    public ResponseEntity<List<DetalleCompra>> obtenerDetalles(@PathVariable Long id) {
        return ResponseEntity.ok(compraService.obtenerDetallesCompra(id));
    }

    /**
     * Crear nueva compra (API)
     */
    @PostMapping("/api")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    @ResponseBody
    public ResponseEntity<?> crearCompra(@RequestBody CompraRequest request, Authentication authentication) {
        try {
            // Crear objeto Compra
            Compra compra = new Compra();
            compra.setEstado("COMPLETADA");
            compra.setObservaciones(request.getObservaciones());
            
            // Buscar usuario autenticado
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(request.getIdUsuario());
            compra.setUsuario(usuario);

            // Asignar proveedor
            if (request.getIdProveedor() == null) {
                throw new RuntimeException("El proveedor es obligatorio");
            }
            Proveedor proveedor = new Proveedor();
            proveedor.setIdProveedor(request.getIdProveedor());
            compra.setProveedor(proveedor);

            // Crear detalles
            List<DetalleCompra> detalles = request.getDetalles().stream()
                    .map(d -> {
                        DetalleCompra detalle = new DetalleCompra();
                        
                        Producto producto = new Producto();
                        producto.setIdProducto(d.getIdProducto());
                        detalle.setProducto(producto);
                        
                        detalle.setCantidad(d.getCantidad());
                        detalle.setPrecioUnitario(d.getPrecioUnitario());
                        
                        return detalle;
                    })
                    .toList();

            // Crear compra con detalles
            Compra compraCreada = compraService.crearCompra(compra, detalles);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Compra creada exitosamente");
            response.put("compra", compraCreada);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("success", "false");
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * Anular compra (API)
     */
    @PatchMapping("/api/{id}/anular")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<?> anularCompra(@PathVariable Long id) {
        try {
            Compra compraAnulada = compraService.anularCompra(id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Compra anulada exitosamente");
            response.put("compra", compraAnulada);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("success", "false");
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * Obtener compras por rango de fechas (API)
     */
    @GetMapping("/api/reportes/por-fecha")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    @ResponseBody
    public ResponseEntity<?> comprasPorFecha(
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin) {
        try {
            LocalDateTime inicio = LocalDate.parse(fechaInicio).atStartOfDay();
            LocalDateTime fin = LocalDate.parse(fechaFin).atTime(23, 59, 59);
            
            List<Compra> compras = compraService.obtenerComprasPorFechas(inicio, fin);
            
            BigDecimal total = compras.stream()
                    .filter(c -> !"ANULADA".equals(c.getEstado()))
                    .map(Compra::getTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            Map<String, Object> response = new HashMap<>();
            response.put("compras", compras);
            response.put("total", total);
            response.put("cantidad", compras.size());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * Obtener compras del día (API)
     */
    @GetMapping("/api/reportes/hoy")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    @ResponseBody
    public ResponseEntity<?> comprasHoy() {
        LocalDateTime hoy = LocalDateTime.now();
        BigDecimal totalHoy = compraService.calcularComprasDia(hoy);
        
        Map<String, Object> response = new HashMap<>();
        response.put("fecha", hoy.toLocalDate());
        response.put("total", totalHoy);
        
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener compras por proveedor (API)
     */
    @GetMapping("/api/proveedor/{idProveedor}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    @ResponseBody
    public ResponseEntity<?> comprasPorProveedor(@PathVariable Long idProveedor) {
        List<Compra> compras = compraService.obtenerComprasPorProveedor(idProveedor);
        BigDecimal total = compraService.calcularTotalComprasProveedor(idProveedor);
        
        Map<String, Object> response = new HashMap<>();
        response.put("compras", compras);
        response.put("total", total);
        
        return ResponseEntity.ok(response);
    }

    /**
     * DTO para recibir datos de compra
     */
    public static class CompraRequest {
        private Long idUsuario;
        private Long idProveedor;
        private String observaciones;
        private List<DetalleRequest> detalles;

        public Long getIdUsuario() { return idUsuario; }
        public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

        public Long getIdProveedor() { return idProveedor; }
        public void setIdProveedor(Long idProveedor) { this.idProveedor = idProveedor; }

        public String getObservaciones() { return observaciones; }
        public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

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
