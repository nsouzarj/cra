package br.adv.cra.persistence;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import br.adv.cra.entity.Historico;

public class HistoricoDao implements Serializable {
	private static final long serialVersionUID = 1L;
	private Session session;
	private Query query;
	public List<Historico> busca66;
	private Transaction transaction;

	public HistoricoDao() {
	}

	/**
	 * Salva o historico da solicitacao para ser auditada
	 * 
	 * @param historico
	 * @return
	 */
	public String Salvar(Historico historico) {
		try {

			// Date datahoje=new Date();
			// long javaTime =datahoje.getTime();
			// java.sql.Date sqlDate = new java.sql.Date(javaTime);
			// String padrao = "dd/MM/yyyy kk:mm:ss";
			// String data="";
			// SimpleDateFormat sdf = new SimpleDateFormat(padrao);
			// data=sdf.format(new Date());
			// ;
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			
			this.session.save(historico);
			
			this.session.flush();
			// this.session.save(historico);
			// Aqui fiz em sql puro para nao da problema com o Hibernate
			// String
			// sqltxt="insert into historico (idhistorico,datahistorico,idstatus,idusuario,idsolicitacao,idrenumeracao) values("+"nextval('idhistorico')"+","
			// +"to_timestamp("+"'"+
			// data+"'"+",'dd/MM/yyyy HH24:MI:SS')"+","+historico.getStatusSolicitacao().getIdstatus()+","
			// +historico.getUsuario().getIdusuario()+","+historico.getSolicitacao().getIdsolicitacao()+","+historico.getRenumeracao().getIdrenumeracao()+
			// ")";
			// this.session.createSQLQuery(sqltxt).executeUpdate();
			this.transaction.commit();
			this.session.close();

		} catch (HibernateException e) {
			System.out.println("Erro: " + "<HTML>" + e.getMessage() + "</HTML");
		}
		return "";

	}

	/**
	 * Altera o historico da solicitacao
	 * 
	 * @param historico
	 * @return
	 */
	public String Alterar(Historico historico) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.update(historico);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return "";
	}

	/**
	 * Traz a lista dos hisotrico
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Historico> buscargeral() {
		List<Historico> busca = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = session
					.createQuery("from Historico  where   order by datahistorico");
			busca = this.query.list();
			this.transaction.commit();
			this.session.close();

		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return busca;
	}

	/***
	 * Lista is históricos da solicitação
	 * 
	 * @param idsocilitacao
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Historico> buscartipos(Integer idsocilitacao, Integer idstatus) {
		List<Historico> busca = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = session.createQuery("from Historico where solicitacao.idsolicitacao=:idsocilitacao and statusSolicitacao.idstatus=:idstatus");
			this.query.setInteger("idsocilitacao", idsocilitacao);
			this.query.setInteger("idstatus", idstatus);
			busca = this.query.list();
			this.transaction.commit();
			this.session.close();

		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return busca;
	}
	
	
	
	/***
	 * Lista is históricos da solicitação por solcitacao
	 * 
	 * @param idsocilitacao
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Historico> buscarhistorico(Integer idsocilitacao) {
		List<Historico> busca = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = session.createQuery("from Historico where solicitacao.idsolicitacao=:idsocilitacao");
			this.query.setInteger("idsocilitacao", idsocilitacao);
			busca = this.query.list();
			this.transaction.commit();
			this.session.close();

		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return busca;
	}
	
	/**
	 * Excluir o historico da solicitacao
	 * @param historico
	 * @return
	 */
	public String Excluir(Historico historico) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.delete(historico);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return "";
	}
	
	
	

	/**
	 * TRaz único histórico
	 * 
	 * @param codigo
	 * @return
	 */
	public Historico trazhistorico(Integer codigo) {
		Historico historico = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session
					.createQuery("from Historico where idhistorico=:codigo");
			this.query.setInteger("codigo", codigo);
			this.transaction.commit();
			historico = (Historico) this.query.uniqueResult();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());

		}
		return historico;
	}

	/**
	 * ALERTA O USUARIO AS DILIGENCIAS PENDENTES NO SISTEMA, NO PRAZ DE 12 HORAS
	 * APARTIR DA DATA DA CRIACAO, E COM O STATUS '1 e 4 ' regra status 1-12
	 * horas regra status 2-24 horas
	 * 
	 * @param idstatus
	 * @param idusuario
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public List<Historico> alertaDiligenciaHistor(Integer idstatus,
			Integer idusuario) {
		try {
			Date data = new Date();
			Calendar cd = GregorianCalendar.getInstance();
			cd.add(5, -1); // menos 12 horas

			Date datahist = new Date();
			Calendar cdhis = GregorianCalendar.getInstance();
			cdhis.add(5, -2); // menos 24 horas

			data = cd.getTime();
			data = cdhis.getTime();

			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
            /**
			if (idusuario == 0) {
				query = session
						.createQuery("from Historico where datahistorico<:datahistorico"
								+ " and solicitacao.datasolictacao<:datasolictacao and solicitacao.renumeracao.tipoSolicitacaoCorrespondente.tipoSolicitacao.especie='DILIGENCIA' and (solicitacao.statusSolicitacao.idstatus=1 or solicitacao.statusSolicitacao.idstatus=4) order by solicitacao.statusSolicitacao.status desc");
				query.setDate("datasolictacao", data);
				query.setDate("datahistorico", datahist);
			} else if (idusuario != 0) {
				query = session
						.createQuery("from Historico where datahistorico<:datahistorico and usuario.idusuario=:idusuario"
								+ " and solicitacao.datasolictacao<:datasolictacao and solicitacao.renumeracao.tipoSolicitacaoCorrespondente.tipoSolicitacao.especie='DILIGENCIA' and (solicitacao.statusSolicitacao.idstatus=1 or solicitacao.statusSolicitacao.idstatus=4) order by solicitacao.statusSolicitacao.status desc");
				query.setInteger("idusuario", idusuario);
				query.setDate("datasolictacao", data);
				query.setDate("datahistorico", datahist);

			}
			
			*/
			query = session
					.createQuery("from Historico where datahistorico<:datahistorico  "
							+ " and (solicitacao.datasolictacao<:datasolictacao and solicitacao.datasolictacao >= '01/01/2019')  and solicitacao.renumeracao.tipoSolicitacaoCorrespondente.tipoSolicitacao.especie='DILIGENCIA' and (solicitacao.statusSolicitacao.idstatus=1 or solicitacao.statusSolicitacao.idstatus=4) order by solicitacao.datasolictacao desc");
			query.setDate("datasolictacao", data);
			query.setDate("datahistorico", datahist);
			busca66 = query.list();

			transaction.commit();
			session.close();
			return busca66;
		} catch (Exception e) {
			// TODO: handle exception
			busca66 = null;
			return busca66;
		}

	}

}