package br.adv.cra.entity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name = "bancaprocesso")
@SequenceGenerator(name = "seqbanca", sequenceName = "idbanca", allocationSize = 1, initialValue = 1)
public class BancaProcesso implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqbanca")
	private Integer idbanca;
	private String banca;
	private String descricao;
	private boolean ativa;
	private String email; //Email da banca 
	private String emailgestordabanca; //Email do gestor da banca
	
	public BancaProcesso() {
		
	}

	public BancaProcesso(Integer idbanca, String banca, String descricao, boolean ativa, String email,
			String emailgestordabanca) {
		this.idbanca = idbanca;
		this.banca = banca;
		this.descricao = descricao;
		this.ativa = ativa;
		this.email = email;
		this.emailgestordabanca = emailgestordabanca;
	}

	public Integer getIdbanca() {
		return idbanca;
	}

	public void setIdbanca(Integer idbanca) {
		this.idbanca = idbanca;
	}

	public String getBanca() {
		return banca;
	}

	public void setBanca(String banca) {
		this.banca = banca;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isAtiva() {
		return ativa;
	}

	public void setAtiva(boolean ativa) {
		this.ativa = ativa;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailgestordabanca() {
		return emailgestordabanca;
	}

	public void setEmailgestordabanca(String emailgestordabanca) {
		this.emailgestordabanca = emailgestordabanca;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ativa ? 1231 : 1237);
		result = prime * result + ((banca == null) ? 0 : banca.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((emailgestordabanca == null) ? 0 : emailgestordabanca.hashCode());
		result = prime * result + ((idbanca == null) ? 0 : idbanca.hashCode());
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
		BancaProcesso other = (BancaProcesso) obj;
		if (ativa != other.ativa)
			return false;
		if (banca == null) {
			if (other.banca != null)
				return false;
		} else if (!banca.equals(other.banca))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (emailgestordabanca == null) {
			if (other.emailgestordabanca != null)
				return false;
		} else if (!emailgestordabanca.equals(other.emailgestordabanca))
			return false;
		if (idbanca == null) {
			if (other.idbanca != null)
				return false;
		} else if (!idbanca.equals(other.idbanca))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BancaProcesso [idbanca=" + idbanca + ", banca=" + banca + ", descricao=" + descricao + ", ativa="
				+ ativa + ", email=" + email + ", emailgestordabanca=" + emailgestordabanca + "]";
	}

	
	

}
