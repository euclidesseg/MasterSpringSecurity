package com.master.api.spring.security.master.config_security.filter;

import java.io.IOException;
import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.master.api.spring.security.master.persistance.entity.Security.JwtToken;
import com.master.api.spring.security.master.persistance.entity.Security.User;
import com.master.api.spring.security.master.persistance.repository.security.IJwtTokenRespository;
import com.master.api.spring.security.master.services.UserDetailService;
import com.master.api.spring.security.master.services.UserService;
import com.master.api.spring.security.master.services.Auth.JwtService;
import org.springframework.util.StringUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;
    @Autowired
    UserDetailService userDetailService;
    @Autowired
    UserService userService;
    @Autowired
    private IJwtTokenRespository jwtTokenRespository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
      System.out.println("Entró en filtro Jwt Authentication Filter");
      String jwt = this.jwtService.extractJwtFromRequest(request); // ver en este metodo los pasos 1 y 2 para Obtener authorization header y  Obtener token
      //3. validar que el jwt si este valido y que este metodo no me retoene null
      if(jwt == null || !StringUtils.hasText(jwt)){
        filterChain.doFilter(request, response);
        return;
      }
      //3.1 obtener el token no expirado y valido desde base de datos

      Optional<JwtToken> token = this.jwtTokenRespository.findByToken(jwt);
      boolean isValid = validateToken(token);
      if(!isValid){
        filterChain.doFilter(request, response);
        return;
      }

      //4. Obtener el subject/username desde el token
      // Esta accion a su vez valida el formato del token firma y fecha de expiración
      String username = jwtService.extractUsername(jwt); // cuando el token llegue aqui siempre será valido ya que ha pasado los dos if anteriores
      
      
      //5. Setear el objeto authentication dentro de securit cotnext Holder
      // Este objeto representa el usuario logueado esta dentro del securityContext y este a su vez dentro del securitycontext holder
        User userDetails = this.userService.findOneByUsername(username).get();
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);


      //6. Ejecutar el registro de filtros
      filterChain.doFilter(request, response);
    }

    //metodo para validar el token que viene de base de datos
    private boolean validateToken(Optional<JwtToken> optionalToken) {
      if(!optionalToken.isPresent()){
        System.out.println("Token no existente");
        return false;
      }
      JwtToken token = optionalToken.get();
      Date now = new Date(System.currentTimeMillis());
      boolean isValid = token.isValid() && token.getExpiration().after(now); // si la fecha de expiración esta despues de la fecha actual
      if(!isValid){
        System.out.println("Token no valido");
        updateTokenStatus(token);
      }
      return isValid;
    }
    private void updateTokenStatus(JwtToken token) {
      token.setValid(false);
      this.jwtTokenRespository.save(token);
    }
    
}


//#  OncePerRequestFilter
//** Es una clase abstracta de java proporcionada por spring security que simplifica la creacion de filtros personalizados
//** para procesar peticones http una sola vez por solicitud
//** garantiza que el filtro se aplique una sola vez a la cadena de filtros de spring security lo que evita procesamientos inncecesarios
//** o duplicados que podrian ocurrir si se usa un filtro común. Tambien se tiene acceso tanto a HttpServletRequest(Solicitud) como a HttpServeletResponse(respuesta)

//#  Funcionamiento de FilterChain
//** Cuando un cliente envía una solicitud a un recurso web (servlet o JSP), 
//** el contenedor de servlets primero verifica si hay filtros mapeados para
//** ese recurso en el descriptor de implementación (web.xml).
//** Si se definen filtros, el contenedor crea un objeto FilterChain que contiene la secuencia de filtros que se invocarán.
//** El contenedor inicia el procesamiento llamando al método doFilter del primer filtro en la cadena.

//# UsernamePasswordAuthenticationToken
//** Si enntramos a la clase nos damos cuenta de que resibe un objeto Object principal y Object es la clase padre de java es decir recibe cualquier tipo de objeto
//** en este caso como principal le estamos enviando solo el nombre del usuario ya que con eso es suficiente para mantener el contexto de la aplicacion
//** pero tecnicamente podria enviar el objeto Usuario en esta instancia
//** 
//** 
//** 

//# Nota
//** El filtro JwtAuthenticationFilter no solo valida el token JWT, sino que también establece
//** el contexto de seguridad con la información del usuario autenticado. Esto es crucial para que las partes
//** posteriores del flujo de la solicitud, como los controladores o servicios, puedan acceder a la identidad del usuario
//** y aplicar las reglas de autorización correspondientes.
//** 2. Actualización del contexto de seguridad:

