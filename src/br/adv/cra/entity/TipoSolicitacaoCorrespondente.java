package br.adv.cra.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Embeddable
public class TipoSolicitacaoCorrespondente implements Serializable {
	private static final long serialVersionUID = 1L;
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinColumn(name = "idcorrespondente")
	private Correspondente correspondente;

	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinColumn(name = "idsolicitacao")
	private TipoSolicitacao tipoSolicitacao;

	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinColumn(name = "idenvio")
	private Envio envio;

	public TipoSolicitacaoCorrespondente() {
	}

	public TipoSolicitacaoCorrespondente(Correspondente correspondente,
			TipoSolicitacao tipoSolicitacao) {
		this.correspondente = correspondente;
		this.tipoSolicitacao = tipoSolicitacao;
	}

	public Correspondente getCorrespondente() {
		return correspondente;
	}

	public void setCorrespondente(Correspondente correspondente) {
		this.correspondente = correspondente;
	}

	public TipoSolicitacao getTipoSolicitacao() {
		return tipoSolicitacao;
	}

	public void setTipoSolicitacao(TipoSolicitacao tipoSolicitacao) {
		this.tipoSolicitacao = tipoSolicitacao;
	}

	public Envio getEnvio() {
		return envio;
	}

	public void setEnvio(Envio envio) {
		this.envio = envio;
	}

	@Override
	public String toString() {
		return "TipoSolicitacaoCorrespondente [correspondente="
				+ correspondente + ", tipoSolicitacao=" + tipoSolicitacao + "]";
	}

}
