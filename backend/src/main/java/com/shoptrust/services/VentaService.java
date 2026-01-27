package com.shoptrust.services;

import com.shoptrust.models.*;
import com.shoptrust.repositories.DetalleVentaRepository;
import com.shoptrust.repositories.ProductoRepository;
import com.shoptrust.repositories.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    /**
     * Obtener todas las ventas
     */
    public List<Venta> obtenerTodasVentas() {
        return ventaRepository.findAll();
    }

    /**
     * Obtener venta por ID
     */
    public Optional<Venta> obtenerVentaPorId(Long id) {
        return ventaRepository.findById(id);
    }

    /**
     * Buscar venta por número
     */
    public Optional<Venta> buscarPorNumeroVenta(String numeroVenta) {
        return ventaRepository.findByNumeroVenta(numeroVenta);
    }

    /**
     * Obtener ventas por rango de fechas
     */
    public List<Venta> obtenerVentasPorFechas(LocalDateTime inicio, LocalDateTime fin) {
        return ventaRepository.findByFechaVentaBetween(inicio, fin);
    }

    /**
     * Obtener ventas de un usuario
     */
    public List<Venta> obtenerVentasPorUsuario(Long idUsuario) {
        return ventaRepository.findByUsuario_IdUsuario(idUsuario);
    }

    /**
     * Crear venta con sus detalles
     */
    public Venta crearVenta(Venta venta, List<DetalleVenta> detalles) {
        // Validar que hay detalles
        if (detalles == null || detalles.isEmpty()) {
            throw new RuntimeException("La venta debe tener al menos un producto");
        }

        // Generar número de venta si no existe
        if (venta.getNumeroVenta() == null || venta.getNumeroVenta().isEmpty()) {
            venta.setNumeroVenta(generarNumeroVenta());
        }

        // Calcular totales
        BigDecimal subtotal = BigDecimal.ZERO;
        for (DetalleVenta detalle : detalles) {
            // Validar stock disponible
            Producto producto = productoRepository.findById(detalle.getProducto().getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + detalle.getProducto().getIdProducto()));

            if (producto.getStockActual() < detalle.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombreProducto());
            }

            // Calcular subtotal del detalle
            BigDecimal subtotalDetalle = detalle.getPrecioUnitario()
                    .multiply(BigDecimal.valueOf(detalle.getCantidad()));
            detalle.setSubtotal(subtotalDetalle);
            subtotal = subtotal.add(subtotalDetalle);
        }

        // Establecer totales en la venta
        venta.setSubtotal(subtotal);
        
        // Calcular impuesto (18% IGV en Perú)
        BigDecimal impuesto = subtotal.multiply(new BigDecimal("0.18"));
        venta.setImpuesto(impuesto);
        
        // Total = subtotal + impuesto
        venta.setTotal(subtotal.add(impuesto));

        // Guardar venta
        Venta ventaGuardada = ventaRepository.save(venta);

        // Guardar detalles y actualizar stock
        for (DetalleVenta detalle : detalles) {
            detalle.setVenta(ventaGuardada);
            detalleVentaRepository.save(detalle);

            // Actualizar stock del producto
            Producto producto = detalle.getProducto();
            producto.setStockActual(producto.getStockActual() - detalle.getCantidad());
            productoRepository.save(producto);
        }

        return ventaGuardada;
    }

    /**
     * Anular venta (cambiar estado y devolver stock)
     */
    public Venta anularVenta(Long idVenta) {
        Venta venta = ventaRepository.findById(idVenta)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

        if ("ANULADA".equals(venta.getEstado())) {
            throw new RuntimeException("La venta ya está anulada");
        }

        // Obtener detalles y devolver stock
        List<DetalleVenta> detalles = detalleVentaRepository.findByVenta_IdVenta(idVenta);
        for (DetalleVenta detalle : detalles) {
            Producto producto = detalle.getProducto();
            producto.setStockActual(producto.getStockActual() + detalle.getCantidad());
            productoRepository.save(producto);
        }

        // Cambiar estado
        venta.setEstado("ANULADA");
        return ventaRepository.save(venta);
    }

    /**
     * Obtener detalles de una venta
     */
    public List<DetalleVenta> obtenerDetallesVenta(Long idVenta) {
        return detalleVentaRepository.findByVenta_IdVenta(idVenta);
    }

    /**
     * Generar número de venta automático
     */
    private String generarNumeroVenta() {
        LocalDateTime ahora = LocalDateTime.now();
        String prefijo = "VEN-%04d%02d%02d-".formatted(
                ahora.getYear(), ahora.getMonthValue(), ahora.getDayOfMonth());
        
        // Buscar el último número del día
        List<Venta> ventasHoy = ventaRepository.findByFechaVentaBetween(
                ahora.toLocalDate().atStartOfDay(),
                ahora.toLocalDate().atTime(23, 59, 59)
        );
        
        int numero = ventasHoy.size() + 1;
        return prefijo + "%04d".formatted(numero);
    }

    /**
     * Calcular ventas totales del día
     */
    public BigDecimal calcularVentasDia(LocalDateTime fecha) {
        List<Venta> ventas = ventaRepository.findByFechaVentaBetween(
                fecha.toLocalDate().atStartOfDay(),
                fecha.toLocalDate().atTime(23, 59, 59)
        );
        
        return ventas.stream()
                .filter(v -> !"ANULADA".equals(v.getEstado()))
                .map(Venta::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
