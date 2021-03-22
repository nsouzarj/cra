package br.adv.cra.persistence;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.adv.cra.entity.Orgao;

public class OrgaoDao implements Serializable {
	private static final long serialVersionUID = 1L;
	private Session session;
	private Query query;
	private Transaction transaction;

	public OrgaoDao() {
	}

	/**
	 * Salava o orgao na tabela
	 * 
	 * @param orgao
	 * @return
	 */
	public Orgao Salvar(Orgao orgao) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = session.beginTransaction();
			this.session.save(orgao);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
			// TODO: handle exception
		}

		return orgao;
	}

	/**
	 * Altera o orgao
	 * 
	 * @param orgao
	 * @return
	 */
	public Orgao Alterar(Orgao orgao) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = session.beginTransaction();
			this.session.update(orgao);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
			// TODO: handle exception
		}

		return orgao;
	}

	/**
	 * Excluir o objeto da tabela
	 * 
	 * @param orgao
	 * @return
	 */
	public String Excluir(Orgao orgao) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = session.beginTransaction();
			this.session.delete(orgao);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
			// TODO: handle exception
		}

		return "";
	}

	/**
	 * Lista os orgao da tabela do banco de dados
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Orgao> listar() {
		List<Orgao> busca = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = session.beginTransaction();
			this.query = session
					.createQuery("from  Orgao f order by f.descricao");
			busca = this.query.list();
			this.transaction.commit();
			this.session.close();

		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return busca;
	}

	/**
	 * Traz um unico orgao
	 * 
	 * @param codigo
	 * @return
	 */
	public Orgao trazorgao(Integer codigo) {
		Orgao orgao = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = session.beginTransaction();
			this.query = this.session
					.createQuery("from Orgao where idorgao=:codigo");
			this.query.setInteger("codigo", codigo);
			this.transaction.commit();
			orgao = (Orgao) this.query.uniqueResult();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());

		}
		return orgao;
	}
}
