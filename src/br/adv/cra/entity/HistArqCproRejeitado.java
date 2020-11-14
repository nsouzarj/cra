package br.adv.cra.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "histarqcpprorej")
@SequenceGenerator(name = "seqhistcprorej", sequenceName = "idarqcpprorej", allocationSize = 1, initialValue = 1)
public class HistArqCproRejeitado implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqhistcprorej")
	private Integer idarqcpprorej;
	private Integer idarquivocppro;
	private Date rejeitadoem;
	@Column(columnDefinition="TEXT")
    private String motivo;
	public HistArqCproRejeitado() { 
		
	}
	public HistArqCproRejeitado(Integer idarqcpprorej, Integer idarquivocppro,
			Date rejeitadoem, String motivo) {

		this.idarqcpprorej = idarqcpprorej;
				this.idarquivocppro = idarquivocppro;
		this.rejeitadoem = rejeitadoem;
		this.motivo = motivo;
	}
	public Integer getIdarqcpprorej() {
		return idarqcpprorej;
	}
	public void setIdarqcpprorej(Integer idarqcpprorej) {
		this.idarqcpprorej = idarqcpprorej;
	}
	
	public Integer getIdarquivocppro() {
		return idarquivocppro;
	}
	public void setIdarquivocppro(Integer idarquivocppro) {
		this.idarquivocppro = idarquivocppro;
	}
	public Date getRejeitadoem() {
		return rejeitadoem;
	}
	public void setRejeitadoem(Date rejeitadoem) {
		this.rejeitadoem = rejeitadoem;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idarqcpprorej == null) ? 0 : idarqcpprorej.hashCode());
		result = prime * result + ((idarquivocppro == null) ? 0 : idarquivocppro.hashCode());
		result = prime * result + ((motivo == null) ? 0 : motivo.hashCode());
		result = prime * result + ((rejeitadoem == null) ? 0 : rejeitadoem.hashCode());
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
		HistArqCproRejeitado other = (HistArqCproRejeitado) obj;
		if (idarqcpprorej == null) {
			if (other.idarqcpprorej != null)
				return false;
		} else if (!idarqcpprorej.equals(other.idarqcpprorej))
			return false;
		if (idarquivocppro == null) {
			if (other.idarquivocppro != null)
				return false;
		} else if (!idarquivocppro.equals(other.idarquivocppro))
			return false;
		if (motivo == null) {
			if (other.motivo != null)
				return false;
		} else if (!motivo.equals(other.motivo))
			return false;
		if (rejeitadoem == null) {
			if (other.rejeitadoem != null)
				return false;
		} else if (!rejeitadoem.equals(other.rejeitadoem))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "HistArqCproRejeitado [idarqcpprorej=" + idarqcpprorej + ", idarquivocppro=" + idarquivocppro
				+ ", rejeitadoem=" + rejeitadoem + ", motivo=" + motivo + "]";
	}
	
	
}
