package com.master.api.spring.security.master.exception.handler;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.master.api.spring.security.master.dto.ApiError;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
//== clase manejadora de excepciones forbiben o no authenticado, basada authorizacines por url o http, solo maneja excepciones 
//== por ejemplo cuando se intenta acceder a un recurso sin estar authenticado
//== authenticado ya que si se manda una peticion estando authenticado a un recurso sin authorizacion retornara un 403 por defecto por lo que esta excepcion
//== no nos funcionara, necesitamos otra excepcion cuando es un error del tipo no authorizado 
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        ApiError apiError = new ApiError();
        // Establece el mensaje del backend con el mensaje localizado de la excepción
        apiError.setBackendMessage(authException.getLocalizedMessage());

        // Establece la URL que el cliente intentó acceder
        apiError.setUrl(request.getRequestURL().toString());

        // Establece el método HTTP utilizado (GET, POST, etc.)
        apiError.setMethod(request.getMethod());

        // Mensaje genérico para el cliente indicando que no se encontraron credenciales
        // de autenticación
        apiError.setMessage(
                "No se encontraron credenciales de autenticación por favor inicie sesion para acceder a esta función");

        // Establece la marca de tiempo actual
        apiError.setTimestamp(LocalDateTime.now());

        // Configura la respuesta HTTP para que el contenido sea JSON
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // Configura el código de estado HTTP a 401 (No Autorizado)
        response.setStatus(HttpStatus.UNAUTHORIZED.value());


        // Convierte el objeto ApiError a una cadena JSON gracias a la libreria jaxson
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String apiErrorAsJson = objectMapper.writeValueAsString(apiError);

        // Escribe la cadena JSON en la respuesta HTTP
        response.getWriter().write(apiErrorAsJson);
    }

}
