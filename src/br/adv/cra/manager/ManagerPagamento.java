package br.adv.cra.manager;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.adv.cra.entity.Correspondente;
import br.adv.cra.entity.ReciboPagamento;
import br.adv.cra.entity.Solicitacao;
import br.adv.cra.entity.Usuario;
import br.adv.cra.persistence.CorrespondenteDao;
import br.adv.cra.persistence.Dao;
import br.adv.cra.persistence.ReciboPagamentoDao;
import br.adv.cra.persistence.SolicitacaoDao;
import br.adv.cra.persistence.UsuarioDao;
import br.adv.cra.utilitarios.DownloadArquivo;
import br.adv.cra.utilitarios.Relatorios;

@ManagedBean(name = "pagamento", eager = true)
@SessionScoped
public class ManagerPagamento implements Serializable {

	private FacesContext context;
	private HttpSession session;
	private static final long serialVersionUID = 1L;
	private String nomecorrespondente;
	private List<Correspondente> listacorrespondente;
	private List<Solicitacao> solicitacoes;
	private SolicitacaoDao solicitacaoDao;
	private SimpleDateFormat dateFormat;
	private CorrespondenteDao correspondenteDao;
	private Integer idcorrespondente;
	private Integer idusuario;
	private String dtbuscainicial; // datas de buscas
	private String dtbuscafinal;
	private String usuariologado;
	private TimeZone timeZone;
	private Date bdatainicial;
	private Date bdatafinal;
	private Integer bcorrespondente;
	private Usuario usuario;
	private ReciboPagamento reciboPagamento;
	private UsuarioDao usuarioDao;
	private String tiposolicitacao;
	private ReciboPagamentoDao reciboPagamentoDao;
	private String textopagamento;
	private Integer formatorel;
	private boolean listaresumopgato;
	private Integer qualrel;
	

	public ManagerPagamento() {
		bcorrespondente = 0;
		solicitacaoDao = new SolicitacaoDao();
		correspondenteDao = new CorrespondenteDao();
		usuarioDao = new UsuarioDao();
		usuario = new Usuario();
		textopagamento = "";
		dtbuscafinal = "";
		listaresumopgato=false;
		dtbuscainicial = "";
		dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Para formatacao de
		// data

		context = FacesContext.getCurrentInstance();
		session = (HttpSession) context.getExternalContext().getSession(true);
		idusuario = (Integer) session.getAttribute("IdUsuario");
		try {
			usuario = usuarioDao.trazusuario(idusuario);
		} catch (Exception e) {
			
		}
		
		FacesContext context = FacesContext.getCurrentInstance();
		usuariologado = (String) context.getExternalContext().getSessionMap()
				.remove("idUsuario");
		
		
		
	}

	public ManagerPagamento(FacesContext context, HttpSession session,
			String nomecorrespondente,
			List<Correspondente> listacorrespondente,
			List<Solicitacao> solicitacoes, SolicitacaoDao solicitacaoDao,
			Solicitacao solicitacao, SimpleDateFormat dateFormat,
			CorrespondenteDao correspondenteDao, Integer idcorrespondente,
			Integer idusuario, String dtbuscainicial, String dtbuscafinal,
			String usuariologado, TimeZone timeZone, Date bdatainicial,
			Date bdatafinal, Integer bcorrespondente, Usuario usuario,
			ReciboPagamento reciboPagamento, UsuarioDao usuarioDao,
			String tiposolicitacao, ReciboPagamentoDao reciboPagamentoDao,
			String textopagamento, boolean listaresumopgato, Integer formatorel,Integer qualrel) {
		this.context = context;
		this.session = session;
		this.nomecorrespondente = nomecorrespondente;
		this.listacorrespondente = listacorrespondente;
		this.solicitacoes = solicitacoes;
		this.solicitacaoDao = solicitacaoDao;
		this.dateFormat = dateFormat;
		this.correspondenteDao = correspondenteDao;
		this.idcorrespondente = idcorrespondente;
		this.idusuario = idusuario;
		this.dtbuscainicial = dtbuscainicial;
		this.dtbuscafinal = dtbuscafinal;
		this.usuariologado = usuariologado;
		this.timeZone = timeZone;
		this.bdatainicial = bdatainicial;
		this.bdatafinal = bdatafinal;
		this.bcorrespondente = bcorrespondente;
		this.usuario = usuario;
		this.reciboPagamento = reciboPagamento;
		this.usuarioDao = usuarioDao;
		this.tiposolicitacao = tiposolicitacao;
		this.reciboPagamentoDao = reciboPagamentoDao;
		this.textopagamento = textopagamento;
		this.listaresumopgato = listaresumopgato;
		this.formatorel=formatorel;
		this.qualrel=qualrel;
	}
    
	
	public Integer getQualrel() {
		return qualrel;
	}

	public void setQualrel(Integer qualrel) {
		this.qualrel = qualrel;
	}

	public Integer getFormatorel() {
		return formatorel;
	}

	public void setFormatorel(Integer formatorel) {
		this.formatorel = formatorel;
	}

	public boolean isListaresumopgato() {
		return listaresumopgato;
	}

	public void setListaresumopgato(boolean listaresumopgato) {
		this.listaresumopgato = listaresumopgato;
	}

	public String getTextopagamento() {
		return textopagamento;
	}

	public void setTextopagamento(String textopagamento) {
		this.textopagamento = textopagamento;
	}

	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public ReciboPagamento getReciboPagamento() {
		return reciboPagamento;
	}

	public void setReciboPagamento(ReciboPagamento reciboPagamento) {
		this.reciboPagamento = reciboPagamento;
	}

	public CorrespondenteDao getCorrespondenteDao() {
		return correspondenteDao;
	}

	public void setCorrespondenteDao(CorrespondenteDao correspondenteDao) {
		this.correspondenteDao = correspondenteDao;
	}

	public ReciboPagamentoDao getReciboPagamentoDao() {
		return reciboPagamentoDao;
	}

	public void setReciboPagamentoDao(ReciboPagamentoDao reciboPagamentoDao) {
		this.reciboPagamentoDao = reciboPagamentoDao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getNomecorrespondente() {
		return nomecorrespondente;
	}

	public void BuscaSolicitacao() {
		getSolicitacoes();
	}

	public Integer getBcorrespondente() {
		return bcorrespondente;
	}

	public void setBcorrespondente(Integer bcorrespondente) {
		this.bcorrespondente = bcorrespondente;
	}

	public String getDtbuscainicial() {
		return dtbuscainicial;
	}

	public TimeZone getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}

	public void setDtbuscainicial(String dtbuscainicial) {
		this.dtbuscainicial = dtbuscainicial;
	}

	public String getDtbuscafinal() {
		return dtbuscafinal;
	}

	public void setDtbuscafinal(String dtbuscafinal) {
		this.dtbuscafinal = dtbuscafinal;
	}

	public String getUsuariologado() {
		return usuariologado;
	}

	public void setUsuariologado(String usuariologado) {
		this.usuariologado = usuariologado;
	}

	public void setNomecorrespondente(String nomecorrespondente) {
		this.nomecorrespondente = nomecorrespondente;
	}

	public List<Correspondente> getListacorrespondente() {
		listacorrespondente = correspondenteDao.buscargeral();
		return listacorrespondente;
	}

	public void setListacorrespondente(List<Correspondente> listacorrespondente) {
		this.listacorrespondente = listacorrespondente;
	}

	public List<Solicitacao> getSolicitacoes() {
		solicitacoes = solicitacaoDao.buscargeral(0, "", bcorrespondente, 0,
				"", 0, 0, 7, "T", bdatainicial, bdatafinal, "", "", 2, 0, 0,"","","");
		return solicitacoes;

	}

	public void setSolicitacoes(List<Solicitacao> solicitacoes) {
		this.solicitacoes = solicitacoes;
	}

	public Date getBdatainicial() {
		return bdatainicial;
	}

	public void setBdatainicial(Date bdatainicial) {
		this.bdatainicial = bdatainicial;
	}

	public Date getBdatafinal() {
		return bdatafinal;
	}

	public void setBdatafinal(Date bdatafinal) {
		this.bdatafinal = bdatafinal;
	}

	public Integer getIdcorrespondente() {
		return idcorrespondente;
	}

	public void setIdcorrespondente(Integer idcorrespondente) {
		this.idcorrespondente = idcorrespondente;
	}

	public Integer getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(Integer idusuario) {
		this.idusuario = idusuario;
	}

	public SolicitacaoDao getSolicitacaoDao() {
		return solicitacaoDao;
	}

	public String getTiposolicitacao() {
		return tiposolicitacao;
	}

	public void setTiposolicitacao(String tiposolicitacao) {
		this.tiposolicitacao = tiposolicitacao;
	}

	public void setSolicitacaoDao(SolicitacaoDao solicitacaoDao) {
		this.solicitacaoDao = solicitacaoDao;
	}

	/*
	 * Realiza a baixa do pagamento
	 */
	public void BaixaPagamento() {
		reciboPagamento = new ReciboPagamento();
		reciboPagamentoDao = new ReciboPagamentoDao();
		// reciboPagamento.setDatafechamento();
		reciboPagamento.setAnotacao(textopagamento);
		reciboPagamento.setUsuario(usuario);
		reciboPagamento.setDatafechamento(new Date());
		reciboPagamentoDao.Salvar(reciboPagamento);

		for (Solicitacao solicitacao : solicitacoes) {
			solicitacao.setPago("SIM");
			solicitacao.setReciboPagamento(reciboPagamento);

			try {
				solicitacaoDao.Alterar(solicitacao);
			} catch (Exception e) {
				FacesContext cont1 = FacesContext.getCurrentInstance();
				cont1.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Erro ao salvar entrar em contato com o suporte.", ""));
			}
		}
	}

	@SuppressWarnings({ "unused", "unchecked" })
	public String ImprimirPagamentoPdf() {
		if (bcorrespondente.equals(0) && listaresumopgato==false) {
			FacesContext cont1 = FacesContext.getCurrentInstance();
			cont1.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Selecione um correspondente para imprimir o relatório de pagamento.",
							""));
			return "/financeiro/financeiro.jsf";
		}

		Relatorios relatorios = new Relatorios();
		@SuppressWarnings("rawtypes")
		HashMap param = new HashMap();
		// param.clear();
		String val1 = "";
		String val2 = "";
		String tipo_soli = "";

		try {
			val1 = dateFormat.format(bdatainicial);
			val2 = dateFormat.format(bdatafinal);

			if (bdatafinal.equals(null) | bdatafinal.equals(null)) {
				FacesContext cont1 = FacesContext.getCurrentInstance();
				cont1.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Selecione um período para imprimir o relatório.", ""));
				return "/financeiro/financeiro.jsf";

			}
		} catch (Exception e) {
			FacesContext cont1 = FacesContext.getCurrentInstance();
			cont1.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Selecione um período para imprimir o relatório.", ""));
			return "/financeiro/financeiro.jsf";
		}

		param.put("ID_COLABORADOR", bcorrespondente);
		param.put("NOME", "");
		param.put("DATA_INICIAL", val1);
		param.put("DATA_FINAL", val2);

		Dao d = new Dao();
		try {
			d.open();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Connection connection = d.getConexao();

		String arq = null;
		try {

			if(listaresumopgato==false){
			
			arq = relatorios.imprimir(param, "pagamento", connection,
					"/WEB-INF/relatorios/pagamento.jasper",
					"/WEB-INF/relatorios/pagamento.jrxml", ".pdf");
			DownloadArquivo abrir = new DownloadArquivo();
			abrir.Abrir("c:/relaweb/" + arq, ".pdf", true);
			
			}else if (listaresumopgato==true){
				arq = relatorios.imprimir(param, "pagamento", connection,
						"/WEB-INF/relatorios/ResumoPagto.jasper",
						"/WEB-INF/relatorios/ResumoPagto.jrxml", ".pdf");
				DownloadArquivo abrir = new DownloadArquivo();
				abrir.Abrir("c:/relaweb/" + arq, ".pdf", true);
				
			}
			

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}
	
	
	
	//
	
	@SuppressWarnings({ "unused", "unchecked" })
	public String ImprimeResumoColab() {
		
		Relatorios relatorios = new Relatorios();
		@SuppressWarnings("rawtypes")
		HashMap param = new HashMap();
		// param.clear();
		String val1 = "";
		String val2 = "";
		String tipoarqivo="";
		String tipo_soli = "";

		try {
			val1 = dateFormat.format(bdatainicial);
			val2 = dateFormat.format(bdatafinal);

			if (bdatafinal.equals(null) | bdatafinal.equals(null)) {
				FacesContext cont1 = FacesContext.getCurrentInstance();
				cont1.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Selecione um período para imprimir o relatório de resumo geral.", ""));
				return "/financeiro/democolaborador.jsf";

			}
		} catch (Exception e) {
			FacesContext cont1 = FacesContext.getCurrentInstance();
			cont1.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Selecione um período para imprimir o relatório demostrativo.", ""));
			return "/financeiro/democolaborador.jsf";
		}

		
		param.put("datainicial", val1);
		param.put("datafinal", val2);

		Dao d = new Dao();
		try {
			d.open();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Connection connection = d.getConexao();

		String arq = null;
		try {
             if (formatorel==0){
            	 tipoarqivo=".pdf";
             }else if(formatorel==1){
            	 tipoarqivo=".xls";
             }
             FacesContext cont1 = FacesContext.getCurrentInstance();
 			cont1.addMessage(null, new FacesMessage(
 					FacesMessage.SEVERITY_INFO,
 					"Gerando o demostrativo geral.", ""));
 			
 			
 			if(qualrel==0){
			
			arq = relatorios.imprimir(param, "demostrativogeral", connection,
					"/WEB-INF/relatorios/listacolabvalor.jasper",
					"/WEB-INF/relatorios/listacolabvalor.jrxml", tipoarqivo);
			DownloadArquivo abrir = new DownloadArquivo();
			abrir.Abrir("c:/relaweb/" + arq, tipoarqivo, true);
 			}else if(qualrel==1){
 				arq = relatorios.imprimir(param, "canceladoreprovado", connection,
 						"/WEB-INF/relatorios/listacolabvalorepcan.jasper",
 						"/WEB-INF/relatorios/listacolabvalorepcan.jrxml", tipoarqivo);
 				DownloadArquivo abrir = new DownloadArquivo();
 				abrir.Abrir("c:/relaweb/" + arq, tipoarqivo, true);
 				
 				
 			}
		

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}
	
	
	
	
	
	

}
