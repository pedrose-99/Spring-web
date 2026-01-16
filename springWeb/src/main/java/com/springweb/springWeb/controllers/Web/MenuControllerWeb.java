package com.springweb.springWeb.controllers.Web;

import com.springweb.springWeb.entities.Menu;
import com.springweb.springWeb.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

        return "list";
    }
}
