package com.springweb.springWeb.service;


import com.springweb.springWeb.entities.Principal;
import com.springweb.springWeb.repository.PrincipalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PrincipalService {

    private final PrincipalRepository principalRepository;

    public PrincipalService(PrincipalRepository principalRepository) {
        this.principalRepository = principalRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Principal> findPrincipalById(Long id){
        return principalRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Principal> findAllPrincipales(){
        return principalRepository.findAll();
    }

    @Transactional
    public Principal savePrincipal(Principal principal) {
        return principalRepository.save(principal);
    }

    @Transactional
    public Principal updatePrincipal(Principal principal) {
        return principalRepository.save(principal);
    }

    @Transactional
    public void deletePrincipalById(Long id){
        principalRepository.deleteById(id);
    }

    @Transactional
    public void deletePrincipal(Principal principal){
        principalRepository.delete(principal);
    }

}
