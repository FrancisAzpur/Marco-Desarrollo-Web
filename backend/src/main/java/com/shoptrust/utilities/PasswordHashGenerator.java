package com.shoptrust.utilities;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Utilidad para generar hash de contraseñas BCrypt
 * Ejecuta este main para generar hashes
 */
public class PasswordHashGenerator {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // Generar hash para admin123
        String passwordAdmin = "admin123";
        String hashAdmin = encoder.encode(passwordAdmin);
        System.out.println("Hash para 'admin123': " + hashAdmin);
        
        // Generar hash para empleado123
        String passwordEmpleado = "empleado123";
        String hashEmpleado = encoder.encode(passwordEmpleado);
        System.out.println("Hash para 'empleado123': " + hashEmpleado);
        
        // Verificar que funciona
        System.out.println("\nVerificación:");
        System.out.println("admin123 matches: " + encoder.matches(passwordAdmin, hashAdmin));
        System.out.println("empleado123 matches: " + encoder.matches(passwordEmpleado, hashEmpleado));
    }
}
