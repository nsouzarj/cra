package br.adv.cra.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "processo")
@SequenceGenerator(name = "seqprocesso", sequenceName = "idprocesso", allocationSize = 1, initialValue = 1)
public class Processo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqprocesso")
	private Integer idprocesso;
	private String numeroprocesso;
	private String numeroprocessopesq;
	private String parte;
	private String adverso;
	private String posicao;
	private String status;
	private String cartorio;
	@Column(length = 600, columnDefinition = "Text")
	private String assunto;
	private String localizacao;
	private String numerointegracao;
	@OneToOne(fetch=FetchType.EAGER)
	private Comarca comarca;
	@OneToOne(fetch=FetchType.EAGER)
	private Orgao orgao;
	private Integer numorgao;
	private String proceletronico;
	private Integer quantsoli;
	@Transient
	private Integer totalfeita;

	public Processo() {
	}

	public Processo(Integer idprocesso, String numeroprocesso, String numeroprocessopesq, String parte, String adverso,
			String posicao, String status, String cartorio, String assunto, String localizacao, String numerointegracao,
			Comarca comarca, Orgao orgao, Integer numorgao, String proceletronico, Integer quantsoli,
			Integer totalfeita) {
		super();
		this.idprocesso = idprocesso;
		this.numeroprocesso = numeroprocesso;
		this.numeroprocessopesq = numeroprocessopesq;
		this.parte = parte;
		this.adverso = adverso;
		this.posicao = posicao;
		this.status = status;
		this.cartorio = cartorio;
		this.assunto = assunto;
		this.localizacao = localizacao;
		this.numerointegracao = numerointegracao;
		this.comarca = comarca;
		this.orgao = orgao;
		this.numorgao = numorgao;
		this.proceletronico = proceletronico;
		this.quantsoli = quantsoli;
		this.totalfeita = totalfeita;
	}

	public Integer getIdprocesso() {
		return idprocesso;
	}

	public void setIdprocesso(Integer idprocesso) {
		this.idprocesso = idprocesso;
	}

	public String getNumeroprocesso() {
		return numeroprocesso;
	}

	public void setNumeroprocesso(String numeroprocesso) {
		this.numeroprocesso = numeroprocesso;
	}

	public String getNumeroprocessopesq() {
		return numeroprocessopesq;
	}

	public void setNumeroprocessopesq(String numeroprocessopesq) {
		this.numeroprocessopesq = numeroprocessopesq;
	}

	public String getParte() {
		return parte;
	}

	public void setParte(String parte) {
		this.parte = parte;
	}

	public String getAdverso() {
		return adverso;
	}

	public void setAdverso(String adverso) {
		this.adverso = adverso;
	}

	public String getPosicao() {
		return posicao;
	}

	public void setPosicao(String posicao) {
		this.posicao = posicao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCartorio() {
		return cartorio;
	}

	public void setCartorio(String cartorio) {
		this.cartorio = cartorio;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public String getNumerointegracao() {
		return numerointegracao;
	}

	public void setNumerointegracao(String numerointegracao) {
		this.numerointegracao = numerointegracao;
	}

	public Comarca getComarca() {
		return comarca;
	}

	public void setComarca(Comarca comarca) {
		this.comarca = comarca;
	}

	public Orgao getOrgao() {
		return orgao;
	}

	public void setOrgao(Orgao orgao) {
		this.orgao = orgao;
	}

	public Integer getNumorgao() {
		return numorgao;
	}

	public void setNumorgao(Integer numorgao) {
		this.numorgao = numorgao;
	}

	public String getProceletronico() {
		return proceletronico;
	}

	public void setProceletronico(String proceletronico) {
		this.proceletronico = proceletronico;
	}

	public Integer getQuantsoli() {
		return quantsoli;
	}

	public void setQuantsoli(Integer quantsoli) {
		this.quantsoli = quantsoli;
	}

	public Integer getTotalfeita() {
		return totalfeita;
	}

	public void setTotalfeita(Integer totalfeita) {
		this.totalfeita = totalfeita;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adverso == null) ? 0 : adverso.hashCode());
		result = prime * result + ((assunto == null) ? 0 : assunto.hashCode());
		result = prime * result + ((cartorio == null) ? 0 : cartorio.hashCode());
		result = prime * result + ((idprocesso == null) ? 0 : idprocesso.hashCode());
		result = prime * result + ((localizacao == null) ? 0 : localizacao.hashCode());
		result = prime * result + ((numerointegracao == null) ? 0 : numerointegracao.hashCode());
		result = prime * result + ((numeroprocesso == null) ? 0 : numeroprocesso.hashCode());
		result = prime * result + ((numeroprocessopesq == null) ? 0 : numeroprocessopesq.hashCode());
		result = prime * result + ((numorgao == null) ? 0 : numorgao.hashCode());
		result = prime * result + ((parte == null) ? 0 : parte.hashCode());
		result = prime * result + ((posicao == null) ? 0 : posicao.hashCode());
		result = prime * result + ((proceletronico == null) ? 0 : proceletronico.hashCode());
		result = prime * result + ((quantsoli == null) ? 0 : quantsoli.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((totalfeita == null) ? 0 : totalfeita.hashCode());
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
		Processo other = (Processo) obj;
		if (adverso == null) {
			if (other.adverso != null)
				return false;
		} else if (!adverso.equals(other.adverso))
			return false;
		if (assunto == null) {
			if (other.assunto != null)
				return false;
		} else if (!assunto.equals(other.assunto))
			return false;
		if (cartorio == null) {
			if (other.cartorio != null)
				return false;
		} else if (!cartorio.equals(other.cartorio))
			return false;
		if (idprocesso == null) {
			if (other.idprocesso != null)
				return false;
		} else if (!idprocesso.equals(other.idprocesso))
			return false;
		if (localizacao == null) {
			if (other.localizacao != null)
				return false;
		} else if (!localizacao.equals(other.localizacao))
			return false;
		if (numerointegracao == null) {
			if (other.numerointegracao != null)
				return false;
		} else if (!numerointegracao.equals(other.numerointegracao))
			return false;
		if (numeroprocesso == null) {
			if (other.numeroprocesso != null)
				return false;
		} else if (!numeroprocesso.equals(other.numeroprocesso))
			return false;
		if (numeroprocessopesq == null) {
			if (other.numeroprocessopesq != null)
				return false;
		} else if (!numeroprocessopesq.equals(other.numeroprocessopesq))
			return false;
		if (numorgao == null) {
			if (other.numorgao != null)
				return false;
		} else if (!numorgao.equals(other.numorgao))
			return false;
		if (parte == null) {
			if (other.parte != null)
				return false;
		} else if (!parte.equals(other.parte))
			return false;
		if (posicao == null) {
			if (other.posicao != null)
				return false;
		} else if (!posicao.equals(other.posicao))
			return false;
		if (proceletronico == null) {
			if (other.proceletronico != null)
				return false;
		} else if (!proceletronico.equals(other.proceletronico))
			return false;
		if (quantsoli == null) {
			if (other.quantsoli != null)
				return false;
		} else if (!quantsoli.equals(other.quantsoli))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (totalfeita == null) {
			if (other.totalfeita != null)
				return false;
		} else if (!totalfeita.equals(other.totalfeita))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Processo [idprocesso=" + idprocesso + ", numeroprocesso=" + numeroprocesso + ", numeroprocessopesq="
				+ numeroprocessopesq + ", parte=" + parte + ", adverso=" + adverso + ", posicao=" + posicao
				+ ", status=" + status + ", cartorio=" + cartorio + ", assunto=" + assunto + ", localizacao="
				+ localizacao + ", numerointegracao=" + numerointegracao + ", comarca=" + comarca + ", orgao=" + orgao
				+ ", numorgao=" + numorgao + ", proceletronico=" + proceletronico + ", quantsoli=" + quantsoli
				+ ", totalfeita=" + totalfeita + "]";
	}
	
	
  
}
