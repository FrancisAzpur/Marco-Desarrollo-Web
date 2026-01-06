# 🛒 SHOPTRUST - Sistema de Gestión de Ventas

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.1-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-8.x-blue)
![Bootstrap](https://img.shields.io/badge/Bootstrap-5.3.2-purple)
![License](https://img.shields.io/badge/License-Educational-yellow)

Sistema completo de gestión de ventas y compras con control automático de inventario, desarrollado con Spring Boot y arquitectura empresarial.

## 📋 Características Principales

- ✅ **Gestión de Productos** - CRUD completo con categorías y control de stock
- ✅ **Control de Inventario** - Actualización automática con ventas y compras
- ✅ **Sistema de Ventas** - Carrito interactivo con cálculo automático de IGV
- ✅ **Sistema de Compras** - Órdenes de compra con gestión de proveedores
- ✅ **Gestión de Clientes** - Registro y seguimiento de clientes
- ✅ **Gestión de Proveedores** - Control de proveedores y compras
- ✅ **Reportes** - Reportes por fechas y totales
- ✅ **Seguridad** - Spring Security con roles Admin/Empleado
- ✅ **Interfaz Moderna** - Bootstrap 5 responsive

## 🚀 Inicio Rápido

### Requisitos Previos

- Java JDK 21
- Maven 3.9+
- MySQL 8.x
- Puerto 8080 disponible

### Instalación en 5 Pasos

1. **Clonar el repositorio**
   ```bash
   cd "d:\MILES\UTP\Marco web\Ventas_1v"
   ```

2. **Configurar MySQL**
   ```sql
   mysql -u root -p < database/01_crear_base_datos.sql
   mysql -u root -p < database/02_insertar_datos_iniciales.sql
   ```

3. **Configurar application.properties**
   ```properties
   spring.datasource.password=TU_PASSWORD_MYSQL
   ```

4. **Compilar y ejecutar**
   ```bash
   cd backend
   mvn spring-boot:run
   ```

5. **Acceder al sistema**
   ```
   http://localhost:8080
   Usuario: admin / Contraseña: admin123
   ```

## 📁 Estructura del Proyecto

```
Ventas_1v/
├── backend/
│   ├── src/main/java/com/shoptrust/
│   │   ├── controllers/          # 7 controladores REST
│   │   ├── services/              # 7 servicios con lógica de negocio
│   │   ├── repositories/          # 9 repositorios JPA
│   │   ├── models/                # 9 entidades
│   │   ├── configuration/         # Configuración de seguridad
│   │   └── utilities/             # Herramientas auxiliares
│   ├── src/main/resources/
│   │   ├── templates/             # 8 vistas Thymeleaf
│   │   ├── static/                # CSS, JS, imágenes
│   │   └── application.properties # Configuración
│   └── pom.xml
├── database/
│   ├── 01_crear_base_datos.sql
│   └── 02_insertar_datos_iniciales.sql
└── INSTRUCCIONES_INSTALACION.txt
```

## 🛠️ Tecnologías Utilizadas

### Backend
- **Spring Boot 3.2.1** - Framework principal
- **Spring Security** - Autenticación y autorización
- **Spring Data JPA** - ORM y persistencia
- **Hibernate** - Implementación JPA
- **MySQL** - Base de datos
- **Lombok** - Reducción de boilerplate

### Frontend
- **Thymeleaf** - Motor de plantillas
- **Bootstrap 5.3.2** - Framework CSS
- **JavaScript ES6** - Interactividad
- **Bootstrap Icons** - Iconografía

## 📊 Módulos del Sistema

| Módulo | Descripción | Funcionalidades |
|--------|-------------|-----------------|
| **Categorías** | Gestión de categorías | CRUD, Activar/Desactivar |
| **Productos** | Gestión de productos | CRUD, Stock, Alertas |
| **Clientes** | Gestión de clientes | CRUD, Documentos únicos |
| **Proveedores** | Gestión de proveedores | CRUD, RUC, Contactos |
| **Ventas** | Proceso de ventas | Carrito, IGV, Descuenta stock |
| **Compras** | Proceso de compras | Orden, IGV, Incrementa stock |
| **Reportes** | Análisis de datos | Por fechas, Totales |
| **Usuarios** | Control de acceso | Roles, Permisos |

## 👥 Usuarios por Defecto

| Usuario | Contraseña | Rol | Permisos |
|---------|-----------|-----|----------|
| admin | admin123 | ADMIN | Acceso total, puede anular |
| empleado | empleado123 | EMPLEADO | Operaciones diarias |

## 🔐 Seguridad

- ✅ Autenticación con Spring Security
- ✅ Contraseñas encriptadas con BCrypt
- ✅ Control de acceso por roles
- ✅ Protección CSRF habilitada
- ✅ Sesiones HTTP seguras
- ✅ Anotaciones @PreAuthorize en métodos críticos

## 📈 Flujo de Ventas

1. Buscar productos en el catálogo
2. Agregar al carrito con cantidades
3. Sistema valida stock disponible
4. Seleccionar cliente (opcional) y método de pago
5. Procesar venta
6. Sistema genera número único (VEN-YYYYMMDD-XXXX)
7. Calcula IGV (18%) automáticamente
8. Descuenta stock de productos
9. Guarda venta y detalles

## 🔄 API REST Endpoints

### Ventas
- `GET /ventas` - Vista HTML
- `GET /ventas/api` - Listar todas
- `POST /ventas/api` - Crear nueva
- `GET /ventas/api/{id}/detalles` - Ver detalles
- `PATCH /ventas/api/{id}/anular` - Anular (Admin)

### Productos
- `GET /productos/api` - Listar todos
- `GET /productos/api/stock-bajo` - Con stock bajo
- `POST /productos/api` - Crear nuevo
- `PUT /productos/api/{id}` - Actualizar

[Ver documentación completa en INSTRUCCIONES_INSTALACION.txt]

## 🧪 Datos de Prueba

El sistema incluye datos precargados:
- 2 usuarios (admin, empleado)
- 5 categorías
- 10 productos de ejemplo
- 3 clientes
- 2 proveedores

## 📝 Características Destacadas

### Control de Stock Automático
```java
// Al vender: descuenta stock
producto.setStockActual(producto.getStockActual() - cantidad);

// Al comprar: incrementa stock
producto.setStockActual(producto.getStockActual() + cantidad);

// Al anular: revierte cambios
```

### Cálculo Automático de IGV
```java
// IGV 18% en Perú
BigDecimal igv = subtotal.multiply(new BigDecimal("0.18"));
BigDecimal total = subtotal.add(igv);
```

### Validaciones Robustas
```java
// Validación de stock
if (producto.getStockActual() < cantidad) {
    throw new RuntimeException("Stock insuficiente");
}

// Validación de documento único
if (existePorDocumento(numeroDocumento)) {
    throw new RuntimeException("Documento ya registrado");
}
```

## 🐛 Solución de Problemas

### Puerto 8080 en uso
```bash
# Detener proceso
Stop-Process -Name "java" -Force

# O cambiar puerto en application.properties
server.port=8081
```

### Error de conexión a BD
```bash
# Verificar servicio MySQL
net start MySQL80

# Verificar credenciales en application.properties
spring.datasource.password=tu_password
```

### Errores de compilación
```bash
# Limpiar y recompilar
mvn clean compile
```

## 📚 Documentación

- **INSTRUCCIONES_INSTALACION.txt** - Manual completo (100+ páginas)
- **Estructura detallada** - Descripción de cada archivo
- **Arquitectura** - Flujos y patrones de diseño
- **FAQ** - Preguntas frecuentes
- **Mejoras futuras** - Roadmap de desarrollo

## 🤝 Contribuciones

Este es un proyecto educativo. Siéntete libre de:
- Reportar bugs
- Sugerir mejoras
- Hacer fork y personalizar
- Usar como base para otros proyectos

## 📄 Licencia

Proyecto educativo de uso libre. Las dependencias tienen sus propias licencias.

## 👨‍💻 Desarrollo

### Ejecutar en modo desarrollo
```bash
mvn spring-boot:run
```

### Generar JAR ejecutable
```bash
mvn clean package
java -jar target/sistema-ventas-1.0.0.jar
```

### Probar APIs
Abrir en navegador: `http://localhost:8080/test-api.html`

## 🌟 Próximas Mejoras

- [ ] Dashboard con gráficos (Chart.js)
- [ ] Reportes en PDF
- [ ] Exportación a Excel
- [ ] Códigos de barras
- [ ] Facturación electrónica SUNAT
- [ ] Sistema de descuentos
- [ ] Multi-almacén
- [ ] App móvil

## 📞 Soporte

Para problemas técnicos:
1. Revisar INSTRUCCIONES_INSTALACION.txt
2. Verificar logs en consola
3. Consultar documentación de Spring Boot

---

**Versión:** 1.0.0  
**Fecha:** Enero 2026  
**Estado:** ✅ Producción Ready

Desarrollado con ❤️ usando Spring Boot
