package com.test.application.department;

class DepartmentNotFoundException extends RuntimeException {

	DepartmentNotFoundException(Long id) {
		super("Could not find department " + id);
	}
}
