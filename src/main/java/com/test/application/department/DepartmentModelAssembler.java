package com.test.application.department;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class DepartmentModelAssembler implements RepresentationModelAssembler<Department, EntityModel<Department>> {
	
	@Override
	public EntityModel<Department> toModel(Department department) {
		return EntityModel.of(department,
				linkTo(methodOn(DepartmentController.class).findOne(department.getId())).withSelfRel(),
				linkTo(methodOn(DepartmentController.class).findAll()).withRel("departments"));
	}
}
