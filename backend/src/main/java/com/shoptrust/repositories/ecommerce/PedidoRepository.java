package com.shoptrust.repositories.ecommerce;

import com.shoptrust.models.ecommerce.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository para gestionar operaciones de Pedido
 */
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    Optional<Pedido> findByNumeroPedido(String numeroPedido);
    
    List<Pedido> findByUsuarioIdUsuarioOrderByFechaPedidoDesc(Long idUsuario);
    
    List<Pedido> findByEstadoOrderByFechaPedidoDesc(Pedido.EstadoPedido estado);
}
