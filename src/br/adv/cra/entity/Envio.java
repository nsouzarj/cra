package br.adv.cra.entity;

import java.io.Serializable; 

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
/**
 * Classe de teste
 * @author Nelson
 *
 */

@Entity
@Table(name = "envio")
@SequenceGenerator(name = "seqenvio", sequenceName = "idenvio", initialValue = 1, allocationSize = 1)
public class Envio  {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqenvio")
	private Integer idenvio;
	private String descricao;

	public Envio() {
	}

	public Envio(Integer idenvio, String descricao) {
		this.idenvio = idenvio;
		this.descricao = descricao;
	}

	public Integer getIdenvio() {
		return idenvio;
	}

	public void setIdenvio(Integer idenvio) {
		this.idenvio = idenvio;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((idenvio == null) ? 0 : idenvio.hashCode());
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
		Envio other = (Envio) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (idenvio == null) {
			if (other.idenvio != null)
				return false;
		} else if (!idenvio.equals(other.idenvio))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Envio [idenvio=" + idenvio + ", descricao=" + descricao + "]";
	}

}
