package br.adv.cra.persistence;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.adv.cra.entity.Envio;
/**
 * Calsse de envio do tipo preposto sem preposto 
 * @author Nelson
 *
 */

public class EnvioDao implements Serializable {
	private static final long serialVersionUID = 1L;
	private Session session;
	private Query query;
	private Transaction transaction;

	@SuppressWarnings("unchecked")
	public List<Envio> buscargeral() {
		List<Envio> busca = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = session.createQuery("from Envio");
			busca = this.query.list();
			this.transaction.commit();
			this.session.close();

		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return busca;
	}

	/**
	 * 
	 * @param idenvio
	 * @return
	 */
	public Envio TrazUnico(Integer idenvio) {
		Envio envio = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session
					.createQuery("from Envio where idenvio=:idenvio");
			this.query.setInteger("idenvio", idenvio);
			this.transaction.commit();
			envio = (Envio) this.query.uniqueResult();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());

		}
		return envio;

	}

}
