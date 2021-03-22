package br.adv.cra.persistence;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import br.adv.cra.entity.Historico;
import br.adv.cra.entity.Solicitacao;

public class SolicitacaoDao implements Serializable {
	private static final long serialVersionUID = 1L;
	private Session session;
	private Query query;
	private Transaction transaction;
	private List<Solicitacao> busca;
	public List<Solicitacao> busca2;
	public List<Historico> busca66;
	public List<Solicitacao> busca3;
	public List<Solicitacao> buscadoisdias;
	public List<Solicitacao> resumopgato;
	public boolean novabusca;

	public SolicitacaoDao() {
		busca = null;
	}

	public boolean isNovabusca() {
		return novabusca;
	}

	public void setNovabusca(boolean novabusca) {
		this.novabusca = novabusca;
	}

	/**
	 * Salva a solicitacao
	 * 
	 * @param solicitacao
	 * @return
	 */
	public Solicitacao Salvar(Solicitacao solicitacao) throws Exception {

		this.session = HibernateUtil.getSessionFactory().openSession();
		this.transaction = this.session.beginTransaction();
		this.session.saveOrUpdate(solicitacao);
		this.transaction.commit();
		this.session.close();
		return solicitacao;
	}

	/**
	 * Altera a solicitacao
	 * 
	 * @param solicitacao
	 * @return
	 */
	public String Alterar(Solicitacao solicitacao) throws Exception {

		this.session = HibernateUtil.getSessionFactory().openSession();
		this.transaction = this.session.beginTransaction();
		this.session.update(solicitacao);
		this.transaction.commit();
		this.session.close();

		return "";
	}

	/**
	 * metodo usado na tela alteranovasolicitacao manager no metodo
	 * Cadastranovasolialterada. 16/06/2014
	 * 
	 */

	public Solicitacao AlterarNovaSoli(Solicitacao solicitacao) throws Exception {

		this.session = HibernateUtil.getSessionFactory().openSession();
		this.transaction = this.session.beginTransaction();
		this.session.update(solicitacao);
		this.transaction.commit();
		this.session.close();

		return solicitacao;
	}

	@SuppressWarnings({ "unchecked", "null" })
	/**
	 * Lista as solicitacaoes e Busca as solicitações
	 * 
	 * @param numero
	 * @param numprocesso
	 * @param correspondente
	 * @param idusuario
	 * @param numerointegracao
	 * @param comarca
	 * @param orgao
	 * @param status
	 * @param tiposolicitacao
	 * @param datainicial
	 * @param datafinal
	 * @param autor
	 * @param reu
	 * @param tipoperido
	 * @param estado
	 * @return
	 */
	public List<Solicitacao> buscargeral(Integer numero, String numprocesso, Integer correspondente, Integer idusuario,
			String numerointegracao, Integer comarca, Integer orgao, Integer status, String tiposolicitacao,
			Date datainicial, Date datafinal, String autor, String reu, Integer tipoperido, Integer estado,
			Integer bancabusca, String proclocalizacao, String buscacolab, String lide) {
		String sqlbusca = " ";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		// Trata a formatacao aqui
		// String variavel = numprocesso;
		// String parte1 = variavel.replace(".", "");
		// String parte2 = parte1.replace("-", "");
		// String variavelfinal = parte2;

		// Verifica se esta null se estiver atribui valor 0;
		if (numero == null) {
			numero = 0;
		}
		if (numero != 0) {
			sqlbusca = sqlbusca + "where s.usuario.idusuario > 0  and   s.idsolicitacao=" + numero;
		} else if (numero == 0) {
			sqlbusca = sqlbusca + "where s.idsolicitacao > 0  and s.usuario.idusuario > 0";
		}

		if (bancabusca != 0) {
			sqlbusca = sqlbusca + " and  s.bancaProcesso.idbanca=" + bancabusca;
		}
		//else if (bancabusca == 0) {
         //			sqlbusca = sqlbusca + " and  s.bancaProcesso.idbanca is not null";
	  //	}

		if (numprocesso != "") {
			sqlbusca = sqlbusca + " and  s.processo.numeroprocesso like " + "'" + "%" + numprocesso + "%" + "'";
		}
		

		if (numerointegracao.length() > 0) {
			sqlbusca = sqlbusca + " and  s.processo.numerointegracao like " + "'" + "%" + numerointegracao + "%" + "'";
		}

		if (autor.length() > 0) {
			sqlbusca = sqlbusca + " and s.processo.parte like " + "'%" + autor + '%' + "'";
		}
		if (reu.length() > 0) {
			sqlbusca = sqlbusca + " and s.processo.adverso like " + "'%" + reu + '%' + "'";
		}
		//Busca por colabloradores via string

		if (buscacolab != "") {
			sqlbusca = sqlbusca +" and  s.renumeracao.tipoSolicitacaoCorrespondente.correspondente.nome like" + "'" + "%" + buscacolab + "%" + "'";;
		}

		if (comarca != 0) {
			sqlbusca = sqlbusca + " and  s.comarca.idcomarca=" + comarca;
		}

		if (orgao != 0) {
			sqlbusca = sqlbusca + " and s.processo.orgao.idorgao=" + orgao;
		}

		if (proclocalizacao.length() > 0) {
			sqlbusca = sqlbusca + " and s.processo.localizacao=" + "'" + proclocalizacao + "'";
		}

		if (status != 0) {
			if (status == 9) {
				sqlbusca = sqlbusca + " and s.statusSolicitacao.idstatus <> 8";
			} else {
				sqlbusca = sqlbusca + " and s.statusSolicitacao.idstatus=" + status;
			}
		}

		if (estado != 0) {
			sqlbusca = sqlbusca + " and  s.comarca.uf.iduf=" + estado;
		}

		if (idusuario != 0) {
			sqlbusca = sqlbusca + " and s.usuario.idusuario=" + idusuario;
		}

		if (correspondente != 0) {
			sqlbusca = sqlbusca + " and  s.renumeracao.tipoSolicitacaoCorrespondente.correspondente.idcorrespondente="
					+ correspondente;
		}

		if (tiposolicitacao.equals("A")) {
			sqlbusca = sqlbusca
					+ " and s.renumeracao.tipoSolicitacaoCorrespondente.tipoSolicitacao.especie='AUDIENCIA'";
		}

		if (tiposolicitacao.equals("D")) {
			sqlbusca = sqlbusca
					+ " and  s.renumeracao.tipoSolicitacaoCorrespondente.tipoSolicitacao.especie='DILIGENCIA'";
					
		}
		if (lide!="") {
			sqlbusca = sqlbusca+" and s.lide="+"'"+lide+"'";
		}

		if (datainicial != null || datafinal != null) {
			// Se vier nullo prioriza pela data da elaboracao
			if (tipoperido == null) {
				tipoperido = 1;
			}
			if (tipoperido.equals(1)) {
				sqlbusca = sqlbusca + " and  to_date(to_char(s.datasolictacao,'DD/MM/YYYY'),'DD/MM/YYYY') >=" + "'"
						+ sdf.format(datainicial) + "'"
						+ "   and to_date(to_char(s.datasolictacao,'DD/MM/YYYY'),'DD/MM/YYYY') <=" + "'"
						+ sdf.format(datafinal) + "'";
			} else if (tipoperido.equals(2)) {
				sqlbusca = sqlbusca + " and  to_date(to_char(s.dataprazo,'DD/MM/YYYY'),'DD/MM/YYYY') >= " + "'"
						+ sdf.format(datainicial) + "'"
						+ "   and to_date(to_char(s.dataprazo,'DD/MM/YYYY'),'DD/MM/YYYY') <=" + "'"
						+ sdf.format(datafinal) + "'";

			} else {
				sqlbusca = sqlbusca + " and  to_date(to_char(s.dataprazo,'DD/MM/YYYY'),'DD/MM/YYYY') >=" + "'"
						+ sdf.format(datainicial) + "'" + "   and s.dataprazo <=" + "'" + sdf.format(datafinal) + "'";

			}

		}

		// Busca quando datas são iguais

		/// System.out.println(sqlbusca);
		if (novabusca == true) {
			try {
				busca = null;
				this.session = HibernateUtil.getSessionFactory().openSession();
				this.transaction = this.session.beginTransaction();
				if (sqlbusca != "") {
					this.query = this.session
							.createQuery("from Solicitacao s " + sqlbusca + " order by s.dataprazo asc");
					busca = this.query.list();
					// this.transaction.commit();
					this.session.close();
					novabusca = false;
				}

			} catch (HibernateException e) {
				System.out.println("Erro: " + e.getMessage());
			}
		}
		return busca;
	}

	/**
	 * Traz uma unica solicitacao
	 * 
	 * @param codigo
	 * @return
	 */
	public Solicitacao trazrsolicitacao(Integer idsolicitacao) {
		Solicitacao solicitacao = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session.createQuery("from Solicitacao where idsolicitacao=:idsolicitacao");
			this.query.setInteger("idsolicitacao", idsolicitacao);
			this.transaction.commit();
			solicitacao = (Solicitacao) this.query.uniqueResult();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());

		}
		return solicitacao;
	}

	@SuppressWarnings("unused")
	public boolean verifica(Integer idsolicitacao) {
		Solicitacao solicitacao1 = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session.createQuery("from Solicitacao where idsolicitacao=:idsolicitacao");
			this.query.setInteger("idsolicitacao", idsolicitacao);
			this.transaction.commit();
			solicitacao1 = (Solicitacao) this.query.uniqueResult();
			this.session.close();
		} catch (HibernateException e) {
			return false;

		}
		return true;
	}

	/**
	 * Cancela a transação no banco de dados
	 */
	public void Cancelar() {
		this.transaction.rollback();

	}

	/**
	 * METODO SENDO USADO APENAS PARA ENVIO DO EMAIL E OUTRAS COISAS DA
	 * DILIGENCIA... ATUAL
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<Solicitacao> alertaDiligencia(Integer idstatus, Integer idusuario) {

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

		if (idusuario == 0) {
			query = session.createQuery("from Historico where datahistorico<:datahistorico"
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
		busca66 = query.list();

		transaction.commit();
		session.close();
		return busca2;
	}

	/***
	 * METODO PARA ENVIAR SOLICITACOES DE AUDIENCIAS PENDENTES NO PRAZO DE 12
	 * HORAS,.status aguardando confirmacao, mudando o status para em producao, dar
	 * o prazo de 24horas.
	 * 
	 */

	@SuppressWarnings("unchecked")
	public List<Solicitacao> alertaAudiencia(Integer idstatus, Integer idusuario) {

		Date data = new Date();
		Calendar cd = GregorianCalendar.getInstance();
		cd.add(5, -1); // menos 12 horas

		Date datasoli = new Date();
		Calendar cdsoli = GregorianCalendar.getInstance();
		cdsoli.add(5, -2); // menos 24 horas

		data = cd.getTime();
		datasoli = cdsoli.getTime();
		session = HibernateUtil.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		try {
			/**
			 * if (idusuario == 0) { query = session.createQuery( "from Solicitacao where
			 * dataprazo<:dataprazo and datasolictacao<:datasolictacao and
			 * renumeracao.tipoSolicitacaoCorrespondente.tipoSolicitacao.especie='AUDIENCIA'
			 * and (statusSolicitacao.idstatus=1 or statusSolicitacao.idstatus=4) order by
			 * statusSolicitacao.idstatus desc"); query.setDate("dataprazo", data);
			 * query.setDate("datasolictacao", datasoli); } else if (idusuario != 0) { query
			 * = session.createQuery( "from Solicitacao where idusuario=:idusuario and
			 * dataprazo<:dataprazo and datasolictacao<:datasolictacao and
			 * renumeracao.tipoSolicitacaoCorrespondente.tipoSolicitacao.especie='AUDIENCIA'
			 * and (statusSolicitacao.idstatus=1 or statusSolicitacao.idstatus=4) order by
			 * statusSolicitacao.idstatus desc"); query.setInteger("idusuario", idusuario);
			 * query.setDate("dataprazo", data); query.setDate("datasolictacao", datasoli);
			 * }
			 */

			query = session.createQuery(
					"from Solicitacao where dataprazo<:dataprazo and renumeracao.tipoSolicitacaoCorrespondente.tipoSolicitacao.especie='AUDIENCIA'  and (datasolictacao<:datasolictacao and  datasolictacao >='01/01/2019') and (statusSolicitacao.idstatus=1 or statusSolicitacao.idstatus=4) order by datasolictacao desc");

			query.setDate("dataprazo", data);
			query.setDate("datasolictacao", datasoli);

			// query.setCacheable(true);
			busca3 = query.list();
			transaction.commit();
			session.close();

			return busca3;

		} catch (Exception e) { 
	   	    System.out.println("Erro: " + e.getMessage());
			idusuario = 0;
			busca3 = null;
			return busca3;
			// TODO: handle exception
		}

	}

	public static void main(String[] args) {
		String teste = null;
		String teste2 = null;
		String final2 = null;

		teste = "002008.99.80.09-1000";
		teste2 = teste.replace(".", "");
		final2 = teste2.replace("-", "");
		System.out.print(final2);

	}

	/**
	 * METODO QUE CHECARA SE O PROCESSO JÁ EXISTE NA BASE A SOLICITACAO COM O NUMERO
	 * DAQUELE PROCESSO. SE A DATADASOLICITACAO DESSE PROCESSO PASSAR DE 48-HORAS
	 * PODERÁ SER FEITA UMA NOVA SOLICITACAO --- TIPO DE SOLICITACAO: DILIGENCIA
	 * PARCIAL E INTEGRAL... TELA NOVASOLICITACAO "DIALOG"--------07/072014
	 * 
	 */

	@SuppressWarnings("unchecked")
	public List<Solicitacao> alertaDoisDiasNovaSoli(String localizacao) {
       
		try {
			
			Date dataaviso = new Date();
			Date compara1 = new Date();
			Calendar cdaviso = GregorianCalendar.getInstance();

			cdaviso.add(5, -2); // menos 48 horas

			dataaviso = cdaviso.getTime();
			if (compara1 != dataaviso) {

			}

			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();

			query = session.createQuery(
					"from Solicitacao where processo.localizacao=:localizacao and datasolictacao>:datasoli  and  "
							+ "(renumeracao.tipoSolicitacaoCorrespondente.tipoSolicitacao.idtiposolicitacao=13 or renumeracao.tipoSolicitacaoCorrespondente.tipoSolicitacao.idtiposolicitacao=14) ");
			query.setDate("datasoli", dataaviso);
			query.setString("localizacao", localizacao);
			// query.setCacheable(true);
			buscadoisdias = query.list();
			transaction.commit();
			session.close();
			return buscadoisdias;

		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
			// TODO: handle exception
		}
		return buscadoisdias;
	
	}

	public void setResumopgato(List<Solicitacao> resumopgato) {
		this.resumopgato = resumopgato;
	}

}
