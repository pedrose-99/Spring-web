package com.springweb.springWeb.controllers.Web;


import com.springweb.springWeb.entities.Entrante;
import com.springweb.springWeb.service.EntranteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
