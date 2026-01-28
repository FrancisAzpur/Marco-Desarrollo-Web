package com.shoptrust.models.ecommerce;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Entidad TicketSoporte para sistema de soporte técnico
 */
@Entity
@Table(name = "tickets_soporte")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketSoporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ticket")
    private Long idTicket;

    @Column(name = "numero_ticket", nullable = false, unique = true, length = 50)
    private String numeroTicket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @Column(name = "asunto", nullable = false, length = 200)
    private String asunto;

    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "prioridad", nullable = false, length = 10)
    private Prioridad prioridad = Prioridad.MEDIA;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private Estado estado = Estado.ABIERTO;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column(name = "fecha_cierre")
    private LocalDateTime fechaCierre;

    // Relaciones
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<RespuestaTicket> respuestas;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        if (prioridad == null) prioridad = Prioridad.MEDIA;
        if (estado == null) estado = Estado.ABIERTO;
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }

    public enum Prioridad {
        BAJA,
        MEDIA,
        ALTA,
        URGENTE
    }

    public enum Estado {
        ABIERTO,
        EN_PROCESO,
        ESPERANDO_RESPUESTA,
        RESUELTO,
        CERRADO
    }
}
