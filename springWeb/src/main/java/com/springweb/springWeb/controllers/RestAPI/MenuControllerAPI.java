package com.springweb.springWeb.controllers.RestAPI;


import com.springweb.springWeb.entities.Menu;
import com.springweb.springWeb.service.EntranteService;
import com.springweb.springWeb.service.MenuService;
import com.springweb.springWeb.service.PostreService;
import com.springweb.springWeb.service.PrincipalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/menus")
@Tag(name = "Menus", description = "API para gestionar menus del restaurante")
public class MenuControllerAPI {
    private final MenuService menuService;
    private final EntranteService entranteService;
    private final PrincipalService principalService;
    private final PostreService postreService;

    public MenuControllerAPI(MenuService menuService, EntranteService entranteService,
                             PrincipalService principalService, PostreService postreService) {
        this.menuService = menuService;
        this.entranteService = entranteService;
        this.principalService = principalService;
        this.postreService = postreService;
    }

    @Operation(summary = "Obtener todos los menus", description = "Devuelve una lista con todos los menus disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de menus obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<Menu>> findAll() {
        return ResponseEntity.ok(menuService.findAllMenus());
    }

    @Operation(summary = "Crear un nuevo menu", description = "Guarda un nuevo menu en la base de datos")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Menu creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos del menu no validos")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        content = @Content(examples = @ExampleObject(value = """
            {
                "nombre": "Menu del dia",
                "descripcion": "Menu completo",
                "entrante": {"id": 1},
                "principal": {"id": 1},
                "postre": {"id": 1}
            }
            """))
    )
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Menu menu, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(e -> errores.put(e.getField(), e.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        var entrante = entranteService.findEntranteById(menu.getEntrante().getId());
        var principal = principalService.findPrincipalById(menu.getPrincipal().getId());
        var postre = postreService.findPostreById(menu.getPostre().getId());

        if (entrante.isEmpty() || principal.isEmpty() || postre.isEmpty()) {
            Map<String, String> errores = new HashMap<>();
            if (entrante.isEmpty()) errores.put("entrante", "Entrante no encontrado");
            if (principal.isEmpty()) errores.put("principal", "Principal no encontrado");
            if (postre.isEmpty()) errores.put("postre", "Postre no encontrado");
            return ResponseEntity.badRequest().body(errores);
        }

        menu.setEntrante(entrante.get());
        menu.setPrincipal(principal.get());
        menu.setPostre(postre.get());

        Menu savedMenu = menuService.saveMenu(menu);
        return ResponseEntity.ok(menuService.findMenuById(savedMenu.getId()).orElse(savedMenu));
    }

    @Operation(summary = "Buscar menu por ID", description = "Devuelve un menu según su ID")
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
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        content = @Content(examples = @ExampleObject(value = """
            {
                "nombre": "Menu del dia",
                "descripcion": "Menu completo",
                "entrante": {"id": 1},
                "principal": {"id": 1},
                "postre": {"id": 1}
            }
            """))
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Menu menu, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(e -> errores.put(e.getField(), e.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        var entrante = entranteService.findEntranteById(menu.getEntrante().getId());
        var principal = principalService.findPrincipalById(menu.getPrincipal().getId());
        var postre = postreService.findPostreById(menu.getPostre().getId());

        if (entrante.isEmpty() || principal.isEmpty() || postre.isEmpty()) {
            Map<String, String> errores = new HashMap<>();
            if (entrante.isEmpty()) errores.put("entrante", "Entrante no encontrado");
            if (principal.isEmpty()) errores.put("principal", "Principal no encontrado");
            if (postre.isEmpty()) errores.put("postre", "Postre no encontrado");
            return ResponseEntity.badRequest().body(errores);
        }

        return menuService.findMenuById(id).map(menu1 -> {
            menu1.setNombre(menu.getNombre());
            menu1.setDescripcion(menu.getDescripcion());
            menu1.setPrecio(menu.getPrecio());
            menu1.setEntrante(entrante.get());
            menu1.setPrincipal(principal.get());
            menu1.setPostre(postre.get());
            Menu updated = menuService.updateMenu(menu1);
            return ResponseEntity.ok(menuService.findMenuById(updated.getId()).orElse(updated));
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar un menu", description = "Elimina un menu de la base de datos")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Menu eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Menu no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return menuService.findMenuById(id)
            .map(menu -> {
                menuService.deleteMenuById(id);
                return ResponseEntity.noContent().<Void>build();
            })
            .orElse(ResponseEntity.notFound().build());
    }

}
