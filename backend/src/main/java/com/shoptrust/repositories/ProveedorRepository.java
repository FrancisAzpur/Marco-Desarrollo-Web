package com.shoptrust.repositories;

import com.shoptrust.models.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    Optional<Proveedor> findByRuc(String ruc);
    List<Proveedor> findByActivoTrue();
    List<Proveedor> findByNombreEmpresaContainingIgnoreCase(String nombre);
    Boolean existsByRuc(String ruc);
}
