package br.adv.cra.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "preposto")
@SequenceGenerator(initialValue = 1, name = "seqpreposto", sequenceName = "seqpreposto")
public class Preposto implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqpreposto")
	private Integer idpresposto;
	private String nomecompleto;
	private String cpf;
	private String identidade;
	private Date datanascimento;
	private String observacao;
	private Endereco enderecos;

	public Preposto() {
	}

	public Preposto(Integer idpresposto, String nomecompleto, String cpf,
			String identidade, Date datanascimento, String observacao,
			Endereco enderecos) {
		this.idpresposto = idpresposto;
		this.nomecompleto = nomecompleto;
		this.cpf = cpf;
		this.identidade = identidade;
		this.datanascimento = datanascimento;
		this.observacao = observacao;
		this.enderecos = enderecos;
	}

	public Integer getIdpresposto() {
		return idpresposto;
	}

	public void setIdpresposto(Integer idpresposto) {
		this.idpresposto = idpresposto;
	}

	public String getNomecompleto() {
		return nomecompleto;
	}

	public void setNomecompleto(String nomecompleto) {
		this.nomecompleto = nomecompleto;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getIdentidade() {
		return identidade;
	}

	public void setIdentidade(String identidade) {
		this.identidade = identidade;
	}

	public Date getDatanascimento() {
		return datanascimento;
	}

	public void setDatanascimento(Date datanascimento) {
		this.datanascimento = datanascimento;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Endereco getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(Endereco enderecos) {
		this.enderecos = enderecos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result
				+ ((datanascimento == null) ? 0 : datanascimento.hashCode());
		result = prime * result
				+ ((identidade == null) ? 0 : identidade.hashCode());
		result = prime * result
				+ ((idpresposto == null) ? 0 : idpresposto.hashCode());
		result = prime * result
				+ ((nomecompleto == null) ? 0 : nomecompleto.hashCode());
		result = prime * result
				+ ((observacao == null) ? 0 : observacao.hashCode());
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
		Preposto other = (Preposto) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (datanascimento == null) {
			if (other.datanascimento != null)
				return false;
		} else if (!datanascimento.equals(other.datanascimento))
			return false;
		if (identidade == null) {
			if (other.identidade != null)
				return false;
		} else if (!identidade.equals(other.identidade))
			return false;
		if (idpresposto == null) {
			if (other.idpresposto != null)
				return false;
		} else if (!idpresposto.equals(other.idpresposto))
			return false;
		if (nomecompleto == null) {
			if (other.nomecompleto != null)
				return false;
		} else if (!nomecompleto.equals(other.nomecompleto))
			return false;
		if (observacao == null) {
			if (other.observacao != null)
				return false;
		} else if (!observacao.equals(other.observacao))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Preposto [idpresposto=" + idpresposto + ", nomecompleto="
				+ nomecompleto + ", cpf=" + cpf + ", identidade=" + identidade
				+ ", datanascimento=" + datanascimento + ", observacao="
				+ observacao + ", enderecos=" + enderecos + "]";
	}
}
