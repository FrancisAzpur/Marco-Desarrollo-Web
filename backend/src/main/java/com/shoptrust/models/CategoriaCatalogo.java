package com.shoptrust.models;

/**
 * Modelo de Categoría para el catálogo
 */
public class CategoriaCatalogo {
    
    private Long id;
    private String nombre;
    private String descripcion;
    private String icono;
    private String imagen;
    private int cantidadProductos;

    // Constructor vacío
    public CategoriaCatalogo() {
    }

    // Constructor completo
    public CategoriaCatalogo(Long id, String nombre, String descripcion, String icono, 
                    String imagen, int cantidadProductos) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.icono = icono;
        this.imagen = imagen;
        this.cantidadProductos = cantidadProductos;
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

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getCantidadProductos() {
        return cantidadProductos;
    }

    public void setCantidadProductos(int cantidadProductos) {
        this.cantidadProductos = cantidadProductos;
    }
}
