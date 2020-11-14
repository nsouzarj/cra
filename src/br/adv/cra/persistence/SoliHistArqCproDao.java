package br.adv.cra.persistence;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.adv.cra.entity.HistArqCpro;
public class SoliHistArqCproDao {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	private Session session;
	private Query query;
	private Transaction transaction;
	public SoliHistArqCproDao() {
	
	}
	
	public void  Salvar(HistArqCpro histArqCpro){
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.save(histArqCpro);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
	
	
	
	public String Excluir(Integer idarquivoanexocpro) {
		HistArqCpro histArqCpro=null;
	
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session
					.createQuery("from HistArqCpro where idarquivocppro=:idarquivocppro");
			this.query.setInteger("idarquivocppro", idarquivoanexocpro);
			histArqCpro = (HistArqCpro) this.query.uniqueResult();
			this.session.delete(histArqCpro);
			this.transaction.commit();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return "";
	}
	
	
	public HistArqCpro BuscaUnico(Integer idarquivocppro){
		HistArqCpro histArqCpro=null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session.createQuery("from HistArqCpro where idarquivocppro=:idarquivocppro");
			this.query.setInteger("idarquivocppro", idarquivocppro);
			histArqCpro = (HistArqCpro) this.query.uniqueResult();
			this.transaction.commit();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return histArqCpro;
	}
		
	/*
	 * Busca pelo id do arquivo
	 */
	public HistArqCpro BuscaUnicoPeloHist(Integer idarquivo){
		HistArqCpro histArqCpro=null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session.createQuery("from HistArqCpro where solicitacaoAnexo.idarquivoanexo=:idarquivoanexo");
			this.query.setInteger("idarquivoanexo", idarquivo);
			histArqCpro = (HistArqCpro) this.query.uniqueResult();
			this.transaction.commit();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return histArqCpro;
	}
		

}
