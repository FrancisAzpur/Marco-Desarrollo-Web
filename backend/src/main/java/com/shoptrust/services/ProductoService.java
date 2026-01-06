package com.shoptrust.services;

import com.shoptrust.models.Producto;
import com.shoptrust.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestionar operaciones de Productos
 */
@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    /**
     * Obtener todos los productos activos
     */
    public List<Producto> obtenerProductosActivos() {
        return productoRepository.findByActivoTrue();
    }

    /**
     * Obtener todos los productos
     */
    public List<Producto> obtenerTodosProductos() {
        return productoRepository.findAll();
    }

    /**
     * Buscar producto por ID
     */
    public Optional<Producto> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }

    /**
     * Buscar producto por código
     */
    public Optional<Producto> buscarPorCodigo(String codigo) {
        return productoRepository.findByCodigoProducto(codigo);
    }

    /**
     * Buscar productos por nombre
     */
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreProductoContainingIgnoreCase(nombre);
    }

    /**
     * Buscar productos por categoría
     */
    public List<Producto> buscarPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoriaIdCategoria(categoriaId);
    }

    /**
     * Obtener productos con stock bajo
     */
    public List<Producto> obtenerProductosStockBajo() {
        return productoRepository.findProductosConStockBajo();
    }

    /**
     * Guardar o actualizar producto
     */
    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    /**
     * Eliminar (desactivar) producto
     */
    public void eliminar(Long id) {
        Optional<Producto> producto = productoRepository.findById(id);
        producto.ifPresent(prod -> {
            prod.setActivo(false);
            productoRepository.save(prod);
        });
    }

    /**
     * Actualizar stock del producto
     */
    public void actualizarStock(Long id, Integer cantidad) {
        Optional<Producto> producto = productoRepository.findById(id);
        producto.ifPresent(prod -> {
            prod.setStockActual(cantidad);
            productoRepository.save(prod);
        });
    }

    /**
     * Verificar si existe producto por código
     */
    public boolean existePorCodigo(String codigo) {
        return productoRepository.findByCodigoProducto(codigo).isPresent();
    }
}
