package com.pmo.viagens.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.pmo.viagens.api.model.Veiculo;
import com.pmo.viagens.api.repository.VeiculoRepository;

@Service
public class VeiculoService {

	@Autowired
	private VeiculoRepository repository;

	public Veiculo getPorId(Long id) {
		Optional<Veiculo> findById = this.repository.findById(id);
		if (findById.isEmpty())
			throw new EmptyResultDataAccessException(1);
		return findById.get();
	}

	public Veiculo salvar(Veiculo veiculo) {
		Veiculo veiculoSalvo = this.repository.save(veiculo);
		return veiculoSalvo;		
	}

}
