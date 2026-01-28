package com.shoptrust.controllers.ecommerce;

import com.shoptrust.models.ecommerce.Usuario;
import com.shoptrust.services.ecommerce.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;

/**
 * Controller para autenticación de usuarios
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Formulario de login
     */
    @GetMapping("/login")
    public String mostrarLogin() {
        return "auth/login";
    }

    /**
     * Procesar login
     */
    @PostMapping("/login")
    public String login(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        Optional<Usuario> usuarioOpt = usuarioService.autenticar(email, password);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            session.setAttribute("usuario", usuario);
            session.setAttribute("idUsuario", usuario.getIdUsuario());
            return "redirect:/tienda";
        }

        redirectAttributes.addFlashAttribute("error", "Credenciales inválidas");
        return "redirect:/auth/login";
    }

    /**
     * Formulario de registro
     */
    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "auth/registro";
    }

    /**
     * Procesar registro
     */
    @PostMapping("/registro")
    public String registrar(
            @ModelAttribute Usuario usuario,
            RedirectAttributes redirectAttributes) {

        if (usuarioService.existeEmail(usuario.getEmail())) {
            redirectAttributes.addFlashAttribute("error", "El email ya está registrado");
            return "redirect:/auth/registro";
        }

        usuarioService.registrar(usuario);
        redirectAttributes.addFlashAttribute("mensaje", "Registro exitoso. Por favor inicia sesión");
        return "redirect:/auth/login";
    }

    /**
     * Cerrar sesión
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/tienda";
    }
}
