package com.test.application.department;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepartmentController {

	private final DepartmentRepository departmentRepo;
	private final DepartmentModelAssembler assembler;
	
	DepartmentController(DepartmentRepository departmentRepository, DepartmentModelAssembler assembler) {
		this.departmentRepo = departmentRepository;
		this.assembler = assembler;
	}
	
	@GetMapping("/departments")
	public CollectionModel<EntityModel<Department>> findAll() {

		List<EntityModel<Department>> departments = departmentRepo.findAll().stream()
				.map(assembler::toModel) 
				.collect(Collectors.toList());

		return CollectionModel.of(departments, //
						linkTo(methodOn(DepartmentController.class).findAll()).withSelfRel());
	}
	
	@PostMapping("/departments")
	ResponseEntity<?> newDepartment(@RequestBody Department department) {

		EntityModel<Department> entityModel = 
				assembler.toModel(departmentRepo.save(department));
		
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}
	
	@GetMapping("/departments/{id}")
	public EntityModel<Department> findOne(@PathVariable Long id) {
		Department department = departmentRepo.findById(id)
				.orElseThrow(() -> new DepartmentNotFoundException(id));
		
		return assembler.toModel(department);
	}
	
	@PutMapping("/departments/{id}")
	ResponseEntity<?> replaceDepartment(@RequestBody Department newDepartment, @PathVariable long id) {

		Department updatedDepartment = departmentRepo.findById(id)
				.map(department -> {
					department.setName(newDepartment.getName());
					return departmentRepo.save(department);
				})
				.orElseGet(() -> {
					newDepartment.setId(id);
					return departmentRepo.save(newDepartment);
				});

		EntityModel<Department> entityModel = assembler.toModel(updatedDepartment);
		
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);		
	}
	
	@DeleteMapping("/departments/{id}")
	ResponseEntity<?> deleteDepartment(@PathVariable Long id) {
		departmentRepo.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}

}
