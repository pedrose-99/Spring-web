package com.springweb.springWeb.controllers.Web;

import com.springweb.springWeb.entities.Principal;
import com.springweb.springWeb.service.PrincipalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/principales")
public class PrincipalControllerWeb {

    PrincipalService principalService;
    public PrincipalControllerWeb(PrincipalService principalService) {
        this.principalService = principalService;
    }

    @GetMapping("/lista")
    public String viewMain(Model model) {

        List<Principal> principales = principalService.findAllPrincipales();

        model.addAttribute("title", "Ver principales")
                .addAttribute("encabezado", "Vista de los platos principales del restaurante")
                .addAttribute("entidades", principales);

        return "list";
    }

    @GetMapping(params = "nombre")
    public String viewByName(Model model, @RequestParam String nombre) {
        model.addAttribute("title", "Buscar principal por nombre")
                .addAttribute("encabezado", "Resultado de la búsqueda");

        List<Principal> principales = principalService.findAllPrincipales();
        for (Principal principal : principales) {
            if (principal.getNombre().equals(nombre)) {
                model.addAttribute("entidad", principal);
                return "listByNombre";
            }
        }

        model.addAttribute("error", "El principal '" + nombre + "' no existe");
        return "listByNombre";
    }
}
