package com.springweb.springWeb.controllers.RestAPI;

import com.springweb.springWeb.assembler.EntranteModelAssembler;
import com.springweb.springWeb.entities.Entrante;
import com.springweb.springWeb.service.EntranteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/entrantes")
@Tag(name = "Entrantes", description = "API para gestionar entrantes del restaurante")
public class EntranteControllerAPI {
    private final EntranteService entranteService;
    private final EntranteModelAssembler assembler;

    public EntranteControllerAPI(EntranteService entranteService, EntranteModelAssembler assembler) {
        this.entranteService = entranteService;
        this.assembler = assembler;
    }

    @Operation(summary = "Obtener todos los entrantes", description = "Devuelve una lista con todos los entrantes disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de entrantes obtenida correctamente")
    @GetMapping
    public CollectionModel<EntityModel<Entrante>> findAll() {
        List<EntityModel<Entrante>> entrantes = entranteService.findAllEntrantes().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(entrantes,
                linkTo(methodOn(EntranteControllerAPI.class).findAll()).withSelfRel());
    }

    @Operation(summary = "Crear un nuevo entrante", description = "Guarda un nuevo entrante en la base de datos")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Entrante creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos del entrante no validos")
    })
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Entrante entrante, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(e -> errores.put(e.getField(), e.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        Entrante saved = entranteService.saveEntrante(entrante);
        EntityModel<Entrante> entityModel = assembler.toModel(saved);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @Operation(summary = "Buscar entrante por ID", description = "Devuelve un entrante segun su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Entrante encontrado"),
            @ApiResponse(responseCode = "404", description = "Entrante no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Entrante>> findById(@PathVariable Long id) {
        return entranteService.findEntranteById(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
            Entrante updated = entranteService.updateEntrante(entrante1);
            return ResponseEntity.ok(assembler.toModel(updated));
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