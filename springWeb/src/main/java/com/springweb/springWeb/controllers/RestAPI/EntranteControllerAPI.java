package com.springweb.springWeb.controllers.RestAPI;


import com.springweb.springWeb.entities.Entrante;
import com.springweb.springWeb.service.EntranteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<Entrante> findAll() {
        return entranteService.findAllEntrantes();
    }

    @Operation(summary = "Crear un nuevo entrante", description = "Guarda un nuevo entrante en la base de datos")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Entrante creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos del entrante no validos")
    })
    @PostMapping
    public Entrante save(@RequestBody Entrante entrante) {
        return entranteService.saveEntrante(entrante);
    }

    @Operation(summary = "Buscar entrante por ID", description = "Devuelve un entrante segun su ID")
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
    public ResponseEntity<Entrante> update(@PathVariable Long id, @RequestBody Entrante entrante) {
        return entranteService.findEntranteById(id).map(entrante1 ->  {
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
    public ResponseEntity<Entrante> delete(@PathVariable Long id) {
        if (entranteService.findEntranteById(id).isPresent()) {
            entranteService.deleteEntranteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
