package com.springweb.springWeb.controllers.RestAPI;


import com.springweb.springWeb.entities.Entrante;
import com.springweb.springWeb.service.EntranteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entrantes")
public class EntranteControllerAPI {
    private final EntranteService entranteService;

    public EntranteControllerAPI(EntranteService entranteService) {
        this.entranteService = entranteService;
    }

    @GetMapping
    public List<Entrante> findAll() {
        return entranteService.findAllEntrantes();
    }

    @PostMapping
    public Entrante save(@RequestBody Entrante entrante) {
        return entranteService.saveEntrante(entrante);
    }

    @GetMapping("/{id}") //Si existe, envuelve en ResponseEntity y devuelve OK(200), si no devuelve not found
    public ResponseEntity<Entrante> findById(@PathVariable Long id) {
        return entranteService.findEntranteById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Entrante> update(@PathVariable Long id, @RequestBody Entrante entrante) {
        return entranteService.findEntranteById(id).map(entrante1 ->  {
            entrante1.setNombre(entrante.getNombre());
            entrante1.setDescripcion(entrante.getDescripcion());
            entrante1.setPrecio(entrante.getPrecio());
            return ResponseEntity.ok(entranteService.updateEntrante(entrante1));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Entrante> delete(@PathVariable Long id) {
        if (entranteService.findEntranteById(id).isPresent()) {
            entranteService.deleteEntranteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
