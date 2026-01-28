package com.shoptrust.services;
package com.shoptrust.models;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProductoOferta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String marca;
    private String imagenUrl;
    private String categoria;       // "Refrigerador", "Lavadora", etc.

    @Column(precision = 10, scale = 2)
    private BigDecimal precioOriginal;

    @Column(precision = 10, scale = 2)
    private BigDecimal precioOferta;

    private Integer descuentoPorcentaje; // 25, 40, etc.
    private Boolean destacado = false;
    private Integer stock;
}
