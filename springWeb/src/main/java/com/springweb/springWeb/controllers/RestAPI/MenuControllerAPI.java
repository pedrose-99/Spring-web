package com.springweb.springWeb.controllers.RestAPI;


import com.springweb.springWeb.entities.Menu;
import com.springweb.springWeb.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
@Tag(name = "Menus", description = "API para gestionar menus del restaurante")
public class MenuControllerAPI {
    private final MenuService menuService;

    public MenuControllerAPI(MenuService menuService) {
        this.menuService = menuService;
    }

    @Operation(summary = "Obtener todos los menus", description = "Devuelve una lista con todos los menus disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de menus obtenida correctamente")
    @GetMapping
    public List<Menu> findAll() {
        return menuService.findAllMenus();
    }

    @Operation(summary = "Crear un nuevo menu", description = "Guarda un nuevo menu en la base de datos")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Menu creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos del menu no validos")
    })
    @PostMapping
    public Menu save(@RequestBody Menu menu) {
        return menuService.saveMenu(menu);
    }

    @Operation(summary = "Buscar menu por ID", description = "Devuelve un menu segun su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Menu encontrado"),
        @ApiResponse(responseCode = "404", description = "Menu no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Menu> findById(@PathVariable Long id) {
        return menuService.findMenuById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar un menu", description = "Actualiza los datos de un menu existente, incluyendo entrante, principal y postre")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Menu actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Menu no encontrado")
    })
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

    @Operation(summary = "Eliminar un menu", description = "Elimina un menu de la base de datos")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Menu eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Menu no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Menu> delete(@PathVariable Long id) {
        if (menuService.findMenuById(id).isPresent()) {
            menuService.deleteMenuById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
