package br.adv.cra.persistence;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.adv.cra.entity.Uf;

public class UfDao implements Serializable {
	private static final long serialVersionUID = 1L;
	private Session session;
	private Query query;
	private Transaction transaction;

	public UfDao() {

	}

	@SuppressWarnings("unchecked")
	public List<Uf> listagem() {
		List<Uf> busca = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = session.createQuery("from Uf order by nome");
			busca = this.query.list();
			this.transaction.commit();
			this.session.close();

		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
			// TODO: handle exception
		}
		return busca;
	}

	/**
	 * Traz unica comarca
	 * 
	 * @param codigo
	 * @return
	 */
	public Uf trazuf(Integer codigo) {
		Uf uf = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session.createQuery("from Uf where iduf=:codigo");
			this.query.setInteger("codigo", codigo);
			this.transaction.commit();
			uf = (Uf) this.query.uniqueResult();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return uf;
	}

	public static void main(String[] args) {
		UfDao ufDao;
		ufDao = new UfDao();
		ufDao.listagem();
	}
}
