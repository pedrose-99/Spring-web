package com.springweb.springWeb.repository;

import com.springweb.springWeb.entities.Principal;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrincipalRepository extends ListCrudRepository<Principal,Long> {
}
