package br.adv.cra.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "logsistema")
@SequenceGenerator(name = "seqlog", sequenceName = "idlog", allocationSize = 1, initialValue = 1)
public class LogSistema implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqlog")
	private Integer idlog;
	private String usuario;
	private Integer idsolicitacao;
	private Date datalog;
	private String tela;
	@Column(length=3000,columnDefinition="Text")
	private String descricao;
	public LogSistema() {
	}
	public LogSistema(Integer idlog, String usuario, Date datalog, String tela,
			String descricao, Integer idsolicitacao) {
		this.idlog = idlog;
		this.usuario = usuario;
		this.datalog = datalog;
		this.tela = tela;
		this.descricao = descricao;
		this.idsolicitacao=idsolicitacao;
	}
	
	public Integer getIdsolicitacao() {
		return idsolicitacao;
	}
	public void setIdsolicitacao(Integer idsolicitacao) {
		this.idsolicitacao = idsolicitacao;
	}
	public Integer getIdlog() {
		return idlog;
	}
	public void setIdlog(Integer idlog) {
		this.idlog = idlog;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public Date getDatalog() {
		return datalog;
	}
	public void setDatalog(Date datalog) {
		this.datalog = datalog;
	}
	public String getTela() {
		return tela;
	}
	public void setTela(String tela) {
		this.tela = tela;
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
		result = prime * result + ((datalog == null) ? 0 : datalog.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((idlog == null) ? 0 : idlog.hashCode());
		result = prime * result
				+ ((idsolicitacao == null) ? 0 : idsolicitacao.hashCode());
		result = prime * result + ((tela == null) ? 0 : tela.hashCode());
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
		LogSistema other = (LogSistema) obj;
		if (datalog == null) {
			if (other.datalog != null)
				return false;
		} else if (!datalog.equals(other.datalog))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (idlog == null) {
			if (other.idlog != null)
				return false;
		} else if (!idlog.equals(other.idlog))
			return false;
		if (idsolicitacao == null) {
			if (other.idsolicitacao != null)
				return false;
		} else if (!idsolicitacao.equals(other.idsolicitacao))
			return false;
		if (tela == null) {
			if (other.tela != null)
				return false;
		} else if (!tela.equals(other.tela))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "LogSistema [idlog=" + idlog + ", usuario=" + usuario
				+ ", idsolicitacao=" + idsolicitacao + ", datalog=" + datalog
				+ ", tela=" + tela + ", descricao=" + descricao + "]";
	}
	
}
