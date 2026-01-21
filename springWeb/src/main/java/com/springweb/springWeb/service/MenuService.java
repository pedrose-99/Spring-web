package com.springweb.springWeb.service;


import com.springweb.springWeb.entities.Menu;
import com.springweb.springWeb.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Menu> findMenuById(Long id){
        return menuRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Menu> findAllMenus(){
        return menuRepository.findAll();
    }

    @Transactional
    public Menu saveMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    @Transactional
    public Menu updateMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    @Transactional
    public void deleteMenuById(Long id){
        menuRepository.deleteById(id);
    }

}
