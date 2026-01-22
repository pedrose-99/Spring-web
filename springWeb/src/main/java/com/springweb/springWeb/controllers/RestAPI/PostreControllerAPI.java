package com.springweb.springWeb.controllers.RestAPI;

import com.springweb.springWeb.assembler.PostreModelAssembler;
import com.springweb.springWeb.entities.Postre;
import com.springweb.springWeb.service.PostreService;
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
@RequestMapping("/api/postres")
@Tag(name = "Postres", description = "API para gestionar postres del restaurante")
public class PostreControllerAPI {
    private final PostreService postreService;
    private final PostreModelAssembler assembler;

    public PostreControllerAPI(PostreService postreService, PostreModelAssembler assembler) {
        this.postreService = postreService;
        this.assembler = assembler;
    }

    @Operation(summary = "Obtener todos los postres", description = "Devuelve una lista con todos los postres disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de postres obtenida correctamente")
    @GetMapping
    public CollectionModel<EntityModel<Postre>> findAll() {
        List<EntityModel<Postre>> postres = postreService.findAllPostres().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(postres,
                linkTo(methodOn(PostreControllerAPI.class).findAll()).withSelfRel());
    }

    @Operation(summary = "Crear un nuevo postre", description = "Guarda un nuevo postre en la base de datos")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Postre creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos del postre no validos")
    })
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Postre postre, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(e -> errores.put(e.getField(), e.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        Postre saved = postreService.savePostre(postre);
        EntityModel<Postre> entityModel = assembler.toModel(saved);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @Operation(summary = "Buscar postre por ID", description = "Devuelve un postre segun su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Postre encontrado"),
        @ApiResponse(responseCode = "404", description = "Postre no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Postre>> findById(@PathVariable Long id) {
        return postreService.findPostreById(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
            Postre updated = postreService.updatePostre(postre1);
            return ResponseEntity.ok(assembler.toModel(updated));
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
