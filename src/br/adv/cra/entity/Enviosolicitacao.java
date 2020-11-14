package br.adv.cra.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "enviode")
@SequenceGenerator(name = "seqenviosoli", sequenceName = "idenviosolicitacao", allocationSize = 1, initialValue = 1)
public class Enviosolicitacao implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqenviosoli")
	private Integer idenviosolicitacao;
	private String tipoenvio;

	public Enviosolicitacao() {
	}

	public Enviosolicitacao(Integer idenviosolicitacao, String tipoenvio) {
		this.idenviosolicitacao = idenviosolicitacao; 
		this.tipoenvio = tipoenvio;
	}

	

	public Integer getIdenviosolicitacao() {
		return idenviosolicitacao;
	}

	public void setIdenviosolicitacao(Integer idenviosolicitacao) {
		this.idenviosolicitacao = idenviosolicitacao;
	}

	public String getTipoenvio() {
		return tipoenvio;
	}

	public void setTipoenvio(String tipoenvio) {
		this.tipoenvio = tipoenvio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((idenviosolicitacao == null) ? 0 : idenviosolicitacao
						.hashCode());
		result = prime * result
				+ ((tipoenvio == null) ? 0 : tipoenvio.hashCode());
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
		Enviosolicitacao other = (Enviosolicitacao) obj;
		if (idenviosolicitacao == null) {
			if (other.idenviosolicitacao != null)
				return false;
		} else if (!idenviosolicitacao.equals(other.idenviosolicitacao))
			return false;
		if (tipoenvio == null) {
			if (other.tipoenvio != null)
				return false;
		} else if (!tipoenvio.equals(other.tipoenvio))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Enviosolicitacao [idenviosolicitacao=" + idenviosolicitacao + ", tipoenvio=" + tipoenvio + "]";
	}

	
	
}
