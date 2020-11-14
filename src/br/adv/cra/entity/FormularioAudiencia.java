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
@Table(name = "formularioaudiencia")
@SequenceGenerator(name = "seqformulario", sequenceName = "idformulario", allocationSize = 1, initialValue = 1)
public class FormularioAudiencia implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqformulario")
	private Integer idformulario;
	private String nomeadvogado;
	private String numoab;
	private String telefoneadvogado;
	//@Email
	private String emailadvogado;
	private String advogadoadverso;
	private String numoabadverso;
	
	private String telefoneadvadervoso;
	private String telefonecel1;
	private String telefonecel2;
	//@Email
	private String emailadvadverso;
	private boolean defesagenerica;
	private boolean contraproposta;
	private boolean aijdesiginada;
	private Date dataaij;
	private double valorproposta;
	private double valorcontraproposta;
    @Column(length = 1000, columnDefinition = "Text")
	private String obrigacaoafazer;
    @Column(length = 1000, columnDefinition = "Text")
	private String informecontraproposta;
    @Column(length = 1000, columnDefinition = "Text")
	private String objetodalide;
    @Column(length = 1000, columnDefinition = "Text")
	private String estrategiadefesa;
    @Column(length = 1000, columnDefinition = "Text")
	private String informeacontecimento;
	private Integer nivel;
	private Date dataformulario;
	private boolean acordorealizado;
	private double valoracordo;
	public FormularioAudiencia() {
	}
	public FormularioAudiencia(Integer idformulario, String nomeadvogado, String numoab, String telefoneadvogado,
			String emailadvogado, String advogadoadverso, String numoabadverso, String telefoneadvadervoso,
			String telefonecel1, String telefonecel2, String emailadvadverso, boolean defesagenerica,
			boolean contraproposta, boolean aijdesiginada, Date dataaij, double valorproposta,
			double valorcontraproposta, String obrigacaoafazer, String informecontraproposta, String objetodalide,
			String estrategiadefesa, String informeacontecimento, Integer nivel, Date dataformulario,
			boolean acordorealizado, double valoracordo) {
		super();
		this.idformulario = idformulario;
		this.nomeadvogado = nomeadvogado;
		this.numoab = numoab;
		this.telefoneadvogado = telefoneadvogado;
		this.emailadvogado = emailadvogado;
		this.advogadoadverso = advogadoadverso;
		this.numoabadverso = numoabadverso;
		this.telefoneadvadervoso = telefoneadvadervoso;
		this.telefonecel1 = telefonecel1;
		this.telefonecel2 = telefonecel2;
		this.emailadvadverso = emailadvadverso;
		this.defesagenerica = defesagenerica;
		this.contraproposta = contraproposta;
		this.aijdesiginada = aijdesiginada;
		this.dataaij = dataaij;
		this.valorproposta = valorproposta;
		this.valorcontraproposta = valorcontraproposta;
		this.obrigacaoafazer = obrigacaoafazer;
		this.informecontraproposta = informecontraproposta;
		this.objetodalide = objetodalide;
		this.estrategiadefesa = estrategiadefesa;
		this.informeacontecimento = informeacontecimento;
		this.nivel = nivel;
		this.dataformulario = dataformulario;
		this.acordorealizado = acordorealizado;
		this.valoracordo = valoracordo;
	}
	public Integer getIdformulario() {
		return idformulario;
	}
	public void setIdformulario(Integer idformulario) {
		this.idformulario = idformulario;
	}
	public String getNomeadvogado() {
		return nomeadvogado;
	}
	public void setNomeadvogado(String nomeadvogado) {
		this.nomeadvogado = nomeadvogado;
	}
	public String getNumoab() {
		return numoab;
	}
	public void setNumoab(String numoab) {
		this.numoab = numoab;
	}
	public String getTelefoneadvogado() {
		return telefoneadvogado;
	}
	public void setTelefoneadvogado(String telefoneadvogado) {
		this.telefoneadvogado = telefoneadvogado;
	}
	public String getEmailadvogado() {
		return emailadvogado;
	}
	public void setEmailadvogado(String emailadvogado) {
		this.emailadvogado = emailadvogado;
	}
	public String getAdvogadoadverso() {
		return advogadoadverso;
	}
	public void setAdvogadoadverso(String advogadoadverso) {
		this.advogadoadverso = advogadoadverso;
	}
	public String getNumoabadverso() {
		return numoabadverso;
	}
	public void setNumoabadverso(String numoabadverso) {
		this.numoabadverso = numoabadverso;
	}
	public String getTelefoneadvadervoso() {
		return telefoneadvadervoso;
	}
	public void setTelefoneadvadervoso(String telefoneadvadervoso) {
		this.telefoneadvadervoso = telefoneadvadervoso;
	}
	public String getTelefonecel1() {
		return telefonecel1;
	}
	public void setTelefonecel1(String telefonecel1) {
		this.telefonecel1 = telefonecel1;
	}
	public String getTelefonecel2() {
		return telefonecel2;
	}
	public void setTelefonecel2(String telefonecel2) {
		this.telefonecel2 = telefonecel2;
	}
	public String getEmailadvadverso() {
		return emailadvadverso;
	}
	public void setEmailadvadverso(String emailadvadverso) {
		this.emailadvadverso = emailadvadverso;
	}
	public boolean isDefesagenerica() {
		return defesagenerica;
	}
	public void setDefesagenerica(boolean defesagenerica) {
		this.defesagenerica = defesagenerica;
	}
	public boolean isContraproposta() {
		return contraproposta;
	}
	public void setContraproposta(boolean contraproposta) {
		this.contraproposta = contraproposta;
	}
	public boolean isAijdesiginada() {
		return aijdesiginada;
	}
	public void setAijdesiginada(boolean aijdesiginada) {
		this.aijdesiginada = aijdesiginada;
	}
	public Date getDataaij() {
		return dataaij;
	}
	public void setDataaij(Date dataaij) {
		this.dataaij = dataaij;
	}
	public double getValorproposta() {
		return valorproposta;
	}
	public void setValorproposta(double valorproposta) {
		this.valorproposta = valorproposta;
	}
	public double getValorcontraproposta() {
		return valorcontraproposta;
	}
	public void setValorcontraproposta(double valorcontraproposta) {
		this.valorcontraproposta = valorcontraproposta;
	}
	public String getObrigacaoafazer() {
		return obrigacaoafazer;
	}
	public void setObrigacaoafazer(String obrigacaoafazer) {
		this.obrigacaoafazer = obrigacaoafazer;
	}
	public String getInformecontraproposta() {
		return informecontraproposta;
	}
	public void setInformecontraproposta(String informecontraproposta) {
		this.informecontraproposta = informecontraproposta;
	}
	public String getObjetodalide() {
		return objetodalide;
	}
	public void setObjetodalide(String objetodalide) {
		this.objetodalide = objetodalide;
	}
	public String getEstrategiadefesa() {
		return estrategiadefesa;
	}
	public void setEstrategiadefesa(String estrategiadefesa) {
		this.estrategiadefesa = estrategiadefesa;
	}
	public String getInformeacontecimento() {
		return informeacontecimento;
	}
	public void setInformeacontecimento(String informeacontecimento) {
		this.informeacontecimento = informeacontecimento;
	}
	public Integer getNivel() {
		return nivel;
	}
	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}
	public Date getDataformulario() {
		return dataformulario;
	}
	public void setDataformulario(Date dataformulario) {
		this.dataformulario = dataformulario;
	}
	public boolean isAcordorealizado() {
		return acordorealizado;
	}
	public void setAcordorealizado(boolean acordorealizado) {
		this.acordorealizado = acordorealizado;
	}
	public double getValoracordo() {
		return valoracordo;
	}
	public void setValoracordo(double valoracordo) {
		this.valoracordo = valoracordo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (acordorealizado ? 1231 : 1237);
		result = prime * result + ((advogadoadverso == null) ? 0 : advogadoadverso.hashCode());
		result = prime * result + (aijdesiginada ? 1231 : 1237);
		result = prime * result + (contraproposta ? 1231 : 1237);
		result = prime * result + ((dataaij == null) ? 0 : dataaij.hashCode());
		result = prime * result + ((dataformulario == null) ? 0 : dataformulario.hashCode());
		result = prime * result + (defesagenerica ? 1231 : 1237);
		result = prime * result + ((emailadvadverso == null) ? 0 : emailadvadverso.hashCode());
		result = prime * result + ((emailadvogado == null) ? 0 : emailadvogado.hashCode());
		result = prime * result + ((estrategiadefesa == null) ? 0 : estrategiadefesa.hashCode());
		result = prime * result + ((idformulario == null) ? 0 : idformulario.hashCode());
		result = prime * result + ((informeacontecimento == null) ? 0 : informeacontecimento.hashCode());
		result = prime * result + ((informecontraproposta == null) ? 0 : informecontraproposta.hashCode());
		result = prime * result + ((nivel == null) ? 0 : nivel.hashCode());
		result = prime * result + ((nomeadvogado == null) ? 0 : nomeadvogado.hashCode());
		result = prime * result + ((numoab == null) ? 0 : numoab.hashCode());
		result = prime * result + ((numoabadverso == null) ? 0 : numoabadverso.hashCode());
		result = prime * result + ((objetodalide == null) ? 0 : objetodalide.hashCode());
		result = prime * result + ((obrigacaoafazer == null) ? 0 : obrigacaoafazer.hashCode());
		result = prime * result + ((telefoneadvadervoso == null) ? 0 : telefoneadvadervoso.hashCode());
		result = prime * result + ((telefoneadvogado == null) ? 0 : telefoneadvogado.hashCode());
		result = prime * result + ((telefonecel1 == null) ? 0 : telefonecel1.hashCode());
		result = prime * result + ((telefonecel2 == null) ? 0 : telefonecel2.hashCode());
		long temp;
		temp = Double.doubleToLongBits(valoracordo);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(valorcontraproposta);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(valorproposta);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		FormularioAudiencia other = (FormularioAudiencia) obj;
		if (acordorealizado != other.acordorealizado)
			return false;
		if (advogadoadverso == null) {
			if (other.advogadoadverso != null)
				return false;
		} else if (!advogadoadverso.equals(other.advogadoadverso))
			return false;
		if (aijdesiginada != other.aijdesiginada)
			return false;
		if (contraproposta != other.contraproposta)
			return false;
		if (dataaij == null) {
			if (other.dataaij != null)
				return false;
		} else if (!dataaij.equals(other.dataaij))
			return false;
		if (dataformulario == null) {
			if (other.dataformulario != null)
				return false;
		} else if (!dataformulario.equals(other.dataformulario))
			return false;
		if (defesagenerica != other.defesagenerica)
			return false;
		if (emailadvadverso == null) {
			if (other.emailadvadverso != null)
				return false;
		} else if (!emailadvadverso.equals(other.emailadvadverso))
			return false;
		if (emailadvogado == null) {
			if (other.emailadvogado != null)
				return false;
		} else if (!emailadvogado.equals(other.emailadvogado))
			return false;
		if (estrategiadefesa == null) {
			if (other.estrategiadefesa != null)
				return false;
		} else if (!estrategiadefesa.equals(other.estrategiadefesa))
			return false;
		if (idformulario == null) {
			if (other.idformulario != null)
				return false;
		} else if (!idformulario.equals(other.idformulario))
			return false;
		if (informeacontecimento == null) {
			if (other.informeacontecimento != null)
				return false;
		} else if (!informeacontecimento.equals(other.informeacontecimento))
			return false;
		if (informecontraproposta == null) {
			if (other.informecontraproposta != null)
				return false;
		} else if (!informecontraproposta.equals(other.informecontraproposta))
			return false;
		if (nivel == null) {
			if (other.nivel != null)
				return false;
		} else if (!nivel.equals(other.nivel))
			return false;
		if (nomeadvogado == null) {
			if (other.nomeadvogado != null)
				return false;
		} else if (!nomeadvogado.equals(other.nomeadvogado))
			return false;
		if (numoab == null) {
			if (other.numoab != null)
				return false;
		} else if (!numoab.equals(other.numoab))
			return false;
		if (numoabadverso == null) {
			if (other.numoabadverso != null)
				return false;
		} else if (!numoabadverso.equals(other.numoabadverso))
			return false;
		if (objetodalide == null) {
			if (other.objetodalide != null)
				return false;
		} else if (!objetodalide.equals(other.objetodalide))
			return false;
		if (obrigacaoafazer == null) {
			if (other.obrigacaoafazer != null)
				return false;
		} else if (!obrigacaoafazer.equals(other.obrigacaoafazer))
			return false;
		if (telefoneadvadervoso == null) {
			if (other.telefoneadvadervoso != null)
				return false;
		} else if (!telefoneadvadervoso.equals(other.telefoneadvadervoso))
			return false;
		if (telefoneadvogado == null) {
			if (other.telefoneadvogado != null)
				return false;
		} else if (!telefoneadvogado.equals(other.telefoneadvogado))
			return false;
		if (telefonecel1 == null) {
			if (other.telefonecel1 != null)
				return false;
		} else if (!telefonecel1.equals(other.telefonecel1))
			return false;
		if (telefonecel2 == null) {
			if (other.telefonecel2 != null)
				return false;
		} else if (!telefonecel2.equals(other.telefonecel2))
			return false;
		if (Double.doubleToLongBits(valoracordo) != Double.doubleToLongBits(other.valoracordo))
			return false;
		if (Double.doubleToLongBits(valorcontraproposta) != Double.doubleToLongBits(other.valorcontraproposta))
			return false;
		if (Double.doubleToLongBits(valorproposta) != Double.doubleToLongBits(other.valorproposta))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "FormularioAudiencia [idformulario=" + idformulario + ", nomeadvogado=" + nomeadvogado + ", numoab="
				+ numoab + ", telefoneadvogado=" + telefoneadvogado + ", emailadvogado=" + emailadvogado
				+ ", advogadoadverso=" + advogadoadverso + ", numoabadverso=" + numoabadverso + ", telefoneadvadervoso="
				+ telefoneadvadervoso + ", telefonecel1=" + telefonecel1 + ", telefonecel2=" + telefonecel2
				+ ", emailadvadverso=" + emailadvadverso + ", defesagenerica=" + defesagenerica + ", contraproposta="
				+ contraproposta + ", aijdesiginada=" + aijdesiginada + ", dataaij=" + dataaij + ", valorproposta="
				+ valorproposta + ", valorcontraproposta=" + valorcontraproposta + ", obrigacaoafazer="
				+ obrigacaoafazer + ", informecontraproposta=" + informecontraproposta + ", objetodalide="
				+ objetodalide + ", estrategiadefesa=" + estrategiadefesa + ", informeacontecimento="
				+ informeacontecimento + ", nivel=" + nivel + ", dataformulario=" + dataformulario
				+ ", acordorealizado=" + acordorealizado + ", valoracordo=" + valoracordo + "]";
	}
	
   
}
