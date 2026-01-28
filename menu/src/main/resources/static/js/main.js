// ==========================================
// INICIALIZACIÓN DE AOS (Animate On Scroll)
// ==========================================
AOS.init({ 
    duration: 1200, 
    once: true 
});

// ==========================================
// NAVBAR SCROLL EFFECT
// ==========================================
window.addEventListener('scroll', () => {
    const navbar = document.querySelector('.navbar');
    if (navbar) {
        navbar.classList.toggle('scrolled', window.scrollY > 50);
    }
});

// ==========================================
// GESTIÓN DE CARRITO CON LOCALSTORAGE
// ==========================================

/**
 * Obtiene el carrito desde localStorage
 * @returns {Array} Array de productos en el carrito
 */
function getCarrito() {
    const carrito = localStorage.getItem('carrito');
    return carrito ? JSON.parse(carrito) : [];
}

/**
 * Guarda el carrito en localStorage
 * @param {Array} carrito - Array de productos
 */
function setCarrito(carrito) {
    localStorage.setItem('carrito', JSON.stringify(carrito));
}

/**
 * Agrega un producto al carrito
 * @param {Object} producto - Objeto con datos del producto
 */
function agregarAlCarrito(producto) {
    let carrito = getCarrito();
    
    // Buscar si el producto ya existe en el carrito
    const productoExistente = carrito.find(item => item.id === producto.id);
    
    if (productoExistente) {
        // Si existe, incrementar cantidad
        productoExistente.cantidad++;
    } else {
        // Si no existe, agregar con cantidad 1
        carrito.push({
            ...producto,
            cantidad: 1
        });
    }
    
    setCarrito(carrito);
    actualizarContadorCarrito();
    
    // Mostrar mensaje de éxito
    mostrarNotificacion(`✓ ${producto.nombre} agregado al carrito`);
}

/**
 * Actualiza el contador del carrito en el navbar
 */
function actualizarContadorCarrito() {
    const carrito = getCarrito();
    const totalItems = carrito.reduce((total, item) => total + item.cantidad, 0);
    
    const contadorElemento = document.querySelector('.cart-count');
    if (contadorElemento) {
        contadorElemento.textContent = totalItems;
    }
}

/**
 * Muestra una notificación temporal
 * @param {string} mensaje - Mensaje a mostrar
 */
function mostrarNotificacion(mensaje) {
    // Crear elemento de notificación
    const notificacion = document.createElement('div');
    notificacion.className = 'alert alert-success position-fixed';
    notificacion.style.cssText = 'top: 80px; right: 20px; z-index: 9999; min-width: 250px;';
    notificacion.textContent = mensaje;
    
    document.body.appendChild(notificacion);
    
    // Eliminar después de 3 segundos
    setTimeout(() => {
        notificacion.remove();
    }, 3000);
}

// ==========================================
// EVENTO PARA BOTONES DE AÑADIR AL CARRITO
// ==========================================
document.addEventListener('DOMContentLoaded', () => {
    // Actualizar contador al cargar la página
    actualizarContadorCarrito();
    
    // Agregar event listeners a todos los botones de "Añadir al carrito"
    const botonesAgregar = document.querySelectorAll('.btn-add-cart');
    
    botonesAgregar.forEach(boton => {
        boton.addEventListener('click', (e) => {
            e.preventDefault();
            
            // Obtener datos del producto desde atributos data-*
            const producto = {
                id: parseInt(boton.dataset.id),
                nombre: boton.dataset.nombre,
                precio: parseFloat(boton.dataset.precio),
                imagen: boton.dataset.imagen
            };
            
            agregarAlCarrito(producto);
        });
    });
});

// ==========================================
// FUNCIONES AUXILIARES
// ==========================================

/**
 * Vacía completamente el carrito
 */
function vaciarCarrito() {
    localStorage.removeItem('carrito');
    actualizarContadorCarrito();
}

/**
 * Elimina un producto específico del carrito
 * @param {number} productoId - ID del producto a eliminar
 */
function eliminarDelCarrito(productoId) {
    let carrito = getCarrito();
    carrito = carrito.filter(item => item.id !== productoId);
    setCarrito(carrito);
    actualizarContadorCarrito();
}

/**
 * Obtiene el total del carrito
 * @returns {number} Total en soles
 */
function calcularTotalCarrito() {
    const carrito = getCarrito();
    return carrito.reduce((total, item) => total + (item.precio * item.cantidad), 0);
}

// ==========================================
// CONSOLA - DEBUG (solo en desarrollo)
// ==========================================
console.log('🛒 Sistema de carrito con localStorage inicializado');
console.log('📦 Productos en carrito:', getCarrito());
