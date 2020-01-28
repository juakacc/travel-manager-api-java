package com.pmo.viagens.api.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pmo.viagens.api.model.Viagem;
import com.pmo.viagens.api.repository.ViagemRepository;
import com.pmo.viagens.api.service.ViagemService;

@RestController
@RequestMapping("/viagens")
public class ViagemResource {

	@Autowired
	private ViagemRepository repository;
	
	@Autowired
	private ViagemService service;
	
	@GetMapping
	public ResponseEntity<List<Viagem>> listar() {
		List<Viagem> findAll = this.repository.findAll();
		return ResponseEntity.ok(findAll);
	}
	
	@GetMapping("/nao-concluidas")
	public ResponseEntity<List<Viagem>> listarViagensEmAndamento() {
		List<Viagem> findAll = this.repository.findAll();
		List<Viagem> emAndamento = new ArrayList<>();
		
		for (Viagem viagem : findAll) {
			if (viagem.getChegada() == null)
				emAndamento.add(viagem);
		}
		return ResponseEntity.ok(emAndamento);
	}
	
	@PostMapping
	public ResponseEntity<Viagem> salvar(@RequestBody Viagem viagem) {
		Viagem viagemSalva = this.service.iniciarViagem(viagem);
		return ResponseEntity.status(HttpStatus.CREATED).body(viagemSalva);
	}
	
	@PutMapping("/concluir/{id}")
	public ResponseEntity<Viagem> concluirViagem(@PathVariable Long id, @RequestBody Viagem viagem) {
		Viagem viagemSalva = this.service.concluirViagem(id, viagem);
		return ResponseEntity.ok(viagemSalva);
	}
}
