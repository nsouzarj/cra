package br.adv.cra.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.constraints.Email;
@Entity
@Table(name = "usuario")
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "sequsu", sequenceName = "idusuario")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequsu")
	private Integer idusuario;
	private String login;
	private String senha;
	private String nomecompleto;
	@Email
	private String emailprincipal;
	@Email
	private String emailsecundario;
	@Email
	private String emailresponsavel;
    @OneToOne
    private Correspondente correspondente;
	private Integer tipo; // 1-Adm, 2-Advgodado, 3-Correspondente
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataentrada;
	private boolean ativo;

	public Usuario() {
	}

	public Usuario(Integer idusuario, String login, String senha,
			String nomecompleto, String emailprincipal, String emailsecundario, String emailresponsavel,
			Correspondente correspondente, Integer tipo, Date dataentrada,
			boolean ativo) {
		this.idusuario = idusuario;
		this.login = login;
		this.senha = senha;
		this.nomecompleto = nomecompleto;
		this.emailprincipal = emailprincipal;
		this.emailsecundario = emailsecundario;
		this.correspondente = correspondente;
		this.tipo = tipo;
		this.emailresponsavel =emailresponsavel;
		this.dataentrada = dataentrada;
		this.ativo = ativo;
	}

	public Integer getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(Integer idusuario) {
		this.idusuario = idusuario;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNomecompleto() {
		return nomecompleto;
	}

	public void setNomecompleto(String nomecompleto) {
		this.nomecompleto = nomecompleto;
	}

	public String getEmailprincipal() {
		return emailprincipal;
	}

	public void setEmailprincipal(String emailprincipal) {
		this.emailprincipal = emailprincipal;
	}

	public String getEmailsecundario() {
		return emailsecundario;
	}

	public void setEmailsecundario(String emailsecundario) {
		this.emailsecundario = emailsecundario;
	}

	
	
	public String getEmailresponsavel() {
		return emailresponsavel;
	}

	public void setEmailresponsavel(String emailresponsavel) {
		this.emailresponsavel = emailresponsavel;
	}

	public Correspondente getCorrespondente() {
		return correspondente;
	}

	public void setCorrespondente(Correspondente correspondente) {
		this.correspondente = correspondente;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Date getDataentrada() {
		return dataentrada;
	}

	public void setDataentrada(Date dataentrada) {
		this.dataentrada = dataentrada;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result
				+ ((dataentrada == null) ? 0 : dataentrada.hashCode());
		result = prime * result
				+ ((emailprincipal == null) ? 0 : emailprincipal.hashCode());
		result = prime
				* result
				+ ((emailresponsavel == null) ? 0 : emailresponsavel.hashCode());
		result = prime * result
				+ ((emailsecundario == null) ? 0 : emailsecundario.hashCode());
		result = prime * result
				+ ((idusuario == null) ? 0 : idusuario.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result
				+ ((nomecompleto == null) ? 0 : nomecompleto.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		Usuario other = (Usuario) obj;
		if (ativo != other.ativo)
			return false;
		if (dataentrada == null) {
			if (other.dataentrada != null)
				return false;
		} else if (!dataentrada.equals(other.dataentrada))
			return false;
		if (emailprincipal == null) {
			if (other.emailprincipal != null)
				return false;
		} else if (!emailprincipal.equals(other.emailprincipal))
			return false;
		if (emailresponsavel == null) {
			if (other.emailresponsavel != null)
				return false;
		} else if (!emailresponsavel.equals(other.emailresponsavel))
			return false;
		if (emailsecundario == null) {
			if (other.emailsecundario != null)
				return false;
		} else if (!emailsecundario.equals(other.emailsecundario))
			return false;
		if (idusuario == null) {
			if (other.idusuario != null)
				return false;
		} else if (!idusuario.equals(other.idusuario))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (nomecompleto == null) {
			if (other.nomecompleto != null)
				return false;
		} else if (!nomecompleto.equals(other.nomecompleto))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [idusuario=" + idusuario + ", login=" + login
				+ ", senha=" + senha + ", nomecompleto=" + nomecompleto
				+ ", emailprincipal=" + emailprincipal + ", emailsecundario="
				+ emailsecundario + ", emailresponsavel=" + emailresponsavel
				+ ", correspondente=" + correspondente + ", tipo=" + tipo
				+ ", dataentrada=" + dataentrada + ", ativo=" + ativo + "]";
	}
	
	
}
