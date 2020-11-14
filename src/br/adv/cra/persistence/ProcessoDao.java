package br.adv.cra.persistence;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.adv.cra.entity.Correspondente;
import br.adv.cra.entity.Processo;
import br.adv.cra.entity.Renumeracao;
import br.adv.cra.entity.Solicitacao;
import br.adv.cra.entity.TipoSolicitacao;
import br.adv.cra.utilitarios.Converte;

public class ProcessoDao implements Serializable {
	private static final long serialVersionUID = 1L;
	private Session session;
	private Query query;
	private Transaction transaction;

	public ProcessoDao() {
	}

	/**
	 * Salva o processo
	 * 
	 * @param processo
	 * @return
	 */
	public String Salvar(Processo processo) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.saveOrUpdate(processo);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return "";
	}

	/**
	 * Altera o processo
	 * 
	 * @param processo
	 * @return
	 */
	public String Alterar(Processo processo) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.update(processo);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return "";
	}

	/**
	 * Lista os processos
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Processo> buscargeral() {
		List<Processo> busca = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session.createQuery("from Processo  order by parte");
			busca = this.query.list();
			this.transaction.commit();
			this.session.close();

		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());

		}
		return busca;
	}

	/**
	 * Traz um unico processo pela pasta
	 * 
	 * @param codigo
	 * @return
	 */
	public Processo trazprocesso(String codigo) {
		Processo processo = null;

		Converte con1 = new Converte();

		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			
			//this.query = this.session.createQuery("from Processo where replace(upper(localizacao),' ','')=:codigo");
			//this.query.setString("codigo", codigo.toUpperCase().trim().replace(" ","" ));
			this.query = this.session.createQuery("from Processo where localizacao=:codigo");
			this.query.setString("codigo", codigo);				
			this.transaction.commit();
			processo =  (Processo) this.query.uniqueResult();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return processo;
	}

	/**
	 * Traz um unico processo pela string
	 * 
	 * @param codigo
	 * @return
	 */
	public Processo trazprocessostring(String codigostring) {
		Processo processo = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session.createQuery("from Processo where replace(upper(localizacao),' ','')=:codigo");
			this.query.setString("codigo", codigostring.toUpperCase().trim().replace(" ", ""));
			this.transaction.commit();
			processo = (Processo) this.query.uniqueResult();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());

		}
		return processo;
	}

	
	
}
