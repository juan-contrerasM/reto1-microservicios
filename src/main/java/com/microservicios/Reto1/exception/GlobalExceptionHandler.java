package com.microservicios.Reto1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<String> handleBadRequest(BadRequestException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}

	@ExceptionHandler(EmpleadoNoEncontradoException.class)
	public ResponseEntity<String> handleEmpleadoNoEncontrado(EmpleadoNoEncontradoException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleValidation(MethodArgumentNotValidException ex) {
		String mensaje = ex.getBindingResult().getFieldErrors().stream()
				.findFirst()
				.map(error -> error.getDefaultMessage())
				.orElse("Datos de entrada inválidos");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<String> handleUnreadable(HttpMessageNotReadableException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body("El cuerpo de la solicitud es inválido o está mal formado");
	}

	@ExceptionHandler({
			NoHandlerFoundException.class,
			NoResourceFoundException.class,
			HttpRequestMethodNotSupportedException.class
	})
	public ResponseEntity<String> handleRecursoNoEncontrado(Exception ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recurso no encontrado");
	}
}
