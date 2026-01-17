package com.springweb.springWeb.controllers.Web;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cliente")
public class ClienteInfoControllerWeb {

    @GetMapping("/info")
    public String infoCliente(
            Model model,
            @RequestHeader("User-Agent") String navegador,
            @RequestHeader(value = "Accept-Language", defaultValue = "desconocido") String idiomaHeader,
            @CookieValue(name = "idioma", defaultValue = "no definido") String idiomaCookie) {

        model.addAttribute("title", "Informacion del Cliente");
        model.addAttribute("navegador", navegador);
        model.addAttribute("idiomaHeader", idiomaHeader);
        model.addAttribute("idiomaCookie", idiomaCookie);

        return "clienteInfo";
    }

}