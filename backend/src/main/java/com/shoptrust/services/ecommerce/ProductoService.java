package com.shoptrust.services.ecommerce;

import com.shoptrust.models.ecommerce.Producto;
import com.shoptrust.repositories.ecommerce.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service para gestionar la lógica de negocio de Producto
 */
@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public List<Producto> obtenerActivos() {
        return productoRepository.findByActivoTrue();
    }

    public List<Producto> obtenerDestacados() {
        return productoRepository.findByDestacadoTrueAndActivoTrue();
    }

    public List<Producto> obtenerNuevos() {
        return productoRepository.findByNuevoTrueAndActivoTrue();
    }

    public List<Producto> obtenerEnOferta() {
        return productoRepository.findProductosEnOferta();
    }

    public List<Producto> obtenerPorCategoria(Long idCategoria) {
        return productoRepository.findByCategoriaIdCategoriaAndActivoTrue(idCategoria);
    }

    public List<Producto> obtenerPorMarca(Long idMarca) {
        return productoRepository.findByMarcaIdMarcaAndActivoTrue(idMarca);
    }

    public List<Producto> buscarPorNombre(String keyword) {
        return productoRepository.buscarPorNombre(keyword);
    }

    public List<Producto> obtenerPorRangoPrecio(BigDecimal min, BigDecimal max) {
        return productoRepository.findByRangoPrecio(min, max);
    }

    public Optional<Producto> obtenerPorId(Long id) {
        return productoRepository.findById(id);
    }

    public Optional<Producto> obtenerPorSlug(String slug) {
        return productoRepository.findBySlug(slug);
    }

    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }

    public boolean actualizarStock(Long idProducto, Integer cantidad) {
        Optional<Producto> productoOpt = productoRepository.findById(idProducto);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            if (producto.getStock() >= cantidad) {
                producto.setStock(producto.getStock() - cantidad);
                productoRepository.save(producto);
                return true;
            }
        }
        return false;
    }
}
