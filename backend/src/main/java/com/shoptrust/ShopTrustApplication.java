package com.shoptrust;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Shop Trust
 * Sistema de Ventas
 * 
 * @SpringBootApplication incluye:
 * - @Configuration: Define beans de configuración
 * - @EnableAutoConfiguration: Habilita configuración automática
 * - @ComponentScan: Escanea componentes en el paquete com.shoptrust
 */
@SpringBootApplication
public class ShopTrustApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopTrustApplication.class, args);
        System.out.println("==============================================");
        System.out.println("  SHOP TRUST - SISTEMA DE VENTAS INICIADO");
        System.out.println("  Servidor corriendo en: http://localhost:8080");
        System.out.println("==============================================");
    }
}
