package br.adv.cra.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tiposolicitacao")
@SequenceGenerator(name = "seqtiposolicitacao", sequenceName = "idtiposolicitacao", allocationSize = 1, initialValue = 1)
public class TipoSolicitacao implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqtiposolicitacao")
	private Integer idtiposolicitacao;
	private String especie;
	private String descricao;
	private String tipo;
	private Boolean visualizar;

	public TipoSolicitacao() {
	}

	public Integer getIdtiposolicitacao() {
		return idtiposolicitacao;
	}

	public void setIdtiposolicitacao(Integer idtiposolicitacao) {
		this.idtiposolicitacao = idtiposolicitacao;
	}

	public String getEspecie() {
		return especie;
	}

	public TipoSolicitacao(Integer idtiposolicitacao, String especie,
			String descricao, String tipo, Boolean visualizar) {
		super();
		this.idtiposolicitacao = idtiposolicitacao;
		this.especie = especie;
		this.descricao = descricao;
		this.tipo = tipo;
		this.visualizar = visualizar;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Boolean getVisualizar() {
		return visualizar;
	}

	public void setVisualizar(Boolean visualizar) {
		this.visualizar = visualizar;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((especie == null) ? 0 : especie.hashCode());
		result = prime
				* result
				+ ((idtiposolicitacao == null) ? 0 : idtiposolicitacao
						.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result
				+ ((visualizar == null) ? 0 : visualizar.hashCode());
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
		TipoSolicitacao other = (TipoSolicitacao) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (especie == null) {
			if (other.especie != null)
				return false;
		} else if (!especie.equals(other.especie))
			return false;
		if (idtiposolicitacao == null) {
			if (other.idtiposolicitacao != null)
				return false;
		} else if (!idtiposolicitacao.equals(other.idtiposolicitacao))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (visualizar == null) {
			if (other.visualizar != null)
				return false;
		} else if (!visualizar.equals(other.visualizar))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TipoSolicitacao [idtiposolicitacao=" + idtiposolicitacao
				+ ", especie=" + especie + ", descricao=" + descricao
				+ ", tipo=" + tipo + ", visualizar=" + visualizar + "]";
	}

}
