package br.adv.cra.persistence;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.adv.cra.entity.SoliArquivo;
import br.adv.cra.entity.SolicitacaoAnexo;

public class SoliArquivoDao implements Serializable {
	private static final long serialVersionUID = 1L;
	private Session session;
	private Query query;
	private Transaction transaction;

	public SoliArquivoDao() {
	}

	/**
	 * Salva o arquivo de solicitacao
	 * 
	 * @param soliArquivo
	 */
	public void Salvar(SoliArquivo soliArquivo) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.save(soliArquivo);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}

	}

	/**
	 * Salva o arquivona base de dados
	 * 
	 * @param solicitacaoAnexo
	 */
	public void SalvaArquivo(SolicitacaoAnexo solicitacaoAnexo) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.save(solicitacaoAnexo);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}

	}

	/**
	 * Altera o arquivo da solicitacao
	 * 
	 * @param soliArquivo
	 */
	public void Alterar(SoliArquivo soliArquivo) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.save(soliArquivo);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}

	}

	/**
	 * Exclui o arquivo da solicitacao
	 * 
	 * @param soliArquivo
	 */
	public void Excluir(SoliArquivo soliArquivo) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.delete(soliArquivo);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	/**
	 * Exclui um unico arquivo
	 * 
	 * @param idarquivo
	 */
	public void ExcluirViaCodigo(Integer idarquivoanexo) {
		try {
			String sql = "from SoliArquivo  S where S.solicitacaoPossuiArquivo.solicitacaoAnexo.idarquivoanexo=:idarquivoanexo";
			SoliArquivo soliArquivo = new SoliArquivo();
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session.createQuery(sql);
			this.query.setInteger("idarquivoanexo", idarquivoanexo);
			soliArquivo = (SoliArquivo) this.query.uniqueResult();
			this.session.delete(soliArquivo);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	/**
	 * Lista os arquivo da solicitacao
	 * 
	 * @param codigo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SoliArquivo> buscararquivo(Integer idsolicitacao,
			String operacao) throws Exception {
		List<SoliArquivo> possuiarquivo = null;
		String sql = "";
		this.session = HibernateUtil.getSessionFactory().openSession();
		this.transaction = this.session.beginTransaction();
		if (operacao.equals("")) {
			sql = "from SoliArquivo  S where S.solicitacaoPossuiArquivo.solicitacao.idsolicitacao=:idsolicitacao";
			this.query = this.session.createQuery(sql);
			this.query.setInteger("idsolicitacao", idsolicitacao);
		} else {
			sql = "from SoliArquivo  S where S.solicitacaoPossuiArquivo.solicitacao.idsolicitacao=:idsolicitacao and S.solicitacaoPossuiArquivo.solicitacaoAnexo.operacao=:operacao";
			this.query = this.session.createQuery(sql);
			this.query.setInteger("idsolicitacao", idsolicitacao);
			this.query.setString("operacao", operacao);
		}
		possuiarquivo = this.query.list();
		this.session.close();

		return possuiarquivo;
	}

	public static void main(String[] args) {
		SoliArquivoDao teste = new SoliArquivoDao();
		try {
			teste.buscararquivo(6, "RECEBIDO");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
