package com.springweb.springWeb.repository;

import com.springweb.springWeb.entities.Menu;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends ListCrudRepository<Menu,Long> {
}
