package br.adv.cra.entity;

import java.sql.Timestamp;
import java.util.Date;

public class Rejeitado {
	private Integer idsolicitacao;
	private String numeroprocesso;
	private String pasta;
	private String motivo;
	private String documento;
	private String anexadopor;
	private Timestamp datanexado;
	private Timestamp rejeitadoem;
	private Timestamp datasolicitacao;
	private String quemrejeitou;
	public Rejeitado() {

	}
	
	

	public Rejeitado(Integer idsolicitacao, String numeroprocesso, String pasta, String motivo, String documento,
			String anexadopor, Timestamp datanexado, Timestamp rejeitadoem, Timestamp datasolicitacao,
			String quemrejeitou) {
		super();
		this.idsolicitacao = idsolicitacao;
		this.numeroprocesso = numeroprocesso;
		this.pasta = pasta;
		this.motivo = motivo;
		this.documento = documento;
		this.anexadopor = anexadopor;
		this.datanexado = datanexado;
		this.rejeitadoem = rejeitadoem;
		this.datasolicitacao = datasolicitacao;
		this.quemrejeitou = quemrejeitou;
	}



	public Timestamp getRejeitadoem() {
		return rejeitadoem;
	}



	public void setRejeitadoem(Timestamp rejeitadoem) {
		this.rejeitadoem = rejeitadoem;
	}



	public Timestamp getDatasolicitacao() {
		return datasolicitacao;
	}



	public void setDatasolicitacao(Timestamp datasolicitacao) {
		this.datasolicitacao = datasolicitacao;
	}



	public String getQuemrejeitou() {
		return quemrejeitou;
	}



	public void setQuemrejeitou(String quemrejeitou) {
		this.quemrejeitou = quemrejeitou;
	}



	public void setDatanexado(Timestamp datanexado) {
		this.datanexado = datanexado;
	}



	public Integer getIdsolicitacao() {
		return idsolicitacao;
	}
	public void setIdsolicitacao(Integer idsolicitacao) {
		this.idsolicitacao = idsolicitacao;
	}
	public String getNumeroprocesso() {
		return numeroprocesso;
	}
	public void setNumeroprocesso(String numeroprocesso) {
		this.numeroprocesso = numeroprocesso;
	}
	public String getPasta() {
		return pasta;
	}
	public void setPasta(String pasta) {
		this.pasta = pasta;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getAnexadopor() {
		return anexadopor;
	}
	public void setAnexadopor(String anexadopor) {
		this.anexadopor = anexadopor;
	}
	public Date getDatanexado() {
		return datanexado;
	}
	

	@Override
	public String toString() {
		return "Rejeitado [idsolicitacao=" + idsolicitacao + ", numeroprocesso=" + numeroprocesso + ", pasta=" + pasta
				+ ", motivo=" + motivo + ", documento=" + documento + ", anexadopor=" + anexadopor + ", datanexado="
				+ datanexado + ", rejeitadoem=" + rejeitadoem + ", datasolicitacao=" + datasolicitacao
				+ ", quemrejeitou=" + quemrejeitou + "]";
	}
	
    
}
