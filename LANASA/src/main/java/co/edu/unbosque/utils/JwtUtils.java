package co.edu.unbosque.utils;

import co.edu.unbosque.config.JwtProperties;
import co.edu.unbosque.entity.Empleado;
import co.edu.unbosque.service.api.EmpleadoServiceAPI;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {

	private final SecretKey secretKey;
    private final long expiration;
    @Autowired private EmpleadoServiceAPI EmpleadoService;
    
    public JwtUtils(JwtProperties jwtProperties) {
        this.secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
        this.expiration = jwtProperties.getExpiration();
    }

    public String generateToken(String login) {
        return Jwts.builder()
                .setSubject(login)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)
                .compact();
    }

    public String getLoginFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
    
    public Empleado getEmpleadoFromToken(String authorizationHeader) {
    	if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
		    throw new RuntimeException("Token no enviado o malformado");
		}
	    String token = authorizationHeader.replace("Bearer ", "");
	    String loginUsuario = getLoginFromToken(token);
	    
	    Empleado Empleado = EmpleadoService.findByCorreo(loginUsuario);
	    
	    return Empleado;
    }
}

