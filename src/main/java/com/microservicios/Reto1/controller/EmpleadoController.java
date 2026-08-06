package com.microservicios.Reto1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicios.Reto1.model.Empleado;
import com.microservicios.Reto1.service.EmpleadoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

	private final EmpleadoService empleadoService;

	public EmpleadoController(EmpleadoService empleadoService) {
		this.empleadoService = empleadoService;
	}

	@PostMapping
	public ResponseEntity<Empleado> registrar(@Valid @RequestBody Empleado empleado) {
		Empleado registrado = empleadoService.registrar(empleado);
		return ResponseEntity.ok(registrado);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Empleado> consultar(@PathVariable String id) {
		Empleado empleado = empleadoService.consultarPorId(id);
		return ResponseEntity.ok(empleado);
	}
}
