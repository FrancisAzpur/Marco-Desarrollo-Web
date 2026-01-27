-- ========================================
-- SCRIPT DE CREACIÓN DE BASE DE DATOS
-- TIENDA E-COMMERCE ELECTRODOMÉSTICOS
-- Sistema completo de compras online
-- ========================================

DROP DATABASE IF EXISTS tienda_electrodomesticos;
CREATE DATABASE tienda_electrodomesticos CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE tienda_electrodomesticos;

-- ========================================
-- TABLA: usuarios
-- Clientes registrados en la plataforma
-- ========================================
CREATE TABLE usuarios (
    id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    telefono VARCHAR(20),
    fecha_nacimiento DATE,
    activo BOOLEAN DEFAULT TRUE,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_email (email),
    INDEX idx_nombre (nombre, apellido)
) ENGINE=InnoDB;

-- ========================================
-- TABLA: direcciones
-- Direcciones de envío de usuarios
-- ========================================
CREATE TABLE direcciones (
    id_direccion BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_usuario BIGINT NOT NULL,
    nombre_destinatario VARCHAR(100) NOT NULL,
    direccion_linea1 VARCHAR(255) NOT NULL,
    direccion_linea2 VARCHAR(255),
    ciudad VARCHAR(100) NOT NULL,
    departamento VARCHAR(100) NOT NULL,
    codigo_postal VARCHAR(20),
    telefono_contacto VARCHAR(20) NOT NULL,
    es_principal BOOLEAN DEFAULT FALSE,
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    INDEX idx_usuario (id_usuario)
) ENGINE=InnoDB;

-- ========================================
-- TABLA: categorias
-- Categorías de productos
-- ========================================
CREATE TABLE categorias (
    id_categoria BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion TEXT,
    imagen VARCHAR(255),
    icono VARCHAR(50),
    slug VARCHAR(100) NOT NULL UNIQUE,
    activo BOOLEAN DEFAULT TRUE,
    orden INT DEFAULT 0,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    INDEX idx_slug (slug),
    INDEX idx_activo (activo)
) ENGINE=InnoDB;

-- ========================================
-- TABLA: marcas
-- Marcas de electrodomésticos
-- ========================================
CREATE TABLE marcas (
    id_marca BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion TEXT,
    logo VARCHAR(255),
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    INDEX idx_nombre (nombre)
) ENGINE=InnoDB;

-- ========================================
-- TABLA: productos
-- Catálogo de electrodomésticos
-- ========================================
CREATE TABLE productos (
    id_producto BIGINT AUTO_INCREMENT PRIMARY KEY,
    sku VARCHAR(50) NOT NULL UNIQUE,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    descripcion_corta VARCHAR(500),
    id_categoria BIGINT NOT NULL,
    id_marca BIGINT,
    precio DECIMAL(10,2) NOT NULL,
    precio_anterior DECIMAL(10,2),
    en_oferta BOOLEAN DEFAULT FALSE,
    porcentaje_descuento INT DEFAULT 0,
    stock INT NOT NULL DEFAULT 0,
    imagen_principal VARCHAR(255),
    destacado BOOLEAN DEFAULT FALSE,
    nuevo BOOLEAN DEFAULT FALSE,
    calificacion_promedio DECIMAL(3,2) DEFAULT 0,
    total_resenas INT DEFAULT 0,
    vistas INT DEFAULT 0,
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria),
    FOREIGN KEY (id_marca) REFERENCES marcas(id_marca),
    INDEX idx_sku (sku),
    INDEX idx_nombre (nombre),
    INDEX idx_categoria (id_categoria),
    INDEX idx_marca (id_marca),
    INDEX idx_destacado (destacado),
    INDEX idx_oferta (en_oferta),
    INDEX idx_activo (activo)
) ENGINE=InnoDB;

-- ========================================
-- TABLA: imagenes_producto
-- Galería de imágenes de productos
-- ========================================
CREATE TABLE imagenes_producto (
    id_imagen BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_producto BIGINT NOT NULL,
    url_imagen VARCHAR(255) NOT NULL,
    orden INT DEFAULT 0,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto) ON DELETE CASCADE,
    INDEX idx_producto (id_producto)
) ENGINE=InnoDB;

-- ========================================
-- TABLA: especificaciones_producto
-- Características técnicas
-- ========================================
CREATE TABLE especificaciones_producto (
    id_especificacion BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_producto BIGINT NOT NULL,
    nombre_especificacion VARCHAR(100) NOT NULL,
    valor_especificacion VARCHAR(255) NOT NULL,
    orden INT DEFAULT 0,
    
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto) ON DELETE CASCADE,
    INDEX idx_producto (id_producto)
) ENGINE=InnoDB;

-- ========================================
-- TABLA: resenas
-- Reseñas y calificaciones
-- ========================================
CREATE TABLE resenas (
    id_resena BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_producto BIGINT NOT NULL,
    id_usuario BIGINT NOT NULL,
    calificacion INT NOT NULL CHECK (calificacion BETWEEN 1 AND 5),
    titulo VARCHAR(200),
    comentario TEXT,
    verificado BOOLEAN DEFAULT FALSE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto) ON DELETE CASCADE,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    INDEX idx_producto (id_producto),
    INDEX idx_usuario (id_usuario),
    UNIQUE KEY unique_resena (id_producto, id_usuario)
) ENGINE=InnoDB;

-- ========================================
-- TABLA: pedidos
-- Órdenes de compra
-- ========================================
CREATE TABLE pedidos (
    id_pedido BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_pedido VARCHAR(50) NOT NULL UNIQUE,
    id_usuario BIGINT NOT NULL,
    id_direccion BIGINT NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    descuento DECIMAL(10,2) DEFAULT 0,
    costo_envio DECIMAL(10,2) DEFAULT 0,
    total DECIMAL(10,2) NOT NULL,
    estado VARCHAR(50) NOT NULL DEFAULT 'PENDIENTE',
    metodo_pago VARCHAR(50) NOT NULL,
    estado_pago VARCHAR(50) NOT NULL DEFAULT 'PENDIENTE',
    notas TEXT,
    fecha_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_direccion) REFERENCES direcciones(id_direccion),
    INDEX idx_numero_pedido (numero_pedido),
    INDEX idx_usuario (id_usuario),
    INDEX idx_estado (estado),
    INDEX idx_fecha (fecha_pedido)
) ENGINE=InnoDB;

-- ========================================
-- TABLA: detalle_pedido
-- Items de cada pedido
-- ========================================
CREATE TABLE detalle_pedido (
    id_detalle BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_pedido BIGINT NOT NULL,
    id_producto BIGINT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    
    FOREIGN KEY (id_pedido) REFERENCES pedidos(id_pedido) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto),
    INDEX idx_pedido (id_pedido),
    INDEX idx_producto (id_producto)
) ENGINE=InnoDB;

-- ========================================
-- TABLA: carrito
-- Carrito de compras temporal
-- ========================================
CREATE TABLE carrito (
    id_carrito BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_usuario BIGINT NOT NULL,
    id_producto BIGINT NOT NULL,
    cantidad INT NOT NULL DEFAULT 1,
    fecha_agregado TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto) ON DELETE CASCADE,
    UNIQUE KEY unique_carrito (id_usuario, id_producto),
    INDEX idx_usuario (id_usuario)
) ENGINE=InnoDB;

-- ========================================
-- TABLA: favoritos
-- Productos favoritos de usuarios
-- ========================================
CREATE TABLE favoritos (
    id_favorito BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_usuario BIGINT NOT NULL,
    id_producto BIGINT NOT NULL,
    fecha_agregado TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto) ON DELETE CASCADE,
    UNIQUE KEY unique_favorito (id_usuario, id_producto),
    INDEX idx_usuario (id_usuario)
) ENGINE=InnoDB;

-- ========================================
-- TABLA: tickets_soporte
-- Sistema de soporte técnico
-- ========================================
CREATE TABLE tickets_soporte (
    id_ticket BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_ticket VARCHAR(50) NOT NULL UNIQUE,
    id_usuario BIGINT NOT NULL,
    asunto VARCHAR(255) NOT NULL,
    descripcion TEXT NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    prioridad VARCHAR(20) NOT NULL DEFAULT 'MEDIA',
    estado VARCHAR(30) NOT NULL DEFAULT 'ABIERTO',
    id_pedido BIGINT,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    fecha_cierre TIMESTAMP NULL,
    
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_pedido) REFERENCES pedidos(id_pedido),
    INDEX idx_numero_ticket (numero_ticket),
    INDEX idx_usuario (id_usuario),
    INDEX idx_estado (estado),
    INDEX idx_fecha (fecha_creacion)
) ENGINE=InnoDB;

-- ========================================
-- TABLA: respuestas_ticket
-- Respuestas a tickets de soporte
-- ========================================
CREATE TABLE respuestas_ticket (
    id_respuesta BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_ticket BIGINT NOT NULL,
    id_usuario BIGINT NOT NULL,
    mensaje TEXT NOT NULL,
    es_staff BOOLEAN DEFAULT FALSE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (id_ticket) REFERENCES tickets_soporte(id_ticket) ON DELETE CASCADE,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    INDEX idx_ticket (id_ticket),
    INDEX idx_fecha (fecha_creacion)
) ENGINE=InnoDB;

-- ========================================
-- TABLA: cupones
-- Cupones de descuento
-- ========================================
CREATE TABLE cupones (
    id_cupon BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(255),
    tipo_descuento VARCHAR(20) NOT NULL,
    valor_descuento DECIMAL(10,2) NOT NULL,
    monto_minimo DECIMAL(10,2) DEFAULT 0,
    usos_maximos INT,
    usos_actuales INT DEFAULT 0,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    INDEX idx_codigo (codigo),
    INDEX idx_activo (activo)
) ENGINE=InnoDB;

-- ========================================
-- TABLA: historial_precios
-- Seguimiento de cambios de precios
-- ========================================
CREATE TABLE historial_precios (
    id_historial BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_producto BIGINT NOT NULL,
    precio_anterior DECIMAL(10,2) NOT NULL,
    precio_nuevo DECIMAL(10,2) NOT NULL,
    fecha_cambio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto) ON DELETE CASCADE,
    INDEX idx_producto (id_producto),
    INDEX idx_fecha (fecha_cambio)
) ENGINE=InnoDB;

-- ========================================
-- VISTA: productos_completos
-- Vista con información completa de productos
-- ========================================
CREATE VIEW vista_productos_completos AS
SELECT 
    p.id_producto,
    p.sku,
    p.nombre,
    p.descripcion,
    p.descripcion_corta,
    c.nombre AS categoria,
    m.nombre AS marca,
    p.precio,
    p.precio_anterior,
    p.en_oferta,
    p.porcentaje_descuento,
    p.stock,
    p.imagen_principal,
    p.destacado,
    p.nuevo,
    p.calificacion_promedio,
    p.total_resenas,
    p.vistas,
    p.activo
FROM productos p
LEFT JOIN categorias c ON p.id_categoria = c.id_categoria
LEFT JOIN marcas m ON p.id_marca = m.id_marca;

-- ========================================
-- VISTA: pedidos_resumen
-- Vista resumen de pedidos
-- ========================================
CREATE VIEW vista_pedidos_resumen AS
SELECT 
    ped.id_pedido,
    ped.numero_pedido,
    CONCAT(u.nombre, ' ', u.apellido) AS cliente,
    u.email,
    ped.total,
    ped.estado,
    ped.estado_pago,
    ped.metodo_pago,
    ped.fecha_pedido,
    COUNT(dp.id_detalle) AS total_items
FROM pedidos ped
JOIN usuarios u ON ped.id_usuario = u.id_usuario
LEFT JOIN detalle_pedido dp ON ped.id_pedido = dp.id_pedido
GROUP BY ped.id_pedido;

-- Mensaje de éxito
SELECT '✅ Base de datos creada exitosamente para e-commerce' AS mensaje;
