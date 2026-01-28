package com.shoptrust.repositories.ecommerce;

import com.shoptrust.models.ecommerce.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository para gestionar operaciones de Categoria
 */
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
    Optional<Categoria> findBySlug(String slug);
    
    List<Categoria> findByActivoTrueOrderByOrdenAsc();
    
    boolean existsByNombre(String nombre);
}
