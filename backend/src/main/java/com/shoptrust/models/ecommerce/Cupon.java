package com.shoptrust.models.ecommerce;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad Cupon para descuentos y promociones
 */
@Entity
@Table(name = "cupones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cupon")
    private Long idCupon;

    @Column(name = "codigo", nullable = false, unique = true, length = 50)
    private String codigo;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_descuento", nullable = false, length = 20)
    private TipoDescuento tipoDescuento;

    @Column(name = "valor_descuento", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorDescuento;

    @Column(name = "compra_minima", precision = 10, scale = 2)
    private BigDecimal compraMinima;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_expiracion", nullable = false)
    private LocalDate fechaExpiracion;

    @Column(name = "usos_maximos")
    private Integer usosMaximos;

    @Column(name = "usos_actuales", nullable = false)
    private Integer usosActuales = 0;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        if (usosActuales == null) usosActuales = 0;
        if (activo == null) activo = true;
    }

    /**
     * Verifica si el cupón es válido en la fecha actual
     */
    public boolean isValido() {
        LocalDate hoy = LocalDate.now();
        boolean dentroFechas = !hoy.isBefore(fechaInicio) && !hoy.isAfter(fechaExpiracion);
        boolean tieneusos = usosMaximos == null || usosActuales < usosMaximos;
        return activo && dentroFechas && tieneusos;
    }

    /**
     * Calcula el descuento aplicable según el subtotal
     */
    public BigDecimal calcularDescuento(BigDecimal subtotal) {
        if (!isValido()) {
            return BigDecimal.ZERO;
        }
        if (compraMinima != null && subtotal.compareTo(compraMinima) < 0) {
            return BigDecimal.ZERO;
        }

        if (tipoDescuento == TipoDescuento.PORCENTAJE) {
            return subtotal.multiply(valorDescuento).divide(new BigDecimal("100"));
        } else {
            return valorDescuento;
        }
    }

    public enum TipoDescuento {
        PORCENTAJE,
        MONTO_FIJO
    }
}
