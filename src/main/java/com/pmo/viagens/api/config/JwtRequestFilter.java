package com.pmo.viagens.api.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.pmo.viagens.api.security.UsuarioDetailsService;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private UsuarioDetailsService usuarioDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authorization");

		String username = null;
		String token = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			token = requestTokenHeader.substring(7);

			try {
				username = jwtTokenUtil.getUserNameFromToken(token);
			} catch (IllegalArgumentException e) {
				System.err.println("Token inválido...");
			} catch (ExpiredJwtException e) {
				System.err.println("Token expirado...");
			}
		} else {
			System.out.println("Token não inicia com Bearer...");
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = usuarioDetailsService.loadUserByUsername(username);

			if (jwtTokenUtil.validarToken(token, userDetails)) {

				UsernamePasswordAuthenticationToken userAutenticado = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				userAutenticado.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(userAutenticado);
			}
		}
		filterChain.doFilter(request, response);
	}

}
