package com.shoptrust.controllers;

import com.shoptrust.models.ProductoCatalogo;
import com.shoptrust.models.CategoriaCatalogo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador para el catálogo de productos (Comercial Robinson)
 */
@Controller
@RequestMapping("/catalogo")
public class CatalogoController {

    private static final String ATTR_TITULO = "titulo";
    private static final String ATTR_CATEGORIAS = "categorias";
    private static final String ATTR_PRODUCTOS = "productos";
    private static final String CAT_ELECTRODOMESTICOS = "Electrodomésticos";
    private static final String CAT_HOGAR = "Hogar";

    /**
     * Página principal del catálogo
     */
    @GetMapping({"", "/", "/inicio"})
    public String inicio(Model model) {
        model.addAttribute(ATTR_TITULO, "Catálogo - Inicio");
        model.addAttribute(ATTR_CATEGORIAS, obtenerCategorias());
        model.addAttribute("productosDestacados", obtenerProductosDestacados());
        return "catalogo/index";
    }

    /**
     * Página de electrodomésticos
     */
    @GetMapping("/electrodomesticos")
    public String electrodomesticos(Model model) {
        model.addAttribute(ATTR_TITULO, CAT_ELECTRODOMESTICOS);
        model.addAttribute(ATTR_PRODUCTOS, obtenerProductosPorCategoria(CAT_ELECTRODOMESTICOS));
        return "catalogo/electrodomesticos";
    }

    /**
     * Página de productos para el hogar
     */
    @GetMapping("/hogar")
    public String hogar(Model model) {
        model.addAttribute(ATTR_TITULO, CAT_HOGAR);
        model.addAttribute(ATTR_PRODUCTOS, obtenerProductosPorCategoria(CAT_HOGAR));
        return "catalogo/hogar";
    }

    /**
     * Página de ofertas
     */
    @GetMapping("/ofertas")
    public String ofertas(Model model) {
        model.addAttribute(ATTR_TITULO, "Ofertas Especiales");
        model.addAttribute(ATTR_PRODUCTOS, obtenerProductosEnOferta());
        return "catalogo/ofertas";
    }

    /**
     * Página de contacto
     */
    @GetMapping("/contacto")
    public String contacto(Model model) {
        model.addAttribute(ATTR_TITULO, "Contacto");
        return "catalogo/contacto";
    }

    /**
     * Página de categorías
     */
    @GetMapping("/categorias")
    public String categorias(Model model) {
        model.addAttribute(ATTR_TITULO, "Categorías");
        model.addAttribute(ATTR_CATEGORIAS, obtenerCategorias());
        return "catalogo/categorias";
    }

    /**
     * Detalle de producto
     */
    @GetMapping("/producto/{id}")
    public String detalleProducto(@PathVariable Long id, Model model) {
        ProductoCatalogo producto = obtenerProductoPorId(id);
        if (producto != null) {
            model.addAttribute(ATTR_TITULO, producto.getNombre());
            model.addAttribute("producto", producto);
            return "catalogo/detalle-producto";
        }
        return "redirect:/catalogo";
    }

    // ============ MÉTODOS AUXILIARES (simulan base de datos) ============

    private List<CategoriaCatalogo> obtenerCategorias() {
        List<CategoriaCatalogo> categorias = new ArrayList<>();
        categorias.add(new CategoriaCatalogo(1L, CAT_ELECTRODOMESTICOS, "Todo para tu cocina y hogar", 
                "fa-blender", "SmartTv.jpeg", 25));
        categorias.add(new CategoriaCatalogo(2L, CAT_HOGAR, "Decoración y confort", 
                "fa-couch", "Moderno.jpg", 30));
        categorias.add(new CategoriaCatalogo(3L, "Refrigeración", "Refrigeradoras y congeladores", 
                "fa-snowflake", "Refri1.jpg", 15));
        categorias.add(new CategoriaCatalogo(4L, "Lavado", "Lavadoras y secadoras", 
                "fa-soap", "Lavadora1.jpg", 12));
        return categorias;
    }

    private List<ProductoCatalogo> obtenerTodosLosProductos() {
        List<ProductoCatalogo> productos = new ArrayList<>();
        
        // Electrodomésticos - TVs
        productos.add(new ProductoCatalogo(1L, "Smart TV OLED 55\"", 
                "Televisor OLED 4K con HDR10+ y Dolby Vision. Sistema operativo Android TV con Google Assistant.",
                new BigDecimal("2499.00"), "tv_oled_55.avif", CAT_ELECTRODOMESTICOS, true, 
                true, new BigDecimal("2999.00"), 10));
        
        productos.add(new ProductoCatalogo(2L, "Smart TV LED 43\"", 
                "Televisor LED Full HD con WiFi integrado y aplicaciones pre-instaladas.",
                new BigDecimal("899.00"), "smart_tv_2.webp", CAT_ELECTRODOMESTICOS, true, 
                false, null, 15));
        
        productos.add(new ProductoCatalogo(3L, "Smart TV 50\" 4K", 
                "Pantalla 4K Ultra HD con tecnología HDR. Conexión WiFi y Bluetooth.",
                new BigDecimal("1299.00"), "SmartTv.jpeg", CAT_ELECTRODOMESTICOS, false, 
                true, new BigDecimal("1599.00"), 8));

        // Refrigeración
        productos.add(new ProductoCatalogo(4L, "Refrigeradora Inverter 420L", 
                "Refrigeradora No Frost con tecnología Inverter. Eficiencia energética A+++",
                new BigDecimal("1899.00"), "Refri1.jpg", CAT_ELECTRODOMESTICOS, true, 
                false, null, 12));
        
        productos.add(new ProductoCatalogo(5L, "Refrigeradora Side by Side", 
                "Diseño moderno con dispensador de agua y hielo. Capacidad 600L",
                new BigDecimal("3499.00"), "refrigerador.webp", CAT_ELECTRODOMESTICOS, true, 
                true, new BigDecimal("3999.00"), 5));
        
        productos.add(new ProductoCatalogo(6L, "Refrigeradora Compacta", 
                "Perfecta para espacios pequeños. Eficiente y silenciosa.",
                new BigDecimal("799.00"), "refrigeradora_2.jpg", CAT_ELECTRODOMESTICOS, false, 
                false, null, 20));

        // Lavado
        productos.add(new ProductoCatalogo(7L, "Lavadora Inverter 16kg", 
                "Lavadora de carga frontal con tecnología Inverter Direct Drive",
                new BigDecimal("1599.00"), "Lavadora1.jpg", CAT_ELECTRODOMESTICOS, true, 
                true, new BigDecimal("1999.00"), 7));
        
        productos.add(new ProductoCatalogo(8L, "Lavadora Automática 10kg", 
                "Carga superior con 12 programas de lavado. Eficiencia energética A+",
                new BigDecimal("999.00"), "lavadora_2.webp", CAT_ELECTRODOMESTICOS, false, 
                false, null, 15));
        
        productos.add(new ProductoCatalogo(9L, "Lavadora-Secadora Combo", 
                "Lavadora y secadora en un solo equipo. Capacidad 9kg/6kg",
                new BigDecimal("2299.00"), "lavadora_inverter.webp", CAT_ELECTRODOMESTICOS, true, 
                true, new BigDecimal("2699.00"), 6));

        // Hogar
        productos.add(new ProductoCatalogo(10L, "Juego de Sala Moderno", 
                "Sala de 3 piezas tapizado en tela de alta calidad. Diseño contemporáneo",
                new BigDecimal("2999.00"), "Moderno.jpg", CAT_HOGAR, true, 
                false, null, 8));

        return productos;
    }

    private List<ProductoCatalogo> obtenerProductosDestacados() {
        return obtenerTodosLosProductos().stream()
                .filter(ProductoCatalogo::isDestacado)
                .limit(6)
                .collect(Collectors.toList());
    }

    private List<ProductoCatalogo> obtenerProductosPorCategoria(String categoria) {
        return obtenerTodosLosProductos().stream()
                .filter(p -> p.getCategoria().equals(categoria))
                .collect(Collectors.toList());
    }

    private List<ProductoCatalogo> obtenerProductosEnOferta() {
        return obtenerTodosLosProductos().stream()
                .filter(ProductoCatalogo::isEnOferta)
                .collect(Collectors.toList());
    }

    private ProductoCatalogo obtenerProductoPorId(Long id) {
        return obtenerTodosLosProductos().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
