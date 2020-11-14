package br.adv.cra.manager;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import com.human.gateway.client.bean.Response;
import com.human.gateway.client.bean.SimpleMessage;
import com.human.gateway.client.exception.ClientHumanException;
import com.human.gateway.client.service.SimpleMessageService;
import br.adv.cra.entity.AndamentoCPJ;
import br.adv.cra.entity.ArquivoAnexoCPRO;
import br.adv.cra.entity.ArquivoAnexoCPROSalvo;
import br.adv.cra.entity.AuditoriaInterna;
import br.adv.cra.entity.BancaProcesso;
import br.adv.cra.entity.Comarca;
import br.adv.cra.entity.ComarcaPossui;
import br.adv.cra.entity.Correspondente;
import br.adv.cra.entity.EmailCorrespondente;
import br.adv.cra.entity.Enviosolicitacao;
import br.adv.cra.entity.FormularioAudiencia;
import br.adv.cra.entity.FormularioAudienciaNovo;
import br.adv.cra.entity.HistArqCpro;
import br.adv.cra.entity.HistArqCproRejeitado;
import br.adv.cra.entity.Historico;
import br.adv.cra.entity.LogSistema;
import br.adv.cra.entity.Orgao;
import br.adv.cra.entity.Processo;
import br.adv.cra.entity.ProcessoCPJ;
import br.adv.cra.entity.ProcessoCPPRO;
import br.adv.cra.entity.ProcessoCpproConsulta;
import br.adv.cra.entity.Rejeitado;
import br.adv.cra.entity.Renumeracao;
import br.adv.cra.entity.SoliArquivo;
import br.adv.cra.entity.Solicitacao;
import br.adv.cra.entity.SolicitacaoAnexo;
import br.adv.cra.entity.SolicitacaoPossuiArquivo;
import br.adv.cra.entity.StatusSolicitacao;
import br.adv.cra.entity.TipoSolicitacao;
import br.adv.cra.entity.Uf;
import br.adv.cra.entity.Usuario;
import br.adv.cra.persistence.AndamentoCPJDao;
import br.adv.cra.persistence.AuditoriaInternaDao;
import br.adv.cra.persistence.BancaProcessoDao;
import br.adv.cra.persistence.ComarcaDao;
import br.adv.cra.persistence.CorrespondenteDao;
import br.adv.cra.persistence.Dao;
import br.adv.cra.persistence.DaoArquivoCPRO;
import br.adv.cra.persistence.DaoBuscaProcesso;
import br.adv.cra.persistence.DaoBuscaRejeitados;
import br.adv.cra.persistence.DaoRela;
import br.adv.cra.persistence.EmailCorrespondenteDao;
import br.adv.cra.persistence.EnviodesolicitacaoDao;
import br.adv.cra.persistence.FormularioAudienciaDao;
import br.adv.cra.persistence.FormularioAudienciaNovoDao;
import br.adv.cra.persistence.HistoricoDao;
import br.adv.cra.persistence.LogSistemaDao;
import br.adv.cra.persistence.OrgaoDao;
import br.adv.cra.persistence.PossuiComarcaDao;
import br.adv.cra.persistence.ProcessoCPPRODAO;
import br.adv.cra.persistence.ProcessoDao;
import br.adv.cra.persistence.ProcessoDaoConsultaCppro;
import br.adv.cra.persistence.ProcessoDaoMysql;
import br.adv.cra.persistence.RenumeracaoDao;
import br.adv.cra.persistence.SoliArqCproDaoSalvo;
import br.adv.cra.persistence.SoliArquivoDao;
import br.adv.cra.persistence.SoliHistArqCproDao;
import br.adv.cra.persistence.SoliHistArqCproRejDao;
import br.adv.cra.persistence.SolicitacaoDao;
import br.adv.cra.persistence.StatusSolictacaoDao;
import br.adv.cra.persistence.TipoSolicitacaoDao;
import br.adv.cra.persistence.UfDao;
import br.adv.cra.persistence.UsuarioDao;
import br.adv.cra.utilitarios.Converte;
import br.adv.cra.utilitarios.CopiaArqDestino;
import br.adv.cra.utilitarios.DownloadArquivo;
import br.adv.cra.utilitarios.EnviaEmail;
import br.adv.cra.utilitarios.NovoDownload;
import br.adv.cra.utilitarios.Relatorios;

/**
 * Classe Bean responsavel por gerenciar as solictacoes de Audiencias e
 * Diligencias. Autor:Nelson Seixas de Souza -Java Web Deveoper Data de Inicio:
 * 01/06/2012 Houve uma atualização para ser colocado ADVI na elaboração de
 * solicitações CopyRight Nelson Seixas de Souza (Java Web Developer)
 **/
/**
 * @Date 01/06/2012
 * @author nelson
 *
 */
@ManagedBean(name = "solicitacao", eager = true)
@SessionScoped
// @ApplicationScoped
public class ManagerSolicitacao implements Serializable {
	private FacesContext context;
	private HttpSession session;
	private static final long serialVersionUID = 1L;
	private String nomecorrespondente;
	private String emailcorrespondenteun;
	private String emailcorresponentedois;
	private File arquivoenvia; // Envia os arquivos para o correspondente
	private File arquivorecebe; // Recebe os arquivos do correspondete
	private PossuiComarcaDao possuiComarcaDao;
	private RenumeracaoDao renumeracaoDao;
	private ComarcaPossui comarcaPossui;
	private Date datasolicitacao;
	private Usuario useraltera;
	private Processo processo;
	private Solicitacao solicitacao;
	private StatusSolicitacao statusSolicitacao;
	private Correspondente correspondente;
	private List<Correspondente> listacorrespondente;
	private List<EmailCorrespondente> emailCorrespondentes;
	private List<BancaProcesso> bancaProcessos;
	private List<Comarca> listacomarca;
	private List<Solicitacao> solicitacoes;
	private List<StatusSolicitacao> statussolicitacoes;
	private List<ProcessoCPJ> processocpj;
	private List<ProcessoCPPRO> processoCPPROLista;
	private List<ProcessoCpproConsulta> processoCpproConsultas;
	private List<TipoSolicitacao> tipoSolicitacaos;
	public List<ComarcaPossui> comarcaPossuis;
	public List<ComarcaPossui> comarcasolicitacao;
	public List<Renumeracao> renumeracao;
	public List<Orgao> listaorgao;
	public List<AndamentoCPJ> listaandamento;
	public List<SoliArquivo> listaarquivos;
	public List<SoliArquivo> listaentrada;
	public List<SoliArquivo> listasaida;
	public List<ArquivoAnexoCPRO> listarquivocppro;
	public List<ArquivoAnexoCPRO> listacproarqtodos;
	public List<Historico> listahistorico;
	public List<Enviosolicitacao> enviosolicitacaos;
	public List<Uf> listuf;
	public List<Comarca> listaporestado;
	public Solicitacao unicasolicitacao;
	public Solicitacao alterarsolicitacao;
	public Solicitacao novasolicitacao;
	private CorrespondenteDao correspondenteDao;
	private StatusSolictacaoDao statusSolictacaoDao;
	private AuditoriaInternaDao auditoriaInternaDao;
	private AuditoriaInterna auditoriaInterna;
	private Comarca comarca;
	private Orgao orgao;
	private ComarcaDao comarcaDao;
	private SolicitacaoDao solicitacaoDao;
	private FormularioAudiencia formularioAudiencia;
	private FormularioAudienciaDao formularioAudienciaDao;
	private FormularioAudienciaNovo formularioAudienciaNovo;
	private FormularioAudienciaNovoDao formularioAudienciaNovoDao;
	private ProcessoDaoMysql processoDaoFB;
	private ProcessoCPJ processoCPJ;
	private AndamentoCPJDao andamentoCPJDao;
	private ProcessoCPPRODAO processoCPPRODAO;
	private ProcessoCPPRO processoCPPRO;
	private ProcessoCpproConsulta processoCpproConsulta;
	private ProcessoDaoConsultaCppro processoDaoConsultaCppro;
	@SuppressWarnings("unused")
	private ArquivoAnexoCPRO arquivoAnexoCPRO;
	private SoliArquivoDao soliArquivoDao;
	private TipoSolicitacaoDao tipoSolicitacaoDao;
	private EmailCorrespondenteDao emailCorrespondenteDao;
	private HistoricoDao historicoDao;
	private BancaProcesso bancaProcesso;
	private BancaProcessoDao bancaProcessoDao;
	private UfDao ufDao;
	private SolicitacaoAnexo solicitacaoAnexo;
	private TipoSolicitacao tipoSolicitacao;
	private StatusSolicitacao tipoUnicoMuda;
	private Renumeracao renumeracao2;
	private OrgaoDao orgaoDao;
	private ProcessoDao processoDao;
	private UsuarioDao usuarioDao;
	private Usuario usuario;
	private Integer codigocorrespondente;
	private Integer idcorrespondente;
	private Integer idsolicitacao;
	private Integer idcomarca;
	private Integer idstatus;
	private String processobusca;
	private String autorbusca;
	private String reubusca;
	private String numerointegracaobusca;
	private String numprocessocpj;
	private Date datainicial;
	private Date datafinal;
	private String cnpjcomarca;
	private Integer tiposolictacao;
	private Date datarealizacao;
	private String processonotribunal;
	private Integer idorgao;
	private Integer numorgao;
	private Integer idnumerocomarca;
	private String instrucoes;
	private String ficha;
	private Boolean mostrajanela;
	private Boolean preposto;
	private Boolean foiconvolada;
	private String nomeautor;
	private String nomereu;
	private Integer idrenumbusca;
	private Integer statusdasolicitacao;
	private StreamedContent arquivodownload;
	private Solicitacao solicitacaonova;
	private Integer tipounicasolicitacao;
	private Integer idusuario;
	private EnviaEmail enviaEmail;
	// *****************************************************
	// Variaveis de busca
	private Integer bnumero;
	private String bprocesso;
	private Integer bcorrespondente;
	private Integer bcomarca;
	private Integer bstatus;
	private Integer borgao;
	private Date bdatainicial;
	private Date bdatafinal;
	private Integer idsolicitacaobusca;
	private String arquivoparabaixar;
	private String arquivoparaenviar;
	private String statusexterno;
	private String horaaudiencia;
	private String textodarejeicao;
	private String observacaofinal;
	private String emaildeenvio;
	private Integer listahistoricosoilicitacao;
	private SimpleDateFormat dateFormat;
	private Integer enviodesolicitacao;
	private Enviosolicitacao enviosolicitacao;
	private EnviodesolicitacaoDao enviodesolicitacaoDao;
	private Date dataminimasolictacao;
	private Integer idstatusbuscasolicitacao;
	private String tiposolicitacaobusca;
	private String tipoespecie;
	private String nomearquivo;
	private Integer idarquivoanexo;
	private String idobservacao;
	private Boolean buscatodasoclitacoes;
	private String dtbuscainicial; // datas de buscas
	private String dtbuscafinal;
	public List<Usuario> usuarios;
	public String numerointegracao;
	private String usuariologado;
	private boolean nova_soli_salva;
	private Integer tipo_periodo;
	private Renumeracao renumeracao3;
	private Integer bestadocomarca;
	private TimeZone timeZone;
	private boolean cancelasolicitacao;
	private boolean alterasolicitacaodenovo;
	private Solicitacao solicitacaopendente;
	public List<Historico> alertaDiligencia;
	public List<Solicitacao> alertabox;
	public List<Solicitacao> alertaboxAudiencia;
	private String recebe;
	private Integer idbanca;
	private Integer idbancabusca;
	private Integer idgrupo;
	private Boolean idproacordo;
	public List<Solicitacao> AvisoDoisDias; // 07/07/2014 avisa se o processo
	// existe na base.
	public String solialerta;
	public static String MSG_VALIDACAO_SOLICITACAO = "Existe Solicitação pendente de confirmação.";
	public static String MSG_VALIDACAO_HORA = "Existe Solicitação aberta 48H.";
	private boolean alertafalse;// checa se o usuario possui ou não solicitacao
	private boolean alertamapeia; // boleano do botao enviar solicitacao tela,
	private float valordaalcada;
	private String vaiacordo;
	private Integer recebecomplemento;
	private String valoralca;
	private boolean mostraauditexto;
	private boolean buscacppro;
	private List<Solicitacao> buscasolirep;
	private List<Solicitacao> receberepitadas;
	private String textoemail;
	private String buscapastaprocesso;
	private String textosolic;
	private LogSistemaDao daolog;
	private LogSistema logSistema;
	private Solicitacao solitemp;
	private HistArqCpro histArqCpro;
	private DaoArquivoCPRO daoArquivoCPRO;
	private SoliHistArqCproDao soliHistArqCproDao;
	private SoliHistArqCproRejDao soliHistArqCproRejDao;
	private HistArqCproRejeitado histArqCproRejeitado;
	private boolean repindv; // Reprovacao indevida
	private String motivorejeicao;
	private String corpodarejeicao;
	private String msgemailarquvoreijatado;
	private String emailougrupo; // Email do grupo
	private Integer idarquivocppro;
	private boolean jacarregou;
	@SuppressWarnings("unused")
	private List<ArquivoAnexoCPROSalvo> arquivoAnexoCPROSalvos;
	private SoliArqCproDaoSalvo soliArqCproDaoSalvo;
	private ArquivoAnexoCPROSalvo arquivoAnexoCPROSalvo;
	private String nomearqcppro;
	@SuppressWarnings("unused")
	private DaoBuscaProcesso daoBuscaProcesso;
	private Integer buscatotalfeitas;
	@SuppressWarnings("unused")
	private Rejeitado rejeitado;
	private DaoBuscaRejeitados daoBuscaRejeitados;
	private List<Rejeitado> rejeitados;
	private Date datainirej;
	private Date datafinrej;
	@SuppressWarnings("unused")
	private String testem;
	private String paramjs;
	private String buscamaluca;
	private Integer reltiporel;
	private String lide;
	private boolean liberaarqsoli;
	private String buscacolab; // Busca colaborador externo pela string de filtro
	private String reprovardefinitivamente;
	private Integer avaliacaocolab;
	private String textoavaliacaocolab;

	private StreamedContent file;
	String arquivobaixar;
	private boolean vercevero;
	private String envionasolictaco;
	

	/**
	 * Função create da classe e classes de inicio para ser feito Esta manager e a
	 * prinipal doo processo
	 */
	public ManagerSolicitacao() {
		soliArqCproDaoSalvo = new SoliArqCproDaoSalvo();
		solicitacao = new Solicitacao();
		statusSolicitacao = new StatusSolicitacao();
		processo = new Processo();
		comarcaPossui = new ComarcaPossui();
		possuiComarcaDao = new PossuiComarcaDao();
		correspondenteDao = new CorrespondenteDao();
		enviodesolicitacaoDao = new EnviodesolicitacaoDao();
		renumeracaoDao = new RenumeracaoDao();
		comarcaDao = new ComarcaDao();
		solicitacaoDao = new SolicitacaoDao();
		statusSolictacaoDao = new StatusSolictacaoDao();
		processoCPJ = new ProcessoCPJ();
		orgaoDao = new OrgaoDao();
		buscamaluca = "";
		daoArquivoCPRO = new DaoArquivoCPRO();
		andamentoCPJDao = new AndamentoCPJDao();
		soliArquivoDao = new SoliArquivoDao();
		processoDao = new ProcessoDao();
		tipoSolicitacaoDao = new TipoSolicitacaoDao();
		emailCorrespondenteDao = new EmailCorrespondenteDao();
		historicoDao = new HistoricoDao();
		usuarioDao = new UsuarioDao();
		usuario = new Usuario();
		renumeracao3 = new Renumeracao();
		enviaEmail = new EnviaEmail();
		bancaProcesso = new BancaProcesso();
		bancaProcessoDao = new BancaProcessoDao();
		enviosolicitacao = new Enviosolicitacao();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Para formatacao de
		formularioAudiencia = new FormularioAudiencia();
		formularioAudienciaDao = new FormularioAudienciaDao();
		auditoriaInterna = new AuditoriaInterna();
		auditoriaInternaDao = new AuditoriaInternaDao();
		processoCPPRODAO = new ProcessoCPPRODAO();
		processoDaoConsultaCppro = new ProcessoDaoConsultaCppro();
		processoCpproConsulta = new ProcessoCpproConsulta();
		formularioAudienciaNovo = new FormularioAudienciaNovo();
		formularioAudienciaNovoDao = new FormularioAudienciaNovoDao();
		arquivoAnexoCPRO = new ArquivoAnexoCPRO();
		soliHistArqCproDao = new SoliHistArqCproDao();
		arquivoAnexoCPRO = new ArquivoAnexoCPRO();
		histArqCpro = new HistArqCpro();
		histArqCproRejeitado = new HistArqCproRejeitado();
		soliHistArqCproRejDao = new SoliHistArqCproRejDao();
		arquivoAnexoCPROSalvo = new ArquivoAnexoCPROSalvo();
		rejeitado = new Rejeitado();
		daoBuscaRejeitados = new DaoBuscaRejeitados();
		msgemailarquvoreijatado = "";
		arquivoparaenviar = "";
		emailougrupo = "";
		nomearqcppro = "";
		daoBuscaProcesso = new DaoBuscaProcesso();
		buscatotalfeitas = 0;
		// daolog=new LogSistemaDao();
		// logSistema=new LogSistema();
		motivorejeicao = "";
		corpodarejeicao = "";
		useraltera = new Usuario();
		novasolicitacao = new Solicitacao();
		ufDao = new UfDao();
		idcorrespondente = 0;
		idcomarca = 0;
		idorgao = 0;
		idusuario = 0;
		numorgao = 0;
		idnumerocomarca = 0;
		bestadocomarca = 0;
		processobusca = "";
		idbanca = 0;
		autorbusca = "";
		reubusca = "";
		envionasolictaco = "";
		observacaofinal = "";
		ficha = "0";
		nomeautor = "";
		nomereu = "";
		mostrajanela = false;
		bnumero = 0;
		bprocesso = "";
		bcorrespondente = 0;
		bcomarca = 0;
		bstatus = 0;
		buscasolirep = null;
		bdatainicial = null;
		bdatafinal = null;
		borgao = 0;
		jacarregou = false;
		idbancabusca = 0;
		idsolicitacaobusca = 0;
		arquivoparabaixar = "";
		tiposolicitacaobusca = "T";
		statusdasolicitacao = 0;
		listahistoricosoilicitacao = 0;
		idstatusbuscasolicitacao = 0;
		datasolicitacao = new Date();
		datarealizacao = null;
		preposto = false;
		foiconvolada = false;
		statusexterno = "";
		horaaudiencia = "";
		recebecomplemento = 0;
		lide = "";
		liberaarqsoli = false;
		avaliacaocolab = 0;

		try {
			context = FacesContext.getCurrentInstance();
			session = (HttpSession) context.getExternalContext().getSession(true);
			idusuario = (Integer) session.getAttribute("IdUsuario");
			usuario = usuarioDao.trazusuario(idusuario);
		} catch (Exception e) {
			System.out.println("Erro: " + "\n" + e.getMessage());
		}

		enviodesolicitacao = 0;
		textodarejeicao = "";
		idarquivoanexo = 0;
		idobservacao = "N";
		idrenumbusca = 0;
		idgrupo = 0;
		vaiacordo = "";
		alterasolicitacaodenovo = false;
		valoralca = "0";
		// idusuario=0;
		buscatodasoclitacoes = false;
		dtbuscainicial = "";
		dtbuscafinal = "";
		numerointegracao = "";
		numerointegracaobusca = "";
		FacesContext context = FacesContext.getCurrentInstance();
		usuariologado = (String) context.getExternalContext().getSessionMap().remove("idUsuario");
		nova_soli_salva = false;
		cancelasolicitacao = false;
		emaildeenvio = "";
		tipoespecie = "";
		recebe = "";
		idproacordo = false;
		valordaalcada = 0;
		mostraauditexto = false;
		buscacppro = true;
		textoemail = "";
		buscapastaprocesso = "";
		textosolic = "";
		solitemp = new Solicitacao();
		repindv = false;
		jacarregou = false;
		datainirej = null;
		datafinrej = null;
		buscamaluca = "N";
		tipo_periodo = 1;
		buscacolab = "";
		reprovardefinitivamente = "";
		textoavaliacaocolab = "";
		vercevero = false;
	}

	/**
	 * Constructor da classe principal Teste
	 * 
	 * @param nomecorrespondente
	 * @param emailcorrespondenteun
	 * @param emailcorresponentedois
	 * @param arquivoenvia
	 * @param arquivorecebe
	 * @param possuiComarcaDao
	 * @param renumeracaoDao
	 * @param comarcaPossui
	 * @param datasolicitacao
	 * @param processo
	 * @param solicitacao
	 * @param statusSolicitacao
	 * @param correspondente
	 * @param listacorrespondente
	 * @param alertabox
	 * @param listacomarca
	 * @param solicitacoes
	 * @param statussolicitacoes
	 * @param processocpj
	 * @param comarcaPossuis
	 * @param comarcasolicitacao
	 * @param renumeracao
	 * @param listaorgao
	 * @param listaandamento
	 * @param listaarquivos
	 * @param listaentrada
	 * @param listasaida
	 * @param unicasolicitacao
	 * @param alterarsolicitacao
	 * @param correspondenteDao
	 * @param statusSolictacaoDao
	 * @param comarca
	 * @param orgao
	 * @param comarcaDao
	 * @param solicitacaoDao
	 * @param processoDaoFB
	 * @param processoCPJ2
	 * @param andamentoCPJDao
	 * @param soliArquivoDao
	 * @param tipoSolicitacaoDao
	 * @param historicoDao
	 * @param solicitacaoAnexo
	 * @param tipoSolicitacao
	 * @param tipoUnicoMuda
	 * @param renumeracao2
	 * @param historico
	 * @param orgaoDao
	 * @param processoDao
	 * @param codigocorrespondente
	 * @param idcorrespondente
	 * @param idsolicitacao
	 * @param idcomarca
	 * @param idstatus
	 * @param processobusca
	 * @param autorbusca
	 * @param reubusca
	 * @param numprocessocpj
	 * @param datainicial
	 * @param datafinal
	 * @param cnpjcomarca
	 * @param tiposolictacao
	 * @param datarealizacao
	 * @param processonotribunal
	 * @param idorgao
	 * @param idnumerocomarca
	 * @param instrucoes
	 * @param ficha
	 * @param mostrajanela
	 * @param preposto
	 * @param nomeautor
	 * @param nomereu
	 * @param statusdasolicitacao
	 * @param arquivodownload
	 * @param solicitacaonova
	 * @param tipounicasolicitacao
	 * @param file
	 * @param byteArray
	 * @param filearquivo
	 * @param idusuario
	 * @param bnumero
	 * @param bprocesso
	 * @param bcorrespondente
	 * @param bcomarca
	 * @param bstatus
	 * @param bdatainicial
	 * @param bdatafinal
	 * @param idsolicitacaobusca
	 * @param arquivoparabaixar
	 * @param statusexterno
	 * @param horaaudiencia
	 * @param enviodesolicitacao
	 * @param textodarejeicao
	 * @param observacaofinal
	 * @param datamininasolicitacao
	 * @param idstatusbuscasolicitacao
	 * @param idobservacao
	 * @param buscatodasoclitacoes
	 * @param borgao
	 * @param dtbuscainicial
	 * @param dtbuscafinal
	 * @param usuariologado
	 * @param tipo_periodo
	 * @param cancelasolicitacao
	 * @param emaildeenvio
	 * @param solialerta
	 * @param alertaboxAudiencia
	 * @param alertaDiligencia
	 * @param AvisoDoisDias
	 * @param recebe
	 */

	public ManagerSolicitacao(String nomecorrespondente, String emailcorrespondenteun, String emailcorresponentedois,
			File arquivoenvia, File arquivorecebe, PossuiComarcaDao possuiComarcaDao, RenumeracaoDao renumeracaoDao,
			ComarcaPossui comarcaPossui, Date datasolicitacao, Processo processo, Solicitacao solicitacao,
			StatusSolicitacao statusSolicitacao, Correspondente correspondente,
			List<Correspondente> listacorrespondente, List<Solicitacao> alertabox, List<Comarca> listacomarca,
			List<Solicitacao> solicitacoes, List<StatusSolicitacao> statussolicitacoes, List<ProcessoCPJ> processocpj,
			List<ComarcaPossui> comarcaPossuis, List<ComarcaPossui> comarcasolicitacao,
			// ComarcaPossui comarcasolicitacao,
			List<Renumeracao> renumeracao, List<Orgao> listaorgao, List<AndamentoCPJ> listaandamento,
			List<SoliArquivo> listaarquivos, List<SoliArquivo> listaentrada, List<SoliArquivo> listasaida,
			Solicitacao unicasolicitacao, Solicitacao alterarsolicitacao, CorrespondenteDao correspondenteDao,
			StatusSolictacaoDao statusSolictacaoDao, Comarca comarca, Orgao orgao, ComarcaDao comarcaDao,
			SolicitacaoDao solicitacaoDao, ProcessoDaoMysql processoDaoFB, ProcessoCPJ processoCPJ2,
			AndamentoCPJDao andamentoCPJDao, SoliArquivoDao soliArquivoDao, TipoSolicitacaoDao tipoSolicitacaoDao,
			HistoricoDao historicoDao, SolicitacaoAnexo solicitacaoAnexo, TipoSolicitacao tipoSolicitacao,
			StatusSolicitacao tipoUnicoMuda, Renumeracao renumeracao2, Historico historico, OrgaoDao orgaoDao,
			ProcessoDao processoDao, Integer codigocorrespondente, Integer idcorrespondente, Integer idsolicitacao,
			Integer idcomarca, Integer idstatus, String processobusca, String autorbusca, String reubusca,
			String numprocessocpj, Date datainicial, Date datafinal, String cnpjcomarca, Integer tiposolictacao,
			Date datarealizacao, String processonotribunal, Integer idorgao, Integer idnumerocomarca, String instrucoes,
			String ficha, Boolean mostrajanela, Boolean preposto, String nomeautor, String nomereu,
			Integer statusdasolicitacao, StreamedContent arquivodownload, Solicitacao solicitacaonova,
			Integer tipounicasolicitacao, File file, byte[] byteArray, UploadedFile filearquivo, Integer idusuario,
			Integer bnumero, String bprocesso, Integer bcorrespondente, Integer bcomarca, Integer bstatus,
			Date bdatainicial, Date bdatafinal, Integer idsolicitacaobusca, String arquivoparabaixar,
			String statusexterno, String horaaudiencia, Integer enviodesolicitacao, String textodarejeicao,
			String observacaofinal, Date datamininasolicitacao, Integer idstatusbuscasolicitacao, String idobservacao,
			boolean buscatodasoclitacoes, Integer borgao, String dtbuscainicial, String dtbuscafinal,
			String usuariologado, Integer tipo_periodo, boolean cancelasolicitacao, String emaildeenvio,
			String solialerta, List<Solicitacao> alertaboxAudiencia, List<Historico> alertaDiligencia,
			List<Solicitacao> AvisoDoisDias, String recebe, Integer idgrupo, float valordaalcada, String vaiacordo,
			BancaProcesso bancaProcesso, BancaProcessoDao bancaProcessoDao, Integer recebecomplemento, Integer uf,
			String valoralca, boolean mostraauditexto, AuditoriaInterna auditoriaInterna,
			AuditoriaInternaDao auditoriaInternaDao, boolean buscacppro, ProcessoCPPRO processoCPPRO,
			ProcessoCPPRODAO processoCPPRODAO, FormularioAudienciaNovo formularioAudienciaNovo,
			FormularioAudienciaNovoDao formularioAudienciaNovoDao, Usuario useraltera, boolean alterasolicitacaodenovo,
			String textoemail, String buscapastaprocesso, String textosolic, boolean repindv, String lide,
			boolean liberaarqsoli, String buscacolab, String reprovardefinitivamente, Integer avaliacaocolab,
			String textoavaliacaocolab, Integer numorgao) {
		this.nomecorrespondente = nomecorrespondente;
		this.emailcorrespondenteun = emailcorrespondenteun;
		this.emailcorresponentedois = emailcorresponentedois;
		this.arquivoenvia = arquivoenvia;
		this.arquivorecebe = arquivorecebe;
		this.numorgao = numorgao;
		this.possuiComarcaDao = possuiComarcaDao;
		this.renumeracaoDao = renumeracaoDao;
		this.comarcaPossui = comarcaPossui;
		this.datasolicitacao = datasolicitacao;
		this.processo = processo;
		this.solicitacao = solicitacao;
		this.statusSolicitacao = statusSolicitacao;
		this.correspondente = correspondente;
		this.listacorrespondente = listacorrespondente;
		this.listacomarca = listacomarca;
		this.solicitacoes = solicitacoes;
		this.statussolicitacoes = statussolicitacoes;
		this.processocpj = processocpj;
		this.comarcaPossuis = comarcaPossuis;
		this.comarcasolicitacao = comarcasolicitacao;
		this.renumeracao = renumeracao;
		this.listaorgao = listaorgao;
		this.listaandamento = listaandamento;
		this.listaarquivos = listaarquivos;
		this.listaentrada = listaentrada;
		this.listasaida = listasaida;
		this.unicasolicitacao = unicasolicitacao;
		this.alterarsolicitacao = alterarsolicitacao;
		this.correspondenteDao = correspondenteDao;
		this.statusSolictacaoDao = statusSolictacaoDao;
		this.comarca = comarca;
		this.orgao = orgao;
		this.comarcaDao = comarcaDao;
		this.solicitacaoDao = solicitacaoDao;
		this.processoDaoFB = processoDaoFB;
		this.processoCPJ = processoCPJ2;
		this.andamentoCPJDao = andamentoCPJDao;
		this.soliArquivoDao = soliArquivoDao;
		this.tipoSolicitacaoDao = tipoSolicitacaoDao;
		this.historicoDao = historicoDao;
		this.solicitacaoAnexo = solicitacaoAnexo;
		this.tipoSolicitacao = tipoSolicitacao;
		this.tipoUnicoMuda = tipoUnicoMuda;
		this.renumeracao2 = renumeracao2;
		this.orgaoDao = orgaoDao;
		this.processoDao = processoDao;
		this.codigocorrespondente = codigocorrespondente;
		this.idcorrespondente = idcorrespondente;
		this.idsolicitacao = idsolicitacao;
		this.idcomarca = idcomarca;
		this.idstatus = idstatus;
		this.processobusca = processobusca;
		this.autorbusca = autorbusca;
		this.reubusca = reubusca;
		this.numprocessocpj = numprocessocpj;
		this.datainicial = datainicial;
		this.datafinal = datafinal;
		this.cnpjcomarca = cnpjcomarca;
		this.tiposolictacao = tiposolictacao;
		this.datarealizacao = datarealizacao;
		this.processonotribunal = processonotribunal;
		this.idorgao = idorgao;
		this.idnumerocomarca = idnumerocomarca;
		this.instrucoes = instrucoes;
		this.ficha = ficha;
		this.mostrajanela = mostrajanela;
		this.preposto = preposto;
		this.nomeautor = nomeautor;
		this.nomereu = nomereu;
		this.statusdasolicitacao = statusdasolicitacao;
		this.arquivodownload = arquivodownload;
		this.solicitacaonova = solicitacaonova;
		this.tipounicasolicitacao = tipounicasolicitacao;
		this.idusuario = idusuario;
		this.bnumero = bnumero;
		this.bprocesso = bprocesso;
		this.bcorrespondente = bcorrespondente;
		this.bcomarca = bcomarca;
		this.bstatus = bstatus;
		this.bdatainicial = bdatainicial;
		this.bdatafinal = bdatafinal;
		this.idsolicitacaobusca = idsolicitacaobusca;
		this.arquivoparabaixar = arquivoparabaixar;
		this.statusexterno = statusexterno;
		this.horaaudiencia = horaaudiencia;
		this.enviodesolicitacao = enviodesolicitacao;
		this.textodarejeicao = textodarejeicao;
		this.observacaofinal = observacaofinal;
		this.dataminimasolictacao = datamininasolicitacao;
		this.idstatusbuscasolicitacao = idstatusbuscasolicitacao;
		this.idobservacao = idobservacao;
		this.buscatodasoclitacoes = buscatodasoclitacoes;
		this.borgao = borgao;
		this.dtbuscainicial = dtbuscainicial;
		this.dtbuscafinal = dtbuscafinal;
		this.usuariologado = usuariologado;
		this.tipo_periodo = tipo_periodo;
		this.cancelasolicitacao = cancelasolicitacao;
		this.emaildeenvio = emaildeenvio;
		this.solialerta = solialerta;
		this.alertabox = alertabox;
		this.alertaboxAudiencia = alertaboxAudiencia;
		this.alertaDiligencia = alertaDiligencia;
		this.AvisoDoisDias = AvisoDoisDias;
		this.recebe = recebe;
		this.idgrupo = idgrupo;
		this.valordaalcada = valordaalcada;
		this.vaiacordo = vaiacordo;
		this.bancaProcesso = bancaProcesso;
		this.recebecomplemento = recebecomplemento;
		this.valoralca = valoralca;
		this.mostraauditexto = mostraauditexto;
		this.auditoriaInterna = auditoriaInterna;
		this.auditoriaInternaDao = auditoriaInternaDao;
		this.buscacppro = buscacppro;
		this.processoCPPRO = processoCPPRO;
		this.processoCPPRODAO = processoCPPRODAO;
		this.formularioAudienciaNovoDao = formularioAudienciaNovoDao;
		this.formularioAudienciaNovo = formularioAudienciaNovo;
		this.useraltera = useraltera;
		this.alterasolicitacaodenovo = alterasolicitacaodenovo;
		this.textoemail = textoemail;
		this.buscapastaprocesso = buscapastaprocesso;
		this.textosolic = textosolic;
		this.repindv = repindv;
		this.lide = lide;
		this.liberaarqsoli = liberaarqsoli;
		this.buscacolab = buscacolab;
		this.reprovardefinitivamente = reprovardefinitivamente;
		this.avaliacaocolab = avaliacaocolab;
		this.textoavaliacaocolab = textoavaliacaocolab;
	}

	public String getEnvionasolictaco() {
		return envionasolictaco;
	}

	public void setEnvionasolictaco(String envionasolictaco) {
		this.envionasolictaco = envionasolictaco;
	}

	public Integer getNumorgao() {
		return numorgao;
	}

	public void setNumorgao(Integer numorgao) {
		this.numorgao = numorgao;
	}

	public boolean isVercevero() {
		return vercevero;
	}

	public void setVercevero(boolean vercevero) {
		this.vercevero = vercevero;
	}

	public String getArquivobaixar() {
		return arquivobaixar;
	}

	public void setArquivobaixar(String arquivobaixar) {
		this.arquivobaixar = arquivobaixar;
	}

	public StreamedContent getFile() {
		return file;
	}

	public String getTextoavaliacaocolab() {
		return textoavaliacaocolab;
	}

	public void setTextoavaliacaocolab(String textoavaliacaocolab) {
		this.textoavaliacaocolab = textoavaliacaocolab;
	}

	public Integer getAvaliacaocolab() {
		return avaliacaocolab;
	}

	public void setAvaliacaocolab(Integer avaliacaocolab) {
		this.avaliacaocolab = avaliacaocolab;
	}

	public String getReprovardefinitivamente() {
		return reprovardefinitivamente;
	}

	public void setReprovardefinitivamente(String reprovardefinitivamente) {
		this.reprovardefinitivamente = reprovardefinitivamente;
	}

	public Comarca getComarca() {
		return comarca;
	}

	public void setComarca(Comarca comarca) {
		this.comarca = comarca;
	}

	public String getBuscacolab() {
		return buscacolab;
	}

	public void setBuscacolab(String buscacolab) {
		this.buscacolab = buscacolab;
	}

	public boolean isLiberaarqsoli() {
		return liberaarqsoli;
	}

	public void setLiberaarqsoli(boolean liberaarqsoli) {
		this.liberaarqsoli = liberaarqsoli;
	}

	public String getLide() {
		return lide;
	}

	public void setLide(String lide) {
		this.lide = lide;
	}

	public Date getDatainirej() {
		return datainirej;
	}

	public void setDatainirej(Date datainirej) {
		this.datainirej = datainirej;
	}

	public Date getDatafinrej() {
		return datafinrej;
	}

	public void setDatafinrej(Date datafinrej) {
		this.datafinrej = datafinrej;
	}

	public Integer getBuscatotalfeitas() {
		return buscatotalfeitas;
	}

	public void setBuscatotalfeitas(Integer buscatotalfeitas) {
		this.buscatotalfeitas = buscatotalfeitas;
	}

	public String getNomearqcppro() {
		return nomearqcppro;
	}

	public void setNomearqcppro(String nomearqcppro) {
		this.nomearqcppro = nomearqcppro;
	}

	public ArquivoAnexoCPROSalvo getArquivoAnexoCPROSalvo() {
		return arquivoAnexoCPROSalvo;
	}

	public void setArquivoAnexoCPROSalvo(ArquivoAnexoCPROSalvo arquivoAnexoCPROSalvo) {
		this.arquivoAnexoCPROSalvo = arquivoAnexoCPROSalvo;
	}

	public SoliArqCproDaoSalvo getSoliArqCproDaoSalvo() {
		return soliArqCproDaoSalvo;
	}

	public void setSoliArqCproDaoSalvo(SoliArqCproDaoSalvo soliArqCproDaoSalvo) {
		this.soliArqCproDaoSalvo = soliArqCproDaoSalvo;
	}

	/**
	 * Traz os arquvos que ja foram atualizados na tabela do ged do cppro para serem
	 * ataulizados para o envio ao colaborador
	 * 
	 * @return
	 */
	public List<ArquivoAnexoCPROSalvo> getArquivoAnexoCPROSalvos() {
		List<ArquivoAnexoCPROSalvo> arquivoAnexoCPROSalvos = soliArqCproDaoSalvo.traztodos("0",
				alterarsolicitacao.getIdsolicitacao());
		return arquivoAnexoCPROSalvos;
	}

	public void setArquivoAnexoCPROSalvos(List<ArquivoAnexoCPROSalvo> arquivoAnexoCPROSalvos) {
		this.arquivoAnexoCPROSalvos = arquivoAnexoCPROSalvos;
	}

	public List<ArquivoAnexoCPRO> getListarquivocppro() {
		return listarquivocppro;
	}

	public void setListarquivocppro(List<ArquivoAnexoCPRO> listarquivocppro) {
		this.listarquivocppro = listarquivocppro;
	}

	public DaoArquivoCPRO getDaoArquivoCPRO() {
		return daoArquivoCPRO;
	}

	public void setDaoArquivoCPRO(DaoArquivoCPRO daoArquivoCPRO) {
		this.daoArquivoCPRO = daoArquivoCPRO;
	}

	public boolean isJacarregou() {
		return jacarregou;
	}

	public void setJacarregou(boolean jacarregou) {
		this.jacarregou = jacarregou;
	}

	public Integer getIdarquivocppro() {
		return idarquivocppro;
	}

	public void setIdarquivocppro(Integer idarquivocppro) {
		this.idarquivocppro = idarquivocppro;
	}

	public String getEmailougrupo() {
		return emailougrupo;
	}

	public void setEmailougrupo(String emailougrupo) {
		this.emailougrupo = emailougrupo;
	}

	public String getMsgemailarquvoreijatado() {
		return msgemailarquvoreijatado;
	}

	public void setMsgemailarquvoreijatado(String msgemailarquvoreijatado) {
		this.msgemailarquvoreijatado = msgemailarquvoreijatado;
	}

	public String getMotivorejeicao() {
		return motivorejeicao;
	}

	public void setMotivorejeicao(String motivorejeicao) {
		this.motivorejeicao = motivorejeicao;
	}

	public String getCorpodarejeicao() {
		return corpodarejeicao;
	}

	public void setCorpodarejeicao(String corpodarejeicao) {
		this.corpodarejeicao = corpodarejeicao;
	}

	public boolean isRepindv() {
		return repindv;
	}

	public void setRepindv(boolean repindv) {
		this.repindv = repindv;
	}

	public Solicitacao getSolitemp() {
		return solitemp;
	}

	public void setSolitemp(Solicitacao solitemp) {
		this.solitemp = solitemp;
	}

	public String getTextosolic() {
		return textosolic;
	}

	public void setTextosolic(String textosolic) {
		this.textosolic = textosolic;
	}

	public String getBuscapastaprocesso() {
		return buscapastaprocesso;
	}

	public void setBuscapastaprocesso(String buscapastaprocesso) {
		this.buscapastaprocesso = buscapastaprocesso;
	}

	public List<Solicitacao> getReceberepitadas() {
		return receberepitadas;
	}

	public void setReceberepitadas(List<Solicitacao> receberepitadas) {
		this.receberepitadas = receberepitadas;
	}

	public List<Solicitacao> getBuscasolirep() {
		return buscasolirep;
	}

	public void setBuscasolirep(List<Solicitacao> buscasolirep) {
		this.buscasolirep = buscasolirep;
	}

	public FormularioAudienciaNovo getFormularioAudienciaNovo() {
		return formularioAudienciaNovo;
	}

	public void setFormularioAudienciaNovo(FormularioAudienciaNovo formularioAudienciaNovo) {
		this.formularioAudienciaNovo = formularioAudienciaNovo;
	}

	public FormularioAudienciaNovoDao getFormularioAudienciaNovoDao() {
		return formularioAudienciaNovoDao;
	}

	public void setFormularioAudienciaNovoDao(FormularioAudienciaNovoDao formularioAudienciaNovoDao) {
		this.formularioAudienciaNovoDao = formularioAudienciaNovoDao;
	}

	public boolean isBuscacppro() {
		return buscacppro;
	}

	public void setBuscacppro(boolean buscacppro) {
		this.buscacppro = buscacppro;
	}

	public Usuario getUseraltera() {
		return useraltera;
	}

	public void setUseraltera(Usuario useraltera) {
		this.useraltera = useraltera;
	}

	public AuditoriaInternaDao getAuditoriaInternaDao() {
		return auditoriaInternaDao;
	}

	public void setAuditoriaInternaDao(AuditoriaInternaDao auditoriaInternaDao) {
		this.auditoriaInternaDao = auditoriaInternaDao;
	}

	public AuditoriaInterna getAuditoriaInterna() {
		return auditoriaInterna;
	}

	public void setAuditoriaInterna(AuditoriaInterna auditoriaInterna) {
		this.auditoriaInterna = auditoriaInterna;
	}

	public boolean isMostraauditexto() {
		return mostraauditexto;
	}

	public void setMostraauditexto(boolean mostraauditexto) {
		this.mostraauditexto = mostraauditexto;
	}

	public String getValoralca() {
		return valoralca;
	}

	public void setValoralca(String valoralca) {
		this.valoralca = valoralca;
	}

	public Integer getRecebecomplemento() {
		return recebecomplemento;
	}

	public void setRecebecomplemento(Integer recebecomplemento) {
		this.recebecomplemento = recebecomplemento;
	}

	public BancaProcessoDao getBancaProcessoDao() {
		return bancaProcessoDao;
	}

	public void setBancaProcessoDao(BancaProcessoDao bancaProcessoDao) {
		this.bancaProcessoDao = bancaProcessoDao;
	}

	public BancaProcesso getBancaProcesso() {
		return bancaProcesso;
	}

	public void setBancaProcesso(BancaProcesso bancaProcesso) {
		this.bancaProcesso = bancaProcesso;
	}

	public String getVaiacordo() {
		return vaiacordo;
	}

	public void setVaiacordo(String vaiacordo) {
		this.vaiacordo = vaiacordo;
	}

	public Integer getIdgrupo() {
		return idgrupo;
	}

	public void setIdgrupo(Integer idgrupo) {
		this.idgrupo = idgrupo;
	}

	public Integer getIdbancabusca() {
		return idbancabusca;
	}

	public void setIdbancabusca(Integer idbancabusca) {
		this.idbancabusca = idbancabusca;
	}

	public Integer getIdbanca() {
		return idbanca;
	}

	public void setIdbanca(Integer idbanca) {
		this.idbanca = idbanca;
	}

	public String getRecebe() {
		return recebe;
	}

	public void setRecebe(String recebe) {
		this.recebe = recebe;
	}

	public List<Solicitacao> getAvisoDoisDias() {
		return AvisoDoisDias;
	}

	public void setAvisoDoisDias(List<Solicitacao> avisoDoisDias) {
		AvisoDoisDias = avisoDoisDias;
	}

	public List<Historico> getAlertaDiligencia() {
		return alertaDiligencia;
	}

	public void setAlertaDiligencia(List<Historico> alertaDiligencia) {
		this.alertaDiligencia = alertaDiligencia;
	}

	public List<Solicitacao> getAlertaboxAudiencia() {
		return alertaboxAudiencia;
	}

	public void setAlertaboxAudiencia(List<Solicitacao> alertaboxAudiencia) {
		this.alertaboxAudiencia = alertaboxAudiencia;
	}

	public List<Solicitacao> getAlertabox() {
		return alertabox;
	}

	/**
	 * Traz a alerta de solcitacao pendendte
	 * 
	 * @param alertabox
	 */
	public void setAlertabox(List<Solicitacao> alertabox) {
		this.alertabox = alertabox;
	}

	public boolean isAlertafalse() {
		return alertafalse;
	}

	public void setAlertafalse(boolean alertafalse) {
		this.alertafalse = alertafalse;
	}

	public boolean isAlertamapeia() {
		return alertamapeia;
	}

	public void setAlertamapeia(boolean alertamapeia) {
		this.alertamapeia = alertamapeia;
	}

	public String getTipoespecie() {
		return tipoespecie;
	}

	public void setTipoespecie(String tipoespecie) {
		this.tipoespecie = tipoespecie;
	}

	public String getEmaildeenvio() {
		return emaildeenvio;
	}

	public void setEmaildeenvio(String emaildeenvio) {
		this.emaildeenvio = emaildeenvio;
	}

	public List<EmailCorrespondente> getEmailCorrespondentes() {
		emailCorrespondentes = emailCorrespondenteDao.lista(idcorrespondente);
		return emailCorrespondentes;
	}

	public void setEmailCorrespondentes(List<EmailCorrespondente> emailCorrespondentes) {
		this.emailCorrespondentes = emailCorrespondentes;
	}

	public EmailCorrespondenteDao getEmailCorrespondenteDao() {
		return emailCorrespondenteDao;
	}

	public void setEmailCorrespondenteDao(EmailCorrespondenteDao emailCorrespondenteDao) {
		this.emailCorrespondenteDao = emailCorrespondenteDao;
	}

	public boolean isCancelasolicitacao() {
		return cancelasolicitacao;
	}

	public void setCancelasolicitacao(boolean cancelasolicitacao) {
		this.cancelasolicitacao = cancelasolicitacao;
	}

	/**
	 * Pega a timezone atual do servidor para atualizar a hora no sistema
	 * 
	 * @return
	 */
	@SuppressWarnings("static-access")
	public TimeZone getTimeZone() {
		return timeZone.getDefault();
	}

	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}

	/**
	 * 
	 * @return
	 */
	public List<Comarca> getListaporestado() {

		listaporestado = comarcaDao.buscargeralporestado(bestadocomarca);
		return listaporestado;
	}

	public void setListaporestado(List<Comarca> listaporestado) {
		this.listaporestado = listaporestado;
	}

	public Integer getBestadocomarca() {
		return bestadocomarca;
	}

	public void setBestadocomarca(Integer bestadocomarca) {
		this.bestadocomarca = bestadocomarca;
	}

	public List<Uf> getListuf() {

		listuf = ufDao.listagem();

		return listuf;
	}

	public void setListuf(List<Uf> listuf) {
		this.listuf = listuf;
	}

	public Renumeracao getRenumeracao3() {
		return renumeracao3;
	}

	public void setRenumeracao3(Renumeracao renumeracao3) {
		this.renumeracao3 = renumeracao3;
	}

	public Integer getTipo_periodo() {
		return tipo_periodo;
	}

	public void setTipo_periodo(Integer tipo_periodo) {
		this.tipo_periodo = tipo_periodo;
	}

	public boolean isNova_soli_salva() {
		return nova_soli_salva;
	}

	public void setNova_soli_salva(boolean nova_soli_salva) {
		this.nova_soli_salva = nova_soli_salva;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getUsuariologado() {
		return usuariologado;
	}

	public void setUsuariologado(String usuariologado) {
		this.usuariologado = usuariologado;
	}

	public String getNumerointegracaobusca() {
		return numerointegracaobusca;
	}

	public void setNumerointegracaobusca(String numerointegracaobusca) {
		this.numerointegracaobusca = numerointegracaobusca;
	}

	public String getNumerointegracao() {
		return numerointegracao;
	}

	public void setNumerointegracao(String numerointegracao) {
		this.numerointegracao = numerointegracao;
	}

	public List<Usuario> getUsuarios() {
		usuarios = usuarioDao.listagemusuario();
		return usuarios;
	}

	/**
	 * Lista os usuarios do sistema na base
	 * 
	 * @param usuarios
	 */
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public String getDtbuscainicial() {
		return dtbuscainicial;
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

	public Integer getBorgao() {
		return borgao;
	}

	public void setBorgao(Integer borgao) {
		this.borgao = borgao;
	}

	public Boolean getBuscatodasoclitacoes() {
		return buscatodasoclitacoes;
	}

	public void setBuscatodasoclitacoes(Boolean buscatodasoclitacoes) {
		this.buscatodasoclitacoes = buscatodasoclitacoes;
	}

	public Boolean getFoiconvolada() {
		return foiconvolada;
	}

	public void setFoiconvolada(Boolean foiconvolada) {
		this.foiconvolada = foiconvolada;
	}

	public Integer getIdrenumbusca() {
		return idrenumbusca;
	}

	public void setIdrenumbusca(Integer idrenumbusca) {
		this.idrenumbusca = idrenumbusca;
	}

	public String getIdobservacao() {
		return idobservacao;
	}

	public void setIdobservacao(String idobservacao) {
		this.idobservacao = idobservacao;
	}

	public String getNomearquivo() {
		return nomearquivo;
	}

	public void setNomearquivo(String nomearquivo) {
		this.nomearquivo = nomearquivo;
	}

	public Integer getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(Integer idusuario) {
		this.idusuario = idusuario;
	}

	public String getObservacaofinal() {
		return observacaofinal;
	}

	public void setObservacaofinal(String observacaofinal) {
		this.observacaofinal = observacaofinal;
	}

	public String getTextodarejeicao() {
		return textodarejeicao;
	}

	public void setTextodarejeicao(String textodarejeicao) {
		this.textodarejeicao = textodarejeicao;
	}

	public Integer getListahistoricosoilicitacao() {
		return listahistoricosoilicitacao;
	}

	public void setListahistoricosoilicitacao(Integer listahistoricosoilicitacao) {
		this.listahistoricosoilicitacao = listahistoricosoilicitacao;
	}

	public Solicitacao getAlterarsolicitacao() {

		return alterarsolicitacao;
	}

	public Date getDataminimasolictacao() {
		dataminimasolictacao = Dataminima();
		return dataminimasolictacao;
	}

	public void setDataminimasolictacao(Date dataminimasolictacao) {
		this.dataminimasolictacao = dataminimasolictacao;
	}

	public TipoSolicitacaoDao getTipoSolicitacaoDao() {
		return tipoSolicitacaoDao;
	}

	public void setTipoSolicitacaoDao(TipoSolicitacaoDao tipoSolicitacaoDao) {
		this.tipoSolicitacaoDao = tipoSolicitacaoDao;
	}

	public void setAlterarsolicitacao(Solicitacao alterarsolicitacao) {
		this.alterarsolicitacao = alterarsolicitacao;
	}

	public String getNomeautor() {
		return nomeautor;
	}

	public void setNomeautor(String nomeautor) {
		this.nomeautor = nomeautor;
	}

	public String getNomereu() {
		return nomereu;
	}

	public Integer getIdstatusbuscasolicitacao() {
		return idstatusbuscasolicitacao;
	}

	public void setIdstatusbuscasolicitacao(Integer idstatusbuscasolicitacao) {
		this.idstatusbuscasolicitacao = idstatusbuscasolicitacao;
	}

	public void setNomereu(String nomereu) {
		this.nomereu = nomereu;
	}

	public String getHoraaudiencia() {
		return horaaudiencia;
	}

	public void setHoraaudiencia(String horaaudiencia) {
		this.horaaudiencia = horaaudiencia;
	}

	public Integer getStatusdasolicitacao() {
		return statusdasolicitacao;
	}

	public void setStatusdasolicitacao(Integer statusdasolicitacao) {
		this.statusdasolicitacao = statusdasolicitacao;
	}

	public Boolean getMostrajanela() {
		return mostrajanela;
	}

	public void setMostrajanela(Boolean mostrajanela) {
		this.mostrajanela = mostrajanela;
	}

	public String getNomecorrespondente() {
		return nomecorrespondente;
	}

	public Integer getEnviodesolicitacao() {
		return enviodesolicitacao;
	}

	public void setEnviodesolicitacao(Integer enviodesolicitacao) {
		this.enviodesolicitacao = enviodesolicitacao;
	}

	public void setNomecorrespondente(String nomecorrespondente) {
		this.nomecorrespondente = nomecorrespondente;
	}

	public String getEmailcorrespondenteun() {
		return emailcorrespondenteun;
	}

	public void setEmailcorrespondenteun(String emailcorrespondenteun) {
		this.emailcorrespondenteun = emailcorrespondenteun;
	}

	public String getEmailcorresponentedois() {
		return emailcorresponentedois;
	}

	public SolicitacaoAnexo getSolicitacaoAnexo() {
		return solicitacaoAnexo;
	}

	public void setSolicitacaoAnexo(SolicitacaoAnexo solicitacaoAnexo) {
		this.solicitacaoAnexo = solicitacaoAnexo;
	}

	public void setEmailcorresponentedois(String emailcorresponentedois) {
		this.emailcorresponentedois = emailcorresponentedois;
	}

	public File getArquivoenvia() {
		return arquivoenvia;
	}

	public void setArquivoenvia(File arquivoenvia) {
		this.arquivoenvia = arquivoenvia;
	}

	public Date getDatarealizacao() {
		return datarealizacao;
	}

	public Integer getIdnumerocomarca() {
		return idnumerocomarca;
	}

	public void setIdnumerocomarca(Integer idnumerocomarca) {
		this.idnumerocomarca = idnumerocomarca;
	}

	public void setDatarealizacao(Date datarealizacao) {
		this.datarealizacao = datarealizacao;
	}

	public String getFicha() {
		return ficha;
	}

	public void setFicha(String ficha) {
		this.ficha = ficha;
	}

	public Integer getIdorgao() {
		return idorgao;
	}

	public void setIdorgao(Integer idorgao) {
		this.idorgao = idorgao;
	}

	public File getArquivorecebe() {
		return arquivorecebe;
	}

	public void setArquivorecebe(File arquivorecebe) {
		this.arquivorecebe = arquivorecebe;
	}

	public PossuiComarcaDao getPossuiComarcaDao() {
		return possuiComarcaDao;
	}

	public void setPossuiComarcaDao(PossuiComarcaDao possuiComarcaDao) {
		this.possuiComarcaDao = possuiComarcaDao;
	}

	public RenumeracaoDao getRenumeracaoDao() {
		return renumeracaoDao;
	}

	public Date getDatasolicitacao() {
		return datasolicitacao;
	}

	public void setRenumeracaoDao(RenumeracaoDao renumeracaoDao) {
		this.renumeracaoDao = renumeracaoDao;
	}

	public void setDatasolicitacao(Date datasolicitacao) {
		this.datasolicitacao = datasolicitacao;
	}

	public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	public Solicitacao getSolicitacao() {

		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

	public ComarcaPossui getComarcaPossui() {
		return comarcaPossui;
	}

	public void setComarcaPossui(ComarcaPossui comarcaPossui) {
		this.comarcaPossui = comarcaPossui;
	}

	public Integer getIdcomarca() {
		return idcomarca;
	}

	public void setIdcomarca(Integer idcomarca) {
		this.idcomarca = idcomarca;
	}

	public ComarcaDao getComarcaDao() {
		return comarcaDao;
	}

	public void setComarcaDao(ComarcaDao comarcaDao) {
		this.comarcaDao = comarcaDao;
	}

	public Integer getIdsolicitacao() {
		return idsolicitacao;
	}

	public void setIdsolicitacao(Integer idsolicitacao) {
		this.idsolicitacao = idsolicitacao;
	}

	public Integer getIdcorrespondente() {
		return idcorrespondente;
	}

	public void setIdcorrespondente(Integer idcorrespondente) {
		this.idcorrespondente = idcorrespondente;
	}

	public Integer getCodigocorrespondente() {
		return codigocorrespondente;
	}

	public void setCodigocorrespondente(Integer codigocorrespondente) {
		this.codigocorrespondente = codigocorrespondente;
	}

	public Correspondente getCorrespondente() {
		return correspondente;
	}

	public void setCorrespondente(Correspondente correspondente) {
		this.correspondente = correspondente;
	}

	public CorrespondenteDao getCorrespondenteDao() {
		return correspondenteDao;
	}

	public void setCorrespondenteDao(CorrespondenteDao correspondenteDao) {
		this.correspondenteDao = correspondenteDao;
	}

	public TipoSolicitacao getTipoSolicitacao() {
		return tipoSolicitacao;
	}

	public void setTipoSolicitacao(TipoSolicitacao tipoSolicitacao) {
		this.tipoSolicitacao = tipoSolicitacao;
	}

	public StatusSolictacaoDao getStatusSolictacaoDao() {
		return statusSolictacaoDao;
	}

	public void setStatusSolictacaoDao(StatusSolictacaoDao statusSolictacaoDao) {
		this.statusSolictacaoDao = statusSolictacaoDao;
	}

	public SolicitacaoDao getSolicitacaoDao() {
		return solicitacaoDao;
	}

	public void setSolicitacaoDao(SolicitacaoDao solicitacaoDao) {
		this.solicitacaoDao = solicitacaoDao;
	}

	public String getStatusexterno() {
		return statusexterno;
	}

	public void setStatusexterno(String statusexterno) {
		this.statusexterno = statusexterno;
	}

	public ProcessoCPPRODAO getProcessoCPPRODAO() {
		return processoCPPRODAO;
	}

	public void setProcessoCPPRODAO(ProcessoCPPRODAO processoCPPRODAO) {
		this.processoCPPRODAO = processoCPPRODAO;
	}

	public ProcessoCPPRO getProcessoCPPRO() {
		return processoCPPRO;
	}

	public void setProcessoCPPRO(ProcessoCPPRO processoCPPRO) {
		this.processoCPPRO = processoCPPRO;
	}

	/**
	 * Este método salva a a solicitacao nova para que possa ser incluidos os
	 * arquivos
	 */
	public void AtualizaNovaSolicitacao() {

		FacesContext.getCurrentInstance();
		// Cria a solicitacao nova sempre
		solicitacaonova = new Solicitacao();
		comarca = comarcaDao.trazcomarca(idcomarca);
		orgao = orgaoDao.trazorgao(idorgao);
		enviosolicitacao = enviodesolicitacaoDao.trazenviodesolicitacao(enviodesolicitacao);
		Processo processo2 = new Processo();
		// Verifica se o processo ja nao esta cadastrado na base se tiver nao
		// cadastra
		processo2 = processoDao.trazprocesso(processo.getLocalizacao());
		try {
			if (processo2 == null) {
				processo.setComarca(comarca);
				processo.setOrgao(orgao);
				processo.setNumorgao(idnumerocomarca);
				processoDao.Salvar(processo);
			} else {
				processo = processo2;
			}

		} catch (Exception e) {
			processo.setComarca(comarca);
			processo.setOrgao(orgao);
			processo.setNumorgao(idnumerocomarca);
			processoDao.Salvar(processo);
		}

		renumeracao2 = renumeracaoDao.trazrenumeracao(tiposolictacao, idcorrespondente, idrenumbusca);
		solicitacaonova.setProcesso(processo);
		solicitacaonova.setDatasolictacao(getDatasolicitacao());
		solicitacaonova.setDataprazo(getDatarealizacao());
		solicitacaonova.setInstrucoes(getInstrucoes());
		statusSolicitacao = statusSolictacaoDao.trazunico(1);
		solicitacaonova.setEnviosolicitacao(enviosolicitacao);
		solicitacaonova.setStatusSolicitacao(statusSolicitacao);
		solicitacaonova.setComarca(comarca);
		solicitacaonova.setRenumeracao(renumeracao2);
		solicitacaonova.setHoraudiencia(horaaudiencia);
		solicitacaonova.setEnviosolicitacao(enviosolicitacao);
		// solicitacaonova.setPago("NAO");
		if (solicitacaonova.getComarca() == null) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Erro ao salvar a solicitação coloque a comarca.", ""));

		}

		try {
			solicitacaoDao.Salvar(solicitacaonova);
		} catch (Exception e) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro ao alterar a solicitação.", ""));
		}

		horaaudiencia = "";
		if (session.getMaxInactiveInterval() > 1000) {

		}

	}

	/**
	 * Altera a proposta de accordo na tela de alterar a solicitacao
	 * 
	 * 
	 */
	public String AlteraAcordo() {
		try {
			if (idproacordo.equals(false)) {
				valordaalcada = 0;
			}
			alterarsolicitacao.setValordaalcada(valordaalcada);
			alterarsolicitacao.setPropostaacordo(idproacordo);
			alterarsolicitacao.setGrupo(idgrupo);

			solicitacaoDao.Alterar(alterarsolicitacao);

			// FacesContext.getCurrentInstance();
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Alteração feita com sucesso.", ""));
			return "/alterasolicitacao.jsf";
		} catch (Exception e) {
			// FacesContext.getCurrentInstance();
			context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro ao alterar a solicitação nova.", ""));
			// TODO: handle exception
			return "/alterasolicitacao.jsf";
		}

	}
	//

	/**
	 * Altera a a lide da solicitação
	 * 
	 * 
	 */
	public String AlteraLide() {
		try {
			context = FacesContext.getCurrentInstance();
			alterarsolicitacao.setLide(lide);
			solicitacaoDao.Alterar(alterarsolicitacao);
			Processo proce = new Processo();
			// procbusca = processoDao.trazprocesso(processo.getLocalizacao());
			proce = processoDao.trazprocesso(alterarsolicitacao.getProcesso().getLocalizacao());
			processoDao.Alterar(proce);
			// FacesContext.getCurrentInstance();
			//
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Lide alterada com suceso.", ""));
			return "/alterarsolicitacao.jsf";
		} catch (Exception e) {
			// FacesContext.getCurrentInstance();
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro ao alterar a Lide", ""));
			return "/alterarsolicitacao.jsf";
			// TODO: handle exception
		}

	}

	/**
	 * Verifica se existe nova solicitação
	 * 
	 * @return
	 */
	public String NovaSoli() {
		/**
		 * Verifica se o usuario está logado antes de requisistar uma nova solicitação
		 */
		// FacesContext context = FacesContext.getCurrentInstance();

		if (idusuario == 0) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Selecione o seu nome ou saia e entre novamente no sistema.", ""));
			return "/novasolicitacao";

		}

		// Seta as variaveis para uma nova solicitacao

		LimpaListagens();

		return "/processo/processocpj.jsf";
	}

	/**
	 * Cadastrar solicitacao e envia a notificacao via email para o correspondente
	 * 
	 */
	public String Cadastrarsolicitacao() {
		// FacesContext context = FacesContext.getCurrentInstance();
		/**
		 * Verifica o idsuario
		 */
		try {
			if (idusuario == 0) {
				context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Selecione o usuario na busca ou saia e entre novamente no sistema.", ""));
				return "/novasolicitacao";
			}

		} catch (Exception e) {
			// Seta a variavel para corrige o erro na proxima vez
			idusuario = 0;
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Selecione o usuario na busca ou saia e entre novamente no sistema.", ""));
			return "/novasolicitacao";
		}

		/**
		 * Cria a solcitação nova sempre
		 */

		try {
			if (solicitacaonova.getIdsolicitacao() != null) {
				context = FacesContext.getCurrentInstance();
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "A Solictação ja foi registrada.", ""));
				return "/novasolicitacao";
			}

		} catch (Exception e) {
			solicitacaonova = new Solicitacao();
		}

		/**
		 * Aqui verifica o colaborador
		 *
		 */

		comarca = comarcaDao.trazcomarca(idcomarca);
		orgao = orgaoDao.trazorgao(idorgao);
		enviosolicitacao = enviodesolicitacaoDao.trazenviodesolicitacao(enviodesolicitacao);
		Processo processo2 = new Processo();
		Historico historicosoli = new Historico();

		/**
		 * Verifica se a comarca esta vazia
		 */
		if (idnumerocomarca.equals(0)) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "o campo número do orgão está vazio.", ""));
			return "/novasolicitacao";
		}
		/**
		 * Verifica o orgao
		 */
		if (idorgao.equals(0)) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Orgão está vazio", ""));
			return "/novasolicitacao";
		}
		/**
		 * Verifica o correspondente
		 */
		if (idcorrespondente.equals(0)) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecione um correspondente.", ""));
			return "/novasolicitacao";
		}
		/**
		 * Verifica as instruções da solicitação se esta vazio
		 */
		if (instrucoes.equals("")) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "O campo de instrução está vazio.", ""));
			return "/novasolicitacao";

		}
		/**
		 * Verifica se o email esta vazio
		 */

		if (emaildeenvio.equals("")) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Selecione um email para o envio da solicitação.", ""));
			return "/novasolicitacao";

		}

		/**
		 * Verifica se e audiencia para validar os campos
		 */

		if (tiposolictacao.equals(0)) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecioneo tipo de solicitação.", ""));
			return "/novasolicitacao";
		}

		/**
		 * Verifica se a banca esta ok
		 */
		if (idbanca.equals(0)) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Selecioneo uma banca para cadastrar a nova solicitação .", ""));
			return "/novasolicitacao";
		}

		bancaProcesso = bancaProcessoDao.trazunico(idbanca);

		/**
		 * Verifica os tipos de solicitação
		 */
		if (tiposolictacao == 1 || tiposolictacao == 2 || tiposolictacao == 3 || tiposolictacao == 4
				|| tiposolictacao == 5 || tiposolictacao == 6 || tiposolictacao == 15) {
			if (horaaudiencia.equals("")) {
				context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hora está vazia.", ""));
				return "/novasolicitacao";

			}
			if (enviodesolicitacao.equals(0)) {
				context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecione o envio de.", ""));
				return "/novasolicitacao";
			}
		}

		/***
		 * Traz de processo
		 */

		try {
			processo2 = new Processo();
			processo2 = processoDao.trazprocesso(processo.getLocalizacao());
			Converte cou = new Converte();
			if (processo2 == null) {
				processo.setComarca(comarca);
				processo.setOrgao(orgao);
				processo.setNumeroprocessopesq(cou.traz(processo.getNumeroprocesso()));
				processo.setNumorgao(idnumerocomarca);

				// verifica se for santander salvara so com uma quant 1 para so
				// fazer uma vez
				processo.setQuantsoli(2);
				/**
				 * Era da grea do santander if (processo.getAdverso().contains("SANTANDER")) {
				 * processo.setQuantsoli(1); } else { processo.setQuantsoli(2); }
				 */
				processoDao.Salvar(processo);
			} else {
				processo = processo2;
			}
		} catch (Exception e) {

			context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error ao cadastrar o processo na base.!!!", ""));

			// processo.setComarca(comarca);
			// processo.setOrgao(orgao);
			// processo.setLide(lide);
			// processo.setNumorgao(idnumerocomarca);
			// processoDao.Alterar(processo);

		}
		// Verifica se ja foi feita duas diliencgias na data atual
		/**
		 * if (VerfeitaNoDiaTipoDiligencia() == true) { context =
		 * FacesContext.getCurrentInstance(); context.addMessage(null, new
		 * FacesMessage(FacesMessage.SEVERITY_ERROR, "A solicitação ja foi feita duas
		 * dilegencias na data de hoje", "")); return "/novasolicitacao";
		 * 
		 * }
		 */
		renumeracao2 = null;
		renumeracao2 = renumeracaoDao.trazrenumeracao(tiposolictacao, idcorrespondente, enviodesolicitacao);

		/**
		 * Verifica se o correspondente tem valor cadastrado para realizar a solicitação
		 */
		if (renumeracao2 == null) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"O Correspondente não possui o valor cadastrado para este tipo de solicitação favor em contato com o AD",
					""));
			return "/novasolicitacao";
		}

		/**
		 * Verifica se o valor esta ativo
		 */

		if (!renumeracao2.isAtivo()) {

			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"O valor cadastrado da solicitação não está ativo para fazer a solicitação", ""));
			return "/novasolicitacao";

		}

		/**
		 * Verifica se o correspondete esta ativo na base
		 */
		if (renumeracao2.getTipoSolicitacaoCorrespondente().getCorrespondente().isAtivo() == false) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"O Correspondente não está ativo para realizar a socilitação entre em contato com o seu coordenador.",
					""));
			return "/novasolicitacao";
		}

		// Pega o status da solicitação

		statusSolicitacao = statusSolictacaoDao.trazunico(1); // pega o status
		/*
		 * Aqui alimenta os dados da nova solicitacao;
		 */

		/**
		 * Aqui verifica se o processo esta correto com os dados ou vazio
		 */
		if (processo.equals(null)) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"O processo contém erros de dados verifique antes de fazer a solicitação.", ""));
			return "/novasolicitacao";
		}

		/**
		 * Aqui verifica se pode fazer processo eletronico
		 */
		try {

			if (processo.getProceletronico().equals("E") && (tiposolictacao == 13 || tiposolictacao == 14)) {
				context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"O processo e eletronico não pode ser feito uma diligência do tipo copia parcial e integral favor contactar o coordenador.!!!",
						""));
				return "/novasolicitacao";
			}

		} catch (Exception e) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"O processo e eletronico não contém dados corretos se e eletronico ou não entre em contato com o seu coordenador.!!!",
					""));
		}

		/****
		 * Verifica o processo na base tem mais de 3 audiencias caso tenha somente com
		 * autorização do Adminsitrador" Era 3 agora so pode fazer duas agora
		 ****/
		if (renumeracao2.getTipoSolicitacaoCorrespondente().getTipoSolicitacao().getEspecie().equals("AUDIENCIA")) {

			if (Verquantprocesso() == true && processo.getQuantsoli() == 2) {
				context = FacesContext.getCurrentInstance();
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Não pode fazer mais de 2 audiências nesse processo." + "\nQuantidade: "
										+ receberepitadas.size(),
								""));
				return "/novasolicitacao";
			}
			/**
			 * A rega foi excluida a mando de LG em 04/07/2019
			 * 
			 * 
			 * if (VerquantprocessoSant() == true && processo.getQuantsoli() == 1) {
			 * 
			 * context = FacesContext.getCurrentInstance(); context.addMessage(null, new
			 * FacesMessage(FacesMessage.SEVERITY_ERROR, "Não pode fazer mais de 1 audiência
			 * nesse processo do Santander." + "\nQuantidade: " + receberepitadas.size(),
			 * "")); return "/novasolicitacao";
			 * 
			 * }
			 **/

			/**
			 * A solicitação ja foi feita na data de hoje
			 */
			/**
			 * if (VerfeitaNoDiaAudiencia() == true) {
			 * 
			 * context = FacesContext.getCurrentInstance(); context.addMessage(null, new
			 * FacesMessage(FacesMessage.SEVERITY_ERROR, "Ja foi feita duas audiências na
			 * data de hoje", "")); return "/novasolicitacao";
			 * 
			 * }
			 */
			/**
			 * Verifica a lide se for sim nao deixa fazer a solicitação novamente
			 */

			try {
				Processo procbusca = new Processo();
				// Faz a busca da lide
				procbusca = processoDao.trazprocesso(processo.getLocalizacao());

				if (Verlide() == true) {
					context = FacesContext.getCurrentInstance();
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Não pode fazer mais de 2 audiência nesse processo - HOUVE JUGALMETO ANTECIPADO DA LIDE.",
							""));
					return "/novasolicitacao";
				}
				// Se haver erro deve ter processos orfãos

			} catch (Exception e) {
				context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Error fazer solicitação favor entrar em contato com o Suporte para corrigir o erro", ""));
				return "/novasolicitacao";
			}
			/**
			 * Ver amoedo se fez tres
			 */
			if (renumeracao2.getTipoSolicitacaoCorrespondente().getCorrespondente().getIdcorrespondente() == 57
					|| renumeracao2.getTipoSolicitacaoCorrespondente().getCorrespondente().getIdcorrespondente() == 58
					|| renumeracao2.getTipoSolicitacaoCorrespondente().getCorrespondente().getIdcorrespondente() == 60
					|| renumeracao2.getTipoSolicitacaoCorrespondente().getCorrespondente().getIdcorrespondente() == 87
					|| renumeracao2.getTipoSolicitacaoCorrespondente().getCorrespondente().getIdcorrespondente() == 90
					|| renumeracao2.getTipoSolicitacaoCorrespondente().getCorrespondente()
							.getIdcorrespondente() == 91) {

				vercevero = VerquantprocessoAmoedo();

			} else {
				vercevero = false;
			}

		}

		/**
		 * Seta as classes com os dados
		 * 
		 * 
		 */
		// Cria um objeto usuario apartir so usuario logado
		Usuario usuariologado = new Usuario();
		usuariologado = usuarioDao.trazusuario(usuario.getLogin());
		solicitacaonova.setProcesso(processo);
		solicitacaonova.setDatasolictacao(getDatasolicitacao());
		solicitacaonova.setDataprazo(getDatarealizacao());
		solicitacaonova.setInstrucoes(getInstrucoes());
		solicitacaonova.setStatusSolicitacao(statusSolicitacao);
		solicitacaonova.setComarca(comarca);
		solicitacaonova.setRenumeracao(renumeracao2);
		solicitacaonova.setHoraudiencia(horaaudiencia);
		solicitacaonova.setEnviosolicitacao(enviosolicitacao);
		solicitacaonova.setEmailenvio(emaildeenvio);
		solicitacaonova.setUsuario(usuariologado); // Aqui era o usuario
		solicitacaonova.setObservacao("");
		solicitacaonova.setComplemento("");
		solicitacaonova.setJustificativa("");

		// Verifica se cevero scalpo fez tres
		if (vercevero == true) {
			solicitacaonova.setValor(0);
		} else {
			solicitacaonova.setValor(renumeracao2.getValor());
		}
		vercevero = false;
		solicitacaonova.setPago("NAO");
		solicitacaonova.setBancaProcesso(bancaProcesso);
		solicitacaonova.setGrupo(idgrupo); // Grupo
		solicitacaonova.setPropostaacordo(idproacordo);
		solicitacaonova.setValordaalcada(valordaalcada);
		solicitacaonova.setAudinterna(false);
		solicitacaonova.setLide(lide);
		solicitacaonova.setAvaliacaonota(0);
		solicitacaonova.setTextoavaliacao("");

		if (solicitacaonova.getComarca() == null) {

			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro interno ao salvar a solicitação favor selecione o estado e a comarca novamente.", ""));
			if (idcomarca > 0) {
				comarca = comarcaDao.trazcomarca(idcomarca);
				solicitacaonova.setComarca(comarca);
			}
			return "/novasolicitacao";

		}

		/**
		 * Salva no banco de dados
		 */
		try {
			solicitacaonova = solicitacaoDao.Salvar(solicitacaonova);
			processo.setQuantsoli(2);
			processoDao.Alterar(processo);
			liberaarqsoli = true;

		} catch (Exception e) {
			// solicitacaoDao.Cancelar();
			liberaarqsoli = false;
			context = FacesContext.getCurrentInstance();
			System.out.println("ERRO :" + e.getMessage());
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"2 - Erro interno ao salvar a solicitação favor sair completamente do sistema fechando o navegador e refaça novamente "
							+ e.getMessage(),
					""));
			return "/novasolicitacao";
		}

		/**
		 * Verifica se realmente a solictação foi salva
		 */
		if (solicitacaoDao.verifica(solicitacaonova.getIdsolicitacao()) == false) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"1 - Erro interno ao salvar a solicitação favor sair completamente do sistema fechando o navegador e refaça novamente ",
					""));
			return "/novasolicitacao";
		}

		if (tiposolictacao == 1 || tiposolictacao == 2 || tiposolictacao == 3 || tiposolictacao == 4
				|| tiposolictacao == 5 || tiposolictacao == 6 || tiposolictacao == 15) {
			envionasolictaco = "Envio de: " + solicitacaonova.getEnviosolicitacao().getTipoenvio();

		} else {
			envionasolictaco = "";
		}

		horaaudiencia = "";
		idsolicitacaobusca = solicitacaonova.getIdsolicitacao();
		/**
		 * Salva um novo historico da nova solicitação
		 */
		historicosoli.setSolicitacao(solicitacaonova);
		historicosoli.setDatahistorico(new Date());
		historicosoli.setUsuario(usuario); // Era usuario mas perdia o objeto
		historicosoli.setStatusSolicitacao(solicitacaonova.getStatusSolicitacao()); // Era
																					// o
																					// status
																					// da
		// solicitação
		historicosoli.setRenumeracao(solicitacaonova.getRenumeracao()); // Pega

		// Log desativado em 29/05/2019
		// Salva o log
		// SalvarLogSys(solicitacaonova, "Solicitação Original: ");

		// a
		// renumeracao
		// diretamente

		/**
		 * Tentando tratar um erro genérico mas ninguem me deixa programar poha!!!!
		 */

		try {
			historicoDao.Salvar(historicosoli);
			context = FacesContext.getCurrentInstance();
			context.addMessage("msg5", new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Solicitação elaborada e salva com sucesso. ID - " + solicitacaonova.getIdsolicitacao(), ""));

			historicosoli = null; // Seta o obejto
		} catch (Exception e) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"2 - Erro interno ao salvar a solicitação favor sair completamente do sistema fechando o navegador e refaça novamente ",
					""));
			return "/novasolicitacao";
		}

		if (solicitacaonova.isPropostaacordo() == true) {

			vaiacordo = "Proposta de Acordo: SIM" + "\n" + "Valor da Alçada: R$"
					+ String.format("%.2f", solicitacaonova.getValordaalcada());
		} else if (solicitacaonova.isPropostaacordo() == false) {
			vaiacordo = "Proposta de Acordo: NÃ0" + "\n";
		}

		try {

			Usuario novo = new Usuario();
			novo = usuarioDao.trazusuarioCorrespondente(solicitacaonova.getRenumeracao()
					.getTipoSolicitacaoCorrespondente().getCorrespondente().getIdcorrespondente());

			nova_soli_salva = true;
			textoemail = "";
			textoemail = "---------------------------------------------------------------------------------------------------\n"
					+ "Id Solicitacao: " + solicitacaonova.getIdsolicitacao() + "\n" + "Número do Processo: "
					+ solicitacaonova.getProcesso().getNumeroprocesso() + "\n" + "Parte contrária: "
					+ solicitacaonova.getProcesso().getParte() + "\n" + "Cliente: "
					+ solicitacaonova.getProcesso().getAdverso() + "\n" + "Tipo de Solicitação: "

					+ solicitacaonova.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao()
							.getEspecie()
					+ " - "
					+ solicitacaonova.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao().getTipo()
					+ "\n" + "Colaborador: "
					+ solicitacaonova.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente().getNome()
					+ "\n" + "Login do Colaborador: " + novo.getLogin() + "\n" + "Data da solicitação: "
					+ dateFormat.format(solicitacaonova.getDatasolictacao()) + "\n"
					// + vaiacordo
					// + "\n"

					+ "Comarca: " + solicitacaonova.getComarca().getNome() + "\n" + "Localização: "
					+ solicitacaonova.getProcesso().getNumorgao() + " - "
					+ solicitacaonova.getProcesso().getOrgao().getDescricao() + "\n"
					// Nome: nelson
					// Modulo:
					// Data: 21/06/2015 Hora: 11:15:46
					// Descrição:
					+ envionasolictaco + "\n"

					+ "Prazo Final: " + dateFormat.format(solicitacaonova.getDataprazo()) + " - Hora: "
					+ solicitacaonova.getHoraudiencia() + "\n" + "Instruções: " + "\n" + solicitacaonova.getInstrucoes()
					+ "\n" + "Status da Solicitação: " + solicitacaonova.getStatusSolicitacao().getStatus() + "\n"
					+ "---------------------------------------------------------------------------------------------------\n";

			enviaEmail.Enviar(solicitacaonova, usuario.getEmailprincipal(), solicitacaonova.getEmailenvio(),
					solicitacaonova.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente()
							.getEmailsecundario(),
					usuario.getEmailresponsavel(),
					solicitacaonova.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente().getNome(),
					"", textoemail, "** AVISO DE SOLICITAÇÃO ** ID - " + solicitacaonova.getIdsolicitacao());
			context = FacesContext.getCurrentInstance();
			context.addMessage("msg5", new FacesMessage(FacesMessage.SEVERITY_INFO,
					"O e-mail foi enviado com sucesso para o colaborador.", ""));

		} catch (Exception e) {

			try {
				nova_soli_salva = true;

				enviaEmail.EnviarDeNovo(solicitacaonova, usuario.getEmailprincipal(), solicitacaonova.getEmailenvio(),
						solicitacaonova.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente()
								.getEmailsecundario(),
						usuario.getEmailresponsavel(),
						solicitacaonova.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente()
								.getNome(),
						"", textoemail, "** AVISO DE SOLICITAÇÃO ** ID - " + solicitacaonova.getIdsolicitacao());
				context = FacesContext.getCurrentInstance();
				context.addMessage("msg5", new FacesMessage(FacesMessage.SEVERITY_INFO,
						"O e-mail foi enviado com sucesso para o colaborador.", ""));

			} catch (Exception e2) {
				nova_soli_salva = true;
				// TODO Auto-generated catch block
				context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"E-Mail não foi enviado com sucesso" + e.getMessage(), ""));

				// TODO: handle exception
			}

		}

		return "/novasolicitacao";
	}

	/**
	 * 
	 * SALVA A ALTERACAO REALIZADA NA PAGINA ALTERANOVASOLICITACAO- BOTAO REGISTRAR
	 * 16/06/2014 x3
	 * 
	 **/

	public String Cadastranovasolialterada() {
		FacesContext context = FacesContext.getCurrentInstance();

		if (idcomarca == 0) {
			comarca = comarcaDao.trazcomarca(alterarsolicitacao.getComarca().getIdcomarca());
		} else {
			comarca = comarcaDao.trazcomarca(idcomarca);
		}

		if (idorgao == 0) {
			orgao = orgaoDao.trazorgao(alterarsolicitacao.getProcesso().getOrgao().getIdorgao());
		} else {
			orgao = orgaoDao.trazorgao(idorgao);
		}

		if (idbancabusca == 0) {
			bancaProcesso = bancaProcessoDao.trazunico(alterarsolicitacao.getBancaProcesso().getIdbanca());
		} else {
			bancaProcesso = bancaProcessoDao.trazunico(idbancabusca);
		}

		processo = alterarsolicitacao.getProcesso();

		enviosolicitacao = enviodesolicitacaoDao.trazenviodesolicitacao(enviodesolicitacao);
		processo.setComarca(comarca);
		processo.setOrgao(orgao);
		processo.setNumorgao(numorgao);

		/**
		 * Teste
		 */
		processoDao.Alterar(processo);

		Processo processo2 = new Processo();
		Historico historicosoli = new Historico();
		processo2 = processoDao.trazprocesso(alterarsolicitacao.getProcesso().getLocalizacao());

		try {
			Converte cou = new Converte();
			if (processo2 == null) {
				processo.setComarca(comarca);
				processo.setOrgao(orgao);
				processo.setNumeroprocessopesq(cou.traz(processo.getNumeroprocesso()));
				processo.setNumorgao(numorgao);

				processoDao.Salvar(processo);
			} else {
				processo = processo2;
			}
		} catch (Exception e) {
			processo.setComarca(comarca);
			processo.setOrgao(orgao);
			processo.setNumorgao(idnumerocomarca);
			processoDao.Alterar(processo);

		}

		// Verifica se o processo ja nao esta cadastrado na base se tiver nao
		// cadastra

		/**
		 * Seta as classes com os dados
		 */

		alterarsolicitacao.setProcesso(processo);

		// alterarsolicitacao.setDatasolictacao(getDatasolicitacao());
		alterarsolicitacao.setDataprazo(alterarsolicitacao.getDataprazo()); // getDatarealizacao()
		alterarsolicitacao.setInstrucoes(getInstrucoes());
		alterarsolicitacao.setEnviosolicitacao(enviosolicitacao);
		// alterarsolicitacao.setStatusSolicitacao(statusSolicitacao);
		alterarsolicitacao.setComarca(comarca);

		if (alterarsolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao().getEspecie()
				.equals("DILIGENCIA")) {
			alterarsolicitacao.setHoraudiencia("");
			alterarsolicitacao.setLide("");
		} else if (alterarsolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao()
				.getEspecie().equals("AUDIENCIA")) {
			alterarsolicitacao.setHoraudiencia(horaaudiencia); // horaaudiencia
			alterarsolicitacao.setLide(lide);
		}

		alterarsolicitacao.setEnviosolicitacao(enviosolicitacao);
		alterarsolicitacao.setEmailenvio(emaildeenvio);

		if (usuario == null) {
			try {
				usuario = usuarioDao.trazusuario(idusuario);
			} catch (Exception e) {
				context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Erro ao acessa dados." + "\n" + e.getMessage(), ""));
				// TODO: handle exception
			}

		}

		// NAo vou alterar o usuario para nao modificar os dados oriiginais da
		// solicitacao
		// alterarsolicitacao.setUsuario(usuario);

		alterarsolicitacao.setBancaProcesso(bancaProcesso);

		if (alterarsolicitacao.getStatusSolicitacao().getIdstatus() == 1) {

			alterarsolicitacao.setObservacao("");
			alterarsolicitacao.setComplemento("");
			alterarsolicitacao.setJustificativa("");
		}
		alterarsolicitacao.setGrupo(idgrupo); // Grupo
		alterarsolicitacao.setPropostaacordo(idproacordo);

		// Muda altomaticamente para nao salvar o valor da alçada caso não haja
		// envio de acordo
		if (idproacordo == false) {
			valordaalcada = 0;
		}
		alterarsolicitacao.setValordaalcada(valordaalcada);

		try {
			if (alterarsolicitacao.getProcesso().getComarca().getIdcomarca().equals(0)) {

				context = FacesContext.getCurrentInstance();
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "o campo número do orgão está vazio.", ""));
				return "/alteranovasolicitacao";
			}

		} catch (Exception e) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "o campo número do orgão está vazio ou nulo.", ""));
			return "/alteranovasolicitacao";
			// TODO: handle exception
		}
		try {
			if (alterarsolicitacao.getProcesso().getComarca().getIdcomarca().equals(0)) {

				context = FacesContext.getCurrentInstance();
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "o campo número do orgão está vazio.", ""));
				return "/alteranovasolicitacao";
			}

		} catch (Exception e) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "o campo número do orgão está vazio. ou nulo", ""));
			return "/alteranovasolicitacao";
		}
		// TODO: handle exception

		try {
			if (alterarsolicitacao.getProcesso().getComarca().getIdcomarca().equals(0)) {

				context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "A comarca está vazia.", ""));
				return "/alteranovasolicitacao";
			}

		} catch (Exception e) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "A comarca está vazia ou nulo.", ""));
			return "/alteranovasolicitacao";
			// TODO: handle exception
		}

		try {
			if (alterarsolicitacao.getProcesso().getComarca().getIdcomarca().equals(0)) {

				context = FacesContext.getCurrentInstance();
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "o campo número do orgão está vazio.", ""));
				return "/alteranovasolicitacao";
			}

		} catch (Exception e) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "o campo número do orgão está vazio ou nulo.", ""));
			return "/alteranovasolicitacao";
		}
		// TODO: handle exception

		try {
			if (alterarsolicitacao.getProcesso().getOrgao().getIdorgao().equals(0)) {
				context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Orgão está vazio", ""));
				return "/alteranovasolicitacao";
			}
		} catch (Exception e) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Orgão está vazio ou nulo", ""));
			return "/alteranovasolicitacao";

			// TODO: handle exception
		}

		if (getInstrucoes().equals("")) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "O campo de instrução está vazio.", ""));
			return "/alteranovasolicitacao";

		}

		if (alterarsolicitacao.getEmailenvio().equals("")) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Selecione um email para o envio da solicitação.", ""));
			return "/alteranovasolicitacao";

		}

		/**
		 * Verifica se e audiencia para validar os campos
		 */

		if (tiposolictacao.equals(0)) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecioneo tipo de solicitação.", ""));
			return "/alteranovasolicitacao";
		}

		// AUDIENCIA SALVA HORA E ENVIO DE 24/06/2014

		if (tiposolictacao == 1 || tiposolictacao == 2 || tiposolictacao == 3 || tiposolictacao == 4
				|| tiposolictacao == 5 || tiposolictacao == 6 || tiposolictacao == 15) {

			if (horaaudiencia.equals("")) {
				context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hora está vazia.", ""));
				return "/alteranovasolicitacao";
			}
		}

		// DILIGENCIA ZERA A HORA E O ENVIO DE: 24/06/2014
		if (tiposolictacao == 12 || tiposolictacao == 8 || tiposolictacao == 11 || tiposolictacao == 7
				|| tiposolictacao == 10 || tiposolictacao == 13 || tiposolictacao == 14 || tiposolictacao == 9) {

			horaaudiencia = "";
			enviodesolicitacao = 0;
			alterarsolicitacao.setEnviosolicitacao(null);
		}

		/****
		 * if (alterarsolicitacao.getEnviosolicitacao().equals(0)) { context =
		 * FacesContext.getCurrentInstance(); context.addMessage(null, new FacesMessage(
		 * FacesMessage.SEVERITY_ERROR, "Selecione o envio de.", "")); return
		 * "/alteranovasolicitacao"; }
		 */

		renumeracao2 = null;
		renumeracao2 = renumeracaoDao.trazrenumeracao(tiposolictacao, idcorrespondente, enviodesolicitacao);

		/**
		 * Verifica se o correspondente tem valor cadastrado para realizar a solicitação
		 */

		if (renumeracao2 == null) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"O Correspondente não possui o valor cadastrado da solicitação", ""));
			return "/alteranovasolicitacao";
		}
		/**
		 * Verifica se o correspondete esta ativo na base
		 */
		if (renumeracao2.getTipoSolicitacaoCorrespondente().getCorrespondente().isAtivo() == false) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"O Correspondente não está ativo para realizar a socilitação entre em contato com o seu coordenador.",
					""));
			return "/alteranovasolicitacao";
		}

		// Pega o status da solicitação

		// statusSolicitacao = statusSolictacaoDao.trazunico(1); // pega o
		// status

		/*
		 * Aqui alimenta os dados da nova solicitacao;
		 */

		/**
		 * Aqui verifica se o processo esta correto com os dados ou vazio
		 */
		if (processo.equals(null)) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"O processo contém erros de dados verifique antes de fazer a solicitação.", ""));
			return "/alteranovasolicitacao";

		}
		/**
		 * Aqui verifica se pode fazer processo eletronico
		 */

		try {

			if (processo.getProceletronico().equals("E") && (tiposolictacao == 7 || tiposolictacao == 8
					|| tiposolictacao == 9 || tiposolictacao == 10 || tiposolictacao == 11 || tiposolictacao == 12
					|| tiposolictacao == 13 || tiposolictacao == 14)) {
				context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"O processo e eletronico não pode ser feito uma diligência favor contactar o coordenador.!!!",
						""));
				return "/alteranovasolicitacao";
			}

		} catch (Exception e) {
			// solicitacaoDao.Cancelar();
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"O processo e eletronico não contém dados corretos se e eletronico ou não entre em contato com o seu coordenador.!!!",
					""));
		}

		if (alterarsolicitacao.getStatusSolicitacao().getIdstatus() == 1) {
			// || (alterarsolicitacao.getStatusSolicitacao().getIdstatus() ==
			// 4)) {
			alterarsolicitacao.setRenumeracao(renumeracao2); // Aqui muda o
			alterarsolicitacao.setValor(renumeracao2.getValor());
			alterarsolicitacao.setPago("NAO");
			alterarsolicitacao.setHoraudiencia(horaaudiencia);
		}
		try {

			alterarsolicitacao = solicitacaoDao.AlterarNovaSoli(alterarsolicitacao);
			alterasolicitacaodenovo = false;
			/////////////////////////////////////
			// Anulei em 20/05/2019 para nao comsumir recurso do banco de dados
			// SalvarLogSys(alterarsolicitacao, "Solicitação Alterada: ");

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao alterar s solictação", ""));
		}
		// horaaudiencia = "";
		idsolicitacaobusca = alterarsolicitacao.getIdsolicitacao();
		/**
		 * Documentei para nao alterar o historico da mesma Salva um novo historico da
		 * nova solicitação
		 * 
		 * historicosoli.setSolicitacao(alterarsolicitacao);
		 * historicosoli.setDatahistorico(new Date());
		 * historicosoli.setUsuario(usuario); // Era usuario mas perdia o objeto
		 * historicosoli.setStatusSolicitacao(alterarsolicitacao
		 * .getStatusSolicitacao()); // Era o status da solicitação
		 * historicosoli.setRenumeracao(alterarsolicitacao.getRenumeracao()); // Pega
		 **/
		// a
		// renumeracao
		// diretamente

		/**
		 * Tentando tratar um erro genérico mas ninguem me deixa programar poha!!!!
		 */
		try {
			historicoDao.Salvar(historicosoli);
			historicosoli = null; // Seta o obejto

		} catch (Exception e) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"2 - Erro interno ao salvar a solicitação favor sair completamente do sistema fechando o navegador e refaça novamente ",
					""));
			return "/alteranovasolicitacao";
		}

		// Envia a prosposta de acordo
		if (alterarsolicitacao.isPropostaacordo() == true) {

			vaiacordo = "Proposta de Acordo: SIM" + "\n" + "Valor da Alçada: R$"
					+ String.format("%.2f", alterarsolicitacao.getValordaalcada());
		} else if (alterarsolicitacao.isPropostaacordo() == false) {
			vaiacordo = "Proposta de Acordo: NÃ0";
		}

		try {
			textoemail = "";
			textoemail = "---------------------------------------------------------------------------------------------------\n"
					+ "Id Solicitacao: " + alterarsolicitacao.getIdsolicitacao() + "\n" + "Número do Processo: "
					+ alterarsolicitacao.getProcesso().getNumeroprocesso() + "\n" + "Parte contrária: "
					+ alterarsolicitacao.getProcesso().getParte() + "\n" + "Cliente: "
					+ alterarsolicitacao.getProcesso().getAdverso() + "\n" + "Tipo de Solicitação: "
					+ alterarsolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao()
							.getEspecie()
					+ " - " + alterarsolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente()
							.getTipoSolicitacao().getTipo()
					+ "\n"
					// + vaiacordo
					// + "\n"
					+ "Comarca: " + alterarsolicitacao.getComarca().getNome() + "\n" + "Localização: "
					+ alterarsolicitacao.getProcesso().getNumorgao() + " - "
					+ alterarsolicitacao.getProcesso().getOrgao().getDescricao() + "\n" + "Data da solicitação: "
					+ dateFormat.format(alterarsolicitacao.getDatasolictacao()) + "\n" + "Prazo Final: "
					+ dateFormat.format(alterarsolicitacao.getDataprazo()) + " - Hora: "
					+ alterarsolicitacao.getHoraudiencia() + "\n" + "Instruções: " + "\n"
					+ alterarsolicitacao.getInstrucoes() + "\n" + "Status da Solicitação: "
					+ alterarsolicitacao.getStatusSolicitacao().getStatus() + "\n"
					+ "OBSERVAÇÂO: SOLICITAÇÃO ALTERADA PELO SOLICITANTE - "
					+ alterarsolicitacao.getUsuario().getLogin() + "\n"
					+ "---------------------------------------------------------------------------------------------------\n";

			enviaEmail.EnviarDeNovo(alterarsolicitacao, alterarsolicitacao.getUsuario().getEmailprincipal(),
					"nelson.seixas@cra.adv.br", "", "",
					alterarsolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente()
							.getNome(),
					"", textoemail, "** SOLICITAÇÃO ALTERADA  ** - ID " + alterarsolicitacao.getIdsolicitacao());

			context = FacesContext.getCurrentInstance();
			context.addMessage("msg5", new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Solicitação Alterada com sucesso. E-mail enviado com sucesso.", ""));
			nova_soli_salva = true;
		} catch (Exception e) {

			// TODO Auto-generated catch block
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"E-mail não foi enviado com sucesso" + e.getMessage(), ""));
		}

		return "/alteranovasolictacao";
	}

	/**
	 * Muda o estatus quando o correspondente seleciona
	 * 
	 * @return
	 */
	public String MudaStatus() {
		try {
			bnumero = unicasolicitacao.getIdsolicitacao();

			if (statusexterno.equals("CONFIRMADO")) {
				tipoUnicoMuda = new StatusSolicitacao();
				tipoUnicoMuda = statusSolictacaoDao.trazunico(4);
			} else if (statusexterno.equals("REJEITADO")) {
				tipoUnicoMuda = new StatusSolicitacao();
				tipoUnicoMuda = statusSolictacaoDao.trazunico(3);
			}

		} catch (Exception e) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecione um Status Correto", ""));
			return BuscaUnica();
		}

		bnumero = unicasolicitacao.getIdsolicitacao();
		if (statusexterno.equals("CONFIRMADO")) {
			tipoUnicoMuda = new StatusSolicitacao();
			tipoUnicoMuda = statusSolictacaoDao.trazunico(4);
		} else if (statusexterno.equals("REJEITADO")) {
			tipoUnicoMuda = new StatusSolicitacao();
			tipoUnicoMuda = statusSolictacaoDao.trazunico(3);
		} else {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecione um Status Correto", ""));
			return BuscaUnica();
		}

		unicasolicitacao.setStatusexterno(statusexterno);
		unicasolicitacao.setStatusSolicitacao(tipoUnicoMuda);
		unicasolicitacao.setJustificativa(textodarejeicao);
		// Envia a prosposta de acordo

		if (unicasolicitacao.isPropostaacordo() == true) {
			vaiacordo = "Proposta de Acordo: SIM" + "\n" + "Valor da Alçada: R$" + unicasolicitacao.getValordaalcada();
		} else if (unicasolicitacao.isPropostaacordo() == false) {
			vaiacordo = "Proposta de Acordo: NÃ0";
		}

		try {
			solicitacaoDao.Alterar(unicasolicitacao);
		} catch (Exception e1) {
			// solicitacaoDao.Cancelar();
			// TODO Auto-generated catch block
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecione um Status Correto", ""));
		}

		/**
		 * Salva o historico
		 */
		Date datahis = new Date();// Pega a data do servidor atual
		Usuario usu = new Usuario();

		// usu = usuarioDao.trazusuario(usuario.getIdusuario());
		Historico historicosoli2 = new Historico();
		historicosoli2.setSolicitacao(unicasolicitacao);
		historicosoli2.setDatahistorico(datahis);
		historicosoli2.setUsuario(usuario);// Uuario logado
		historicosoli2.setStatusSolicitacao(tipoUnicoMuda);
		historicosoli2.setRenumeracao(unicasolicitacao.getRenumeracao());
		historicoDao.Salvar(historicosoli2);
		historicosoli2 = null; // Seta tudo
		usu = null;
		String texto1 = "";
		String assunto = "";
		FacesContext context1 = FacesContext.getCurrentInstance();
		try {

			if (statusexterno.equals("CONFIRMADO")) {
				assunto = "** SOLICITAÇÃO CONFIRMADA ** ID - " + unicasolicitacao.getIdsolicitacao()
						+ " CORREPONDENTE - " + unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente()
								.getCorrespondente().getNome();

				texto1 = "---------------------------------------------------------------------------------------------------\n"
						+ "Id Solicitação: " + unicasolicitacao.getIdsolicitacao() + "\n" + "Número do Processo: "
						+ unicasolicitacao.getProcesso().getNumeroprocesso() + "\n" + "Parte contrária: "
						+ unicasolicitacao.getProcesso().getParte() + "\n" + "Cliente: "
						+ unicasolicitacao.getProcesso().getAdverso() + "\n" + "Tipo de Solicitação: "
						+ unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao()
								.getEspecie()
						+ " - " + unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente()
								.getTipoSolicitacao().getTipo()
						+ "\n"
						// + vaiacordo
						// + "\n"
						+ "Comarca: " + unicasolicitacao.getComarca().getNome() + "\n" + "Data da solicitação: "
						+ dateFormat.format(unicasolicitacao.getDatasolictacao()) + "\n" + "Prazo Final: "
						+ dateFormat.format(unicasolicitacao.getDataprazo()) + " - Hora: "
						+ unicasolicitacao.getHoraudiencia() + "\n" + "Instruções: " + "\n"
						+ unicasolicitacao.getInstrucoes() + "\n" + "Status da Solicitação: "
						+ unicasolicitacao.getStatusSolicitacao().getStatus() + "\n"
						+ "---------------------------------------------------------------------------------------------------\n"
						+ "Observção:" + "\n" + "Sua SOLICITAÇÃO/AUDIÊNCIA foi " + unicasolicitacao.getStatusexterno()
						+ "  pelo correspondente ." + unicasolicitacao.getRenumeracao()
								.getTipoSolicitacaoCorrespondente().getCorrespondente().getNome();
				/**
				 * Envia a mensagem
				 */

			}
			if (statusexterno.equals("REJEITADO")) {
				assunto = "** SOLICITAÇÃO REJEITADA ** ID - " + unicasolicitacao.getIdsolicitacao()
						+ " CORREPONDENTE - " + unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente()
								.getCorrespondente().getNome();
				texto1 = "---------------------------------------------------------------------------------------------------\n"
						+ "Id Solicitação: " + unicasolicitacao.getIdsolicitacao() + "\n" + "Número do Processo: "
						+ unicasolicitacao.getProcesso().getNumeroprocesso() + "\n" + "Parte contrária: "
						+ unicasolicitacao.getProcesso().getParte() + "\n" + "Cliente: "
						+ unicasolicitacao.getProcesso().getAdverso() + "\n" + "Tipo de Solicitação: "
						+ unicasolicitacao
								.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao().getEspecie()
						+ " - "
						+ unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao()
								.getTipo()
						+ "\n" + vaiacordo + "\n" + "Comarca: " + unicasolicitacao.getComarca().getNome() + "\n"
						+ "Data da solicitação: " + dateFormat.format(unicasolicitacao.getDatasolictacao()) + "\n"
						+ "Prazo Final: " + dateFormat.format(unicasolicitacao.getDataprazo()) + " - Hora: "
						+ unicasolicitacao.getHoraudiencia() + "\n" + "Instruções: " + "\n"
						+ unicasolicitacao.getInstrucoes() + "\n" + "Status da Solicitação: "
						+ unicasolicitacao.getStatusSolicitacao().getStatus() + "\n"
						+ "---------------------------------------------------------------------------------------------------\n"

						+ "Sua SOLICITAÇÃO/AUDIÊNCIA foi " + unicasolicitacao.getStatusexterno()
						+ "  pelo correspondente ."
						+ unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente()
								.getNome()
						+ "\n" + "Descrição da Rejeição" + "\n" + unicasolicitacao.getJustificativa();

			}

			enviaEmail.Enviar(unicasolicitacao, unicasolicitacao.getUsuario().getEmailprincipal(),
					unicasolicitacao.getEmailenvio(),
					unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente()
							.getEmailsecundario(),
					unicasolicitacao.getUsuario().getEmailresponsavel(),
					unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente().getNome(),
					"", texto1, assunto + " ID -" + unicasolicitacao.getIdsolicitacao());
			context1 = FacesContext.getCurrentInstance();
			context1.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "E-Mail foi enviado com sucesso...", ""));
		} catch (Exception e) {

			try {
				enviaEmail.EnviarDeNovo(unicasolicitacao, unicasolicitacao.getUsuario().getEmailprincipal(),
						unicasolicitacao.getEmailenvio(),
						unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente()
								.getEmailsecundario(),
						unicasolicitacao.getUsuario().getEmailresponsavel(), unicasolicitacao.getRenumeracao()
								.getTipoSolicitacaoCorrespondente().getCorrespondente().getNome(),
						"", texto1, assunto + " ID -" + unicasolicitacao.getIdsolicitacao());
				context1 = FacesContext.getCurrentInstance();
				context1.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "E-Mail foi enviado com sucesso...", ""));

			} catch (Exception e2) {
				// TODO Auto-generated catch block
				context1 = FacesContext.getCurrentInstance();
				context1.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"E-Mail não foi enviado com sucesso" + e.getMessage(), ""));
			}

		}

		return BuscaUnica();

	}

	/**
	 * Muda o status de producao para concluido assim que o correspondente aciona e
	 * aplica as regras conforme abaixo no código
	 * 
	 * @return
	 */

	public String MudaStatusGeral() {

		// Pega o contesto

		if (unicasolicitacao == null) {
			FacesContext cont1 = FacesContext.getCurrentInstance();
			cont1.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao concluir a solicitação favor sair do sistema e do navegador e entrar outra vez.", ""));
			return BuscaUnica();

		}

		/**
		 * Verifica se o arquivo de retorno ja foi colocado em caso de AUDIENCIA
		 * 27/06/2014
		 */

		if (unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao().getEspecie()
				.equals("AUDIENCIA")) {

			try {
				soliArquivoDao = null;
				soliArquivoDao = new SoliArquivoDao();
				listaentrada = soliArquivoDao.buscararquivo(unicasolicitacao.getIdsolicitacao(), "RECEBIDO");
				if ((listaentrada.size() == 0) || (listaentrada == null)) {
					FacesContext cont1 = FacesContext.getCurrentInstance();
					cont1.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Favor anexar os arquivos de retorno ( Ata de Audiência e outros arquivos pertinentes ) para ser finalizado.",
							""));
					return BuscaUnica();

				}

			} catch (Exception e) {
				FacesContext cont1 = FacesContext.getCurrentInstance();
				cont1.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao buscar arqueivos no servidor ", ""));
			}

		}

		/**
		 * Verifica se a comarca esta preenchida
		 */

		if (unicasolicitacao.getComarca() == null) {
			FacesContext cont1 = FacesContext.getCurrentInstance();
			cont1.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Favor entrar em contato com o suporte para preenchimento da comarca informando o ID da solicitação.",
					""));
			return BuscaUnica();

		}

		/**
		 * Verifica se a conclusão final da solictação esta preenchida com texto tamanho
		 * 10 caracteres
		 */
		if (observacaofinal.length() < 10) {

			FacesContext cont1 = FacesContext.getCurrentInstance();
			cont1.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Favor inserir o texto de observação antes de concluir a solicitação não podendo ser 10 caracteres.",
					""));
			return BuscaUnica();
		}

		/**
		 * Verifica para concluir somente com o formulario caso seje solicitacao
		 */
		if (unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao().getEspecie()
				.equals("AUDIENCIA")) {
			if (unicasolicitacao.getFormularioAudiencia() == null) {
				FacesContext cont1 = FacesContext.getCurrentInstance();
				cont1.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Favor preecher o formulario de audiência antes de concluir.", ""));
				return BuscaUnica();

			}

			/**
			 * Verifica se tem acordo Valor da contra proposta antes verifica se o acordo
			 * foi realizado
			 * 
			 */
			if (unicasolicitacao.isPropostaacordo() == true) {
				// Valor do iguais
				/*
				 * if (unicasolicitacao.getFormularioAudiencia().getValorproposta() ==
				 * unicasolicitacao .getFormularioAudiencia().getValoracordo()) { context =
				 * FacesContext.getCurrentInstance();
				 * 
				 * context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
				 * "O valor da contra proposta e igual o valor da contra proposta. Favor verifique o preenchimento do formulário."
				 * , "")); return BuscaUnica();
				 * 
				 * }
				 */
				// Verifica se o valor do acordo e menor quee o valor da alçada
				/*
				 * if (unicasolicitacao.getFormularioAudiencia().isAcordorealizado() == false) {
				 * if (unicasolicitacao.getFormularioAudiencia().getValorproposta() <
				 * unicasolicitacao .getValordaalcada()) { context =
				 * FacesContext.getCurrentInstance();
				 * 
				 * context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
				 * "O valor da contra proposta e menor que o valor da alçada oferecida pelo banco. Favor verifique o preenchimento do formulário."
				 * , "")); return BuscaUnica();
				 * 
				 * } }
				 */

				// Valor do acordo e me menor ou igual o valor da alçada

				/*
				 * if (unicasolicitacao.getFormularioAudiencia().isAcordorealizado() == true) {
				 * if (unicasolicitacao.getFormularioAudiencia().getValoracordo() >
				 * unicasolicitacao .getValordaalcada()) { context =
				 * FacesContext.getCurrentInstance();
				 * 
				 * context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
				 * "O valor do acordo e maior que o valor da alçada oferecida pelo banco. Favor verifique o preenchimento do formulário."
				 * , "")); return BuscaUnica();
				 * 
				 * }
				 * 
				 * }
				 */
			}

		}

		// if (!listasaida.isEmpty()) {
		bnumero = unicasolicitacao.getIdsolicitacao();
		if (unicasolicitacao.getStatusexterno().equals("CONFIRMADO"))

		{
			tipoUnicoMuda = new StatusSolicitacao();
			tipoUnicoMuda = statusSolictacaoDao.trazunico(5);

		} else if (unicasolicitacao.getStatusexterno().equals("REJEITADO")) {
			tipoUnicoMuda = new StatusSolicitacao();
			tipoUnicoMuda = statusSolictacaoDao.trazunico(3);
		}

		unicasolicitacao.setStatusSolicitacao(tipoUnicoMuda);
		unicasolicitacao.setObservacao(observacaofinal);
		// Aqui coloca a data de conclusao para ser feito o caulo de desconto no atrazo
		// do servico

		// unicasolicitacao.setDataconclusao(new Date());

		/**
		 * Salva o historico
		 */
		renumeracao2 = null;

		if (foiconvolada.equals(true)) {
			renumeracao2 = renumeracaoDao
					.trazrenumeracao(5,
							unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente()
									.getIdcorrespondente(),
							unicasolicitacao.getEnviosolicitacao().getIdenviosolicitacao());
		} else if (foiconvolada.equals(false)) {
			renumeracao2 = unicasolicitacao.getRenumeracao(); // renumeracaoDao.trazrenumeracao(unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao().getIdtiposolicitacao()
			// ,
			// unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente().getIdcorrespondente(),unicasolicitacao.getEnviosolicitacao().getIdEnviosolicitacao());
		}

		if (renumeracao2 == null) {
			FacesContext cont1 = FacesContext.getCurrentInstance();
			cont1.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao salvar entrar em contato com o suporte.", ""));
			return BuscaUnica();

		}

		/**
		 * Formula de comversao aqui aplica a regra do valores de acordo como feita a o
		 * acordo relizado - REGRA 2
		 */

		unicasolicitacao.setRenumeracao(renumeracao2);

		if (unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao()
				.getEspecie() == "AUDIENCIA") {
			if ((unicasolicitacao.getFormularioAudiencia().isAcordorealizado() == true)
					&& (unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente()
							.isAplicaregra1() == true)) {
				if (unicasolicitacao.getFormularioAudiencia().getValoracordo() > 3000.0) {
					unicasolicitacao.setValor(115);
				} else if (unicasolicitacao.getFormularioAudiencia().getValoracordo() > 2000.0
						&& unicasolicitacao.getFormularioAudiencia().getValoracordo() <= 3000.0) {
					unicasolicitacao.setValor(150);
				} else if (unicasolicitacao.getFormularioAudiencia().getValoracordo() <= 2000.0) {
					unicasolicitacao.setValor(200);
				}

			} else {

				unicasolicitacao.setValor(renumeracao2.getValor());
			}

			/**
			 * Formula de comversao aqui aplica a regra do valores de acordo como feita a o
			 * acordo relizado - REGRA 1
			 **/

			if ((unicasolicitacao.getFormularioAudiencia().isAcordorealizado() == true)
					&& (unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente()
							.isAplicaregra2() == true)) {
				if (unicasolicitacao.getFormularioAudiencia().getValoracordo() > 3000.0) {
					unicasolicitacao.setValor(165);
				} else if (unicasolicitacao.getFormularioAudiencia().getValoracordo() > 2000.0
						&& unicasolicitacao.getFormularioAudiencia().getValoracordo() <= 3000.0) {
					unicasolicitacao.setValor(190);
				} else if (unicasolicitacao.getFormularioAudiencia().getValoracordo() <= 2000.0) {
					unicasolicitacao.setValor(225);
				}

			} else {
				unicasolicitacao.setValor(renumeracao2.getValor());
			}

		}

		/**
		 * Aplica a regra de desconto ainda nao posto em producaovigente apartir de
		 * 08/12/2014 pelo LG esta desabilitada
		 */

		try {
			// unicasolicitacao.setDataconclusao(new Date());
			// Aqui so aplica e regra
			if (unicasolicitacao.getRenumeracao().getValor() == unicasolicitacao.getValor()) {
				unicasolicitacao.setValor(DescontaAtrazoSolicitacao());
			}
			unicasolicitacao.setDataconclusao(new Date());
			solicitacaoDao.Alterar(unicasolicitacao);
		} catch (Exception e1) { //
			solicitacaoDao.Cancelar(); // TODO Auto-generated catch block FacesContext
			FacesContext cont1 = FacesContext.getCurrentInstance();
			// cont1 = FacesContext.getCurrentInstance();
			cont1.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao salvar entrar em contato com o suporte.", ""));
			return BuscaUnica();

		}

		Historico historico3 = new Historico();
		historico3.setSolicitacao(unicasolicitacao);
		historico3.setDatahistorico(new Date());
		// Usuario usuarionovo = new Usuario();
		// usuarionovo = usuarioDao.trazusuario(usuario.getIdusuario());
		// historico3.setUsuario(usuarionovo);
		historico3.setUsuario(usuario); // Usuario logado
		historico3.setStatusSolicitacao(tipoUnicoMuda);
		historico3.setRenumeracao(renumeracao2);
		historico3.setTextohistorico(observacaofinal);
		historicoDao.Salvar(historico3);
		historico3 = null;
		// usuarionovo = null;
		// Envia a prosposta de acordo
		if (unicasolicitacao.isPropostaacordo() == true) {
			vaiacordo = "Proposta de Acordo: SIM" + "\n" + "Valor da Alçada: R$"
					+ String.format("%.2f", unicasolicitacao.getValordaalcada());
		} else if (unicasolicitacao.isPropostaacordo() == false) {
			vaiacordo = "Propposta de Acordo: NÃ0";
		}

		/**
		 * Envia o email conforme o envio diferente de 3,5 e 7
		 */

		/*
		 * 
		 * Aqui verifica se a audiencia foi residgnada com a data de ACIJ se feito
		 * enviar email para a banca ou grupo Data 14/11/2019
		 * 
		 */
		if (unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao()
				.getEspecie().equals("AUDIENCIA")) {
			if (unicasolicitacao.getFormularioAudiencia().isAijdesiginada() == true) {

				try {
					textoemail = "";

					textoemail = "---------------------------------------------------------------------------------------------------\n"
							+ "DATA DA ACIJ: "
							+ dateFormat.format(unicasolicitacao.getFormularioAudiencia().getDataaij()) + "\n"
							+ "Id Solicitação: " + unicasolicitacao.getIdsolicitacao() + "\n" + "Número do Processo: "
							+ unicasolicitacao.getProcesso().getNumeroprocesso() + "\n" + "Parte contrária: "
							+ unicasolicitacao.getProcesso().getParte() + "\n" + "Cliente: "
							+ unicasolicitacao.getProcesso().getAdverso() + "\n" + "Tipo de Solicitação: "
							+ unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao()
									.getEspecie()
							+ " - "
							+ unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao()
									.getTipo()
							+ "\n" + "Comarca :" + unicasolicitacao.getComarca().getNome() + "\n" + "Localização: "
							+ unicasolicitacao.getProcesso().getNumorgao() + " - "
							+ unicasolicitacao.getProcesso().getOrgao().getDescricao() + "\n" + "Data da solicitação: "
							+ dateFormat.format(unicasolicitacao.getDatasolictacao()) + "\n" + "Prazo Final: "
							+ dateFormat.format(unicasolicitacao.getDataprazo()) + " - Hora: "
							+ unicasolicitacao.getHoraudiencia() + "\n" + "Instruções: " + "\n"
							+ unicasolicitacao.getInstrucoes() + "\n" + "Status da Solicitação: "
							+ unicasolicitacao.getStatusSolicitacao().getStatus() + "\n"

							+ "---------------------------------------------------------------------------------------------------\n";

					enviaEmail.Enviar(unicasolicitacao, unicasolicitacao.getUsuario().getEmailprincipal(),
							unicasolicitacao.getEmailenvio(), unicasolicitacao.getRenumeracao()
									.getTipoSolicitacaoCorrespondente().getCorrespondente().getEmailsecundario(),
							unicasolicitacao.getUsuario().getEmailresponsavel(),
							unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente()
									.getNome(),
							"", textoemail, "** SOLICTAÇÃO DESIGNADA ** ID -  " + unicasolicitacao.getIdsolicitacao());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		if (unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao().getEspecie()
				.equals("AUDIENCIA") && unicasolicitacao.getEnviosolicitacao().getIdenviosolicitacao() != 3
				&& unicasolicitacao.getEnviosolicitacao().getIdenviosolicitacao() != 5
				&& unicasolicitacao.getEnviosolicitacao().getIdenviosolicitacao() != 7) {
			// Envia o email se nao houve proposta de acordo
			EmailSemproposta();

		}
		FacesContext cont = FacesContext.getCurrentInstance();
		try {
			textoemail = "";
			textoemail = "---------------------------------------------------------------------------------------------------\n"
					+ "Id Solicitação: " + unicasolicitacao.getIdsolicitacao() + "\n" + "Número do Processo: "
					+ unicasolicitacao.getProcesso().getNumeroprocesso() + "\n" + "Parte contrária: "
					+ unicasolicitacao.getProcesso().getParte() + "\n" + "Cliente: "
					+ unicasolicitacao.getProcesso().getAdverso() + "\n" + "Tipo de Solicitação: "
					+ unicasolicitacao
							.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao().getEspecie()
					+ " - "
					+ unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao()
							.getTipo()
					+ "\n" + "Comarca :" + unicasolicitacao.getComarca().getNome() + "\n" + "Localização: "
					+ unicasolicitacao.getProcesso().getNumorgao() + " - "
					+ unicasolicitacao.getProcesso().getOrgao().getDescricao() + "\n" + "Data da solicitação: "
					+ dateFormat.format(unicasolicitacao.getDatasolictacao()) + "\n" + "Prazo Final: "
					+ dateFormat.format(unicasolicitacao.getDataprazo()) + " - Hora: "
					+ unicasolicitacao.getHoraudiencia() + "\n" + "Instruções: " + "\n"
					+ unicasolicitacao.getInstrucoes() + "\n" + "Status da Solicitação: "
					+ unicasolicitacao.getStatusSolicitacao().getStatus() + "\n"
					+ "---------------------------------------------------------------------------------------------------\n";

			enviaEmail.Enviar(unicasolicitacao, unicasolicitacao.getUsuario().getEmailprincipal(),
					unicasolicitacao.getEmailenvio(),
					unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente()
							.getEmailsecundario(),
					unicasolicitacao.getUsuario().getEmailresponsavel(),
					unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente().getNome(),
					"", textoemail,
					"** AVISO DE SOLICITAÇÃO ** CONCLUIDA ** ID -  " + unicasolicitacao.getIdsolicitacao());

			cont.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Solicitação foi concluída e o e-mail foi enviado com sucesso.", ""));

		} catch (Exception e) {

			try {
				enviaEmail.Enviar(unicasolicitacao, unicasolicitacao.getUsuario().getEmailprincipal(),
						unicasolicitacao.getEmailenvio(),
						unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente()
								.getEmailsecundario(),
						unicasolicitacao.getUsuario().getEmailresponsavel(),
						unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente()
								.getNome(),
						"", textoemail,
						"** AVISO DE SOLICITAÇÃO ** CONCLUIDA ** ID - " + unicasolicitacao.getIdsolicitacao());

				cont.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Solicitação foi concluída e o e-mail foi enviado com sucesso.", ""));

			} catch (Exception e2) {
				try {
					enviaEmail.EnviarDeNovo(unicasolicitacao, unicasolicitacao.getUsuario().getEmailprincipal(),
							unicasolicitacao.getEmailenvio(), unicasolicitacao.getRenumeracao()
									.getTipoSolicitacaoCorrespondente().getCorrespondente().getEmailsecundario(),
							unicasolicitacao.getUsuario().getEmailresponsavel(),
							unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente()
									.getNome(),
							"", textoemail, "** AVISO DE SOLICITAÇÃO ** - ID " + unicasolicitacao.getIdsolicitacao());

					cont.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Solicitação foi concluída e o e-mail foi enviado com sucesso.", ""));

				} catch (Exception e3) {
					// TODO: handle exception
					// TODO: handle exception
					cont = FacesContext.getCurrentInstance();
					cont.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Erro ao finalizar a solicitação o e-mail não foi enviado com sucesso.", ""));
				}
			}

			// TODO Auto-generated catch block

		}
		return

		BuscaUnica();

	}

	/**
	 * Reprova a solictacao caso haja copia ilegiveis etc e nao sera faturado
	 * podendo o correspondente ativá-la novamente
	 * 
	 * @return
	 */
	public String SolicitacaoReprovado() {
		bnumero = unicasolicitacao.getIdsolicitacao();
		renumeracao2 = null;
		renumeracao2 = renumeracaoDao.trazrenumeracao(
				unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao()
						.getIdtiposolicitacao(),
				unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente()
						.getIdcorrespondente(),
				unicasolicitacao.getEnviosolicitacao().getIdenviosolicitacao());

		tipoUnicoMuda = statusSolictacaoDao.trazunico(6);
		unicasolicitacao.setStatusSolicitacao(tipoUnicoMuda);
		try {
			solicitacaoDao.Alterar(unicasolicitacao);
		} catch (Exception e1) {
			FacesContext cont1 = FacesContext.getCurrentInstance();
			cont1.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao salvar entrar em contato com o suporte.", ""));
			return BuscaUnica();

		}

		/**
		 * Salva o historico
		 */
		Historico historico3 = new Historico();
		historico3.setSolicitacao(unicasolicitacao);
		historico3.setDatahistorico(new Date());
		historico3.setUsuario(unicasolicitacao.getUsuario());
		historico3.setStatusSolicitacao(tipoUnicoMuda);
		historico3.setRenumeracao(renumeracao2);
		historicoDao.Salvar(historico3);
		historico3 = null; // Seta o historico

		if (unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao()
				.getEspecie() == "AUDIENCIA") {
			if (unicasolicitacao.getFormularioAudiencia().isAcordorealizado() == false
					&& unicasolicitacao.isPropostaacordo() == true) {

				String texto1 = "-------------------------------------------------------------------------------------------------------\n"
						+ "Servimos do presente para informar que a audiência abaixo não houve acordo realizado\n"
						+ "ID: " + unicasolicitacao.getIdsolicitacao() + "\n" + "Correspondente: "
						+ unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente()
								.getNome()
						+ "\n" + "Advogaddo Ex Adverso: "
						+ unicasolicitacao.getFormularioAudiencia().getAdvogadoadverso() + "\n"
						+ "Telefone do Advogado Ex Adverso: "
						+ unicasolicitacao.getFormularioAudiencia().getTelefoneadvadervoso() + "\n"
						+ "Email do Advogado Ex Adverso: "
						+ unicasolicitacao.getFormularioAudiencia().getEmailadvadverso() + "\n" + "Autor: "
						+ unicasolicitacao.getProcesso().getParte() + "\n" + "Réu  : "
						+ unicasolicitacao.getProcesso().getAdverso() + "\n"
						// + "Valor da Alçada: "
						// + "R$"
						// + String.format("%.2f",
						// unicasolicitacao.getValordaalcada())
						// + "\n"
						// + "Valor da Contra Proposta: "
						// + "R$"
						// + String.format("%.2f", unicasolicitacao
						// .getFormularioAudiencia()
						// .getValorcontraproposta())
						+ "\n" + " " + "\n"
						+ "-------------------------------------------------------------------------------------------------------\n";

				try {
					enviaEmail.EnviarMsg("admin@cra.adv.br", "nelsonrjbrazil@gmail.com", "OUVIDORIA COLABORADOR", "",
							texto1,
							"** AVISO DE SOLICITAÇÃO - REPROVADA ** ID - " + unicasolicitacao.getIdsolicitacao());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					FacesContext cont = FacesContext.getCurrentInstance();
					cont = FacesContext.getCurrentInstance();
					cont.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao enviar e-mail.", ""));
				}
			}
		}

		if (unicasolicitacao.isPropostaacordo() == true) {

			vaiacordo = "Proposta de Acordo: SIM" + "\n" + "Valor da Alçada: R$"
					+ String.format("%.2f", unicasolicitacao.getValordaalcada());
		} else if (unicasolicitacao.isPropostaacordo() == false) {
			vaiacordo = "Propposta de Acordo: NÃ0";
		}

		String texto3 = "---------------------------------------------------------------------------------------------------\n"
				+ "Id Solicitação: " + unicasolicitacao.getIdsolicitacao() + "\n" + "Número do Processo: "
				+ unicasolicitacao.getProcesso().getNumeroprocesso() + "\n" + "Parte contrária: "
				+ unicasolicitacao.getProcesso().getParte() + "\n" + "Cliente: "
				+ unicasolicitacao.getProcesso().getAdverso() + "\n" + "Localização: "
				+ unicasolicitacao.getProcesso().getNumorgao() + " - "
				+ unicasolicitacao.getProcesso().getOrgao().getDescricao() + "\n" + "Tipo de Solicitação: "
				+ unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao().getEspecie()
				+ " - "
				+ unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao().getTipo()
				+ "\n"

				+ "Data da solicitação: " + dateFormat.format(unicasolicitacao.getDatasolictacao()) + "\n"
				+ "Prazo Final: " + dateFormat.format(unicasolicitacao.getDataprazo()) + " - Hora: "
				+ unicasolicitacao.getHoraudiencia() + "\n" + "Instruções: " + "\n" + unicasolicitacao.getInstrucoes()
				+ "\n" + "Status da Solicitação: " + unicasolicitacao.getStatusSolicitacao().getStatus() + "\n"
				+ "-----------------------------------------------------------------------------------------------------------------------------\n"
				+ "Observação:" + "\n" + "Sua SOLICITAÇÃO/AUDIÊNCIA foi rejeitada pelo Escritório remetente." + "\n"
				+ "\n"
				+ "Favor em contato com o telefone (21)2201-3250 Ramais: 3278, 3230 ou 3329 informando o Id Solicitação: "
				+ unicasolicitacao.getIdsolicitacao() + "\n" + "questionando o motivo da rejeição da mesma.";
		try {
			enviaEmail.Enviar(unicasolicitacao, usuario.getEmailprincipal(), unicasolicitacao.getEmailenvio(),
					unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente()
							.getEmailsecundario(),
					usuario.getEmailresponsavel(),
					unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente().getNome(),
					"", texto3, "** AVISO DE SOLICITAÇÃO **");
			FacesContext cont1 = FacesContext.getCurrentInstance();
			cont1.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitação foi rejeitada com sucesso.", ""));

		} catch (Exception e) {
			try {
				enviaEmail.EnviarDeNovo(unicasolicitacao, usuario.getEmailprincipal(), unicasolicitacao.getEmailenvio(),
						unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente()
								.getEmailsecundario(),
						usuario.getEmailresponsavel(),
						unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente()
								.getNome(),
						"", texto3,
						"** AVISO DE SOLICITAÇÃO - REJEITADA ** ID -" + unicasolicitacao.getIdsolicitacao());
				FacesContext cont1 = FacesContext.getCurrentInstance();
				cont1.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitação foi rejeitada com sucesso.", ""));

			} catch (Exception e2) {

				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.printf(e.getMessage());

				// TODO: handle exception
			}

		}

		return BuscaUnica();
	}

	/**
	 * Esta função reativa a solicitação através do correspondente para colocar em
	 * producao e finalizar novamente
	 * 
	 * @return
	 */
	public String ReativaSolictacao() {
		// Verifica se esta no status recusado se estiver nao faz
		if (unicasolicitacao.getStatusSolicitacao().getIdstatus().equals(10)) {
			FacesContext cont1 = FacesContext.getCurrentInstance();
			cont1.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Solicitação não pode ser reativada entre em contato com o Suporte.", ""));
			return BuscaUnica();
		}
		// verifica se ja foi retivada anteriormente
		if (historicoDao.buscartipos(unicasolicitacao.getIdsolicitacao(), 4).size() > 2) {
			FacesContext cont1 = FacesContext.getCurrentInstance();
			cont1.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Solicitação não pode ser reativada entre em contato com o Suporte. Pois ja houve uma reativação anteriormente",
					""));
			return BuscaUnica();

		}

		bnumero = unicasolicitacao.getIdsolicitacao();
		tipoUnicoMuda = statusSolictacaoDao.trazunico(4);
		unicasolicitacao.setStatusSolicitacao(tipoUnicoMuda);
		unicasolicitacao.setComplemento("");

		try {
			solicitacaoDao.Alterar(unicasolicitacao);
		} catch (Exception e1) {
			// solicitacaoDao.Cancelar();
			FacesContext cont1 = FacesContext.getCurrentInstance();
			cont1.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao reativr entrar em contato com o suporte.", ""));
			return BuscaUnica();

		}
		/**
		 * Salva o historico
		 */
		Historico historiconovo = new Historico();
		Usuario usuativa = new Usuario();

		try {
			usuario = usuarioDao.trazusuario(usuario.getIdusuario());
		} catch (Exception e) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao acessa dados." + "\n" + e.getMessage(), ""));
			// TODO: handle exception
		}

		historiconovo.setSolicitacao(unicasolicitacao);
		historiconovo.setDatahistorico(new Date());
		historiconovo.setUsuario(usuativa);
		historiconovo.setStatusSolicitacao(tipoUnicoMuda);
		historiconovo.setRenumeracao(unicasolicitacao.getRenumeracao());
		historicoDao.Salvar(historiconovo);
		historiconovo = null; // Seta o obejto
		usuativa = null;
		if (unicasolicitacao.isPropostaacordo() == true) {

			vaiacordo = "Proposta de Acordo: SIM" + "\n" + "Valor da Alçada: R$"
					+ String.format("%.2f", unicasolicitacao.getValordaalcada());
		} else if (unicasolicitacao.isPropostaacordo() == false) {
			vaiacordo = "Propposta de Acordo: NÃ0";
		}

		String texto3 = "---------------------------------------------------------------------------------------------------\n"
				+ "Id Solicitação: " + unicasolicitacao.getIdsolicitacao() + "\n" + "Número do Processo: "
				+ unicasolicitacao.getProcesso().getNumeroprocesso() + "\n" + "Parte contrária: "
				+ unicasolicitacao.getProcesso().getParte() + "\n" + "Cliente: "
				+ unicasolicitacao.getProcesso().getAdverso() + "\n" + "Tipo de Solicitação: "
				+ unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao().getEspecie()
				+ " - "
				+ unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao().getTipo()
				+ "\n\n"

				+ "Data da solicitação: " + dateFormat.format(unicasolicitacao.getDatasolictacao()) + "\n"
				+ "Prazo Final: " + dateFormat.format(unicasolicitacao.getDataprazo()) + " - Hora: "
				+ unicasolicitacao.getHoraudiencia() + "\n"
				// + vaiacordo
				// + "\n"
				+ "Instruções: " + "\n" + unicasolicitacao.getInstrucoes() + "\n" + "Status da Solicitação: "
				+ unicasolicitacao.getStatusSolicitacao().getStatus() + "\n"
				+ "---------------------------------------------------------------------------------------------------\n"
				+ "Observção:" + "\n"
				+ "A SOLICITAÇÃO/AUDIÊNCIA foi reativada pelo correspondente novamente para ser refeita.";
		FacesContext cont1 = FacesContext.getCurrentInstance();
		try {
			enviaEmail.Enviar(unicasolicitacao, unicasolicitacao.getUsuario().getEmailprincipal(),
					unicasolicitacao.getEmailenvio(),
					unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente()
							.getEmailsecundario(),
					unicasolicitacao.getUsuario().getEmailresponsavel(),
					unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente().getNome(),
					"", texto3, "** SOLICITAÇÃO REATIVADA ** ID - " + unicasolicitacao.getIdsolicitacao());

			cont1.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "A solicitação foi reativada  com sucesso.", ""));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			cont1.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"A solicitação foi reativada  com sucesso mas o email não enviado.", ""));
		}

		if (unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao()
				.getEspecie() == "AUDIENCIA") {
			if (unicasolicitacao.getFormularioAudiencia().isAcordorealizado() == false
					&& unicasolicitacao.isPropostaacordo() == true) {
				String texto1 = "-------------------------------------------------------------------------------------------------------\n"
						+ "Servimos do presente para informar que a audiência abaixo não houve acordo realizado\n"
						+ "ID: " + unicasolicitacao.getIdsolicitacao() + "\n" + "Correspondente: "
						+ unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente()
								.getNome()
						+ "\n" + "Advogaddo Ex Adverso: "
						+ unicasolicitacao.getFormularioAudiencia().getAdvogadoadverso() + "\n"
						+ "Telefone do Advogado Ex Adverso: "
						+ unicasolicitacao.getFormularioAudiencia().getTelefoneadvadervoso() + "\n"
						+ "Email do Advogado Ex Adverso: "
						+ unicasolicitacao.getFormularioAudiencia().getEmailadvadverso() + "\n" + "Autor: "
						+ unicasolicitacao.getProcesso().getParte() + "\n" + "Réu  : "
						+ unicasolicitacao.getProcesso().getAdverso() + "\n"
						// + "Valor da Alçada: "
						// + "R$"
						// + String.format("%.2f",
						// unicasolicitacao.getValordaalcada())
						// + "\n"
						// + "Valor da Contra Proposta: "
						// + "R$"
						// + String.format("%.2f", unicasolicitacao
						// .getFormularioAudiencia()
						// .getValorcontraproposta())
						+ " " + "\n"
						+ "-------------------------------------------------------------------------------------------------------\n";

				try {
					enviaEmail.EnviarMsg("suporte@cra.adv.br", "ouvidoria.colaborador@cra.adv.br",
							"OUVIDORIA COLABORADOR", "", texto1,
							"** AVISO DE SOLICITAÇÃO ** ID - " + unicasolicitacao.getIdsolicitacao());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					FacesContext cont = FacesContext.getCurrentInstance();
					cont = FacesContext.getCurrentInstance();
					cont.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao enviar e-mail.", ""));
				}

			}
		}
		historiconovo = null;
		return BuscaUnica();
	}

	/**
	 * Finaliza a solicitação realizada pelo colaborador e deixa pronto para gerar o
	 * faturamento ou pode ser rejeitada pelo cliente
	 * 
	 * @return
	 */
	public String FinalizaSolictacao() {
		try {
			VerSessao();
		} catch (Exception e) {

		}
		bnumero = alterarsolicitacao.getIdsolicitacao();
		renumeracao2 = alterarsolicitacao.getRenumeracao();
		// String textorecusa="";
		if (idobservacao.equals("N")) {
			tipoUnicoMuda = statusSolictacaoDao.trazunico(7);// Finaliza a
		} else if (idobservacao.equals("S")) {
			// Muda o status em cada caso de rejeição oo reprovado etc

			// Desconta os valores na rejeição // valor
			if (getRecebecomplemento().equals(1)) {
				// A) Proposta de acordo não consignada em ata;
				// if (alterarsolicitacao.getValor() >= alterarsolicitacao
				// .getRenumeracao().getValor()) {
				// alterarsolicitacao.setValor(DescontaFinal(
				// / alterarsolicitacao.getValor(), 50));
				// }
				if (reprovardefinitivamente.equals("N")) {
					tipoUnicoMuda = statusSolictacaoDao.trazunico(6);
				} else if (reprovardefinitivamente.equals("S")) {
					tipoUnicoMuda = statusSolictacaoDao.trazunico(10);
				}

			} else if (getRecebecomplemento().equals(2)) {
				// B) Ata de audiência constando ''não houve proposta de
				// acordo''
				// if (alterarsolicitacao.getValor() >= alterarsolicitacao
				// .getRenumeracao().getValor()) {
				// alterarsolicitacao.setValor(0);
				// }
				if (reprovardefinitivamente.equals("N")) {
					tipoUnicoMuda = statusSolictacaoDao.trazunico(6);
				} else if (reprovardefinitivamente.equals("S")) {
					tipoUnicoMuda = statusSolictacaoDao.trazunico(10);
				}
			} else if (getRecebecomplemento().equals(3)) {
				// C) Depoimento pessoal não colhido;
				if (reprovardefinitivamente.equals("N")) {
					tipoUnicoMuda = statusSolictacaoDao.trazunico(6);
				} else if (reprovardefinitivamente.equals("S")) {
					tipoUnicoMuda = statusSolictacaoDao.trazunico(10);
				}
				// if (alterarsolicitacao.getValor() >= alterarsolicitacao
				// .getRenumeracao().getValor()) {
				// alterarsolicitacao.setValor(0);
				// }
			} else if (getRecebecomplemento().equals(4)) {
				// D) Formulário incompleto;
				// if (alterarsolicitacao.getValor() >= alterarsolicitacao
				// .getRenumeracao().getValor()) {
				// alterarsolicitacao.setValor(DescontaFinal(
				// alterarsolicitacao.getValor(), 10));
				// }
				if (reprovardefinitivamente.equals("N")) {
					tipoUnicoMuda = statusSolictacaoDao.trazunico(6);
				} else if (reprovardefinitivamente.equals("S")) {
					tipoUnicoMuda = statusSolictacaoDao.trazunico(10);
				}
			} else if (getRecebecomplemento().equals(5)) {
				// E) Cópia ilegível;
				// if (alterarsolicitacao.getValor() >= alterarsolicitacao
				// .getRenumeracao().getValor()) {
				// alterarsolicitacao.setValor(DescontaFinal(
				// alterarsolicitacao.getValor(), 10));
				// }
				if (reprovardefinitivamente.equals("N")) {
					tipoUnicoMuda = statusSolictacaoDao.trazunico(6);
				} else if (reprovardefinitivamente.equals("S")) {
					tipoUnicoMuda = statusSolictacaoDao.trazunico(10);
				}
			} else if (getRecebecomplemento().equals(6)) {

				if (reprovardefinitivamente.equals("N")) {
					tipoUnicoMuda = statusSolictacaoDao.trazunico(6);
				} else if (reprovardefinitivamente.equals("S")) {
					tipoUnicoMuda = statusSolictacaoDao.trazunico(10);
				}
				// Aqui rejita definitivamente a solicitação
			}
		}

		alterarsolicitacao.setStatusSolicitacao(tipoUnicoMuda);

		try {
			solicitacaoDao.Alterar(alterarsolicitacao);
			// TODO Auto-generated catch block
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Solicitação finalizada ou rejeitada com successo.", ""));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao salvar entrar em contato com o suporte.", ""));
			return BuscaUnicaAlteracao();
		}

		/**
		 * Salva o historico
		 */

		Historico novohistorico1 = new Historico();
		novohistorico1.setRenumeracao(renumeracao2);
		novohistorico1.setSolicitacao(alterarsolicitacao);
		novohistorico1.setDatahistorico(new Date());
		novohistorico1.setUsuario(usuario);
		novohistorico1.setTextohistorico(alterarsolicitacao.getComplemento());
		novohistorico1.setStatusSolicitacao(tipoUnicoMuda);
		historicoDao.Salvar(novohistorico1);
		// alterarsolicitacao.setStatusSolicitacao(tipoUnicoMuda);
		FacesContext cont2 = FacesContext.getCurrentInstance();

		if (alterarsolicitacao.isPropostaacordo() == true) {

			vaiacordo = "Proposta de Acordo: SIM" + "\n" + "Valor da Alçada: R$"
					+ String.format("%.2f", alterarsolicitacao.getValordaalcada());
		} else if (alterarsolicitacao.isPropostaacordo() == false) {
			vaiacordo = "Propposta de Acordo: NÃ0";
		}

		/**
		 * Envia o e-email somente com os status 7 ou 6 // Documentei havia um erro
		 * ambiguo no código
		 */
		// if (alterarsolicitacao.getStatusSolicitacao().getIdstatus().equals(7)
		// || alterarsolicitacao.getStatusSolicitacao().getIdstatus()
		// .equals(6))

		if (tipoUnicoMuda.getIdstatus() == 7 || tipoUnicoMuda.getIdstatus() == 6 || tipoUnicoMuda.getIdstatus() == 10) {
			String texto3 = "---------------------------------------------------------------------------------------------------\n"
					+ "Id Solicitação: " + alterarsolicitacao.getIdsolicitacao() + "\n" + "Número do Processo: "
					+ alterarsolicitacao.getProcesso().getNumeroprocesso() + "\n" + "Parte contrária: "
					+ alterarsolicitacao.getProcesso().getParte() + "\n" + "Cliente: "
					+ alterarsolicitacao.getProcesso().getAdverso() + "\n" + "Tipo de Solicitação: "
					+ alterarsolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao()
							.getEspecie()
					+ " - " + alterarsolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente()
							.getTipoSolicitacao().getTipo()
					+ "\n"
					// + vaiacordo
					// + "\n"
					+ "Data da solicitação: " + dateFormat.format(alterarsolicitacao.getDatasolictacao()) + "\n"
					+ "Prazo Final: " + dateFormat.format(alterarsolicitacao.getDataprazo()) + " - Hora: "
					+ alterarsolicitacao.getHoraudiencia() + "\n" + "Instruções: " + "\n"
					+ alterarsolicitacao.getInstrucoes() + "\n" + "Status da Solicitação: "
					+ alterarsolicitacao.getStatusSolicitacao().getStatus() + "\n"
					+ "---------------------------------------------------------------------------------------------------\n";
			try {
				System.out.print(texto3);
				enviaEmail.EnviarDeNovo(alterarsolicitacao, alterarsolicitacao.getUsuario().getEmailprincipal(),
						alterarsolicitacao.getEmailenvio(), alterarsolicitacao.getRenumeracao()
								.getTipoSolicitacaoCorrespondente().getCorrespondente().getEmailsecundario(),
						usuario.getEmailresponsavel(),
						alterarsolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente()
								.getNome(),
						"", texto3, "** AVISO DE SOLICITAÇÃO ** ID - " + alterarsolicitacao.getIdsolicitacao());
				cont2.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitação foi finalizada com sucesso.", ""));

			} catch (Exception e) {
				// TODO Auto-generated catch block
				cont2.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Erro ao enviar email para o destinatário.",
						"** AVISO DE SOLICITAÇÃO - FINALIZADA ** ID - " + alterarsolicitacao.getIdsolicitacao()));
			}

		}
		// Documentei aqui para ser mais rapido em 03/06/2019160:8080
		// getSolicitacoes();
		return BuscaUnicaAlteracao();
	}

	/**
	 * Altera a solicitacao caso o correspondente nao realize passando para o
	 * proximo que podera ser relaizado na mesma comarca
	 */
	public void Alterarsolicitacao() {
		@SuppressWarnings("unused")
		FacesContext context = FacesContext.getCurrentInstance();
		// Traz a solicitacao sempre
		renumeracao2 = null;
		statusSolicitacao = statusSolictacaoDao.trazunico(1);
		String texto4 = "";
		Integer idenvio = 0;
		Integer idtiposolictacao = 0;

		idtiposolictacao = alterarsolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao()
				.getIdtiposolicitacao();
		if (alterarsolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao().getEspecie()
				.equals("AUDIENCIA")) {
			idenvio = alterarsolicitacao.getEnviosolicitacao().getIdenviosolicitacao();
		} else if (alterarsolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao()
				.getEspecie().equals("DILIGENCIA")) {
			idenvio = 0;
		}

		renumeracao2 = renumeracaoDao.trazrenumeracao(idtiposolictacao, idcorrespondente, idenvio);
		alterarsolicitacao.setRenumeracao(renumeracao2);
		alterarsolicitacao.setStatusexterno("");// Coloca vazio no status
		// externo para o correspondente
		// validar outra vez
		alterarsolicitacao.setStatusSolicitacao(statusSolicitacao);
		alterarsolicitacao.setEmailenvio(emaildeenvio);

		try {
			solicitacaoDao.Alterar(alterarsolicitacao);
		} catch (Exception e1) {
			FacesContext cont1 = FacesContext.getCurrentInstance();
			cont1.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao salvar entrar em contato com o suporte.", ""));
		}

		// Pega o testo
		texto4 = "---------------------------------------------------------------------------------------------------\n"
				+ "Id Solicitação: " + alterarsolicitacao.getIdsolicitacao() + "\n" + "Número do Processo: "
				+ alterarsolicitacao.getProcesso().getNumeroprocesso() + "\n" + "Parte contrária: "
				+ alterarsolicitacao.getProcesso().getParte() + "\n" + "Cliente: "
				+ alterarsolicitacao.getProcesso().getAdverso() + "\n" + "Tipo de Solicitação: "
				+ alterarsolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao()
						.getEspecie()
				+ " - "
				+ alterarsolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao().getTipo()
				+ "\n" + "Data da solicitação: " + dateFormat.format(alterarsolicitacao.getDatasolictacao()) + "\n"
				+ "Prazo Final: " + dateFormat.format(alterarsolicitacao.getDataprazo()) + " - Hora: "
				+ alterarsolicitacao.getHoraudiencia() + "\n"
				// + vaiacordo
				// + "\n"
				+ "Instruções: " + "\n" + alterarsolicitacao.getInstrucoes() + "\n" + "Status da Solicitação: "
				+ alterarsolicitacao.getStatusSolicitacao().getStatus() + "\n"
				+ "---------------------------------------------------------------------------------------------------\n"
				+ "Observção:" + "\n" + "Sua SOLICITAÇÃO/AUDIÊNCIA foi enviada com sucesso.";
		try {
			enviaEmail.Enviar(alterarsolicitacao, usuario.getEmailprincipal(), alterarsolicitacao.getEmailenvio(),
					alterarsolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente()
							.getEmailsecundario(),
					usuario.getEmailresponsavel(),
					alterarsolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente()
							.getNome(),
					"", texto4, "** AVISO DE SOLICITAÇÃO ALTERADA ** ID - " + alterarsolicitacao.getIdsolicitacao());
			FacesContext cont1 = FacesContext.getCurrentInstance();
			cont1.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Solicitação foi alterada com sucesso para um novo correspondente.", ""));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.printf(e.getMessage());
		}
	}

	/**
	 * Cancela a solicitacao do Correspondente pelo usuario que fez ou outro So
	 * envia o email quando a solicitação é cancelada ao contrário so mostra a
	 * mensagem
	 */
	public void CancelarSolicitacao() {
		if (cancelasolicitacao == true) {
			String texto5 = "";
			statusSolicitacao = statusSolictacaoDao.trazunico(8); // Cancelado
			alterarsolicitacao.setStatusSolicitacao(statusSolicitacao);
			try {
				alterarsolicitacao.setStatusSolicitacao(statusSolicitacao);
				solicitacaoDao.Alterar(alterarsolicitacao);
				Historico novohistorico3 = new Historico();
				useraltera = usuarioDao.trazusuario(idusuario);
				novohistorico3.setRenumeracao(alterarsolicitacao.getRenumeracao());
				novohistorico3.setSolicitacao(alterarsolicitacao);
				novohistorico3.setDatahistorico(new Date());
				novohistorico3.setUsuario(alterarsolicitacao.getUsuario());
				novohistorico3.setStatusSolicitacao(statusSolicitacao);
				historicoDao.Salvar(novohistorico3);
				novohistorico3 = null; // seta o hsitorico
			} catch (Exception e1) {
				FacesContext cont1 = FacesContext.getCurrentInstance();
				cont1.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro ao alterar a solicitacao.", ""));

			}
			renumeracao = null;
			renumeracao2 = null;
			statusSolicitacao = null;
			comarcasolicitacao = null;
			// useraltera=null;
			// Pega o testo
			texto5 = "---------------------------------------------------------------------------------------------------\n"
					+ "Id Solicitação: " + alterarsolicitacao.getIdsolicitacao() + "\n" + "Número do Processo: "
					+ alterarsolicitacao.getProcesso().getNumeroprocesso() + "\n" + "Parte contrária: "
					+ alterarsolicitacao.getProcesso().getParte() + "\n" + "Cliente: "
					+ alterarsolicitacao.getProcesso().getAdverso() + "\n" + "Tipo de Solicitação: "
					+ alterarsolicitacao
							.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao().getEspecie()
					+ " - "
					+ alterarsolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao()
							.getTipo()
					+ "\n" + "Data da solicitação: " + dateFormat.format(alterarsolicitacao.getDatasolictacao()) + "\n"
					+ "Prazo Final: " + dateFormat.format(alterarsolicitacao.getDataprazo()) + " - Hora: "
					+ alterarsolicitacao.getHoraudiencia() + "\n"
					// + vaiacordo
					// + "\n"
					+ "Instruções: " + "\n" + alterarsolicitacao.getInstrucoes() + "\n" + "Status da Solicitação: "
					+ alterarsolicitacao.getStatusSolicitacao().getStatus() + "\n"
					+ "---------------------------------------------------------------------------------------------------\n"
					+ "Observção:" + "\n" + "Sua SOLICITAÇÃO/AUDIÊNCIA foi cancelada.";
			try {
				enviaEmail.Enviar(alterarsolicitacao, usuario.getEmailprincipal(), alterarsolicitacao.getEmailenvio(),
						alterarsolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente()
								.getEmailsecundario(),
						usuario.getEmailresponsavel(),
						alterarsolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente()
								.getNome(),
						"", texto5, "** SOLICITAÇÃO CANCELADA ** ID -" + alterarsolicitacao.getIdsolicitacao());
				FacesContext cont1 = FacesContext.getCurrentInstance();
				cont1.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitação foi cancelada com sucesso.", ""));

			} catch (Exception e) {
				// TODO Auto-generated catch block
				FacesContext cont1 = FacesContext.getCurrentInstance();
				cont1.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error ao enviar o email.", ""));
			}

		} else {
			FacesContext cont1 = FacesContext.getCurrentInstance();
			cont1.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Solicitação não foi cancelada.", ""));
		}
	}

	/**
	 * Ativa a Solicitacao novamente
	 */
	public void AtivarSolicitacao() {

		Calendar dataInicial = Calendar.getInstance();
		dataInicial.setTime(alterarsolicitacao.getDatasolictacao());
		long diferenca = System.currentTimeMillis() - dataInicial.getTimeInMillis();
		// long diferencaSeg = diferenca / 1000; //DIFERENCA EM SEGUNDOS
		// long diferencaMin = diferenca / (60 * 1000); //DIFERENCA EM
		// MINUTOS
		long diferencaHoras = diferenca / (60 * 60 * 1000); // DIFERENCA EM
		// HORAS
		if (alterasolicitacaodenovo == true) {
			statusSolicitacao = statusSolictacaoDao.trazunico(1); // Cancelado
			alterarsolicitacao.setStatusSolicitacao(statusSolicitacao);
			try {

				alterarsolicitacao.setStatusSolicitacao(statusSolicitacao);
				solicitacaoDao.Alterar(alterarsolicitacao);
				Historico novohistorico4 = new Historico();
				useraltera = usuarioDao.trazusuario(idusuario);
				novohistorico4.setRenumeracao(alterarsolicitacao.getRenumeracao());
				novohistorico4.setSolicitacao(alterarsolicitacao);
				novohistorico4.setDatahistorico(new Date());
				novohistorico4.setUsuario(alterarsolicitacao.getUsuario());
				novohistorico4.setStatusSolicitacao(statusSolicitacao);
				historicoDao.Salvar(novohistorico4);
				novohistorico4 = null; // seta o hsitorico

				// Envia um email pra ficar registrado
				String texto6 = "---------------------------------------------------------------------------------------------------\n"
						+ "Id Solicitação: " + alterarsolicitacao.getIdsolicitacao() + "\n" + "Número do Processo: "
						+ alterarsolicitacao.getProcesso().getNumeroprocesso() + "\n" + "Parte contrária: "
						+ alterarsolicitacao.getProcesso().getParte() + "\n" + "Cliente: "
						+ alterarsolicitacao.getProcesso().getAdverso() + "\n" + "Tipo de Solicitação: "
						+ alterarsolicitacao
								.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao().getEspecie()
						+ " - "
						+ alterarsolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao()
								.getTipo()
						+ "\n" + "Data da solicitação: " + dateFormat.format(alterarsolicitacao.getDatasolictacao())
						+ "\n" + "Prazo Final: " + dateFormat.format(alterarsolicitacao.getDataprazo()) + " - Hora: "
						+ alterarsolicitacao.getHoraudiencia() + "\n"
						// + vaiacordo
						// + "\n"
						+ "Instruções: " + "\n" + alterarsolicitacao.getInstrucoes() + "\n" + "Status da Solicitação: "
						+ alterarsolicitacao.getStatusSolicitacao().getStatus() + "\n"
						+ "---------------------------------------------------------------------------------------------------\n"
						+ "Observção:" + "\n" + "Sua SOLICITAÇÃO/AUDIÊNCIA foi reativada com sucesso!.";

				enviaEmail.Enviar(alterarsolicitacao, alterarsolicitacao.getUsuario().getEmailprincipal(),
						alterarsolicitacao.getUsuario().getEmailprincipal(), "", "", "", "", texto6,
						"** SOLICITAÇÃO ATIVADA ** ID - " + alterarsolicitacao.getIdsolicitacao());
				FacesContext cont1 = FacesContext.getCurrentInstance();
				cont1.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitação foi ativada com sucesso.", ""));

			} catch (Exception e) {
				// TODO Auto-generated catch block
				FacesContext cont1 = FacesContext.getCurrentInstance();
				cont1.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Error ao ativar a solicitação ou erro no envio de e-mail.", ""));
			}

		} else {
			FacesContext cont1 = FacesContext.getCurrentInstance();
			cont1.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Solicitação não foi ativada.", ""));
		}
	}

	/**
	 * Pega a lista de correspondente
	 * 
	 * @return
	 */
	public List<Correspondente> getListacorrespondente() {

		listacorrespondente = correspondenteDao.buscafiltro("", "", "", true);

		return listacorrespondente;
	}

	/**
	 * Busca os andamentos no processo da base do cpj
	 */
	public void BuscaAdamento() {
		listaandamento = null;
		listaandamento = andamentoCPJDao.Busca(ficha);
		mostrajanela = true;
	}

	public List<AndamentoCPJ> getListaandamento() {
		return listaandamento;
	}

	public void setListaandamento(List<AndamentoCPJ> listaandamento) {
		this.listaandamento = listaandamento;
	}

	/**
	 * Hist Limpa a lista de andamento
	 */
	public void LimpaListaAdamento() {
		mostrajanela = false;
		listaandamento = null;
		nomeautor = "";
		nomereu = "";
		numprocessocpj = "";
	}

	public List<Orgao> getListaorgao() {

		listaorgao = orgaoDao.listar();

		return listaorgao;
	}

	public void setListaorgao(List<Orgao> listaorgao) {
		this.listaorgao = listaorgao;
	}

	public void setListacorrespondente(List<Correspondente> listacorrespondente) {
		this.listacorrespondente = listacorrespondente;
	}

	public List<Comarca> getListacomarca() {

		listacomarca = comarcaDao.buscargeral();

		return listacomarca;
	}

	public void setListacomarca(List<Comarca> listacomarca) {
		this.listacomarca = listacomarca;
	}

	public void setEnviaEmail(EnviaEmail enviaEmail) {
		this.enviaEmail = enviaEmail;
	}

	/**
	 * Busca as solicitações
	 * 
	 * @return
	 */

	public List<Solicitacao> getSolicitacoes() {
		context = FacesContext.getCurrentInstance();
		if (buscatodasoclitacoes.equals(true)) {

			solicitacoes = solicitacaoDao.buscargeral(bnumero, bprocesso, bcorrespondente, idusuario, numerointegracao,
					bcomarca, idorgao, idstatusbuscasolicitacao, tiposolicitacaobusca, bdatainicial, bdatafinal,
					nomeautor, nomereu, tipo_periodo, bestadocomarca, idbancabusca, "", buscacolab, "");

		} else if (buscatodasoclitacoes.equals(false)) {
			solicitacoes = null;
		}
		return solicitacoes;
	}

	/**
	 * Busca as solictacoes
	 */
	public void BuscaSolicitacaoCorrespondente() {

		// solicitacoes = solicitacaoDao.buscargeral(bnumero,
		// bprocesso,bcorrespondente, bcomarca,
		// idorgao,idstatusbuscasolicitacao,tiposolicitacaobusca,null,null,nomeautor,nomereu);
		getSolicitacoes();
	}

	/**
	 * Buscas as solicitações
	 */
	public void BuscaSolicitacao() {

		getSolicitacoes();

		// VerificaPendente();

	}

	public void setSolicitacoes(List<Solicitacao> solicitacoes) {
		this.solicitacoes = solicitacoes;
	}

	public Date getDatainicial() {
		return datainicial;
	}

	public void setDatainicial(Date datainicial) {
		this.datainicial = datainicial;
	}

	public Date getDatafinal() {
		return datafinal;
	}

	public void setDatafinal(Date datafinal) {
		this.datafinal = datafinal;
	}

	public String getProcessobusca() {
		return processobusca;
	}

	public void setProcessobusca(String processobusca) {
		this.processobusca = processobusca;
	}

	public String getAutorbusca() {
		return autorbusca;
	}

	public void setAutorbusca(String autorbusca) {
		this.autorbusca = autorbusca;
	}

	public String getReubusca() {
		return reubusca;
	}

	public void setReubusca(String reubusca) {
		this.reubusca = reubusca;
	}

	/**
	 * Lista osprocesso do cpj
	 * 
	 * @return
	 */
	public List<ProcessoCPJ> getProcessocpj() {
		processoDaoFB = new ProcessoDaoMysql(); // Cosulta o CPJ
		processocpj = null;

		processocpj = processoDaoFB.Busca(autorbusca, reubusca, processobusca, numerointegracaobusca.trim());
		return processocpj;

	}

	/**
	 * Lista os processo do CPPRO importado da planilha excel
	 * 
	 * @return
	 */
	public List<ProcessoCPPRO> getProcessoCPPROLista() {
		processoCPPROLista = null;
		try {
			processoCPPROLista = processoCPPRODAO.Filtro(processobusca, reubusca, autorbusca, "",
					numerointegracaobusca.trim());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return processoCPPROLista;
	}

	public void setProcessoCPPROLista(List<ProcessoCPPRO> processoCPPROLista) {
		this.processoCPPROLista = processoCPPROLista;
	}

	/**
	 * Lista os processos da base direta do CPPRO
	 * 
	 * @return
	 */
	public List<ProcessoCpproConsulta> BuscaProcessoCpproConsultas() {
		processoCpproConsultas = null;

		try {

			processoCpproConsultas = processoDaoConsultaCppro.consulta(processobusca, reubusca, autorbusca, "", "",
					numerointegracaobusca);

		} catch (Exception e) {
			// TODO: handle exception
		}

		return processoCpproConsultas;
	}

	/**
	 * Lista do processo n abase lEgal One
	 * 
	 * @param processoCpproConsultas
	 */

	public List<ProcessoCpproConsulta> BuscaProcessoCpproConsultasLegalOne() {
		processoCpproConsultas = null;
		try {
			processoCpproConsultas = processoDaoConsultaCppro.PegaProcessoLegalOne(processobusca, reubusca, autorbusca,
					"", "", numerointegracaobusca);

		} catch (Exception e) {
			// TODO: handle exception
		}

		return processoCpproConsultas;
	}

	public List<ProcessoCpproConsulta> getProcessoCpproConsultas() {
		return processoCpproConsultas;
	}

	public void setProcessoCpproConsultas(List<ProcessoCpproConsulta> processoCpproConsultas) {
		this.processoCpproConsultas = processoCpproConsultas;
	}

	/**
	 * Busca unico processo no cpj
	 * 
	 * @return
	 */
	public String Buscaprocessounicocpj() {

		idsolicitacaobusca = 0;
		idcomarca = 0;
		idorgao = 0;
		idnumerocomarca = 0;
		instrucoes = "";
		enviodesolicitacao = 0;
		tiposolictacao = 0;

		/**
		 * Aqui seta tudo para uma nova solictação
		 */
		listacomarca = null;
		listacomarca = null;
		tipoSolicitacaos = null;
		enviosolicitacaos = null;
		comarcasolicitacao = null;
		enviosolicitacaos = null;
		listaarquivos = null;
		listaentrada = null;
		listaarquivos = null;
		datarealizacao = null;
		listacorrespondente = null;

		getComarcasolicitacao();
		getEnviosolicitacaos();
		getListacomarca();
		getListaorgao();
		getTipoSolicitacaos();
		getEnviosolicitacaos();
		getListaentrada();
		getListaarquivos();
		getListacorrespondente();

		Converte fez = new Converte();
		processoDaoFB = new ProcessoDaoMysql(); // Cosulta o CPJ
		processoCPJ = new ProcessoCPJ();
		processoCPJ = processoDaoFB.BuscaUnico(numprocessocpj);
		processo = new Processo();
		processo.setNumeroprocesso(processoCPJ.getNumprocesso());
		processo.setNumeroprocessopesq(fez.traz(processoCPJ.getNumprocesso()));
		processo.setParte(processoCPJ.getNomeautor().trim());
		processo.setAdverso(processoCPJ.getNomereu().trim());
		processo.setNumeroprocessopesq(processoCPJ.getNumprocessopes());
		processo.setAssunto(processoCPJ.getAcao());
		processo.setNumerointegracao(processoCPJ.getNumerointegracao());
		processo.setCartorio(processoCPJ.getJuizo() + "-" + processoCPJ.getOjnumero() + "-" + processoCPJ.getOjsigla());
		processo.setLocalizacao(processoCPJ.getSiglaloc() + "-" + processoCPJ.getLocnumero());
		processo.setProceletronico(processoCPJ.getProceletronico());
		solicitacaonova = new Solicitacao();

		return "/solicitacao/novasolicitacao.jsf";
	}

	/**
	 * Busca u unico processo la lista do cppro
	 * 
	 * @return
	 */
	public String BuscaProcessoUnicoCPPRO() {
		listacomarca = null;
		listacomarca = null;
		tipoSolicitacaos = null;
		enviosolicitacaos = null;
		comarcasolicitacao = null;
		enviosolicitacaos = null;
		listaarquivos = null;
		listaentrada = null;
		listaarquivos = null;
		datarealizacao = null;
		listacorrespondente = null;
		String caminho = "";

		getComarcasolicitacao();
		getEnviosolicitacaos();
		getListacomarca();
		getListaorgao();
		getTipoSolicitacaos();
		getEnviosolicitacaos();
		getListaentrada();
		getListaarquivos();
		getListacorrespondente();
		Converte fez = new Converte();
		processo = new Processo();

		processoCPPRO = new ProcessoCPPRO();
		processoCPPRODAO = new ProcessoCPPRODAO();
		processoCpproConsulta = new ProcessoCpproConsulta();
		processoDaoConsultaCppro = new ProcessoDaoConsultaCppro();

		try {

			if (buscacppro == false) {
				for (ProcessoCpproConsulta solilegeal : processoCpproConsultas) {
					if (solilegeal.getCodigopasta() == numprocessocpj) {
						processoCpproConsulta = solilegeal;
					}
				}

			} else if (buscacppro == true) {
				processoCpproConsulta = processoDaoConsultaCppro.BuscaUnico(numprocessocpj); //
			}

			String pastasoli = alterarsolicitacao.getProcesso().getLocalizacao();
			String pastacp = processoCpproConsulta.getCodigopasta();

			if (pastasoli != pastacp) {
				processo = new Processo();
				processo.setNumeroprocesso(processoCpproConsulta.getNumerinical());
				processo.setNumeroprocessopesq(fez.traz(processoCpproConsulta.getNumerinical()));
				processo.setParte(processoCpproConsulta.getContrarioprincipal().trim());
				processo.setAdverso(processoCpproConsulta.getClienteprincipal().trim());
				processo.setAssunto(processoCpproConsulta.getTipoacaorito());
				processo.setNumerointegracao(processoCpproConsulta.getCodigopasta());
				processo.setCartorio(processoCpproConsulta.getOrgaoinicial());
				processo.setLocalizacao(processoCpproConsulta.getCodigopasta());
				processo.setQuantsoli(2);
				if (processoCpproConsulta.getProcessoeletronico().equalsIgnoreCase("SIM")) {
					processo.setProceletronico("E");
				} else if (processoCpproConsulta.getProcessoeletronico().equalsIgnoreCase("NÃO")) {
					processo.setProceletronico("N");
				}

				// Atualiza a parte eletronica

				if (alterasolicitacaodenovo = false) {
					solicitacaonova = new Solicitacao();

				} else if (alterasolicitacaodenovo = true) {

					processoDao.Salvar(processo);
					alterarsolicitacao.setProcesso(processo);
				}

				if (alterasolicitacaodenovo = false) {
					solicitacaonova = new Solicitacao();
					caminho = "/solicitacao/novasolicitacao.jsf";
				} else if (alterasolicitacaodenovo = true) {
					caminho = "/solicitacao/alteranovasolicitacao.jsf";

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return caminho;
	}

	/**
	 * Busca o processo unico da listagem do processod a base sqlserver
	 * 
	 * @return
	 */
	public String BuscaProcessoUnicoCPPROSQL() {
		listacomarca = null;
		listacomarca = null;
		tipoSolicitacaos = null;
		enviosolicitacaos = null;
		comarcasolicitacao = null;
		enviosolicitacaos = null;
		listaarquivos = null;
		listaentrada = null;
		listaarquivos = null;
		datarealizacao = null;
		listacorrespondente = null;

		getComarcasolicitacao();
		getEnviosolicitacaos();
		getListacomarca();
		getListaorgao();
		getTipoSolicitacaos();
		getEnviosolicitacaos();
		getListaentrada();
		getListaarquivos();
		getListacorrespondente();
		Converte fez = new Converte();
		processo = new Processo();
		processoCpproConsulta = new ProcessoCpproConsulta();
		processoDaoConsultaCppro = new ProcessoDaoConsultaCppro();
		try {
			if (buscacppro == true) {
				processoCpproConsulta = processoDaoConsultaCppro.BuscaUnico(buscapastaprocesso);// numprocessocpj
			} else if (buscacppro == false) {

				for (ProcessoCpproConsulta buscacon : processoCpproConsultas) {
					if (buscacon.getCodigopasta().equals(buscapastaprocesso)) {
						processoCpproConsulta = buscacon;
					}

				}

			}

			processo = new Processo();
			processo.setNumeroprocesso(processoCpproConsulta.getNumerinical());
			processo.setNumeroprocessopesq(fez.traz(processoCpproConsulta.getNumerinical()));
			processo.setParte(processoCpproConsulta.getContrarioprincipal().trim());
			processo.setAdverso(processoCpproConsulta.getClienteprincipal().trim());
			processo.setNumeroprocessopesq(fez.traz(processoCpproConsulta.getNumerinical()));
			processo.setAssunto(processoCpproConsulta.getTipoacaorito());
			processo.setNumerointegracao(processoCpproConsulta.getPastacliente());
			processo.setCartorio(processoCpproConsulta.getOrgaoinicial());
			processo.setLocalizacao(processoCpproConsulta.getCodigopasta());
			// Atualiza a parte eletronica
			if (processoCpproConsulta.getProcessoeletronico().equalsIgnoreCase("SIM")) {
				processo.setProceletronico("E");
			} else if (processoCpproConsulta.getProcessoeletronico().equalsIgnoreCase("NÃO")) {
				processo.setProceletronico("N");
			} else if (processoCpproConsulta.getProcessoeletronico() == null) {
				processo.setProceletronico("N");

			}

			// solicitacaonova = new Solicitacao();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		solicitacaonova = new Solicitacao();

		return "/solicitacao/novasolicitacao.jsf";
	}

	/**
	 * Altera o processo do cppro para caso de dar msg como processo eletronico
	 * 
	 * @return
	 */
	public String AlteraProcessoCppro() {
		Converte fez = new Converte();
		processo = new Processo();
		processoCpproConsulta = processoDaoConsultaCppro
				.BuscaUnico(buscapastaprocesso.trim().toUpperCase().replace(" ", ""));
		// processo =
		// processoDao.trazprocesso(buscapastaprocesso.trim().toUpperCase().replace(" ",
		// ""));
		processo = processoDao.trazprocesso(buscapastaprocesso);
		if (processo != null) {
			if (processo.getIdprocesso() > 0) {
				processo.setNumeroprocesso(processoCpproConsulta.getNumerinical());
				processo.setNumeroprocessopesq(fez.traz(processoCpproConsulta.getNumerinical()));
				processo.setParte(processoCpproConsulta.getContrarioprincipal().trim());
				processo.setAdverso(processoCpproConsulta.getClienteprincipal().trim());
				processo.setNumeroprocessopesq(fez.traz(processoCpproConsulta.getNumerinical()));
				processo.setAssunto(processoCpproConsulta.getTipoacaorito());
				processo.setNumerointegracao(processoCpproConsulta.getPastacliente());
				processo.setCartorio(processoCpproConsulta.getOrgaoinicial());
				processo.setLocalizacao(processoCpproConsulta.getCodigopasta());
				// Atualiza a parte eletronica
				if (processoCpproConsulta.getProcessoeletronico().equalsIgnoreCase("SIM")) {
					processo.setProceletronico("E");
				} else if (processoCpproConsulta.getProcessoeletronico().equalsIgnoreCase("NÃO")) {
					processo.setProceletronico("N");
				}

			}

			processoDao.Alterar(processo);

			FacesContext cont1 = FacesContext.getCurrentInstance();
			cont1.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "processo atualizado com sucesso.", ""));
		} else {
			FacesContext cont1 = FacesContext.getCurrentInstance();
			cont1.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Processo não pode ser atualizado ou existe N processos repetidos no SISGECOL.", ""));

		}

		return "";

	}

	/**
	 * 
	 * METODO USADO NA TELA ALTERA NOVASOLCITITACAO PARA ALTERACAO UMA SOLICITACAO
	 * JA EXISTENTE .11/06/2014
	 * 
	 * @return
	 */

	public String AlteraSolinovasolicitacao() {
		idsolicitacaobusca = 0;
		idcomarca = 0;
		idorgao = 0;
		idnumerocomarca = 0;
		instrucoes = "";
		enviodesolicitacao = 0;
		tiposolictacao = 0;
		// Aqui pega a antiga pra ao log

		/**
		 * Aqui seta tudo para uma nova solictação
		 * 
		 * listacomarca = null; listacomarca = null; tipoSolicitacaos = null;
		 * enviosolicitacaos = null; comarcasolicitacao = null; enviosolicitacaos =
		 * null; listaarquivos = null; listaentrada = null; listaarquivos = null;
		 * datarealizacao = null;
		 */

		getComarcasolicitacao();
		getEnviosolicitacaos();
		getListacomarca();
		getListaorgao();
		getTipoSolicitacaos();
		getEnviosolicitacaos();
		getListaentrada();
		getListaarquivos();

		Converte fez = new Converte();
		/**
		 * if (buscacppro == false) { processoDaoFB = new ProcessoDaoMysql(); // Cosulta
		 * o CPJ processoCPJ = new ProcessoCPJ();
		 * 
		 * processoCPJ = processoDaoFB.BuscaUnico(numprocessocpj); processo = new
		 * Processo();
		 * 
		 * processo.setNumeroprocesso(processoCPJ.getNumprocesso());
		 * processo.setNumeroprocessopesq(fez.traz(processoCPJ.getNumprocesso()));
		 * processo.setParte(processoCPJ.getNomeautor());
		 * processo.setAdverso(processoCPJ.getNomereu());
		 * processo.setNumeroprocessopesq(processoCPJ.getNumprocessopes());
		 * processo.setAssunto(processoCPJ.getAcao());
		 * processo.setNumerointegracao(processoCPJ.getNumerointegracao());
		 * processo.setCartorio( processoCPJ.getJuizo() + "-" +
		 * processoCPJ.getOjnumero() + "-" + processoCPJ.getOjsigla());
		 * processo.setLocalizacao(processoCPJ.getSiglaloc() + "-" +
		 * processoCPJ.getLocnumero());
		 * processo.setProceletronico(processoCPJ.getProceletronico());
		 * alterarsolicitacao.setProcesso(processo);
		 * 
		 * } else
		 **/
		// if (buscacppro == true) {
		processoCPPRO = new ProcessoCPPRO();
		processoCPPRODAO = new ProcessoCPPRODAO();
		try {
			processoCPPRO = processoCPPRODAO.BuscarProcesso(numprocessocpj);
			processo = new Processo();
			processo.setNumeroprocesso(processoCPPRO.getNumprocesso());
			processo.setNumeroprocessopesq(fez.traz(processoCPPRO.getNumprocesso()));
			processo.setParte(processoCPPRO.getPartecontraria().trim());
			processo.setAdverso(processoCPPRO.getCliente().trim());
			processo.setNumeroprocessopesq(fez.traz(processoCPPRO.getNumprocesso()));
			processo.setAssunto(processoCPPRO.getAcao());
			processo.setNumerointegracao(processoCPPRO.getLocalizador());
			processo.setCartorio(processoCPPRO.getOrgaoinicial());
			processo.setLocalizacao(processoCPPRO.getLocalizador());

			// Atualiza a parte eletronica
			if (processoCPPRO.getEletronico().equalsIgnoreCase("SIM")) {
				processo.setProceletronico("E");
			} else if (processoCPPRO.getEletronico().equalsIgnoreCase("NÃO")) {
				processo.setProceletronico("N");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// }

		return "/solicitacao/alteranovasolicitacao.jsf";
	}

	/**
	 * Cria novo processo na base para fazer a solicitação
	 * 
	 * @return
	 */
	public String NovoProcessoSolictacao() {

		idsolicitacaobusca = 0;
		idcomarca = 0;
		idorgao = 0;
		idnumerocomarca = 0;
		instrucoes = "";
		enviodesolicitacao = 0;
		tiposolictacao = 0;

		/**
		 * Aqui seta tudo para uma nova solictação
		 */
		listacomarca = null;
		listacomarca = null;
		tipoSolicitacaos = null;
		enviosolicitacaos = null;
		comarcasolicitacao = null;
		enviosolicitacaos = null;
		listaarquivos = null;
		listaentrada = null;
		listaarquivos = null;
		datarealizacao = null;
		getComarcasolicitacao();
		getEnviosolicitacaos();
		getListacomarca();
		getListaorgao();
		getTipoSolicitacaos();
		getEnviosolicitacaos();
		getListaentrada();
		getListaarquivos();
		solicitacaonova = new Solicitacao();
		return "/solicitacao/novasolicitacao.jsf";
	}

	/**
	 * Cadastra um novo processo inexistente na base do CPJ
	 */
	public String NovoProcesso() {
		processo = new Processo();
		processo.setNumerointegracao("0000000");
		processo.setProceletronico("N");
		return "/processo/cadprocesso.jsf";

	}

	public void setProcessocpj(List<ProcessoCPJ> processocpj) {
		this.processocpj = processocpj;
	}

	public ProcessoDaoMysql getProcessoDaoFB() {
		return processoDaoFB;
	}

	public void setProcessoDaoFB(ProcessoDaoMysql processoDaoFB) {
		this.processoDaoFB = processoDaoFB;
	}

	public ProcessoCPJ getProcessoCPJ() {
		return processoCPJ;
	}

	public void setProcessoCPJ(ProcessoCPJ processoCPJ) {
		this.processoCPJ = processoCPJ;
	}

	/**
	 * Lista os estatus das solicitações
	 * 
	 * @return
	 */
	public List<StatusSolicitacao> getStatussolicitacoes() {
		statussolicitacoes = statusSolictacaoDao.buscargeral();
		return statussolicitacoes;
	}

	public void setStatussolicitacoes(List<StatusSolicitacao> statussolicitacoes) {
		this.statussolicitacoes = statussolicitacoes;
	}

	public Integer getIdstatus() {
		return idstatus;
	}

	public void setIdstatus(Integer idstatus) {
		this.idstatus = idstatus;
	}

	public StatusSolicitacao getStatusSolicitacao() {
		return statusSolicitacao;
	}

	public void setStatusSolicitacao(StatusSolicitacao statusSolicitacao) {
		this.statusSolicitacao = statusSolicitacao;
	}

	/**
	 * Busca os processo no cppro ou no legal one
	 */
	public void Buscadadoscpj() {
		processoCpproConsultas = null;

		if (buscacppro == true) {
			BuscaProcessoCpproConsultas();
		} else if (buscacppro == false) {
			BuscaProcessoCpproConsultasLegalOne();
		}
		// return "";
	}

	public String getNumprocessocpj() {
		return numprocessocpj;
	}

	public void setNumprocessocpj(String numprocessocpj) {
		this.numprocessocpj = numprocessocpj;
	}

	/**
	 * Busca unica comarca através do parametro idcomarca
	 */
	public void BuscComarcaPossui() {
		comarcasolicitacao = null;
		comarcasolicitacao = possuiComarcaDao.buscarcomcorr(idcomarca);// buscargeral(0,

	}

	/**
	 * Busca os envio na solicitações
	 */
	public void BuscarTipode() {
		getEnviosolicitacaos();
	}

	/**
	 * Nao mostra as falsas
	 */
	public void BuscaTipoSolicitacao() {
		renumeracao = null;
		renumeracao = renumeracaoDao.buscargeral(idcorrespondente, false);
	}

	/**
	 * Mostra todas as solicitações verdadeiras
	 */
	public void BuscaTipoSolicitacaoTodas() {
		renumeracao = null;
		renumeracao = renumeracaoDao.buscargeral(idcorrespondente, true);
	}

	public List<Renumeracao> getRenumeracao() {
		return renumeracao;
	}

	public void setRenumeracao(List<Renumeracao> renumeracao) {
		this.renumeracao = renumeracao;
	}

	/**
	 * Traz uma renumeracao
	 * 
	 * @return
	 */
	public List<ComarcaPossui> getComarcasolicitacao() {
		comarcasolicitacao = possuiComarcaDao.buscarcomcorr(idcomarca);
		return comarcasolicitacao;
	}

	public void setComarcasolicitacao(List<ComarcaPossui> comarcasolicitacao) {
		this.comarcasolicitacao = comarcasolicitacao;
	}

	public String getCnpjcomarca() {
		return cnpjcomarca;
	}

	public void setCnpjcomarca(String cnpjcomarca) {
		this.cnpjcomarca = cnpjcomarca;
	}

	public Integer getTiposolictacao() {
		return tiposolictacao;
	}

	public void setTiposolictacao(Integer tiposolictacao) {
		this.tiposolictacao = tiposolictacao;
	}

	public Solicitacao getUnicasolicitacao() {
		// if (unicasolicitacao==null){
		// unicasolicitacao = solicitacaoDao.trazrsolicitacao(bnumero);
		// }
		return unicasolicitacao;
	}

	/**
	 * Traz unica solicitacao do correspondente
	 * 
	 * @return
	 */
	public String BuscaUnica() {
		// getUnicasolicitacao();
		try {
			unicasolicitacao = null;
			unicasolicitacao = solicitacaoDao.trazrsolicitacao(bnumero);
			mostraauditexto = unicasolicitacao.isAudinterna();
			observacaofinal = unicasolicitacao.getObservacao();
			return "/solicitacao/dadosolicitacao.jsf";

		} catch (Exception e) {
			// TODO: handle exception

			FacesContext cont1 = FacesContext.getCurrentInstance();
			cont1.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Erro ao buscar solicitação favor sair do sistema e entrar novamente." + "\n"
									+ "Caso persistir o erro entre em contato com o suporte do desenvolvimento",
							""));

			return "/solicitacao/dadosolicitacao.jsf";
		}

	}

	/**
	 * Traz unica solicitacao do correspondente MOBILE DADOS DA SOLICITACAO
	 * 25/06/2014
	 * 
	 * @return
	 */
	public String BuscaUnicamobile() {
		// getUnicasolicitacao();

		unicasolicitacao = null;
		unicasolicitacao = solicitacaoDao.trazrsolicitacao(bnumero);

		observacaofinal = unicasolicitacao.getObservacao();
		return "/mobile/dadosolicitacaocel.jsf";

	}

	/**
	 * Traz a solicitacao pra alteracao
	 * 
	 * @return
	 */
	public String BuscaUnicaAlteracao() {
		// getAlterarsolicitacao();

		try {

			alterarsolicitacao = null;
			alterarsolicitacao = solicitacaoDao.trazrsolicitacao(bnumero);
			mostraauditexto = alterarsolicitacao.isAudinterna();
			// daoArquivoCPRO.setJacarregou(false);
			TrazAudInterna();

			// if (listarquivocppro==null){
			// listarquivocppro = new ArrayList<ArquivoAnexoCPRO>();
			// listarquivocppro=daoArquivoCPRO.Consulta(alterarsolicitacao.getProcesso().getLocalizacao());
			// }
			// arquivoAnexoCPROSalvos = null;
			// arquivoAnexoCPROSalvos =
			// soliArqCproDaoSalvo.traztodos(alterarsolicitacao.getProcesso().getLocalizacao(),
			// alterarsolicitacao.getIdsolicitacao());

			return "/solicitacao/alterasolicitacao.jsf";

		} catch (Exception e) {
			// TODO: handle exception

			FacesContext cont1 = FacesContext.getCurrentInstance();
			cont1.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Erro ao buscar solicitação favor sair do sistema e entrar novamente." + "\n"
									+ "Caso persistir o erro entre em contato com o suporte do desenvolvimento",
							""));
			return "/solicitacao.jsf";
			// return "/solicitacao/alterasolicitacao.jsf";

		}

	}

	/**
	 * Traz o formulario da slicitacao tipo audiencia ja concluído
	 */
	public String BuscaFormulario() {
		unicasolicitacao = null;
		unicasolicitacao = solicitacaoDao.trazrsolicitacao(bnumero);
		return "/solicitacao/formularioaudiencia.jsf";
	}

	/**
	 * Traz a solicitacao pra alteracao-TELA ALTERASOLICITACAOTESTECORRESPONDENTE
	 * 27/06/204
	 * 
	 * @return
	 */
	public String BuscaunicaAlteraTESTECORRES() {
		// getAlterarsolicitacao();
		alterarsolicitacao = null;
		alterarsolicitacao = solicitacaoDao.trazrsolicitacao(bnumero);
		return "/alterasolicitacaoTesteCorrespondente.jsf";

	}

	public void setUnicasolicitacao(Solicitacao unicasolicitacao) {
		this.unicasolicitacao = unicasolicitacao;
	}

	/*
	 * Sai para a tela de processos da busca
	 */
	public String Sairprocessocpj() {
		try {
			processoDaoFB.close();
			processoDaoFB = null;
			processocpj = null;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/processo/processocpj.jsf";

	}

	/**
	 * SAIR TELA -- NOVASOLI 16/06/14
	 * 
	 * 
	 */

	public String sairnovasoli() {

		return "/solicitacao/novasolicitacao.jsf";

	}

	/**
	 * Sair tela alteranovasoli 16/06/2014
	 */
	public String SairAlteranovasoli() {
		return "/solicitacao/solicitacao.jsf";
	}

	/**
	 * Sair da tela pra voltar
	 */
	public String VoltarSolicitacoes() {

		return "/solicitacao/solicitacao.jsf";

	}

	/*
	 * Sair tela alteranovasoli 16/06/2014
	 */
	public String sairalteranovasoli() {
		return "/solicitacao/alteranovasolicitacao.jsf";
	}

	/**
	 * Sair para as solitacoes feitas
	 * 
	 * @return
	 */
	public String SairParaSoliciatcao() {
		try {
			processoDaoFB.close();
			processoDaoFB = null;
			processocpj = null;
			processobusca = "";
			numerointegracao = "";
			autorbusca = "";
			reubusca = "";
			// FacesContext.getCurrentInstance().getExternalContext().redirect("/solicitacao/solicitacao.jsf");
			// FacesContext.getCurrentInstance().addMessage(null, new
			// FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Saindo da tela."));
		} catch (Exception e) {
			// FacesContext.getCurrentInstance().addMessage(null, new
			// FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Erro ai sair da tela."));
			// TODO: handle exception
		}
		return "/solicitacao/solicitacao.jsf";
	}

	/**
	 * Sair DIALOG ALTERANOVASOLI 16/06/2014
	 * 
	 * @return
	 */
	public String Sairalteranovasoli() {
		processobusca = "";
		numerointegracao = "";
		autorbusca = "";
		reubusca = "";
		return "/solicitacao/alteranovasolicitacao.jsf";
	}

	public String getProcessonotribunal() {
		return processonotribunal;
	}

	public Integer getTipounicasolicitacao() {

		return tipounicasolicitacao;
	}

	public void setTipounicasolicitacao(Integer tipounicasolicitacao) {
		this.tipounicasolicitacao = tipounicasolicitacao;
	}

	public void setProcessonotribunal(String processonotribunal) {
		this.processonotribunal = processonotribunal;
	}

	public boolean isAlterasolicitacaodenovo() {
		return alterasolicitacaodenovo;
	}

	public void setAlterasolicitacaodenovo(boolean alterasolicitacaodenovo) {
		this.alterasolicitacaodenovo = alterasolicitacaodenovo;
	}

	/**
	 * Faz a comsulta no site do tj para analisar o processo
	 * 
	 * @return
	 */
	public String CosultaProcessoSite() {
		FacesContext context = FacesContext.getCurrentInstance();

		String site = "http://www4.tjrj.jus.br/consultaProcessoWebV2/consultaProc.do?v=2&FLAGNOME=&back=1&tipoConsulta=publica&numProcesso="
				+ processonotribunal;

		try {
			context.getExternalContext().redirect(site);
			// return site;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		return "";
	}

	/**
	 * Envia os arquivos para o servidor da nova solicitação
	 * 
	 * @param event
	 * @throws FileNotFoundException
	 */
	public void EnviarArquivos(FileUploadEvent event) throws FileNotFoundException {
		if (nova_soli_salva == true) {

			Solicitacao solicitacaobusca = new Solicitacao();
			solicitacaobusca = null;
			solicitacaobusca = solicitacaoDao.trazrsolicitacao(idsolicitacaobusca);

			UploadedFile arquivoanexo = event.getFile();
			File arquivocja = new File(arquivoanexo.getFileName());
			Usuario usuariologado = new Usuario();
			usuariologado = usuarioDao.trazusuario(usuario.getLogin());

			try {
				InputStream inputStream = arquivoanexo.getInputstream();

				// event.getFile().getInputstream();
				// "G:\\arqsolicitacao\\enviado\\"
				// Foi mudado para a letra I devido ao espaco do ged esta cheio em 13/08/2019
				OutputStream out = new FileOutputStream(new File("I:\\arqsolicitacao\\enviado\\"
						+ solicitacaobusca.getIdsolicitacao() + arquivocja.getAbsoluteFile().getName()));
				int read = 0;
				byte[] bytes = new byte[1024];
				while ((read = inputStream.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
				inputStream.close();
				out.flush();
				out.close();
				solicitacaoAnexo = new SolicitacaoAnexo();
				SolicitacaoPossuiArquivo novo = new SolicitacaoPossuiArquivo();
				solicitacaoAnexo.setDatasolicitacao(datasolicitacao);
				solicitacaoAnexo
						.setNomearquivo(solicitacaobusca.getIdsolicitacao() + arquivocja.getAbsoluteFile().getName());
				solicitacaoAnexo.setLinkarquivo("I:\\arqsolicitacao\\enviado\\" + solicitacaobusca.getIdsolicitacao()
						+ arquivocja.getAbsoluteFile().getName());
				solicitacaoAnexo.setOperacao("ENVIADO"); // Envio para arquivos
				solicitacaoAnexo.setOrigemarq(1);
				solicitacaoAnexo.setUsuario(usuariologado);
				// enviados aos
				// correspondente -
				// Recebido para
				// arquvios recebidos da
				// resposta da
				// solicitacao
				soliArquivoDao.SalvaArquivo(solicitacaoAnexo);
				novo.setSolicitacao(solicitacaobusca);
				novo.setSolicitacaoAnexo(solicitacaoAnexo);
				SoliArquivo arquivo = new SoliArquivo();
				arquivo.setSolicitacaoPossuiArquivo(novo);
				soliArquivoDao.Salvar(arquivo);
				// idsolicitacaobusca = solicitacaonova.getIdsolicitacao();
				getListaarquivos();
				FacesContext cont1 = FacesContext.getCurrentInstance();
				cont1.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Arquivos adcionados com sucesso no servidor.", ""));
				solicitacaobusca = null;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				FacesContext cont1 = FacesContext.getCurrentInstance();
				cont1.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error ao salvar arquivo no servidor tente novamente. "
										+ "Caso o erro persista, favor entrar em contato com o suporte técnico do "
										+ "CRA - suporte@cra.adv.br",
								""));
				solicitacaobusca = null;
			}

		} else {
			FacesContext cont1 = FacesContext.getCurrentInstance();
			cont1.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Registre  a solicitacao antes de anexar os arquivos depois não precisa registar novamente.", ""));

		}

	}

	/**
	 * Envia arquibvos novos da solcitacao para o colaborado externo
	 * 
	 * @param event
	 * @throws FileNotFoundException
	 */
	public String EnviarArquivosNovos(FileUploadEvent event) throws FileNotFoundException {

		UploadedFile arquivoanexo = event.getFile();
		Date datanova = new Date();

		File arquivocja = new File(arquivoanexo.getFileName());
		Usuario usuariologado = new Usuario();
		usuariologado = usuarioDao.trazusuario(usuario.getLogin());
		try {
			InputStream inputStream = arquivoanexo.getInputstream();

			// event.getFile().getInputstream();
			// "G:\\arqsolicitacao\\enviado\\"
			// Foi mudado para a letra I devido ao espaco do ged esta cheio em 13/08/2019
			OutputStream out = new FileOutputStream(new File("I:\\arqsolicitacao\\enviado\\"
					+ alterarsolicitacao.getIdsolicitacao() + arquivocja.getAbsoluteFile().getName()));
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			inputStream.close();
			out.flush();
			out.close();

			solicitacaoAnexo = new SolicitacaoAnexo();
			SolicitacaoPossuiArquivo novo = new SolicitacaoPossuiArquivo();
			solicitacaoAnexo.setDatasolicitacao(datanova);
			solicitacaoAnexo
					.setNomearquivo(alterarsolicitacao.getIdsolicitacao() + arquivocja.getAbsoluteFile().getName());
			solicitacaoAnexo.setLinkarquivo("I:\\arqsolicitacao\\enviado\\" + alterarsolicitacao.getIdsolicitacao()
					+ arquivocja.getAbsoluteFile().getName());
			solicitacaoAnexo.setOperacao("ENVIADO"); // Envio para arquivos
			solicitacaoAnexo.setOrigemarq(1);
			solicitacaoAnexo.setDatasolicitacao(new Date());
			solicitacaoAnexo.setUsuario(usuariologado);

			soliArquivoDao.SalvaArquivo(solicitacaoAnexo);
			novo.setSolicitacao(alterarsolicitacao);
			novo.setSolicitacaoAnexo(solicitacaoAnexo);
			SoliArquivo arquivo = new SoliArquivo();
			arquivo.setSolicitacaoPossuiArquivo(novo);
			soliArquivoDao.Salvar(arquivo);

			idsolicitacaobusca = alterarsolicitacao.getIdsolicitacao();
			getListaarquivos();

			FacesContext cont1 = FacesContext.getCurrentInstance();
			cont1.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Arquivos adcionados com sucesso no servidor", ""));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			FacesContext cont1 = FacesContext.getCurrentInstance();
			cont1.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error ao salvar arquivo no servidor tente novamente. "
									+ "Caso o erro persista, favor entrar em contato com o suporte técnico do "
									+ "CRA - suporte@cra.adv.br",
							"Erro"));
		}

		return "";
	}

	/**
	 * Envia arquivos do correspondente para o servidor oara ser aberto por eles
	 * 
	 * @param event
	 * @throws FileNotFoundException
	 */
	public String AnexarArquivos(FileUploadEvent event) throws FileNotFoundException {
		datasolicitacao = new Date();

		if (unicasolicitacao.getIdsolicitacao() > 0) {
			UploadedFile arquivoanexo = event.getFile();
			File arquivocorrespondente = new File(arquivoanexo.getFileName());

			try {
				InputStream inputStream = arquivoanexo.getInputstream();

				// event.getFile().getInputstream();
				// "G:\\arqsolicitacao\\recebido\\"
				// Foi mudado para a letra I devido ao espaco do ged esta cheio em 13/08/2019
				OutputStream out = new FileOutputStream(new File("I:\\arqsolicitacao\\recebido\\"
						+ unicasolicitacao.getIdsolicitacao() + arquivocorrespondente.getAbsoluteFile().getName())); // arquivoanexo.getFileName()
				int read = 0;
				byte[] bytes = new byte[1024];
				while ((read = inputStream.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
				inputStream.close();
				out.flush();
				out.close();
				solicitacaoAnexo = new SolicitacaoAnexo();
				SolicitacaoPossuiArquivo novo = new SolicitacaoPossuiArquivo();
				solicitacaoAnexo.setDatasolicitacao(datasolicitacao);
				solicitacaoAnexo.setNomearquivo(
						unicasolicitacao.getIdsolicitacao() + arquivocorrespondente.getAbsoluteFile().getName());
				solicitacaoAnexo.setLinkarquivo("I:\\arqsolicitacao\\recebido\\" + unicasolicitacao.getIdsolicitacao()
						+ arquivocorrespondente.getAbsoluteFile().getName());
				solicitacaoAnexo.setOperacao("RECEBIDO"); // Envio para arquivos
				solicitacaoAnexo.setOrigemarq(1);
				solicitacaoAnexo.setDatasolicitacao(new Date());

				soliArquivoDao.SalvaArquivo(solicitacaoAnexo);
				novo.setSolicitacao(unicasolicitacao);
				novo.setSolicitacaoAnexo(solicitacaoAnexo);
				SoliArquivo arquivo = new SoliArquivo();
				arquivo.setSolicitacaoPossuiArquivo(novo);

				soliArquivoDao.Salvar(arquivo);
				idsolicitacaobusca = unicasolicitacao.getIdsolicitacao();
				getListaarquivos();

				FacesContext cont1 = FacesContext.getCurrentInstance();
				cont1.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Arquivos adcionados com sucesso no servidor", ""));

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				FacesContext.getCurrentInstance().addMessage("xc:msg3",
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error ao salvar arquivo no servidor tente novamente. "
										+ "Caso o erro persista, favor entrar em contato com o suporte técnico do "
										+ "CRA - suporte@cra.adv.br",
								"Erro"));
			}
		} else {
			FacesContext.getCurrentInstance().addMessage("xc:msg3",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Salve a solicitacao antes"));
		}

		return "";
	}

	/**
	 * Baixa arquivo do servidor
	 * 
	 * @param nome
	 * @throws FileNotFoundException
	 */
	public String BaixarArquivo(String linkarquivo, String tipobaixar) {

		// Cria a intanci para baixar o arquivo
		DownloadArquivo baixar = new DownloadArquivo();

		try {
			// Baixa do correspondente
			if (tipobaixar.equals("RECEBIDO")) {
				// baixar.Abrir("G:\\arqsolicitacao\\recebido\\" +
				// arquivoparabaixar, "", false);
				baixar.Abrir(linkarquivo, "", false);
			}
			// Baixa do solicitante
			if (tipobaixar.equals("ENVIADO")) {
				baixar.Abrir(linkarquivo, "", false);
			}

		} catch (FileNotFoundException e1) {

			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao baixar o arquivo anexo tente novamente", ""));
		}

		arquivoparabaixar = "";
		return "";
	}

	/**
	 * Baixa arquivo do servidor nat tela de elaboracao
	 * 
	 * @param nome
	 * @throws FileNotFoundException
	 */
	public String BaixarArquivoCPRO(String Caminho, String tipobaixar) {
		if (Caminho != "") {

			// Cria a intanci para baixar o arquivo
			DownloadArquivo baixar = new DownloadArquivo();

			try {
				// Baixa do correspondente
				if (tipobaixar.equals("RECEBIDO")) {
					baixar.Abrir(Caminho, "", false);

				}
				// Baixa do solicitante
				if (tipobaixar.equals("ENVIADO")) {
					baixar.Abrir(Caminho, "", false);

				}

			} catch (FileNotFoundException e1) {

				FacesContext cont1 = FacesContext.getCurrentInstance();
				cont1.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Erro ao baixar o arquivo anexo tente novamente erro: " + e1.getMessage(), ""));
			}
			arquivoparabaixar = "";
			arquivoparaenviar = "";
			return "";
		}
		return "";
	}

	/**
	 * Este procedimento faz um copia do arquivo fisico do servidor do CPPRO para o
	 * servidor d sisgecole ataualiza as informções nas tabelas
	 * 
	 * @param Caminho
	 * @param NomeArquivo
	 * @param tipobaixar
	 * @param idarqcpprosalvo
	 * @param idsolicitacao
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String LiberarArquivoBaixarArquivoCPRO(String Caminho, String NomeArquivo, String tipobaixar,
			Integer idarqcpprosalvo, Integer idsolicitacao) {
		// Esse if foi elaborado para verificar se o arquivo esta co m nome
		// vazio se estiver nao faz o procedimento
		// o CP-PRO foi descontinuado
		if (NomeArquivo != "") {
			try {

				// Verifica se as variaveis tem valor nulo antes de processar
				if ((Caminho == null) || (NomeArquivo == null) || (tipobaixar == null) || (idarqcpprosalvo == null)
						|| (idsolicitacao == null)) {
					FacesContext cont1 = FacesContext.getCurrentInstance();
					cont1.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Erro interno no sistema feche o navegador e tente de novamente baixar os arquivos", ""));
					return "";

				}

				// Cria novo objeto do arquvo
				HistArqCpro hist = new HistArqCpro();
				soliHistArqCproDao = new SoliHistArqCproDao();
				SolicitacaoAnexo solicitacaoAnexo = new SolicitacaoAnexo();
				SolicitacaoPossuiArquivo novo = new SolicitacaoPossuiArquivo();
				SoliArquivo arquivo = new SoliArquivo();
				histArqCpro = new HistArqCpro();
				ArquivoAnexoCPROSalvo atualiza = new ArquivoAnexoCPROSalvo();
				soliArqCproDaoSalvo = new SoliArqCproDaoSalvo();

				hist = soliHistArqCproDao.BuscaUnico(idarqcpprosalvo);
				// Verifica se for nulo caso for nao faz
				if (hist == null) {
					// Copia o arquvo do cprpo para o sisgecol
					CopiaArqDestino copia = new CopiaArqDestino();
					File origem = new File(Caminho);

					// File destino = new File("c:\\telas\\" +
					// alterarsolicitacao.getIdsolicitacao() + NomeArquivo);
					File destino = new File(
							"I:\\arqsolicitacao\\enviado\\" + alterarsolicitacao.getIdsolicitacao() + NomeArquivo);

					// Documentei aqui em 10/06/2019
					origem.getAbsoluteFile();
					destino.getAbsoluteFile();
					copia.CopiaFromDestino(origem, destino);

					// Salva o arquivo em anexo
					solicitacaoAnexo.setDatasolicitacao(datasolicitacao);
					solicitacaoAnexo.setNomearquivo(alterarsolicitacao.getIdsolicitacao() + NomeArquivo);
					solicitacaoAnexo.setOperacao("ENVIADO"); // Envio para
																// arquivos
					solicitacaoAnexo.setLinkarquivo(destino.getAbsoluteFile().toString());
					solicitacaoAnexo.setOrigemarq(2); // Origem cppr
					solicitacaoAnexo.setDatasolicitacao(new Date());
					soliArquivoDao.SalvaArquivo(solicitacaoAnexo);

					// Cria uma nova instacia da classe
					histArqCpro.setSolicitacaoAnexo(solicitacaoAnexo);
					histArqCpro.setIdarquivocppro(idarqcpprosalvo);
					histArqCpro.setSalvoem(new Date());

					soliHistArqCproDao.Salvar(histArqCpro);

					atualiza = soliArqCproDaoSalvo.BuscaUnico(idarqcpprosalvo);
					atualiza.setBaixadoEm(new Date());
					atualiza.setBaixado(true);
					atualiza.setIdsolicitacao(alterarsolicitacao.getIdsolicitacao());
					soliArqCproDaoSalvo.Alterar(atualiza);
					/*
					 * Salva o historico do aruvio do cprpo
					 */

					novo.setSolicitacao(alterarsolicitacao);
					novo.setSolicitacaoAnexo(solicitacaoAnexo);

					arquivo.setSolicitacaoPossuiArquivo(novo);
					soliArquivoDao.Salvar(arquivo);

					idsolicitacaobusca = alterarsolicitacao.getIdsolicitacao();
					// Traz a listagem salva dos arquivos
					arquivoAnexoCPROSalvos = null;
					arquivoAnexoCPROSalvos = soliArqCproDaoSalvo.traztodos(
							alterarsolicitacao.getProcesso().getLocalizacao(), alterarsolicitacao.getIdsolicitacao());
					context = FacesContext.getCurrentInstance();
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Arquivo adicionado com sucesso para o colaborador", ""));
					System.out.print("Arquivos copiados....");

				} else {

					context = FacesContext.getCurrentInstance();
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"O arquivos ja foi adicionado na solicitação", ""));
				}

			} catch (Exception e) {
				context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Erro no sistema feche o navegador e tente de novo: " + e.getMessage(), ""));
			}

			getListaarquivos();
			return "";
		}
		return "";

	}

	@SuppressWarnings("static-access")
	/**
	 * Copia os arquivos diretos do ged do cppro pro ged do sisgecol sem passar pelo
	 * historico Agiliza o trabalho
	 * 
	 * @param pastaprocesso
	 * @return
	 */
	public String LiberaDireto(String pastaprocesso) {
		try {
			listacproarqtodos = daoArquivoCPRO.CosultaTodos(pastaprocesso);
			Usuario usuariologado = new Usuario();
			usuariologado = usuarioDao.trazusuario(usuario.getLogin());
			for (Integer cont = 0; cont < listacproarqtodos.size(); cont++) {
				SolicitacaoAnexo solicitacaoAnexo = new SolicitacaoAnexo();
				SoliArquivo arquivo = new SoliArquivo();
				CopiaArqDestino copia = new CopiaArqDestino();
				SolicitacaoPossuiArquivo novo = new SolicitacaoPossuiArquivo();
				File origem = new File(listacproarqtodos.get(cont).getCaminhoGed());
				// Foi mudado para a letra I devido ao espaco do ged esta cheio em 13/08/2019
				File destino = new File("I:\\arqsolicitacao\\enviado\\" + alterarsolicitacao.getIdsolicitacao()
						+ listacproarqtodos.get(cont).getNomeDocumento());

				origem.getAbsoluteFile();
				destino.getAbsoluteFile();

				copia.CopiaFromDestino(origem, destino);
				solicitacaoAnexo.setDatasolicitacao(datasolicitacao);
				solicitacaoAnexo.setNomearquivo(
						alterarsolicitacao.getIdsolicitacao() + listacproarqtodos.get(cont).getNomeDocumento());
				solicitacaoAnexo.setOperacao("ENVIADO"); // Envio para
															// arquivos
				solicitacaoAnexo.setLinkarquivo(destino.getAbsoluteFile().toString());
				solicitacaoAnexo.setOrigemarq(2); // Origem cp-pro
				solicitacaoAnexo.setDatasolicitacao(new Date());
				solicitacaoAnexo.setUsuario(usuariologado);
				soliArquivoDao.SalvaArquivo(solicitacaoAnexo);

				novo.setSolicitacao(alterarsolicitacao);
				novo.setSolicitacaoAnexo(solicitacaoAnexo);

				arquivo.setSolicitacaoPossuiArquivo(novo);
				soliArquivoDao.Salvar(arquivo);
				idsolicitacaobusca = alterarsolicitacao.getIdsolicitacao();

			}

			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Arquivos adicionados com sucesso para o colaborador", ""));

		} catch (Exception e) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro no sistema feche o navegador e tente de novo: " + e.getMessage(), ""));
		}

		return "";
	}

	/***
	 * Libera de vez os arquivos para os colabordores
	 * 
	 * @param pastaprocesso
	 * @param idsolicitacao
	 * @return Foi documentado parte para na ler mis os arquivos do cppro
	 * 
	 */
	@SuppressWarnings({ "static-access", "unchecked", "rawtypes", "null" })
	public String LiberaTodosArqCproSalvo(String pastaprocesso, Integer idsolicitacao) {
		try {

			// Cria um array de objetos

			List<ArquivoAnexoCPROSalvo> atuliazameu = null;
			// Carrega o array vindo da tabela

			atuliazameu = new ArrayList();
			atuliazameu = soliArqCproDaoSalvo.traztodos(pastaprocesso, idsolicitacao);

			// Inicia o contador ate ao tamanho do array
			for (Integer cont = 0; cont < atuliazameu.size(); cont++) {
				// Se estiver com flag de rejeitado nao adciona na lista e passa
				// para o proximo
				if (atuliazameu.get(cont).isRejeitado() == false) {

					// Cria novo objeto do arquvo
					HistArqCpro hist = new HistArqCpro();
					soliHistArqCproDao = new SoliHistArqCproDao();
					SolicitacaoAnexo solicitacaoAnexo = new SolicitacaoAnexo();
					SolicitacaoPossuiArquivo novo = new SolicitacaoPossuiArquivo();
					SoliArquivo arquivo = new SoliArquivo();
					histArqCpro = new HistArqCpro();
					ArquivoAnexoCPROSalvo atualiza = new ArquivoAnexoCPROSalvo();
					soliArqCproDaoSalvo = new SoliArqCproDaoSalvo();

					hist = soliHistArqCproDao.BuscaUnico(atuliazameu.get(cont).getIdarqcpprosalvo());
					// Verifica se for nulo caso for nao faz
					if (hist == null) {
						// Copia o arquvo do cprpo para o sisgecol
						CopiaArqDestino copia = new CopiaArqDestino();
						File origem = new File(atuliazameu.get(cont).getCaminhoGed());
						// Foi mudado para a letra I devido ao espaco do ged esta cheio em 13/08/2019
						File destino = new File("I:\\arqsolicitacao\\enviado\\" + alterarsolicitacao.getIdsolicitacao()
								+ atuliazameu.get(cont).getNomeDocumento());

						origem.getAbsoluteFile();
						destino.getAbsoluteFile();

						copia.CopiaFromDestino(origem, destino);

						// Salva o arquivo em anexo
						solicitacaoAnexo.setDatasolicitacao(datasolicitacao);
						solicitacaoAnexo.setNomearquivo(
								alterarsolicitacao.getIdsolicitacao() + atuliazameu.get(cont).getNomeDocumento());
						solicitacaoAnexo.setOperacao("ENVIADO"); // Envio para
																	// arquivos
						solicitacaoAnexo.setLinkarquivo(destino.getAbsoluteFile().toString());
						solicitacaoAnexo.setOrigemarq(2); // Origem cppro
						solicitacaoAnexo.setDatasolicitacao(new Date());
						soliArquivoDao.SalvaArquivo(solicitacaoAnexo);

						// Cria uma nova instacia da classe
						histArqCpro.setSolicitacaoAnexo(solicitacaoAnexo);
						histArqCpro.setIdarquivocppro(atuliazameu.get(cont).getIdarqcpprosalvo());
						histArqCpro.setSalvoem(new Date());

						soliHistArqCproDao.Salvar(histArqCpro);

						atualiza = soliArqCproDaoSalvo.BuscaUnico(atuliazameu.get(cont).getIdarqcpprosalvo());
						atualiza.setBaixadoEm(new Date());
						atualiza.setBaixado(true);
						atualiza.setIdsolicitacao(alterarsolicitacao.getIdsolicitacao());
						soliArqCproDaoSalvo.Alterar(atualiza);

						// Salva o historico do aruvio do cprpo

						novo.setSolicitacao(alterarsolicitacao);
						novo.setSolicitacaoAnexo(solicitacaoAnexo);

						arquivo.setSolicitacaoPossuiArquivo(novo);
						soliArquivoDao.Salvar(arquivo);

						idsolicitacaobusca = alterarsolicitacao.getIdsolicitacao();
						// Traz a listagem salva dos arquivos

						System.out.print("Arquivos copiado.");

					}

				}

			}

			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Arquivos adicionados com sucesso para o colaborador", ""));

		} catch (Exception e) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro no sistema feche o navegador e tente de novo: " + e.getMessage(), ""));
		}
		// Recupera a listagem
		arquivoAnexoCPROSalvos = null;
		arquivoAnexoCPROSalvos = soliArqCproDaoSalvo.traztodos(alterarsolicitacao.getProcesso().getLocalizacao(),
				alterarsolicitacao.getIdsolicitacao());
		// Atualiza as funções da lista de arquivos
		getListaarquivos();

		return "";

	}

	/**
	 * Baixa com nome no parameetro OBS: Não esta sendo usada atualmente nenhum
	 * lugar do projeto
	 * 
	 * @param tipobaixar
	 * @return
	 */
	/**
	 * public String BaixarArquivoDireto(String tipobaixar, String nome) { // Cria a
	 * intancia para baixar o arquivo DownloadArquivo baixar = new
	 * DownloadArquivo();
	 * 
	 * try { // Baixa do correspondente //Foi mudado para a letra I devido ao espaco
	 * do ged esta cheio em 13/08/2019 if (tipobaixar.equals("RECEBIDO")) {
	 * baixar.Abrir("I:\\arqsolicitacao\\recebido\\" + nome, "", false); } // Baixa
	 * do solicitante if (tipobaixar.equals("ENVIADO")) {
	 * baixar.Abrir("I:\\arqsolicitacao\\enviado\\" + nome, "", false); }
	 * 
	 * } catch (FileNotFoundException e1) { // TODO Auto-generated catch block
	 * context = FacesContext.getCurrentInstance(); context.addMessage(null, new
	 * FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao baixar o arquivo: " +
	 * e1.getMessage(), "")); } arquivoparabaixar = "";
	 * 
	 * return "";
	 * 
	 * }
	 */
	/**
	 * Esta funcao acrescenta uma dia a mais na data
	 * 
	 * @return
	 */
	public Date Dataminima() {
		Date datarela = null;
		datarela = new Date();
		java.util.Calendar cd = java.util.GregorianCalendar.getInstance();// Utiliza
		// o
		// gregoria
		if (datarela != null) {
			cd.add(java.util.Calendar.DAY_OF_MONTH, 0);
			// Diminui 2 dias da data atual
			// Atribui o dia da semana do calendario na variavel
			Integer diaDaSemana = cd.get(java.util.Calendar.DAY_OF_WEEK);
			// Verifica se e final de semana se for dimui as datas

			if (diaDaSemana == java.util.Calendar.SUNDAY) {
				cd.add(java.util.Calendar.DAY_OF_MONTH, 1);
			}
			if (diaDaSemana == java.util.Calendar.SATURDAY) {
				cd.add(java.util.Calendar.DAY_OF_MONTH, 2);
			}

			Date nova = cd.getTime();// data trabalhada
			datarela = nova;

		}
		return datarela;
	}

	/**
	 * Lista o hsitorico da solicitação
	 * 
	 * @return
	 */
	public List<Historico> getListahistorico() {
		try {
			listahistorico = historicoDao.buscarhistorico(idsolicitacaobusca);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.print("Erro ao trazer a lista de historico");
			listahistorico = null;
		}

		return listahistorico;

	}

	/**
	 * Lista o envio da solicitacao
	 * 
	 * @return
	 */
	public List<Enviosolicitacao> getEnviosolicitacaos() {
		enviosolicitacaos = null;
		enviosolicitacaos = enviodesolicitacaoDao.buscargeral();
		return enviosolicitacaos;
	}

	public void setEnviosolicitacaos(List<Enviosolicitacao> enviosolicitacaos) {
		this.enviosolicitacaos = enviosolicitacaos;
	}

	public void setListahistorico(List<Historico> listahistorico) {
		this.listahistorico = listahistorico;
	}

	public StreamedContent getArquivodownload() {
		return this.arquivodownload;
	}

	public void setArquivodownload(StreamedContent arquivodownload) {
		this.arquivodownload = arquivodownload;
	}

	public List<SoliArquivo> getListaarquivos() {
		listaarquivos = null;
		try {
			listaarquivos = soliArquivoDao.buscararquivo(idsolicitacaobusca, "");
		} catch (Exception e) {
			// FacesContext cont1 = FacesContext.getCurrentInstance();
			// cont1.addMessage(null, new FacesMessage(
			// FacesMessage.SEVERITY_ERROR,
			// "Erro ao buscar arquivos no servidor ", ""));
			listaarquivos = null;
		} // RCEBIDO
			// FacesContext teste=FacesContext.getCurrentInstance();
			// teste.addMessage("link3",new
			// FacesMessage(FacesMessage.SEVERITY_INFO,
			// "",""));
		return listaarquivos;
	}

	public List<ArquivoAnexoCPRO> getListarquivocppro(String codigo) {

		listarquivocppro = null;
		try {
			listarquivocppro = daoArquivoCPRO.Consulta(codigo);
			daoArquivoCPRO.setJacarregou(true);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return listarquivocppro;
	}

	/*
	 * Fiz outra função de busca para pega os arquivos do cprpo
	 */
	public List<ArquivoAnexoCPRO> listacproarq(String codigo, Integer idsolicitacao) {

		try {
			listarquivocppro = null;
			daoArquivoCPRO = new DaoArquivoCPRO();
			listarquivocppro = daoArquivoCPRO.Consulta(codigo);

		} catch (Exception e) {
			// TODO: handle exception
		}

		arquivoAnexoCPROSalvos = null;
		arquivoAnexoCPROSalvos = soliArqCproDaoSalvo.traztodos(codigo, idsolicitacao);
		return listarquivocppro;

	}
	/*
	 * Atualiza a tabela dos arquivos no sisgecol paa nao ser mais usada do cprpo
	 */

	public void AtualizaSisgecol(String codigo, Integer idsolicitacao) {
		try {

			daoArquivoCPRO.AtualizaArquivos(codigo, idsolicitacao);

			arquivoAnexoCPROSalvos = null;
			arquivoAnexoCPROSalvos = soliArqCproDaoSalvo.traztodos(codigo, idsolicitacao);
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"A listagem dos arquivos do GED do CPRPRO foi atualizada no Sisgecol.", ""));

		} catch (Exception e) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"A listagem dos arquivos do GED do CPRPO não pode ser atualizada no Sisgecol.", ""));
		}

	}

	public void AtualizaSisgecolUnico(Integer idarqivocp, String coodigo, Integer idsolicitacao) {
		try {

			arquivoAnexoCPROSalvo = soliArqCproDaoSalvo.BuscaUnicoPorId(idarqivocp, idsolicitacao);
			if (arquivoAnexoCPROSalvo == null) {
				daoArquivoCPRO.AtualizaArquivoUnico(idarqivocp, idsolicitacao);
				arquivoAnexoCPROSalvos = null;
				arquivoAnexoCPROSalvos = soliArqCproDaoSalvo.traztodos(coodigo, idsolicitacao);
				// FacesContext cont1 = FacesContext.getCurrentInstance();
				context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"A listagem do arquivo do GED do CPRPRO foi atualizada no Sisgecol.", ""));
			} else {
				// FacesContext cont1 = FacesContext.getCurrentInstance();
				context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"O dados  do GED do CPRPRO ja se encontra na base Sisgecol.", ""));

			}

		} catch (Exception e) {

			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"A listagem do arquivo do GED do CPRPO não pode ser atualizada no Sisgecol.", ""));
		}

	}

	/**
	 * 
	 * @param listaarquivos
	 */
	public List<ArquivoAnexoCPRO> listacproarqtodos(String codigo) {

		try {
			listarquivocppro = new ArrayList<ArquivoAnexoCPRO>();
			daoArquivoCPRO = new DaoArquivoCPRO();
			listarquivocppro = daoArquivoCPRO.CosultaTodos(codigo);
		} catch (Exception e) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"O arquivos não pode ser carregados d Ged do CPPRO.", ""));
		}

		return listarquivocppro;

	}

	public void setListaarquivos(List<SoliArquivo> listaarquivos) {
		this.listaarquivos = listaarquivos;
	}

	public String getInstrucoes() {
		return instrucoes;
	}

	public void setInstrucoes(String instrucoes) {
		this.instrucoes = instrucoes;
	}

	public Integer getBnumero() {
		return bnumero;
	}

	public void setBnumero(Integer bnumero) {
		this.bnumero = bnumero;
	}

	public String getBprocesso() {
		return bprocesso;
	}

	public void setBprocesso(String bprocesso) {
		this.bprocesso = bprocesso;
	}

	public Integer getBcorrespondente() {
		return bcorrespondente;
	}

	public void setBcorrespondente(Integer bcorrespondente) {
		this.bcorrespondente = bcorrespondente;
	}

	public Integer getBcomarca() {
		return bcomarca;
	}

	public void setBcomarca(Integer bcomarca) {
		this.bcomarca = bcomarca;
	}

	public Integer getBstatus() {
		return bstatus;
	}

	public void setBstatus(Integer bstatus) {
		this.bstatus = bstatus;
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

	public Integer getIdsolicitacaobusca() {
		return idsolicitacaobusca;
	}

	public void setIdsolicitacaobusca(Integer idsolicitacaobusca) {
		this.idsolicitacaobusca = idsolicitacaobusca;
	}

	public String getArquivoparabaixar() {
		return arquivoparabaixar;
	}

	public void setArquivoparabaixar(String arquivoparabaixar) {
		this.arquivoparabaixar = arquivoparabaixar;
	}

	public String getArquivoparaenviar() {
		return arquivoparaenviar;
	}

	public void setArquivoparaenviar(String arquivoparaenviar) {
		this.arquivoparaenviar = arquivoparaenviar;
	}

	// Arquivos enviado para o correspondente
	public List<SoliArquivo> getListaentrada() {
		try {
			listaentrada = soliArquivoDao.buscararquivo(idsolicitacaobusca, "RECEBIDO");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// FacesContext cont1 = FacesContext.getCurrentInstance();
			// cont1.addMessage(null, new FacesMessage(
			// FacesMessage.SEVERITY_ERROR,
			// "Error ao buscar os arquivos no servidor", ""));
			listaentrada = null;
		}
		return listaentrada;
	}

	public void setListaentrada(List<SoliArquivo> listaentrada) {
		this.listaentrada = listaentrada;
	}

	// Arquivos recebidos do correspondente
	public List<SoliArquivo> getListasaida() {
		try {

			listasaida = soliArquivoDao.buscararquivo(idsolicitacaobusca, "ENVIADO");
		} catch (Exception e) {
			// FacesContext cont1 = FacesContext.getCurrentInstance();
			// cont1.addMessage(null, new FacesMessage(
			// FacesMessage.SEVERITY_ERROR,
			// "Erro ao buscar arquivos no servidor ", ""));
			listasaida = null;
		}
		return listasaida;
	}

	public void setListasaida(List<SoliArquivo> listasaida) {
		this.listasaida = listasaida;
	}

	public Boolean getPreposto() {
		return preposto;
	}

	public void setPreposto(Boolean preposto) {
		this.preposto = preposto;
	}

	/**
	 * Atualiza a tela de saida dos arquivos
	 */
	public void AtualizaListaArquvosSaida() {
		getListasaida();
	}

	public Solicitacao getSolicitacaopendente() {
		return solicitacaopendente;
	}

	public void setSolicitacaopendente(Solicitacao solicitacaopendente) {
		this.solicitacaopendente = solicitacaopendente;
	}

	/**
	 * Limpa o historico
	 */
	public String limparhist() {

		return "/menu.jsf";
	}

	/**
	 * Traz a tela anterio de solicitação
	 */
	public String VoltarNaBusca() {
		getSolicitacoes();
		return "/solicitacao/solicitacao.jsf";
	}

	/**
	 * Traz as solictacoes com historicos
	 */
	public void trazhistarq() {
		listahistorico = null;
		listaandamento = null;
		getListaarquivos();
		getListahistorico();
	}

	/**
	 * Traz os emaisl do correspondente
	 */
	public void trazemails() {
		getEmailCorrespondentes();

	}

	public String getTiposolicitacaobusca() {
		return tiposolicitacaobusca;
	}

	public void setTiposolicitacaobusca(String tiposolicitacaobusca) {
		this.tiposolicitacaobusca = tiposolicitacaobusca;
	}

	public Integer getIdarquivoanexo() {
		return idarquivoanexo;
	}

	public void setIdarquivoanexo(Integer idarquivoanexo) {
		this.idarquivoanexo = idarquivoanexo;
	}

	/**
	 * Exclui arquivos da solicitacao na base de dados
	 */
	public String ExcluirArquivoAnexo(Integer idarquivo) {

		try {

			/*
			 * Pega oo historico
			 */
			histArqCpro = new HistArqCpro();
			soliHistArqCproDao = new SoliHistArqCproDao();
			histArqCpro = soliHistArqCproDao.BuscaUnicoPeloHist(idarquivo);

			/*
			 * Atualiza a tabela do cprpo copiado
			 */
			if (histArqCpro != null) {
				ArquivoAnexoCPROSalvo atualiza = new ArquivoAnexoCPROSalvo();
				atualiza = soliArqCproDaoSalvo.BuscaUnico(histArqCpro.getIdarquivocppro());
				if (atualiza != null) {
					atualiza.setBaixadoEm(null);
					atualiza.setBaixado(false);
					soliArqCproDaoSalvo.Alterar(atualiza);
					/*
					 * Exclui o arquivo logicamente do sisgecol
					 */

					/*
					 * Exclui o historico do sigecol
					 */
					soliHistArqCproDao.Excluir(histArqCpro.getIdarquivocppro());

					/*
					 * Atualiza as tabela e grids
					 */

				}
			}
			/**
			 * Verifica se aa alteracao esta vazia se na nao faz nada
			 */
			if (alterarsolicitacao != null) {
				arquivoAnexoCPROSalvos = null;
				arquivoAnexoCPROSalvos = soliArqCproDaoSalvo.traztodos(
						alterarsolicitacao.getProcesso().getLocalizacao(), alterarsolicitacao.getIdsolicitacao());
			}
			/**
			 * Apaga o arquivo de qq forma
			 */
			soliArquivoDao.ExcluirViaCodigo(idarquivoanexo);

			context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Arquivo excluído com sucesso no servidor", ""));

		} catch (Exception e) {

			context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao  exclui arquivo do servidor", ""));

		}
		return "";
	}

	/**
	 * Finaliza a solcitacao conforme as resposta
	 */
	public void Fazer() {
		if (idobservacao.equals("S")) {
			SolicitacaoReprovado();
		} else if (idobservacao.equals("N")) {
			FinalizaSolictacao();
		}
	}

	/**
	 * Limpa as buscas dos campo de processo
	 */
	public String LimparBuscaProcesso() {
		processobusca = "";
		numerointegracaobusca = "";
		autorbusca = "";
		reubusca = "";
		return "/processo/processocpj.jsf";// /processo/processocpj.jsf
	}

	/*
	 * Limpa as buscas do dialog alteranovasoli 16/06/2014
	 */
	public void Limparalteranovasoli() {
		processobusca = "";
		numerointegracaobusca = "";
		autorbusca = "";
		reubusca = "";
	}

	/**
	 * Metodo limpar campo-soliticatacao.jsf
	 */

	/**
	 * Traz os tipos de solicitaoes visieveis
	 * 
	 * @return
	 */
	public List<TipoSolicitacao> getTipoSolicitacaos() {

		tipoSolicitacaos = tipoSolicitacaoDao.buscargeralinvisivel();
		buscatotalfeitas = BuscaProcessoBase();
		return tipoSolicitacaos;
	}

	public void setTipoSolicitacaos(List<TipoSolicitacao> tipoSolicitacaos) {
		this.tipoSolicitacaos = tipoSolicitacaos;
	}

	/**
	 * Ver sessao das paginas remove sa sessão no servidor
	 */
	public void VerSessao() throws Exception {

		try {
			String teste = null;
			teste = (String) session.getAttribute("Usuario");
			if (teste.equals("") || (teste.equals(null))) {
				context = FacesContext.getCurrentInstance();

				context.getExternalContext().getSessionMap().remove("comarcas");
				context.getExternalContext().getSessionMap().remove("corresponde");
				context.getExternalContext().getSessionMap().remove("solicitacao");
				context.getExternalContext().getSessionMap().remove("orgaos");
				context.getExternalContext().getSessionMap().remove("Usuario");
				context.getExternalContext().getSessionMap().remove("javax.faces.request.charset");
				context.getExternalContext().getSessionMap()
						.remove("com.sun.faces.renderkit.ServerSideStateHelper.LogicalViewMap");
				context.getExternalContext().getSessionMap().remove("usuarios");
				context.getExternalContext().getSessionMap().remove("idUsuario");
				HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
				session.invalidate();

				context.getExternalContext().redirect("/cra/expirado.html");
			}

		} catch (Exception e) {
			try {
				context = FacesContext.getCurrentInstance();

				context.getExternalContext().getSessionMap().remove("comarcas");
				context.getExternalContext().getSessionMap().remove("corresponde");
				context.getExternalContext().getSessionMap().remove("solicitacao");
				context.getExternalContext().getSessionMap().remove("orgaos");
				context.getExternalContext().getSessionMap().remove("Usuario");
				context.getExternalContext().getSessionMap().remove("javax.faces.request.charset");
				context.getExternalContext().getSessionMap()
						.remove("com.sun.faces.renderkit.ServerSideStateHelper.LogicalViewMap");
				context.getExternalContext().getSessionMap().remove("usuarios");
				context.getExternalContext().getSessionMap().remove("idUsuario");
				HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
				session.invalidate();
				context.getExternalContext().redirect("/cra/expirado.html");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Sessao final da rais
	 * 
	 * @throws Exception
	 */
	public void VerSessaoFinal() throws Exception {

		try {
			String teste = null;
			teste = (String) session.getAttribute("Usuario");
			if (teste.equals("") || (teste.equals(null))) {
				context = FacesContext.getCurrentInstance();
				context.getExternalContext().getSessionMap().remove("comarcas");
				context.getExternalContext().getSessionMap().remove("corresponde");
				context.getExternalContext().getSessionMap().remove("solicitacao");
				context.getExternalContext().getSessionMap().remove("orgaos");
				context.getExternalContext().getSessionMap().remove("Usuario");
				context.getExternalContext().getSessionMap().remove("javax.faces.request.charset");
				context.getExternalContext().getSessionMap()
						.remove("com.sun.faces.renderkit.ServerSideStateHelper.LogicalViewMap");
				context.getExternalContext().getSessionMap().remove("usuarios");
				context.getExternalContext().getSessionMap().remove("idUsuario");
				HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
				session.invalidate();
				context.getExternalContext().redirect("/cra/expirado.html");
			}

		} catch (Exception e) {
			try {
				context = FacesContext.getCurrentInstance();
				context.getExternalContext().getSessionMap().remove("comarcas");
				context.getExternalContext().getSessionMap().remove("corresponde");
				context.getExternalContext().getSessionMap().remove("solicitacao");
				context.getExternalContext().getSessionMap().remove("orgaos");
				context.getExternalContext().getSessionMap().remove("Usuario");
				context.getExternalContext().getSessionMap().remove("javax.faces.request.charset");
				context.getExternalContext().getSessionMap()
						.remove("com.sun.faces.renderkit.ServerSideStateHelper.LogicalViewMap");
				context.getExternalContext().getSessionMap().remove("usuarios");
				context.getExternalContext().getSessionMap().remove("idUsuario");
				HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
				session.invalidate();
				context.getExternalContext().redirect("/cra/expirado.html");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Sai para o menu
	 */
	public void saimenu() {

		try {
			context = FacesContext.getCurrentInstance();
			context.getExternalContext().redirect("/cra/menu.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Função que zera tudo na solictacao resetando objetos e valores
	 */
	public void FodeTudo() {
		solicitacao = new Solicitacao();
		statusSolicitacao = new StatusSolicitacao();
		processo = new Processo();
		comarcaPossui = new ComarcaPossui();
		possuiComarcaDao = new PossuiComarcaDao();
		correspondenteDao = new CorrespondenteDao();
		enviodesolicitacaoDao = new EnviodesolicitacaoDao();
		renumeracaoDao = new RenumeracaoDao();
		comarcaDao = new ComarcaDao();
		solicitacaoDao = new SolicitacaoDao();
		statusSolictacaoDao = new StatusSolictacaoDao();
		processoCPJ = new ProcessoCPJ();
		orgaoDao = new OrgaoDao();
		andamentoCPJDao = new AndamentoCPJDao();
		soliArquivoDao = new SoliArquivoDao();
		processoDao = new ProcessoDao();
		tipoSolicitacaoDao = new TipoSolicitacaoDao();
		historicoDao = new HistoricoDao();
		usuarioDao = new UsuarioDao();
		usuario = new Usuario();
		new Historico();
		enviaEmail = new EnviaEmail();

		enviosolicitacao = new Enviosolicitacao();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Para formatacao de
		// data
		idcorrespondente = 0;
		idcomarca = 0;
		idorgao = 0;
		idnumerocomarca = 0;
		processobusca = "";
		autorbusca = "";
		reubusca = "";
		observacaofinal = "";
		ficha = "0";
		nomeautor = "";
		nomereu = "";
		mostrajanela = false;
		bnumero = 0;
		bprocesso = "";
		bcorrespondente = 0;
		bcomarca = 0;
		bstatus = 0;
		bdatainicial = null;
		bdatafinal = null;
		idsolicitacaobusca = 0;
		arquivoparabaixar = "";
		tiposolicitacaobusca = "T";
		statusdasolicitacao = 0;
		listahistoricosoilicitacao = 0;
		idstatusbuscasolicitacao = 0;
		datasolicitacao = new Date();
		preposto = false;
		statusexterno = "";
		horaaudiencia = "";
		context = FacesContext.getCurrentInstance();
		session = (HttpSession) context.getExternalContext().getSession(true);
		idusuario = (Integer) session.getAttribute("IdUsuario");

		try {
			usuario = usuarioDao.trazusuario(idusuario);
		} catch (Exception e) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao acessa dados." + "\n" + e.getMessage(), ""));
			// TODO: handle exception
		}

		enviodesolicitacao = 0;
		textodarejeicao = "";
		idarquivoanexo = 0;
	}

	/**
	 * Limpa os campos da busca de solicitacoes
	 */
	public void LimparCamposSolicitacoes() {
		try {
			bnumero = 0;
			buscacolab = "";
			bprocesso = "";
			listacorrespondente = null;
			listacomarca = null;
			tiposolicitacaobusca = "";
			nomeautor = "";
			nomereu = "";
			datainicial = null;
			datafinal = null;
			solicitacaoDao.novabusca = true;
			idstatusbuscasolicitacao = 0;
			tiposolicitacaobusca = "T";
			idcomarca = 0;
			idorgao = 0;
			bcorrespondente = 0;
			idbancabusca = 0;
			bestadocomarca = 0;
			bnumero = 0;
			solicitacoes = null;
			bdatafinal = null;
			bdatainicial = null;
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Campos limpos.", ""));
		} catch (Exception e) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Campos limpos.", ""));
		}

	}

	/**
	 * Reinvia o email da solicitação
	 */
	public void ReinviaEmail() {
		String texto5 = "";

		try {
			Usuario novo = new Usuario();
			UsuarioDao t = new UsuarioDao();
			novo = t.trazusuarioCorrespondente(idcorrespondente);

			// Pega o testo
			texto5 = "---------------------------------------------------------------------------------------------------\n"
					+ "Servimos do presente para informar que a solicitação abaixo encontra-se atrasada para conclusão: \n"
					+ "Id Solicitação: " + alterarsolicitacao.getIdsolicitacao() + "\n" + "Número do Processo: "
					+ alterarsolicitacao.getProcesso().getNumeroprocesso() + "\n" + "Parte contrária: "
					+ alterarsolicitacao.getProcesso().getParte() + "\n" + "Cliente: "
					+ alterarsolicitacao.getProcesso().getAdverso() + "\n" + "Tipo de Solicitação: "
					+ alterarsolicitacao
							.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao().getEspecie()
					+ " - "
					+ alterarsolicitacao
							.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao().getTipo()
					+ "\n" + "Correspondeente: "
					+ alterarsolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente()
							.getNome()
					+ "\n" + "Login: " + novo.getLogin() + "\n" + "Data da solicitação: "
					+ dateFormat.format(alterarsolicitacao.getDatasolictacao()) + "\n" + "Prazo Final: "
					+ dateFormat.format(alterarsolicitacao.getDataprazo()) + " - Hora: "
					+ alterarsolicitacao.getHoraudiencia() + "\n" + "Instruções: " + "\n"
					+ alterarsolicitacao.getInstrucoes() + "\n" + "Status da Solicitação: "
					+ alterarsolicitacao.getStatusSolicitacao().getStatus() + "\n" + "\n" + "\n"
					+ "Caso de dúvida entre em contato com os números +55 21 2203-3250 / 2221-0084." + "\n"
					+ "Falar com " + alterarsolicitacao.getUsuario().getNomecompleto() + "\n"
					+ "---------------------------------------------------------------------------------------------------\n";
			/**
			 * Envia email novamente
			 * 
			 */
			enviaEmail.EnviarNovamente(alterarsolicitacao, usuario.getEmailprincipal(),
					alterarsolicitacao.getEmailenvio(), alterarsolicitacao.getRenumeracao()
							.getTipoSolicitacaoCorrespondente().getCorrespondente().getEmailsecundario(),
					usuario.getEmailresponsavel(),
					alterarsolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente()
							.getNome(),
					"", texto5, "** AVISO DE SOLICITÇÃO ** ID - " + alterarsolicitacao.getIdsolicitacao());
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "E-Mail enviado com sucesso.", ""));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error ao enviar o email.", ""));
		}

	}

	/**
	 * Reenvia os email que estão no status aguardando confirmação Diligência.
	 * 19/02/2014 - tela alertasolicitação. 24horas
	 */
	public void ReinviaEmailPendenteDiligencia() {
		String texto5 = "";

		try {

			String statustexto = "";
			if (alterarsolicitacao.getStatusSolicitacao().getIdstatus() == 1) {
				statustexto = " há mais de 12 horas.";

			} else if (alterarsolicitacao.getStatusSolicitacao().getIdstatus() == 4) {
				statustexto = " há mais de 24 horas.";
			}

			// Pega o testo
			texto5 = "---------------------------------------------------------------------------------------------------\n"
					+ "Servimos do presente para informar que a solicitação abaixo encontra-se,"
					+ alterarsolicitacao.getStatusSolicitacao().getStatus() + " " + statustexto + "\n"
					+ "Id Solicitação: " + alterarsolicitacao.getIdsolicitacao() + "\n" + "Número do Processo: "
					+ alterarsolicitacao.getProcesso().getNumeroprocesso() + "\n" + "Parte contrária: "
					+ alterarsolicitacao.getProcesso().getParte() + "\n" + "Cliente: "
					+ alterarsolicitacao.getProcesso().getAdverso() + "\n" + "Tipo de Solicitação: "
					+ alterarsolicitacao
							.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao().getEspecie()
					+ " - "
					+ alterarsolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao()
							.getTipo()
					+ "\n" + "Data da solicitação: " + dateFormat.format(alterarsolicitacao.getDatasolictacao()) + "\n"
					+ "Prazo Final: " + dateFormat.format(alterarsolicitacao.getDataprazo()) + " - Hora: "
					+ alterarsolicitacao.getHoraudiencia() + "\n" + "Instruções: " + "\n"
					+ alterarsolicitacao.getInstrucoes() + "\n" + "Status da Solicitação: "
					+ alterarsolicitacao.getStatusSolicitacao().getStatus() + "\n" + "\n"
					+ "Caso de dúvida entre em contato com os números +55 21 2203-3250 / 2221-0084." + "\n"
					+ "Falar com " + alterarsolicitacao.getUsuario().getNomecompleto() + "\n"
					+ "---------------------------------------------------------------------------------------------------\n";

			enviaEmail.EnviarNovamente(alterarsolicitacao, usuario.getEmailprincipal(),
					alterarsolicitacao.getEmailenvio(), alterarsolicitacao.getRenumeracao()
							.getTipoSolicitacaoCorrespondente().getCorrespondente().getEmailsecundario(),
					usuario.getEmailresponsavel(),
					alterarsolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente()
							.getNome(),
					"", texto5, "** SOLITAÇÂO ALTERADA ** ID - " + alterarsolicitacao.getIdsolicitacao());
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "E-Mail enviado com sucesso.", ""));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error ao enviar o email.", ""));
		}

	}

	/**
	 * Reenvia os email que estão no status aguardando confirmação Audiencia.
	 * 19/02/2014 - tela alertasolicitação. 12horas
	 */
	public void ReinviaEmailPendenteAdiencia() {
		String texto5 = "";

		try {

			String statustexto = "";
			if (alterarsolicitacao.getStatusSolicitacao().getIdstatus() == 1) {
				statustexto = " há mais de 12 horas.";

			} else if (alterarsolicitacao.getStatusSolicitacao().getIdstatus() == 4) {
				statustexto = " há mais de 24 horas.";
			}

			// Pega o testo
			texto5 = "---------------------------------------------------------------------------------------------------\n"
					+ "Servimos do presente para informar que a solicitação abaixo encontra-se,"
					+ alterarsolicitacao.getStatusSolicitacao().getStatus() + " " + statustexto + "\n"
					+ "Id Solicitação: " + alterarsolicitacao.getIdsolicitacao() + "\n" + "Número do Processo: "
					+ alterarsolicitacao.getProcesso().getNumeroprocesso() + "\n" + "Parte contrária: "
					+ alterarsolicitacao.getProcesso().getParte() + "\n" + "Cliente: "
					+ alterarsolicitacao.getProcesso().getAdverso() + "\n" + "Tipo de Solicitação: "
					+ alterarsolicitacao
							.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao().getEspecie()
					+ " - "
					+ alterarsolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao()
							.getTipo()
					+ "\n" + "Data da solicitação: " + dateFormat.format(alterarsolicitacao.getDatasolictacao()) + "\n"
					+ "Prazo Final: " + dateFormat.format(alterarsolicitacao.getDataprazo()) + " - Hora: "
					+ alterarsolicitacao.getHoraudiencia() + "\n" + "Instruções: " + "\n"
					+ alterarsolicitacao.getInstrucoes() + "\n" + "Status da Solicitação: "
					+ alterarsolicitacao.getStatusSolicitacao().getStatus() + "\n" + "\n"
					+ "Caso de dúvida entre em contato com os números +55 21 2203-3250 / 2221-0084." + "\n"
					+ "Falar com " + alterarsolicitacao.getUsuario().getNomecompleto() + "\n"
					+ "---------------------------------------------------------------------------------------------------\n";

			enviaEmail.EnviarNovamente(alterarsolicitacao, usuario.getEmailprincipal(),
					alterarsolicitacao.getEmailenvio(), alterarsolicitacao.getRenumeracao()
							.getTipoSolicitacaoCorrespondente().getCorrespondente().getEmailsecundario(),
					usuario.getEmailresponsavel(),
					alterarsolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente()
							.getNome(),
					"", texto5, "** SOLICITAÇÃO PENDENTE ** ID - " + alterarsolicitacao.getIdsolicitacao());
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "E-Mail enviado com sucesso.", ""));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error ao enviar o email.", ""));
		}

	}

	/**
	 * Reenvia apenas o email da solicitacao solicitada, na tela alteraSolicitacao.
	 * botao reenviar email.ENVIA APERNAS AS SOLCITIACAO DO STATUS 1-AGUARDANDO
	 * CONFIRMACAO
	 */

	public String ReenviaAlteraSoli() {
		Usuario novo = new Usuario();
		UsuarioDao t = new UsuarioDao();
		novo = t.trazusuarioCorrespondente(alterarsolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente()
				.getCorrespondente().getIdcorrespondente());

		String texto = "---------------------------------------------------------------------------------------------------\n"
				+ "Id Solicitacao: " + alterarsolicitacao.getIdsolicitacao() + "\n" + "Número do Processo: "
				+ alterarsolicitacao.getProcesso().getNumeroprocesso() + "\n" + "Parte contrária: "
				+ alterarsolicitacao.getProcesso().getParte() + "\n" + "Cliente: "
				+ alterarsolicitacao.getProcesso().getAdverso() + "\n" + "Tipo de Solicitação: "
				+ alterarsolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao()
						.getEspecie()
				+ " - "
				+ alterarsolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao().getTipo()
				+ "\n" + "Correspondente: "
				+ alterarsolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente().getNome()
				+ "\n" + "Login: " + novo.getLogin()

				+ "\n" + "Data da solicitação: " + dateFormat.format(alterarsolicitacao.getDatasolictacao()) + "\n"
				+ "Prazo Final: " + dateFormat.format(alterarsolicitacao.getDataprazo()) + " - Hora: "
				+ alterarsolicitacao.getHoraudiencia() + "\n" + "Instruções: " + "\n"
				+ alterarsolicitacao.getInstrucoes() + "\n" + "Status da Solicitação: "
				+ alterarsolicitacao.getStatusSolicitacao().getStatus() + "\n"
				+ "---------------------------------------------------------------------------------------------------\n";

		try {

			enviaEmail.EnviarNovamente(alterarsolicitacao, usuario.getEmailprincipal(),
					alterarsolicitacao.getEmailenvio(), alterarsolicitacao.getRenumeracao()
							.getTipoSolicitacaoCorrespondente().getCorrespondente().getEmailsecundario(),
					usuario.getEmailresponsavel(),
					alterarsolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente()
							.getNome(),
					"", texto,
					"** SOLICITAÇÃO AGUARDANDO CONFIRMAÇÃO ** ID - " + alterarsolicitacao.getIdsolicitacao());
			context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "E-mail da solicitação reenviado com sucesso.", ""));
			nova_soli_salva = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"E-mail não foi enviado com sucesso" + e.getMessage(), ""));
		}

		return "/alterasolicitacao";
	}

	/**
	 * Verifica as solicitações que estao com a data de elaboração vecida mairo de
	 * 48 horas e com status aguardando comfirmação
	 */
	public void VerificaPendente() {
		{

			for (Solicitacao solicitacao1 : solicitacoes) {
				if (solicitacao1.getStatusexterno() != "CONFIRMADO") {

					try {

						// SimpleDateFormat sdf = new
						// SimpleDateFormat("dd/MM/yyyy");
						Date data1 = solicitacao1.getDataprazo(); // sdf.parse(solicitacao1.getDataprazo().toString());
						Date data2 = new Date();

						long diff = data2.getTime() - data1.getTime();

						// System.out.println("Dif. Segundos: "+diff/1000);
						// System.out.println("Dif. Minutos: "+diff/(1000*60));
						// System.out.println("Dif. Horas: "+diff/(1000*60*60));
						// System.out.println("Dif. Dias:
						// "+diff/(1000*60*60*24));

						float teste = diff / (1000 * 60 * 60);
						if (teste >= 48) {
							System.out.println("solicitacao Pendente");
						}

					} catch (Exception x) {
						x.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * Imprime o faturamento de solcitações finalizadas com os parametros
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public String ImprimirPdf() {
		if (bcorrespondente.equals(0)) {
			FacesContext cont1 = FacesContext.getCurrentInstance();
			cont1.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Selecione um correspondente para imprimir o faturamento.", ""));
			return "/solicitacao/solicitacao.jsf";

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
				context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Selecione um período para imprimir o faturamento.", ""));
				return "/solicitacao/solicitacao.jsf";

			}
		} catch (Exception e) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Selecione um período para imprimir o faturamento.", ""));
			return "/solicitacao/solicitacao.jsf";
		}

		if (tiposolicitacaobusca.equals("T")) {
			param.put("ID_COLABORADOR", bcorrespondente);
			param.put("NOME", "");
			param.put("DATA_INICIAL", val1);
			param.put("DATA_FINAL", val2);
		} else if (tiposolicitacaobusca.equals("A")) {
			param.put("ID_COLABORADOR", bcorrespondente);
			param.put("NOME", "");
			param.put("DATA_INICIAL", val1);
			param.put("DATA_FINAL", val2);
			param.put("TIPO_SOLICITACAO_ESPECIE", "AUDIENCIA");
		} else if (tiposolicitacaobusca.equals("D")) {
			param.put("ID_COLABORADOR", bcorrespondente);
			param.put("NOME", "");
			param.put("DATA_INICIAL", val1);
			param.put("DATA_FINAL", val2);
			param.put("TIPO_SOLICITACAO_ESPECIE", "DILIGENCIA");
		}

		Dao d = new Dao();
		try {
			d.open();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Connection connection = d.getConexao();

		try {
			String arq = null;
			if (tiposolicitacaobusca.equals("T")) {

				arq = relatorios.imprimir(param, "faturamento", connection, "/WEB-INF/relatorios/faturamento.jasper",
						"/WEB-INF/relatorios/faturamento.jrxml", ".pdf");
				DownloadArquivo abrir = new DownloadArquivo();
				abrir.Abrir("c:/relaweb/" + arq, ".pdf", true);
			}
			if (tiposolicitacaobusca.equals("A") || tiposolicitacaobusca.equals("D")) {

				arq = relatorios.imprimir(param, "faturamento", connection,
						"/WEB-INF/relatorios/faturamentoespecie.jasper", "/WEB-INF/relatorios/faturamentoespecie.jrxml",
						".pdf");
				DownloadArquivo abrir = new DownloadArquivo();
				abrir.Abrir("c:/relaweb/" + arq, ".pdf", true);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * Imprime o formulario de audiencia para ser anexado no cpj
	 */

	@SuppressWarnings("unchecked")
	public String ImprimeFormAud() {
		Dao d = new Dao();
		try {

			d.open();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Relatorios relatorios = new Relatorios();
		@SuppressWarnings("rawtypes")
		HashMap param = new HashMap();
		param.put("ID_SOLICITACAO", bnumero);

		try {
			Connection connection = d.getConexao();
			String arq = null;
			arq = relatorios.imprimir(param, "RelFormulario", connection, "/WEB-INF/relatorios/RelFormulario.jasper",
					"/WEB-INF/relatorios/RelFormulario.jrxml", ".pdf");
			DownloadArquivo abrir = new DownloadArquivo();
			abrir.Abrir("c:/relaweb/" + arq, ".pdf", true);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * Imprime o formulario de audiencia para ser anexado no cpj
	 */

	@SuppressWarnings("unchecked")
	public String ImprimeDemostrativo() {

		Dao d = new Dao();
		try {

			d.open();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Relatorios relatorios = new Relatorios();
		@SuppressWarnings("rawtypes")
		HashMap param = new HashMap();
		param.put("ID_SOLICITACAO", bnumero);

		try {
			Connection connection = d.getConexao();
			String arq = null;
			arq = relatorios.imprimir(param, "RelFormulario", connection, "/WEB-INF/relatorios/RelFormulario.jasper",
					"/WEB-INF/relatorios/RelFormulario.jrxml", ".pdf");
			DownloadArquivo abrir = new DownloadArquivo();
			abrir.Abrir("c:/relaweb/" + arq, ".pdf", true);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public Integer getReltiporel() {
		return reltiporel;
	}

	public void setReltiporel(Integer reltiporel) {
		this.reltiporel = reltiporel;
	}

	/**
	 * Imprime o gráfico de desempenho em formato pizza
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String ImprimirDesempenho() {
		// Tipo 1 grafio anual por estado
		// Tipo 2 rela de desenpenho

		Dao d = new Dao();
		try {
			d.open();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			String val1 = "";
			String val2 = "";

			val1 = dateFormat.format(bdatainicial);
			val2 = dateFormat.format(bdatafinal);

			DaoRela m = new DaoRela();
			m.Atualiza(val1, val2);

			HashMap<String, Comparable> param = new HashMap();
			param.put("TIPOREL", "GRAFICO DE DESEMPENHO");
			param.put("DATA_INICIAL", val1);
			param.put("DATA_FINAL", val2);

			Connection conexao = d.getConexao();
			Relatorios relatorios = new Relatorios();
			String arq = null;
			// Tipo 1 grafio anual por estado
			if (reltiporel == 1) {
				arq = relatorios.imprimir(param, "empenhoporestado", conexao,
						"/WEB-INF/relatorios/relaeempenho1.jasper", "/WEB-INF/relatorios/relaeempenho1.jrxml", ".pdf");
				DownloadArquivo abrir = new DownloadArquivo();
				abrir.Abrir("c:/relaweb/" + arq, ".pdf", true);
			}
			// Tipo 2 rela de desenpenho
			if (reltiporel == 2) {
				arq = relatorios.imprimir(param, "amostrapercentual", conexao,
						"/WEB-INF/relatorios/amostrapercentual.jasper", "/WEB-INF/relatorios/amostrapercentual.jrxml",
						".pdf");
				DownloadArquivo abrir = new DownloadArquivo();
				abrir.Abrir("c:/relaweb/" + arq, ".pdf", true);
			}
			// Tipo 3 grafico de desempenho
			if (reltiporel == 3) {
				arq = relatorios.imprimir(param, "analisededados", conexao, "/WEB-INF/relatorios/analisedados.jasper",
						"/WEB-INF/relatorios/analisedados.jrxml", ".pdf");
				DownloadArquivo abrir = new DownloadArquivo();
				abrir.Abrir("c:/relaweb/" + arq, ".pdf", true);
			}
			// FileNotFoundException

		} catch (Exception e) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao imprimir o relatório.", ""));
		}

		return null;

	}

	/**
	 * Atualiza os dados do processo do cpj novawnte
	 * 
	 * @return
	 */
	public String AtualizaBase() {
		try {
			Converte fez = new Converte();
			ProcessoCPJ procdocpj = new ProcessoCPJ();
			ProcessoDao repara = new ProcessoDao();
			Processo processoatualizado = new Processo();
			procdocpj = processoDaoFB.BuscaUnico(numprocessocpj);
			processoatualizado = repara.trazprocesso(fez.traz(procdocpj.getNumprocesso()));

			if (processoatualizado != null) {
				processoatualizado.setNumeroprocesso(procdocpj.getNumprocesso());
				processoatualizado.setNumeroprocessopesq(fez.traz(procdocpj.getNumprocesso()));
				processoatualizado.setParte(procdocpj.getNomeautor());
				processoatualizado.setAdverso(procdocpj.getNomereu());
				processoatualizado.setNumeroprocessopesq(procdocpj.getNumprocessopes());
				processoatualizado.setAssunto(procdocpj.getAssuntodoprocesso());
				processoatualizado.setNumerointegracao(procdocpj.getNumerointegracao());
				processoatualizado.setCartorio(
						procdocpj.getJuizo() + "-" + procdocpj.getOjnumero() + "-" + procdocpj.getOjsigla());
				processoatualizado.setLocalizacao(procdocpj.getSiglaloc() + "-" + procdocpj.getLocnumero());
				processoatualizado.setProceletronico(procdocpj.getProceletronico());

				repara.Alterar(processoatualizado);

				context = FacesContext.getCurrentInstance();
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Processo Atualizado na base com sucesso.", ""));
			} else {
				context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Processo ainda não feito uma Dilegência ou Audiência.", ""));
			}
		} catch (Exception e) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao atualizar o processo eletronico.", ""));
		}
		return "/processo/processocpj.jsf";
	}

	/**
	 * Enviar apenas um email da solicitacao que já esta cadastrada, com o status 1
	 * aguardando confirmacao.
	 * 
	 */

	public void emailAlterasoli() {

		alterarsolicitacao = new Solicitacao();
		alterarsolicitacao = solicitacaoDao.trazrsolicitacao(idsolicitacaobusca);
		ReenviaAlteraSoli();
	}

	/**
	 * ROBO MEPHISTOFELES- CLICA SOZINHO AO ENTRAR NA PAGINA
	 */
	public void robotbotao() {
		Robot robot;
		try {
			robot = new Robot();
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
		} catch (AWTException e) {
			// / TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Manda apenas 1 E-mail, Clicando em enviar e-mail, manda apenas o email do
	 * usuario selecionado.tela testecorrespondente..DILIGENCIA--GRID
	 * 
	 */
	public void mapeiaumemailDiligencia() {

		alterarsolicitacao = new Solicitacao();
		alterarsolicitacao = solicitacaoDao.trazrsolicitacao(idsolicitacaobusca);
		ReinviaEmailPendenteDiligencia();
	}

	/**
	 * Envia todos os emails das solicitacoes pendentes DILIGENCIA para o usuario,
	 * tela: testecorrespondente, botão enviar;
	 * 
	 */

	public void mapeiaemailDiligencia() {

		alterarsolicitacao = new Solicitacao();

		alertaDiligencia = historicoDao.alertaDiligenciaHistor(idstatus, idusuario);

		// alertabox = solicitacaoDao.alertaDiligencia(1, idusuario);

		for (Historico alterar : alertaDiligencia) {
			alterarsolicitacao = alterar.getSolicitacao();
			ReinviaEmailPendenteDiligencia();
		}
	}

	/**
	 * Envia todos os emails do grid audiencias
	 */
	public void emailaudiencias() {

		alterarsolicitacao = new Solicitacao();
		alertaboxAudiencia = solicitacaoDao.alertaAudiencia(idstatus, idusuario);
		for (Solicitacao alterar2 : alertaboxAudiencia) {
			alterarsolicitacao = alterar2;
			ReinviaEmailPendenteAdiencia();
		}
	}

	/**
	 * Traz apenas o usuario cadastrado em audiência,que passaram do prazo de 24
	 * horas e que possui o status " 4 - em produção..anexo "
	 */

	/***
	 * verifica se o usuario cadastrado possui diligência, caso possua, traz as
	 * diligências atrazadas no prazo maximo de 12horas e com o status "1-Aguardando
	 * Confirmação"
	 * 
	 * @return
	 */

	public void testaConfirmacao() {
		if (idusuario == null) {
			try {

				context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sua sessão expirou !!!.", ""));

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

			context = FacesContext.getCurrentInstance();
			session = (HttpSession) context.getExternalContext().getSession(true);
			idusuario = (Integer) session.getAttribute("IdUsuario");

			if ((idusuario == 2) || (idusuario == 462) || (idusuario == 934) || (idusuario == 872)
					|| (idusuario == 4477) || (idusuario == 2370 || (idusuario == 997))) {

				alertaDiligencia = historicoDao.alertaDiligenciaHistor(idstatus, idusuario);
				alertaboxAudiencia = solicitacaoDao.alertaAudiencia(idstatus, idusuario);
				try {
					if (alertaDiligencia.size() >= 1) {
						try {
							if (alertafalse == false) {
								context = FacesContext.getCurrentInstance();
								context.getExternalContext().redirect("TESTEcorrespondente.jsf");
							}
							alertafalse = false;
						} catch (Exception e) {
							// TODO Auto-generated catch block

							try {
								context = FacesContext.getCurrentInstance();
								;
								context.getExternalContext().redirect("expirado.jsf");
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}

				} catch (Exception e) {
					context = FacesContext.getCurrentInstance();
					;
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Erro interno na busca favor sair da aplicação e entrar novamente !!!.", ""));
				}

			}

			alertafalse = true;
		}
	}

	/**
	 * Chama os relatórios de
	 */
	public void ChamaRela() {
		alertaDiligencia = historicoDao.alertaDiligenciaHistor(idstatus, idusuario);
		alertaboxAudiencia = solicitacaoDao.alertaAudiencia(idstatus, idusuario);

	}

	/**
	 * Traz a tela para alterar a solictação
	 * 
	 * @return
	 */
	public String Alteranovasoliunica() {
		alterarsolicitacao = solicitacaoDao.trazrsolicitacao(idsolicitacao);

		return "/solicitacao/alteranovasolicitacao.jsf";

	}

	/**
	 * METODO QUE CHECARA SE O PROCESSO JÁ EXISTE NA BASE A SOLICITACAO COM O NUMERO
	 * DAQUELE PROCESSO. SE A DATADASOLICITACAO DESSE PROCESSO PASSAR DE 48-HORAS
	 * PODERÁ SER FEITA UMA NOVA SOLICITACAO --- TIPO DE SOLICITACAO: DILIGENCIA
	 * PARCIAL E INTEGRAL... TELA NOVASOLICITACAO "DIALOG"--------07/07/2014
	 * 
	 */

	public void DoisDiasNovaSoli() {
		AvisoDoisDias = solicitacaoDao.alertaDoisDiasNovaSoli(buscapastaprocesso);
		if ((AvisoDoisDias.size() >= 1) && (tiposolictacao == 13 || tiposolictacao == 14)) {
			// && (AvisoDoisDias.get(0).getStatusSolicitacao().getIdstatus() !=
			// 7 ) &&
			// (AvisoDoisDias.get(0).getStatusSolicitacao().getIdstatus()!= 8 )
			// FacesContext doisdias = FacesContext.getCurrentInstance();
			// doisdias.addMessage(null, new
			// FacesMessage(FacesMessage.SEVERITY_ERROR,"Esse processo já existe
			// na base, não pode ser registrado.",
			// ""));
			recebe = "verdadeiro";

			textosolic = "Esta solicitação não poderá ser salva pois existe" + "\r\n"
					+ "uma solicitação ainda dentro do prazo de 48 horas so poderá" + "\r\n"
					+ "ser feita um cópia parcial o integral depois do prazo" + "\r\n"
					+ "de 48 horas cso de dúvida procure o suporte do SISGECOL.";

		} else {

			// FacesContext doisdias2 = FacesContext.getCurrentInstance();
			// doisdias2.addMessage(null, new
			// FacesMessage(FacesMessage.SEVERITY_ERROR,"Esse processo pode ser
			// registrado.",
			// ""));
			recebe = "falso";
			textosolic = "";
		}
	}

	/**
	 * Traz a listagem de bancas para ser inserido na solicitação ou na busca
	 * 
	 * @return
	 */
	public List<BancaProcesso> getBancaProcessos() {
		bancaProcessos = bancaProcessoDao.buscargeral();
		return bancaProcessos;
	}

	public void setBancaProcessos(List<BancaProcesso> bancaProcessos) {
		this.bancaProcessos = bancaProcessos;
	}

	/**
	 * Atualiza o formulário na base verificando as validações
	 * 
	 */
	public String SalvarFormulario() {
		try {
			/**
			 * Valida o telefone do ex adverso nao podendo ser igual ao do audiencista
			 */
			if (formularioAudiencia.getTelefoneadvadervoso().equals(formularioAudiencia.getTelefoneadvogado())) {

				context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Telefone do audiencista não pode ser vazio ou igual a do Ex Adverso ", ""));
				return "/solicitacao/formulario.jsf";
			}

			/**
			 * Valida o numero da OAB nao podendo ser igual ao do adiencista e vice versa
			 */
			if (formularioAudiencia.getNumoab().equals(formularioAudiencia.getNumoabadverso())) {

				context = FacesContext.getCurrentInstance();
				;
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"O número da oab não pode ser vazio ou igual do audiencita ou o ex adverso", ""));
				return "/solicitacao/formulario.jsf";
			}
			// "(00)0000-0000"
			/**
			 * Valida o telefone do correpondete para não colocar o formato errado
			 */
			if (formularioAudiencia.getTelefoneadvogado().substring(4, 13).equals("0000-0000")
					|| formularioAudiencia.getTelefoneadvogado().substring(4, 13).equals("1111-1111")
					|| formularioAudiencia.getTelefoneadvogado().substring(4, 13).equals("2222-2222)")
					|| formularioAudiencia.getTelefoneadvogado().substring(4, 13).equals("3333-3333")
					|| formularioAudiencia.getTelefoneadvogado().substring(4, 13).equals("4444-4444")
					|| formularioAudiencia.getTelefoneadvogado().substring(4, 13).equals("5555-5555")
					|| formularioAudiencia.getTelefoneadvogado().substring(4, 13).equals("6666-6666")
					|| formularioAudiencia.getTelefoneadvogado().substring(4, 13).equals("7777-7777")
					|| formularioAudiencia.getTelefoneadvogado().substring(4, 13).equals("8888-8888")
					|| formularioAudiencia.getTelefoneadvogado().substring(4, 13).equals("9999-9999")) {
				context = FacesContext.getCurrentInstance();
				;
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"O número de telefone do audiencista está no formato está errado.", ""));
				return "/solicitacao/formulario.jsf";
			}

			/**
			 * Valida o telefone do exadverso para não colocar telefone errado
			 */
			if (formularioAudiencia.getTelefoneadvadervoso().substring(4, 13).equals("0000-0000")
					|| formularioAudiencia.getTelefoneadvadervoso().substring(4, 13).equals("1111-1111")
					|| formularioAudiencia.getTelefoneadvadervoso().substring(4, 13).equals("2222-2222)")
					|| formularioAudiencia.getTelefoneadvadervoso().substring(4, 13).equals("3333-3333")
					|| formularioAudiencia.getTelefoneadvadervoso().substring(4, 13).equals("4444-4444")
					|| formularioAudiencia.getTelefoneadvadervoso().substring(4, 13).equals("5555-5555")
					|| formularioAudiencia.getTelefoneadvadervoso().substring(4, 13).equals("6666-6666")
					|| formularioAudiencia.getTelefoneadvadervoso().substring(4, 13).equals("7777-7777")
					|| formularioAudiencia.getTelefoneadvadervoso().substring(4, 13).equals("8888-8888")
					|| formularioAudiencia.getTelefoneadvadervoso().substring(4, 13).equals("9999-9999")) {
				context = FacesContext.getCurrentInstance();
				;
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"O número do telefone do Ex Adverso está no formato errado.", ""));
				return "/solicitacao/formulario.jsf";

			}

			/**
			 * Valida o telefone celular do advogado para não colocar telefone errado
			 * 
			 */
			/*
			 * if (formularioAudiencia.getTelefonecel1().substring(4,
			 * 13).equals("0000-0000") || formularioAudiencia.getTelefonecel1().substring(4,
			 * 13).equals("1111-1111") || formularioAudiencia.getTelefonecel1().substring(4,
			 * 13).equals("2222-2222)") ||
			 * formularioAudiencia.getTelefonecel1().substring(4, 13).equals("3333-3333") ||
			 * formularioAudiencia.getTelefonecel1().substring(4, 13).equals("4444-4444") ||
			 * formularioAudiencia.getTelefonecel1().substring(4, 13).equals("5555-5555") ||
			 * formularioAudiencia.getTelefonecel1().substring(4, 13).equals("6666-6666") ||
			 * formularioAudiencia.getTelefonecel1().substring(4, 13).equals("7777-7777") ||
			 * formularioAudiencia.getTelefonecel1().substring(4, 13).equals("8888-8888") ||
			 * formularioAudiencia.getTelefonecel1().substring(4, 13).equals("9999-9999")) {
			 * context = FacesContext.getCurrentInstance(); context.addMessage(null, new
			 * FacesMessage(FacesMessage.SEVERITY_ERROR,
			 * "O número do telefone celular do advogado está no formato errado.", ""));
			 * return "/solicitacao/formulario.jsf";
			 * 
			 * }
			 */

			/**
			 * Valida o telefone celular do exa adverso para não colocar telefone errado
			 * 
			 */

			/*
			 * if (formularioAudiencia.getTelefonecel2().substring(4,
			 * 13).equals("0000-0000") || formularioAudiencia.getTelefonecel2().substring(4,
			 * 13).equals("1111-1111") || formularioAudiencia.getTelefonecel2().substring(4,
			 * 13).equals("2222-2222)") ||
			 * formularioAudiencia.getTelefonecel2().substring(4, 13).equals("3333-3333") ||
			 * formularioAudiencia.getTelefonecel2().substring(4, 13).equals("4444-4444") ||
			 * formularioAudiencia.getTelefonecel2().substring(4, 13).equals("5555-5555") ||
			 * formularioAudiencia.getTelefonecel2().substring(4, 13).equals("6666-6666") ||
			 * formularioAudiencia.getTelefonecel2().substring(4, 13).equals("7777-7777") ||
			 * formularioAudiencia.getTelefonecel2().substring(4, 13).equals("8888-8888") ||
			 * formularioAudiencia.getTelefonecel2().substring(4, 13).equals("9999-9999")) {
			 * context = FacesContext.getCurrentInstance();
			 * 
			 * context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
			 * "O número do telefone celular do advogado está no formato errado.", ""));
			 * return "/solicitacao/formulario.jsf"; }
			 */
			/**
			 * Valor da contra proposta antes verifica se o acordo foi realizado
			 * 
			 */

			/*
			 * if (formularioAudiencia.isAcordorealizado() == false) { if
			 * (formularioAudiencia.getValorproposta() <
			 * unicasolicitacao.getValordaalcada()) { context =
			 * FacesContext.getCurrentInstance(); ; context.addMessage(null, new
			 * FacesMessage(FacesMessage.SEVERITY_ERROR,
			 * "O valor da contra proposta e menor que o valor da alçada oferecida pelo banco."
			 * , "")); return "/solicitacao/formulario.jsf";
			 * 
			 * } }
			 * 
			 *//**
				 * Valor do acordo e me menor ou igual o valor da alçada
				 *//*
					 * if (formularioAudiencia.isAcordorealizado() == true) { if
					 * (formularioAudiencia.getValoracordo() > unicasolicitacao.getValordaalcada())
					 * { context = FacesContext.getCurrentInstance(); ; context.addMessage(null, new
					 * FacesMessage(FacesMessage.SEVERITY_ERROR,
					 * "O valor do acordo e maior que o valor da alçada oferecida pelo banco.",
					 * "")); return "/solicitacao/formulario.jsf";
					 * 
					 * } }
					 */

			formularioAudiencia.setDataformulario(new Date());
			formularioAudienciaDao.Salvar(formularioAudiencia);

			// FormularioAudiencia form =new FormularioAudiencia();
			formularioAudiencia = formularioAudienciaDao.trazformulario(formularioAudiencia.getIdformulario());
			unicasolicitacao.setFormularioAudiencia(getFormularioAudiencia());
			solicitacaoDao.Alterar(unicasolicitacao);
			context = FacesContext.getCurrentInstance();

			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Formulário atualizado com sucesso.", ""));
			return "/solicitacao/formulario.jsf";

		} catch (Exception e) {
			context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao atualizar o formulário." + e.getMessage(), ""));
			return "/solicitacao/formulario.jsf";
		}
	}

	/**
	 * Atualiza o formulário do mobile na base verificando as validações
	 * 
	 */
	public String SalvarFormularioCel() {
		try {
			/**
			 * Valida o telefone do ex adverso nao podendo ser igual ao do audiencista
			 */
			if (formularioAudiencia.getTelefoneadvadervoso().equals(formularioAudiencia.getTelefoneadvogado())) {

				context = FacesContext.getCurrentInstance();
				;
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Telefone do audiencista não pode ser vazio ou igual a do Ex Adverso ", ""));
				return "/mobile/formulariocel.jsf";
			}

			/**
			 * Valida o numero da OAB nao podendo ser igual ao do adiencista e vice versa
			 */
			if (formularioAudiencia.getNumoab().equals(formularioAudiencia.getNumoabadverso())) {

				context = FacesContext.getCurrentInstance();
				;
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"O número da oab não pode ser vazio ou igual do audiencita ou o ex adverso", ""));
				return "/mobile/formulariocel.jsf";
			}
			// "(00)0000-0000"
			/**
			 * Valida o telefone do correpondete para não colocar o formato errado
			 */
			if (formularioAudiencia.getTelefoneadvogado().substring(4, 13).equals("0000-0000")
					|| formularioAudiencia.getTelefoneadvogado().substring(4, 13).equals("1111-1111")
					|| formularioAudiencia.getTelefoneadvogado().substring(4, 13).equals("2222-2222)")
					|| formularioAudiencia.getTelefoneadvogado().substring(4, 13).equals("3333-3333")
					|| formularioAudiencia.getTelefoneadvogado().substring(4, 13).equals("4444-4444")
					|| formularioAudiencia.getTelefoneadvogado().substring(4, 13).equals("5555-5555")
					|| formularioAudiencia.getTelefoneadvogado().substring(4, 13).equals("6666-6666")
					|| formularioAudiencia.getTelefoneadvogado().substring(4, 13).equals("7777-7777")
					|| formularioAudiencia.getTelefoneadvogado().substring(4, 13).equals("8888-8888")
					|| formularioAudiencia.getTelefoneadvogado().substring(4, 13).equals("9999-9999")) {
				context = FacesContext.getCurrentInstance();
				;
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"O número de telefone do audiencista está no formato está errado.", ""));
				return "/mobile/formulariocel.jsf";
			}

			/**
			 * Valida o telefone do exadverso para não colocar telefone errado
			 */
			if (formularioAudiencia.getTelefoneadvadervoso().substring(4, 13).equals("0000-0000")
					|| formularioAudiencia.getTelefoneadvadervoso().substring(4, 13).equals("1111-1111")
					|| formularioAudiencia.getTelefoneadvadervoso().substring(4, 13).equals("2222-2222)")
					|| formularioAudiencia.getTelefoneadvadervoso().substring(4, 13).equals("3333-3333")
					|| formularioAudiencia.getTelefoneadvadervoso().substring(4, 13).equals("4444-4444")
					|| formularioAudiencia.getTelefoneadvadervoso().substring(4, 13).equals("5555-5555")
					|| formularioAudiencia.getTelefoneadvadervoso().substring(4, 13).equals("6666-6666")
					|| formularioAudiencia.getTelefoneadvadervoso().substring(4, 13).equals("7777-7777")
					|| formularioAudiencia.getTelefoneadvadervoso().substring(4, 13).equals("8888-8888")
					|| formularioAudiencia.getTelefoneadvadervoso().substring(4, 13).equals("9999-9999")) {
				context = FacesContext.getCurrentInstance();
				;
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"O número do telefone do Ex Adverso está no formato errado.", ""));
				return "/mobile/formulariocel.jsf";

			}

			formularioAudiencia.setDataformulario(new Date());
			formularioAudienciaDao.Salvar(formularioAudiencia);

			// FormularioAudiencia form =new FormularioAudiencia();
			formularioAudiencia = formularioAudienciaDao.trazformulario(formularioAudiencia.getIdformulario());
			unicasolicitacao.setFormularioAudiencia(getFormularioAudiencia());
			solicitacaoDao.Alterar(unicasolicitacao);
			context = FacesContext.getCurrentInstance();
			;
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Formulário atualizado com sucesso.", ""));
			return "/mobile/formulariocel.jsf";

		} catch (Exception e) {
			context = FacesContext.getCurrentInstance();
			;
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao atualizar o formulário." + e.getMessage(), ""));
			return "/mobile/formulariocel.jsf";
		}
	}

	public FormularioAudiencia getFormularioAudiencia() {
		return formularioAudiencia;
	}

	public void setFormularioAudiencia(FormularioAudiencia formularioAudiencia) {
		this.formularioAudiencia = formularioAudiencia;
	}

	public FormularioAudienciaDao getFormularioAudienciaDao() {
		return formularioAudienciaDao;
	}

	public void setFormularioAudienciaDao(FormularioAudienciaDao formularioAudienciaDao) {
		this.formularioAudienciaDao = formularioAudienciaDao;
	}

	/**
	 * Traz o formulario para preencger
	 */

	public String TrazVazioFomulario() {
		if (unicasolicitacao.getFormularioAudiencia() == null) {
			formularioAudiencia = null;
			formularioAudiencia = new FormularioAudiencia();
		} else {
			formularioAudiencia = null;
			formularioAudiencia = unicasolicitacao.getFormularioAudiencia();
		}
		return "/solicitacao/formulario.jsf";
	}

	/**
	 * Traz o formulario para preencger
	 */
	public String TrazFinalizacao() {

		return "/solicitacao/finalizarsoli.jsf";
	}

	/**
	 * Traz os arquvios para dowload da solicitacao
	 */
	public String TrazArquivos() {
		// Zera os arquivos do GED do CPPRO
		listacproarqtodos("0");
		return "/solicitacao/arquivosigecol.jsf";
	}

	/**
	 * Volta pra a tela de solcitacao
	 * 
	 * @return
	 */
	public String VoltarAlterarSolicitao() {
		return "/solicitacao/alterasolicitacao.jsf";
	}

	/**
	 * Traz o formulario do mobile vazio
	 * 
	 * @return
	 */
	public String TrazVazioFomularioCel() {
		if (unicasolicitacao.getFormularioAudiencia() == null) {
			formularioAudiencia = null;
			formularioAudiencia = new FormularioAudiencia();
		} else {
			formularioAudiencia = null;
			formularioAudiencia = unicasolicitacao.getFormularioAudiencia();
		}
		return "/mobile/formulariocel.jsf";
	}

	public Boolean getIdproacordo() {
		return idproacordo;
	}

	public void setIdproacordo(Boolean idproacordo) {
		this.idproacordo = idproacordo;
	}

	public float getValordaalcada() {
		return valordaalcada;
	}

	public void setValordaalcada(float valordaalcada) {
		this.valordaalcada = valordaalcada;
	}

	/**
	 * Envia um e-meil que nao houve proposta de se nao houver acordo
	 * 
	 * @return
	 */
	public String EmailSemproposta() {

		if (unicasolicitacao.isPropostaacordo() == true) {
			vaiacordo = "Proposta de Acordo: SIM" + "\n" + "Valor da Alçada: R$"
					+ String.format("%.2f", unicasolicitacao.getValordaalcada());
		} else if (unicasolicitacao.isPropostaacordo() == false) {
			vaiacordo = "Propposta de Acordo: NÃ0";
		}

		/**
		 * Dispara o email para a ouvidoria colaborador
		 */

		if (unicasolicitacao.getFormularioAudiencia().isAcordorealizado() == false
				&& unicasolicitacao.isPropostaacordo() == true) {
			String texto1 = "-------------------------------------------------------------------------------------------------------\n"
					+ "Servimos do presente para informar que a audiência abaixo não houve acordo realizado\n" + "ID: "
					+ unicasolicitacao.getIdsolicitacao() + "\n" + "Número do Processo : "
					+ unicasolicitacao.getProcesso().getNumeroprocesso() + "\n" + "Correspondente: "
					+ unicasolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente().getNome()
					+ "\n" + "Advogaddo Ex Adverso: " + unicasolicitacao.getFormularioAudiencia().getAdvogadoadverso()
					+ "\n" + "Telefone do Advogado Ex Adverso: "
					+ unicasolicitacao.getFormularioAudiencia().getTelefoneadvadervoso() + "\n"
					+ "Email do Advogado Ex Adverso: " + unicasolicitacao.getFormularioAudiencia().getEmailadvadverso()
					+ "\n" + "Autor: " + unicasolicitacao.getProcesso().getParte() + "\n" + "Réu  : "
					+ unicasolicitacao.getProcesso().getAdverso() + "\n" + "Valor da Alçada: " + "R$"
					+ String.format("%.2f", unicasolicitacao.getValordaalcada()) + "\n" + "Valor da Contra Proposta: "
					+ "R$" + String.format("%.2f", unicasolicitacao.getFormularioAudiencia().getValorcontraproposta())
					+ "\n" + " " + "\n"
					+ "-------------------------------------------------------------------------------------------------------\n";

			try {
				enviaEmail.EnviarMsg("admin@cra.adv.br", "ouvidoria.colaborador@cra.adv.br", "OUVIDORIA COLABORADOR",
						"", texto1, "** SEM PROPOSTA DE ACORDO **");
			} catch (Exception e) {
				context = FacesContext.getCurrentInstance();
				// TODO Auto-generated catch block

				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao enviar e-mail.", ""));
			}

		}
		return BuscaUnica();
	}

	/**
	 * Verifica se a conlusão da solictação esta atrazada se estiver desconta 30% do
	 * valor do pagamento da mesma somente para o estado do Rio de Janeiro
	 */
	@SuppressWarnings("unused")
	public float DescontaAtrazoSolicitacao() {
		long diafinalsemana = 0;
		long diferencaHoras = 0;
		long diferenca = 0;

		Calendar dataPrazo = Calendar.getInstance();
		Calendar finaldesemana = Calendar.getInstance();
		Calendar novadata = Calendar.getInstance();
		Float valor = (float) 300.0;
		valor = unicasolicitacao.getValor();
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		float recebenovovalor = 0; // Seta para zero
		Date data2Conclusao = new Date(); // Data de conclusao
		Date dataAtual = new Date();
		dataPrazo.setTime(unicasolicitacao.getDataprazo());
		finaldesemana.setTimeInMillis(data2Conclusao.getTime());

		// Pega a diferença
		// Multiplica por 2 para acrescentar mais um dia apartir da data de prazo
		diferenca = (System.currentTimeMillis() - (dataPrazo.getTimeInMillis() * 2));
		// Faz a verificação se e final de semana se sabado ou domingo
		if (finaldesemana.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			diferencaHoras = diferenca / (60 * 60 * 1000); // DIFERENCA EM HORAS
			diferencaHoras = (diferencaHoras + 48);
			novadata.add(Calendar.DATE, +2);
			novadata.add(Calendar.HOUR, +48);
			dataAtual = novadata.getTime();
		} else if (finaldesemana.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			diferencaHoras = diferenca / (60 * 60 * 1000); // DIFERENCA EM HORAS
			diferencaHoras = (diferencaHoras + 24);
			novadata.add(Calendar.DATE, +2);
			novadata.add(Calendar.HOUR, +48);
			dataAtual = novadata.getTime();
		} else {
			// diferenca = (data2Conclusao.getTime() - data1Prazo.getTime());
			diferencaHoras = diferenca / (60 * 60 * 1000); // DIFERENCA EM HORAS
		}
		// Abate 24 horas para contar a partir das 24 horas
		// Desconta no prazo de 65 horas 15%
		diferencaHoras = (diferencaHoras - 24);

		if (diferencaHoras > 65 && diferencaHoras < 88) {
			Float teste = (valor / 100);
			Float desconto = (teste * 15);
			recebenovovalor = (valor - desconto);
		}
		// Desconta no prazo de 88 horas 30%
		if (diferencaHoras > 88 && diferencaHoras < 109) {
			Float teste = (valor / 100);
			Float desconto = (teste * 30);
			recebenovovalor = (valor - desconto);
		}
		// Desconta no prazo de 109 horas 50%
		if (diferencaHoras > 109) {
			Float teste = (valor / 100);
			Float desconto = (teste * 50);
			recebenovovalor = (valor - desconto);
		}
		if (recebenovovalor == 0) {
			recebenovovalor = valor;
		}
		// Muda a data de conclusão
		unicasolicitacao.setDataconclusao(dataAtual);
		return recebenovovalor;
	}

	/**
	 * Muda o status para a o b ou cma reprovação d solicitação
	 * 
	 */
	public String MudaTexto() {

		if (getRecebecomplemento().equals(1)) {
			alterarsolicitacao.setComplemento("Proposta de acordo não consignada em ata;");
		} else if (getRecebecomplemento().equals(2)) {
			alterarsolicitacao.setComplemento("Ata de audiência constando ''não houve proposta de acordo''");
		} else if (getRecebecomplemento().equals(3)) {
			alterarsolicitacao.setComplemento("Depoimento pessoal não colhido;");
		} else if (getRecebecomplemento().equals(4)) {
			alterarsolicitacao.setComplemento("Formulário incompleto;");
		} else if (getRecebecomplemento().equals(5)) {
			alterarsolicitacao.setComplemento("Cópia ilegível;");

		} else if (getRecebecomplemento().equals(6)) {
			alterarsolicitacao.setComplemento("");
		} else if (getRecebecomplemento().equals(7)) {
			alterarsolicitacao.setComplemento("Sua solicitação foi cancelada definitivamente." + "\n" + "Motivo:");
		} else {
			alterarsolicitacao.setComplemento("");
		}
		return "";
	}

	/***
	 * Seta as variaveis e listagem de busca para nova solicitacao a ser feita
	 */
	public String LimpaListagens() {
		idbanca = 0;
		bestadocomarca = 0;
		idcomarca = 0;
		idorgao = 0;
		// idcorrespondente = 0;
		// bancaProcesso = null;
		// listuf = null;
		// listaporestado = null;
		// listaorgao = null;
		// tipoSolicitacaos = null;
		idnumerocomarca = 0;
		lide = "";

		// comarcasolicitacao = null;
		idproacordo = false;
		liberaarqsoli = false;
		idsolicitacaobusca = 0;
		valordaalcada = 0;
		tiposolictacao = 0;
		// instrucoes = "";
		// enviodesolicitacao = 0;
		getListuf();
		getListaporestado();
		getListaorgao();
		getComarcasolicitacao();
		solicitacao = new Solicitacao();
		return "";
	}

	/**
	 * Envia msg para o telefone do correspondente
	 */
	public String MsgTel() {
		SimpleMessageService cliente = new SimpleMessageService("cavalcanteramos", "cra.2014");
		SimpleMessage mensagem1 = new SimpleMessage();
		mensagem1.setTo("03121982751433");
		mensagem1.setMessage("Vindo do SIGECOL como teste!!! ");
		mensagem1.setSchedule(new Date());

		try {
			List<Response> retornos = cliente.send(mensagem1);

			if (retornos.get(1).isError() == true) {

				context = FacesContext.getCurrentInstance();
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao enviar a mensagem.", ""));
			} else if (retornos.get(1).isError() == false) {
				context = FacesContext.getCurrentInstance();
				;

				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Messagem enviada com sucesso.", ""));
			}

		} catch (ClientHumanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Nome: nelson Modulo: Data: 29/01/2015 Hora: 10:23:21 Descrição: Altera o
	 * valor se tiver zerado
	 * 
	 * @return
	 */

	public String SetaValor() {

		if (alterarsolicitacao.getValor() == 0) {
			alterarsolicitacao.setValor(alterarsolicitacao.getRenumeracao().getValor());
			try {
				solicitacaoDao.Alterar(alterarsolicitacao);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "";
	}

	/**
	 * Desconta na finalizacao
	 * 
	 */
	public float DescontaFinal(float valor, float descontado) {
		float recebenovovalor = 0;
		float desconto = 0;// Seta para zero
		float teste = 0;
		teste = (valor / 100);
		desconto = (teste * descontado);
		recebenovovalor = (valor - desconto);
		return recebenovovalor;
	}

	/**
	 * Altera o texto de auditoria da solicitacao
	 * 
	 * @date
	 * @return
	 */
	public String AlterarTextoAuditoria() {

		try {
			solicitacaoDao.Alterar(alterarsolicitacao);

			// TODO Auto-generated catch block
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Alterado com sucesso.", ""));
		} catch (Exception e) {

			// TODO Auto-generated catch block
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao alterar o texto.", ""));
		}
		return "";
	}

	/****
	 * Salva ou autera a auditoria;
	 * 
	 * @return
	 */

	public String SalvarAuterarAuditoriaInterna() {
		try {
			auditoriaInterna.setDataauditoria(new Date());
			auditoriaInternaDao.SalvarAlterar(auditoriaInterna);
			alterarsolicitacao.setAuditoriaInterna(auditoriaInterna);
			auditoriaInterna = auditoriaInternaDao.trazauditoria(auditoriaInterna.getIdaudiinterna());
			solicitacaoDao.Alterar(alterarsolicitacao);

			// TODO Auto-generated catch block
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Auditoria salva com sucesso.", ""));
		} catch (Exception e) {

			// TODO Auto-generated catch block
			context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao alterar ou salvar a auditoria.", ""));
			// TODO: handle exception
		}

		return "";
	}

	/**
	 * Traz o formulario de auditoria interna atualizado se ja estiver salvo
	 */

	public void TrazAudInterna() {
		if (alterarsolicitacao.getAuditoriaInterna() == null) {
			auditoriaInterna = null;
			auditoriaInterna = new AuditoriaInterna();
		} else {
			auditoriaInterna = null;
			auditoriaInterna = alterarsolicitacao.getAuditoriaInterna();
		}
		// return "/solicitacao/alterasolictacao.jsf";
	}

	/**
	 * Altera o valor da solictação avulsamente
	 */
	public void AlteraValorSoli() {
		try {
			solicitacaoDao.Alterar(alterarsolicitacao);

			// TODO Auto-generated catch block
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Valor alterado com sucesso !", ""));
		} catch (Exception e) {

			context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro alterar o valor da solicitação !", ""));
		}

	}

	/**
	 * Seta os valores das variaveis pegando os dados da solicitação para quando
	 * pegar um alteração nova nao pega os dados da anterior
	 */

	public String AlteraSolicatacaoFeita() {

		// solitemp = solicitacaoDao.trazrsolicitacao(bnumero); // Soliictacao
		alterarsolicitacao = solicitacaoDao.trazrsolicitacao(bnumero);
		alterasolicitacaodenovo = true; // temporraria
		try {
			idcorrespondente= alterarsolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getCorrespondente().getIdcorrespondente();
		} catch (Exception e) {
			idcorrespondente=0;
			// TODO: handle exception
		}
		
		try {
			emaildeenvio = alterarsolicitacao.getEmailenvio();
		} catch (Exception e) {
			emaildeenvio="";
			// TODO: handle exception
		}
		
		try {
			bestadocomarca = alterarsolicitacao.getComarca().getUf().getIduf();
		} catch (Exception e) {
			bestadocomarca = 0;
		}

		try {
			idcomarca = alterarsolicitacao.getComarca().getIdcomarca();
		} catch (Exception e) {
			idcomarca = 0;
			// TODO: handle exception
		}

		try {
			valordaalcada = alterarsolicitacao.getValordaalcada();
		} catch (Exception e) {
			valordaalcada = 0;
			// TODO: handle exception
		}

		try {
			idorgao = alterarsolicitacao.getProcesso().getOrgao().getIdorgao();
		} catch (Exception e) {
			idorgao = 0;
		}

		try {
			idbancabusca = alterarsolicitacao.getBancaProcesso().getIdbanca();
		} catch (Exception e) {
			idbancabusca = 0;
		}
		try {
			numorgao = alterarsolicitacao.getProcesso().getNumorgao();
		} catch (Exception e) {
			numorgao = 0;
		}

		try {
			lide = alterarsolicitacao.getLide();
		} catch (Exception e) {
			lide = "";
		}

		try {
			instrucoes = alterarsolicitacao.getInstrucoes();
		} catch (Exception e) {
			instrucoes = "";
		}

		if (alterarsolicitacao.getRenumeracao().getTipoSolicitacaoCorrespondente().getTipoSolicitacao().getEspecie()
				.equals("AUDIENCIA")) {
			idgrupo = alterarsolicitacao.getGrupo();
			enviodesolicitacao = alterarsolicitacao.getEnviosolicitacao().getIdenviosolicitacao(); // Envio
																									// de
																									// preposto
			horaaudiencia = alterarsolicitacao.getHoraudiencia();
			datarealizacao = alterarsolicitacao.getDatasolictacao();
			idproacordo = alterarsolicitacao.isPropostaacordo();
		}
		return "/solicitacao/alteranovasolicitacao.jsf";
	}

	/***
	 * Verifica o processo se ja foi feito mais de 3 vezes na base apartir do dia
	 * 01/01/2014
	 */

	public boolean Verquantprocesso() {
		buscasolirep = null;
		boolean fez;
		// SolicitacaoDao testenovo= new SolicitacaoDao();
		solicitacaoDao.novabusca = true;
		SimpleDateFormat sdf = new SimpleDateFormat("mm-DD-yyyy");
		Date teste = null;
		try {
			teste = sdf.parse("01-01-2015");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// teste.setTime(arg0);
		receberepitadas = solicitacaoDao.buscargeral(0, "", 0, 0, "", 0, 0, 7, "A", teste, new Date(), "", "", 2, 0, 0,
				processo.getLocalizacao(), "", "");
		if (receberepitadas.size() >= 2) {
			fez = true;
		} else {
			fez = false;
		}
		return fez;
	}

	/***
	 * Conta para ver se tem lide
	 * 
	 * @return
	 */
	public boolean Verlide() {
		buscasolirep = null;
		boolean fezuma;
		fezuma = false;
		// SolicitacaoDao testenovo= new SolicitacaoDao();
		solicitacaoDao.novabusca = true;
		SimpleDateFormat sdf = new SimpleDateFormat("mm-DD-yyyy");
		Date teste = null;
		try {
			teste = sdf.parse("01-01-2015");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// teste.setTime(arg0);
		receberepitadas = solicitacaoDao.buscargeral(0, "", 0, 0, "", 0, 0, 0, "A", teste, new Date(), "", "", 1, 0, 0,
				processo.getLocalizacao(), "", "S");
		// Crai a instacia do novo processo

		if (receberepitadas.size() >= 1) {
			fezuma = true;
		} else {
			fezuma = false;
		}
		return fezuma;
	}

	/**
	 * Ver se severo scapo ja fez ms de 3 pra zerar o valor
	 * 
	 * @return
	 */
	public boolean VerquantprocessoAmoedo() {
		buscasolirep = null;
		boolean fez;
		// SolicitacaoDao testenovo= new SolicitacaoDao();
		solicitacaoDao.novabusca = true;
		SimpleDateFormat sdf = new SimpleDateFormat("mm-DD-yyyy");
		Date teste = null;
		try {
			teste = sdf.parse("01-01-2015");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// teste.setTime(arg0);
		receberepitadas = solicitacaoDao.buscargeral(0, "",
				renumeracao2.getTipoSolicitacaoCorrespondente().getCorrespondente().getIdcorrespondente(), 0, "", 0, 0,
				7, "A", teste, new Date(), "", "", 1, 0, 0, processo.getLocalizacao(), "", "");
		if (receberepitadas.size() >= 3) {
			fez = true;
		} else {
			fez = false;
		}
		return fez;
	}

	/**
	 * Verifica se ja feita com a pasta do processo duas audiencias
	 */
	public boolean VerfeitaNoDiaAudiencia() {
		buscasolirep = null;
		boolean fezuma;
		fezuma = false;
		// SolicitacaoDao testenovo= new SolicitacaoDao();
		solicitacaoDao.novabusca = true;
		SimpleDateFormat sdf = new SimpleDateFormat("mm-DD-yyyy");
		Date teste = null;
		try {
			teste = sdf.parse("01-01-2015");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// teste.setTime(arg0);
		receberepitadas = solicitacaoDao.buscargeral(0, "", 0, 0, "", 0, 0, 0, "A", new Date(), new Date(), "", "", 1,
				0, 0, processo.getLocalizacao(), "", "");
		// Crai a instacia do novo processo

		if (receberepitadas.size() >= 1) {
			fezuma = true;
		} else {
			fezuma = false;
		}
		return fezuma;
	}

	/**
	 * Verifica se ja feita com a pasta do processo duas diligencias no mesmo dia
	 */
	public boolean VerfeitaNoDiaTipoDiligencia() {
		buscasolirep = null;
		boolean fezuma;
		fezuma = false;
		// SolicitacaoDao testenovo= new SolicitacaoDao();
		solicitacaoDao.novabusca = true;
		SimpleDateFormat sdf = new SimpleDateFormat("mm-DD-yyyy");
		Date teste = null;
		try {
			teste = sdf.parse("01-01-2015");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// teste.setTime(arg0);
		receberepitadas = solicitacaoDao.buscargeral(0, "", 0, 0, "", 0, 0, 0, "D", new Date(), new Date(), "", "", 1,
				0, 0, processo.getLocalizacao(), "", "");
		// Crai a instacia do novo processo

		if (receberepitadas.size() >= 1) {
			fezuma = true;
		} else {
			fezuma = false;
		}
		return fezuma;
	}

	/**
	 * Traz quantidade deprocesso
	 * 
	 * @return
	 */
	public Integer BuscaProcessoBase() {
		try {
			DaoBuscaProcesso daoBuscaProcesso = new DaoBuscaProcesso();
			Integer traz = daoBuscaProcesso.quantidadefeita(processo.getNumeroprocesso(), idcorrespondente);
			return traz;
		} catch (Exception e) {
			return 0;
		}

	}

	/***
	 * Ver para banco santander so pode gazer uma solicitação apartir 20/09/2016 as
	 * 19:01
	 */
	public boolean VerquantprocessoSant() {
		buscasolirep = null;
		boolean fez;
		// SolicitacaoDao testenovo= new SolicitacaoDao();
		solicitacaoDao.novabusca = true;
		SimpleDateFormat sdf = new SimpleDateFormat("mm-DD-yyyy");
		Date teste = null;
		try {
			teste = sdf.parse("01-01-2015");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// teste.setTime(arg0);
		receberepitadas = solicitacaoDao.buscargeral(0, "", 0, 0, "", 0, 0, 7, "A", teste, new Date(), "", "", 2, 0, 0,
				processo.getLocalizacao(), "", "");
		if (receberepitadas.size() >= 1) {
			fez = true;
		} else {
			fez = false;
		}
		return fez;
	}

	/**
	 * Salava log
	 */
	public void SalvarLogSys(Solicitacao soli, String texto) {
		daolog = new LogSistemaDao();
		logSistema = new LogSistema();
		logSistema.setDatalog(new Date());
		logSistema.setIdsolicitacao(soli.getIdsolicitacao());
		logSistema.setUsuario(usuario.getNomecompleto());
		logSistema.setTela("LOG DE SISTEMA");
		logSistema.setDescricao(texto + soli.toString());
		daolog.Salvar(logSistema);
		logSistema = null;
		daolog = null;
	}

	/*
	 * Atualiza so dados da rejeicao
	 */
	public void PegaRejeicao(String dados, String dados2) {
		String teste = "atualiza;";
		setMotivorejeicao("Dados do Arquivo :" + dados2 + " - " + dados);

	}

	/**
	 * Envia msg para a banca sobrea rejeição do arquivo
	 * 
	 * @param emailorigem
	 * @param emaildestino
	 * @param msg
	 * @param dadosarquivo
	 */
	public void EnviarEmailRejeitadoArquivo(String emailorigem, String emaildestino, String emailgestorbanca,
			String msg, String dadosarquivo, Integer idarquivocpro) {

		try {

			ArquivoAnexoCPROSalvo atualiza = new ArquivoAnexoCPROSalvo();

			atualiza = soliArqCproDaoSalvo.BuscaUnico(idarquivocpro);

			atualiza.setMotivodarejeicao("Arquivo: " + atualiza.getNomeDocumento() + "\n" + "Pasta: "
					+ atualiza.getPastaDoProcesso() + "\n" + msg);
			atualiza.setRejeitado(true);
			soliArqCproDaoSalvo.Alterar(atualiza);

			enviaEmail.EnviarMsgBanca(emailorigem, emaildestino, emailgestorbanca, "", "",
					"Pasta: " + atualiza.getPastaDoProcesso() + "\n" + "Arquivo: " + atualiza.getNomeDocumento() + "\n"
							+ msg,
					"** REJEIÇÃO DE ARQUIVO ** - Pasta: " + atualiza.getPastaDoProcesso() + " - Arquivo: "
							+ atualiza.getNomeDocumento());

			msgemailarquvoreijatado = "";
			emailougrupo = "";
			motivorejeicao = "";
			histArqCproRejeitado.setMotivo(atualiza.getMotivodarejeicao());
			histArqCproRejeitado.setRejeitadoem(new Date());
			histArqCproRejeitado.setIdarquivocppro(idarquivocpro);
			soliHistArqCproRejDao.Salvar(histArqCproRejeitado);

			arquivoAnexoCPROSalvos = null;
			arquivoAnexoCPROSalvos = soliArqCproDaoSalvo.traztodos(alterarsolicitacao.getProcesso().getLocalizacao(),
					alterarsolicitacao.getIdsolicitacao());
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Email enviado ao responsavel.", ""));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			context = FacesContext.getCurrentInstance();
			;
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao enviar email.", ""));
			msgemailarquvoreijatado = "";
			emailougrupo = "";
			motivorejeicao = "";
		}

	}

	public List<Rejeitado> getRejeitados() {

		return rejeitados;
	}

	public void setRejeitados(List<Rejeitado> rejeitados) {
		this.rejeitados = rejeitados;
	}

	/**
	 * Traz os rejeitados
	 */
	public List<Rejeitado> listarejeitados() {

		try {
			rejeitados = new ArrayList<Rejeitado>();

			rejeitados = daoBuscaRejeitados.CosultaTodos(datainirej, datafinrej, "");
			context = FacesContext.getCurrentInstance();
			;
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Listagem carregada com sucesso.", ""));

		} catch (Exception e) {
			context = FacesContext.getCurrentInstance();
			;
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "A listagem não pode ser lida no momento.", ""));
		}

		return rejeitados;

	}

	/**
	 * Executa a função jsf
	 * 
	 * @throws IOException
	 */

	public String getParamjs() {
		return paramjs;
	}

	public void setParamjs(String paramjs) {
		this.paramjs = paramjs;
	}

	public String getBuscamaluca() {
		return buscamaluca;
	}

	public void setBuscamaluca(String buscamaluca) {
		this.buscamaluca = buscamaluca;
	}

	/**
	 * 
	 */
	public String Voltasolialteradados() {

		return "/solicitacao/dadosolicitacao.jsf";
	}

	/**
	 * Muda o texto de avaliacao 5 estrelas do colaborador
	 */

	public String MudaTextoAvaliacao() {

		if (avaliacaocolab.equals(0)) {
			textoavaliacaocolab = "";
		} else if (avaliacaocolab.equals(1)) {
			textoavaliacaocolab = "Péssimo:";
		} else if (avaliacaocolab.equals(2)) {
			textoavaliacaocolab = "Ruim:";
		} else if (avaliacaocolab.equals(3)) {
			textoavaliacaocolab = "Médio:";
		} else if (avaliacaocolab.equals(4)) {
			textoavaliacaocolab = "Bom:";
		} else if (avaliacaocolab.equals(5)) {
			textoavaliacaocolab = "Excelente:";
		} else {
			textoavaliacaocolab = "";
		}

		return textoavaliacaocolab;
	}

	/**
	 * Zera o texto da avaliação
	 * 
	 * @return
	 */
	public String ZeraTextoAvaliacao() {
		textoavaliacaocolab = "";
		avaliacaocolab = 0;
		return textoavaliacaocolab;
	}

	/**
	 * Salva a avaliação
	 */

	public String SalvarAvaliacao() {
		try {

			alterarsolicitacao.setAvaliacaonota(avaliacaocolab);
			alterarsolicitacao.setTextoavaliacao(textoavaliacaocolab);
			solicitacaoDao.Alterar(alterarsolicitacao);
			// TODO Auto-generated catch block
			context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitação avaliada  com successo.", ""));

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao avaliar a solicitação.", ""));
			return BuscaUnicaAlteracao();
		}
		return BuscaUnicaAlteracao();
	}

	/**
	 * Baixas os arquivo enviados para o correspoendente
	 */
	public void FileDownload(String nome) {

		InputStream stream;
		try {
			stream = new FileInputStream(nome);
			File arq1 = new File(nome);
			arq1.getAbsoluteFile();
			file = new DefaultStreamedContent(stream, "application/octet-stream", arq1.getName());
		} catch (FileNotFoundException e) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao baixar o arquivo.", ""));
		}

	}

}
