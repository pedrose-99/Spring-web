package com.springweb.springWeb.controllers.Web;

import com.springweb.springWeb.entities.Principal;
import com.springweb.springWeb.service.PrincipalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
