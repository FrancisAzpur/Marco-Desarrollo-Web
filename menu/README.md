# 🛍️ Comercial Robinson - Módulo Demo

Sistema de catálogo de productos con Spring Boot y Thymeleaf (versión standalone sin base de datos).

## 📋 Características

- ✅ **Sin Base de Datos** - Los datos están hardcodeados en el controlador
- ✅ **Thymeleaf** - Motor de plantillas para las vistas
- ✅ **Bootstrap 5.3** - Framework CSS responsive
- ✅ **Font Awesome 6** - Iconos modernos
- ✅ **AOS Animations** - Animaciones al hacer scroll
- ✅ **Animate.css** - Animaciones CSS
- ✅ **Header y Footer Reutilizables** - Fragmentos de Thymeleaf
- ✅ **JavaScript Modular** - Carrito de compras funcional

## 🚀 Cómo Ejecutar

### Opción 1: Integrar con el proyecto principal

1. Copiar los archivos Java a: `backend/src/main/java/com/comercialrobinson/`
2. Copiar las plantillas a: `backend/src/main/resources/templates/`
3. Copiar los archivos estáticos a: `backend/src/main/resources/static/`
4. Copiar las imágenes a: `backend/src/main/resources/static/img/`
5. Ejecutar el proyecto principal desde `backend/`

### Opción 2: Ejecutar como proyecto independiente

Si deseas ejecutar esta carpeta como un proyecto Spring Boot independiente, necesitas:

1. Crear un `pom.xml` en la carpeta menu con las dependencias de Spring Boot
2. Ajustar la estructura de carpetas:
   - `java/` → `src/main/java/`
   - `templates/` → `src/main/resources/templates/`
   - `static/` → `src/main/resources/static/`
   - `img/` → `src/main/resources/static/img/`

3. Ejecutar:
```bash
mvn spring-boot:run
```

4. Acceder a: `http://localhost:8090`

## 📁 Estructura de Archivos

```
menu/
├── java/com/comercialrobinson/
│   ├── ComercialRobinsonApplication.java    # Clase principal
│   ├── controllers/
│   │   └── HomeController.java              # Controlador con datos hardcodeados
│   └── models/
│       ├── Producto.java                    # Modelo de producto
│       └── Categoria.java                   # Modelo de categoría
├── templates/
│   ├── fragments/
│   │   ├── header.html                      # Header reutilizable
│   │   └── footer.html                      # Footer reutilizable
│   ├── index.html                           # Página principal
│   ├── electrodomesticos.html               # Catálogo de electrodomésticos
│   ├── hogar.html                           # Productos para el hogar
│   ├── categorias.html                      # Vista de categorías
│   ├── ofertas.html                         # Productos en oferta
│   └── contacto.html                        # Formulario de contacto
├── static/
│   ├── css/
│   │   └── styles.css                       # Estilos personalizados
│   └── js/
│       └── main.js                          # JavaScript principal
├── img/                                      # Imágenes de productos
└── application.properties                    # Configuración

```

## 🎨 Páginas Disponibles

| Ruta | Descripción |
|------|-------------|
| `/` o `/home` | Página principal con categorías y productos destacados |
| `/electrodomesticos` | Catálogo de electrodomésticos (TVs, refrigeradoras, lavadoras) |
| `/hogar` | Productos para el hogar |
| `/categorias` | Vista de todas las categorías |
| `/ofertas` | Productos en oferta con descuentos |
| `/contacto` | Formulario de contacto |

## 🛠️ Tecnologías

- **Spring Boot** - Framework backend
- **Thymeleaf** - Motor de plantillas
- **Bootstrap 5.3** - Framework CSS
- **Font Awesome 6** - Iconos
- **AOS** - Animaciones al scroll
- **Animate.css** - Animaciones CSS
- **JavaScript ES6** - Funcionalidad del frontend

## 💡 Características Destacadas

### Header y Footer Reutilizables
Se usan fragmentos de Thymeleaf para reutilizar el header y footer en todas las páginas:

```html
<!-- Incluir header -->
<nav th:replace="~{fragments/header :: header}"></nav>

<!-- Incluir footer -->
<footer th:replace="~{fragments/footer :: footer}"></footer>

<!-- Incluir scripts -->
<div th:replace="~{fragments/footer :: scripts}"></div>
```

### Carrito de Compras (JavaScript)
El carrito funciona con localStorage y persiste entre páginas:

- Agregar productos al carrito
- Contador de productos en el navbar
- Notificaciones animadas
- Almacenamiento local

### Productos Hardcodeados
Los productos están definidos en `HomeController.java`:
- 10 productos de ejemplo
- 4 categorías
- Precios, descripciones e imágenes

## 🎯 Próximos Pasos (Para integrar con el proyecto principal)

Para conectar este módulo demo con el sistema principal:

1. Reemplazar los datos hardcodeados con servicios que consulten la base de datos
2. Usar los mismos modelos de `backend/src/main/java/com/shoptrust/models/`
3. Integrar con Spring Security para autenticación
4. Agregar funcionalidad real de carrito con backend

## 📝 Notas

- Este módulo está diseñado para demostración sin base de datos
- Las imágenes deben estar en la carpeta `img/` o `static/img/`
- El puerto por defecto es **8090** para no conflictuar con el proyecto principal (8080)
- Los productos y categorías son de ejemplo

---

**Desarrollado con ❤️ para Comercial Robinson**
