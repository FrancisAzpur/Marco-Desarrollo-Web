package com.shoptrust.repositories;

import com.shoptrust.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByActivoTrue();
    List<Categoria> findByNombreCategoriaContainingIgnoreCase(String nombre);
    Optional<Categoria> findByNombreCategoriaIgnoreCase(String nombre);
}
