package com.springweb.springWeb.controllers.RestAPI;


import com.springweb.springWeb.entities.Menu;
import com.springweb.springWeb.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
public class MenuControllerAPI {
    private final MenuService menuService;

    public MenuControllerAPI(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public List<Menu> findAll() {
        return menuService.findAllMenus();
    }

    @PostMapping
    public Menu save(@RequestBody Menu menu) {
        return menuService.saveMenu(menu);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Menu> findById(@PathVariable Long id) {
        return menuService.findMenuById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Menu> update(@PathVariable Long id, @RequestBody Menu menu) {
        return menuService.findMenuById(id).map(menu1 -> {
            menu1.setNombre(menu.getNombre());
            menu1.setDescripcion(menu.getDescripcion());
            menu1.setPrecio(menu.getPrecio());
            menu1.setEntrante(menu.getEntrante());
            menu1.setPrincipal(menu.getPrincipal());
            menu1.setPostre(menu.getPostre());
            return ResponseEntity.ok(menuService.updateMenu(menu1));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Menu> delete(@PathVariable Long id) {
        if (menuService.findMenuById(id).isPresent()) {
            menuService.deleteMenuById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
