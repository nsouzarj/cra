/**
 * 
 */
package br.adv.cra.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author nelson
 * Esta tabela guarda os processo do cprpo que ficam armazenado em planilha
 */
@Entity
@Table(name = "processocppro")
@SequenceGenerator(name = "seqprocessocppro", sequenceName = "idprocessocppro", allocationSize = 1, initialValue = 1)
public class ProcessoCPPRO implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqprocessocppro")
	private Integer idprocessocppro;
	private String numprocesso;
	private String cliente;
	private String orgaoinicial;
	private String partecontraria;
	@Column(length = 600, columnDefinition = "Text")
	private String acao;
	private String localizador;
	private String eletronico;
	public ProcessoCPPRO() {
	}
	public ProcessoCPPRO(Integer idprocessocppro, String numprocesso,
			String cliente, String orgaoinicial, String partecontraria,
			String acao, String localizador, String eletronico) {
		this.idprocessocppro = idprocessocppro;
		this.numprocesso = numprocesso;
		this.cliente = cliente;
		this.orgaoinicial = orgaoinicial;
		this.partecontraria = partecontraria;
		this.acao = acao;
		this.localizador = localizador;
		this.eletronico = eletronico;
	}
	public Integer getIdprocessocppro() {
		return idprocessocppro;
	}
	public void setIdprocessocppro(Integer idprocessocppro) {
		this.idprocessocppro = idprocessocppro;
	}
	public String getNumprocesso() {
		return numprocesso;
	}
	public void setNumprocesso(String numprocesso) {
		this.numprocesso = numprocesso;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getOrgaoinicial() {
		return orgaoinicial;
	}
	public void setOrgaoinicial(String orgaoinicial) {
		this.orgaoinicial = orgaoinicial;
	}
	public String getPartecontraria() {
		return partecontraria;
	}
	public void setPartecontraria(String partecontraria) {
		this.partecontraria = partecontraria;
	}
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
	public String getLocalizador() {
		return localizador;
	}
	public void setLocalizador(String localizador) {
		this.localizador = localizador;
	}
	public String getEletronico() {
		return eletronico;
	}
	public void setEletronico(String eletronico) {
		this.eletronico = eletronico;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acao == null) ? 0 : acao.hashCode());
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result
				+ ((eletronico == null) ? 0 : eletronico.hashCode());
		result = prime * result
				+ ((idprocessocppro == null) ? 0 : idprocessocppro.hashCode());
		result = prime * result
				+ ((localizador == null) ? 0 : localizador.hashCode());
		result = prime * result
				+ ((numprocesso == null) ? 0 : numprocesso.hashCode());
		result = prime * result
				+ ((orgaoinicial == null) ? 0 : orgaoinicial.hashCode());
		result = prime * result
				+ ((partecontraria == null) ? 0 : partecontraria.hashCode());
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
		ProcessoCPPRO other = (ProcessoCPPRO) obj;
		if (acao == null) {
			if (other.acao != null)
				return false;
		} else if (!acao.equals(other.acao))
			return false;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (eletronico == null) {
			if (other.eletronico != null)
				return false;
		} else if (!eletronico.equals(other.eletronico))
			return false;
		if (idprocessocppro == null) {
			if (other.idprocessocppro != null)
				return false;
		} else if (!idprocessocppro.equals(other.idprocessocppro))
			return false;
		if (localizador == null) {
			if (other.localizador != null)
				return false;
		} else if (!localizador.equals(other.localizador))
			return false;
		if (numprocesso == null) {
			if (other.numprocesso != null)
				return false;
		} else if (!numprocesso.equals(other.numprocesso))
			return false;
		if (orgaoinicial == null) {
			if (other.orgaoinicial != null)
				return false;
		} else if (!orgaoinicial.equals(other.orgaoinicial))
			return false;
		if (partecontraria == null) {
			if (other.partecontraria != null)
				return false;
		} else if (!partecontraria.equals(other.partecontraria))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ProcessoCPPRO [idprocessocppro=" + idprocessocppro
				+ ", numprocesso=" + numprocesso + ", cliente=" + cliente
				+ ", orgaoinicial=" + orgaoinicial + ", partecontraria="
				+ partecontraria + ", acao=" + acao + ", localizador="
				+ localizador + ", eletronico=" + eletronico + "]";
	}
	
	

}
