package com.shoptrust.controllers.ecommerce;

import com.shoptrust.models.ecommerce.Categoria;
import com.shoptrust.models.ecommerce.Producto;
import com.shoptrust.services.ecommerce.CategoriaService;
import com.shoptrust.services.ecommerce.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Controller para el catálogo de productos
 */
@Controller
@RequestMapping("/tienda")
public class TiendaController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    /**
     * Página principal de la tienda
     */
    @GetMapping
    public String inicio(Model model) {
        List<Categoria> categorias = categoriaService.obtenerActivas();
        List<Producto> destacados = productoService.obtenerDestacados();
        List<Producto> nuevos = productoService.obtenerNuevos();
        List<Producto> ofertas = productoService.obtenerEnOferta();

        model.addAttribute("categorias", categorias);
        model.addAttribute("destacados", destacados);
        model.addAttribute("nuevos", nuevos);
        model.addAttribute("ofertas", ofertas);

        return "tienda/inicio";
    }

    /**
     * Catálogo de productos
     */
    @GetMapping("/catalogo")
    public String catalogo(
            @RequestParam(required = false) Long categoria,
            @RequestParam(required = false) Long marca,
            @RequestParam(required = false) String buscar,
            @RequestParam(required = false) BigDecimal precioMin,
            @RequestParam(required = false) BigDecimal precioMax,
            Model model) {

        List<Producto> productos;

        if (buscar != null && !buscar.isEmpty()) {
            productos = productoService.buscarPorNombre(buscar);
        } else if (categoria != null) {
            productos = productoService.obtenerPorCategoria(categoria);
        } else if (marca != null) {
            productos = productoService.obtenerPorMarca(marca);
        } else if (precioMin != null && precioMax != null) {
            productos = productoService.obtenerPorRangoPrecio(precioMin, precioMax);
        } else {
            productos = productoService.obtenerActivos();
        }

        List<Categoria> categorias = categoriaService.obtenerActivas();

        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categorias);
        model.addAttribute("buscar", buscar);
        model.addAttribute("categoriaSeleccionada", categoria);

        return "tienda/catalogo";
    }

    /**
     * Detalle de producto
     */
    @GetMapping("/producto/{slug}")
    public String detalleProducto(@PathVariable String slug, Model model) {
        Optional<Producto> productoOpt = productoService.obtenerPorSlug(slug);

        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            List<Producto> relacionados = productoService.obtenerPorCategoria(
                    producto.getCategoria().getIdCategoria()
            );

            model.addAttribute("producto", producto);
            model.addAttribute("relacionados", relacionados);
            return "tienda/detalle-producto";
        }

        return "redirect:/tienda/catalogo";
    }

    /**
     * Productos por categoría
     */
    @GetMapping("/categoria/{slug}")
    public String productosPorCategoria(@PathVariable String slug, Model model) {
        Optional<Categoria> categoriaOpt = categoriaService.obtenerPorSlug(slug);

        if (categoriaOpt.isPresent()) {
            Categoria categoria = categoriaOpt.get();
            List<Producto> productos = productoService.obtenerPorCategoria(categoria.getIdCategoria());
            List<Categoria> todasCategorias = categoriaService.obtenerActivas();

            model.addAttribute("categoria", categoria);
            model.addAttribute("productos", productos);
            model.addAttribute("categorias", todasCategorias);

            return "tienda/categoria";
        }

        return "redirect:/tienda/catalogo";
    }

    /**
     * Productos en oferta
     */
    @GetMapping("/ofertas")
    public String ofertas(Model model) {
        List<Producto> ofertas = productoService.obtenerEnOferta();
        List<Categoria> categorias = categoriaService.obtenerActivas();

        model.addAttribute("productos", ofertas);
        model.addAttribute("categorias", categorias);

        return "tienda/ofertas";
    }
}
