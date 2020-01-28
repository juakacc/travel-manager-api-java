package com.pmo.viagens.api.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pmo.viagens.api.model.Motorista;
import com.pmo.viagens.api.repository.MotoristaRepository;

@RestController
@RequestMapping("/motoristas")
public class MotoristaResource {

	@Autowired
	private MotoristaRepository repository;
	
	@GetMapping
	public ResponseEntity<List<Motorista>> listar() {
		List<Motorista> findAll = this.repository.findAll();
		return ResponseEntity.ok(findAll);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Motorista> getPorId(@PathVariable Long id) {
		Optional<Motorista> findById = this.repository.findById(id);
		return findById.isPresent() ? ResponseEntity.ok(findById.get())
				: ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Motorista> salvar(@RequestBody Motorista motorista) {
		Motorista motoristaSalvo = this.repository.save(motorista);
		return ResponseEntity.status(HttpStatus.CREATED).body(motoristaSalvo);
	}
	
	
}
