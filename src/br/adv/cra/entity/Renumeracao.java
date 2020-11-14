package br.adv.cra.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "renumeracao")
@SequenceGenerator(name = "seqrenumeracao", sequenceName = "idrenumeracao", allocationSize = 1, initialValue = 1)
public class Renumeracao implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqrenumeracao")
	private Integer idrenumeracao;
	
	private TipoSolicitacaoCorrespondente tipoSolicitacaoCorrespondente;
	// private ComarcaCorrespondente comarcaCorrespondente;
	private float valor;
	private boolean ativo;

	public Renumeracao() {

	}
	
	
   

	public Renumeracao(Integer idrenumeracao, TipoSolicitacaoCorrespondente tipoSolicitacaoCorrespondente, float valor,
			boolean ativo) {
		this.idrenumeracao = idrenumeracao;
		this.tipoSolicitacaoCorrespondente = tipoSolicitacaoCorrespondente;
		this.valor = valor;
		this.ativo = ativo;
	}



	public Integer getIdrenumeracao() {
		return idrenumeracao;
	}

	public void setIdrenumeracao(Integer idrenumeracao) {
		this.idrenumeracao = idrenumeracao;
	}

	public TipoSolicitacaoCorrespondente getTipoSolicitacaoCorrespondente() {
		return tipoSolicitacaoCorrespondente;
	}

	public void setTipoSolicitacaoCorrespondente(
			TipoSolicitacaoCorrespondente tipoSolicitacaoCorrespondente) {
		this.tipoSolicitacaoCorrespondente = tipoSolicitacaoCorrespondente;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	


	public boolean isAtivo() {
		return ativo;
	}




	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result + ((idrenumeracao == null) ? 0 : idrenumeracao.hashCode());
		result = prime * result + Float.floatToIntBits(valor);
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Renumeracao other = (Renumeracao) obj;
		if (ativo != other.ativo)
			return false;
		if (idrenumeracao == null) {
			if (other.idrenumeracao != null)
				return false;
		} else if (!idrenumeracao.equals(other.idrenumeracao))
			return false;
		if (Float.floatToIntBits(valor) != Float.floatToIntBits(other.valor))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "Renumeracao [idrenumeracao=" + idrenumeracao + ", tipoSolicitacaoCorrespondente="
				+ tipoSolicitacaoCorrespondente + ", valor=" + valor + ", ativo=" + ativo + "]";
	}

	
}
