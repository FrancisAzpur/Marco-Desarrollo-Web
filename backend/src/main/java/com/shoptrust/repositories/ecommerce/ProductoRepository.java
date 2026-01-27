package com.shoptrust.repositories.ecommerce;

import com.shoptrust.models.ecommerce.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repository para gestionar operaciones de Producto
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    Optional<Producto> findBySku(String sku);
    
    Optional<Producto> findBySlug(String slug);
    
    List<Producto> findByActivoTrue();
    
    List<Producto> findByDestacadoTrueAndActivoTrue();
    
    List<Producto> findByNuevoTrueAndActivoTrue();
    
    List<Producto> findByCategoriaIdCategoriaAndActivoTrue(Long idCategoria);
    
    List<Producto> findByMarcaIdMarcaAndActivoTrue(Long idMarca);
    
    @Query("SELECT p FROM Producto p WHERE p.activo = true AND p.nombre LIKE %:keyword%")
    List<Producto> buscarPorNombre(@Param("keyword") String keyword);
    
    @Query("SELECT p FROM Producto p WHERE p.activo = true AND p.precioOferta IS NOT NULL AND p.precioOferta < p.precio")
    List<Producto> findProductosEnOferta();
    
    @Query("SELECT p FROM Producto p WHERE p.activo = true AND p.precio BETWEEN :precioMin AND :precioMax")
    List<Producto> findByRangoPrecio(@Param("precioMin") BigDecimal precioMin, @Param("precioMax") BigDecimal precioMax);
}
