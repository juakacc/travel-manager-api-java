package com.pmo.viagens.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pmo.viagens.api.model.Viagem;

public interface ViagemRepository extends JpaRepository<Viagem, Long>{

}
