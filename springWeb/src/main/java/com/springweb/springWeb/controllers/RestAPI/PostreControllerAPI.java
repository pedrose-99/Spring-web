package com.springweb.springWeb.controllers.RestAPI;


import com.springweb.springWeb.entities.Postre;
import com.springweb.springWeb.service.PostreService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/postres")
@Tag(name = "Postres", description = "API para gestionar postres del restaurante")
public class PostreControllerAPI {
    private final PostreService postreService;

    public PostreControllerAPI(PostreService postreService) {
        this.postreService = postreService;
    }

    @Operation(summary = "Obtener todos los postres", description = "Devuelve una lista con todos los postres disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de postres obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<Postre>> findAll() {
        return ResponseEntity.ok(postreService.findAllPostres());
    }

    @Operation(summary = "Crear un nuevo postre", description = "Guarda un nuevo postre en la base de datos")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Postre creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos del postre no validos")
    })
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Postre postre, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(e -> errores.put(e.getField(), e.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        return ResponseEntity.ok(postreService.savePostre(postre));
    }

    @Operation(summary = "Buscar postre por ID", description = "Devuelve un postre segun su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Postre encontrado"),
        @ApiResponse(responseCode = "404", description = "Postre no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Postre> findById(@PathVariable Long id) {
        return postreService.findPostreById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar un postre", description = "Actualiza los datos de un postre existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Postre actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Postre no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Postre postre, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(e -> errores.put(e.getField(), e.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        return postreService.findPostreById(id).map(postre1 -> {
            postre1.setNombre(postre.getNombre());
            postre1.setDescripcion(postre.getDescripcion());
            postre1.setPrecio(postre.getPrecio());
            return ResponseEntity.ok(postreService.updatePostre(postre1));
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar un postre", description = "Elimina un postre de la base de datos")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Postre eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Postre no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return postreService.findPostreById(id)
            .map(postre -> {
                postreService.deletePostreById(id);
                return ResponseEntity.noContent().<Void>build();
            })
            .orElse(ResponseEntity.notFound().build());
    }

}
