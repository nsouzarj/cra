package br.adv.cra.persistence;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.adv.cra.entity.Banco;

public class BancoDao implements Serializable {
	private static final long serialVersionUID = 1L;
	private Session session;
	private Query query;
	private Transaction transaction;

	
	/**
	 * Salva o banco
	 * 
	 * @param banco
	 * @return
	 */
	public String Salvar(Banco banco) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.save(banco);
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
	public String Alterar(Banco banco) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.update(banco);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return "";
	}

	/**
	 * Exclui o banco da listagem
	 */
	public String Excluir(Integer idbanco) {
		Banco banco = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session
					.createQuery("from Banco where idbanco=:idbanco");
			this.query.setInteger("idbanco", idbanco);
			banco = (Banco) this.query.uniqueResult();
			this.session.delete(banco);
			this.transaction.commit();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return "";
	}

	/**
	 * Traz a lista de bancos
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Banco> buscargeral() {
		List<Banco> busca = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = session.createQuery("from Banco  order by banco");
			busca = this.query.list();
			this.transaction.commit();
			this.session.close();

		} catch (HibernateException e) {
		}
		return busca;
	}

	/**
	 * Traz um unico banco
	 * 
	 * @param codigo
	 * @return
	 */
	public Banco trazbanco(Integer codigo) {
		Banco banco = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session
					.createQuery("from Banco where idbanco=:codigo");
			this.query.setInteger("codigo", codigo);
			this.transaction.commit();
			banco = (Banco) this.query.uniqueResult();
			this.session.close();
		} catch (HibernateException e) {

		}
		return banco;
	}

	public Banco trazbancocorrespondente(Integer codigo) {
		Banco banco = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session
					.createQuery("from Banco where correspondente.idcorrespondente=:codigo");
			this.query.setInteger("codigo", codigo);
			this.transaction.commit();
			banco = (Banco) this.query.uniqueResult();
			this.session.close();
		} catch (HibernateException e) {

		}
		return banco;
	}

	@SuppressWarnings("unchecked")
	public List<Banco> bancocorrespondente(Integer idcliente) {
		List<Banco> busca = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = session
					.createQuery("from Banco where correspondente.idcorrespondente=:codigo");
			this.query.setInteger("codigo", idcliente);
			this.query.setCacheable(true);
			busca = this.query.list();
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
		}
		return busca;
	}

}
