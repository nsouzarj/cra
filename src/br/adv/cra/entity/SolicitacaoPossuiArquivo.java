package br.adv.cra.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Embeddable
public class SolicitacaoPossuiArquivo implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinColumn(name = "idsolicitacao")
	private Solicitacao solicitacao;

	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinColumn(name = "idarquivoanexo")
	private SolicitacaoAnexo solicitacaoAnexo;

	public SolicitacaoPossuiArquivo() {
	}

	public SolicitacaoPossuiArquivo(Solicitacao solicitacao,
			SolicitacaoAnexo solicitacaoAnexo) {
		this.solicitacao = solicitacao;
		this.solicitacaoAnexo = solicitacaoAnexo;
	}

	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

	public SolicitacaoAnexo getSolicitacaoAnexo() {
		return solicitacaoAnexo;
	}

	public void setSolicitacaoAnexo(SolicitacaoAnexo solicitacaoAnexo) {
		this.solicitacaoAnexo = solicitacaoAnexo;
	}

	@Override
	public String toString() {
		return "SolicitacaoPossuiArquivo [solicitacao=" + solicitacao
				+ ", solicitacaoAnexo=" + solicitacaoAnexo + "]";
	}

	

	
	
}
