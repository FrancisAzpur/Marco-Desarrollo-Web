package com.shoptrust.controllers.ecommerce;

import com.shoptrust.models.ecommerce.Carrito;
import com.shoptrust.services.ecommerce.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.math.BigDecimal;
import java.util.List;

/**
 * Controller para gestionar el carrito de compras
 */
@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    /**
     * Ver carrito
     * TODO: Obtener idUsuario de la sesión
     */
    @GetMapping
    public String verCarrito(Model model) {
        Long idUsuario = 1L; // TODO: Obtener de sesión

        List<Carrito> items = carritoService.obtenerCarritoPorUsuario(idUsuario);
        BigDecimal total = carritoService.calcularTotal(idUsuario);

        model.addAttribute("items", items);
        model.addAttribute("total", total);
        model.addAttribute("cantidadItems", items.size());

        return "tienda/carrito";
    }

    /**
     * Agregar producto al carrito
     */
    @PostMapping("/agregar")
    public String agregarAlCarrito(
            @RequestParam Long idProducto,
            @RequestParam(defaultValue = "1") Integer cantidad,
            RedirectAttributes redirectAttributes) {

        Long idUsuario = 1L; // TODO: Obtener de sesión

        Carrito carrito = carritoService.agregarAlCarrito(idUsuario, idProducto, cantidad);

        if (carrito != null) {
            redirectAttributes.addFlashAttribute("mensaje", "Producto agregado al carrito");
        } else {
            redirectAttributes.addFlashAttribute("error", "No se pudo agregar el producto");
        }

        return "redirect:/carrito";
    }

    /**
     * Actualizar cantidad de un item
     */
    @PostMapping("/actualizar/{idCarrito}")
    public String actualizarCantidad(
            @PathVariable Long idCarrito,
            @RequestParam Integer cantidad,
            RedirectAttributes redirectAttributes) {

        boolean actualizado = carritoService.actualizarCantidad(idCarrito, cantidad);

        if (actualizado) {
            redirectAttributes.addFlashAttribute("mensaje", "Cantidad actualizada");
        } else {
            redirectAttributes.addFlashAttribute("error", "No se pudo actualizar");
        }

        return "redirect:/carrito";
    }

    /**
     * Eliminar item del carrito
     */
    @PostMapping("/eliminar/{idCarrito}")
    public String eliminarItem(
            @PathVariable Long idCarrito,
            RedirectAttributes redirectAttributes) {

        carritoService.eliminarItem(idCarrito);
        redirectAttributes.addFlashAttribute("mensaje", "Producto eliminado del carrito");

        return "redirect:/carrito";
    }

    /**
     * Limpiar carrito completo
     */
    @PostMapping("/limpiar")
    public String limpiarCarrito(RedirectAttributes redirectAttributes) {
        Long idUsuario = 1L; // TODO: Obtener de sesión

        carritoService.limpiarCarrito(idUsuario);
        redirectAttributes.addFlashAttribute("mensaje", "Carrito vaciado");

        return "redirect:/carrito";
    }

    /**
     * Contador de items (para navbar)
     */
    @GetMapping("/count")
    @ResponseBody
    public Long contarItems() {
        Long idUsuario = 1L; // TODO: Obtener de sesión
        return carritoService.contarItems(idUsuario);
    }
}
