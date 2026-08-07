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

import com.microservicios.Reto1.dto.ApiError;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(ex.getMessage()));
	}

	@ExceptionHandler(ConflictException.class)
	public ResponseEntity<ApiError> handleConflict(ConflictException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiError(ex.getMessage()));
	}

	@ExceptionHandler(EmpleadoNoEncontradoException.class)
	public ResponseEntity<ApiError> handleEmpleadoNoEncontrado(EmpleadoNoEncontradoException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(ex.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
		String mensaje = ex.getBindingResult().getFieldErrors().stream()
				.findFirst()
				.map(error -> error.getDefaultMessage())
				.orElse("Datos de entrada inválidos");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(mensaje));
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiError> handleUnreadable(HttpMessageNotReadableException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ApiError("El cuerpo de la solicitud es inválido o está mal formado"));
	}

	@ExceptionHandler({
			NoHandlerFoundException.class,
			NoResourceFoundException.class,
			HttpRequestMethodNotSupportedException.class
	})
	public ResponseEntity<ApiError> handleRecursoNoEncontrado(Exception ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError("Recurso no encontrado"));
	}
}
