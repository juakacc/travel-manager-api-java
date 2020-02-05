package com.pmo.viagens.api.security;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pmo.viagens.api.model.Motorista;
import com.pmo.viagens.api.repository.MotoristaRepository;

@Service
public class UsuarioDetailsService implements UserDetailsService {
	
	@Autowired
	private MotoristaRepository motoristaRepository;

	@Override
	public UserDetails loadUserByUsername(String apelido) throws UsernameNotFoundException {
		
		Optional<Motorista> motoristaOptional = motoristaRepository.findByApelido(apelido);
		
		if (motoristaOptional.isPresent()) {
			Motorista motorista = motoristaOptional.get();
			return new User(motorista.getApelido(), motorista.getSenha(), new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
	}
}
