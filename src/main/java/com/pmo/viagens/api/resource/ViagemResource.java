package com.pmo.viagens.api.resource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pmo.viagens.api.model.Viagem;
import com.pmo.viagens.api.model.ViagemConcluida;
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
	public ResponseEntity<List<Viagem>> listar(@RequestParam(required = false) String date) {
		List<Viagem> findAll = this.repository.findAll();
		List<Viagem> selecionadas = new ArrayList<>();
		
		if (date != null) {
			date = date.replace('T', ' ');
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
			System.out.println("Date Time: " + dateTime);
			
			for (Viagem viagem : findAll) {
				if (dateTime.isAfter(viagem.getSaida())) {
					if (viagem.getChegada() != null) { // já foi concluída
						if (dateTime.isBefore(viagem.getChegada())) {
							selecionadas.add(viagem);		
						}
					} else { // ainda não concluída
						selecionadas.add(viagem);
					}					
				}
			}
			return ResponseEntity.ok(selecionadas);
		}		
		return ResponseEntity.ok(findAll);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Viagem> getById(@PathVariable Long id) {
		Optional<Viagem> findAll = repository.findById(id);
		
		return findAll.isPresent() ? ResponseEntity.ok(findAll.get()) : ResponseEntity.notFound().build();
	}
		
	@PostMapping
	public ResponseEntity<Viagem> salvar(@RequestBody Viagem viagem) {
		Viagem viagemSalva = service.iniciarViagem(viagem);
		return ResponseEntity.status(HttpStatus.CREATED).body(viagemSalva);
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
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long id) {
		this.repository.deleteById(id);
	}
	
	@GetMapping("/nao-concluidas/{id}")
	public ResponseEntity<Viagem> listarViagemEmAndamentoDeMotorista(@PathVariable Long id) {
		Optional<Viagem> viagem = this.service.getViagemEmAndamentoDeMotorista(id);
		
		if (viagem.isPresent())
			return ResponseEntity.ok(viagem.get());
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/concluidas")
	public ResponseEntity<List<Viagem>> listarViagensConcluidas() {
		List<Viagem> viagens = this.service.getViagensConcluidas();
		return ResponseEntity.ok(viagens);
	}
	
	@PutMapping("/concluir/{id}")
	public ResponseEntity<Viagem> concluirViagem(@PathVariable Long id, @RequestBody ViagemConcluida viagem) {
		Viagem viagemSalva = this.service.concluirViagem(id, viagem);
		return ResponseEntity.ok(viagemSalva);
	}
}
