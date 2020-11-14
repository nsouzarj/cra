package br.adv.cra.entity;

import java.util.Date;

import javax.persistence.Entity;
@Entity
public class AndamentoCPJ {
	private String ficha;
	private Date datahora;
	private String andamento;

	public AndamentoCPJ() {
	}

	public AndamentoCPJ(String ficha, Date datahora, String andamento) {
		this.ficha = ficha;
		this.datahora = datahora;
		this.andamento = andamento;
	}

	public String getFicha() {
		return ficha;
	}

	public void setFicha(String ficha) {
		this.ficha = ficha;
	}

	public Date getDatahora() {
		return datahora;
	}

	public void setDatahora(Date datahora) {
		this.datahora = datahora;
	}

	public String getAndamento() {
		return andamento;
	}

	public void setAndamento(String andamento) {
		this.andamento = andamento;
	}

	@Override
	public String toString() {
		return "AndamentoCPJ [ficha=" + ficha + ", datahora=" + datahora
				+ ", andamento=" + andamento + "]";
	}

}
