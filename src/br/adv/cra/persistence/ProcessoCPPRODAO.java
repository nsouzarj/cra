package br.adv.cra.persistence;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.adv.cra.entity.ProcessoCPPRO;

public class ProcessoCPPRODAO {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	private Session session;
	private Query query;
	private Transaction transaction;
	public List<ProcessoCPPRO> buscargeral;
	public List<ProcessoCPPRO> filtro;

	public ProcessoCPPRODAO() {
	}

	/**
	 * Salva o processo do cppro na base
	 * 
	 * @param processoCPPRO
	 * @return
	 */
	public String Salvar(ProcessoCPPRO processoCPPRO) throws Exception {

		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.saveOrUpdate(processoCPPRO);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return "";
	}

	/**
	 * Altera o processo na base somete
	 * 
	 * @param processoCPPRO
	 * @return
	 * @throws Exception
	 */
	public String Alterar(ProcessoCPPRO processoCPPRO) throws Exception {

		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.update(processoCPPRO);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return "";
	}

	/**
	 * Excluir da base o processo
	 * 
	 * @param processoCPPRO
	 * @return
	 */
	public String Excluir(ProcessoCPPRO processoCPPRO) throws Exception {

		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.delete(processoCPPRO);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return "";
	}

	/**
	 * Traz um unico processo do cpprpo
	 * 
	 * @param idprocessocppro
	 * @return
	 */
	public ProcessoCPPRO BuscarUnico(Integer idprocessocppro) throws Exception {

		ProcessoCPPRO processoCPPRO = null;

		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session
					.createQuery("from ProcessoCPPRO where idprocessocppro=:idprocessocppro");
			this.query.setInteger("idprocessocppro", idprocessocppro);
			this.transaction.commit();
			processoCPPRO = (ProcessoCPPRO) this.query.uniqueResult();
			this.session.close();
		} catch (HibernateException e) {

		}
		return processoCPPRO;
	}

	/**
	 * Traz a listagem geral
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ProcessoCPPRO> buscargeral() throws Exception {
		List<ProcessoCPPRO> processoCPPROs = null;
		this.session = HibernateUtil.getSessionFactory().openSession();
		this.transaction = session.beginTransaction();
		try {
			query = this.session.createQuery("from ProcessoCPPRO order by cliente");
			processoCPPROs = this.query.list();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return processoCPPROs;
	}
	
	
	
	

	/**
	 * Filtra por parametros
	 * 
	 * @param numprocesso
	 * @param cliente
	 * @param partecontraria
	 * @param acao
	 * @param localizador
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ProcessoCPPRO> Filtro(String numprocesso, String cliente,
			String partecontraria, String acao, String localizador) {
		String sqlbusca = "";
		List<ProcessoCPPRO> processoCPPROs = null;
		
		try {

			if (numprocesso != "") {
				sqlbusca = " where P.numprocesso=" +"'"+numprocesso+"'";
			}
			if (cliente != "") {
				sqlbusca = " where P.cliente like " + "'" + "%" + cliente + "%"
						+ "'";
			}
			if (partecontraria != "") {
				sqlbusca = " where P.partecontraria like " + "'" + "%"
						+ partecontraria + "%" + "'";
			}
			if (acao != "") {
				sqlbusca = " where P.acao like " + "'" + "%" + acao + "%" + "'";
			}
			if (localizador != "") {
				sqlbusca = " where P.localizador like " + "'" + "%"	+ localizador + "%" + "'";
			}
			if (sqlbusca != "") {
				this.session = HibernateUtil.getSessionFactory().openSession();
				this.transaction = this.session.beginTransaction();
				query = this.session.createQuery("from ProcessoCPPRO P "+sqlbusca+"  order by P.cliente");
				processoCPPROs = this.query.list();
				this.session.close();
			} else {
				processoCPPROs = null;
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return processoCPPROs;

	}

	/**
	 * Busca processo pelo numero do processo
	 * 
	 * @param numprocesso
	 * @return
	 * @throws Exception
	 */
	public ProcessoCPPRO BuscarProcesso(String numprocesso) throws Exception {

		ProcessoCPPRO processoCPPRO = null;

		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session
					.createQuery("from ProcessoCPPRO where numprocesso=:numprocesso");
			this.query.setString("numprocesso", numprocesso);
			this.transaction.commit();
			processoCPPRO = (ProcessoCPPRO) this.query.uniqueResult();
			this.session.close();
		} catch (HibernateException e) {

		}
		return processoCPPRO;
	}
	
	
	
	
	

}
