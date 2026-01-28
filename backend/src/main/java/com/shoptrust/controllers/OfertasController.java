package com.shoptrust.controllers;
package com.tienda.controller;
import com.tienda.model.ProductoOferta;
import com.tienda.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class OfertasController {
    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping("/ofertas/electrodomesticos")
    public String mostrarOfertasElectrodomesticos(Model model) {

        // Puedes tener varios queries o uno solo con filtro
        List<ProductoOferta> ofertas = productoRepository.findByCategoriaAndPrecioOfertaIsNotNullOrderByDescuentoPorcentajeDesc(
                "Electrodomésticos"
        );
        model.addAttribute("ofertas", ofertas);
        model.addAttribute("tituloPagina", "Ofertas en Electrodomésticos - ¡Hasta 50% OFF!");

        return "ofertas";
    }
}
