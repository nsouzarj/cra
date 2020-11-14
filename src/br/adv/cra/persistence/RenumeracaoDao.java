package br.adv.cra.persistence;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.adv.cra.entity.Renumeracao;
import br.adv.cra.entity.TipoSolicitacaoCorrespondente;

public class RenumeracaoDao implements Serializable {
	private static final long serialVersionUID = 1L;
	private Session session;
	private Query query;
	private Transaction transaction;

	public RenumeracaoDao() {
	}

	/**
	 * Salva a renumeracao
	 * 
	 * @param renumeracao
	 * @return
	 */
	public String Salvar(Renumeracao renumeracao) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.save(renumeracao);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return "";
	}

	/**
	 * Altera a renumeracao
	 * 
	 * @param renumeracao
	 * @return
	 */
	public String Alterar(Renumeracao renumeracao) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.update(renumeracao);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return "";
	}

	/**
	 * Exclui o valro de uma remuneracao
	 * */

	public String Excluir(Renumeracao renumeracao) throws Exception {
	
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.delete(renumeracao);
			this.transaction.commit();
			this.session.close();
		
		return "";

	}

	/**
	 * Altera a renumeracao
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Renumeracao> buscargeral(Integer codigo, boolean visualizatipo) {
		List<Renumeracao> busca = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			if (visualizatipo == false) {
				this.query = this.session
						.createQuery("from Renumeracao where tipoSolicitacaoCorrespondente.correspondente.idcorrespondente=:codigo and tipoSolicitacaoCorrespondente.tipoSolicitacao.visualizar <> false");
				this.query.setInteger("codigo", codigo);

			}
			if (visualizatipo == true) {
				this.query = this.session
						.createQuery("from Renumeracao where tipoSolicitacaoCorrespondente.correspondente.idcorrespondente=:codigo");
				this.query.setInteger("codigo", codigo);
			}

			busca = this.query.list();
			this.session.close();

		} catch (HibernateException e) {
		}
		return busca;
	}

	/**
	 * Traz uma renumeracao
	 * 
	 * @param codigo
	 * @return
	 */
	public Renumeracao trazrenumeracao(Integer idtiposolicitacao,
			Integer idcorrespondente, Integer idenvio) {
		Renumeracao renumeracao = null;

		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			if (idenvio != 0) {
				this.query = this.session
						.createQuery("from  Renumeracao where  tipoSolicitacaoCorrespondente.correspondente.idcorrespondente =:idcorrespondente  and  tipoSolicitacaoCorrespondente.tipoSolicitacao.idtiposolicitacao=:idtiposolicitacao and  tipoSolicitacaoCorrespondente.envio.idenvio=:idenvio");
				this.query.setInteger("idcorrespondente", idcorrespondente);
				this.query.setInteger("idtiposolicitacao", idtiposolicitacao);
				this.query.setInteger("idenvio", idenvio);
			} else if (idenvio == 0) {
				this.query = this.session
						.createQuery("from  Renumeracao where  tipoSolicitacaoCorrespondente.correspondente.idcorrespondente =:idcorrespondente  and  tipoSolicitacaoCorrespondente.tipoSolicitacao.idtiposolicitacao=:idtiposolicitacao");
				this.query.setInteger("idcorrespondente", idcorrespondente);
				this.query.setInteger("idtiposolicitacao", idtiposolicitacao);
			}
			this.transaction.commit();
			renumeracao = (Renumeracao) query.uniqueResult();
			this.session.close();
		} catch (HibernateException e) {

		}
		return renumeracao;
	}

	/**
	 * Renumeracao
	 * 
	 * @param idrenumeracao
	 * @return
	 */
	public Renumeracao trazunicarenumeracao(Integer idrenumeracao) {
		Renumeracao renumeracao = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session
					.createQuery("from  Renumeracao where  idrenumeracao=:idrenumeracao");
			this.query.setInteger("idrenumeracao", idrenumeracao);
			renumeracao = (Renumeracao) query.uniqueResult();

		} catch (HibernateException e) {

		}
		return renumeracao;
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		TipoSolicitacaoCorrespondente tipoSolicitacaoCorrespondente = new TipoSolicitacaoCorrespondente();
		// tipoSolicitacaoCorrespondente.getTipoSolicitacao().getIdtiposolicitacao();
		RenumeracaoDao teste = new RenumeracaoDao();
		Renumeracao renumeracao = new Renumeracao();
		renumeracao = teste.trazrenumeracao(4, 6, 2);
		System.out.print(renumeracao.toString());
	}
}
