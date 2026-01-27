# 🚀 GUÍA COMPLETA PARA EJECUTAR LOS PROYECTOS

## 📋 RESUMEN DE LO QUE SE HA HECHO

### ✅ **COMPLETADO:**

1. **Base de Datos E-commerce Completa:**
   - ✅ 18 tablas diseñadas (usuarios, productos, pedidos, carrito, soporte, etc.)
   - ✅ 2 vistas SQL (vista_productos_completos, vista_pedidos_resumen)
   - ✅ Datos de muestra (18 productos, 6 categorías, 8 marcas)
   - ✅ Scripts SQL listos en `database/`

2. **Backend Java/Spring Boot:**
   - ✅ 16 entidades JPA creadas
   - ✅ 6 repositories con métodos de consulta
   - ✅ 4 services con lógica de negocio
   - ✅ 3 controllers (TiendaController, CarritoController, AuthController)
   - ✅ application.properties actualizado
   - ✅ **COMPILACIÓN EXITOSA** ✓

3. **Frontend Menu (Sin BD):**
   - ✅ index3.html actualizado con navegación
   - ✅ Enlaces a electrodomesticos.html funcionando
   - ✅ Botones CTA en hero y sección de productos
   - ✅ Footer con enlaces correctos

---

## 🗄️ PASO 1: CREAR LA BASE DE DATOS

### **Opción A: Usando MySQL Workbench (RECOMENDADO)**

1. **Abre MySQL Workbench**
2. **Conéctate a tu servidor MySQL** (localhost)
3. **Abre y ejecuta el primer script:**
   - Ve a: `File` → `Open SQL Script`
   - Selecciona: `database/01_crear_base_datos_ecommerce.sql`
   - Clic en el botón de rayo ⚡ para ejecutar
   - Espera a que termine (creará 18 tablas)

4. **Abre y ejecuta el segundo script:**
   - Ve a: `File` → `Open SQL Script`
   - Selecciona: `database/02_insertar_datos_ecommerce.sql`
   - Clic en el botón de rayo ⚡ para ejecutar
   - Esto insertará los datos de muestra

5. **Verifica que se creó correctamente:**
   ```sql
   USE tienda_electrodomesticos;
   SELECT COUNT(*) FROM productos;  -- Debe mostrar 18
   SELECT COUNT(*) FROM categorias; -- Debe mostrar 6
   ```

### **Opción B: Desde línea de comandos (si MySQL está en PATH)**

```bash
# Navega a la carpeta del proyecto
cd C:\Users\franc\OneDrive\Documentos\GitHub\Marco-Desarrollo-Web

# Ejecuta los scripts
mysql -u root -p < database/01_crear_base_datos_ecommerce.sql
mysql -u root -p < database/02_insertar_datos_ecommerce.sql
```

### **Opción C: Si usas XAMPP/WAMP**

1. Abre phpMyAdmin: `http://localhost/phpmyadmin`
2. Clic en "Importar"
3. Selecciona `01_crear_base_datos_ecommerce.sql` → Ejecutar
4. Selecciona `02_insertar_datos_ecommerce.sql` → Ejecutar

---

## 🔧 PASO 2: CONFIGURAR CONTRASEÑA DE MYSQL

Abre `backend/src/main/resources/application.properties` y verifica/cambia:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/tienda_electrodomesticos?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD_AQUI  # ⚠️ CAMBIA ESTO
```

---

## 🚀 PASO 3: EJECUTAR EL PROYECTO BACKEND (Con Base de Datos)

### **Método 1: Con Maven (PowerShell)**

```powershell
# Navega al directorio backend
cd C:\Users\franc\OneDrive\Documentos\GitHub\Marco-Desarrollo-Web\backend

# Ejecuta el proyecto
mvn spring-boot:run
```

### **Método 2: Con IntelliJ IDEA / Eclipse**

1. **Abre el proyecto backend** en tu IDE
2. **Busca la clase principal:** `ShopTrustApplication.java`
3. **Clic derecho → Run 'ShopTrustApplication'**

### **Método 3: Compilar y ejecutar JAR**

```powershell
cd backend
mvn clean package -DskipTests
java -jar target/sistema-ventas-1.0.0.jar
```

### **Verificar que está funcionando:**

```
✅ Si ves esto en la consola:
   - Started ShopTrustApplication in X.XXX seconds
   - Tomcat started on port(s): 8080

✅ Abre tu navegador en: http://localhost:8080/tienda
```

---

## 🎨 PASO 4: EJECUTAR EL PROYECTO MENU (Sin Base de Datos)

Este proyecto es **standalone** (HTML/CSS/JavaScript puro, sin backend).

### **Opción A: Abrir directamente en el navegador**

1. Navega a la carpeta:
   ```
   C:\Users\franc\OneDrive\Documentos\GitHub\Marco-Desarrollo-Web\menu
   ```

2. **Haz doble clic en `index3.html`**
   - Se abrirá en tu navegador predeterminado

### **Opción B: Con Live Server en VS Code**

1. Instala la extensión **Live Server** en VS Code
2. Abre la carpeta `menu` en VS Code
3. Clic derecho en `index3.html` → **Open with Live Server**
4. Se abrirá en: `http://127.0.0.1:5500/index3.html`

### **Verificar funcionamiento:**

```
✅ Deberías ver:
   - Hero section con botón "VER ELECTRODOMÉSTICOS"
   - 3 productos destacados
   - Navbar con enlace a "Electrodomésticos"
   - Footer con enlaces

✅ Haz clic en "Electrodomésticos" o "VER ELECTRODOMÉSTICOS"
   - Te llevará a electrodomesticos.html
   - Verás animaciones, filtros por categoría, productos con hover effects
```

---

## 📍 RUTAS Y ENDPOINTS DISPONIBLES

### **PROYECTO BACKEND (Puerto 8080):**

| Ruta | Descripción |
|------|-------------|
| `http://localhost:8080/tienda` | Página principal de la tienda |
| `http://localhost:8080/tienda/catalogo` | Catálogo completo |
| `http://localhost:8080/tienda/producto/{slug}` | Detalle de producto |
| `http://localhost:8080/tienda/categoria/{slug}` | Productos por categoría |
| `http://localhost:8080/tienda/ofertas` | Productos en oferta |
| `http://localhost:8080/carrito` | Ver carrito de compras |
| `http://localhost:8080/auth/login` | Login de usuarios |
| `http://localhost:8080/auth/registro` | Registro de usuarios |

### **PROYECTO MENU (HTML estático):**

| Archivo | Descripción |
|---------|-------------|
| `menu/index3.html` | Página principal |
| `menu/templates/electrodomesticos.html` | Catálogo con animaciones |

---

## 🐛 SOLUCIÓN DE PROBLEMAS COMUNES

### **1. Error: "Communications link failure"**
```
❌ No se puede conectar a MySQL
✅ Solución:
   - Verifica que MySQL esté corriendo
   - En XAMPP: Inicia MySQL
   - En Windows Services: Inicia "MySQL80"
   - Verifica puerto 3306 esté libre
```

### **2. Error: "Access denied for user 'root'@'localhost'"**
```
❌ Contraseña incorrecta
✅ Solución:
   - Cambia la contraseña en application.properties
   - Línea 16: spring.datasource.password=TU_PASSWORD
```

### **3. Error: "Unknown database 'tienda_electrodomesticos'"**
```
❌ La base de datos no existe
✅ Solución:
   - Ejecuta primero los scripts SQL (PASO 1)
   - Verifica que se creó correctamente en MySQL Workbench
```

### **4. Puerto 8080 ya está en uso**
```
❌ Otro proceso usa el puerto 8080
✅ Solución A: Cierra el otro proceso
✅ Solución B: Cambia el puerto en application.properties
   - Línea 10: server.port=8081
```

### **5. Menu no muestra imágenes**
```
❌ Las rutas de las imágenes no se encuentran
✅ Solución:
   - Verifica que existe la carpeta menu/img/
   - Las imágenes deben estar en menu/img/
   - Rutas relativas: <img src="img/nombre.jpg">
```

---

## 📊 DATOS DE PRUEBA DISPONIBLES

Una vez ejecutados los scripts SQL, tendrás acceso a:

### **Usuarios de Prueba:**
```
Email: juan.perez@email.com
Password: password123

Email: maria.garcia@email.com  
Password: password123
```

### **Productos (18 en total):**
- 📺 **Televisores:** 5 productos (S/ 899 - S/ 7,999)
- 🧊 **Refrigeración:** 4 productos (S/ 499 - S/ 3,499)
- 🫧 **Lavado:** 3 productos (S/ 999 - S/ 2,299)
- 🍳 **Cocina:** 6 productos (S/ 349 - S/ 1,299)

### **Categorías (6):**
- Televisores
- Refrigeración
- Lavado  
- Cocina
- Climatización
- Pequeños Electrodomésticos

---

## ✅ CHECKLIST DE VERIFICACIÓN

Antes de probar, asegúrate de:

- [ ] MySQL está corriendo
- [ ] Base de datos `tienda_electrodomesticos` existe
- [ ] Contraseña MySQL configurada en application.properties
- [ ] Backend compila sin errores (mvn clean compile)
- [ ] Backend corriendo en puerto 8080
- [ ] Puedes acceder a http://localhost:8080/tienda
- [ ] Menu index3.html abre correctamente
- [ ] Enlaces a electrodomesticos.html funcionan

---

## 🎯 PRÓXIMOS PASOS SUGERIDOS

1. **Crear plantillas Thymeleaf** para las vistas del backend:
   - templates/tienda/inicio.html
   - templates/tienda/catalogo.html
   - templates/tienda/detalle-producto.html
   - templates/tienda/carrito.html
   - templates/auth/login.html

2. **Implementar seguridad con Spring Security:**
   - Proteger rutas
   - Gestión de sesiones
   - Roles (CLIENTE, ADMIN)

3. **Integrar pasarela de pagos:**
   - Culqi, Mercado Pago, PayPal

4. **Añadir panel de administración:**
   - Gestión de productos
   - Gestión de pedidos
   - Reportes de ventas

---

## 📞 CONTACTO / AYUDA

Si encuentras algún error durante la ejecución:

1. Revisa los logs en la consola
2. Verifica que todos los servicios estén corriendo
3. Comprueba que los puertos no estén ocupados
4. Asegúrate de que la base de datos tenga datos

---

**¡LISTO! Ahora tienes dos proyectos funcionando:**
- 🏪 **Backend E-commerce** en http://localhost:8080/tienda
- 🎨 **Menu Frontend** abriendo index3.html directamente

---

*Última actualización: 27 de enero de 2026*
