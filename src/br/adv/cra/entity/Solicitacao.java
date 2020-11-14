package br.adv.cra.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
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
import org.hibernate.annotations.Proxy;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Table(name = "solicitacao")
@SequenceGenerator(name = "seqsolicitacao", sequenceName = "idsolicitacao", allocationSize = 1, initialValue = 1)
public class Solicitacao implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqsolicitacao")
	private Integer idsolicitacao;
	private Integer referenciasolicitacao;
	@Temporal(TemporalType.TIMESTAMP)
	private Date datasolictacao;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataconclusao;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataprazo;
	@Column(length = 600, columnDefinition = "Text")
	private String observacao;
	@Column(length = 600, columnDefinition = "Text")
	private String instrucoes;
	@Column(length = 600, columnDefinition = "Text")
	private String complemento;
	@Column(length = 600, columnDefinition = "Text")
	private String justificativa;
	@Column(length = 600, columnDefinition = "Text")
	private String tratposaudiencia; //Tratamento pos audiencia
	@Column(length = 30)
	private String numcontrole;
	private boolean tempreposto;
	private boolean convolada;
	@Column(nullable = true, length = 10)
	private String horaudiencia;
	@Column(nullable = true, length = 20)
	private String statusexterno; // CONFIRMAR - REJEITAR

	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = "idprocesso")
	private Processo processo;

	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = "idrenumeracao")
	private Renumeracao renumeracao;

	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = "idstatus")
	private StatusSolicitacao statusSolicitacao;


	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = "idcomarca")
	//@javax.validation.constraints.NotBlank
	//@Column(nullable=false)
	private Comarca comarca;

	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = "idformulario")
	private FormularioAudiencia formularioAudiencia;


	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = "idbanca")
	//@javax.validation.constraints.NotBlank
	private BancaProcesso bancaProcesso;

	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = "idenviosolicitacao")
	private Enviosolicitacao enviosolicitacao;
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = "idrecibo")
	private ReciboPagamento reciboPagamento;
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = "idusuario")
	private Usuario usuario;
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	@JoinColumn(name="idaudiinterna")
	private AuditoriaInterna auditoriaInterna;
	private float valor;
	private float valordaalcada;
	private String emailenvio;
	private String pago;
	private Integer grupo;
	@Column(nullable=true)
	private boolean propostaacordo;
	@Column(nullable=true)
	private boolean audinterna;
	@Column(length=1)
	private String lide;
	private Integer avaliacaonota;
	private String textoavaliacao;
	public Solicitacao() {
		
	}
	public Solicitacao(Integer idsolicitacao, Integer referenciasolicitacao, Date datasolictacao, Date dataconclusao,
			Date dataprazo, String observacao, String instrucoes, String complemento, String justificativa,
			String tratposaudiencia, String numcontrole, boolean tempreposto, boolean convolada, String horaudiencia,
			String statusexterno, Processo processo, Renumeracao renumeracao, StatusSolicitacao statusSolicitacao,
			Comarca comarca, FormularioAudiencia formularioAudiencia, BancaProcesso bancaProcesso,
			Enviosolicitacao enviosolicitacao, ReciboPagamento reciboPagamento, Usuario usuario,
			AuditoriaInterna auditoriaInterna, float valor, float valordaalcada, String emailenvio, String pago,
			Integer grupo, boolean propostaacordo, boolean audinterna, String lide, Integer avaliacaonota,
			String textoavaliacao) {
		super();
		this.idsolicitacao = idsolicitacao;
		this.referenciasolicitacao = referenciasolicitacao;
		this.datasolictacao = datasolictacao;
		this.dataconclusao = dataconclusao;
		this.dataprazo = dataprazo;
		this.observacao = observacao;
		this.instrucoes = instrucoes;
		this.complemento = complemento;
		this.justificativa = justificativa;
		this.tratposaudiencia = tratposaudiencia;
		this.numcontrole = numcontrole;
		this.tempreposto = tempreposto;
		this.convolada = convolada;
		this.horaudiencia = horaudiencia;
		this.statusexterno = statusexterno;
		this.processo = processo;
		this.renumeracao = renumeracao;
		this.statusSolicitacao = statusSolicitacao;
		this.comarca = comarca;
		this.formularioAudiencia = formularioAudiencia;
		this.bancaProcesso = bancaProcesso;
		this.enviosolicitacao = enviosolicitacao;
		this.reciboPagamento = reciboPagamento;
		this.usuario = usuario;
		this.auditoriaInterna = auditoriaInterna;
		this.valor = valor;
		this.valordaalcada = valordaalcada;
		this.emailenvio = emailenvio;
		this.pago = pago;
		this.grupo = grupo;
		this.propostaacordo = propostaacordo;
		this.audinterna = audinterna;
		this.lide = lide;
		this.avaliacaonota = avaliacaonota;
		this.textoavaliacao = textoavaliacao;
	}
	public Integer getIdsolicitacao() {
		return idsolicitacao;
	}
	public void setIdsolicitacao(Integer idsolicitacao) {
		this.idsolicitacao = idsolicitacao;
	}
	public Integer getReferenciasolicitacao() {
		return referenciasolicitacao;
	}
	public void setReferenciasolicitacao(Integer referenciasolicitacao) {
		this.referenciasolicitacao = referenciasolicitacao;
	}
	public Date getDatasolictacao() {
		return datasolictacao;
	}
	public void setDatasolictacao(Date datasolictacao) {
		this.datasolictacao = datasolictacao;
	}
	public Date getDataconclusao() {
		return dataconclusao;
	}
	public void setDataconclusao(Date dataconclusao) {
		this.dataconclusao = dataconclusao;
	}
	public Date getDataprazo() {
		return dataprazo;
	}
	public void setDataprazo(Date dataprazo) {
		this.dataprazo = dataprazo;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getInstrucoes() {
		return instrucoes;
	}
	public void setInstrucoes(String instrucoes) {
		this.instrucoes = instrucoes;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getJustificativa() {
		return justificativa;
	}
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}
	public String getTratposaudiencia() {
		return tratposaudiencia;
	}
	public void setTratposaudiencia(String tratposaudiencia) {
		this.tratposaudiencia = tratposaudiencia;
	}
	public String getNumcontrole() {
		return numcontrole;
	}
	public void setNumcontrole(String numcontrole) {
		this.numcontrole = numcontrole;
	}
	public boolean isTempreposto() {
		return tempreposto;
	}
	public void setTempreposto(boolean tempreposto) {
		this.tempreposto = tempreposto;
	}
	public boolean isConvolada() {
		return convolada;
	}
	public void setConvolada(boolean convolada) {
		this.convolada = convolada;
	}
	public String getHoraudiencia() {
		return horaudiencia;
	}
	public void setHoraudiencia(String horaudiencia) {
		this.horaudiencia = horaudiencia;
	}
	public String getStatusexterno() {
		return statusexterno;
	}
	public void setStatusexterno(String statusexterno) {
		this.statusexterno = statusexterno;
	}
	public Processo getProcesso() {
		return processo;
	}
	public void setProcesso(Processo processo) {
		this.processo = processo;
	}
	public Renumeracao getRenumeracao() {
		return renumeracao;
	}
	public void setRenumeracao(Renumeracao renumeracao) {
		this.renumeracao = renumeracao;
	}
	public StatusSolicitacao getStatusSolicitacao() {
		return statusSolicitacao;
	}
	public void setStatusSolicitacao(StatusSolicitacao statusSolicitacao) {
		this.statusSolicitacao = statusSolicitacao;
	}
	public Comarca getComarca() {
		return comarca;
	}
	public void setComarca(Comarca comarca) {
		this.comarca = comarca;
	}
	public FormularioAudiencia getFormularioAudiencia() {
		return formularioAudiencia;
	}
	public void setFormularioAudiencia(FormularioAudiencia formularioAudiencia) {
		this.formularioAudiencia = formularioAudiencia;
	}
	public BancaProcesso getBancaProcesso() {
		return bancaProcesso;
	}
	public void setBancaProcesso(BancaProcesso bancaProcesso) {
		this.bancaProcesso = bancaProcesso;
	}
	public Enviosolicitacao getEnviosolicitacao() {
		return enviosolicitacao;
	}
	public void setEnviosolicitacao(Enviosolicitacao enviosolicitacao) {
		this.enviosolicitacao = enviosolicitacao;
	}
	public ReciboPagamento getReciboPagamento() {
		return reciboPagamento;
	}
	public void setReciboPagamento(ReciboPagamento reciboPagamento) {
		this.reciboPagamento = reciboPagamento;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public AuditoriaInterna getAuditoriaInterna() {
		return auditoriaInterna;
	}
	public void setAuditoriaInterna(AuditoriaInterna auditoriaInterna) {
		this.auditoriaInterna = auditoriaInterna;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	public float getValordaalcada() {
		return valordaalcada;
	}
	public void setValordaalcada(float valordaalcada) {
		this.valordaalcada = valordaalcada;
	}
	public String getEmailenvio() {
		return emailenvio;
	}
	public void setEmailenvio(String emailenvio) {
		this.emailenvio = emailenvio;
	}
	public String getPago() {
		return pago;
	}
	public void setPago(String pago) {
		this.pago = pago;
	}
	public Integer getGrupo() {
		return grupo;
	}
	public void setGrupo(Integer grupo) {
		this.grupo = grupo;
	}
	public boolean isPropostaacordo() {
		return propostaacordo;
	}
	public void setPropostaacordo(boolean propostaacordo) {
		this.propostaacordo = propostaacordo;
	}
	public boolean isAudinterna() {
		return audinterna;
	}
	public void setAudinterna(boolean audinterna) {
		this.audinterna = audinterna;
	}
	public String getLide() {
		return lide;
	}
	public void setLide(String lide) {
		this.lide = lide;
	}
	public Integer getAvaliacaonota() {
		return avaliacaonota;
	}
	public void setAvaliacaonota(Integer avaliacaonota) {
		this.avaliacaonota = avaliacaonota;
	}
	public String getTextoavaliacao() {
		return textoavaliacao;
	}
	public void setTextoavaliacao(String textoavaliacao) {
		this.textoavaliacao = textoavaliacao;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (audinterna ? 1231 : 1237);
		result = prime * result + ((avaliacaonota == null) ? 0 : avaliacaonota.hashCode());
		result = prime * result + ((complemento == null) ? 0 : complemento.hashCode());
		result = prime * result + (convolada ? 1231 : 1237);
		result = prime * result + ((dataconclusao == null) ? 0 : dataconclusao.hashCode());
		result = prime * result + ((dataprazo == null) ? 0 : dataprazo.hashCode());
		result = prime * result + ((datasolictacao == null) ? 0 : datasolictacao.hashCode());
		result = prime * result + ((emailenvio == null) ? 0 : emailenvio.hashCode());
		result = prime * result + ((enviosolicitacao == null) ? 0 : enviosolicitacao.hashCode());
		result = prime * result + ((grupo == null) ? 0 : grupo.hashCode());
		result = prime * result + ((horaudiencia == null) ? 0 : horaudiencia.hashCode());
		result = prime * result + ((idsolicitacao == null) ? 0 : idsolicitacao.hashCode());
		result = prime * result + ((instrucoes == null) ? 0 : instrucoes.hashCode());
		result = prime * result + ((justificativa == null) ? 0 : justificativa.hashCode());
		result = prime * result + ((lide == null) ? 0 : lide.hashCode());
		result = prime * result + ((numcontrole == null) ? 0 : numcontrole.hashCode());
		result = prime * result + ((observacao == null) ? 0 : observacao.hashCode());
		result = prime * result + ((pago == null) ? 0 : pago.hashCode());
		result = prime * result + (propostaacordo ? 1231 : 1237);
		result = prime * result + ((referenciasolicitacao == null) ? 0 : referenciasolicitacao.hashCode());
		result = prime * result + ((statusexterno == null) ? 0 : statusexterno.hashCode());
		result = prime * result + (tempreposto ? 1231 : 1237);
		result = prime * result + ((textoavaliacao == null) ? 0 : textoavaliacao.hashCode());
		result = prime * result + ((tratposaudiencia == null) ? 0 : tratposaudiencia.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		result = prime * result + Float.floatToIntBits(valor);
		result = prime * result + Float.floatToIntBits(valordaalcada);
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
		Solicitacao other = (Solicitacao) obj;
		if (audinterna != other.audinterna)
			return false;
		if (avaliacaonota == null) {
			if (other.avaliacaonota != null)
				return false;
		} else if (!avaliacaonota.equals(other.avaliacaonota))
			return false;
		if (complemento == null) {
			if (other.complemento != null)
				return false;
		} else if (!complemento.equals(other.complemento))
			return false;
		if (convolada != other.convolada)
			return false;
		if (dataconclusao == null) {
			if (other.dataconclusao != null)
				return false;
		} else if (!dataconclusao.equals(other.dataconclusao))
			return false;
		if (dataprazo == null) {
			if (other.dataprazo != null)
				return false;
		} else if (!dataprazo.equals(other.dataprazo))
			return false;
		if (datasolictacao == null) {
			if (other.datasolictacao != null)
				return false;
		} else if (!datasolictacao.equals(other.datasolictacao))
			return false;
		if (emailenvio == null) {
			if (other.emailenvio != null)
				return false;
		} else if (!emailenvio.equals(other.emailenvio))
			return false;
		if (enviosolicitacao == null) {
			if (other.enviosolicitacao != null)
				return false;
		} else if (!enviosolicitacao.equals(other.enviosolicitacao))
			return false;
		if (grupo == null) {
			if (other.grupo != null)
				return false;
		} else if (!grupo.equals(other.grupo))
			return false;
		if (horaudiencia == null) {
			if (other.horaudiencia != null)
				return false;
		} else if (!horaudiencia.equals(other.horaudiencia))
			return false;
		if (idsolicitacao == null) {
			if (other.idsolicitacao != null)
				return false;
		} else if (!idsolicitacao.equals(other.idsolicitacao))
			return false;
		if (instrucoes == null) {
			if (other.instrucoes != null)
				return false;
		} else if (!instrucoes.equals(other.instrucoes))
			return false;
		if (justificativa == null) {
			if (other.justificativa != null)
				return false;
		} else if (!justificativa.equals(other.justificativa))
			return false;
		if (lide == null) {
			if (other.lide != null)
				return false;
		} else if (!lide.equals(other.lide))
			return false;
		if (numcontrole == null) {
			if (other.numcontrole != null)
				return false;
		} else if (!numcontrole.equals(other.numcontrole))
			return false;
		if (observacao == null) {
			if (other.observacao != null)
				return false;
		} else if (!observacao.equals(other.observacao))
			return false;
		if (pago == null) {
			if (other.pago != null)
				return false;
		} else if (!pago.equals(other.pago))
			return false;
		if (propostaacordo != other.propostaacordo)
			return false;
		if (referenciasolicitacao == null) {
			if (other.referenciasolicitacao != null)
				return false;
		} else if (!referenciasolicitacao.equals(other.referenciasolicitacao))
			return false;
		if (statusexterno == null) {
			if (other.statusexterno != null)
				return false;
		} else if (!statusexterno.equals(other.statusexterno))
			return false;
		if (tempreposto != other.tempreposto)
			return false;
		if (textoavaliacao == null) {
			if (other.textoavaliacao != null)
				return false;
		} else if (!textoavaliacao.equals(other.textoavaliacao))
			return false;
		if (tratposaudiencia == null) {
			if (other.tratposaudiencia != null)
				return false;
		} else if (!tratposaudiencia.equals(other.tratposaudiencia))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		if (Float.floatToIntBits(valor) != Float.floatToIntBits(other.valor))
			return false;
		if (Float.floatToIntBits(valordaalcada) != Float.floatToIntBits(other.valordaalcada))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Solicitacao [idsolicitacao=" + idsolicitacao + ", referenciasolicitacao=" + referenciasolicitacao
				+ ", datasolictacao=" + datasolictacao + ", dataconclusao=" + dataconclusao + ", dataprazo=" + dataprazo
				+ ", observacao=" + observacao + ", instrucoes=" + instrucoes + ", complemento=" + complemento
				+ ", justificativa=" + justificativa + ", tratposaudiencia=" + tratposaudiencia + ", numcontrole="
				+ numcontrole + ", tempreposto=" + tempreposto + ", convolada=" + convolada + ", horaudiencia="
				+ horaudiencia + ", statusexterno=" + statusexterno + ", processo=" + processo + ", renumeracao="
				+ renumeracao + ", statusSolicitacao=" + statusSolicitacao + ", comarca=" + comarca
				+ ", formularioAudiencia=" + formularioAudiencia + ", bancaProcesso=" + bancaProcesso
				+ ", enviosolicitacao=" + enviosolicitacao + ", reciboPagamento=" + reciboPagamento + ", usuario="
				+ usuario + ", auditoriaInterna=" + auditoriaInterna + ", valor=" + valor + ", valordaalcada="
				+ valordaalcada + ", emailenvio=" + emailenvio + ", pago=" + pago + ", grupo=" + grupo
				+ ", propostaacordo=" + propostaacordo + ", audinterna=" + audinterna + ", lide=" + lide
				+ ", avaliacaonota=" + avaliacaonota + ", textoavaliacao=" + textoavaliacao + "]";
	}
	
	

	
	
  

	
	
	
	
}
