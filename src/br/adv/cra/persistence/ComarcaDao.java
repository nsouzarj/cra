package br.adv.cra.persistence;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.adv.cra.entity.Comarca;

public class ComarcaDao implements Serializable {
	private static final long serialVersionUID = 1L;
	private Session session;
	private Query query;
	private Transaction transaction;
	private List<Comarca> busca;

	public ComarcaDao() {
	}

	/**
	 * Salva a comarca
	 * 
	 * @param comarca
	 * @return
	 */
	public void Salvar(Comarca comarca) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.save(comarca);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	/**
	 * Altera a comarca
	 * 
	 * @param comarca
	 * @return
	 */

	public void Alterar(Comarca comarca) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.update(comarca);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	/**
	 * Busca a todas as comarcas por estado e por nome. 20/05/14
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Comarca> Buscacom(String nome, Integer iduf) {

		this.session = HibernateUtil.getSessionFactory().openSession();
		//this.transaction = this.session.beginTransaction();
		if (iduf == 0) {
			this.query = this.session
					.createQuery("from Comarca where idcomarca > 1 order by nome");
		} else if (iduf > 0) {
			this.query = this.session
					.createQuery("from Comarca where idcomarca > 1 and uf.iduf=:iduf order by nome");
			this.query.setInteger("iduf", iduf);
		}
		//this.query.setCacheable(true);
		busca = this.query.list();
		//this.transaction.commit();
		this.session.close();

		return busca;
	}

	/**
	 * Busca a todas as comarcas sem estado
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Comarca> buscargeral() {
		List<Comarca> busca = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			////this.transaction = this.session.beginTransaction();

			this.query = this.session
					.createQuery("from Comarca where idcomarca > 1 order by nome");

			//this.query.setCacheable(true);
			busca = this.query.list();
			//this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return busca;
	}

	/**
	 * Busca a comarca por estado
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Comarca> buscargeralporestado(Integer iduf) {
		List<Comarca> busca1 = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			///this.transaction = this.session.beginTransaction();
			if (iduf == 0) {
				this.query = this.session
						.createQuery("from Comarca where idcomarca > 1 order by nome");
			} else {
				this.query = this.session
						.createQuery("from Comarca where idcomarca > 1 and uf.iduf=:iduf order by nome");
				this.query.setInteger("iduf", iduf);
			}
			this.query.setCacheable(true);
			busca1 = this.query.list();
			//this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return busca1;
	}

	/**
	 * Traz uma unica comarca
	 * 
	 * @param codigo
	 * @return
	 */
	public Comarca trazcomarca(Integer codigo) {
		Comarca comarca = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			//this.transaction = this.session.beginTransaction();
			this.query = this.session
					.createQuery("from Comarca where idcomarca=:codigo");
			this.query.setInteger("codigo", codigo);
		    this.query.setCacheable(true);
			//this.transaction.commit();
			comarca = (Comarca) this.query.uniqueResult();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return comarca;
	}

	public Comarca trazcomarcaestado(Integer iduf) {
		Comarca comarca = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			//this.transaction = this.session.beginTransaction();
			this.query = this.session
					.createQuery("from Comarca where uf.iduf=:iduf");
			this.query.setInteger("iduf", iduf);
			this.query.setCacheable(true);
			//this.transaction.commit();
			comarca = (Comarca) this.query.uniqueResult();
			this.session.close();
		} catch (HibernateException e) {
		}
		return comarca;
	}

	public String excluir(Comarca comarca) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.delete(comarca);
			this.transaction.commit();
			this.session.flush();
			this.session.clear();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
			// TODO: handle exception
		}

		return "";
	}

	public static void main(String[] args) {
		ComarcaDao comarcaDao = new ComarcaDao();
		Comarca comarca = new Comarca();
		comarca = comarcaDao.trazcomarca(35);
		System.out.println(comarca);
		comarcaDao.excluir(comarca);
	}
}
