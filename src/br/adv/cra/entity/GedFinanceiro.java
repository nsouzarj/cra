package br.adv.cra.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "gedfinanceiro")
@SequenceGenerator(name = "seqgedfin", sequenceName = "idgedinf", allocationSize = 1, initialValue = 1)
public class GedFinanceiro implements Serializable  {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqgedfin")
	private Integer idgedfin;
	private Float valorbruto;
	private Float volumetria;
	private Float percvolumetria;
	private Float desconto;
	private Float percdesconto;
	private String notadedebito;
	private Float valor;
	private Date data;
	private String uf;
	@ManyToOne
	private Correspondente correspondente;
	private String nomearquivo;
	private String link;
	public GedFinanceiro() {
	
	}

	public GedFinanceiro(Integer idgedfin, Float valorbruto, Float volumetria, Float percvolumetria, Float desconto,
			Float percdesconto, String notadedebito, Float valor, Date data, String uf, Correspondente correspondente,
			String nomearquivo, String link) {
		super();
		this.idgedfin = idgedfin;
		this.valorbruto = valorbruto;
		this.volumetria = volumetria;
		this.percvolumetria = percvolumetria;
		this.desconto = desconto;
		this.percdesconto = percdesconto;
		this.notadedebito = notadedebito;
		this.valor = valor;
		this.data = data;
		this.uf = uf;
		this.correspondente = correspondente;
		this.nomearquivo = nomearquivo;
		this.link = link;
	}

	public Integer getIdgedfin() {
		return idgedfin;
	}

	public void setIdgedfin(Integer idgedfin) {
		this.idgedfin = idgedfin;
	}

	public Float getValorbruto() {
		return valorbruto;
	}

	public void setValorbruto(Float valorbruto) {
		this.valorbruto = valorbruto;
	}

	public Float getVolumetria() {
		return volumetria;
	}

	public void setVolumetria(Float volumetria) {
		this.volumetria = volumetria;
	}

	public Float getPercvolumetria() {
		return percvolumetria;
	}

	public void setPercvolumetria(Float percvolumetria) {
		this.percvolumetria = percvolumetria;
	}

	public Float getDesconto() {
		return desconto;
	}

	public void setDesconto(Float desconto) {
		this.desconto = desconto;
	}

	public Float getPercdesconto() {
		return percdesconto;
	}

	public void setPercdesconto(Float percdesconto) {
		this.percdesconto = percdesconto;
	}

	public String getNotadedebito() {
		return notadedebito;
	}

	public void setNotadedebito(String notadedebito) {
		this.notadedebito = notadedebito;
	}

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public Correspondente getCorrespondente() {
		return correspondente;
	}

	public void setCorrespondente(Correspondente correspondente) {
		this.correspondente = correspondente;
	}

	public String getNomearquivo() {
		return nomearquivo;
	}

	public void setNomearquivo(String nomearquivo) {
		this.nomearquivo = nomearquivo;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((desconto == null) ? 0 : desconto.hashCode());
		result = prime * result + ((idgedfin == null) ? 0 : idgedfin.hashCode());
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result + ((nomearquivo == null) ? 0 : nomearquivo.hashCode());
		result = prime * result + ((notadedebito == null) ? 0 : notadedebito.hashCode());
		result = prime * result + ((percdesconto == null) ? 0 : percdesconto.hashCode());
		result = prime * result + ((percvolumetria == null) ? 0 : percvolumetria.hashCode());
		result = prime * result + ((uf == null) ? 0 : uf.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		result = prime * result + ((valorbruto == null) ? 0 : valorbruto.hashCode());
		result = prime * result + ((volumetria == null) ? 0 : volumetria.hashCode());
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
		GedFinanceiro other = (GedFinanceiro) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (desconto == null) {
			if (other.desconto != null)
				return false;
		} else if (!desconto.equals(other.desconto))
			return false;
		if (idgedfin == null) {
			if (other.idgedfin != null)
				return false;
		} else if (!idgedfin.equals(other.idgedfin))
			return false;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		if (nomearquivo == null) {
			if (other.nomearquivo != null)
				return false;
		} else if (!nomearquivo.equals(other.nomearquivo))
			return false;
		if (notadedebito == null) {
			if (other.notadedebito != null)
				return false;
		} else if (!notadedebito.equals(other.notadedebito))
			return false;
		if (percdesconto == null) {
			if (other.percdesconto != null)
				return false;
		} else if (!percdesconto.equals(other.percdesconto))
			return false;
		if (percvolumetria == null) {
			if (other.percvolumetria != null)
				return false;
		} else if (!percvolumetria.equals(other.percvolumetria))
			return false;
		if (uf == null) {
			if (other.uf != null)
				return false;
		} else if (!uf.equals(other.uf))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		if (valorbruto == null) {
			if (other.valorbruto != null)
				return false;
		} else if (!valorbruto.equals(other.valorbruto))
			return false;
		if (volumetria == null) {
			if (other.volumetria != null)
				return false;
		} else if (!volumetria.equals(other.volumetria))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GedFinanceiro [idgedfin=" + idgedfin + ", valorbruto=" + valorbruto + ", volumetria=" + volumetria
				+ ", percvolumetria=" + percvolumetria + ", desconto=" + desconto + ", percdesconto=" + percdesconto
				+ ", notadedebito=" + notadedebito + ", valor=" + valor + ", data=" + data + ", uf=" + uf
				+ ", correspondente=" + correspondente + ", nomearquivo=" + nomearquivo + ", link=" + link + "]";
	}
	
	
	

}
