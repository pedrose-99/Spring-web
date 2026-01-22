package com.springweb.springWeb.assembler;

import com.springweb.springWeb.controllers.RestAPI.PostreControllerAPI;
import com.springweb.springWeb.entities.Postre;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PostreModelAssembler implements RepresentationModelAssembler<Postre, EntityModel<Postre>> {

	@Override
	public EntityModel<Postre> toModel(Postre postre) {
		return EntityModel.of(postre,
				linkTo(methodOn(PostreControllerAPI.class).findById(postre.getId())).withSelfRel(),
				linkTo(methodOn(PostreControllerAPI.class).findAll()).withRel("all-postres")
		);
	}
}