package com.springweb.springWeb.service;


import com.springweb.springWeb.entities.Entrante;
import com.springweb.springWeb.repository.EntranteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EntranteService {

    private final EntranteRepository entranteRepository;

    public EntranteService(EntranteRepository entranteRepository) {
        this.entranteRepository = entranteRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Entrante> findEntranteById(Long id){
        return entranteRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Entrante> findAllEntrantes(){
        return entranteRepository.findAll();
    }

    @Transactional
    public Entrante saveEntrante(Entrante entrante) {
        return entranteRepository.save(entrante);
    }

    @Transactional
    public Entrante updateEntrante(Entrante entrante) {
        return entranteRepository.save(entrante);
    }

    @Transactional
    public void deleteEntranteById(Long id){
        entranteRepository.deleteById(id);
    }




}
