function agregarAlCarrito(btn) {
    const id = btn.getAttribute('data-id');
    const nombre = btn.getAttribute('data-nombre');
    const precio = parseFloat(btn.getAttribute('data-precio'));

    // Aquí iría la lógica real de tu carrito (localStorage, fetch a /api/carrito, etc.)
    console.log(`Agregando al carrito → ${nombre} (S/ ${precio}) - ID: ${id}`);

    // Ejemplo simple con alerta (cámbialo por tu sistema real)
    alert(`¡${nombre} agregado al carrito por S/ ${precio}!`);

    // Opcional: animación rápida
    btn.innerHTML = '<i class="fas fa-check"></i> ¡Agregado!';
    btn.classList.remove('from-red-600', 'to-red-700', 'hover:from-red-700');
    btn.classList.add('from-green-600', 'to-green-700');

    setTimeout(() => {
        btn.innerHTML = '<i class="fas fa-cart-plus"></i> Agregar al carrito';
        btn.classList.remove('from-green-600', 'to-green-700');
        btn.classList.add('from-red-600', 'to-red-700', 'hover:from-red-700');
    }, 2000);
}