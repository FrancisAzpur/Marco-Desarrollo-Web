-- ========================================
-- INSERTAR DATOS INICIALES
-- Ejecuta este script en tu base de datos
-- ========================================

USE shoptrust_db;

-- Verificar si ya existen usuarios
SELECT COUNT(*) as total_usuarios FROM usuarios;

-- Insertar usuario administrador
-- Contraseña: admin123
INSERT INTO usuarios (nombre_usuario, contrasena, nombre_completo, correo_electronico, rol, activo, fecha_creacion, fecha_actualizacion) 
VALUES ('admin', '$2a$10$F2FlTWp/3jLVMMKCZXY1OeBfXxcOW8cPa4giw1PViGl/TDWXOlFRi', 'Administrador', 'admin@shoptrust.com', 'ADMIN', TRUE, NOW(), NOW());

-- Insertar usuario empleado
-- Contraseña: empleado123
INSERT INTO usuarios (nombre_usuario, contrasena, nombre_completo, correo_electronico, rol, activo, fecha_creacion, fecha_actualizacion) 
VALUES ('empleado', '$2a$10$9EP39GyhAHN.9URM9rPAuu1jLj/Zu7s3ZgEL3crMh3xa3tHYg7TX.', 'Empleado Ventas', 'empleado@shoptrust.com', 'EMPLEADO', TRUE, NOW(), NOW());

-- Insertar categorías
INSERT INTO categorias (nombre_categoria, descripcion, activo) VALUES 
('Electrónica', 'Productos electrónicos y tecnología', TRUE),
('Ropa', 'Prendas de vestir', TRUE),
('Alimentos', 'Productos alimenticios', TRUE),
('Hogar', 'Artículos para el hogar', TRUE);

-- Insertar productos
INSERT INTO productos (codigo_producto, nombre_producto, descripcion, id_categoria, precio_compra, precio_venta, stock_actual, stock_minimo, activo, fecha_registro) VALUES 
('PROD001', 'Laptop HP 15"', 'Laptop HP con procesador Intel i5', 1, 1500.00, 2000.00, 10, 3, TRUE, NOW()),
('PROD002', 'Mouse Logitech', 'Mouse inalámbrico Logitech', 1, 25.00, 35.00, 50, 10, TRUE, NOW()),
('PROD003', 'Teclado Mecánico', 'Teclado mecánico RGB', 1, 80.00, 120.00, 20, 5, TRUE, NOW());

-- Insertar clientes
INSERT INTO clientes (nombre_completo, documento_identidad, tipo_documento, telefono, correo_electronico, direccion, activo, fecha_registro) VALUES 
('Juan Pérez García', '12345678', 'DNI', '987654321', 'juan.perez@email.com', 'Av. Principal 123', TRUE, NOW()),
('María López Sánchez', '87654321', 'DNI', '912345678', 'maria.lopez@email.com', 'Jr. Comercio 456', TRUE, NOW());

-- Insertar proveedores
INSERT INTO proveedores (nombre_empresa, ruc, contacto_nombre, contacto_telefono, contacto_email, direccion, activo, fecha_registro) VALUES 
('Distribuidora Tech SAC', '20123456789', 'Carlos Ruiz', '987123456', 'ventas@techsac.com', 'Av. Tecnología 500', TRUE, NOW()),
('Importadora Global EIRL', '20987654321', 'Ana Torres', '912987654', 'contacto@global.com', 'Jr. Industrial 200', TRUE, NOW());

-- Verificar inserción
SELECT 'Datos insertados correctamente' as mensaje;
SELECT COUNT(*) AS total_usuarios FROM usuarios;
SELECT COUNT(*) AS total_categorias FROM categorias;
SELECT COUNT(*) AS total_productos FROM productos;
SELECT COUNT(*) AS total_clientes FROM clientes;
SELECT COUNT(*) AS total_proveedores FROM proveedores;
