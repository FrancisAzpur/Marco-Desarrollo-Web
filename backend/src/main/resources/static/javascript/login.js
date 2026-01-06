/**
 * Script para la página de login
 * Maneja la funcionalidad de mostrar/ocultar contraseña
 */

document.addEventListener('DOMContentLoaded', function() {
    // Obtener elementos
    const togglePassword = document.getElementById('togglePassword');
    const passwordInput = document.getElementById('contrasena');
    const toggleIcon = document.getElementById('toggleIcon');

    // Función para mostrar/ocultar contraseña
    if (togglePassword) {
        togglePassword.addEventListener('click', function() {
            // Cambiar el tipo de input
            const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
            passwordInput.setAttribute('type', type);
            
            // Cambiar el icono
            if (type === 'password') {
                toggleIcon.classList.remove('bi-eye-slash-fill');
                toggleIcon.classList.add('bi-eye-fill');
            } else {
                toggleIcon.classList.remove('bi-eye-fill');
                toggleIcon.classList.add('bi-eye-slash-fill');
            }
        });
    }

    // Auto-ocultar alertas después de 5 segundos
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(function(alert) {
        setTimeout(function() {
            const bsAlert = new bootstrap.Alert(alert);
            bsAlert.close();
        }, 5000);
    });

    // Validación del formulario
    const form = document.querySelector('form');
    if (form) {
        form.addEventListener('submit', function(event) {
            const nombreUsuario = document.getElementById('nombreUsuario').value.trim();
            const contrasena = document.getElementById('contrasena').value.trim();

            if (nombreUsuario === '' || contrasena === '') {
                event.preventDefault();
                mostrarError('Por favor, complete todos los campos');
                return false;
            }

            if (nombreUsuario.length < 4) {
                event.preventDefault();
                mostrarError('El nombre de usuario debe tener al menos 4 caracteres');
                return false;
            }

            if (contrasena.length < 6) {
                event.preventDefault();
                mostrarError('La contraseña debe tener al menos 6 caracteres');
                return false;
            }
        });
    }
});

/**
 * Función para mostrar mensajes de error
 */
function mostrarError(mensaje) {
    // Remover alertas existentes
    const alertasAnteriores = document.querySelectorAll('.alert-danger');
    alertasAnteriores.forEach(alerta => alerta.remove());

    // Crear nueva alerta
    const alerta = document.createElement('div');
    alerta.className = 'alert alert-danger alert-dismissible fade show';
    alerta.role = 'alert';
    alerta.innerHTML = `
        <i class="bi bi-exclamation-triangle-fill me-2"></i>
        <span>${mensaje}</span>
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;

    // Insertar antes del formulario
    const form = document.querySelector('form');
    form.parentNode.insertBefore(alerta, form);

    // Auto-ocultar después de 5 segundos
    setTimeout(function() {
        const bsAlert = new bootstrap.Alert(alerta);
        bsAlert.close();
    }, 5000);
}
