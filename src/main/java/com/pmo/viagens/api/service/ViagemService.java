package com.pmo.viagens.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.pmo.viagens.api.model.Veiculo;
import com.pmo.viagens.api.model.Viagem;
import com.pmo.viagens.api.repository.ViagemRepository;

@Service
public class ViagemService {

	@Autowired
	private ViagemRepository viagemRepository;
	
	@Autowired
	private VeiculoService veiculoService;
	
	public Viagem iniciarViagem(Viagem viagem) {
		Veiculo veiculo = this.veiculoService.getPorId(viagem.getVeiculo().getId());
		
		if (veiculo == null || !veiculo.getDisponivel())
			throw new EmptyResultDataAccessException(1);
		
		Viagem viagemSalva = this.viagemRepository.save(viagem);
		
		veiculo.setDisponivel(false); // veiculo agora fica indisponível
		Veiculo veiculoSalvo = this.veiculoService.salvar(veiculo);
		viagemSalva.setVeiculo(veiculoSalvo);
			
		return viagemSalva;
	}
	
	public Viagem concluirViagem(Long id, Viagem viagem) {
		Viagem viagemSalva = buscarViagemPorId(id);
		BeanUtils.copyProperties(viagem, viagemSalva, "id");
		Viagem novaViagem = this.viagemRepository.save(viagemSalva);
		
		Veiculo veiculo = this.veiculoService.getPorId(viagemSalva.getVeiculo().getId());
		
		if (viagem.getChegada() != null) { // entregando
			veiculo.setDisponivel(true); // veiculo agora fica indisponível
			Veiculo veiculoSalvo = this.veiculoService.salvar(veiculo);
			novaViagem.setVeiculo(veiculoSalvo);
		}		
		return novaViagem;
	}

	private Viagem buscarViagemPorId(Long id) {
		Optional<Viagem> viagemSalva = this.viagemRepository.findById(id);
		if (viagemSalva.isEmpty())
			throw new EmptyResultDataAccessException(1);
		return viagemSalva.get();
	}
}
