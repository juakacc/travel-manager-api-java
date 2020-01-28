package com.pmo.viagens.api.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pmo.viagens.api.model.Veiculo;
import com.pmo.viagens.api.repository.VeiculoRepository;
import com.pmo.viagens.api.service.VeiculoService;

@RestController
@RequestMapping("/veiculos")
public class VeiculoResource {
	
	@Autowired
	private VeiculoRepository repository;
	
	@Autowired
	private VeiculoService service;

	@GetMapping
	public ResponseEntity<List<Veiculo>> getAll() {
		List<Veiculo> veiculos = this.repository.findAll();
		return ResponseEntity.ok(veiculos);
	}
	
	@GetMapping("/disponiveis")
	public ResponseEntity<List<Veiculo>> getDisponiveis() {
		List<Veiculo> findAll = this.repository.findAll();
		List<Veiculo> veiculos = new ArrayList<>();
		
		for (Veiculo veiculo : findAll) {
			if (veiculo.getDisponivel())
				veiculos.add(veiculo);
		}
		return ResponseEntity.ok(veiculos);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Veiculo> getPeloId(@PathVariable Long id) {
		Optional<Veiculo> veiculoSalvo = this.repository.findById(id);
		return veiculoSalvo.isPresent() ? ResponseEntity.ok(veiculoSalvo.get())
				: ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		this.repository.deleteById(id);
	}
	
	@PostMapping
	public ResponseEntity<Veiculo> adicionar(@RequestBody Veiculo veiculo) {
		Veiculo veiculoSalvo = this.service.salvar(veiculo);
		return ResponseEntity.status(HttpStatus.CREATED).body(veiculoSalvo);
	}
}
