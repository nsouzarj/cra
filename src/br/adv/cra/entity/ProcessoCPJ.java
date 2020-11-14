/**
 * 
 */
package br.adv.cra.entity;

import java.io.Serializable;

/**
 * @author nelson
 * 
 */
public class ProcessoCPJ implements Serializable {
	private static final long serialVersionUID = 1L;
	private String numprocesso;
	private String numficha;
	private String numprocessopes;
	private String nomeautor;
	private String nomereu;
	private String siglaloc;
	private String locnumero;
	private String siglaintegracao;
	private String numerointegracao;
	private String juizo;
	private String acao;
	private String ojnumero;
	private String ojsigla;
	private String assuntodoprocesso;
	private String proceletronico;

	public ProcessoCPJ() {
		proceletronico = "";

	}

	public ProcessoCPJ(String numprocesso, String numficha,
			String numprocessopes, String nomeautor, String nomereu,
			String siglaloc, String locnumero, String siglaintegracao,
			String numerointegracao, String juizo, String ojnumero,
			String ojsigla, String acao, String assuntodoprocesso,
			String proceletronico) {

		this.numprocesso = numprocesso;
		this.numficha = numficha;
		this.numprocessopes = numprocessopes;
		this.nomeautor = nomeautor;
		this.nomereu = nomereu;
		this.siglaloc = siglaloc;
		this.locnumero = locnumero;
		this.siglaintegracao = siglaintegracao;
		this.numerointegracao = numerointegracao;
		this.juizo = juizo;
		this.ojnumero = ojnumero;
		this.ojsigla = ojsigla;
		this.assuntodoprocesso = assuntodoprocesso;
		this.proceletronico = proceletronico;
		this.acao = acao;
	}

	public String getProceletronico() {
		return proceletronico;
	}

	public void setProceletronico(String proceletronico) {
		this.proceletronico = proceletronico;
	}

	public String getNumprocesso() {
		return numprocesso;
	}

	public void setNumprocesso(String numprocesso) {
		this.numprocesso = numprocesso;
	}

	public String getNumficha() {
		return numficha;
	}

	public void setNumficha(String numficha) {
		this.numficha = numficha;
	}

	public String getNumprocessopes() {
		return numprocessopes;
	}

	public void setNumprocessopes(String numprocessopes) {
		this.numprocessopes = numprocessopes;
	}

	public String getNomeautor() {
		return nomeautor;
	}

	public void setNomeautor(String nomeautor) {
		this.nomeautor = nomeautor;
	}

	public String getNomereu() {
		return nomereu;
	}

	public void setNomereu(String nomereu) {
		this.nomereu = nomereu;
	}

	public String getSiglaloc() {
		return siglaloc;
	}

	public void setSiglaloc(String siglaloc) {
		this.siglaloc = siglaloc;
	}

	public String getLocnumero() {
		return locnumero;
	}

	public void setLocnumero(String locnumero) {
		this.locnumero = locnumero;
	}

	public String getSiglaintegracao() {
		return siglaintegracao;
	}

	public void setSiglaintegracao(String siglaintegracao) {
		this.siglaintegracao = siglaintegracao;
	}

	public String getNumerointegracao() {
		return numerointegracao;
	}

	public void setNumerointegracao(String numerointegracao) {
		this.numerointegracao = numerointegracao;
	}

	public String getJuizo() {
		return juizo;
	}

	public void setJuizo(String juizo) {
		this.juizo = juizo;
	}

	public String getOjnumero() {
		return ojnumero;
	}

	public void setOjnumero(String ojnumero) {
		this.ojnumero = ojnumero;
	}

	public String getOjsigla() {
		return ojsigla;
	}

	public void setOjsigla(String ojsigla) {
		this.ojsigla = ojsigla;
	}

	public String getAssuntodoprocesso() {
		return assuntodoprocesso;
	}

	public void setAssuntodoprocesso(String assuntodoprocesso) {
		this.assuntodoprocesso = assuntodoprocesso;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	@Override
	public String toString() {
		return "ProcessoCPJ [numprocesso=" + numprocesso + ", numficha="
				+ numficha + ", numprocessopes=" + numprocessopes
				+ ", nomeautor=" + nomeautor + ", nomereu=" + nomereu
				+ ", siglaloc=" + siglaloc + ", locnumero=" + locnumero
				+ ", siglaintegracao=" + siglaintegracao
				+ ", numerointegracao=" + numerointegracao + ", juizo=" + juizo
				+ ", ojnumero=" + ojnumero + ", ojsigla=" + ojsigla
				+ ", assuntodoprocesso=" + assuntodoprocesso + "]";
	}

}
