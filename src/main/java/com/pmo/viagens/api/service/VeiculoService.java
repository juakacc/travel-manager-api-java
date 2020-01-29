package com.pmo.viagens.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
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
	
	public List<Veiculo> getDisponiveis() {
		List<Veiculo> findAll = this.repository.findAll();
		List<Veiculo> veiculos = new ArrayList<>();
		
		for (Veiculo veiculo : findAll) {
			if (veiculo.getDisponivel())
				veiculos.add(veiculo);
		}
		return veiculos;
	}

	public Veiculo atualizar(Long id, Veiculo veiculo) {
		Veiculo veiculoSalvo = this.getPorId(id);
		BeanUtils.copyProperties(veiculo, veiculoSalvo, "id");
		return this.repository.save(veiculoSalvo);		
	}

}
