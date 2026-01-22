package com.springweb.springWeb.controllers.RestAPI;


import com.springweb.springWeb.entities.Entrante;
import com.springweb.springWeb.service.EntranteService;
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
@RequestMapping("/api/entrantes")
@Tag(name = "Entrantes", description = "API para gestionar entrantes del restaurante")
public class EntranteControllerAPI {
    private final EntranteService entranteService;

    public EntranteControllerAPI(EntranteService entranteService) {
        this.entranteService = entranteService;
    }

    @Operation(summary = "Obtener todos los entrantes", description = "Devuelve una lista con todos los entrantes disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de entrantes obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<Entrante>> findAll() {
        return ResponseEntity.ok(entranteService.findAllEntrantes());
    }

    @Operation(summary = "Crear un nuevo entrante", description = "Guarda un nuevo entrante en la base de datos")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Entrante creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos del entrante no validos")
    })
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Entrante entrante, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(e -> errores.put(e.getField(), e.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        return ResponseEntity.ok(entranteService.saveEntrante(entrante));
    }

    @Operation(summary = "Buscar entrante por ID", description = "Devuelve un entrante según su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Entrante encontrado"),
        @ApiResponse(responseCode = "404", description = "Entrante no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Entrante> findById(@PathVariable Long id) {
        return entranteService.findEntranteById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar un entrante", description = "Actualiza los datos de un entrante existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Entrante actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Entrante no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Entrante entrante, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(e -> errores.put(e.getField(), e.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        return entranteService.findEntranteById(id).map(entrante1 -> {
            entrante1.setNombre(entrante.getNombre());
            entrante1.setDescripcion(entrante.getDescripcion());
            entrante1.setPrecio(entrante.getPrecio());
            return ResponseEntity.ok(entranteService.updateEntrante(entrante1));
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar un entrante", description = "Elimina un entrante de la base de datos")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Entrante eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Entrante no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return entranteService.findEntranteById(id)
            .map(entrante -> {
                entranteService.deleteEntranteById(id);
                return ResponseEntity.noContent().<Void>build();
            })
            .orElse(ResponseEntity.notFound().build());
    }

}
