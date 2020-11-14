package br.adv.cra.persistence;
import java.awt.HeadlessException;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import br.adv.cra.entity.FormularioAudienciaNovo;


public class FormularioAudienciaNovoDao implements Serializable {
	private static final long serialVersionUID = 1L;
	private Session session;
	private Query query;
	private Transaction transaction;
	
	public FormularioAudienciaNovoDao() {
	}
	
	public FormularioAudienciaNovoDao(Session session, Query query,
			Transaction transaction) {
		this.session = session;
		this.query = query;
		this.transaction = transaction;
	}
	
	/**
	 * Slava ou altera o novo formulario
	 * @param formularioAudienciaNovo
	 * @return
	 */
	public FormularioAudienciaNovo SalvarAlterar(FormularioAudienciaNovo formularioAudienciaNovo){
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = session.beginTransaction();
			this.session.saveOrUpdate(formularioAudienciaNovo);
			this.transaction.commit();
			this.session.close();
		} catch (HeadlessException e) {
			
		}
		return formularioAudienciaNovo;
	}
	
	
	/**
	 * Exclui o formulario de audiencia novo
	 * @param formularioAudienciaNovo
	 */
	public void Excluir(FormularioAudienciaNovo formularioAudienciaNovo){		
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = session.beginTransaction();
			this.session.delete(formularioAudienciaNovo);
			this.transaction.commit();
			this.session.close();
		} catch (HeadlessException e) {
			
		}	
	}
	
	
	/**
	 * Lista os formularios novos
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<FormularioAudienciaNovo>listar(){
		List<FormularioAudienciaNovo> busca = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = session.beginTransaction();
			this.query = session
					.createQuery("from  FormularioAudienciaNovo f");
			busca = this.query.list();
			this.transaction.commit();
			this.session.close();

		} catch (HibernateException e) {
			
		}
		return busca;
	}
	
}
