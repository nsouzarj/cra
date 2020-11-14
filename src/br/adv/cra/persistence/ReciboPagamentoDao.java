package br.adv.cra.persistence;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.adv.cra.entity.ReciboPagamento;

public class ReciboPagamentoDao implements Serializable {
	private static final long serialVersionUID = 1L;
	private Session session;
	private Query query;
	private Transaction transaction;

	public ReciboPagamentoDao() {
	}

	public ReciboPagamentoDao(Session session, Query query,
			Transaction transaction) {
		this.session = session;
		this.query = query;
		this.transaction = transaction;
	}

	/**
	 * Salva a classe de recibo pagamento na base de dados
	 * 
	 * @param reciboPagamento
	 */
	public void Salvar(ReciboPagamento reciboPagamento) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.save(reciboPagamento);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}

	}

	/**
	 * Altera o recibo de pagamento
	 * 
	 * @param reciboPagamento
	 */
	public void Alterar(ReciboPagamento reciboPagamento) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.update(reciboPagamento);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	/**
	 * Excluir recibo de pagamento
	 * 
	 * @param reciboPagamento
	 */
	public void Excluir(ReciboPagamento reciboPagamento) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.delete(reciboPagamento);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	/*
	 * Traz recibo depagamento
	 */
	public ReciboPagamento trazcrecibo(Integer codigo) {
		ReciboPagamento reciboPagamento = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session
					.createQuery("from ReciboPagamento where idrecibo=:codigo");
			this.query.setInteger("codigo", codigo);
			this.query.setCacheable(true);
			this.transaction.commit();
			reciboPagamento = (ReciboPagamento) this.query.uniqueResult();
			this.session.close();
		} catch (HibernateException e) {
		}
		return reciboPagamento;
	}

}
