package com.pmo.viagens.api.resource;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pmo.viagens.api.model.Veiculo;
import com.pmo.viagens.api.repository.VeiculoRepository;

@Resource
@RequestMapping("/veiculos")
public class VeiculoResource {
	
	@Autowired
	private VeiculoRepository repository;

	@GetMapping
	public List<Veiculo> getAll() {
		return this.repository.findAll();
	}
}
