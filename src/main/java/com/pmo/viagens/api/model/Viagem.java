package com.pmo.viagens.api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "viagem")
public class Viagem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime saida;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime chegada;

	private String descricao;

	@ManyToOne
	@JoinColumn(name = "id_veiculo")
	private Veiculo veiculo;

	@ManyToOne
	@JoinColumn(name = "id_motorista")
	private Motorista motorista;

	private BigDecimal km_inicial;
	private BigDecimal km_final;

	public BigDecimal getKm_inicial() {
		return km_inicial;
	}

	public void setKm_inicial(BigDecimal km_inicial) {
		this.km_inicial = km_inicial;
	}

	public BigDecimal getKm_final() {
		return km_final;
	}

	public void setKm_final(BigDecimal km_final) {
		this.km_final = km_final;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public Motorista getMotorista() {
		return motorista;
	}

	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
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

}
