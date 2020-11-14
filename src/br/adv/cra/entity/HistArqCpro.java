package br.adv.cra.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "histarqcppro")
@SequenceGenerator(name = "seqhistcpro", sequenceName = "idarqcppro", allocationSize = 1, initialValue = 1)
public class HistArqCpro {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqhistcpro")
	private Integer idarqcppro;
	@OneToOne
	private SolicitacaoAnexo solicitacaoAnexo;
	private Integer idarquivocppro;
	private Date salvoem;
	public HistArqCpro() { 
		
	}
	public HistArqCpro(Integer idarqcppro, SolicitacaoAnexo solicitacaoAnexo, Integer idarquivocppro, Date salvoem) {
		this.idarqcppro = idarqcppro;
		this.solicitacaoAnexo = solicitacaoAnexo;
		this.idarquivocppro = idarquivocppro;
		this.salvoem = salvoem;
	}
	public Integer getIdarqcppro() {
		return idarqcppro;
	}
	public void setIdarqcppro(Integer idarqcppro) {
		this.idarqcppro = idarqcppro;
	}
	public SolicitacaoAnexo getSolicitacaoAnexo() {
		return solicitacaoAnexo;
	}
	public void setSolicitacaoAnexo(SolicitacaoAnexo solicitacaoAnexo) {
		this.solicitacaoAnexo = solicitacaoAnexo;
	}
	public Integer getIdarquivocppro() {
		return idarquivocppro;
	}
	public void setIdarquivocppro(Integer idarquivocppro) {
		this.idarquivocppro = idarquivocppro;
	}
	public Date getSalvoem() {
		return salvoem;
	}
	public void setSalvoem(Date salvoem) {
		this.salvoem = salvoem;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idarqcppro == null) ? 0 : idarqcppro.hashCode());
		result = prime * result + ((idarquivocppro == null) ? 0 : idarquivocppro.hashCode());
		result = prime * result + ((salvoem == null) ? 0 : salvoem.hashCode());
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
		HistArqCpro other = (HistArqCpro) obj;
		if (idarqcppro == null) {
			if (other.idarqcppro != null)
				return false;
		} else if (!idarqcppro.equals(other.idarqcppro))
			return false;
		if (idarquivocppro == null) {
			if (other.idarquivocppro != null)
				return false;
		} else if (!idarquivocppro.equals(other.idarquivocppro))
			return false;
		if (salvoem == null) {
			if (other.salvoem != null)
				return false;
		} else if (!salvoem.equals(other.salvoem))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "HistArqCpro [idarqcppro=" + idarqcppro + ", solicitacaoAnexo=" + solicitacaoAnexo + ", idarquivocppro="
				+ idarquivocppro + ", salvoem=" + salvoem + "]";
	}
	
	
	
	

}
