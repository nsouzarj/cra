package br.adv.cra.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name="formaudnovo")
@SequenceGenerator(name = "seqformnovo", sequenceName = "idformnovo", allocationSize = 1, initialValue = 1)
public class FormularioAudienciaNovo {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqformnovo")
	private Integer idformnovo;
	private boolean assitidoadvogado; //O autor está assistido por por advogado
	private boolean autorcompaudiencia; //Autor compareceu a audiencia 
	private boolean encerrarformulario; //Encerra o formualrio nao presente mas nada
	private String telautor;//Telefone do autor
	private String emaildoautor; //Email do autor
	private boolean aijdesignada;// Aij desiganda
	private boolean acordorealizado; //Acordo realizado 
	private String oabexadverso; //Oab do exadverso
	private String telexadverso;// Telefone do exaadverso
	private String emailexadverso; //Email do exadverso
	
	public FormularioAudienciaNovo(Integer idformnovo,
			boolean assitidoadvogado, boolean autorcompaudiencia,
			boolean encerrarformulario, String telautor, String emaildoautor,
			boolean aijdesignada, boolean acordorealizado, String oabexadverso,
			String telexadverso, String emailexadverso) {
		this.idformnovo = idformnovo;
		this.assitidoadvogado = assitidoadvogado;
		this.autorcompaudiencia = autorcompaudiencia;
		this.encerrarformulario = encerrarformulario;
		this.telautor = telautor;
		this.emaildoautor = emaildoautor;
		this.aijdesignada = aijdesignada;
		this.acordorealizado = acordorealizado;
		this.oabexadverso = oabexadverso;
		this.telexadverso = telexadverso;
		this.emailexadverso = emailexadverso;
	}
	public FormularioAudienciaNovo() {
	}
	public Integer getIdformnovo() {
		return idformnovo;
	}
	public void setIdformnovo(Integer idformnovo) {
		this.idformnovo = idformnovo;
	}
	public boolean isAssitidoadvogado() {
		return assitidoadvogado;
	}
	public void setAssitidoadvogado(boolean assitidoadvogado) {
		this.assitidoadvogado = assitidoadvogado;
	}
	public boolean isAutorcompaudiencia() {
		return autorcompaudiencia;
	}
	public void setAutorcompaudiencia(boolean autorcompaudiencia) {
		this.autorcompaudiencia = autorcompaudiencia;
	}
	public boolean isEncerrarformulario() {
		return encerrarformulario;
	}
	public void setEncerrarformulario(boolean encerrarformulario) {
		this.encerrarformulario = encerrarformulario;
	}
	public String getTelautor() {
		return telautor;
	}
	public void setTelautor(String telautor) {
		this.telautor = telautor;
	}
	public String getEmaildoautor() {
		return emaildoautor;
	}
	public void setEmaildoautor(String emaildoautor) {
		this.emaildoautor = emaildoautor;
	}
	public boolean isAijdesignada() {
		return aijdesignada;
	}
	public void setAijdesignada(boolean aijdesignada) {
		this.aijdesignada = aijdesignada;
	}
	public boolean isAcordorealizado() {
		return acordorealizado;
	}
	public void setAcordorealizado(boolean acordorealizado) {
		this.acordorealizado = acordorealizado;
	}
	public String getOabexadverso() {
		return oabexadverso;
	}
	public void setOabexadverso(String oabexadverso) {
		this.oabexadverso = oabexadverso;
	}
	public String getTelexadverso() {
		return telexadverso;
	}
	public void setTelexadverso(String telexadverso) {
		this.telexadverso = telexadverso;
	}
	public String getEmailexadverso() {
		return emailexadverso;
	}
	public void setEmailexadverso(String emailexadverso) {
		this.emailexadverso = emailexadverso;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (acordorealizado ? 1231 : 1237);
		result = prime * result + (aijdesignada ? 1231 : 1237);
		result = prime * result + (assitidoadvogado ? 1231 : 1237);
		result = prime * result + (autorcompaudiencia ? 1231 : 1237);
		result = prime * result
				+ ((emaildoautor == null) ? 0 : emaildoautor.hashCode());
		result = prime * result
				+ ((emailexadverso == null) ? 0 : emailexadverso.hashCode());
		result = prime * result + (encerrarformulario ? 1231 : 1237);
		result = prime * result
				+ ((idformnovo == null) ? 0 : idformnovo.hashCode());
		result = prime * result
				+ ((oabexadverso == null) ? 0 : oabexadverso.hashCode());
		result = prime * result
				+ ((telautor == null) ? 0 : telautor.hashCode());
		result = prime * result
				+ ((telexadverso == null) ? 0 : telexadverso.hashCode());
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
		FormularioAudienciaNovo other = (FormularioAudienciaNovo) obj;
		if (acordorealizado != other.acordorealizado)
			return false;
		if (aijdesignada != other.aijdesignada)
			return false;
		if (assitidoadvogado != other.assitidoadvogado)
			return false;
		if (autorcompaudiencia != other.autorcompaudiencia)
			return false;
		if (emaildoautor == null) {
			if (other.emaildoautor != null)
				return false;
		} else if (!emaildoautor.equals(other.emaildoautor))
			return false;
		if (emailexadverso == null) {
			if (other.emailexadverso != null)
				return false;
		} else if (!emailexadverso.equals(other.emailexadverso))
			return false;
		if (encerrarformulario != other.encerrarformulario)
			return false;
		if (idformnovo == null) {
			if (other.idformnovo != null)
				return false;
		} else if (!idformnovo.equals(other.idformnovo))
			return false;
		if (oabexadverso == null) {
			if (other.oabexadverso != null)
				return false;
		} else if (!oabexadverso.equals(other.oabexadverso))
			return false;
		if (telautor == null) {
			if (other.telautor != null)
				return false;
		} else if (!telautor.equals(other.telautor))
			return false;
		if (telexadverso == null) {
			if (other.telexadverso != null)
				return false;
		} else if (!telexadverso.equals(other.telexadverso))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "FormularioAudienciaNovo [idformnovo=" + idformnovo
				+ ", assitidoadvogado=" + assitidoadvogado
				+ ", autorcompaudiencia=" + autorcompaudiencia
				+ ", encerrarformulario=" + encerrarformulario + ", telautor="
				+ telautor + ", emaildoautor=" + emaildoautor
				+ ", aijdesignada=" + aijdesignada + ", acordorealizado="
				+ acordorealizado + ", oabexadverso=" + oabexadverso
				+ ", telexadverso=" + telexadverso + ", emailexadverso="
				+ emailexadverso + "]";
	}
	

}
