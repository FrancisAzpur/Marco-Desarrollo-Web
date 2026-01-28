-- ========================================
-- INSERCIÓN DE DATOS INICIALES
-- TIENDA E-COMMERCE ELECTRODOMÉSTICOS
-- ========================================

USE tienda_electrodomesticos;

-- ========================================
-- USUARIOS DE PRUEBA
-- Password para todos: 123456 (usar BCrypt en producción)
-- ========================================
INSERT INTO usuarios (nombre, apellido, email, password, telefono, fecha_nacimiento, activo) VALUES
('Juan', 'Pérez', 'juan.perez@email.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye', '987654321', '1990-05-15', TRUE),
('María', 'García', 'maria.garcia@email.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye', '912345678', '1988-08-22', TRUE),
('Carlos', 'López', 'carlos.lopez@email.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye', '965432187', '1995-03-10', TRUE),
('Ana', 'Martínez', 'ana.martinez@email.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye', '923456789', '1992-11-30', TRUE);

-- ========================================
-- DIRECCIONES DE ENVÍO
-- ========================================
INSERT INTO direcciones (id_usuario, nombre_destinatario, direccion_linea1, direccion_linea2, ciudad, departamento, codigo_postal, telefono_contacto, es_principal) VALUES
(1, 'Juan Pérez', 'Av. Larco 1234', 'Dpto 301', 'Lima', 'Lima', '15074', '987654321', TRUE),
(1, 'Juan Pérez', 'Jr. Las Flores 567', NULL, 'Miraflores', 'Lima', '15048', '987654321', FALSE),
(2, 'María García', 'Calle Los Olivos 890', NULL, 'San Isidro', 'Lima', '15036', '912345678', TRUE),
(3, 'Carlos López', 'Av. Benavides 2345', 'Casa 12', 'Surco', 'Lima', '15038', '965432187', TRUE);

-- ========================================
-- CATEGORÍAS
-- ========================================
INSERT INTO categorias (nombre, descripcion, imagen, icono, slug, orden) VALUES
('Televisores', 'Smart TVs, OLED, LED y más', 'tv.jpg', 'fa-tv', 'televisores', 1),
('Refrigeración', 'Refrigeradoras, congeladores y frigobar', 'refrigerador.jpg', 'fa-snowflake', 'refrigeracion', 2),
('Lavado', 'Lavadoras, secadoras y lavasecarropas', 'lavadora.jpg', 'fa-soap', 'lavado', 3),
('Cocina', 'Cocinas, hornos y microondas', 'cocina.jpg', 'fa-fire-burner', 'cocina', 4),
('Climatización', 'Aire acondicionado, ventiladores y calefactores', 'clima.jpg', 'fa-temperature-high', 'climatizacion', 5),
('Pequeños Electrodomésticos', 'Licuadoras, batidoras, cafeteras', 'pequenos.jpg', 'fa-blender', 'pequenos-electrodomesticos', 6);

-- ========================================
-- MARCAS
-- ========================================
INSERT INTO marcas (nombre, descripcion, logo) VALUES
('Samsung', 'Tecnología de vanguardia', 'samsung-logo.png'),
('LG', 'Life\'s Good - Calidad premium', 'lg-logo.png'),
('Sony', 'Innovación japonesa', 'sony-logo.png'),
('Whirlpool', 'Electrodomésticos para el hogar', 'whirlpool-logo.png'),
('Electrolux', 'Soluciones inteligentes', 'electrolux-logo.png'),
('Bosch', 'Tecnología alemana', 'bosch-logo.png'),
('Mabe', 'Diseño mexicano', 'mabe-logo.png'),
('Indurama', 'Calidad peruana', 'indurama-logo.png');

-- ========================================
-- PRODUCTOS - TELEVISORES
-- ========================================
INSERT INTO productos (sku, nombre, descripcion, descripcion_corta, id_categoria, id_marca, precio, precio_anterior, en_oferta, porcentaje_descuento, stock, imagen_principal, destacado, nuevo) VALUES
('TV-SAM-55-001', 'Smart TV Samsung 55" OLED 4K', 'Televisor OLED 4K con HDR10+, procesador Quantum 4K, sonido Dolby Atmos y sistema operativo Tizen. Resolución 3840x2160, tasa de refresco 120Hz.', 'Smart TV OLED 4K con HDR10+ y Dolby Atmos', 1, 1, 2499.00, 2999.00, TRUE, 17, 15, 'tv_oled_55.avif', TRUE, TRUE),
('TV-LG-65-002', 'Smart TV LG 65" NanoCell 4K', 'Televisor NanoCell con inteligencia artificial ThinQ AI, panel IPS 4K, HDR Perfect, Magic Remote incluido. Ideal para gaming.', 'Smart TV NanoCell 4K con AI ThinQ', 1, 2, 3299.00, 3799.00, TRUE, 13, 8, 'tv_lg_nanocell.jpg', TRUE, FALSE),
('TV-SON-50-003', 'Smart TV Sony 50" LED 4K', 'Android TV con Google Assistant, procesador X1, triluminos display, compatible con Alexa. Calidad de imagen excepcional.', 'Android TV 4K con Google Assistant', 1, 3, 1899.00, NULL, FALSE, 0, 20, 'SmartTv.jpeg', TRUE, FALSE),
('TV-SAM-43-004', 'Smart TV Samsung 43" Full HD', 'Smart TV con sistema operativo Tizen, WiFi integrado, puertos HDMI y USB. Perfecto para espacios pequeños.', 'Smart TV Full HD 43 pulgadas', 1, 1, 899.00, NULL, FALSE, 0, 30, 'smart_tv_2.webp', FALSE, FALSE),
('TV-LG-75-005', 'Smart TV LG 75" OLED 8K', 'Experiencia visual suprema con resolución 8K, panel OLED auto-iluminado, Dolby Vision IQ, ideal para home theater.', 'Smart TV OLED 8K 75 pulgadas', 1, 2, 7999.00, NULL, FALSE, 0, 3, 'tv_lg_8k.jpg', TRUE, TRUE);

-- ========================================
-- PRODUCTOS - REFRIGERACIÓN
-- ========================================
INSERT INTO productos (sku, nombre, descripcion, descripcion_corta, id_categoria, id_marca, precio, precio_anterior, en_oferta, porcentaje_descuento, stock, imagen_principal, destacado, nuevo) VALUES
('REF-ELX-420-001', 'Refrigeradora Electrolux Inverter 420L', 'Refrigeradora No Frost con tecnología Inverter, eficiencia energética A+++, control de temperatura digital, 3 gavetas transparentes.', 'Refrigeradora No Frost 420L Inverter', 2, 5, 1899.00, NULL, FALSE, 0, 12, 'Refri1.jpg', TRUE, FALSE),
('REF-SAM-600-002', 'Refrigeradora Samsung Side by Side 600L', 'Diseño moderno con dispensador de agua y hielo, sistema Twin Cooling Plus, iluminación LED, puertas de acero inoxidable.', 'Side by Side 600L con dispensador', 2, 1, 3499.00, 3999.00, TRUE, 13, 5, 'refrigerador.webp', TRUE, TRUE),
('REF-WHI-380-003', 'Refrigeradora Whirlpool 380L', 'Sistema No Frost, eficiencia energética A++, gavetas Humidity Fresh, control electrónico de temperatura.', 'Refrigeradora No Frost 380L', 2, 4, 1599.00, NULL, FALSE, 0, 18, 'refrigeradora_2.jpg', FALSE, FALSE),
('REF-LG-550-004', 'Refrigeradora LG Inverter 550L', 'Compresor Lineal Inverter con 10 años de garantía, Door Cooling+, Multi Air Flow, Fresh Zone.', 'Refrigeradora Inverter 550L Premium', 2, 2, 2799.00, NULL, FALSE, 0, 10, 'lg_refrigeradora.jpg', TRUE, FALSE),
('FRIG-IND-120-005', 'Frigobar Indurama 120L', 'Compacto y eficiente, perfecto para departamentos o habitaciones. Bajo consumo energético.', 'Frigobar compacto 120L', 2, 8, 499.00, 599.00, TRUE, 17, 25, 'frigobar.jpg', FALSE, FALSE);

-- ========================================
-- PRODUCTOS - LAVADO
-- ========================================
INSERT INTO productos (sku, nombre, descripcion, descripcion_corta, id_categoria, id_marca, precio, precio_anterior, en_oferta, porcentaje_descuento, stock, imagen_principal, destacado, nuevo) VALUES
('LAV-LG-16-001', 'Lavadora LG Inverter 16kg', 'Lavadora de carga frontal con tecnología Inverter Direct Drive, 14 programas de lavado, TurboWash, SmartThinQ.', 'Lavadora Inverter 16kg carga frontal', 3, 2, 1599.00, 1999.00, TRUE, 20, 10, 'Lavadora1.jpg', TRUE, TRUE),
('LAV-SAM-10-002', 'Lavadora Samsung 10kg Automática', 'Carga superior con 12 programas, Wobble Technology, Diamond Drum, eficiencia energética A+.', 'Lavadora automática 10kg', 3, 1, 999.00, NULL, FALSE, 0, 15, 'lavadora_2.webp', FALSE, FALSE),
('LAVSEC-ELX-009', 'Lavadora-Secadora Electrolux Combo 9kg/6kg', 'Lavadora y secadora en un solo equipo. Perfect Steam, sensor de humedad, 15 programas combinados.', 'Lavasecarropas 9kg/6kg', 3, 5, 2299.00, 2699.00, TRUE, 15, 6, 'lavadora_inverter.webp', TRUE, FALSE),
('LAV-WHI-14-004', 'Lavadora Whirlpool 14kg Turbo', 'Carga superior, sistema Turbo Wash, 10 programas, dosificador automático de detergente.', 'Lavadora Turbo 14kg', 3, 4, 1299.00, NULL, FALSE, 0, 12, 'whirlpool_lavadora.jpg', FALSE, FALSE),
('SEC-BOS-008', 'Secadora Bosch 8kg', 'Secadora a gas con tecnología AutoDry, sensor de humedad, 8 programas especializados.', 'Secadora 8kg con sensor', 3, 6, 1799.00, NULL, FALSE, 0, 8, 'secadora_bosch.jpg', FALSE, TRUE);

-- ========================================
-- PRODUCTOS - COCINA
-- ========================================
INSERT INTO productos (sku, nombre, descripcion, descripcion_corta, id_categoria, id_marca, precio, precio_anterior, en_oferta, porcentaje_descuento, stock, imagen_principal, destacado, nuevo) VALUES
('COC-MAB-6Q-001', 'Cocina Mabe 6 Quemadores', 'Cocina a gas con horno a gas, parrillas de fierro fundido, encendido eléctrico, timer digital.', 'Cocina 6 quemadores con horno', 4, 7, 899.00, NULL, FALSE, 0, 15, 'cocina_mabe.jpg', FALSE, FALSE),
('MIC-SAM-28L-002', 'Microondas Samsung 28L', 'Microondas digital con grill, 10 niveles de potencia, descongelamiento automático, plato giratorio.', 'Microondas 28L con grill', 4, 1, 349.00, 399.00, TRUE, 13, 20, 'microondas_samsung.jpg', FALSE, FALSE),
('HOR-BOS-60-003', 'Horno Empotrable Bosch 60cm', 'Horno eléctrico multifunción, 8 funciones de cocción, limpieza pirolítica, control digital.', 'Horno empotrable multifunción', 4, 6, 1299.00, NULL, FALSE, 0, 8, 'horno_bosch.jpg', TRUE, FALSE);

-- ========================================
-- ESPECIFICACIONES DE PRODUCTOS (Ejemplo para TV Samsung OLED)
-- ========================================
INSERT INTO especificaciones_producto (id_producto, nombre_especificacion, valor_especificacion, orden) VALUES
(1, 'Tamaño de Pantalla', '55 pulgadas', 1),
(1, 'Resolución', '3840 x 2160 (4K UHD)', 2),
(1, 'Tecnología de Panel', 'OLED', 3),
(1, 'Tasa de Refresco', '120Hz', 4),
(1, 'HDR', 'HDR10+, HLG', 5),
(1, 'Sistema Operativo', 'Tizen', 6),
(1, 'Procesador', 'Quantum Processor 4K', 7),
(1, 'Audio', 'Dolby Atmos, 40W', 8),
(1, 'Conectividad', '4 HDMI 2.1, 3 USB, WiFi 6, Bluetooth 5.2', 9),
(1, 'Dimensiones', '122.7 x 70.6 x 4.5 cm', 10),
(1, 'Peso', '18.5 kg', 11),
(1, 'Consumo Energético', 'Clase A', 12);

-- ========================================
-- IMÁGENES ADICIONALES (Galería)
-- ========================================
INSERT INTO imagenes_producto (id_producto, url_imagen, orden) VALUES
(1, 'tv_oled_55_2.jpg', 1),
(1, 'tv_oled_55_3.jpg', 2),
(1, 'tv_oled_55_4.jpg', 3),
(2, 'tv_lg_2.jpg', 1),
(2, 'tv_lg_3.jpg', 2),
(6, 'refrigeradora_interior.jpg', 1),
(6, 'refrigeradora_gavetas.jpg', 2);

-- ========================================
-- RESEÑAS DE PRODUCTOS
-- ========================================
INSERT INTO resenas (id_producto, id_usuario, calificacion, titulo, comentario, verificado) VALUES
(1, 1, 5, 'Excelente calidad de imagen', 'El mejor TV que he comprado. Los colores son increíbles y el sistema operativo es muy fluido.', TRUE),
(1, 2, 5, 'Vale cada centavo', 'Increíble experiencia al ver películas. El sonido Dolby Atmos es impresionante.', TRUE),
(1, 3, 4, 'Muy bueno pero costoso', 'Excelente producto, solo que el precio es algo elevado. Pero la calidad lo justifica.', TRUE),
(6, 1, 5, 'Refrigeradora perfecta', 'Muy silenciosa, mantiene excelente temperatura. El sistema No Frost funciona perfecto.', TRUE),
(11, 2, 5, 'Lava muy bien', 'Excelente lavadora, los programas son muy variados y lava muy bien la ropa.', TRUE);

-- Actualizar calificaciones promedio
UPDATE productos SET calificacion_promedio = 4.67, total_resenas = 3 WHERE id_producto = 1;
UPDATE productos SET calificacion_promedio = 5.00, total_resenas = 1 WHERE id_producto = 6;
UPDATE productos SET calificacion_promedio = 5.00, total_resenas = 1 WHERE id_producto = 11;

-- ========================================
-- CUPONES DE DESCUENTO
-- ========================================
INSERT INTO cupones (codigo, descripcion, tipo_descuento, valor_descuento, monto_minimo, usos_maximos, fecha_inicio, fecha_fin) VALUES
('BIENVENIDO10', 'Descuento de bienvenida', 'PORCENTAJE', 10.00, 500.00, 100, '2026-01-01', '2026-12-31'),
('CYBER50', 'Cyber Days - 50 soles de descuento', 'FIJO', 50.00, 1000.00, 50, '2026-01-27', '2026-02-28'),
('ENVIOGRATIS', 'Envío gratis sin mínimo', 'ENVIO', 0.00, 0.00, 200, '2026-01-01', '2026-12-31');

-- ========================================
-- PEDIDO DE EJEMPLO
-- ========================================
INSERT INTO pedidos (numero_pedido, id_usuario, id_direccion, subtotal, descuento, costo_envio, total, estado, metodo_pago, estado_pago) VALUES
('PED-2026-00001', 1, 1, 2499.00, 0.00, 30.00, 2529.00, 'ENTREGADO', 'TARJETA', 'PAGADO');

INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad, precio_unitario, subtotal) VALUES
(1, 1, 1, 2499.00, 2499.00);

-- ========================================
-- TICKETS DE SOPORTE
-- ========================================
INSERT INTO tickets_soporte (numero_ticket, id_usuario, asunto, descripcion, categoria, prioridad, estado) VALUES
('TKT-2026-00001', 2, 'Consulta sobre garantía extendida', '¿Cómo puedo adquirir garantía extendida para mi TV?', 'GARANTIA', 'BAJA', 'RESUELTO'),
('TKT-2026-00002', 1, 'Problema con lavadora', 'Mi lavadora hace ruido extraño durante el centrifugado', 'TECNICO', 'ALTA', 'EN_PROCESO');

INSERT INTO respuestas_ticket (id_ticket, id_usuario, mensaje, es_staff) VALUES
(1, 2, 'Hola, necesito información sobre garantía extendida', FALSE),
(1, 1, 'Hola! Puedes adquirir garantía extendida hasta 30 días después de tu compra. Te enviamos el formulario por email.', TRUE);

-- ========================================
-- MENSAJE FINAL
-- ========================================
SELECT '✅ Datos iniciales insertados correctamente' AS mensaje;
SELECT CONCAT('Usuarios: ', COUNT(*)) AS total FROM usuarios
UNION ALL SELECT CONCAT('Categorías: ', COUNT(*)) FROM categorias
UNION ALL SELECT CONCAT('Marcas: ', COUNT(*)) FROM marcas
UNION ALL SELECT CONCAT('Productos: ', COUNT(*)) FROM productos
UNION ALL SELECT CONCAT('Reseñas: ', COUNT(*)) FROM resenas
UNION ALL SELECT CONCAT('Cupones: ', COUNT(*)) FROM cupones;
