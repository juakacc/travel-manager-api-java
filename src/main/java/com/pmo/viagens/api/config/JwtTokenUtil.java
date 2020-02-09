package com.pmo.viagens.api.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final long JWT_TOKEN_VALIDITY = 30 * 24 * 60 * 60;
	
	@Value("${jwt.secret}")
	private String secret;

	public String gerarToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}

	private String doGenerateToken(Map<String, Object> claims, String username) {
		return Jwts.builder().setClaims(claims)
				.setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public String getUserNameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	private Date getDataExpiracaoDoToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	public boolean validarToken(String token, UserDetails userDetails) {
		String username = getUserNameFromToken(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpirado(token);
	}

	private boolean isTokenExpirado(String token) {
		Date expiracao = getDataExpiracaoDoToken(token);
		return expiracao.before(new Date());
	}	
}
