package com.master.api.spring.security.master.services.Auth;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.master.api.spring.security.master.dto.RegisteredUser;
import com.master.api.spring.security.master.dto.SaveUser;
import com.master.api.spring.security.master.dto.auth.AuthenticationRequest;
import com.master.api.spring.security.master.dto.auth.AuthenticationResponse;
import com.master.api.spring.security.master.exception.ObjectNotFoundException;
import com.master.api.spring.security.master.persistance.entity.Security.JwtToken;
import com.master.api.spring.security.master.persistance.entity.Security.User;
import com.master.api.spring.security.master.persistance.repository.security.IJwtTokenRespository;
import com.master.api.spring.security.master.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Service
public class AuthenticationService {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private IJwtTokenRespository jwtRepository;

    public RegisteredUser registerOneCustomer(@Valid SaveUser newUser) {
        User user = this.userService.createOneCustomer(newUser);
        String jwt = jwtService.generateToken(user, generateExtraClaims(user));

        saveUserToken(jwt, user);
        
        RegisteredUser registerdUserDto = new RegisteredUser();
        registerdUserDto.setId(user.getId());
        registerdUserDto.setName(user.getName());
        registerdUserDto.setUsername(user.getUsername());
        registerdUserDto.setRole(user.getRole().getName());
        registerdUserDto.setJwt(jwt);
        return registerdUserDto;
    }

    public AuthenticationResponse login(AuthenticationRequest authRequest) {
        System.out.println(authRequest);

        // === El authenticationmanager es el que tiene el metodo authenticate quer es el que authentica aun usuario entonces hacemos uso de el y lo inyectamos aqui

        Authentication authentication = new UsernamePasswordAuthenticationToken( authRequest.getUsername(), authRequest.getPassword());

        //authentication es una implementacion de UsernamePasswordAuthenticationToken que se necesita para enviarcela al metodo authenticate aqui se aplica polimorfismo que dice
        // que una clase padre puede implementar de a cualquiera de sus hijas
        
        this.authenticationManager.authenticate(authentication);//... se hace una validación 
        UserDetails user = this.userService.findOneByUsername(authRequest.getUsername()).get(); // obtener los detalles del usuario
        String jwt = this.jwtService.generateToken(user, generateExtraClaims((User)user)); //...aplicando casteo ya que es lo mismo porque user implementa UserDetails
        saveUserToken(jwt, user);
        AuthenticationResponse authResp = new AuthenticationResponse();
        authResp.setJwt(jwt);
        return authResp;
    }



    //==Metodo para crear un token en base de datos usado al momento de login y de creación de un usuario;
    private void saveUserToken(String jwt, UserDetails user) {
        JwtToken token = new JwtToken();
        token.setToken(jwt);
        token.setUser((User) user);
        token.setExpiration(this.jwtService.extractExpiration(jwt));
        token.setValid(true);
        this.jwtRepository.save(token);
    }

    // ... Metodo para generar los Claims con el que se genera el tocken
    private Map<String, Object> generateExtraClaims(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getName());
        extraClaims.put("role", user.getRole().getName());
        extraClaims.put("authorities", user.getAuthorities());
        return extraClaims;
    }
    
    public boolean validateToken(String jwt) {
        try{
            this.jwtService.extractUsername(jwt);
            return true;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public User getLoggedInUser(){
        
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken)  SecurityContextHolder.getContext().getAuthentication();
          String username = (String) auth.getPrincipal(); // obtengo el principal que como se definio en JwtAuthenticationFilter solo es el nombre de usuario
          return this.userService.findOneByUsername(username).orElseThrow(() -> new ObjectNotFoundException("User not found with username" + username));
        

          
        //== alternativa al metodo de arriba que se usa en caso de que hallan varios tips de authenticacion
        // Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        // if(auth instanceof UsernamePasswordAuthenticationToken authToken){ // si auth es una intancia de UsernamePasswordAuthenticationToken
        //  // authToken = (UsernamePasswordAuthenticationToken) auth; Esto es lo mismo qu esta dentro de los parentecis se crea una variable parseada de UsernamePasswordAuthenticationToken
        //   String username = (String) authToken.getPrincipal(); // obtengo el principal que como se definio en JwtAuthenticationFilter solo es el nombre de usuario
        //   return this.userService.findOneByUsername(username).orElseThrow(() -> new ObjectNotFoundException("User not found with username" + username));
        // }
        
        // return null;
        
    }

    public User getMyProfile(String username){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof UsernamePasswordAuthenticationToken authTocken) {
                String userAuth = (String) authTocken.getPrincipal();
                if(username.equals(userAuth)){
                    System.out.println("Sin son iguales");
                    return this.userService.readMyProfileByUsername(username)
                    .orElseThrow(() -> new ObjectNotFoundException("User not Found with username" + username));
                }else{
                    System.out.println("No son iguales");
                }
        }
        return null;
    }

    public void logout(HttpServletRequest request) {
        String jwt = this.jwtService.extractJwtFromRequest(request);
        if(jwt == null || !StringUtils.hasText(jwt)) return;

        Optional<JwtToken> token = jwtRepository.findByToken(jwt); // buscar el token en db para invalidarlo con invalid false.
        if(token.isPresent() && token.get().isValid()){
            token.get().setValid(false);
            jwtRepository.save(token.get());
        }
    }
}

// == El metodo authenticate recibe un objeto de tipo authentication
// ... SecurityContextHolder.getContext().getAuthentication();

//#  Authentication authentication = new UsernamePasswordAuthenticationToken( authRequest.getUsername(), authRequest.getPassoword());
//... Esta linea de codigo aplica el polimorfismo en el que una clase padre puede instanciar a una clase hija ya que UsernamePasswordAuthenticationToken es nieta
//... de Authentication