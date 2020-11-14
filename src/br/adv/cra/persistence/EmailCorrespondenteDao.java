package br.adv.cra.persistence;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.adv.cra.entity.EmailCorrespondente;

public class EmailCorrespondenteDao implements Serializable {
	private static final long serialVersionUID = 1L;
	private Session session;
	private Query query;
	private Transaction transaction;

	public EmailCorrespondenteDao() {
	}

	/**
	 * Salva o banco
	 * 
	 * @param banco
	 * @return
	 */
	public String Salvar(EmailCorrespondente emailCorrespondente) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.save(emailCorrespondente);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return "";
	}

	/**
	 * Altera o banco
	 * 
	 * @param banco
	 * @return
	 */
	public String Alterar(EmailCorrespondente emailCorrespondente) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.update(emailCorrespondente);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return "";
	}

	/**
	 * Traz unico email do correspodnente
	 * 
	 * @param codigo
	 * @return
	 */

	public EmailCorrespondente trazemail(Integer codigo) {
		EmailCorrespondente emailCorrespondente = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session
					.createQuery("from EmailCorrespondente where idemail=:codigo");
			this.query.setInteger("idemail", codigo);
			this.transaction.commit();
			emailCorrespondente = (EmailCorrespondente) this.query
					.uniqueResult();
			this.session.close();
		} catch (HibernateException e) {

		}
		return emailCorrespondente;
	}

	/**
	 * Traz os emails do cliente selecionado
	 * 
	 * @param idcliente
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EmailCorrespondente> lista(Integer idcliente) {
		List<EmailCorrespondente> busca = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = session
					.createQuery("from EmailCorrespondente  where correspondente.idcorrespondente=:idcorrespondente");
			this.query.setInteger("idcorrespondente", idcliente);
			this.query.setCacheable(true);
			busca = this.query.list();
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
		}
		return busca;
	}

}
