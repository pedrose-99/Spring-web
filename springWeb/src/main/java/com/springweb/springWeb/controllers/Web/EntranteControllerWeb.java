package com.springweb.springWeb.controllers.Web;


import com.springweb.springWeb.entities.Entrante;
import com.springweb.springWeb.service.EntranteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/entrantes")
public class EntranteControllerWeb {

    EntranteService entranteService;
    public EntranteControllerWeb(EntranteService entranteService) {
        this.entranteService = entranteService;
    }

    @GetMapping("/lista")
    public String viewMain(Model model) {

        List<Entrante> entrantes=entranteService.findAllEntrantes();

        //Atributo que hemos puesto en el view.html para que sea el título de la página
        model.addAttribute("title", "Ver entrantes")
                .addAttribute("encabezado","Vista de los entrantes del restaurante" )
                .addAttribute("entidades",entrantes);


        return "list";

    }

    @GetMapping(params = "nombre")
    public String viewByName(Model model, @RequestParam String nombre) {
        model.addAttribute("title", "Buscar entrante por nombre")
                .addAttribute("encabezado", "Resultado de la búsqueda");

        List<Entrante> entrantes = entranteService.findAllEntrantes();
        for (Entrante entrante : entrantes) {
            if (entrante.getNombre().equals(nombre)) {
                model.addAttribute("entidad", entrante);
                return "listByNombre";
            }
        }

        model.addAttribute("error", "El entrante '" + nombre + "' no existe");
        return "listByNombre";
    }
}
