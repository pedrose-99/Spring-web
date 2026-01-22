package com.springweb.springWeb.assembler;

import com.springweb.springWeb.controllers.RestAPI.EntranteControllerAPI;
import com.springweb.springWeb.entities.Entrante;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class EntranteModelAssembler implements RepresentationModelAssembler<Entrante, EntityModel<Entrante>> {

	@Override
	public EntityModel<Entrante> toModel(Entrante entrante) {
		return EntityModel.of(entrante,
				linkTo(methodOn(EntranteControllerAPI.class).findById(entrante.getId())).withSelfRel(),
				linkTo(methodOn(EntranteControllerAPI.class).findAll()).withRel("all-entrantes")
		);
	}
}