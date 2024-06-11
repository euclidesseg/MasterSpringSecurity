package com.master.api.spring.security.master.exception.handler;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.master.api.spring.security.master.dto.ApiError;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,  AccessDeniedException accessDeniedException) throws IOException, ServletException {
         ApiError apiError = new ApiError();
                
                apiError.setBackendMessage(accessDeniedException.getLocalizedMessage());
                apiError.setUrl(request.getRequestURL().toString());
                apiError.setMethod(request.getMethod());
                apiError.setMessage("Acceso denegado. No tienes los permisos necesarios para esta solicitud");
                apiError.setTimestamp(LocalDateTime.now());

                 
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setStatus(HttpStatus.FORBIDDEN.value());
                
                // Convierte el objeto ApiError a una cadena JSON gracias a la libreria jaxson
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                String apiErrorAsJson = objectMapper.writeValueAsString(apiError);

                // Escribe la cadena JSON en la respuesta HTTP
                response.getWriter().write(apiErrorAsJson);
    }
    
}
