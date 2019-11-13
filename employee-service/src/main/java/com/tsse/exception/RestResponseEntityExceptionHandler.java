package com.tsse.exception;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.persistence.EntityNotFoundException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@EnableWebMvc
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	/*@Value("#{messages['error.emp.validation']}")
	private String validationException;*/

	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class, ExecutionException.class,
			NullPointerException.class, ArrayIndexOutOfBoundsException.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
		ExceptionEntity bodyOfResponse = new ExceptionEntity();
		bodyOfResponse.setMessage("A Technical error has occurred");
		bodyOfResponse.setType("Technical Exception");
		bodyOfResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		bodyOfResponse.setErrorCode("1003");
		// String bodyOfResponse = "This should be application specific";
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler(value = { EntityNotFoundException.class })
	protected ResponseEntity<Object> handleEntityNotFound(Exception ex, WebRequest request) {
		// String bodyOfResponse = "Entity not found";
		ExceptionEntity bodyOfResponse = new ExceptionEntity();
		bodyOfResponse.setMessage(ex.getMessage());
		bodyOfResponse.setType("Technical Exception");
		bodyOfResponse.setStatusCode(HttpStatus.NOT_FOUND.toString());
		bodyOfResponse.setErrorCode("1002");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	/*
	 * @ExceptionHandler(value = { GeneralExcpetion.class }) protected
	 * ResponseEntity<Object> generalException(RuntimeException ex, WebRequest
	 * request) { // String bodyOfResponse = "General Exception :" +
	 * ex.getMessage(); ExceptionEntity bodyOfResponse = new ExceptionEntity();
	 * bodyOfResponse.setMessage(ex.getMessage());
	 * bodyOfResponse.setType("General Exception");
	 * bodyOfResponse.setStatusCode(HttpStatus.EXPECTATION_FAILED.toString());
	 * bodyOfResponse.setErrorCode("1001"); return handleExceptionInternal(ex,
	 * bodyOfResponse, new HttpHeaders(), HttpStatus.EXPECTATION_FAILED, request); }
	 */

	@ExceptionHandler({ AccessDeniedException.class })
	public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {

		ExceptionEntity bodyOfResponse = new ExceptionEntity();
		bodyOfResponse.setMessage(ex.getMessage());
		bodyOfResponse.setType("Security Exception");
		bodyOfResponse.setStatusCode(HttpStatus.FORBIDDEN.toString());
		bodyOfResponse.setErrorCode("4001");
		return new ResponseEntity<Object>(bodyOfResponse, new HttpHeaders(), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler({ PropertyReferenceException.class })
	protected ResponseEntity<Object> handlePropertyException(Exception ex, WebRequest request) {
		ExceptionEntity bodyOfResponse = new ExceptionEntity();
		bodyOfResponse.setMessage(ex.getMessage());
		bodyOfResponse.setType("Property Exception");
		bodyOfResponse.setStatusCode(HttpStatus.BAD_REQUEST.toString());
		bodyOfResponse.setErrorCode("4002");
		return new ResponseEntity<Object>(bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { ConstraintViolationException.class })
	protected ResponseEntity<Object> constraintViolationException(Exception ex, WebRequest request) {
		// String bodyOfResponse = "General Exception :" + ex.getMessage();
		ExceptionEntity bodyOfResponse = new ExceptionEntity();
		bodyOfResponse.setMessage(ex.getMessage());
		bodyOfResponse.setType("Constraint Exception");
		bodyOfResponse.setStatusCode(HttpStatus.BAD_REQUEST.toString());
		bodyOfResponse.setErrorCode("1001");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	/**
	 * cant handle @ExceptionHandler because of ambiguity in base class thats why we
	 * override it
	 */
	// for @Valid exceptions
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		// String bodyOfResponse = "General Exception :" + ex.getMessage();
		BindingResult result = ex.getBindingResult();
		List<org.springframework.validation.FieldError> fieldErrors = result.getFieldErrors();

		StringBuilder str = new StringBuilder("");
		Map<String, String> errorsMap = new HashMap<>();
		for (org.springframework.validation.FieldError fieldError : fieldErrors) {
			// str.append(fieldError.getField() + ": " + fieldError.getDefaultMessage());
			errorsMap.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		
		// for custom annotation Errors
		// if(str.length() == 0) {
		List<ObjectError> errors = result.getAllErrors();
		for (Iterator iterator = errors.iterator(); iterator.hasNext();) {
			ObjectError objectError = (ObjectError) iterator.next();
			str.append(objectError.getDefaultMessage() + " ");
			errorsMap.put(objectError.getObjectName(), objectError.getDefaultMessage());
		}
		// }
		ExceptionEntity bodyOfResponse = new ExceptionEntity();
		//bodyOfResponse.setMessage(str.toString());
		bodyOfResponse.setType("validationException");
		bodyOfResponse.setStatusCode(HttpStatus.BAD_REQUEST.toString());
		bodyOfResponse.setErrorCode("1002");
		bodyOfResponse.setErrors(errorsMap);
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
}