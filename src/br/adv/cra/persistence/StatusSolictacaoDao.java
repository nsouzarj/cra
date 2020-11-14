package br.adv.cra.persistence;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.adv.cra.entity.StatusSolicitacao;

public class StatusSolictacaoDao implements Serializable {
	private static final long serialVersionUID = 1L;
	private Session session;
	private Query query;
	private Transaction transaction;

	/**
	 * Salva o status
	 * 
	 * @param statusSolicitacao
	 * @return
	 */
	public String Salvar(StatusSolicitacao statusSolicitacao) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.save(statusSolicitacao);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return "";
	}

	/**
	 * Altera o status
	 * 
	 * @param statusSolicitacao
	 * @return
	 */
	public String Alterar(StatusSolicitacao statusSolicitacao) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = session.beginTransaction();
			this.session.update(statusSolicitacao);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return "";
	}

	/**
	 * Busca geral
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<StatusSolicitacao> buscargeral() {
		List<StatusSolicitacao> busca = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = session.beginTransaction();
			this.query = session.createQuery("from StatusSolicitacao");
			busca = this.query.list();
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
		}
		return busca;
	}

	/**
	 * Traz unico status
	 * 
	 * @param codigo
	 * @return
	 */
	public StatusSolicitacao trazunico(Integer codigo) {
		StatusSolicitacao statusSolicitacao = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = session.beginTransaction();
			this.query = this.session
					.createQuery("from StatusSolicitacao where idstatus=:codigo");
			this.query.setInteger("codigo", codigo);
			this.transaction.commit();
			statusSolicitacao = (StatusSolicitacao) this.query.uniqueResult();
			this.session.close();
		} catch (HibernateException e) {

		}
		return statusSolicitacao;
	}
}
