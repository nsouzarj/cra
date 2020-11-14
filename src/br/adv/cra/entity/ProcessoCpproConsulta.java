package br.adv.cra.entity;

import java.io.Serializable;

public class ProcessoCpproConsulta implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String numerinical;
	private String clienteprincipal;
	private String orgaoinicial;
	private String contrarioprincipal;
	private String tipoacaorito;
	private String codigopasta;
	private String processoeletronico;
	private String pastacliente;
	private String responsavel;
	public ProcessoCpproConsulta() {
	}
	public ProcessoCpproConsulta(String numerinical, String clienteprincipal, String orgaoinicial,
			String contrarioprincipal, String tipoacaorito, String codigopasta, String processoeletronico,
			String pastacliente, String responsavel) {
		super();
		this.numerinical = numerinical;
		this.clienteprincipal = clienteprincipal;
		this.orgaoinicial = orgaoinicial;
		this.contrarioprincipal = contrarioprincipal;
		this.tipoacaorito = tipoacaorito;
		this.codigopasta = codigopasta;
		this.processoeletronico = processoeletronico;
		this.pastacliente = pastacliente;
		this.responsavel = responsavel;
	}
	public String getNumerinical() {
		return numerinical;
	}
	public void setNumerinical(String numerinical) {
		this.numerinical = numerinical;
	}
	public String getClienteprincipal() {
		return clienteprincipal;
	}
	public void setClienteprincipal(String clienteprincipal) {
		this.clienteprincipal = clienteprincipal;
	}
	public String getOrgaoinicial() {
		return orgaoinicial;
	}
	public void setOrgaoinicial(String orgaoinicial) {
		this.orgaoinicial = orgaoinicial;
	}
	public String getContrarioprincipal() {
		return contrarioprincipal;
	}
	public void setContrarioprincipal(String contrarioprincipal) {
		this.contrarioprincipal = contrarioprincipal;
	}
	public String getTipoacaorito() {
		return tipoacaorito;
	}
	public void setTipoacaorito(String tipoacaorito) {
		this.tipoacaorito = tipoacaorito;
	}
	public String getCodigopasta() {
		return codigopasta;
	}
	public void setCodigopasta(String codigopasta) {
		this.codigopasta = codigopasta;
	}
	public String getProcessoeletronico() {
		return processoeletronico;
	}
	public void setProcessoeletronico(String processoeletronico) {
		this.processoeletronico = processoeletronico;
	}
	public String getPastacliente() {
		return pastacliente;
	}
	public void setPastacliente(String pastacliente) {
		this.pastacliente = pastacliente;
	}
	public String getResponsavel() {
		return responsavel;
	}
	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clienteprincipal == null) ? 0 : clienteprincipal.hashCode());
		result = prime * result + ((codigopasta == null) ? 0 : codigopasta.hashCode());
		result = prime * result + ((contrarioprincipal == null) ? 0 : contrarioprincipal.hashCode());
		result = prime * result + ((numerinical == null) ? 0 : numerinical.hashCode());
		result = prime * result + ((orgaoinicial == null) ? 0 : orgaoinicial.hashCode());
		result = prime * result + ((pastacliente == null) ? 0 : pastacliente.hashCode());
		result = prime * result + ((processoeletronico == null) ? 0 : processoeletronico.hashCode());
		result = prime * result + ((responsavel == null) ? 0 : responsavel.hashCode());
		result = prime * result + ((tipoacaorito == null) ? 0 : tipoacaorito.hashCode());
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
		ProcessoCpproConsulta other = (ProcessoCpproConsulta) obj;
		if (clienteprincipal == null) {
			if (other.clienteprincipal != null)
				return false;
		} else if (!clienteprincipal.equals(other.clienteprincipal))
			return false;
		if (codigopasta == null) {
			if (other.codigopasta != null)
				return false;
		} else if (!codigopasta.equals(other.codigopasta))
			return false;
		if (contrarioprincipal == null) {
			if (other.contrarioprincipal != null)
				return false;
		} else if (!contrarioprincipal.equals(other.contrarioprincipal))
			return false;
		if (numerinical == null) {
			if (other.numerinical != null)
				return false;
		} else if (!numerinical.equals(other.numerinical))
			return false;
		if (orgaoinicial == null) {
			if (other.orgaoinicial != null)
				return false;
		} else if (!orgaoinicial.equals(other.orgaoinicial))
			return false;
		if (pastacliente == null) {
			if (other.pastacliente != null)
				return false;
		} else if (!pastacliente.equals(other.pastacliente))
			return false;
		if (processoeletronico == null) {
			if (other.processoeletronico != null)
				return false;
		} else if (!processoeletronico.equals(other.processoeletronico))
			return false;
		if (responsavel == null) {
			if (other.responsavel != null)
				return false;
		} else if (!responsavel.equals(other.responsavel))
			return false;
		if (tipoacaorito == null) {
			if (other.tipoacaorito != null)
				return false;
		} else if (!tipoacaorito.equals(other.tipoacaorito))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ProcessoCpproConsulta [numerinical=" + numerinical + ", clienteprincipal=" + clienteprincipal
				+ ", orgaoinicial=" + orgaoinicial + ", contrarioprincipal=" + contrarioprincipal + ", tipoacaorito="
				+ tipoacaorito + ", codigopasta=" + codigopasta + ", processoeletronico=" + processoeletronico
				+ ", pastacliente=" + pastacliente + ", responsavel=" + responsavel + "]";
	}

}
