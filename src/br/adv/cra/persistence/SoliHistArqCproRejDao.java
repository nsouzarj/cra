package br.adv.cra.persistence;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.adv.cra.entity.Banco;
import br.adv.cra.entity.HistArqCpro;
import br.adv.cra.entity.HistArqCproRejeitado;
public class SoliHistArqCproRejDao {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	private Session session;
	private Query query;
	private Transaction transaction;
	public SoliHistArqCproRejDao() {
	
	}
	/**
	 * Salva
	 * @param histArqCproRejeitado
	 */
	public void  Salvar(HistArqCproRejeitado histArqCproRejeitado){
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.save(histArqCproRejeitado);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
	
	/**
	 * Excluir 
	 * @param idarquivoanexo
	 * @return
	 */
	
	public String Excluir(Integer idarquivoanexo) {
		HistArqCproRejeitado histArqCproRejeitado=null;
	
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session
					.createQuery("from HistArqCproRejeitado where solicitacaoAnexo.idarquivoanexo=:idarquivoanexo");
			this.query.setInteger("idarquivoanexo", idarquivoanexo);
			histArqCproRejeitado = (HistArqCproRejeitado) this.query.uniqueResult();
			this.session.delete(histArqCproRejeitado);
			this.transaction.commit();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return "";
	}
	
	/**
	 * Busca Unico
	 * @param idarquivocppro
	 * @return
	 */
	public HistArqCproRejeitado BuscaUnico(Integer idarquivocppro){
		HistArqCproRejeitado histArqCproRejeitado =null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session.createQuery("from HistArqCproRejeitado where idarquivocppro=:idarquivocppro");
			this.query.setInteger("idarquivocppro", idarquivocppro);
			histArqCproRejeitado = (HistArqCproRejeitado) this.query.uniqueResult();
			this.transaction.commit();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return histArqCproRejeitado;
	}
		
		
	

}
