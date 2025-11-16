package co.edu.unbosque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import co.edu.unbosque.dto.LoginRequest;
import co.edu.unbosque.dto.UsuarioDTOs.UsuarioDTO;
import co.edu.unbosque.entity.Usuario;
import co.edu.unbosque.service.api.UsuarioServiceAPI;
import co.edu.unbosque.utils.HashGenerator;
import co.edu.unbosque.utils.JwtUtils;
import co.edu.unbosque.utils.exception.AuthenticationFailureException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/autenticacion")
@CrossOrigin(origins = "http://localhost:4200")
public class AutenticacionRestController {

	@Autowired
	private UsuarioServiceAPI usuarioService;
	@Autowired
	private JwtUtils jwtUtils;
	
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		
		UsuarioDTO usuario = autenticar(loginRequest.getUsername(), loginRequest.getPassword());
		
		String token = jwtUtils.generateToken(usuario.login());
		Map<String, Object> response = new HashMap<>();
		response.put("usuario", usuario);
		response.put("token", token);
	    
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/validar-token")
    public ResponseEntity<Boolean> validateTokenHeader(
            @RequestHeader(name = "Authorization", required = false) String authHeader
    ) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.ok(false);
        }
        String token = authHeader.replace("Bearer ", "");
        boolean esValido = jwtUtils.validateToken(token);
        return ResponseEntity.ok(esValido);
    }
	
	
	private UsuarioDTO autenticar(String correo, String clavePlano) {
        Usuario u =  usuarioService.findByLogin(correo);
        
        if(!HashGenerator.generarHash(clavePlano).equals(u.getClave()))
        	 throw new AuthenticationFailureException("Correo o contraseña inválidos");
        
        return new UsuarioDTO(u.getIdUsuario(), u.getLogin(), u.getEstado(), u.getTipoUsuario().getTipo());
    }

}
