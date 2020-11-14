package br.adv.cra.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "arquivosanexo")
@SequenceGenerator(name = "seqanexo", sequenceName = "idarquivoanexo", allocationSize = 1, initialValue = 1)
public class SolicitacaoAnexo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqanexo")
	private Integer idarquivoanexo;
	@Lob
	@Transient
	private byte[] arquivo;
	private String linkarquivo;
	private Date datasolicitacao;
	private String tipoarquivo;
	private String nomearquivo;
	private String operacao; // Entrada ou Saida
	private Integer origemarq;   // 1 - Siegecol 2-Cprpo
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinColumn(name = "idusuario")
	private Usuario usuario;


	public SolicitacaoAnexo() {

	}


	public SolicitacaoAnexo(Integer idarquivoanexo, byte[] arquivo, String linkarquivo, Date datasolicitacao,
			String tipoarquivo, String nomearquivo, String operacao, Integer origemarq, Usuario usuario) {
		this.idarquivoanexo = idarquivoanexo;
		this.arquivo = arquivo;
		this.linkarquivo = linkarquivo;
		this.datasolicitacao = datasolicitacao;
		this.tipoarquivo = tipoarquivo;
		this.nomearquivo = nomearquivo;
		this.operacao = operacao;
		this.origemarq = origemarq;
		this.usuario = usuario;
	}


	public Integer getIdarquivoanexo() {
		return idarquivoanexo;
	}


	public void setIdarquivoanexo(Integer idarquivoanexo) {
		this.idarquivoanexo = idarquivoanexo;
	}


	public byte[] getArquivo() {
		return arquivo;
	}


	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}


	public String getLinkarquivo() {
		return linkarquivo;
	}


	public void setLinkarquivo(String linkarquivo) {
		this.linkarquivo = linkarquivo;
	}


	public Date getDatasolicitacao() {
		return datasolicitacao;
	}


	public void setDatasolicitacao(Date datasolicitacao) {
		this.datasolicitacao = datasolicitacao;
	}


	public String getTipoarquivo() {
		return tipoarquivo;
	}


	public void setTipoarquivo(String tipoarquivo) {
		this.tipoarquivo = tipoarquivo;
	}


	public String getNomearquivo() {
		return nomearquivo;
	}


	public void setNomearquivo(String nomearquivo) {
		this.nomearquivo = nomearquivo;
	}


	public String getOperacao() {
		return operacao;
	}


	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}


	public Integer getOrigemarq() {
		return origemarq;
	}


	public void setOrigemarq(Integer origemarq) {
		this.origemarq = origemarq;
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
		result = prime * result + ((datasolicitacao == null) ? 0 : datasolicitacao.hashCode());
		result = prime * result + ((idarquivoanexo == null) ? 0 : idarquivoanexo.hashCode());
		result = prime * result + ((linkarquivo == null) ? 0 : linkarquivo.hashCode());
		result = prime * result + ((nomearquivo == null) ? 0 : nomearquivo.hashCode());
		result = prime * result + ((operacao == null) ? 0 : operacao.hashCode());
		result = prime * result + ((origemarq == null) ? 0 : origemarq.hashCode());
		result = prime * result + ((tipoarquivo == null) ? 0 : tipoarquivo.hashCode());
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
		SolicitacaoAnexo other = (SolicitacaoAnexo) obj;
		if (datasolicitacao == null) {
			if (other.datasolicitacao != null)
				return false;
		} else if (!datasolicitacao.equals(other.datasolicitacao))
			return false;
		if (idarquivoanexo == null) {
			if (other.idarquivoanexo != null)
				return false;
		} else if (!idarquivoanexo.equals(other.idarquivoanexo))
			return false;
		if (linkarquivo == null) {
			if (other.linkarquivo != null)
				return false;
		} else if (!linkarquivo.equals(other.linkarquivo))
			return false;
		if (nomearquivo == null) {
			if (other.nomearquivo != null)
				return false;
		} else if (!nomearquivo.equals(other.nomearquivo))
			return false;
		if (operacao == null) {
			if (other.operacao != null)
				return false;
		} else if (!operacao.equals(other.operacao))
			return false;
		if (origemarq == null) {
			if (other.origemarq != null)
				return false;
		} else if (!origemarq.equals(other.origemarq))
			return false;
		if (tipoarquivo == null) {
			if (other.tipoarquivo != null)
				return false;
		} else if (!tipoarquivo.equals(other.tipoarquivo))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "SolicitacaoAnexo [idarquivoanexo=" + idarquivoanexo + ", arquivo=" + Arrays.toString(arquivo)
				+ ", linkarquivo=" + linkarquivo + ", datasolicitacao=" + datasolicitacao + ", tipoarquivo="
				+ tipoarquivo + ", nomearquivo=" + nomearquivo + ", operacao=" + operacao + ", origemarq=" + origemarq
				+ ", usuario=" + usuario + "]";
	}



 
}
