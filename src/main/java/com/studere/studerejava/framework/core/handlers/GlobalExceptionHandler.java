package com.studere.studerejava.framework.core.handlers;

import com.studere.studerejava.framework.core.exceptions.BaseException;
import com.studere.studerejava.framework.models.dto.response.ErrorResponseDTO;
import com.studere.studerejava.framework.models.dto.response.ValidationErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponseDTO> handleBaseException(BaseException ex) {
        HttpStatus status = getResponseStatus(ex);
        ErrorResponseDTO responseDTO = new ErrorResponseDTO(ex.getMessage(), status.value());
        return ResponseEntity.status(status).body(responseDTO);
    }

    private HttpStatus getResponseStatus(BaseException ex) {
        Class<?> exceptionClass = ex.getClass();
        if (exceptionClass.isAnnotationPresent(ResponseStatus.class)) {
            ResponseStatus responseStatus = exceptionClass.getAnnotation(ResponseStatus.class);
            return responseStatus.value();
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponseDTO> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        ValidationErrorResponseDTO errorResponse = new ValidationErrorResponseDTO(errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
