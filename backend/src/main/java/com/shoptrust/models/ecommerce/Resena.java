package com.shoptrust.models.ecommerce;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Entidad Resena para valoraciones y comentarios de productos
 */
@Entity
@Table(name = "resenas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resena")
    private Long idResena;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "rating", nullable = false)
    private Integer rating; // 1-5 estrellas

    @Column(name = "titulo", length = 200)
    private String titulo;

    @Column(name = "comentario", columnDefinition = "TEXT")
    private String comentario;

    @Column(name = "fecha_publicacion", nullable = false, updatable = false)
    private LocalDateTime fechaPublicacion;

    @Column(name = "verificado", nullable = false)
    private Boolean verificado = false;

    @Column(name = "aprobado", nullable = false)
    private Boolean aprobado = true;

    @PrePersist
    protected void onCreate() {
        fechaPublicacion = LocalDateTime.now();
        if (verificado == null) verificado = false;
        if (aprobado == null) aprobado = true;
    }
}
