package com.shoptrust.services;

import com.shoptrust.models.*;
import com.shoptrust.repositories.CompraRepository;
import com.shoptrust.repositories.DetalleCompraRepository;
import com.shoptrust.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private DetalleCompraRepository detalleCompraRepository;

    @Autowired
    private ProductoRepository productoRepository;

    /**
     * Obtener todas las compras
     */
    public List<Compra> obtenerTodasCompras() {
        return compraRepository.findAll();
    }

    /**
     * Obtener compra por ID
     */
    public Optional<Compra> obtenerCompraPorId(Long id) {
        return compraRepository.findById(id);
    }

    /**
     * Buscar compra por número
     */
    public Optional<Compra> buscarPorNumeroCompra(String numeroCompra) {
        return compraRepository.findByNumeroCompra(numeroCompra);
    }

    /**
     * Obtener compras por rango de fechas
     */
    public List<Compra> obtenerComprasPorFechas(LocalDateTime inicio, LocalDateTime fin) {
        return compraRepository.findByFechaCompraBetween(inicio, fin);
    }

    /**
     * Obtener compras de un proveedor
     */
    public List<Compra> obtenerComprasPorProveedor(Long idProveedor) {
        return compraRepository.findByProveedor_IdProveedor(idProveedor);
    }

    /**
     * Crear compra con sus detalles
     */
    public Compra crearCompra(Compra compra, List<DetalleCompra> detalles) {
        // Validar que hay detalles
        if (detalles == null || detalles.isEmpty()) {
            throw new RuntimeException("La compra debe tener al menos un producto");
        }

        // Generar número de compra si no existe
        if (compra.getNumeroCompra() == null || compra.getNumeroCompra().isEmpty()) {
            compra.setNumeroCompra(generarNumeroCompra());
        }

        // Calcular totales
        BigDecimal subtotal = BigDecimal.ZERO;
        for (DetalleCompra detalle : detalles) {
            // Validar que el producto existe
            Producto producto = productoRepository.findById(detalle.getProducto().getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + detalle.getProducto().getIdProducto()));

            // Calcular subtotal del detalle
            BigDecimal subtotalDetalle = detalle.getPrecioUnitario()
                    .multiply(BigDecimal.valueOf(detalle.getCantidad()));
            detalle.setSubtotal(subtotalDetalle);
            subtotal = subtotal.add(subtotalDetalle);
        }

        // Establecer totales en la compra
        compra.setSubtotal(subtotal);
        
        // Calcular impuesto (18% IGV en Perú)
        BigDecimal impuesto = subtotal.multiply(new BigDecimal("0.18"));
        compra.setImpuesto(impuesto);
        
        // Total = subtotal + impuesto
        compra.setTotal(subtotal.add(impuesto));

        // Guardar compra
        Compra compraGuardada = compraRepository.save(compra);

        // Guardar detalles y actualizar stock
        for (DetalleCompra detalle : detalles) {
            detalle.setCompra(compraGuardada);
            detalleCompraRepository.save(detalle);

            // Actualizar stock del producto (incrementar)
            Producto producto = detalle.getProducto();
            producto.setStockActual(producto.getStockActual() + detalle.getCantidad());
            
            // Actualizar precio de compra del producto
            producto.setPrecioCompra(detalle.getPrecioUnitario());
            
            productoRepository.save(producto);
        }

        return compraGuardada;
    }

    /**
     * Anular compra (cambiar estado y descontar stock)
     */
    public Compra anularCompra(Long idCompra) {
        Compra compra = compraRepository.findById(idCompra)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));

        if ("ANULADA".equals(compra.getEstado())) {
            throw new RuntimeException("La compra ya está anulada");
        }

        // Obtener detalles y descontar stock
        List<DetalleCompra> detalles = detalleCompraRepository.findByCompra_IdCompra(idCompra);
        for (DetalleCompra detalle : detalles) {
            Producto producto = detalle.getProducto();
            int nuevoStock = producto.getStockActual() - detalle.getCantidad();
            
            // Validar que no quede stock negativo
            if (nuevoStock < 0) {
                throw new RuntimeException("No se puede anular la compra. El producto '" + 
                        producto.getNombreProducto() + "' ya ha sido vendido.");
            }
            
            producto.setStockActual(nuevoStock);
            productoRepository.save(producto);
        }

        // Cambiar estado
        compra.setEstado("ANULADA");
        return compraRepository.save(compra);
    }

    /**
     * Obtener detalles de una compra
     */
    public List<DetalleCompra> obtenerDetallesCompra(Long idCompra) {
        return detalleCompraRepository.findByCompra_IdCompra(idCompra);
    }

    /**
     * Generar número de compra automático
     */
    private String generarNumeroCompra() {
        LocalDateTime ahora = LocalDateTime.now();
        String prefijo = String.format("COM-%04d%02d%02d-", 
                ahora.getYear(), ahora.getMonthValue(), ahora.getDayOfMonth());
        
        // Buscar el último número del día
        List<Compra> comprasHoy = compraRepository.findByFechaCompraBetween(
                ahora.toLocalDate().atStartOfDay(),
                ahora.toLocalDate().atTime(23, 59, 59)
        );
        
        int numero = comprasHoy.size() + 1;
        return prefijo + String.format("%04d", numero);
    }

    /**
     * Calcular compras totales del día
     */
    public BigDecimal calcularComprasDia(LocalDateTime fecha) {
        List<Compra> compras = compraRepository.findByFechaCompraBetween(
                fecha.toLocalDate().atStartOfDay(),
                fecha.toLocalDate().atTime(23, 59, 59)
        );
        
        return compras.stream()
                .filter(c -> !"ANULADA".equals(c.getEstado()))
                .map(Compra::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Obtener estadísticas de compras por proveedor
     */
    public BigDecimal calcularTotalComprasProveedor(Long idProveedor) {
        List<Compra> compras = compraRepository.findByProveedor_IdProveedor(idProveedor);
        
        return compras.stream()
                .filter(c -> !"ANULADA".equals(c.getEstado()))
                .map(Compra::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
