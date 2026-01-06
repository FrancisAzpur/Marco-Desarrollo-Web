package com.shoptrust.repositories;

import com.shoptrust.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio de Usuario - Interfaz de acceso a datos
 * Extiende JpaRepository para obtener métodos CRUD automáticos
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por su nombre de usuario
     * @param nombreUsuario nombre de usuario a buscar
     * @return Optional con el usuario si existe
     */
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

    /**
     * Busca un usuario por su correo electrónico
     * @param correoElectronico correo electrónico a buscar
     * @return Optional con el usuario si existe
     */
    Optional<Usuario> findByCorreoElectronico(String correoElectronico);

    /**
     * Verifica si existe un usuario con el nombre de usuario dado
     * @param nombreUsuario nombre de usuario a verificar
     * @return true si existe, false si no
     */
    Boolean existsByNombreUsuario(String nombreUsuario);

    /**
     * Verifica si existe un usuario con el correo electrónico dado
     * @param correoElectronico correo electrónico a verificar
     * @return true si existe, false si no
     */
    Boolean existsByCorreoElectronico(String correoElectronico);

    /**
     * Busca un usuario activo por nombre de usuario
     * @param nombreUsuario nombre de usuario a buscar
     * @return Optional con el usuario si existe y está activo
     */
    Optional<Usuario> findByNombreUsuarioAndActivoTrue(String nombreUsuario);
}
