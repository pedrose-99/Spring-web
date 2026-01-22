package com.springweb.springWeb.controllers.RestAPI;


import com.springweb.springWeb.entities.Principal;
import com.springweb.springWeb.service.PrincipalService;
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
@RequestMapping("/api/principales")
@Tag(name = "Principales", description = "API para gestionar platos principales del restaurante")
public class PrincipalControllerAPI {
    private final PrincipalService principalService;

    public PrincipalControllerAPI(PrincipalService principalService) {
        this.principalService = principalService;
    }

    @Operation(summary = "Obtener todos los principales", description = "Devuelve una lista con todos los platos principales disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de principales obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<Principal>> findAll() {
        return ResponseEntity.ok(principalService.findAllPrincipales());
    }

    @Operation(summary = "Crear un nuevo principal", description = "Guarda un nuevo plato principal en la base de datos")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Principal creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos del principal no validos")
    })
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Principal principal, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(e -> errores.put(e.getField(), e.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        return ResponseEntity.ok(principalService.savePrincipal(principal));
    }

    @Operation(summary = "Buscar principal por ID", description = "Devuelve un plato principal según su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Principal encontrado"),
        @ApiResponse(responseCode = "404", description = "Principal no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Principal> findById(@PathVariable Long id) {
        return principalService.findPrincipalById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar un principal", description = "Actualiza los datos de un plato principal existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Principal actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Principal no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Principal principal, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(e -> errores.put(e.getField(), e.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        return principalService.findPrincipalById(id).map(principal1 -> {
            principal1.setNombre(principal.getNombre());
            principal1.setDescripcion(principal.getDescripcion());
            principal1.setPrecio(principal.getPrecio());
            return ResponseEntity.ok(principalService.updatePrincipal(principal1));
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar un principal", description = "Elimina un plato principal de la base de datos")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Principal eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Principal no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return principalService.findPrincipalById(id)
            .map(principal -> {
                principalService.deletePrincipalById(id);
                return ResponseEntity.noContent().<Void>build();
            })
            .orElse(ResponseEntity.notFound().build());
    }

}
