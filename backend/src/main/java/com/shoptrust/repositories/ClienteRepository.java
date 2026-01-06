package com.shoptrust.repositories;

import com.shoptrust.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByDocumentoIdentidad(String documentoIdentidad);
    List<Cliente> findByActivoTrue();
    List<Cliente> findByNombreCompletoContainingIgnoreCase(String nombre);
    Boolean existsByDocumentoIdentidad(String documentoIdentidad);
}
