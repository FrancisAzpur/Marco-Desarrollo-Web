package com.shoptrust.repositories.ecommerce;

import com.shoptrust.models.ecommerce.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository para gestionar operaciones de Carrito
 */
@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    
    List<Carrito> findByUsuarioIdUsuario(Long idUsuario);
    
    Optional<Carrito> findByUsuarioIdUsuarioAndProductoIdProducto(Long idUsuario, Long idProducto);
    
    void deleteByUsuarioIdUsuario(Long idUsuario);
    
    @Query("SELECT SUM(c.cantidad * c.precioUnitario) FROM Carrito c WHERE c.usuario.idUsuario = :idUsuario")
    Double calcularTotalCarrito(@Param("idUsuario") Long idUsuario);
    
    long countByUsuarioIdUsuario(Long idUsuario);
}
