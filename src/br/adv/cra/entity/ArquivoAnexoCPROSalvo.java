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

/**
 * Arquivos do cpro
 * 
 * @author Nelson
 * Esta classe e a tabela dos arquivos salos do cppro
 *
 */

@Entity
@Table(name = "anexocprosalvo")
@SequenceGenerator(name = "seqcprposalvo", sequenceName = "idarqcpprosalvo", allocationSize = 1, initialValue = 1)
public class ArquivoAnexoCPROSalvo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqcprposalvo")
	private Integer idarqcpprosalvo;
	private Integer idArquivo;
	private Integer idContexto;
	private Integer idsolicitacao;
	private String PastaDoProcesso;
	private String NomeDocumento;
	private String CaminhoGed;
	private String AnexadoPor;
	private String TipoDocumento;
	private String Descricao;
	private Date AnexadoEm;
	private Date BaixadoEm;
	private boolean baixado;
	private boolean rejeitado;
	private Usuario rejeitadopor; //Não tem funcionalidade
	private Date datareijeicao;
	@Column(columnDefinition = "TEXT")
	private String motivodarejeicao;

	public ArquivoAnexoCPROSalvo() {

	}

	public ArquivoAnexoCPROSalvo(Integer idarqcpprosalvo, Integer idArquivo, Integer idContexto, Integer idsolicitacao,
			String pastaDoProcesso, String nomeDocumento, String caminhoGed, String anexadoPor, String tipoDocumento,
			String descricao, Date anexadoEm, Date baixadoEm, boolean baixado, boolean rejeitado, Usuario rejeitadopor,
			Date datareijeicao, String motivodarejeicao) {
		super();
		this.idarqcpprosalvo = idarqcpprosalvo;
		this.idArquivo = idArquivo;
		this.idContexto = idContexto;
		this.idsolicitacao = idsolicitacao;
		PastaDoProcesso = pastaDoProcesso;
		NomeDocumento = nomeDocumento;
		CaminhoGed = caminhoGed;
		AnexadoPor = anexadoPor;
		TipoDocumento = tipoDocumento;
		Descricao = descricao;
		AnexadoEm = anexadoEm;
		BaixadoEm = baixadoEm;
		this.baixado = baixado;
		this.rejeitado = rejeitado;
		this.rejeitadopor = rejeitadopor;
		this.datareijeicao = datareijeicao;
		this.motivodarejeicao = motivodarejeicao;
	}

	public Integer getIdarqcpprosalvo() {
		return idarqcpprosalvo;
	}

	public void setIdarqcpprosalvo(Integer idarqcpprosalvo) {
		this.idarqcpprosalvo = idarqcpprosalvo;
	}

	public Integer getIdArquivo() {
		return idArquivo;
	}

	public void setIdArquivo(Integer idArquivo) {
		this.idArquivo = idArquivo;
	}

	public Integer getIdContexto() {
		return idContexto;
	}

	public void setIdContexto(Integer idContexto) {
		this.idContexto = idContexto;
	}

	public Integer getIdsolicitacao() {
		return idsolicitacao;
	}

	public void setIdsolicitacao(Integer idsolicitacao) {
		this.idsolicitacao = idsolicitacao;
	}

	public String getPastaDoProcesso() {
		return PastaDoProcesso;
	}

	public void setPastaDoProcesso(String pastaDoProcesso) {
		PastaDoProcesso = pastaDoProcesso;
	}

	public String getNomeDocumento() {
		return NomeDocumento;
	}

	public void setNomeDocumento(String nomeDocumento) {
		NomeDocumento = nomeDocumento;
	}

	public String getCaminhoGed() {
		return CaminhoGed;
	}

	public void setCaminhoGed(String caminhoGed) {
		CaminhoGed = caminhoGed;
	}

	public String getAnexadoPor() {
		return AnexadoPor;
	}

	public void setAnexadoPor(String anexadoPor) {
		AnexadoPor = anexadoPor;
	}

	public String getTipoDocumento() {
		return TipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		TipoDocumento = tipoDocumento;
	}

	public String getDescricao() {
		return Descricao;
	}

	public void setDescricao(String descricao) {
		Descricao = descricao;
	}

	public Date getAnexadoEm() {
		return AnexadoEm;
	}

	public void setAnexadoEm(Date anexadoEm) {
		AnexadoEm = anexadoEm;
	}

	public Date getBaixadoEm() {
		return BaixadoEm;
	}

	public void setBaixadoEm(Date baixadoEm) {
		BaixadoEm = baixadoEm;
	}

	public boolean isBaixado() {
		return baixado;
	}

	public void setBaixado(boolean baixado) {
		this.baixado = baixado;
	}

	public boolean isRejeitado() {
		return rejeitado;
	}

	public void setRejeitado(boolean rejeitado) {
		this.rejeitado = rejeitado;
	}

	public Usuario getRejeitadopor() {
		return rejeitadopor;
	}

	public void setRejeitadopor(Usuario rejeitadopor) {
		this.rejeitadopor = rejeitadopor;
	}

	public Date getDatareijeicao() {
		return datareijeicao;
	}

	public void setDatareijeicao(Date datareijeicao) {
		this.datareijeicao = datareijeicao;
	}

	public String getMotivodarejeicao() {
		return motivodarejeicao;
	}

	public void setMotivodarejeicao(String motivodarejeicao) {
		this.motivodarejeicao = motivodarejeicao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((AnexadoEm == null) ? 0 : AnexadoEm.hashCode());
		result = prime * result + ((AnexadoPor == null) ? 0 : AnexadoPor.hashCode());
		result = prime * result + ((BaixadoEm == null) ? 0 : BaixadoEm.hashCode());
		result = prime * result + ((CaminhoGed == null) ? 0 : CaminhoGed.hashCode());
		result = prime * result + ((Descricao == null) ? 0 : Descricao.hashCode());
		result = prime * result + ((NomeDocumento == null) ? 0 : NomeDocumento.hashCode());
		result = prime * result + ((PastaDoProcesso == null) ? 0 : PastaDoProcesso.hashCode());
		result = prime * result + ((TipoDocumento == null) ? 0 : TipoDocumento.hashCode());
		result = prime * result + (baixado ? 1231 : 1237);
		result = prime * result + ((datareijeicao == null) ? 0 : datareijeicao.hashCode());
		result = prime * result + ((idArquivo == null) ? 0 : idArquivo.hashCode());
		result = prime * result + ((idContexto == null) ? 0 : idContexto.hashCode());
		result = prime * result + ((idarqcpprosalvo == null) ? 0 : idarqcpprosalvo.hashCode());
		result = prime * result + ((idsolicitacao == null) ? 0 : idsolicitacao.hashCode());
		result = prime * result + ((motivodarejeicao == null) ? 0 : motivodarejeicao.hashCode());
		result = prime * result + (rejeitado ? 1231 : 1237);
		result = prime * result + ((rejeitadopor == null) ? 0 : rejeitadopor.hashCode());
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
		ArquivoAnexoCPROSalvo other = (ArquivoAnexoCPROSalvo) obj;
		if (AnexadoEm == null) {
			if (other.AnexadoEm != null)
				return false;
		} else if (!AnexadoEm.equals(other.AnexadoEm))
			return false;
		if (AnexadoPor == null) {
			if (other.AnexadoPor != null)
				return false;
		} else if (!AnexadoPor.equals(other.AnexadoPor))
			return false;
		if (BaixadoEm == null) {
			if (other.BaixadoEm != null)
				return false;
		} else if (!BaixadoEm.equals(other.BaixadoEm))
			return false;
		if (CaminhoGed == null) {
			if (other.CaminhoGed != null)
				return false;
		} else if (!CaminhoGed.equals(other.CaminhoGed))
			return false;
		if (Descricao == null) {
			if (other.Descricao != null)
				return false;
		} else if (!Descricao.equals(other.Descricao))
			return false;
		if (NomeDocumento == null) {
			if (other.NomeDocumento != null)
				return false;
		} else if (!NomeDocumento.equals(other.NomeDocumento))
			return false;
		if (PastaDoProcesso == null) {
			if (other.PastaDoProcesso != null)
				return false;
		} else if (!PastaDoProcesso.equals(other.PastaDoProcesso))
			return false;
		if (TipoDocumento == null) {
			if (other.TipoDocumento != null)
				return false;
		} else if (!TipoDocumento.equals(other.TipoDocumento))
			return false;
		if (baixado != other.baixado)
			return false;
		if (datareijeicao == null) {
			if (other.datareijeicao != null)
				return false;
		} else if (!datareijeicao.equals(other.datareijeicao))
			return false;
		if (idArquivo == null) {
			if (other.idArquivo != null)
				return false;
		} else if (!idArquivo.equals(other.idArquivo))
			return false;
		if (idContexto == null) {
			if (other.idContexto != null)
				return false;
		} else if (!idContexto.equals(other.idContexto))
			return false;
		if (idarqcpprosalvo == null) {
			if (other.idarqcpprosalvo != null)
				return false;
		} else if (!idarqcpprosalvo.equals(other.idarqcpprosalvo))
			return false;
		if (idsolicitacao == null) {
			if (other.idsolicitacao != null)
				return false;
		} else if (!idsolicitacao.equals(other.idsolicitacao))
			return false;
		if (motivodarejeicao == null) {
			if (other.motivodarejeicao != null)
				return false;
		} else if (!motivodarejeicao.equals(other.motivodarejeicao))
			return false;
		if (rejeitado != other.rejeitado)
			return false;
		if (rejeitadopor == null) {
			if (other.rejeitadopor != null)
				return false;
		} else if (!rejeitadopor.equals(other.rejeitadopor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ArquivoAnexoCPROSalvo [idarqcpprosalvo=" + idarqcpprosalvo + ", idArquivo=" + idArquivo
				+ ", idContexto=" + idContexto + ", idsolicitacao=" + idsolicitacao + ", PastaDoProcesso="
				+ PastaDoProcesso + ", NomeDocumento=" + NomeDocumento + ", CaminhoGed=" + CaminhoGed + ", AnexadoPor="
				+ AnexadoPor + ", TipoDocumento=" + TipoDocumento + ", Descricao=" + Descricao + ", AnexadoEm="
				+ AnexadoEm + ", BaixadoEm=" + BaixadoEm + ", baixado=" + baixado + ", rejeitado=" + rejeitado
				+ ", rejeitadopor=" + rejeitadopor + ", datareijeicao=" + datareijeicao + ", motivodarejeicao="
				+ motivodarejeicao + "]";
	}

}
