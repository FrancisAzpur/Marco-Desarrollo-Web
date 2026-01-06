package com.shoptrust.services;

import com.shoptrust.models.Cliente;
import com.shoptrust.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestionar operaciones de Clientes
 */
@Service
@Transactional
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Obtener todos los clientes activos
     */
    public List<Cliente> obtenerClientesActivos() {
        return clienteRepository.findByActivoTrue();
    }

    /**
     * Obtener todos los clientes
     */
    public List<Cliente> obtenerTodosClientes() {
        return clienteRepository.findAll();
    }

    /**
     * Buscar cliente por ID
     */
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    /**
     * Buscar cliente por documento de identidad
     */
    public Optional<Cliente> buscarPorDocumento(String documento) {
        return clienteRepository.findByDocumentoIdentidad(documento);
    }

    /**
     * Buscar clientes por nombre
     */
    public List<Cliente> buscarPorNombre(String nombre) {
        return clienteRepository.findByNombreCompletoContainingIgnoreCase(nombre);
    }

    /**
     * Guardar o actualizar cliente
     */
    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    /**
     * Eliminar (desactivar) cliente
     */
    public void eliminar(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        cliente.ifPresent(cli -> {
            cli.setActivo(false);
            clienteRepository.save(cli);
        });
    }

    /**
     * Activar cliente
     */
    public void activar(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        cliente.ifPresent(cli -> {
            cli.setActivo(true);
            clienteRepository.save(cli);
        });
    }

    /**
     * Verificar si existe cliente por documento
     */
    public boolean existePorDocumento(String documento) {
        return clienteRepository.findByDocumentoIdentidad(documento).isPresent();
    }
}
