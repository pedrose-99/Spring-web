package com.springweb.springWeb.service;


import com.springweb.springWeb.entities.Postre;
import com.springweb.springWeb.repository.PostreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PostreService {

    private final PostreRepository postreRepository;

    public PostreService(PostreRepository postreRepository) {
        this.postreRepository = postreRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Postre> findPostreById(Long id){
        return postreRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Postre> findAllPostres(){
        return postreRepository.findAll();
    }

    @Transactional
    public Postre savePostre(Postre postre) {
        return postreRepository.save(postre);
    }

    @Transactional
    public Postre updatePostre(Postre postre) {
        return postreRepository.save(postre);
    }

    @Transactional
    public void deletePostreById(Long id){
        postreRepository.deleteById(id);
    }

}
