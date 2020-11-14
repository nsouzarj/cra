package br.adv.cra.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "arquivoanexado")
public class SoliArquivo implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private SolicitacaoPossuiArquivo solicitacaoPossuiArquivo;

	public SolicitacaoPossuiArquivo getSolicitacaoPossuiArquivo() {
		return solicitacaoPossuiArquivo;
	}

	public void setSolicitacaoPossuiArquivo(
			SolicitacaoPossuiArquivo solicitacaoPossuiArquivo) {
		this.solicitacaoPossuiArquivo = solicitacaoPossuiArquivo;
	}

}
