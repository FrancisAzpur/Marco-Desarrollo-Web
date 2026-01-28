package com.shoptrust.models.ecommerce;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Entidad RespuestaTicket para respuestas en tickets de soporte
 */
@Entity
@Table(name = "respuestas_ticket")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_respuesta")
    private Long idRespuesta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ticket", nullable = false)
    private TicketSoporte ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "respuesta", nullable = false, columnDefinition = "TEXT")
    private String respuesta;

    @Column(name = "es_staff", nullable = false)
    private Boolean esStaff = false;

    @Column(name = "fecha_respuesta", nullable = false, updatable = false)
    private LocalDateTime fechaRespuesta;

    @PrePersist
    protected void onCreate() {
        fechaRespuesta = LocalDateTime.now();
        if (esStaff == null) esStaff = false;
    }
}
