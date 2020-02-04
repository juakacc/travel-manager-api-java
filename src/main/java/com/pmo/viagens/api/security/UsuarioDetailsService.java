package com.pmo.viagens.api.security;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if ("admin".equals(username)) {
			return new User("admin", "$2a$10$MXivadmIDkVm6AV.nN.P/.ryifU0kTnmcj3cRAhj6URe7AUvHobeu", new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
	}
}
