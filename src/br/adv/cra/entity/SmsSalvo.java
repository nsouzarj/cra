package br.adv.cra.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "smsalvo")
@SequenceGenerator(name = "seqsms", sequenceName = "idsms", allocationSize = 1, initialValue = 1)
public class SmsSalvo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private Integer idsms;
	private Usuario  usuario;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataenvio;
	private String numero;
	private String menssagem;
	public SmsSalvo() {
	}
	public SmsSalvo(Integer idsms, Usuario usuario, Date dataenvio,
			String numero, String menssagem) {
		this.idsms = idsms;
		this.usuario = usuario;
		this.dataenvio = dataenvio;
		this.numero = numero;
		this.menssagem = menssagem;
	}
	public Integer getIdsms() {
		return idsms;
	}
	public void setIdsms(Integer idsms) {
		this.idsms = idsms;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Date getDataenvio() {
		return dataenvio;
	}
	public void setDataenvio(Date dataenvio) {
		this.dataenvio = dataenvio;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getMenssagem() {
		return menssagem;
	}
	public void setMenssagem(String menssagem) {
		this.menssagem = menssagem;
	}
	@Override
	public String toString() {
		return "SmsSalvo [idsms=" + idsms + ", usuario=" + usuario
				+ ", dataenvio=" + dataenvio + ", numero=" + numero
				+ ", menssagem=" + menssagem + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataenvio == null) ? 0 : dataenvio.hashCode());
		result = prime * result + ((idsms == null) ? 0 : idsms.hashCode());
		result = prime * result
				+ ((menssagem == null) ? 0 : menssagem.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		SmsSalvo other = (SmsSalvo) obj;
		if (dataenvio == null) {
			if (other.dataenvio != null)
				return false;
		} else if (!dataenvio.equals(other.dataenvio))
			return false;
		if (idsms == null) {
			if (other.idsms != null)
				return false;
		} else if (!idsms.equals(other.idsms))
			return false;
		if (menssagem == null) {
			if (other.menssagem != null)
				return false;
		} else if (!menssagem.equals(other.menssagem))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}
	
	
	
	

}
