package com.pmo.viagens.api.dto;

import com.pmo.viagens.api.model.Motorista;

public class MotoristaDTO {

	private Long id;
	private String nome;
	private String apelido;
	private String cnh;
	private String categoria;
	private String telefone;

	private MotoristaDTO(Long id, String nome, String apelido, String cnh, String categoria, String telefone) {
		this.id = id;
		this.nome = nome;
		this.apelido = apelido;
		this.cnh = cnh;
		this.categoria = categoria;
		this.telefone = telefone;
	}

	public static MotoristaDTO getMotoristaDTO(Motorista motorista) {
		return new MotoristaDTO(motorista.getId(), motorista.getNome(), motorista.getApelido(), motorista.getCnh(),
				motorista.getCategoria(), motorista.getTelefone());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public String getCnh() {
		return cnh;
	}

	public void setCnh(String cnh) {
		this.cnh = cnh;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

}
