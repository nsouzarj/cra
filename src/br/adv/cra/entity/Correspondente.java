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
@Table(name = "correspondente")
@SequenceGenerator(name = "seqcorrespondente", sequenceName = "idcorrespondente", allocationSize = 1, initialValue = 1)
public class Correspondente implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqcorrespondente")
	private Integer idcorrespondente;
	private String nome;
	private String responsavel;
	private String cpfcnpj;
	private String oab;
	private String tipocorrepondente;
	private String telefoneprimario;
	private String telefonesecundario;
	private String telefonecelularprimario;
	private String telefonecelularsecundario;
	@Email
	private String emailprimario;
	@Email
	private String emailsecundario;
	@Temporal(TemporalType.TIMESTAMP)
	private Date datacadastro;
	private boolean ativo;
	private String observacao;
	@OneToOne
	private Endereco enderecos;
	private boolean aplicaregra1;
	private boolean aplicaregra2;


	

	public Correspondente() {
	}

	public Correspondente(Integer idcorrespondente, String nome,
			String responsavel, String cpfcnpj, String oab,
			String tipocorrepondente, String telefoneprimario,
			String telefonesecundario, String telefonecelularprimario,
			String telefonecelularsecundario, String emailprimario,
			String emailsecundario, Date datacadastro, boolean ativo,
			String observacao, Endereco enderecos, boolean aplicaregra1,
			boolean aplicaregra2) {
		this.idcorrespondente = idcorrespondente;
		this.nome = nome;
		this.responsavel = responsavel;
		this.cpfcnpj = cpfcnpj;
		this.oab = oab;
		this.tipocorrepondente = tipocorrepondente;
		this.telefoneprimario = telefoneprimario;
		this.telefonesecundario = telefonesecundario;
		this.telefonecelularprimario = telefonecelularprimario;
		this.telefonecelularsecundario = telefonecelularsecundario;
		this.emailprimario = emailprimario;
		this.emailsecundario = emailsecundario;
		this.datacadastro = datacadastro;
		this.ativo = ativo;
		this.observacao = observacao;
		this.enderecos = enderecos;
		this.aplicaregra1 = aplicaregra1;
		
	}

	
	public Integer getIdcorrespondente() {
		return idcorrespondente;
	}

	public void setIdcorrespondente(Integer idcorrespondente) {
		this.idcorrespondente = idcorrespondente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getCpfcnpj() {
		return cpfcnpj;
	}

	public void setCpfcnpj(String cpfcnpj) {
		this.cpfcnpj = cpfcnpj;
	}

	public String getOab() {
		return oab;
	}

	public void setOab(String oab) {
		this.oab = oab;
	}

	public String getTipocorrepondente() {
		return tipocorrepondente;
	}

	public void setTipocorrepondente(String tipocorrepondente) {
		this.tipocorrepondente = tipocorrepondente;
	}

	public String getTelefoneprimario() {
		return telefoneprimario;
	}

	public void setTelefoneprimario(String telefoneprimario) {
		this.telefoneprimario = telefoneprimario;
	}

	public String getTelefonesecundario() {
		return telefonesecundario;
	}

	public void setTelefonesecundario(String telefonesecundario) {
		this.telefonesecundario = telefonesecundario;
	}

	public String getTelefonecelularprimario() {
		return telefonecelularprimario;
	}

	public void setTelefonecelularprimario(String telefonecelularprimario) {
		this.telefonecelularprimario = telefonecelularprimario;
	}

	public String getTelefonecelularsecundario() {
		return telefonecelularsecundario;
	}

	public void setTelefonecelularsecundario(String telefonecelularsecundario) {
		this.telefonecelularsecundario = telefonecelularsecundario;
	}

	public String getEmailprimario() {
		return emailprimario;
	}

	public void setEmailprimario(String emailprimario) {
		this.emailprimario = emailprimario;
	}

	public String getEmailsecundario() {
		return emailsecundario;
	}

	public void setEmailsecundario(String emailsecundario) {
		this.emailsecundario = emailsecundario;
	}

	public Date getDatacadastro() {
		return datacadastro;
	}

	public void setDatacadastro(Date datacadastro) {
		this.datacadastro = datacadastro;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public boolean isAplicaregra1() {
		return aplicaregra1;
	}

	public Endereco getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(Endereco enderecos) {
		this.enderecos = enderecos;
	}

	public void setAplicaregra1(boolean aplicaregra1) {
		this.aplicaregra1 = aplicaregra1;
	}

	public boolean isAplicaregra2() {
		return aplicaregra2;
	}

	public void setAplicaregra2(boolean aplicaregra2) {
		this.aplicaregra2 = aplicaregra2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (aplicaregra1 ? 1231 : 1237);
		result = prime * result + (aplicaregra2 ? 1231 : 1237);
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result + ((cpfcnpj == null) ? 0 : cpfcnpj.hashCode());
		result = prime * result
				+ ((datacadastro == null) ? 0 : datacadastro.hashCode());
		result = prime * result
				+ ((emailprimario == null) ? 0 : emailprimario.hashCode());
		result = prime * result
				+ ((emailsecundario == null) ? 0 : emailsecundario.hashCode());
		result = prime
				* result
				+ ((idcorrespondente == null) ? 0 : idcorrespondente.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((oab == null) ? 0 : oab.hashCode());
		result = prime * result
				+ ((observacao == null) ? 0 : observacao.hashCode());
		result = prime * result
				+ ((responsavel == null) ? 0 : responsavel.hashCode());
		result = prime
				* result
				+ ((telefonecelularprimario == null) ? 0
						: telefonecelularprimario.hashCode());
		result = prime
				* result
				+ ((telefonecelularsecundario == null) ? 0
						: telefonecelularsecundario.hashCode());
		result = prime
				* result
				+ ((telefoneprimario == null) ? 0 : telefoneprimario.hashCode());
		result = prime
				* result
				+ ((telefonesecundario == null) ? 0 : telefonesecundario
						.hashCode());
		result = prime
				* result
				+ ((tipocorrepondente == null) ? 0 : tipocorrepondente
						.hashCode());
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
		Correspondente other = (Correspondente) obj;
		if (aplicaregra1 != other.aplicaregra1)
			return false;
		if (aplicaregra2 != other.aplicaregra2)
			return false;
		if (ativo != other.ativo)
			return false;
		if (cpfcnpj == null) {
			if (other.cpfcnpj != null)
				return false;
		} else if (!cpfcnpj.equals(other.cpfcnpj))
			return false;
		if (datacadastro == null) {
			if (other.datacadastro != null)
				return false;
		} else if (!datacadastro.equals(other.datacadastro))
			return false;
		if (emailprimario == null) {
			if (other.emailprimario != null)
				return false;
		} else if (!emailprimario.equals(other.emailprimario))
			return false;
		if (emailsecundario == null) {
			if (other.emailsecundario != null)
				return false;
		} else if (!emailsecundario.equals(other.emailsecundario))
			return false;
		if (idcorrespondente == null) {
			if (other.idcorrespondente != null)
				return false;
		} else if (!idcorrespondente.equals(other.idcorrespondente))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (oab == null) {
			if (other.oab != null)
				return false;
		} else if (!oab.equals(other.oab))
			return false;
		if (observacao == null) {
			if (other.observacao != null)
				return false;
		} else if (!observacao.equals(other.observacao))
			return false;
		if (responsavel == null) {
			if (other.responsavel != null)
				return false;
		} else if (!responsavel.equals(other.responsavel))
			return false;
		if (telefonecelularprimario == null) {
			if (other.telefonecelularprimario != null)
				return false;
		} else if (!telefonecelularprimario
				.equals(other.telefonecelularprimario))
			return false;
		if (telefonecelularsecundario == null) {
			if (other.telefonecelularsecundario != null)
				return false;
		} else if (!telefonecelularsecundario
				.equals(other.telefonecelularsecundario))
			return false;
		if (telefoneprimario == null) {
			if (other.telefoneprimario != null)
				return false;
		} else if (!telefoneprimario.equals(other.telefoneprimario))
			return false;
		if (telefonesecundario == null) {
			if (other.telefonesecundario != null)
				return false;
		} else if (!telefonesecundario.equals(other.telefonesecundario))
			return false;
		if (tipocorrepondente == null) {
			if (other.tipocorrepondente != null)
				return false;
		} else if (!tipocorrepondente.equals(other.tipocorrepondente))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Correspondente [idcorrespondente=" + idcorrespondente
				+ ", nome=" + nome + ", responsavel=" + responsavel
				+ ", cpfcnpj=" + cpfcnpj + ", oab=" + oab
				+ ", tipocorrepondente=" + tipocorrepondente
				+ ", telefoneprimario=" + telefoneprimario
				+ ", telefonesecundario=" + telefonesecundario
				+ ", telefonecelularprimario=" + telefonecelularprimario
				+ ", telefonecelularsecundario=" + telefonecelularsecundario
				+ ", emailprimario=" + emailprimario + ", emailsecundario="
				+ emailsecundario + ", datacadastro=" + datacadastro
				+ ", ativo=" + ativo + ", observacao=" + observacao
				+ ", enderecos=" + enderecos + ", aplicaregra1=" + aplicaregra1
				+ ", aplicaregra2=" + aplicaregra2 + "]";
	}

}
