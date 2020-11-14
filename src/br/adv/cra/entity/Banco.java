package br.adv.cra.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "banco")
@SequenceGenerator(name = "seqbanco", sequenceName = "idbanco", allocationSize = 1, initialValue = 1)
public class Banco implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqbanco")
	private Integer idbanco;
	private String codbanco;
	private String agencia;
	private String banco;
	private String contacorrente;
	@ManyToOne
	private Correspondente correspondente;

	public Banco() {
	}

	public Banco(Integer idbanco, String codbanco, String agencia,
			String banco, String contacorrente, Correspondente correspondente) {
		this.idbanco = idbanco;
		this.codbanco = codbanco;
		this.agencia = agencia;
		this.banco = banco;
		this.contacorrente = contacorrente;
		this.correspondente = correspondente;
	}

	public Integer getIdbanco() {
		return idbanco;
	}

	public void setIdbanco(Integer idbanco) {
		this.idbanco = idbanco;
	}

	public String getCodbanco() {
		return codbanco;
	}

	public void setCodbanco(String codbanco) {
		this.codbanco = codbanco;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getContacorrente() {
		return contacorrente;
	}

	public void setContacorrente(String contacorrente) {
		this.contacorrente = contacorrente;
	}

	public Correspondente getCorrespondente() {
		return correspondente;
	}

	public void setCorrespondente(Correspondente correspondente) {
		this.correspondente = correspondente;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agencia == null) ? 0 : agencia.hashCode());
		result = prime * result + ((banco == null) ? 0 : banco.hashCode());
		result = prime * result
				+ ((codbanco == null) ? 0 : codbanco.hashCode());
		result = prime * result
				+ ((contacorrente == null) ? 0 : contacorrente.hashCode());
		result = prime * result + ((idbanco == null) ? 0 : idbanco.hashCode());
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
		Banco other = (Banco) obj;
		if (agencia == null) {
			if (other.agencia != null)
				return false;
		} else if (!agencia.equals(other.agencia))
			return false;
		if (banco == null) {
			if (other.banco != null)
				return false;
		} else if (!banco.equals(other.banco))
			return false;
		if (codbanco == null) {
			if (other.codbanco != null)
				return false;
		} else if (!codbanco.equals(other.codbanco))
			return false;
		if (contacorrente == null) {
			if (other.contacorrente != null)
				return false;
		} else if (!contacorrente.equals(other.contacorrente))
			return false;
		if (idbanco == null) {
			if (other.idbanco != null)
				return false;
		} else if (!idbanco.equals(other.idbanco))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Banco [idbanco=" + idbanco + ", codbanco=" + codbanco
				+ ", agencia=" + agencia + ", banco=" + banco
				+ ", contacorrente=" + contacorrente + ", correspondente="
				+ correspondente + "]";
	}

}
