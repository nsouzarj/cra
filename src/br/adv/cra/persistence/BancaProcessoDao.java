package br.adv.cra.persistence;

import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import br.adv.cra.entity.BancaProcesso;

public class BancaProcessoDao implements Serializable {
	private static final long serialVersionUID = 1L;
	private Session session;
	private Query query;
	private Transaction transaction;

	public BancaProcessoDao() {
	}

	/**
	 * Salva os dados da banca
	 * 
	 * @param bancaProcesso
	 * @return
	 */
	public String Salvar(BancaProcesso bancaProcesso) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.save(bancaProcesso);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return "";
	}

	/**
	 * Altera os dados da banca
	 * 
	 * @param bancaProcesso
	 * @return
	 */
	public String Alterar(BancaProcesso bancaProcesso) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.update(bancaProcesso);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return "";
	}

	/**
	 * Traz a listagem da bancas
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BancaProcesso> buscargeral() {
		List<BancaProcesso> busca = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			//this.transaction = this.session.beginTransaction();
			this.query = session
					.createQuery("from BancaProcesso where ativa=true order by banca");
			busca = this.query.list();
			//this.transaction.commit();
			this.session.close();

		} catch (HibernateException e) {
			return null;
		}
		return busca;
	}

	/**
	 * Traz unica banca atraves do codifo
	 * 
	 * @param idbanca
	 * @return
	 */
	public BancaProcesso trazunico(Integer idbanca) {
		BancaProcesso bancaProcesso = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = session.beginTransaction();
			this.query = this.session
					.createQuery("from BancaProcesso where idbanca=:idbanca and ativa=true");
			this.query.setInteger("idbanca", idbanca);
			bancaProcesso = (BancaProcesso) this.query.uniqueResult();
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {

		}
		return bancaProcesso;
	}
	
	public static void main(String[] args) {
		BancaProcessoDao teste= new BancaProcessoDao();
		System.out.print(teste.buscargeral());
	}
}
