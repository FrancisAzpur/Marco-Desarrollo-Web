package com.shoptrust.repositories.ecommerce;

import com.shoptrust.models.ecommerce.TicketSoporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository para gestionar operaciones de TicketSoporte
 */
@Repository
public interface TicketSoporteRepository extends JpaRepository<TicketSoporte, Long> {
    
    Optional<TicketSoporte> findByNumeroTicket(String numeroTicket);
    
    List<TicketSoporte> findByUsuarioIdUsuarioOrderByFechaCreacionDesc(Long idUsuario);
    
    List<TicketSoporte> findByEstadoOrderByPrioridadDescFechaCreacionAsc(TicketSoporte.Estado estado);
}
