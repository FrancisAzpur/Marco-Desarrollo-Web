package com.shoptrust.repositories;

import com.shoptrust.models.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
    
    List<DetalleVenta> findByVenta_IdVenta(Long idVenta);
    
    @Query("SELECT dv FROM DetalleVenta dv WHERE dv.producto.idProducto = :idProducto")
    List<DetalleVenta> findByProducto(Long idProducto);
}
