package br.adv.cra.persistence;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.adv.cra.entity.ArquivoAnexoCPROSalvo;

public class SoliArqCproDaoSalvo {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	private Session session;
	private Query query;
	private Transaction transaction;
	private List<ArquivoAnexoCPROSalvo> recebe;
	public SoliArqCproDaoSalvo() {
	
	}
	
	/*
	 * Salva os dadoa do cpro e arquivos
	 */
	public void  Salvar(ArquivoAnexoCPROSalvo arquivoAnexoCPROSalvo){
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.save(arquivoAnexoCPROSalvo);
			this.transaction.commit();
			
			//Aqui documentei
			//this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
	/*Altera os dados do arquivo anexo do cppro as tabelas
	 * 
	 */
	public void  Alterar(ArquivoAnexoCPROSalvo arquivoAnexoCPROSalvo){
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.update(arquivoAnexoCPROSalvo);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
	
	
	/*
	 * Exclui os dado do cppro
	 */
	public String Excluir(Integer idarquivoanexocrposalvo) {
		ArquivoAnexoCPROSalvo arquivoAnexoCPROSalvo=null;
	
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session
					.createQuery("from ArquivoAnexoCPROSalvo where idArquivo=:idArquivo");
			this.query.setInteger("idArquivo", idarquivoanexocrposalvo);
			arquivoAnexoCPROSalvo = (ArquivoAnexoCPROSalvo) this.query.uniqueResult();
			this.session.delete(arquivoAnexoCPROSalvo);
			this.transaction.commit();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return "";
	}
	
	/*
	 * Exclui todos os arquivos do anexosalvo
	 */
	public String ExcluirPorPasta(String pastaprocesso) {
	    try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session.createSQLQuery("delete from anexocprosalvo where where pastadoprocesso like  "+"'%"+pastaprocesso.toUpperCase()+'%'+"'");
			this.transaction.commit();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return "";
	}
	
	/*
	 * Busca unico pleo aid do idcpprocesso salvo
	 */
	public ArquivoAnexoCPROSalvo BuscaUnico(Integer idarqcpprosalvo){
		ArquivoAnexoCPROSalvo arquivoAnexoCPROSalvo=null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session.createQuery("from ArquivoAnexoCPROSalvo where idarqcpprosalvo=:idarqcpprosalvo");
			this.query.setInteger("idarqcpprosalvo", idarqcpprosalvo);
			arquivoAnexoCPROSalvo = (ArquivoAnexoCPROSalvo) this.query.uniqueResult();
			this.transaction.commit();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return arquivoAnexoCPROSalvo;
	}
		
	public ArquivoAnexoCPROSalvo BuscaUnicoPorId(Integer idArquivo,Integer idsolicitacao){
		ArquivoAnexoCPROSalvo arquivoAnexoCPROSalvo=null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session.createQuery("from ArquivoAnexoCPROSalvo where idArquivo=:idArquivo and idsolicitacao=:idsolicitacao");
			this.query.setInteger("idArquivo", idArquivo);
			this.query.setInteger("idsolicitacao", idsolicitacao);
			arquivoAnexoCPROSalvo = (ArquivoAnexoCPROSalvo) this.query.uniqueResult();
			this.transaction.commit();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return arquivoAnexoCPROSalvo;
	}
		
	
	
   /**
    * Traz todos os arquivos da lista 
    * @param pastaprocesso
    * @param idsolicitacao
    * @return
    */
	@SuppressWarnings("unchecked")
	public List<ArquivoAnexoCPROSalvo> traztodos(String pastaprocesso, Integer idsolicitacao){
		List<ArquivoAnexoCPROSalvo> teste= null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = session.beginTransaction();
			String sqlmaluca="from ArquivoAnexoCPROSalvo where baixado=false and idsolicitacao="+idsolicitacao;
			//"from ArquivoAnexoCPROSalvo where baixado=false and PastaDoProcesso like  "+"'%"+pastaprocesso.toUpperCase().replace(" ","")+"%' and idsolicitacao="+idsolicitacao;
			this.query = session.createQuery(sqlmaluca);
			teste = this.query.list();
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return teste;
	
	}
	
	
	

}
