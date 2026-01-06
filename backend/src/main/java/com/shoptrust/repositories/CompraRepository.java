package com.shoptrust.repositories;

import com.shoptrust.models.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {
    Optional<Compra> findByNumeroCompra(String numeroCompra);
    List<Compra> findByFechaCompraBetween(LocalDateTime inicio, LocalDateTime fin);
    List<Compra> findByProveedor_IdProveedor(Long idProveedor);
}
