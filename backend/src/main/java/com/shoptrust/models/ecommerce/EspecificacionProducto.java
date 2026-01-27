package com.shoptrust.models.ecommerce;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad EspecificacionProducto para características técnicas
 */
@Entity
@Table(name = "especificaciones_producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EspecificacionProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_especificacion")
    private Long idEspecificacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @Column(name = "nombre_especificacion", nullable = false, length = 100)
    private String nombreEspecificacion;

    @Column(name = "valor_especificacion", nullable = false, length = 200)
    private String valorEspecificacion;

    @Column(name = "orden", nullable = false)
    private Integer orden = 0;
}
