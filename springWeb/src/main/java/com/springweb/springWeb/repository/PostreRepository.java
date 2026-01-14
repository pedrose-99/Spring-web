package com.springweb.springWeb.repository;

import com.springweb.springWeb.entities.Postre;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostreRepository extends ListCrudRepository<Postre,Long> {
}
