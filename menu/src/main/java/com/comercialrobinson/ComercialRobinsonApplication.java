package com.comercialrobinson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Aplicación principal de Comercial Robinson (Módulo de Demostración)
 * Este es un ejemplo standalone sin conexión a base de datos
 */
@SpringBootApplication
public class ComercialRobinsonApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComercialRobinsonApplication.class, args);
        System.out.println("\n" +
                "===========================================\n" +
                "  COMERCIAL ROBINSON - Sistema Iniciado   \n" +
                "  URL: http://localhost:8090              \n" +
                "  Modo: Demo (Sin Base de Datos)          \n" +
                "===========================================\n");
    }
}
