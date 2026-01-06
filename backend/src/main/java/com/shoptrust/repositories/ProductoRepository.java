package com.shoptrust.repositories;

import com.shoptrust.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findByCodigoProducto(String codigoProducto);
    List<Producto> findByActivoTrue();
    List<Producto> findByCategoria_IdCategoria(Long idCategoria);
    List<Producto> findByCategoriaIdCategoria(Long categoriaId);
    List<Producto> findByNombreProductoContainingIgnoreCase(String nombre);
    Boolean existsByCodigoProducto(String codigoProducto);
    
    @org.springframework.data.jpa.repository.Query("SELECT p FROM Producto p WHERE p.stockActual <= p.stockMinimo AND p.activo = true")
    List<Producto> findProductosConStockBajo();
}
