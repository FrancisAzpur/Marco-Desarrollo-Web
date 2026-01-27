package com.shoptrust.services.ecommerce;

import com.shoptrust.models.ecommerce.Carrito;
import com.shoptrust.models.ecommerce.Producto;
import com.shoptrust.models.ecommerce.Usuario;
import com.shoptrust.repositories.ecommerce.CarritoRepository;
import com.shoptrust.repositories.ecommerce.ProductoRepository;
import com.shoptrust.repositories.ecommerce.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service para gestionar la lógica de negocio del Carrito
 */
@Service
@Transactional
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Carrito> obtenerCarritoPorUsuario(Long idUsuario) {
        return carritoRepository.findByUsuarioIdUsuario(idUsuario);
    }

    public Carrito agregarAlCarrito(Long idUsuario, Long idProducto, Integer cantidad) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);
        Optional<Producto> productoOpt = productoRepository.findById(idProducto);

        if (usuarioOpt.isPresent() && productoOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            Producto producto = productoOpt.get();

            // Verificar si ya existe en el carrito
            Optional<Carrito> carritoExistente = carritoRepository
                    .findByUsuarioIdUsuarioAndProductoIdProducto(idUsuario, idProducto);

            if (carritoExistente.isPresent()) {
                // Actualizar cantidad
                Carrito carrito = carritoExistente.get();
                carrito.setCantidad(carrito.getCantidad() + cantidad);
                return carritoRepository.save(carrito);
            } else {
                // Crear nuevo item
                Carrito nuevoCarrito = new Carrito();
                nuevoCarrito.setUsuario(usuario);
                nuevoCarrito.setProducto(producto);
                nuevoCarrito.setCantidad(cantidad);
                nuevoCarrito.setPrecioUnitario(producto.getPrecioEfectivo());
                return carritoRepository.save(nuevoCarrito);
            }
        }
        return null;
    }

    public boolean actualizarCantidad(Long idCarrito, Integer cantidad) {
        Optional<Carrito> carritoOpt = carritoRepository.findById(idCarrito);
        if (carritoOpt.isPresent()) {
            Carrito carrito = carritoOpt.get();
            carrito.setCantidad(cantidad);
            carritoRepository.save(carrito);
            return true;
        }
        return false;
    }

    public void eliminarItem(Long idCarrito) {
        carritoRepository.deleteById(idCarrito);
    }

    public void limpiarCarrito(Long idUsuario) {
        carritoRepository.deleteByUsuarioIdUsuario(idUsuario);
    }

    public BigDecimal calcularTotal(Long idUsuario) {
        List<Carrito> items = carritoRepository.findByUsuarioIdUsuario(idUsuario);
        return items.stream()
                .map(Carrito::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public long contarItems(Long idUsuario) {
        return carritoRepository.countByUsuarioIdUsuario(idUsuario);
    }
}
