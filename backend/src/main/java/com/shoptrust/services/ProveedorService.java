package com.shoptrust.services;

import com.shoptrust.models.Proveedor;
import com.shoptrust.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestionar operaciones de Proveedores
 */
@Service
@Transactional
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    /**
     * Obtener todos los proveedores activos
     */
    public List<Proveedor> obtenerProveedoresActivos() {
        return proveedorRepository.findByActivoTrue();
    }

    /**
     * Obtener todos los proveedores
     */
    public List<Proveedor> obtenerTodosProveedores() {
        return proveedorRepository.findAll();
    }

    /**
     * Buscar proveedor por ID
     */
    public Optional<Proveedor> buscarPorId(Long id) {
        return proveedorRepository.findById(id);
    }

    /**
     * Buscar proveedor por RUC
     */
    public Optional<Proveedor> buscarPorRuc(String ruc) {
        return proveedorRepository.findByRuc(ruc);
    }

    /**
     * Buscar proveedores por nombre de empresa
     */
    public List<Proveedor> buscarPorNombreEmpresa(String nombre) {
        return proveedorRepository.findByNombreEmpresaContainingIgnoreCase(nombre);
    }

    /**
     * Guardar o actualizar proveedor
     */
    public Proveedor guardar(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    /**
     * Eliminar (desactivar) proveedor
     */
    public void eliminar(Long id) {
        Optional<Proveedor> proveedor = proveedorRepository.findById(id);
        proveedor.ifPresent(prov -> {
            prov.setActivo(false);
            proveedorRepository.save(prov);
        });
    }

    /**
     * Activar proveedor
     */
    public void activar(Long id) {
        Optional<Proveedor> proveedor = proveedorRepository.findById(id);
        proveedor.ifPresent(prov -> {
            prov.setActivo(true);
            proveedorRepository.save(prov);
        });
    }

    /**
     * Verificar si existe proveedor por RUC
     */
    public boolean existePorRuc(String ruc) {
        return proveedorRepository.findByRuc(ruc).isPresent();
    }
}
