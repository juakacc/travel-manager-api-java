package com.pmo.viagens.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pmo.viagens.api.model.Motorista;

public interface MotoristaRepository extends JpaRepository<Motorista, Long> {
	
	public Optional<Motorista> findByApelido(String apelido);

}
