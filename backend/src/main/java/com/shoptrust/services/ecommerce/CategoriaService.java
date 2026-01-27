package com.shoptrust.services.ecommerce;

import com.shoptrust.models.ecommerce.Categoria;
import com.shoptrust.repositories.ecommerce.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Service para gestionar la lógica de negocio de Categoria
 */
@Service
@Transactional
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> obtenerTodas() {
        return categoriaRepository.findAll();
    }

    public List<Categoria> obtenerActivas() {
        return categoriaRepository.findByActivoTrueOrderByOrdenAsc();
    }

    public Optional<Categoria> obtenerPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    public Optional<Categoria> obtenerPorSlug(String slug) {
        return categoriaRepository.findBySlug(slug);
    }

    public Categoria guardar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public void eliminar(Long id) {
        categoriaRepository.deleteById(id);
    }

    public boolean existePorNombre(String nombre) {
        return categoriaRepository.existsByNombre(nombre);
    }
}
