package com.pmo.viagens.api.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.pmo.viagens.api.model.Viagem;

public class ViagemDTO {

	private Long id;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime saida;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime chegada;

	private String descricao;

	private String veiculo;

	private String motorista;

	private ViagemDTO(Long id, LocalDateTime saida, LocalDateTime chegada, String descricao, String veiculo,
			String motorista) {
		this.id = id;
		this.saida = saida;
		this.chegada = chegada;
		this.descricao = descricao;
		this.veiculo = veiculo;
		this.motorista = motorista;
	}

	public static ViagemDTO getViagemDTO(Viagem viagem) {
		return new ViagemDTO(viagem.getId(), viagem.getSaida(), viagem.getChegada(), viagem.getDescricao(),
				viagem.getVeiculo().getNome(), viagem.getMotorista().getApelido());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getSaida() {
		return saida;
	}

	public void setSaida(LocalDateTime saida) {
		this.saida = saida;
	}

	public LocalDateTime getChegada() {
		return chegada;
	}

	public void setChegada(LocalDateTime chegada) {
		this.chegada = chegada;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(String veiculo) {
		this.veiculo = veiculo;
	}

	public String getMotorista() {
		return motorista;
	}

	public void setMotorista(String motorista) {
		this.motorista = motorista;
	}

}
