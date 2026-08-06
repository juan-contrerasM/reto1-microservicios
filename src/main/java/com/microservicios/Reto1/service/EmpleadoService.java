package com.microservicios.Reto1.service;

import org.springframework.stereotype.Service;

import com.microservicios.Reto1.exception.BadRequestException;
import com.microservicios.Reto1.exception.EmpleadoNoEncontradoException;
import com.microservicios.Reto1.model.Empleado;
import com.microservicios.Reto1.model.EstadoEmpleado;
import com.microservicios.Reto1.repository.EmpleadoRepository;

@Service
public class EmpleadoService {

	private final EmpleadoRepository empleadoRepository;

	public EmpleadoService(EmpleadoRepository empleadoRepository) {
		this.empleadoRepository = empleadoRepository;
	}

	public Empleado registrar(Empleado empleado) {
		if (empleadoRepository.existsById(empleado.getId())) {
			throw new BadRequestException("Ya existe un empleado con el id " + empleado.getId());
		}
		if (empleadoRepository.existsByEmail(empleado.getEmail())) {
			throw new BadRequestException("Ya existe un empleado registrado con el email " + empleado.getEmail());
		}
		if (empleadoRepository.existsByNumeroEmpleado(empleado.getNumeroEmpleado())) {
			throw new BadRequestException(
					"Ya existe un empleado registrado con el numeroEmpleado " + empleado.getNumeroEmpleado());
		}

		// En este reto solo se maneja ACTIVO
		empleado.setEstado(EstadoEmpleado.ACTIVO);
		return empleadoRepository.save(empleado);
	}

	public Empleado consultarPorId(String id) {
		return empleadoRepository.findById(id)
				.orElseThrow(() -> new EmpleadoNoEncontradoException(id));
	}
}
