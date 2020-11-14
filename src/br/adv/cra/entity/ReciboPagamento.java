package br.adv.cra.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "recibopagamento")
@SequenceGenerator(initialValue = 1, name = "seqpag", sequenceName = "seqpag", allocationSize = 1)
public class ReciboPagamento implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqpag")
	private Integer idrecibo;
	@Temporal(TemporalType.TIMESTAMP)
	private Date datafechamento;
	private String anotacao;
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = "idusuario")
	private Usuario usuario;

	public ReciboPagamento() {

	}

	public ReciboPagamento(Integer idrecibo, Date datafechamento,
			String anotacao, Usuario usuario) {
		this.idrecibo = idrecibo;
		this.datafechamento = datafechamento;
		this.anotacao = anotacao;
		this.usuario = usuario;
	}

	public Integer getIdrecibo() {
		return idrecibo;
	}

	public void setIdrecibo(Integer idrecibo) {
		this.idrecibo = idrecibo;
	}

	public Date getDatafechamento() {
		return datafechamento;
	}

	public void setDatafechamento(Date datafechamento) {
		this.datafechamento = datafechamento;
	}

	public String getAnotacao() {
		return anotacao;
	}

	public void setAnotacao(String anotacao) {
		this.anotacao = anotacao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((anotacao == null) ? 0 : anotacao.hashCode());
		result = prime * result
				+ ((datafechamento == null) ? 0 : datafechamento.hashCode());
		result = prime * result
				+ ((idrecibo == null) ? 0 : idrecibo.hashCode());
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
		ReciboPagamento other = (ReciboPagamento) obj;
		if (anotacao == null) {
			if (other.anotacao != null)
				return false;
		} else if (!anotacao.equals(other.anotacao))
			return false;
		if (datafechamento == null) {
			if (other.datafechamento != null)
				return false;
		} else if (!datafechamento.equals(other.datafechamento))
			return false;
		if (idrecibo == null) {
			if (other.idrecibo != null)
				return false;
		} else if (!idrecibo.equals(other.idrecibo))
			return false;
		return true;
	}
}
