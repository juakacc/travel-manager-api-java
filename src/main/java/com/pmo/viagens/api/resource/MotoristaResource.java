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

import com.pmo.viagens.api.dto.MotoristaDTO;
import com.pmo.viagens.api.model.Motorista;
import com.pmo.viagens.api.repository.MotoristaRepository;
import com.pmo.viagens.api.service.MotoristaService;

@RestController
@RequestMapping("/motoristas")
public class MotoristaResource {

	@Autowired
	private MotoristaRepository repository;

	@Autowired
	private MotoristaService motoristaService;

	@GetMapping
	public ResponseEntity<List<Motorista>> listar() {
		List<Motorista> findAll = this.repository.findAll();
		return ResponseEntity.ok(findAll);
	}
	
	@GetMapping("/dados/{apelido}")
	public ResponseEntity<MotoristaDTO> getPorUsername(@PathVariable String apelido) {
		Optional<Motorista> motoristaOp = this.repository.findByApelido(apelido);
		
		if (motoristaOp.isPresent()) {
			return ResponseEntity.ok(MotoristaDTO.getMotoristaDTO(motoristaOp.get()));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Motorista> getPorId(@PathVariable Long id) {
		Optional<Motorista> findById = this.repository.findById(id);
		return findById.isPresent() ? ResponseEntity.ok(findById.get()) : ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<Motorista> salvar(@RequestBody Motorista motorista) {
		Motorista motoristaSalvo = motoristaService.salvar(motorista);
		return ResponseEntity.status(HttpStatus.CREATED).body(motoristaSalvo);
	}

}
