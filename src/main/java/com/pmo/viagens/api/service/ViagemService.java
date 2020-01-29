package com.pmo.viagens.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.pmo.viagens.api.model.Motorista;
import com.pmo.viagens.api.model.Veiculo;
import com.pmo.viagens.api.model.Viagem;
import com.pmo.viagens.api.model.ViagemConcluida;
import com.pmo.viagens.api.repository.ViagemRepository;

@Service
public class ViagemService {

	@Autowired
	private ViagemRepository viagemRepository;
	
	@Autowired
	private VeiculoService veiculoService;
	
	@Autowired
	private MotoristaService motoristaService;
	
	public Viagem iniciarViagem(Viagem viagem) {
		Veiculo veiculo = this.veiculoService.getPorId(viagem.getVeiculo().getId());
		
		if (veiculo == null || !veiculo.getDisponivel())
			throw new EmptyResultDataAccessException(1);
		
		Motorista motorista = this.motoristaService.getPorId(viagem.getMotorista().getId());
		
		if (motorista == null || this.getViagemEmAndamentoDeMotorista(motorista.getId()).isPresent())
			throw new EmptyResultDataAccessException(1); // ele ja esta ocupado
		
		Viagem viagemSalva = this.viagemRepository.save(viagem);
		
		veiculo.setDisponivel(false); // veiculo agora fica indisponível
		Veiculo veiculoSalvo = this.veiculoService.salvar(veiculo);
		viagemSalva.setVeiculo(veiculoSalvo);
			
		return viagemSalva;
	}
	
	public Viagem concluirViagem(Long id, ViagemConcluida viagemConcluida) {
		Viagem viagemSalva = buscarViagemPorId(id);
		BeanUtils.copyProperties(viagemConcluida.getViagem(), viagemSalva, "id");
		Viagem novaViagem = this.viagemRepository.save(viagemSalva);
		
		Veiculo veiculo = this.veiculoService.getPorId(viagemSalva.getVeiculo().getId());
		
		if (viagemConcluida.getViagem().getChegada() != null) { // entregando
			veiculo.setDisponivel(true); // veiculo volta a ficar disponivel
			veiculo.setQuilometragem(viagemConcluida.getQuilometragem());
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

	public Optional<Viagem> getViagemEmAndamentoDeMotorista(Long motoristaId) {
		List<Viagem> findAll = this.viagemRepository.findAll();
		
		for (Viagem viagem : findAll) {
			if (viagem.getChegada() == null && viagem.getMotorista().getId() == motoristaId)
				return Optional.of(viagem);
		}
		return Optional.empty();
	}
}
