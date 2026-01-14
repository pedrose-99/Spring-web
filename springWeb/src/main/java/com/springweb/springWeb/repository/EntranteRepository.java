package com.springweb.springWeb.repository;

import com.springweb.springWeb.entities.Entrante;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntranteRepository extends ListCrudRepository<Entrante,Long> {
}
