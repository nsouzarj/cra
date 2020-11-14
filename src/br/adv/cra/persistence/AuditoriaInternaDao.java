package br.adv.cra.persistence;

import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import br.adv.cra.entity.AuditoriaInterna;

/**
 * Classe que salva e altera a auditoria interna
 * @author nelson
 *
 */
public class AuditoriaInternaDao implements Serializable {
	private static final long serialVersionUID = 1L;
	private Session session;
	private Query query;
	private Transaction transaction;
	/**
	 * Lista as auditoria
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AuditoriaInterna> lista() {
		List<AuditoriaInterna> busca = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = session.createQuery("from AuditoriaInterna");
			busca = this.query.list();
			this.transaction.commit();
			this.session.close();

		} catch (HibernateException e) {
		}
		
		return busca;
	}
    /**
     * Salva e altera os dados da auditoria interna
     * @param auditoriaInterna
     * @return
     */
	public String SalvarAlterar(AuditoriaInterna auditoriaInterna) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.saveOrUpdate(auditoriaInterna);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}

		return "";

	}

	/**
	 * Busca unica auditoria interna
	 * @param idaudiinterna
	 * @return
	 */
	public AuditoriaInterna trazauditoria(Integer idaudiinterna) {
		AuditoriaInterna auditoriaInterna=null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.query = this.session
					.createQuery("from AuditoriaInterna where idaudiinterna=:idaudiinterna");
			this.query.setInteger("idaudiinterna", idaudiinterna);

			auditoriaInterna = (AuditoriaInterna) this.query.uniqueResult();
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {

		}
		return auditoriaInterna;
	}
}
