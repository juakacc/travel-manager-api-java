package com.pmo.viagens.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.pmo.viagens.api.model.Motorista;
import com.pmo.viagens.api.model.TipoCNH;
import com.pmo.viagens.api.model.Veiculo;
import com.pmo.viagens.api.model.Viagem;
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
		Veiculo veiculo = veiculoService.getPorId(viagem.getVeiculo().getId());
		
		if (veiculo == null || !veiculo.getDisponivel()) {
			throw new DataIntegrityViolationException("Veículo não encontrado ou indisponível");
		}
		Motorista motorista = motoristaService.getPorId(viagem.getMotorista().getId());
		
		if (motorista == null || this.getViagemEmAndamentoDeMotorista(motorista).isPresent()) {
			throw new DataIntegrityViolationException("Motorista não encontrado ou em viagem");
		}
		
		if (!cnh_valida(motorista.getCategoria(), veiculo.getCnh_requerida())) {
			throw new DataIntegrityViolationException(motorista.getNome() + " não tem habilitação para pilotar esse veículo. "
					+ "É necessário habilitação " + veiculo.getCnh_requerida());
		}
				
		Viagem viagemSalva = viagemRepository.save(viagem);
		
		veiculo.setDisponivel(false); // veiculo agora fica indisponível
		Veiculo veiculoSalvo = veiculoService.salvar(veiculo);
		viagemSalva.setVeiculo(veiculoSalvo);
			
		return viagemSalva;
	}
	
	private boolean cnh_valida(TipoCNH cnh, TipoCNH cnh_requerida) {
		if (cnh == cnh_requerida) return true;
		
		if (cnh == null || cnh_requerida == null) return false;
		
		switch (cnh_requerida) {
		case A:
			return cnh == TipoCNH.AB || cnh == TipoCNH.AC || cnh == TipoCNH.AD || cnh == TipoCNH.AE;
		case B:
			return cnh == TipoCNH.AB || cnh == TipoCNH.AC || cnh == TipoCNH.AD || cnh == TipoCNH.AE ||
					cnh == TipoCNH.C || cnh == TipoCNH.D || cnh == TipoCNH.E;
		case C:
			return cnh == TipoCNH.AC || cnh == TipoCNH.AD || cnh == TipoCNH.AE || cnh == TipoCNH.D || cnh == TipoCNH.E;
		case D:
			return cnh == TipoCNH.AD || cnh == TipoCNH.E;
		case E:
			return cnh == TipoCNH.AE;			
		default:
			return false;
		}
	}

	public Viagem concluirViagem(Long id, Viagem viagem) {
		Viagem viagemSalva = buscarViagemPorId(id);
		BeanUtils.copyProperties(viagem, viagemSalva, "id");
		Viagem novaViagem = this.viagemRepository.save(viagemSalva);
		
		Veiculo veiculo = this.veiculoService.getPorId(viagemSalva.getVeiculo().getId());
		
		if (viagem.getChegada() != null) { // entregando
			veiculo.setDisponivel(true); // veiculo volta a ficar disponivel
			veiculo.setQuilometragem(viagem.getKm_final());
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
	
	public Optional<Viagem> getViagemEmAndamentoDeMotorista(Motorista motorista) {
		return this.getViagemEmAndamentoDeMotorista(motorista.getId());
	}

	public List<Viagem> getViagensConcluidas() {
		List<Viagem> viagens = new ArrayList<>();
		
		for (Viagem viagem : this.viagemRepository.findAll()) {
			if (viagem.getChegada() != null) {
				viagens.add(viagem);
			}
		}
		return viagens;
	}
}
