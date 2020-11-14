package br.adv.cra.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "statussolicitacao")
@SequenceGenerator(name = "seqstatus", sequenceName = "idstatus", allocationSize = 1, initialValue = 1)
public class StatusSolicitacao implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqstatus")
	private Integer idstatus;
	private String status;

	public StatusSolicitacao() {
	}

	public StatusSolicitacao(Integer idstatus, String status) {
		this.idstatus = idstatus;
		this.status = status;
	}

	public Integer getIdstatus() {
		return idstatus;
	}

	public void setIdstatus(Integer idstatus) {
		this.idstatus = idstatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idstatus == null) ? 0 : idstatus.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		StatusSolicitacao other = (StatusSolicitacao) obj;
		if (idstatus == null) {
			if (other.idstatus != null)
				return false;
		} else if (!idstatus.equals(other.idstatus))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StatusSolicitacao [idstatus=" + idstatus + ", status=" + status
				+ "]";
	}

}
