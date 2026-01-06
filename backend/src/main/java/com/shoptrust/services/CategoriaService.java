package com.shoptrust.services;

import com.shoptrust.models.Categoria;
import com.shoptrust.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestionar operaciones de Categorías
 */
@Service
@Transactional
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    /**
     * Obtener todas las categorías activas
     */
    public List<Categoria> obtenerCategoriasActivas() {
        return categoriaRepository.findByActivoTrue();
    }

    /**
     * Obtener todas las categorías (activas e inactivas)
     */
    public List<Categoria> obtenerTodasCategorias() {
        return categoriaRepository.findAll();
    }

    /**
     * Buscar categoría por ID
     */
    public Optional<Categoria> buscarPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    /**
     * Buscar categorías por nombre
     */
    public List<Categoria> buscarPorNombre(String nombre) {
        return categoriaRepository.findByNombreCategoriaContainingIgnoreCase(nombre);
    }

    /**
     * Guardar o actualizar categoría
     */
    public Categoria guardar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    /**
     * Eliminar (desactivar) categoría
     */
    public void eliminar(Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        categoria.ifPresent(cat -> {
            cat.setActivo(false);
            categoriaRepository.save(cat);
        });
    }

    /**
     * Activar categoría
     */
    public void activar(Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        categoria.ifPresent(cat -> {
            cat.setActivo(true);
            categoriaRepository.save(cat);
        });
    }

    /**
     * Verificar si existe categoría por nombre
     */
    public boolean existePorNombre(String nombre) {
        return categoriaRepository.findByNombreCategoriaIgnoreCase(nombre).isPresent();
    }
}
