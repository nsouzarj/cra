package br.adv.cra.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "perfilusuario")
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "seqperfil", sequenceName = "idperfil")
public class PerfilUsuario implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqperfil")
	private Integer idperfilusuario;
	private String perfil;
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinColumn(name = "idusuario")
	private Usuario usuario;

	public PerfilUsuario() {
	}

	public PerfilUsuario(Integer idperfilusuario, String perfil, Usuario usuario) {
		this.idperfilusuario = idperfilusuario;
		this.perfil = perfil;
		this.usuario = usuario;
	}

	public Integer getIdperfilusuario() {
		return idperfilusuario;
	}

	public void setIdperfilusuario(Integer idperfilusuario) {
		this.idperfilusuario = idperfilusuario;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
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
				+ ((idperfilusuario == null) ? 0 : idperfilusuario.hashCode());
		result = prime * result + ((perfil == null) ? 0 : perfil.hashCode());
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
		PerfilUsuario other = (PerfilUsuario) obj;
		if (idperfilusuario == null) {
			if (other.idperfilusuario != null)
				return false;
		} else if (!idperfilusuario.equals(other.idperfilusuario))
			return false;
		if (perfil == null) {
			if (other.perfil != null)
				return false;
		} else if (!perfil.equals(other.perfil))
			return false;
		return true;
	}

}
