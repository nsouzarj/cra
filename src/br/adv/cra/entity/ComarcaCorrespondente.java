package br.adv.cra.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Embeddable
public class ComarcaCorrespondente implements Serializable {
	private static final long serialVersionUID = 1L;
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinColumn(name = "idcorrespondente")
	private Correspondente correspondente;

	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinColumn(name = "idcomarca")
	private Comarca comarca;

	public ComarcaCorrespondente() {
	}

	public ComarcaCorrespondente(Correspondente correspondente, Comarca comarca) {
		this.correspondente = correspondente;
		this.comarca = comarca;
	}

	public Correspondente getCorrespondente() {
		return correspondente;
	}

	public void setCorrespondente(Correspondente correspondente) {
		this.correspondente = correspondente;
	}

	public Comarca getComarca() {
		return comarca;
	}

	public void setComarca(Comarca comarca) {
		this.comarca = comarca;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comarca == null) ? 0 : comarca.hashCode());
		result = prime * result
				+ ((correspondente == null) ? 0 : correspondente.hashCode());
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
		ComarcaCorrespondente other = (ComarcaCorrespondente) obj;
		if (comarca == null) {
			if (other.comarca != null)
				return false;
		} else if (!comarca.equals(other.comarca))
			return false;
		if (correspondente == null) {
			if (other.correspondente != null)
				return false;
		} else if (!correspondente.equals(other.correspondente))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ComarcaCorrespondente [correspondente=" + correspondente
				+ ", comarca=" + comarca + "]";
	}

	

}
