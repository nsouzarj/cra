package br.adv.cra.persistence;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.adv.cra.entity.Correspondente;

public class CorrespondenteDao implements Serializable {
	private static final long serialVersionUID = 1L;
	private Session session;
	private Query query;
	private Transaction transaction;

	public CorrespondenteDao() {
	}

	/**
	 * Salva o correspondente
	 * 
	 * @param correspondente
	 * @return
	 */
	public String Salvar(Correspondente correspondente) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.saveOrUpdate(correspondente);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return "";

	}

	/**
	 * Altera o correspondente
	 * 
	 * @param correspondente
	 * @return
	 */
	public String Alterar(Correspondente correspondente) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.update(correspondente);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return "";

	}

	/**
	 * Lista o correspondente
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Correspondente> buscargeral() {
		List<Correspondente> busca = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();

			//this.transaction = this.session.beginTransaction();
			this.query = this.session
					.createQuery("from Correspondente  order by nome");
			busca = this.query.list();
			//this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());

		}
		return busca;
	}

	/**
	 * Traz um unico correspondente
	 * 
	 * @param codigo
	 * @return
	 */
	public Correspondente trazcorrespondente(Integer codigo) {
		Correspondente correspondente = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			//this.transaction = this.session.beginTransaction();
			this.query = this.session
					.createQuery("from Correspondente where idcorrespondente=:codigo");
			this.query.setInteger("codigo", codigo);
			//this.transaction.commit();
			correspondente = (Correspondente) this.query.uniqueResult();
			this.session.close();
		} catch (HibernateException e) {

		}
		return correspondente;
	}

	/**
	 * Faz um filtro de busca do correspondente
	 * 
	 * @param nome
	 * @param cpfcnpj
	 * @param oab
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Correspondente> buscafiltro(String nome, String cpfcnpj,
			String oab, boolean ativo) {
		List<Correspondente> busca = null;
		String teste = "from Correspondente where ativo = "+""+ativo+"";
		String complementa = "";
		if ((nome != "") && (cpfcnpj == "") && (oab == "")) {
			complementa = complementa + " and nome like " + "'" + nome + "%"
					+ "'";
		}
		if ((cpfcnpj != "") && (nome == "") && (oab == "")) {
			complementa = complementa + " and  cpfcnpj=" + cpfcnpj + " ";
		}
		if ((oab != "") && (cpfcnpj == "") && (nome == "")) {
			complementa = complementa + " and  oab=" + oab + " ";
		}
		
		

		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			teste = teste + complementa + " order by nome";
			//this.transaction = this.session.beginTransaction();
			this.query = this.session.createQuery(teste);
			busca = this.query.list();
			//this.transaction.commit();
			this.session.close();

		} catch (HibernateException e) {
		}
		return busca;
	}

}
