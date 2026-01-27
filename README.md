# 🛒 SHOPTRUST - Sistema de Gestión de Ventas

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.2-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-8.2-blue)
![Bootstrap](https://img.shields.io/badge/Bootstrap-5.3-purple)

Sistema completo de gestión de ventas y compras con control automático de inventario, desarrollado con Spring Boot y arquitectura empresarial.

## 📋 Características Principales

- ✅ **Gestión de Productos** - CRUD completo con categorías y control de stock
- ✅ **Control de Inventario** - Actualización automática con ventas y compras
- ✅ **Sistema de Ventas** - Carrito interactivo con cálculo automático de IGV
- ✅ **Sistema de Compras** - Órdenes de compra con gestión de proveedores
- ✅ **Gestión de Clientes y Proveedores** - Registro completo
- ✅ **Reportes** - Consultas por fechas y totales
- ✅ **Seguridad** - Spring Security con roles Admin/Empleado
- ✅ **Interfaz Moderna** - Bootstrap 5 responsive

## 🚀 Inicio Rápido

### Requisitos Previos

- Java JDK 21
- Maven 3.9+
- MySQL 8.x
- Puerto 8080 disponible

### Instalación

1. **Clonar el repositorio**
   ```bash
   git clone <repository-url>
   cd Marco-Desarrollo-Web
   ```

2. **Configurar MySQL**
   ```bash
   mysql -u root -p < database/01_crear_base_datos.sql
   mysql -u root -p < database/02_insertar_datos_iniciales.sql
   ```

3. **Configurar application.properties** ([backend/src/main/resources/application.properties](backend/src/main/resources/application.properties))
   ```properties
   spring.datasource.password=TU_PASSWORD_MYSQL
   ```

4. **Compilar y ejecutar**
   ```bash
   cd backend
   mvn clean install
   mvn spring-boot:run
   ```

5. **Acceder al sistema**
   ```
   URL: http://localhost:8080
   Usuario: admin / Contraseña: admin123
   ```

## 📁 Estructura del Proyecto

```
Marco-Desarrollo-Web/
├── backend/
│   ├── src/main/java/com/shoptrust/
│   │   ├── controllers/          # Controladores REST
│   │   ├── services/              # Lógica de negocio
│   │   ├── repositories/          # Repositorios JPA
│   │   ├── models/                # Entidades JPA
│   │   ├── configuration/         # Configuración Spring Security
│   │   └── utilities/             # Utilidades
│   ├── src/main/resources/
│   │   ├── templates/             # Vistas Thymeleaf
│   │   ├── static/                # CSS, JavaScript
│   │   └── application.properties
│   └── pom.xml
├── database/                      # Scripts SQL
├── menu/                          # Ejemplo standalone (sin BD)
└── README.md
```

## 🛠️ Tecnologías

### Backend
- **Spring Boot 3.4.2** - Framework principal
- **Spring Security** - Autenticación y autorización
- **Spring Data JPA** - Persistencia
- **MySQL 8.2** - Base de datos
- **Lombok** - Reducción de código boilerplate

### Frontend
- **Thymeleaf** - Motor de plantillas
- **Bootstrap 5.3** - Framework CSS
- **JavaScript ES6** - Interactividad

## 📊 Módulos del Sistema

### Funcionalidades Principales
- **Productos**: CRUD, control de stock, alertas de stock bajo
- **Ventas**: Carrito interactivo, cálculo IGV (18%), descuento automático de stock
- **Compras**: Órdenes de compra, incremento automático de stock
- **Clientes y Proveedores**: Gestión completa con validaciones
- **Categorías**: Organización de productos
- **Reportes**: Consultas por fechas y totales
- **Seguridad**: Control de acceso por roles

## 👥 Usuarios por Defecto

| Usuario | Contraseña | Rol | Permisos |
|---------|-----------|-----|----------|
| admin | admin123 | ADMIN | Acceso total, puede anular operaciones |
| empleado | empleado123 | EMPLEADO | Operaciones diarias |

## 🔐 Seguridad

- Autenticación con Spring Security
- Contraseñas encriptadas con BCrypt
- Control de acceso por roles (@PreAuthorize)
- Protección CSRF habilitada
- Sesiones HTTP seguras

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

## 🧪 Datos de Prueba

El sistema incluye datos precargados:
- 2 usuarios (admin, empleado)
- 5 categorías
- 10 productos de ejemplo
- 3 clientes
- 2 proveedores

## 🐛 Solución de Problemas

**Puerto 8080 en uso:**
```bash
# Windows
Stop-Process -Name "java" -Force
```

**Error de conexión MySQL:**
- Verificar servicio: `net start MySQL80`
- Verificar credenciales en [application.properties](backend/src/main/resources/application.properties)

**Errores de compilación:**
```bash
cd backend
mvn clean install
```

## 📄 Licencia

Proyecto educativo de uso libre.

---

**Versión:** 1.0.0 | **Estado:** ✅ Funcional | Desarrollado con Spring Boot

