package com.springweb.springWeb.assembler;

import com.springweb.springWeb.controllers.RestAPI.PrincipalControllerAPI;
import com.springweb.springWeb.entities.Principal;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PrincipalModelAssembler implements RepresentationModelAssembler<Principal, EntityModel<Principal>> {

	@Override
	public EntityModel<Principal> toModel(Principal principal) {
		return EntityModel.of(principal,
				linkTo(methodOn(PrincipalControllerAPI.class).findById(principal.getId())).withSelfRel(),
				linkTo(methodOn(PrincipalControllerAPI.class).findAll()).withRel("all-principales")
		);
	}
}