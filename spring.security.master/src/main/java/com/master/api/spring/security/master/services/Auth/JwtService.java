package com.master.api.spring.security.master.services.Auth;

import java.sql.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;


@Service
public class JwtService {

    @Value("${security.jwt.expiration-in-minutes}")
    private Long EXPIRATION_IN_MINUTES;

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    public String generateToken(UserDetails user, Map<String, Object> extraClaims) throws InvalidKeyException {
        
        
        Date issueDat = new Date(System.currentTimeMillis());
        Date expirationInSecondsDate = new Date((EXPIRATION_IN_MINUTES * 60 * 1000) + issueDat.getTime());
        // @SuppressWarnings("deprecation")
        String jwt = Jwts.builder()
                // ==header
                .header().add("typ", "JWT").and()
                // == pyload
                .claims(extraClaims)
                .subject(user.getUsername())
                .issuedAt(issueDat)
                .expiration(expirationInSecondsDate)
                // === firma
                .signWith(generateKey(), Jwts.SIG.HS384)
                .compact();
        return jwt;
    }

    //genera y devuelve la clave secreta decodificada ya que en las propiedades ya esta codificadda
    private SecretKey generateKey() {
        byte [] passwordDecoded = Decoders.BASE64.decode(SECRET_KEY);
        
        // String passwordPase64 = Encoders.BASE64.encode(passwordDecoded); // esta reia para codificar en base 64
        // byte [] passwordEncodeBytes = passwordPase64.getBytes(StandardCharsets.UTF_8); // obtener en bytes  el passwordPase64

        System.out.println(new String(passwordDecoded));
        return Keys.hmacShaKeyFor(passwordDecoded);
    }
    //== Los siguientes dos metodos son usados para validar un tocken   
    public String extractUsername(String jwt) {
      return extractAllClaims(jwt).getSubject();
    }
    private Claims extractAllClaims(String jwt){
        return Jwts.parser().verifyWith(generateKey()).build().parseSignedClaims(jwt).getPayload(); //parseSignedClaims me indica que es un jwt firmado
    }

    public String extractJwtFromRequest(HttpServletRequest request) {
      //1. Obtener encabezado(header) http llamado autorization
      //   Se verifica si el encabezado Authorization existe y comienza con "Bearer ". 
      //   Si no es así, la cadena de filtros continúa sin procesar el token JWT.
        String autorizationHeader =  request.getHeader("Authorization"); //Bearer jwt aqui se obtiene el jwt incluyendo la palabra Bearer
        if(!StringUtils.hasText(autorizationHeader) || !autorizationHeader.startsWith("Bearer ")){ // == Verificar si el token de autorización existe y comienza con "Bearer "
            return null; // retorna el control a quien mando a llamar doFilterInternal
        }
      //2. Desde el encabezado(Header) sacar el token
        String jwt = autorizationHeader.split(" ")[1];
        return jwt;
    }

    public java.util.Date extractExpiration(String jwt) {
        return extractAllClaims(jwt).getExpiration();
    }
}

// # Los Clains son las afirmaciones o declaraciones que se incluyen en el
// cuerpo del token y contienen informacion
// # sobre el usuario y los permisos asociados