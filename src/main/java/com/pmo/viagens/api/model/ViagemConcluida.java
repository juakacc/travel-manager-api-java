package com.pmo.viagens.api.model;

import java.math.BigDecimal;

public class ViagemConcluida {

	private Viagem viagem;
	private BigDecimal quilometragem;

	public Viagem getViagem() {
		return viagem;
	}

	public void setViagem(Viagem viagem) {
		this.viagem = viagem;
	}

	public BigDecimal getQuilometragem() {
		return quilometragem;
	}

	public void setQuilometragem(BigDecimal quilometragem) {
		this.quilometragem = quilometragem;
	}

}
