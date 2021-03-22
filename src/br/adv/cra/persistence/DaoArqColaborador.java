package br.adv.cra.persistence;

import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import br.adv.cra.entity.ArquivoColaborador;


public class DaoArqColaborador implements Serializable {

	private static final long serialVersionUID = 1L;
	private Session session;
	private Query query;
	private Transaction transaction;
	
	/**
	 * Salva o correspondente
	 * 
	 * @param correspondente
	 * @return
	 */
	public String Salvar(ArquivoColaborador arq) {
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
	public List<ArquivoColaborador> buscargeral(Integer idcorrespondente) {
		List<ArquivoColaborador> busca = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();

			//this.transaction = this.session.beginTransaction();
			this.query = this.session
					.createQuery("from ArquivoColaborador  where corresp.idcorrespondente=:idcorrespondente order by datainclusao asc");
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
	public void excluir(int idgedarquivo) {
		ArquivoColaborador arq = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session.createQuery("from ArquivoColaborador where idgedarquivo=:idgedarquivo");
			this.query.setInteger("idgedarquivo", idgedarquivo);
			arq = (ArquivoColaborador) this.query.uniqueResult();
			this.session.delete(arq);
			this.transaction.commit();
			this.session.close();
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
			// TODO: Excessao maluca
		}
	}
	
	

}
