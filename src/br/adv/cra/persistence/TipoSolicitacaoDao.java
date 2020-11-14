package br.adv.cra.persistence;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.adv.cra.entity.TipoSolicitacao;

public class TipoSolicitacaoDao implements Serializable {
	private static final long serialVersionUID = 1L;
	private Session session;
	private Query query;
	private Transaction transaction;

	/**
	 * Salva o tipo de solicitacao
	 * 
	 * @param tipoSolicitacao
	 * @return
	 */
	public String Salvar(TipoSolicitacao tipoSolicitacao) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.save(tipoSolicitacao);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return "";
	}

	/**
	 * Altera o tipo de solicitacao
	 * 
	 * @param tipoSolicitacao
	 * @return
	 */
	public String Alterar(TipoSolicitacao tipoSolicitacao) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.update(tipoSolicitacao);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return "";
	}

	/**
	 * Busca todos os tipo de solicitacoes
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TipoSolicitacao> buscargeral() {
		List<TipoSolicitacao> busca = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = session
					.createQuery("from TipoSolicitacao   order by tipo");
			busca = this.query.list();
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
		}
		return busca;
	}

	/**
	 * Busca
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<TipoSolicitacao> buscargeralinvisivel() {
		List<TipoSolicitacao> buscav = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = session
					.createQuery("from TipoSolicitacao S where S.visualizar='true' order by tipo");
			buscav = this.query.list();
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
		}
		return buscav;
	}

	/**
	 * Traz unica solicitacao
	 * 
	 * @param codigo
	 * @return
	 */
	public TipoSolicitacao trazunica(Integer codigo) {
		TipoSolicitacao tiposolicitacao = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session
					.createQuery("from TipoSolicitacao where idtiposolicitacao=:codigo");
			this.query.setInteger("codigo", codigo);
			this.transaction.commit();
			tiposolicitacao = (TipoSolicitacao) this.query.uniqueResult();
			this.session.close();
		} catch (HibernateException e) {

		}
		return tiposolicitacao;
	}

}
