package com.springweb.springWeb.controllers.RestAPI;


import com.springweb.springWeb.entities.Principal;
import com.springweb.springWeb.service.PrincipalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/principales")
public class PrincipalControllerAPI {
    private final PrincipalService principalService;

    public PrincipalControllerAPI(PrincipalService principalService) {
        this.principalService = principalService;
    }

    @GetMapping
    public List<Principal> findAll() {
        return principalService.findAllPrincipales();
    }

    @PostMapping
    public Principal save(@RequestBody Principal principal) {
        return principalService.savePrincipal(principal);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Principal> findById(@PathVariable Long id) {
        return principalService.findPrincipalById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Principal> update(@PathVariable Long id, @RequestBody Principal principal) {
        return principalService.findPrincipalById(id).map(principal1 -> {
            principal1.setNombre(principal.getNombre());
            principal1.setDescripcion(principal.getDescripcion());
            principal1.setPrecio(principal.getPrecio());
            return ResponseEntity.ok(principalService.updatePrincipal(principal1));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Principal> delete(@PathVariable Long id) {
        if (principalService.findPrincipalById(id).isPresent()) {
            principalService.deletePrincipalById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
