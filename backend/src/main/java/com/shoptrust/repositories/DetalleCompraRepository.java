package com.shoptrust.repositories;

import com.shoptrust.models.DetalleCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleCompraRepository extends JpaRepository<DetalleCompra, Long> {
    
    List<DetalleCompra> findByCompra_IdCompra(Long idCompra);
    
    @Query("SELECT dc FROM DetalleCompra dc WHERE dc.producto.idProducto = :idProducto")
    List<DetalleCompra> findByProducto(Long idProducto);
}
