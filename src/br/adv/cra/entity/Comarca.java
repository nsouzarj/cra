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
@Table(name = "comarca")
@SequenceGenerator(name = "seqcomarca", sequenceName = "idcomarca", allocationSize = 1, initialValue = 1)
public class Comarca implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqcomarca")
	private Integer idcomarca;
	private String nome;
	@ManyToOne
	private Uf uf;

	public Comarca() {
	}

	public Comarca(Integer idcomarca, String nome, Uf uf) {
		this.idcomarca = idcomarca;
		this.nome = nome;
		this.uf = uf;

	}

	public Integer getIdcomarca() {
		return idcomarca;
	}

	public void setIdcomarca(Integer idcomarca) {
		this.idcomarca = idcomarca;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Uf getUf() {
		return uf;
	}

	public void setUf(Uf uf) {
		this.uf = uf;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idcomarca == null) ? 0 : idcomarca.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Comarca other = (Comarca) obj;
		if (idcomarca == null) {
			if (other.idcomarca != null)
				return false;
		} else if (!idcomarca.equals(other.idcomarca))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Comarca [idcomarca=" + idcomarca + ", nome=" + nome + ", uf="
				+ uf + "]";
	}

}
