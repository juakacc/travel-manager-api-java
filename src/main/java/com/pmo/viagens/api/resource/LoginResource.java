package com.pmo.viagens.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pmo.viagens.api.config.JwtTokenUtil;
import com.pmo.viagens.api.model.LoginRequest;
import com.pmo.viagens.api.model.LoginResponse;
import com.pmo.viagens.api.security.UsuarioDetailsService;

@RestController
@CrossOrigin
@RequestMapping("/login")
public class LoginResource {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UsuarioDetailsService usuarioDetailsService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	
	@PostMapping
	public ResponseEntity<?> login(@RequestBody LoginRequest request) throws Exception {
		
		autenticar(request.getUsername(), request.getPassword());
		
		final UserDetails userDetails = usuarioDetailsService.loadUserByUsername(request.getUsername());
		
		String token = jwtTokenUtil.gerarToken(userDetails);
		return ResponseEntity.ok(new LoginResponse(token));
		
	}

	private void autenticar(String username, String password) throws Exception {
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (Exception e) {
			throw new Exception("Erro ao realizar autenticação", e);
		}
	}
}
