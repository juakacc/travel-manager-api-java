package com.pmo.viagens.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pmo.viagens.api.model.Motorista;
import com.pmo.viagens.api.repository.MotoristaRepository;

@Service
public class MotoristaService {

	@Autowired
	private MotoristaRepository repository;

	public Motorista getPorId(Long id) {
		Optional<Motorista> findById = this.repository.findById(id);

		if (findById.isEmpty())
			throw new EmptyResultDataAccessException(1);
		return findById.get();
	}

	public Motorista salvar(Motorista motorista) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		motorista.setSenha(encoder.encode(motorista.getSenha()));
		Motorista motoristaSalvo = repository.save(motorista);

		return motoristaSalvo;
	}

}
