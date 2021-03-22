package br.adv.cra.persistence;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.adv.cra.entity.Enviosolicitacao;

public class EnviodesolicitacaoDao implements Serializable {
	private static final long serialVersionUID = 1L;
	private Session session;
	private Query query;
	private Transaction transaction;

	public EnviodesolicitacaoDao() {
	}

	/**
	 * Salva o ENVIO DE
	 * 
	 * @param banco
	 * @return
	 */
	public String Salvar(Enviosolicitacao enviosolicitacao) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.saveOrUpdate(enviosolicitacao);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return "";
	}

	/**
	 * Altera o ENVIO DE
	 * 
	 * @param banco
	 * @return
	 */
	public String Alterar(Enviosolicitacao enviosolicitacao) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.update(enviosolicitacao);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return "";
	}

	/**
	 * Traz a lista O ENVIO DE
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Enviosolicitacao> buscargeral() {
		List<Enviosolicitacao> enviosolicitacaos = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = session.createQuery("from Enviosolicitacao");
			enviosolicitacaos = this.query.list();
			this.transaction.commit();
			this.session.close();

		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return enviosolicitacaos;
	}

	/**
	 * Traz um unico ENVIO DE
	 * 
	 * @param codigo
	 * @return
	 */
	public Enviosolicitacao trazenviodesolicitacao(Integer codigo) {
		Enviosolicitacao enviosolicitacao = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session
					.createQuery("from Enviosolicitacao where idenviosolicitacao=:idenviosolicitacao");
			this.query.setInteger("idenviosolicitacao", codigo);
			this.transaction.commit();
			enviosolicitacao = (Enviosolicitacao) this.query.uniqueResult();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());

		}
		return enviosolicitacao;
	}

}
