package com.shoptrust.models.ecommerce;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Entidad Producto para el catálogo de e-commerce
 */
@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;

    @Column(name = "sku", nullable = false, unique = true, length = 50)
    private String sku;

    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

    @Column(name = "slug", nullable = false, unique = true, length = 200)
    private String slug;

    @Column(name = "descripcion_corta", columnDefinition = "TEXT")
    private String descripcionCorta;

    @Column(name = "descripcion_completa", columnDefinition = "TEXT")
    private String descripcionCompleta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_marca", nullable = false)
    private Marca marca;

    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "precio_oferta", precision = 10, scale = 2)
    private BigDecimal precioOferta;

    @Column(name = "stock", nullable = false)
    private Integer stock = 0;

    @Column(name = "stock_minimo", nullable = false)
    private Integer stockMinimo = 5;

    @Column(name = "imagen_principal", length = 255)
    private String imagenPrincipal;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    @Column(name = "destacado", nullable = false)
    private Boolean destacado = false;

    @Column(name = "nuevo", nullable = false)
    private Boolean nuevo = false;

    @Column(name = "rating_promedio", precision = 3, scale = 2)
    private BigDecimal ratingPromedio;

    @Column(name = "total_resenas", nullable = false)
    private Integer totalResenas = 0;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    // Relaciones
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ImagenProducto> imagenes;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<EspecificacionProducto> especificaciones;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Resena> resenas;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Carrito> itemsCarrito;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Favorito> favoritos;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<DetallePedido> detallesPedido;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<HistorialPrecio> historialPrecios;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        if (activo == null) activo = true;
        if (destacado == null) destacado = false;
        if (nuevo == null) nuevo = false;
        if (stock == null) stock = 0;
        if (stockMinimo == null) stockMinimo = 5;
        if (totalResenas == null) totalResenas = 0;
    }

    @PreUpdate
    protected void onUpdate() {
        fechaModificacion = LocalDateTime.now();
    }

    /**
     * Calcula el porcentaje de descuento si hay precio de oferta
     */
    public BigDecimal getPorcentajeDescuento() {
        if (precioOferta != null && precio != null && precio.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal descuento = precio.subtract(precioOferta);
            return descuento
                    .divide(precio, 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"))
                    .setScale(0, RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO;
    }

    /**
     * Retorna el precio efectivo (oferta si existe, sino precio normal)
     */
    public BigDecimal getPrecioEfectivo() {
        return precioOferta != null ? precioOferta : precio;
    }

    /**
     * Verifica si el producto tiene stock bajo
     */
    public boolean isStockBajo() {
        return stock <= stockMinimo;
    }

    /**
     * Verifica si el producto está en oferta
     */
    public boolean isEnOferta() {
        return precioOferta != null && precioOferta.compareTo(precio) < 0;
    }
}
