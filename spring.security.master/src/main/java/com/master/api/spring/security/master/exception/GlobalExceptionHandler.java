package com.master.api.spring.security.master.exception;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.master.api.spring.security.master.dto.ApiError;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice // # Cuando se coloca esta anotación en una clase, Spring Boot la detecta y la utiliza para manejar excepciones lanzadas desde cualquier controlador REST en la aplicación.
public class GlobalExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    //=== Manejará las excepciones mas genericas de nuestra aplicación como cuando no se guarde un registro más que todo errores de servidor con codigo 500
    public ResponseEntity<?> handlerGenericException(Exception exception, HttpServletRequest request){ 

        ApiError apiError = new ApiError();

        apiError.setBackendMessage(exception.getLocalizedMessage());
        apiError.setUrl(request.getRequestURL().toString());
        apiError.setMethod(request.getMethod());
        apiError.setMessage("Error interno en el servidor, vulva a intentarlo");
        apiError.setTimestamp(LocalDateTime.now());
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
        
        }
        @ExceptionHandler(MethodArgumentNotValidException.class)
        //== Manejará las excepciones menos genericas como por ejemplo cuando guardasmos un producto y no cumpla algunas de las validadcioens agregadas por ejemplo 
        //== @DecimalMin(value = "0.01") 
        //== esta excepcion se lanza cuando no se logra el binding(bincular) de json hacia objeto java
        public ResponseEntity<?> handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request){ 
            
            ApiError apiError = new ApiError();
            
            apiError.setBackendMessage(exception.getLocalizedMessage());
            apiError.setUrl(request.getRequestURL().toString());
            apiError.setMethod(request.getMethod());
            apiError.setTimestamp(LocalDateTime.now());
            apiError.setMessage("Error en la petición enviada, "+ exception.getAllErrors().stream().map(each -> each.getDefaultMessage()).collect(Collectors.toList())); //# Estpo puede ser mas especifico porque poedemos sacar mas valores de MethodArgumentNotValidException
            apiError.setTimestamp(LocalDateTime.now());
            
            System.out.println(exception.getAllErrors().stream().map(each -> each.getDefaultMessage()).collect(Collectors.toList()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError); // Error 400
            
            }
            
            @ExceptionHandler(AccessDeniedException.class)
            //=== Manejará las restricciones de acceso, es decir al momento de intentar hacer una peticion si no se tiene acceso a ese recurso o url el controlador retornara esta excepcion
            // == Esta excepcion tiene limitaciones ya que va a retornar denied cuando no estamos autenticados y cuado no estamos autorizados basada en metodos seguros, 
            public ResponseEntity<?> handlerAccesDeniedException(AccessDeniedException exception, HttpServletRequest request){ 
                
                ApiError apiError = new ApiError();
                
                apiError.setBackendMessage(exception.getLocalizedMessage());
                apiError.setUrl(request.getRequestURL().toString());
                apiError.setMethod(request.getMethod());
                apiError.setMessage("Acceso denegado. No tienes permisos necesarios para esta solicitud");
                apiError.setTimestamp(LocalDateTime.now());
                
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiError); // devolvera un 403 error de autorizacion
                
                }
                }
                
                //.... RestControllerAdvice 
                //   # Controla excepciones
