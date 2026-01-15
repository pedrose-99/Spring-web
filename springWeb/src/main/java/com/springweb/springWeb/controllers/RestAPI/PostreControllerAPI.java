package com.springweb.springWeb.controllers.RestAPI;


import com.springweb.springWeb.entities.Postre;
import com.springweb.springWeb.service.PostreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/postres")
public class PostreControllerAPI {
    private final PostreService postreService;

    public PostreControllerAPI(PostreService postreService) {
        this.postreService = postreService;
    }

    @GetMapping
    public List<Postre> findAll() {
        return postreService.findAllPostres();
    }

    @PostMapping
    public Postre save(@RequestBody Postre postre) {
        return postreService.savePostre(postre);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Postre> findById(@PathVariable Long id) {
        return postreService.findPostreById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Postre> update(@PathVariable Long id, @RequestBody Postre postre) {
        return postreService.findPostreById(id).map(postre1 -> {
            postre1.setNombre(postre.getNombre());
            postre1.setDescripcion(postre.getDescripcion());
            postre1.setPrecio(postre.getPrecio());
            return ResponseEntity.ok(postreService.updatePostre(postre1));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Postre> delete(@PathVariable Long id) {
        if (postreService.findPostreById(id).isPresent()) {
            postreService.deletePostreById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
