package br.adv.cra.persistence;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import br.adv.cra.entity.LogSistema;

public class LogSistemaDao implements Serializable {
	/**
	 * Classe Dao criada para gerenciar log do sistema
	 */
	private static final long serialVersionUID = 1L;
	private Session session;
	private Query query;
	private Transaction transaction;
	public LogSistemaDao() {
	}
	/**
	 * Salva o log do sistema
	 * @param log
	 * @return
	 */
    
	public String Salvar(LogSistema log){
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.save(log);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro ao salvar log : " + e.getMessage());
		}
		return "";
		
	}
	/**
	 * Traz o log do sistema
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	public List<LogSistema> buscargeral() {
		List<LogSistema> busca = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = session.createQuery("from LogSistema  order by nome");
			busca = this.query.list();
			this.transaction.commit();
			this.session.close();

		} catch (HibernateException e) {
		}
		return busca;
	}
	

}
