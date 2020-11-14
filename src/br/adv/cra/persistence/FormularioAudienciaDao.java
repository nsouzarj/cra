package br.adv.cra.persistence;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import br.adv.cra.entity.FormularioAudiencia;

public class FormularioAudienciaDao {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	private Session session;
	private Query query;
	private Transaction transaction;
	private List<FormularioAudiencia> buscaformulario;

	public FormularioAudienciaDao() {
	}

	public FormularioAudienciaDao(Session session, Query query,
			Transaction transaction, List<FormularioAudiencia> buscaformulario) {
		this.session = session;
		this.query = query;
		this.transaction = transaction;
		this.buscaformulario = buscaformulario;
	}

	public List<FormularioAudiencia> getBuscaformulario() {
		return buscaformulario;
	}

	public void setBuscaformulario(List<FormularioAudiencia> buscaformulario) {
		this.buscaformulario = buscaformulario;
	}

	/**
	 * Salva o formalrio de audiencia na base
	 * 
	 * @param formularioAudiencia
	 */
	public void Salvar(FormularioAudiencia formularioAudiencia)
			throws HibernateException {

		this.session = HibernateUtil.getSessionFactory().openSession();
		this.transaction = this.session.beginTransaction();
		this.session.saveOrUpdate(formularioAudiencia);
		this.transaction.commit();
		this.session.close();

	}

	/**
	 * Altera o formulario de audiencia na base
	 */
	public void Alterar(FormularioAudiencia formularioAudiencia)
			throws HibernateException {

		this.session = HibernateUtil.getSessionFactory().openSession();
		this.transaction = this.session.beginTransaction();
		this.session.update(formularioAudiencia);
		this.transaction.commit();
		this.session.close();

	}

	/**
	 * Busca o formulario de audiancia
	 */
	@SuppressWarnings("unchecked")
	public List<FormularioAudiencia> BuscaUnico(Integer idformulario) {
		this.session = HibernateUtil.getSessionFactory().openSession();
		this.transaction = this.session.beginTransaction();
		this.query = this.session
				.createQuery("from FormularioAundiencia where idformulario=:idformulario");
		this.query.setInteger("idformulario", idformulario);
		this.query.setCacheable(true);
		buscaformulario = this.query.list();
		this.transaction.commit();
		this.session.close();
		return buscaformulario;
	}

	/**
	 * Traz unico formulario na busca
	 * 
	 * @param idformulario
	 * @return
	 */
	public FormularioAudiencia trazformulario(Integer idformulario) {
		FormularioAudiencia formularioAudiencia = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.query = this.session
					.createQuery("from FormularioAudiencia where idformulario=:idformulario");
			this.query.setInteger("idformulario", idformulario);

			formularioAudiencia = (FormularioAudiencia) this.query
					.uniqueResult();
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {

		}
		return formularioAudiencia;
	}

}
