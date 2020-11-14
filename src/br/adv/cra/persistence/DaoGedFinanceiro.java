package br.adv.cra.persistence;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.adv.cra.entity.GedFinanceiro;



public class DaoGedFinanceiro {
	private Session session;
	private Query query;
	private Transaction transaction;
	
	 /**
	 * Salva o correspondente
	 * 
	 * @param correspondente
	 * @return
	 */
	public String Salvar(GedFinanceiro arq) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.saveOrUpdate(arq);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return "";
	}
	
	
	/**
	 * Lista o arquivos
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<GedFinanceiro> buscargeral(int idcorrespondente) {
		List<GedFinanceiro> busca = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			//this.transaction = this.session.beginTransaction();
			this.query = this.session
					.createQuery("from GedFinanceiro  where correspondente.idcorrespondente=:idcorrespondente order by data asc");
			this.query.setInteger("idcorrespondente", idcorrespondente);
			busca = this.query.list();
			//this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return busca;
	}
	
	
	/**
	 * Funcao para excluir o arquivo
	 * 
	 * @param idusuario
	 */
	public void excluir(int idgedfin) {
		GedFinanceiro arq = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session.createQuery("from GedFinanceiro where idgedfin=:idgedfin");
			this.query.setInteger("idgedfin", idgedfin);
			arq = (GedFinanceiro) this.query.uniqueResult();
			this.session.delete(arq);
			this.transaction.commit();
			this.session.close();
		} catch (Exception e) {
			// TODO: Excessao maluca
		}
	}

}
