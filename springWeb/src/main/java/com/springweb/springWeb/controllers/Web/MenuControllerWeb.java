package com.springweb.springWeb.controllers.Web;

import com.springweb.springWeb.entities.Menu;
import com.springweb.springWeb.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/menus")
public class MenuControllerWeb {

    MenuService menuService;
    public MenuControllerWeb(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/lista")
    public String viewMain(Model model) {

        List<Menu> menus = menuService.findAllMenus();

        model.addAttribute("title", "Ver menus")
                .addAttribute("encabezado", "Vista de los menus del restaurante")
                .addAttribute("entidades", menus);

        return "listMenu";
    }

    @GetMapping(params = "nombreEntrante")
    public String viewByEntrante(Model model, @RequestParam String nombreEntrante) {
        model.addAttribute("title", "Buscar menus por nombre del entrante")
                .addAttribute("encabezado", "Resultado de la búsqueda");

        List<Menu> menus = menuService.findAllMenus();
        menus.removeIf(menu -> !menu.getEntrante().getNombre().equalsIgnoreCase(nombreEntrante));

        if (menus.isEmpty()) {
            model.addAttribute("error", "Ningún menu tiene el entrante con nombre '" + nombreEntrante + "'");
        } else {
            model.addAttribute("menus", menus);
        }
        return "listMenusByNombre";
    }

    @GetMapping(params = "nombrePrincipal")
    public String viewByPrincipal(Model model, @RequestParam String nombrePrincipal) {
        model.addAttribute("title", "Buscar menus por nombre del principal")
                .addAttribute("encabezado", "Resultado de la búsqueda");

        List<Menu> menus = menuService.findAllMenus();
        menus.removeIf(menu -> !menu.getPrincipal().getNombre().equalsIgnoreCase(nombrePrincipal));

        if (menus.isEmpty()) {
            model.addAttribute("error", "Ningún menu tiene el principal con nombre '" + nombrePrincipal + "'");
        } else {
            model.addAttribute("menus", menus);
        }
        return "listMenusByNombre";
    }

    @GetMapping(params = "nombrePostre")
    public String viewByPostre(Model model, @RequestParam String nombrePostre) {
        model.addAttribute("title", "Buscar menus por nombre del postre")
                .addAttribute("encabezado", "Resultado de la búsqueda");

        List<Menu> menus = menuService.findAllMenus();
        menus.removeIf(menu -> !menu.getPostre().getNombre().equalsIgnoreCase(nombrePostre));

        if (menus.isEmpty()) {
            model.addAttribute("error", "Ningún menu tiene el postre con nombre '" + nombrePostre + "'");
        } else {
            model.addAttribute("menus", menus);
        }
        return "listMenusByNombre";
    }
}
