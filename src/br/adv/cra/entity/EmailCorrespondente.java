package br.adv.cra.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "emailscorrespondente")
@SequenceGenerator(initialValue = 1, name = "seqemail", sequenceName = "idemail", allocationSize = 1)
public class EmailCorrespondente implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqemail")
	private Integer idemail;
	@Column(length = 100)
	private String email;
	@ManyToOne
	private Correspondente correspondente;

	public EmailCorrespondente() {
	}

	public EmailCorrespondente(Integer idemail, String email,
			Correspondente correspondente) {
		this.idemail = idemail;
		this.email = email;
		this.correspondente = correspondente;

	}

	public Integer getIdemail() {
		return idemail;
	}

	public void setIdemail(Integer idemail) {
		this.idemail = idemail;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Correspondente getCorrespondente() {
		return correspondente;
	}

	public void setCorrespondente(Correspondente correspondente) {
		this.correspondente = correspondente;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((idemail == null) ? 0 : idemail.hashCode());
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
		EmailCorrespondente other = (EmailCorrespondente) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (idemail == null) {
			if (other.idemail != null)
				return false;
		} else if (!idemail.equals(other.idemail))
			return false;
		return true;
	}

}
