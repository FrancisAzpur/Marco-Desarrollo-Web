-- ========================================
-- SCRIPT DE CREACIÓN DE BASE DE DATOS
-- SISTEMA DE VENTAS SHOP TRUST
-- ========================================

-- Eliminar base de datos si existe
DROP DATABASE IF EXISTS shoptrust_db;

-- Crear base de datos
CREATE DATABASE shoptrust_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Usar la base de datos
USE shoptrust_db;

-- ========================================
-- TABLA: usuarios
-- Almacena información de usuarios del sistema
-- ========================================
CREATE TABLE usuarios (
    id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario VARCHAR(50) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    nombre_completo VARCHAR(100) NOT NULL,
    correo_electronico VARCHAR(100) NOT NULL UNIQUE,
    rol VARCHAR(20) NOT NULL DEFAULT 'EMPLEADO',
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_nombre_usuario (nombre_usuario),
    INDEX idx_correo (correo_electronico),
    INDEX idx_rol (rol)
) ENGINE=InnoDB;

-- ========================================
-- TABLA: clientes
-- Almacena información de clientes
-- ========================================
CREATE TABLE clientes (
    id_cliente BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_completo VARCHAR(100) NOT NULL,
    documento_identidad VARCHAR(20) UNIQUE,
    tipo_documento VARCHAR(20) DEFAULT 'DNI',
    telefono VARCHAR(20),
    correo_electronico VARCHAR(100),
    direccion VARCHAR(255),
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    INDEX idx_documento (documento_identidad),
    INDEX idx_nombre (nombre_completo)
) ENGINE=InnoDB;

-- ========================================
-- TABLA: categorias
-- Categorías de productos
-- ========================================
CREATE TABLE categorias (
    id_categoria BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_categoria VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(255),
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    
    INDEX idx_nombre (nombre_categoria)
) ENGINE=InnoDB;

-- ========================================
-- TABLA: productos
-- Almacena información de productos
-- ========================================
CREATE TABLE productos (
    id_producto BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo_producto VARCHAR(50) NOT NULL UNIQUE,
    nombre_producto VARCHAR(100) NOT NULL,
    descripcion TEXT,
    id_categoria BIGINT NOT NULL,
    precio_compra DECIMAL(10,2) NOT NULL,
    precio_venta DECIMAL(10,2) NOT NULL,
    stock_actual INT NOT NULL DEFAULT 0,
    stock_minimo INT NOT NULL DEFAULT 5,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria),
    INDEX idx_codigo (codigo_producto),
    INDEX idx_nombre (nombre_producto),
    INDEX idx_categoria (id_categoria)
) ENGINE=InnoDB;

-- ========================================
-- TABLA: ventas
-- Registro de ventas realizadas
-- ========================================
CREATE TABLE ventas (
    id_venta BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_venta VARCHAR(20) NOT NULL UNIQUE,
    id_cliente BIGINT,
    id_usuario BIGINT NOT NULL,
    fecha_venta TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    subtotal DECIMAL(10,2) NOT NULL,
    impuesto DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    total DECIMAL(10,2) NOT NULL,
    metodo_pago VARCHAR(20) NOT NULL DEFAULT 'EFECTIVO',
    estado VARCHAR(20) NOT NULL DEFAULT 'COMPLETADA',
    
    FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    INDEX idx_numero_venta (numero_venta),
    INDEX idx_fecha (fecha_venta),
    INDEX idx_cliente (id_cliente),
    INDEX idx_usuario (id_usuario)
) ENGINE=InnoDB;

-- ========================================
-- TABLA: detalle_ventas
-- Detalle de productos vendidos en cada venta
-- ========================================
CREATE TABLE detalle_ventas (
    id_detalle BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_venta BIGINT NOT NULL,
    id_producto BIGINT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    
    FOREIGN KEY (id_venta) REFERENCES ventas(id_venta) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto),
    INDEX idx_venta (id_venta),
    INDEX idx_producto (id_producto)
) ENGINE=InnoDB;

-- ========================================
-- TABLA: proveedores
-- Información de proveedores de productos
-- ========================================
CREATE TABLE proveedores (
    id_proveedor BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_empresa VARCHAR(100) NOT NULL,
    ruc VARCHAR(11) UNIQUE NOT NULL,
    contacto_nombre VARCHAR(100),
    contacto_telefono VARCHAR(20),
    contacto_email VARCHAR(100),
    direccion VARCHAR(255),
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    INDEX idx_ruc (ruc),
    INDEX idx_nombre (nombre_empresa)
) ENGINE=InnoDB;

-- ========================================
-- TABLA: compras
-- Registro de compras a proveedores
-- ========================================
CREATE TABLE compras (
    id_compra BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_compra VARCHAR(20) NOT NULL UNIQUE,
    id_proveedor BIGINT NOT NULL,
    id_usuario BIGINT NOT NULL,
    fecha_compra TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    subtotal DECIMAL(10,2) NOT NULL,
    impuesto DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    total DECIMAL(10,2) NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'COMPLETADA',
    observaciones TEXT,
    
    FOREIGN KEY (id_proveedor) REFERENCES proveedores(id_proveedor),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    INDEX idx_numero_compra (numero_compra),
    INDEX idx_fecha (fecha_compra),
    INDEX idx_proveedor (id_proveedor)
) ENGINE=InnoDB;

-- ========================================
-- TABLA: detalle_compras
-- Detalle de productos comprados
-- ========================================
CREATE TABLE detalle_compras (
    id_detalle_compra BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_compra BIGINT NOT NULL,
    id_producto BIGINT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    
    FOREIGN KEY (id_compra) REFERENCES compras(id_compra) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto),
    INDEX idx_compra (id_compra),
    INDEX idx_producto (id_producto)
) ENGINE=InnoDB;


-- ========================================
-- CONSULTAS DE VERIFICACIÓN
-- ========================================
SELECT 'Base de datos creada exitosamente' AS mensaje;
SELECT COUNT(*) AS total_usuarios FROM usuarios;
SELECT COUNT(*) AS total_categorias FROM categorias;
SELECT COUNT(*) AS total_productos FROM productos;
SELECT COUNT(*) AS total_clientes FROM clientes;
SELECT COUNT(*) AS total_proveedores FROM proveedores;
SELECT COUNT(*) AS total_ventas FROM ventas;
SELECT COUNT(*) AS total_compras FROM compras;
