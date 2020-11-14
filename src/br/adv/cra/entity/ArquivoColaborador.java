package br.adv.cra.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name = "gedcolaborador")
@SequenceGenerator(name = "gedcolaborador", sequenceName = "idgedarquivo", allocationSize = 1, initialValue = 1)
public class ArquivoColaborador {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gedcolaborador")
	private Integer idgedarquivo;
	private String descricao;
	private String nome;
	private String obsged;
	private String mesanocompetencia;
	private String link;
	private Date datainclusao;
	@ManyToOne
	private Correspondente corresp;
	public ArquivoColaborador() {

	}
	public ArquivoColaborador(Integer idgedarquivo, String descricao, String nome, String obsged,
			String mesanocompetencia, String link, Date datainclusao, Correspondente corresp) {
		super();
		this.idgedarquivo = idgedarquivo;
		this.descricao = descricao;
		this.nome = nome;
		this.obsged = obsged;
		this.mesanocompetencia = mesanocompetencia;
		this.link = link;
		this.datainclusao = datainclusao;
		this.corresp = corresp;
	}
	public Integer getIdgedarquivo() {
		return idgedarquivo;
	}
	public void setIdgedarquivo(Integer idgedarquivo) {
		this.idgedarquivo = idgedarquivo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getObsged() {
		return obsged;
	}
	public void setObsged(String obsged) {
		this.obsged = obsged;
	}
	public String getMesanocompetencia() {
		return mesanocompetencia;
	}
	public void setMesanocompetencia(String mesanocompetencia) {
		this.mesanocompetencia = mesanocompetencia;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public Date getDatainclusao() {
		return datainclusao;
	}
	public void setDatainclusao(Date datainclusao) {
		this.datainclusao = datainclusao;
	}
	public Correspondente getCorresp() {
		return corresp;
	}
	public void setCorresp(Correspondente corresp) {
		this.corresp = corresp;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((datainclusao == null) ? 0 : datainclusao.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((idgedarquivo == null) ? 0 : idgedarquivo.hashCode());
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result + ((mesanocompetencia == null) ? 0 : mesanocompetencia.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((obsged == null) ? 0 : obsged.hashCode());
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
		ArquivoColaborador other = (ArquivoColaborador) obj;
		if (datainclusao == null) {
			if (other.datainclusao != null)
				return false;
		} else if (!datainclusao.equals(other.datainclusao))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (idgedarquivo == null) {
			if (other.idgedarquivo != null)
				return false;
		} else if (!idgedarquivo.equals(other.idgedarquivo))
			return false;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		if (mesanocompetencia == null) {
			if (other.mesanocompetencia != null)
				return false;
		} else if (!mesanocompetencia.equals(other.mesanocompetencia))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (obsged == null) {
			if (other.obsged != null)
				return false;
		} else if (!obsged.equals(other.obsged))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ArquivoColaborador [idgedarquivo=" + idgedarquivo + ", descricao=" + descricao + ", nome=" + nome
				+ ", obsged=" + obsged + ", mesanocompetencia=" + mesanocompetencia + ", link=" + link
				+ ", datainclusao=" + datainclusao + ", corresp=" + corresp + "]";
	}
	
	
	
}
