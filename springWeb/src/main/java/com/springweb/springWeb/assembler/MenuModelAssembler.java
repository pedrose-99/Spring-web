package com.springweb.springWeb.assembler;

import com.springweb.springWeb.controllers.RestAPI.MenuControllerAPI;
import com.springweb.springWeb.controllers.RestAPI.EntranteControllerAPI;
import com.springweb.springWeb.controllers.RestAPI.PrincipalControllerAPI;
import com.springweb.springWeb.controllers.RestAPI.PostreControllerAPI;
import com.springweb.springWeb.entities.Menu;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class MenuModelAssembler implements RepresentationModelAssembler<Menu, EntityModel<Menu>>
{

	@Override
	public EntityModel<Menu> toModel(Menu menu)
	{
		EntityModel<Menu> menuModel = EntityModel.of(menu,
				linkTo(methodOn(MenuControllerAPI.class).findById(menu.getId())).withSelfRel(),
				linkTo(methodOn(MenuControllerAPI.class).findAll()).withRel("all-menus")
		);
		if (menu.getEntrante() != null) {
			menuModel.add(linkTo(methodOn(EntranteControllerAPI.class)
					.findById(menu.getEntrante().getId())).withRel("entrante"));
		}
		if (menu.getPrincipal() != null) {
			menuModel.add(linkTo(methodOn(PrincipalControllerAPI.class)
					.findById(menu.getPrincipal().getId())).withRel("principal"));
		}
		if (menu.getPostre() != null) {
			menuModel.add(linkTo(methodOn(PostreControllerAPI.class)
					.findById(menu.getPostre().getId())).withRel("postre"));
		}
		return menuModel;
	}
}
