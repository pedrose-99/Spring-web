package com.springweb.springWeb.controllers.Web;

import com.springweb.springWeb.entities.Postre;
import com.springweb.springWeb.service.PostreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/postres")
public class PostreControllerWeb {

    PostreService postreService;
    public PostreControllerWeb(PostreService postreService) {
        this.postreService = postreService;
    }

    @GetMapping("/lista")
    public String viewMain(Model model) {

        List<Postre> postres = postreService.findAllPostres();

        model.addAttribute("title", "Ver postres")
                .addAttribute("encabezado", "Vista de los postres del restaurante")
                .addAttribute("entidades", postres);

        return "list";
    }

    @GetMapping(params = "nombre")
    public String viewByName(Model model, @RequestParam String nombre) {
        model.addAttribute("title", "Buscar postre por nombre")
                .addAttribute("encabezado", "Resultado de la búsqueda");

        List<Postre> postres = postreService.findAllPostres();
        for (Postre postre : postres) {
            if (postre.getNombre().equals(nombre)) {
                model.addAttribute("entidad", postre);
                return "listByNombre";
            }
        }

        model.addAttribute("error", "El postre '" + nombre + "' no existe");
        return "listByNombre";
    }
}
