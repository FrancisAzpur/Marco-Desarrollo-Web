package com.shoptrust.models;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Modelo de Producto para el catálogo de Comercial Robinson
 */
public class ProductoCatalogo {
    
    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private String imagen;
    private String categoria;
    private boolean destacado;
    private boolean enOferta;
    private BigDecimal precioAnterior;
    private int stock;

    // Constructor vacío
    public ProductoCatalogo() {
    }

    // Constructor completo
    public ProductoCatalogo(Long id, String nombre, String descripcion, BigDecimal precio, 
                   String imagen, String categoria, boolean destacado, 
                   boolean enOferta, BigDecimal precioAnterior, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
        this.categoria = categoria;
        this.destacado = destacado;
        this.enOferta = enOferta;
        this.precioAnterior = precioAnterior;
        this.stock = stock;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public boolean isDestacado() {
        return destacado;
    }

    public void setDestacado(boolean destacado) {
        this.destacado = destacado;
    }

    public boolean isEnOferta() {
        return enOferta;
    }

    public void setEnOferta(boolean enOferta) {
        this.enOferta = enOferta;
    }

    public BigDecimal getPrecioAnterior() {
        return precioAnterior;
    }

    public void setPrecioAnterior(BigDecimal precioAnterior) {
        this.precioAnterior = precioAnterior;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    // Método para calcular porcentaje de descuento
    public int getPorcentajeDescuento() {
        if (enOferta && precioAnterior != null && precioAnterior.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal descuento = precioAnterior.subtract(precio);
            BigDecimal porcentaje = descuento.divide(precioAnterior, 2, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal(100));
            return porcentaje.intValue();
        }
        return 0;
    }
}
