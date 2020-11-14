package br.adv.cra.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Arquivos do cpro
 * 
 * @author Nelson
 *
 */
public class ArquivoAnexoCPRO implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer idArquivo;
	private Integer idContexto;
	private String PastaDoProcesso;
	private String NomeDocumento;
	private String CaminhoGed;
	private String AnexadoPor;
	private String TipoDocumento;
	private String Descricao;
	private Timestamp AnexadoEm;
	private boolean rejeitado;
	private String motivodarejeicao;

	public ArquivoAnexoCPRO() {

	}

	public ArquivoAnexoCPRO(Integer idArquivo, Integer idContexto, String pastaDoProcesso, String nomeDocumento,
			String caminhoGed, String anexadoPor, String tipoDocumento, String descricao, Timestamp anexadoEm,
			boolean rejeitado, String motivodarejeicao) {
		super();
		this.idArquivo = idArquivo;
		this.idContexto = idContexto;
		PastaDoProcesso = pastaDoProcesso;
		NomeDocumento = nomeDocumento;
		CaminhoGed = caminhoGed;
		AnexadoPor = anexadoPor;
		TipoDocumento = tipoDocumento;
		Descricao = descricao;
		AnexadoEm = anexadoEm;
		this.rejeitado = rejeitado;
		this.motivodarejeicao = motivodarejeicao;
	}





	public String getMotivodarejeicao() {
		return motivodarejeicao;
	}

	public void setMotivodarejeicao(String motivodarejeicao) {
		this.motivodarejeicao = motivodarejeicao;
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

	public Timestamp getAnexadoEm() {
		return AnexadoEm;
	}

	public void setAnexadoEm(Timestamp anexadoEm) {
		AnexadoEm = anexadoEm;
	}
	

	public boolean isRejeitado() {
		return rejeitado;
	}

	public void setRejeitado(boolean rejeitado) {
		this.rejeitado = rejeitado;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((AnexadoEm == null) ? 0 : AnexadoEm.hashCode());
		result = prime * result + ((AnexadoPor == null) ? 0 : AnexadoPor.hashCode());
		result = prime * result + ((CaminhoGed == null) ? 0 : CaminhoGed.hashCode());
		result = prime * result + ((Descricao == null) ? 0 : Descricao.hashCode());
		result = prime * result + ((NomeDocumento == null) ? 0 : NomeDocumento.hashCode());
		result = prime * result + ((PastaDoProcesso == null) ? 0 : PastaDoProcesso.hashCode());
		result = prime * result + ((TipoDocumento == null) ? 0 : TipoDocumento.hashCode());
		result = prime * result + ((idArquivo == null) ? 0 : idArquivo.hashCode());
		result = prime * result + ((idContexto == null) ? 0 : idContexto.hashCode());
		result = prime * result + ((motivodarejeicao == null) ? 0 : motivodarejeicao.hashCode());
		result = prime * result + (rejeitado ? 1231 : 1237);
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
		ArquivoAnexoCPRO other = (ArquivoAnexoCPRO) obj;
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
		if (motivodarejeicao == null) {
			if (other.motivodarejeicao != null)
				return false;
		} else if (!motivodarejeicao.equals(other.motivodarejeicao))
			return false;
		if (rejeitado != other.rejeitado)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ArquivoAnexoCPRO [idArquivo=" + idArquivo + ", idContexto=" + idContexto + ", PastaDoProcesso="
				+ PastaDoProcesso + ", NomeDocumento=" + NomeDocumento + ", CaminhoGed=" + CaminhoGed + ", AnexadoPor="
				+ AnexadoPor + ", TipoDocumento=" + TipoDocumento + ", Descricao=" + Descricao + ", AnexadoEm="
				+ AnexadoEm + ", rejeitado=" + rejeitado + ", motivodarejeicao=" + motivodarejeicao + "]";
	}

}
