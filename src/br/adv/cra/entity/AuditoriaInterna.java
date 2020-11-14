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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "auditoriainterna")
@SequenceGenerator(name = "seqaudiinterna", sequenceName = "idaudiinterna", allocationSize = 1, initialValue = 1)
public class AuditoriaInterna implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqaudiinterna")
	private Integer idaudiinterna; 
	private boolean correpfezsub; //Correspoendete se fez substituir
	private String advogadosubs; //Advogado substituto
    private boolean leuorientacao; //Leu a orientação porra !!
    private boolean conhecimentocaso; //Correspondente 
    private boolean duvpropacordo; //Dúvida no acordo
    private boolean portdocumentacao; //Portava documentacao
    private float valorpropinicial; //valor da proposta inicial
    private Integer notaavaliacao;// Nota de avalaicao da audiencia
    @Column(length = 1000, columnDefinition = "Text")
    private String avaliacao1; //Avaliacao 1
    @Column(length = 1000, columnDefinition = "Text")
    private String avaliacao2; //Avaliacao 2
	@Temporal(TemporalType.TIMESTAMP)
    private Date dataauditoria;
    
 
	public AuditoriaInterna() {
	}


	public AuditoriaInterna(Integer idaudiinterna, boolean correpfezsub,
			String advogadosubs, boolean leuorientacao,
			boolean conhecimentocaso, boolean duvpropacordo,
			boolean portdocumentacao, float valorpropinicial,
			String avaliacao1, String avaliacao2, Date dataauditoria, Integer notaavaliacao) {
		this.idaudiinterna = idaudiinterna;
		this.correpfezsub = correpfezsub;
		this.advogadosubs = advogadosubs;
		this.leuorientacao = leuorientacao;
		this.conhecimentocaso = conhecimentocaso;
		this.duvpropacordo = duvpropacordo;
		this.portdocumentacao = portdocumentacao;
		this.valorpropinicial = valorpropinicial;
		this.avaliacao1 = avaliacao1;
		this.avaliacao2 = avaliacao2;
		this.dataauditoria = dataauditoria;
		this.notaavaliacao=notaavaliacao;
	}

   
	
	public Integer getNotaavaliacao() {
		return notaavaliacao;
	}


	public void setNotaavaliacao(Integer notaavaliacao) {
		this.notaavaliacao = notaavaliacao;
	}


	public Integer getIdaudiinterna() {
		return idaudiinterna;
	}


	public void setIdaudiinterna(Integer idaudiinterna) {
		this.idaudiinterna = idaudiinterna;
	}


	public boolean isCorrepfezsub() {
		return correpfezsub;
	}


	public void setCorrepfezsub(boolean correpfezsub) {
		this.correpfezsub = correpfezsub;
	}


	public String getAdvogadosubs() {
		return advogadosubs;
	}


	public void setAdvogadosubs(String advogadosubs) {
		this.advogadosubs = advogadosubs;
	}


	public boolean isLeuorientacao() {
		return leuorientacao;
	}


	public void setLeuorientacao(boolean leuorientacao) {
		this.leuorientacao = leuorientacao;
	}


	public boolean isConhecimentocaso() {
		return conhecimentocaso;
	}


	public void setConhecimentocaso(boolean conhecimentocaso) {
		this.conhecimentocaso = conhecimentocaso;
	}


	public boolean isDuvpropacordo() {
		return duvpropacordo;
	}


	public void setDuvpropacordo(boolean duvpropacordo) {
		this.duvpropacordo = duvpropacordo;
	}


	public boolean isPortdocumentacao() {
		return portdocumentacao;
	}


	public void setPortdocumentacao(boolean portdocumentacao) {
		this.portdocumentacao = portdocumentacao;
	}


	public float getValorpropinicial() {
		return valorpropinicial;
	}


	public void setValorpropinicial(float valorpropinicial) {
		this.valorpropinicial = valorpropinicial;
	}


	public String getAvaliacao1() {
		return avaliacao1;
	}


	public void setAvaliacao1(String avalaicao1) {
		this.avaliacao1 = avalaicao1;
	}


	public String getAvaliacao2() {
		return avaliacao2;
	}


	public void setAvaliacao2(String avaliacao2) {
		this.avaliacao2 = avaliacao2;
	}


	public Date getDataauditoria() {
		return dataauditoria;
	}


	public void setDataauditoria(Date dataauditoria) {
		this.dataauditoria = dataauditoria;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((advogadosubs == null) ? 0 : advogadosubs.hashCode());
		result = prime * result
				+ ((avaliacao1 == null) ? 0 : avaliacao1.hashCode());
		result = prime * result
				+ ((avaliacao2 == null) ? 0 : avaliacao2.hashCode());
		result = prime * result + (conhecimentocaso ? 1231 : 1237);
		result = prime * result + (correpfezsub ? 1231 : 1237);
		result = prime * result
				+ ((dataauditoria == null) ? 0 : dataauditoria.hashCode());
		result = prime * result + (duvpropacordo ? 1231 : 1237);
		result = prime * result
				+ ((idaudiinterna == null) ? 0 : idaudiinterna.hashCode());
		result = prime * result + (leuorientacao ? 1231 : 1237);
		result = prime * result
				+ ((notaavaliacao == null) ? 0 : notaavaliacao.hashCode());
		result = prime * result + (portdocumentacao ? 1231 : 1237);
		result = prime * result + Float.floatToIntBits(valorpropinicial);
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
		AuditoriaInterna other = (AuditoriaInterna) obj;
		if (advogadosubs == null) {
			if (other.advogadosubs != null)
				return false;
		} else if (!advogadosubs.equals(other.advogadosubs))
			return false;
		if (avaliacao1 == null) {
			if (other.avaliacao1 != null)
				return false;
		} else if (!avaliacao1.equals(other.avaliacao1))
			return false;
		if (avaliacao2 == null) {
			if (other.avaliacao2 != null)
				return false;
		} else if (!avaliacao2.equals(other.avaliacao2))
			return false;
		if (conhecimentocaso != other.conhecimentocaso)
			return false;
		if (correpfezsub != other.correpfezsub)
			return false;
		if (dataauditoria == null) {
			if (other.dataauditoria != null)
				return false;
		} else if (!dataauditoria.equals(other.dataauditoria))
			return false;
		if (duvpropacordo != other.duvpropacordo)
			return false;
		if (idaudiinterna == null) {
			if (other.idaudiinterna != null)
				return false;
		} else if (!idaudiinterna.equals(other.idaudiinterna))
			return false;
		if (leuorientacao != other.leuorientacao)
			return false;
		if (notaavaliacao == null) {
			if (other.notaavaliacao != null)
				return false;
		} else if (!notaavaliacao.equals(other.notaavaliacao))
			return false;
		if (portdocumentacao != other.portdocumentacao)
			return false;
		if (Float.floatToIntBits(valorpropinicial) != Float
				.floatToIntBits(other.valorpropinicial))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "AuditoriaInterna [idaudiinterna=" + idaudiinterna
				+ ", correpfezsub=" + correpfezsub + ", advogadosubs="
				+ advogadosubs + ", leuorientacao=" + leuorientacao
				+ ", conhecimentocaso=" + conhecimentocaso + ", duvpropacordo="
				+ duvpropacordo + ", portdocumentacao=" + portdocumentacao
				+ ", valorpropinicial=" + valorpropinicial + ", notaavaliacao="
				+ notaavaliacao + ", avaliacao1=" + avaliacao1
				+ ", avaliacao2=" + avaliacao2 + ", dataauditoria="
				+ dataauditoria + "]";
	}

   
	

}
