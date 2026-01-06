package com.shoptrust.controllers;

import com.shoptrust.models.Usuario;
import com.shoptrust.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador de Autenticación
 * Maneja las peticiones relacionadas con login, registro y cierre de sesión
 */
@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    private final UsuarioService usuarioService;

    /**
     * Muestra la página de login
     * @param error parámetro que indica si hubo error en el login
     * @param logout parámetro que indica si se cerró sesión
     * @param model modelo para pasar datos a la vista
     * @return vista de login
     */
    @GetMapping("/login")
    public String mostrarLogin(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {
        
        if (error != null) {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
        }
        
        if (logout != null) {
            model.addAttribute("mensaje", "Sesión cerrada correctamente");
        }
        
        return "authentication/login";
    }

    /**
     * Muestra la página de registro
     * @param model modelo para pasar datos a la vista
     * @return vista de registro
     */
    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "authentication/registro";
    }

    /**
     * Procesa el formulario de registro
     * @param usuario objeto Usuario con los datos del formulario
     * @param bindingResult resultado de la validación
     * @param redirectAttributes atributos para redirección
     * @return redirección a login si es exitoso, o vuelve al formulario
     */
    @PostMapping("/registro")
    public String procesarRegistro(
            @Valid @ModelAttribute("usuario") Usuario usuario,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        
        // Si hay errores de validación, volver al formulario
        if (bindingResult.hasErrors()) {
            return "authentication/registro";
        }

        try {
            // Registrar el usuario
            usuarioService.registrarUsuario(usuario);
            
            redirectAttributes.addFlashAttribute("mensaje", 
                "Registro exitoso. Por favor inicie sesión");
            
            return "redirect:/login";
            
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/registro";
        }
    }

    /**
     * Página de inicio después de autenticarse
     * @param model modelo para pasar datos a la vista
     * @return vista de inicio
     */
    @GetMapping({"/", "/inicio"})
    public String inicio(Model model) {
        // Obtener el usuario autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = auth.getName();
        
        Usuario usuario = usuarioService.buscarPorNombreUsuario(nombreUsuario)
                .orElse(null);
        
        model.addAttribute("usuario", usuario);
        
        return "pages/inicio";
    }

    /**
     * Página de acceso denegado
     * @return vista de acceso denegado
     */
    @GetMapping("/acceso-denegado")
    public String accesoDenegado() {
        return "authentication/acceso-denegado";
    }
}
