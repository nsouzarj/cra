package br.adv.cra.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "uf")
@SequenceGenerator(name = "sequf", sequenceName = "sequf", initialValue = 1, allocationSize = 1)
public class Uf implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequf")
	private Integer iduf;
	@Column(length = 2)
	private String sigla;
	@Column(length = 60)
	private String nome;

	public Uf() {
	}

	public Uf(Integer iduf, String sigla, String nome) {
		this.iduf = iduf;
		this.sigla = sigla;
		this.nome = nome;
	}

	public Integer getIduf() {
		return iduf;
	}

	public void setIduf(Integer iduf) {
		this.iduf = iduf;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((iduf == null) ? 0 : iduf.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((sigla == null) ? 0 : sigla.hashCode());
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
		Uf other = (Uf) obj;
		if (iduf == null) {
			if (other.iduf != null)
				return false;
		} else if (!iduf.equals(other.iduf))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (sigla == null) {
			if (other.sigla != null)
				return false;
		} else if (!sigla.equals(other.sigla))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Uf [iduf=" + iduf + ", sigla=" + sigla + ", nome=" + nome + "]";
	}

}
