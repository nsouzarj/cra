package br.adv.cra.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.human.gateway.client.bean.Response;
import com.human.gateway.client.bean.SimpleMessage;
import com.human.gateway.client.exception.ClientHumanException;
import com.human.gateway.client.service.SimpleMessageService;
//import com.mysql.jdbc.BestResponseTimeBalanceStrategy;

import br.adv.cra.entity.ArquivoColaborador;
import br.adv.cra.entity.Banco;
import br.adv.cra.entity.Comarca;
import br.adv.cra.entity.ComarcaCorrespondente;
import br.adv.cra.entity.ComarcaPossui;
import br.adv.cra.entity.Correspondente;
import br.adv.cra.entity.EmailCorrespondente;
import br.adv.cra.entity.Endereco;
import br.adv.cra.entity.Envio;
import br.adv.cra.entity.GedFinanceiro;
import br.adv.cra.entity.Renumeracao;
import br.adv.cra.entity.SoliArquivo;
import br.adv.cra.entity.Solicitacao;
import br.adv.cra.entity.SolicitacaoAnexo;
import br.adv.cra.entity.SolicitacaoPossuiArquivo;
import br.adv.cra.entity.TipoSolicitacao;
import br.adv.cra.entity.TipoSolicitacaoCorrespondente;
import br.adv.cra.entity.Uf;
import br.adv.cra.persistence.BancoDao;
import br.adv.cra.persistence.ComarcaDao;
import br.adv.cra.persistence.CorrespondenteDao;
import br.adv.cra.persistence.DaoArqColaborador;
import br.adv.cra.persistence.DaoGedFinanceiro;
import br.adv.cra.persistence.EmailCorrespondenteDao;
import br.adv.cra.persistence.EnderecoDao;
import br.adv.cra.persistence.EnvioDao;
import br.adv.cra.persistence.PossuiComarcaDao;
import br.adv.cra.persistence.RenumeracaoDao;
import br.adv.cra.persistence.SolicitacaoDao;
import br.adv.cra.persistence.TipoSolicitacaoDao;
import br.adv.cra.persistence.UfDao;
import br.adv.cra.utilitarios.Converte;
import br.adv.cra.utilitarios.DownloadArquivo;

@ManagedBean(name = "corresponde")
@SessionScoped
public class ManagerCorrespondente implements Serializable {

	private static final long serialVersionUID = 1L;
	private static String MSG_VALIDACAO_CORRESPONDENTE = "Correspondente cadastrado com sucesso!";
	// private static String MSG_ERRO_CORRESPONDENTE =
	// "Erro ao cadastrar correspondente";
	private Correspondente correspondente;
	private Endereco endereco;
	private Banco banco;
	private Comarca comarca;
	private EmailCorrespondente emailCorrespondente;
	private CorrespondenteDao correspondenteDao;
	private EnderecoDao enderecoDao;
	private ComarcaDao comarcaDao;
	private TipoSolicitacao solicitacao;
	private PossuiComarcaDao possuiComarcaDao;
	private SolicitacaoDao solicitacaoDao;
	private BancoDao bancoDao;
	private RenumeracaoDao renumeracaoDao;
	private TipoSolicitacaoDao tipoSolicitacaoDao;
	private Renumeracao renumeracao;
	private ComarcaPossui comarcaPossui;
	private UfDao ufDao;
	private EnvioDao envioDao;
	private DaoArqColaborador arqColaborador;
	private ArquivoColaborador ged;
	private EmailCorrespondenteDao emailCorrespondenteDao;
	private Envio envio;
	private Uf uf;
	private ComarcaCorrespondente comarcaCorrespondente;
	private TipoSolicitacaoCorrespondente tipoSolicitacaoCorrespondente;
	// Variaveis de busca
	private String nome;
	private String cpfcnpj;
	private String oab;
	private String comarcanome;
	private Integer coduf;
	private Integer codcomarca;
	private Integer codcomarcabusca;
	private Integer codcorres;
	private Integer tiposolicitacao;
	private Integer enviode;
	private FacesMessage msg;
	public List<Endereco> enderecos;
	public List<Correspondente> correspondentes;
	public List<Comarca> comarcas;
	public List<Banco> bancos;
	public List<Uf> ufs;
	public List<Uf> ufgeral;
	public List<Comarca> ComarcaBusca;
	public List<ComarcaPossui> comarcaPossuis;
	public List<ComarcaPossui> listacomarcascolaborador;
	public List<Renumeracao> renumeracoes;
	public List<TipoSolicitacao> tiposolicitacoes;
	public List<Banco> bancoscorrespondente;
	public List<Envio> envios;
	public List<EmailCorrespondente> emailCorrespondentes;
	public List<ArquivoColaborador> aequivoged;
	public List<GedFinanceiro> gedFinanceiros;
	private GedFinanceiro gedFinanceiro;
	private String formato;
	private Date datacad;
	private Integer nivelusuario; // Pega o nivel do usuario;
	private Integer tiposusuario;
	private Integer idtiposolicitacao;
	private Integer idrenumeracao;
	private Integer idcomarca;
	private Integer idcorrespondente;
	private Integer idbanco;
	private String agenciabanco;
	private String nomebanco;
	private String codbanco;
	private String contacorrente;
	private String emailenvio;
	private float valoralterar;
	private float variavelvalor;
	private DaoArqColaborador daoArqColaborador;
	private DaoGedFinanceiro daoGedFinanceiro;
	private String observacao;
	private Integer ufbusca;
	private Integer id_uf_busca;
	private boolean cobativo;
	private String enviasms;
	private String descricaoarquivo;
	private String obsged;
	private String mescompetencia;
	private float valorbruto;
	private float valor;
	private float desconto;
	private float percvolumetria;
	private float percdesconto;
	private float volumetria;
	private String uf1;
	private String notadebito;
	private Date datadoc;

	@Inject
	private ManagerSolicitacao solit;

	public ManagerCorrespondente(Correspondente correspondente, Endereco endereco, Banco banco, Comarca comarca,
			CorrespondenteDao correspondenteDao, EnderecoDao enderecoDao, ComarcaDao comarcaDao,
			TipoSolicitacao solicitacao, PossuiComarcaDao possuiComarcaDao, SolicitacaoDao solicitacaoDao,
			BancoDao bancoDao, RenumeracaoDao renumeracaoDao, TipoSolicitacaoDao tipoSolicitacaoDao,
			Renumeracao renumeracao, ComarcaPossui comarcaPossui, UfDao ufDao, Uf uf,
			ComarcaCorrespondente comarcaCorrespondente, TipoSolicitacaoCorrespondente tipoSolicitacaoCorrespondente,
			String nome, String cpfcnpj, String oab, String comarcanome, Integer coduf, Integer codcomarca,
			Integer codcomarcabusca, Integer codcorres, Integer tiposolicitacao, Integer enviode, FacesMessage msg,
			List<Endereco> enderecos, List<Correspondente> correspondentes, List<Comarca> comarcas, List<Banco> bancos,
			List<Uf> ufs, List<ComarcaPossui> comarcaPossuis, List<ComarcaPossui> listacomarcascolaborador,
			List<Renumeracao> renumeracoes, List<TipoSolicitacao> tiposolicitacoes, String formato, Date datacad,
			Integer nivelusuario, Float valoralterar, Float variavelvalor, Integer idbanco, String observacao,
			Integer ufbusca, Integer id_uf_busca, boolean cobativo, String enviasms) {

		this.correspondente = correspondente;
		this.endereco = endereco;
		this.banco = banco;
		this.comarca = comarca;
		this.correspondenteDao = correspondenteDao;
		this.enderecoDao = enderecoDao;
		this.comarcaDao = comarcaDao;
		this.solicitacao = solicitacao;
		this.possuiComarcaDao = possuiComarcaDao;
		this.solicitacaoDao = solicitacaoDao;
		this.bancoDao = bancoDao;
		this.renumeracaoDao = renumeracaoDao;
		this.tipoSolicitacaoDao = tipoSolicitacaoDao;
		this.renumeracao = renumeracao;
		this.comarcaPossui = comarcaPossui;
		this.ufDao = ufDao;
		this.uf = uf;
		this.comarcaCorrespondente = comarcaCorrespondente;
		this.tipoSolicitacaoCorrespondente = tipoSolicitacaoCorrespondente;
		this.nome = nome;
		this.cpfcnpj = cpfcnpj;
		this.oab = oab;
		this.comarcanome = comarcanome;
		this.coduf = coduf;
		this.codcomarca = codcomarca;
		this.codcomarcabusca = codcomarcabusca;
		this.codcorres = codcorres;
		this.tiposolicitacao = tiposolicitacao;
		this.enviode = enviode;
		this.msg = msg;
		this.enderecos = enderecos;
		this.correspondentes = correspondentes;
		this.comarcas = comarcas;
		this.bancos = bancos;
		this.ufs = ufs;
		this.comarcaPossuis = comarcaPossuis;
		this.listacomarcascolaborador = listacomarcascolaborador;
		this.renumeracoes = renumeracoes;
		this.tiposolicitacoes = tiposolicitacoes;
		this.formato = formato;
		this.datacad = datacad;
		this.nivelusuario = nivelusuario;
		this.valoralterar = valoralterar;
		this.variavelvalor = variavelvalor;
		this.idbanco = idbanco;
		this.observacao = observacao;
		this.ufbusca = ufbusca;
		this.id_uf_busca = id_uf_busca;
		this.cobativo = cobativo;
		this.enviasms = enviasms;

	}

	public String getMescompetencia() {
		return mescompetencia;
	}

	public void setMescompetencia(String mescompetencia) {
		this.mescompetencia = mescompetencia;
	}

	public String getDescricaoarquivo() {
		return descricaoarquivo;
	}

	public void setDescricaoarquivo(String descricaoarquivo) {
		this.descricaoarquivo = descricaoarquivo;
	}

	public String getObsged() {
		return obsged;
	}

	public void setObsged(String obsged) {
		this.obsged = obsged;
	}

	public boolean isCobativo() {
		return cobativo;
	}

	public void setCobativo(boolean cobativo) {
		this.cobativo = cobativo;
	}

	public Integer getUfbusca() {
		return ufbusca;
	}

	public void setUfbusca(Integer ufbusca) {
		this.ufbusca = ufbusca;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Integer getIdbanco() {
		return idbanco;
	}

	public void setIdbanco(Integer idbanco) {
		this.idbanco = idbanco;
	}

	public float getVariavelvalor() {
		return variavelvalor;
	}

	public void setVariavelvalor(float variavelvalor) {
		this.variavelvalor = variavelvalor;
	}

	public float getValoralterar() {
		return valoralterar;
	}

	public void setValoralterar(float valoralterar) {
		this.valoralterar = valoralterar;
	}

	public String getEmailenvio() {
		return emailenvio;
	}

	public void setEmailenvio(String emailenvio) {
		this.emailenvio = emailenvio;
	}

	public ManagerSolicitacao getSolit() {

		return solit;

	}

	public void setSolit(ManagerSolicitacao solit) {
		this.solit = solit;
	}

	public Integer getId_uf_busca() {
		return id_uf_busca;
	}

	public void setId_uf_busca(Integer id_uf_busca) {
		this.id_uf_busca = id_uf_busca;
	}

	public String getAgenciabanco() {
		return agenciabanco;
	}

	public void setAgenciabanco(String agenciabanco) {
		this.agenciabanco = agenciabanco;
	}

	public String getNomebanco() {
		return nomebanco;
	}

	public void setNomebanco(String nomebanco) {
		this.nomebanco = nomebanco;
	}

	public String getCodbanco() {
		return codbanco;
	}

	public void setCodbanco(String codbanco) {
		this.codbanco = codbanco;
	}

	public String getContacorrente() {
		return contacorrente;
	}

	public void setContacorrente(String contacorrente) {
		this.contacorrente = contacorrente;
	}

	public List<Uf> getUfs() {
		ufs = ufDao.listagem();
		return ufs;
	}

	public void setUfs(List<Uf> ufs) {
		this.ufs = ufs;
	}

	public EmailCorrespondenteDao getEmailCorrespondenteDao() {
		return emailCorrespondenteDao;
	}

	public void setEmailCorrespondenteDao(EmailCorrespondenteDao emailCorrespondenteDao) {
		this.emailCorrespondenteDao = emailCorrespondenteDao;
	}

	public List<EmailCorrespondente> getEmailCorrespondentes() {
		emailCorrespondentes = emailCorrespondenteDao.lista(codcorres);
		return emailCorrespondentes;
	}

	public void setEmailCorrespondentes(List<EmailCorrespondente> emailCorrespondentes) {
		this.emailCorrespondentes = emailCorrespondentes;
	}

	public Integer getIdcomarca() {
		return idcomarca;
	}

	public void setIdcomarca(Integer idcomarca) {
		this.idcomarca = idcomarca;
	}

	public Integer getIdcorrespondente() {
		return idcorrespondente;
	}

	public void setIdcorrespondente(Integer idcorrespondente) {
		this.idcorrespondente = idcorrespondente;
	}

	public Integer getIdrenumeracao() {
		return idrenumeracao;
	}

	public void setIdrenumeracao(Integer idrenumeracao) {
		this.idrenumeracao = idrenumeracao;
	}

	public Integer getIdtiposolicitacao() {
		return idtiposolicitacao;
	}

	public void setIdtiposolicitacao(Integer idtiposolicitacao) {
		this.idtiposolicitacao = idtiposolicitacao;
	}

	public Integer getTiposusuario() {
		return tiposusuario;
	}

	public void setTiposusuario(Integer tiposusuario) {
		this.tiposusuario = tiposusuario;
	}

	public void setRenumeracoes(List<Renumeracao> renumeracoes) {
		this.renumeracoes = renumeracoes;
	}

	public List<TipoSolicitacao> getTiposolicitacoes() {
		tiposolicitacoes = tipoSolicitacaoDao.buscargeral();
		return tiposolicitacoes;
	}

	public void setTiposolicitacoes(List<TipoSolicitacao> tiposolicitacoes) {
		this.tiposolicitacoes = tiposolicitacoes;
	}

	public List<ComarcaPossui> getListacomarcascolaborador() {
		listacomarcascolaborador = possuiComarcaDao.buscarcomarcar(codcorres);
		return listacomarcascolaborador;
	}

	public void setListacomarcascolaborador(List<ComarcaPossui> listacomarcascolaborador) {
		this.listacomarcascolaborador = listacomarcascolaborador;
	}

	public Integer getNivelusuario() {
		return nivelusuario;
	}

	public void setNivelusuario(Integer nivelusuario) {
		this.nivelusuario = nivelusuario;
	}

	public void TrazTodos() {
		getCorrespondentes();
	}

	public EnvioDao getEnvioDao() {
		return envioDao;
	}

	public void setEnvioDao(EnvioDao envioDao) {
		this.envioDao = envioDao;
	}

	public Envio getEnvio() {
		return envio;
	}

	public void setEnvio(Envio envio) {
		this.envio = envio;
	}

	public List<Envio> getEnvios() {
		envios = envioDao.buscargeral();
		return envios;
	}

	public void setEnvios(List<Envio> envios) {
		this.envios = envios;
	}

	public String NovoCorrespondente() {
		solit = new ManagerSolicitacao();
		endereco = new Endereco();
		correspondente = new Correspondente();
		codcorres = 0;
		coduf = 0;
		codcomarca = 0;
		tiposolicitacao = 0;
		codcomarcabusca = 0;
		nome = "";
		cpfcnpj = "";
		oab = "";
		listacomarcascolaborador = null;
		listacomarcascolaborador = null;
		renumeracoes = null;
		enviode = 0;
		idbanco = 0;
		observacao = "";
		return "/correspondente/cadcorrespondente";
	}

	public ManagerCorrespondente() {
		correspondente = new Correspondente();
		endereco = new Endereco();
		uf = new Uf();
		banco = new Banco();
		correspondenteDao = new CorrespondenteDao();
		enderecoDao = new EnderecoDao();
		comarcaDao = new ComarcaDao();
		bancoDao = new BancoDao();
		ufDao = new UfDao();
		possuiComarcaDao = new PossuiComarcaDao();
		comarca = new Comarca();
		comarcaCorrespondente = new ComarcaCorrespondente();
		comarcaPossui = new ComarcaPossui();
		solicitacaoDao = new SolicitacaoDao();
		tipoSolicitacaoDao = new TipoSolicitacaoDao();
		renumeracaoDao = new RenumeracaoDao();
		renumeracao = new Renumeracao();
		solicitacao = new TipoSolicitacao();
		tipoSolicitacaoCorrespondente = new TipoSolicitacaoCorrespondente();
		emailCorrespondenteDao = new EmailCorrespondenteDao();
		arqColaborador = new DaoArqColaborador();
		solit = new ManagerSolicitacao();
		envioDao = new EnvioDao();
		daoGedFinanceiro = new DaoGedFinanceiro();

		codcorres = 0;
		coduf = 0;
		codcomarca = 0;
		tiposolicitacao = 0;
		codcomarcabusca = 0;
		nome = "";
		cpfcnpj = "";
		oab = "";
		formato = "";
		enviode = 0;
		idbanco = 0;
		observacao = "";
		ufbusca = 0;
		id_uf_busca = 0;
		cobativo = true;
	}

	public void ListaGedArq() {
		getAequivoged();

	}

	public List<ArquivoColaborador> getAequivoged() {
		aequivoged = arqColaborador.buscargeral(codcorres);
		return aequivoged;
	}

	public void setAequivoged(List<ArquivoColaborador> aequivoged) {
		this.aequivoged = aequivoged;
	}

	public List<Banco> getBancoscorrespondente() {
		bancoscorrespondente = bancoDao.bancocorrespondente(codcorres);
		return bancoscorrespondente;
	}

	public void setBancoscorrespondente(List<Banco> bancoscorrespondente) {
		this.bancoscorrespondente = bancoscorrespondente;
	}

	public CorrespondenteDao getCorrespondenteDao() {
		return correspondenteDao;
	}

	public Date getDatacad() {
		return datacad;
	}

	public void setDatacad(Date datacad) {
		this.datacad = datacad;
	}

	public Integer getCodcomarca() {
		return codcomarca;
	}

	public ComarcaCorrespondente getComarcaCorrespondente() {
		return comarcaCorrespondente;
	}

	public void setComarcaCorrespondente(ComarcaCorrespondente comarcaCorrespondente) {
		this.comarcaCorrespondente = comarcaCorrespondente;
	}

	public void setCodcomarca(Integer codcomarca) {
		this.codcomarca = codcomarca;
	}

	public void setCorrespondenteDao(CorrespondenteDao correspondenteDao) {
		this.correspondenteDao = correspondenteDao;
	}

	public EnderecoDao getEnderecoDao() {
		return enderecoDao;
	}

	public void setEnderecoDao(EnderecoDao enderecoDao) {
		this.enderecoDao = enderecoDao;
	}

	public UfDao getUfDao() {
		return ufDao;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public Integer getCodcorres() {
		return codcorres;
	}

	public Integer getCodcomarcabusca() {
		return codcomarcabusca;
	}

	public void setCodcomarcabusca(Integer codcomarcabusca) {
		this.codcomarcabusca = codcomarcabusca;
	}

	public Integer getTiposolicitacao() {
		return tiposolicitacao;
	}

	public void setTiposolicitacao(Integer tiposolicitacao) {
		this.tiposolicitacao = tiposolicitacao;
	}

	public void setCodcorres(Integer codcorres) {
		this.codcorres = codcorres;
	}

	public void setUfDao(UfDao ufDao) {
		this.ufDao = ufDao;
	}

	public TipoSolicitacao getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(TipoSolicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

	public PossuiComarcaDao getPossuiComarcaDao() {
		return possuiComarcaDao;
	}

	public void setPossuiComarcaDao(PossuiComarcaDao possuiComarcaDao) {
		this.possuiComarcaDao = possuiComarcaDao;
	}

	public SolicitacaoDao getSolicitacaoDao() {
		return solicitacaoDao;
	}

	public void setSolicitacaoDao(SolicitacaoDao solicitacaoDao) {
		this.solicitacaoDao = solicitacaoDao;
	}

	public RenumeracaoDao getRenumeracaoDao() {
		return renumeracaoDao;
	}

	public void setRenumeracaoDao(RenumeracaoDao renumeracaoDao) {
		this.renumeracaoDao = renumeracaoDao;
	}

	public TipoSolicitacaoDao getTipoSolicitacaoDao() {
		return tipoSolicitacaoDao;
	}

	public void setTipoSolicitacaoDao(TipoSolicitacaoDao tipoSolicitacaoDao) {
		this.tipoSolicitacaoDao = tipoSolicitacaoDao;
	}

	public Uf getUf() {
		return uf;
	}

	public void setUf(Uf uf) {
		this.uf = uf;
	}

	public ComarcaDao getComarcaDao() {
		return comarcaDao;
	}

	public void setComarcaDao(ComarcaDao comarcaDao) {
		this.comarcaDao = comarcaDao;
	}

	public BancoDao getBancoDao() {
		return bancoDao;
	}

	public Integer getCoduf() {
		return coduf;
	}

	public void setCoduf(Integer coduf) {
		this.coduf = coduf;
	}

	public void setBancoDao(BancoDao bancoDao) {
		this.bancoDao = bancoDao;
	}

	public Renumeracao getRenumeracao() {
		return renumeracao;
	}

	public void setRenumeracao(Renumeracao renumeracao) {
		this.renumeracao = renumeracao;
	}

	public Integer getEnviode() {
		return enviode;
	}

	public void setEnviode(Integer enviode) {
		this.enviode = enviode;
	}

	public void NovoEndereco() {
		endereco = new Endereco();
	}

	public void IncluiEndereco() {
		enderecoDao.Salvar(endereco);
	}

	public Correspondente getCorrespondente() {
		return correspondente;
	}

	public void setCorrespondente(Correspondente correspondente) {
		this.correspondente = correspondente;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public Comarca getComarca() {
		return comarca;
	}

	public void setComarca(Comarca comarca) {
		this.comarca = comarca;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public List<Correspondente> getCorrespondentes() {
		if (correspondentes == null) {
			correspondentes = correspondenteDao.buscargeral();
		}
		return correspondentes;
	}

	public void setCorrespondentes(List<Correspondente> correspondentes) {
		this.correspondentes = correspondentes;
	}

	public List<Comarca> getComarcas() {
		comarcas = comarcaDao.buscargeral();
		return comarcas;
	}

	public void setComarcas(List<Comarca> comarcas) {
		this.comarcas = comarcas;
	}

	public List<Banco> getBancos() {
		return bancos;
	}

	public void setBancos(List<Banco> bancos) {
		this.bancos = bancos;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpfcnpj() {
		return cpfcnpj;
	}

	public void setCpfcnpj(String cpfcnpj) {
		this.cpfcnpj = cpfcnpj;
	}

	public String getOab() {
		return oab;
	}

	public void setOab(String oab) {
		this.oab = oab;
	}

	public String getComarcanome() {
		return comarcanome;
	}

	public void setComarcanome(String comarcanome) {
		this.comarcanome = comarcanome;
	}

	/**
	 * Pega os colaboradores pela comarca
	 * 
	 * @return
	 */
	public List<ComarcaPossui> getComarcaPossuis() {
		// ufbusca = solit.getBestadocomarca();
		comarcaPossuis = possuiComarcaDao.buscargeral(0, nome, cpfcnpj, oab, codcomarcabusca, id_uf_busca, cobativo);
		return comarcaPossuis;
	}

	public void setComarcaPossuis(List<ComarcaPossui> comarcaPossuis) {
		this.comarcaPossuis = comarcaPossuis;

	}

	/**
	 * Salva o correspondente
	 * 
	 * @return
	 */
	public String SalvaCorrespondente() {
		FacesContext context = FacesContext.getCurrentInstance();
		datacad = new Date();
		correspondente.setDatacadastro(datacad);
		uf = ufDao.trazuf(coduf);
		endereco.setUf(uf);
		IncluiEndereco();
		correspondente.setEnderecos(endereco);
		// correspondente.setObservacao(observacao);

		if (correspondente.getIdcorrespondente() == null) {
			comarca = comarcaDao.trazcomarca(1); // Comarca Default
			comarcaCorrespondente.setComarca(comarca);
			correspondenteDao.Salvar(correspondente);
			correspondente = correspondenteDao.trazcorrespondente(correspondente.getIdcorrespondente());
			comarcaCorrespondente.setCorrespondente(correspondente);
			comarcaPossui.setComarcaCorrespondente(comarcaCorrespondente);
			possuiComarcaDao.Salvar(comarcaPossui);
		} else {
			correspondenteDao.Salvar(correspondente);
		}

		msg = new FacesMessage(MSG_VALIDACAO_CORRESPONDENTE);
		context.addMessage(null, msg);

		return null;
	}

	/**
	 * Busca por filtro
	 * 
	 * @return
	 */
	public String BuscaPorFiltro() {
		// correspondentes=correspondenteDao.buscafiltro(nome, cpfcnpj, oab,
		// codcomarcabusca);
		getComarcaPossuis();
		// comarcaPossuis = possuiComarcaDao.buscargeral(0, nome, cpfcnpj, oab,
		// codcomarcabusca,id_uf_busca);
		return "/correspondente/correspondente";
	}

	/**
	 * Adciona a comarca
	 * 
	 * @return
	 */
	public String AdcionaComarca() {
		comarca = comarcaDao.trazcomarca(codcomarca);
		comarcaCorrespondente.setComarca(comarca);
		comarcaCorrespondente.setCorrespondente(correspondente);
		comarcaPossui.setComarcaCorrespondente(comarcaCorrespondente);
		possuiComarcaDao.Salvar(comarcaPossui);
		// Excluir a 5
		comarca = comarcaDao.trazcomarca(1);
		comarcaCorrespondente.setComarca(comarca);
		comarcaCorrespondente.setCorrespondente(correspondente);
		comarcaPossui.setComarcaCorrespondente(comarcaCorrespondente);
		possuiComarcaDao.Excluir(comarcaPossui);
		getComarcaPossuis();
		return "";

	}

	/**
	 * Inativar comarca
	 */

	public String InativaComarca() {
		possuiComarcaDao.Inativar(idcomarca, idcorrespondente);
		getComarcaPossuis();
		return "";

	}

	/***
	 * Ativa Comarca Novamente
	 */
	public String AtivaComarca() {
		possuiComarcaDao.Ativar(idcomarca, idcorrespondente);
		getComarcaPossuis();
		return "";

	}

	/**
	 * 
	 * Altera o valor da solicitacao, tela cad.correspondente. Alera o valor do tipo
	 * de solicaitacao .
	 */

	public String AlteraValor() {

		// renumeracao.setValor(valoralterar);
		renumeracaoDao.Alterar(renumeracao);
		getRenumeracoes();
		return "";
	}

	/**
	 * Traz apenas um unico valor na tabela renumeracao. valor tipo...05/06/2014
	 * tela cacorrespondente
	 */

	public String unicovalor() {

		renumeracao = renumeracaoDao.trazunicarenumeracao(idrenumeracao);

		return "";
	}

	/**
	 * Adciona o valor
	 * 
	 * @return
	 */
	public String AdcionaValor() {
		solicitacao = tipoSolicitacaoDao.trazunica(tiposolicitacao);
		tipoSolicitacaoCorrespondente.setTipoSolicitacao(solicitacao);
		envio = envioDao.TrazUnico(enviode);
		tipoSolicitacaoCorrespondente.setCorrespondente(correspondente);
		tipoSolicitacaoCorrespondente.setEnvio(envio);
		renumeracao.setAtivo(true);
		renumeracao.setTipoSolicitacaoCorrespondente(tipoSolicitacaoCorrespondente);
		renumeracaoDao.Salvar(renumeracao);
		getRenumeracoes();
		return "/cadcorrespondente";
	}

	/**
	 * Adiciona no banco
	 * 
	 **/

	public String AdcionaBanco() {
		if (banco.toString() != "") {
			banco.setCorrespondente(correspondente);
			banco.setCodbanco(codbanco);
			banco.setContacorrente(contacorrente);
			banco.setAgencia(agenciabanco);
			banco.setBanco(nomebanco);

			bancoDao.Salvar(banco);
			agenciabanco = "";
			nomebanco = "";
			codbanco = "";
			contacorrente = "";
		}
		return "";
	}

	/**
	 * Exclui a renumeracao da listagem
	 * 
	 * @return
	 */
	public String ExcluiValor() {
		renumeracao = renumeracaoDao.trazunicarenumeracao(idrenumeracao);
		try {
			renumeracaoDao.Excluir(renumeracao);
		} catch (Exception e) {
			FacesContext cont = FacesContext.getCurrentInstance();
			cont.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao excluir este valor ja está registrado em alguma solicitação.", ""));
		}

		getRenumeracoes();
		return "";
	}

	/**
	 * Inativa e ativa os valores
	 * 
	 * @return
	 */
	public String AtivarInativar() {
		renumeracao = renumeracaoDao.trazunicarenumeracao(idrenumeracao);
		if (renumeracao.isAtivo()) {
			renumeracao.setAtivo(false);
		} else {
			renumeracao.setAtivo(true);
		}

		renumeracaoDao.Alterar(renumeracao);
		getRenumeracoes();
		return "";

	}

	public String AdcionaEmail() {
		emailCorrespondente = new EmailCorrespondente();
		emailCorrespondente.setEmail(emailenvio);
		emailCorrespondente.setCorrespondente(correspondente);
		emailCorrespondenteDao.Salvar(emailCorrespondente);
		getEmailCorrespondentes();
		return "";
	}

	public String BuscaUnico() {
		solit = new ManagerSolicitacao();
		correspondente = correspondenteDao.trazcorrespondente(codcorres);
		endereco = correspondente.getEnderecos();
		renumeracoes = renumeracaoDao.buscargeral(codcorres, false);
		listacomarcascolaborador = possuiComarcaDao.buscarcomarcar(codcorres);
		banco = bancoDao.trazbancocorrespondente(codcorres);
		observacao = correspondente.getObservacao();
		if (banco == null) {
			banco = new Banco();
		}
		return "/correspondente/cadcorrespondente.jsf";
	}

	/**
	 * Busca as renumeracoes
	 * 
	 * @return
	 */
	public List<Renumeracao> getRenumeracoes() {
		renumeracoes = renumeracaoDao.buscargeral(codcorres, true);
		return renumeracoes;
	}

	/**
	 * Busca os envios
	 */
	public void enviosde() {

		getEnvios();
		// envios=null;
		// envios=envioDao.buscargeral();

	}

	public String Sair() {
		// LImpar tudo

		nome = "";
		cpfcnpj = "";
		oab = "";
		codcomarca = 0;

		// Limpa os campos do banco
		agenciabanco = "";
		nomebanco = "";
		codbanco = "";
		contacorrente = "";
		return "/menu.jsf";

	}

	public String ExcluiBanco() {
		try {
			bancoDao.Excluir(idbanco);
			getBancoscorrespondente();
		} catch (Exception e) {
			System.out.print("Erro");
		}
		return "";
	}

	public static void main(String[] args) {
		ManagerCorrespondente teste = new ManagerCorrespondente();

		System.out.print(teste.getCorrespondentes());

	}

	public List<Comarca> getComarcaBusca() {
		ComarcaBusca = comarcaDao.buscargeralporestado(id_uf_busca);
		return ComarcaBusca;
	}

	public void setComarcaBusca(List<Comarca> comarcaBusca) {
		ComarcaBusca = comarcaBusca;
	}

	public List<Uf> getUfgeral() {
		ufgeral = ufDao.listagem();
		return ufgeral;
	}

	public void setUfgeral(List<Uf> ufgeral) {
		this.ufgeral = ufgeral;
	}

	public String getEnviasms() {
		return enviasms;
	}

	public void setEnviasms(String enviasms) {
		this.enviasms = enviasms;
	}

	/**
	 * Envia msg para o telefone do correspondente
	 */
	@SuppressWarnings("unused")
	public String EnviaMsgTel() {
		Converte connverte = new Converte();
		SimpleMessageService cliente = new SimpleMessageService("cavalcanteramos", "cra.2014");
		SimpleMessage mensagem1 = new SimpleMessage();
		mensagem1.setTo("55" + connverte.traz(correspondente.getTelefonecelularprimario()));
		mensagem1.setMessage(enviasms);
		mensagem1.setSchedule(new Date());
		try {
			List<Response> retornos = cliente.send(mensagem1);
			if (retornos.get(0).isError() == true) {
				FacesContext cont = FacesContext.getCurrentInstance();
				cont.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao enviar a mensagem para o celular.", ""));
			} else if (retornos.get(0).isError() == false) {
				FacesContext cont = FacesContext.getCurrentInstance();
				cont.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Messagem enviada com sucesso para o celular.", ""));
			}

		} catch (ClientHumanException e) {
			FacesContext cont = FacesContext.getCurrentInstance();
			cont.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao enviar.", ""));
		}
		setEnviasms("");
		return "";
	}

	/**
	 * Envia arquivo do ged
	 * 
	 * @param event
	 * @return
	 * @throws FileNotFoundException
	 */
	public String EnviarArquivosNovos(FileUploadEvent event) throws FileNotFoundException {

		UploadedFile arquivoanexo = event.getFile();
		Date datanova = new Date();
		DaoArqColaborador dadsalvar = new DaoArqColaborador();

		File gednovo = new File(arquivoanexo.getFileName());
		if (correspondente.getIdcorrespondente() == null) {
			correspondente = correspondenteDao.trazcorrespondente(codcorres);
		}
		try {
			InputStream inputStream = arquivoanexo.getInputstream();

			// event.getFile().getInputstream();
			OutputStream out = new FileOutputStream(new File("G:\\arqsolicitacao\\gedcolaboradores\\"
					+ correspondente.getIdcorrespondente() + gednovo.getAbsoluteFile().getName()));

			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			inputStream.close();
			out.flush();
			out.close();
			daoArqColaborador = new DaoArqColaborador();
			ged = new ArquivoColaborador();
			ged.setDatainclusao(datanova);
			ged.setCorresp(correspondente);
			ged.setLink("G:\\arqsolicitacao\\gedcolaboradores\\" + correspondente.getIdcorrespondente()
					+ gednovo.getAbsoluteFile().getName());
			ged.setNome(gednovo.getAbsoluteFile().getName());
			ged.setDescricao(descricaoarquivo);
			ged.setObsged(obsged);
			ged.setMesanocompetencia(mescompetencia);
			dadsalvar.Salvar(ged);
			FacesContext cont1 = FacesContext.getCurrentInstance();
			cont1.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Arquivos adcionados com sucesso no servido.", ""));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			FacesContext cont1 = FacesContext.getCurrentInstance();
			cont1.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error ao salvar arquivo no servidor tente novamente. "
									+ "Caso o erro perssista, favor entrar em contato com o suporte técnico do "
									+ "CRA - suporte@cra.adv.br",
							"Erro"));
		}

		return "";
	}

	/**
	 * Baixa o arquivo ged do colaborador
	 * 
	 * @param linkarquivo
	 * @return
	 */
	public String BaixarArquivo(String linkarquivo) {
		// Cria a intanci para baixar o arquivo
		DownloadArquivo baixar = new DownloadArquivo();
		try {
			// Baixa do correspondente
			baixar.Abrir(linkarquivo, "", false);
		} catch (FileNotFoundException e1) {

			FacesContext cont1 = FacesContext.getCurrentInstance();
			cont1.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao baixar o arquivo anexo tente novamente", ""));
		}

		return "";
	}

	/**
	 * Excluir aquivo do ged do colaborador
	 * 
	 * @param idgedarquivo
	 * @return
	 */
	public void ExcluirArquivo(int idgedarquivo) {
		DaoArqColaborador dad = new DaoArqColaborador();
		dad.excluir(idgedarquivo);

	}

	/**
	 * 
	 * @param idgedarquivo
	 */
	public void ExcluirArquivoFin(int idgedfin) {
		DaoGedFinanceiro dad1 = new DaoGedFinanceiro();
		dad1.excluir(idgedfin);
	}

	/**
	 * Busca geral
	 * 
	 * @return
	 */
	public List<GedFinanceiro> getGedFinanceiros() {
		DaoGedFinanceiro gedfin = new DaoGedFinanceiro();
		gedFinanceiros = gedfin.buscargeral(codcorres);
		return gedFinanceiros;
	}

	public void setGedFinanceiros(List<GedFinanceiro> gedFinanceiros) {
		this.gedFinanceiros = gedFinanceiros;
	}

	public GedFinanceiro getGedFinanceiro() {
		return gedFinanceiro;
	}

	public void setGedFinanceiro(GedFinanceiro gedFinanceiro) {
		this.gedFinanceiro = gedFinanceiro;
	}

	public String EnviarArquivosGedFin(FileUploadEvent event) throws FileNotFoundException {

		UploadedFile arquivoanexo = event.getFile();
		Date datanova = new Date();
		// DaoGedFinanceiro gedfin = new DaoGedFinanceiro();
		File gednovofin = new File(arquivoanexo.getFileName());
		try {
			InputStream inputStream = arquivoanexo.getInputstream();

			// event.getFile().getInputstream();
			OutputStream out = new FileOutputStream(new File("G:\\arqsolicitacao\\gedcolaboradores\\arqfin\\"
					+ correspondente.getIdcorrespondente() + gednovofin.getAbsoluteFile().getName()));

			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			inputStream.close();
			out.flush();
			out.close();
			daoGedFinanceiro = new DaoGedFinanceiro();
			gedFinanceiro = new GedFinanceiro();
			gedFinanceiro.setData(datadoc);
			gedFinanceiro.setCorrespondente(correspondente);
			gedFinanceiro.setLink("G:\\arqsolicitacao\\gedcolaboradores\\arqfin\\"
					+ correspondente.getIdcorrespondente() + gednovofin.getAbsoluteFile().getName());
			gedFinanceiro.setNomearquivo(gednovofin.getAbsoluteFile().getName());
			gedFinanceiro.setValorbruto(valorbruto);
			gedFinanceiro.setDesconto(desconto);
			gedFinanceiro.setPercvolumetria(percvolumetria);
			gedFinanceiro.setValor(valor);
			gedFinanceiro.setVolumetria(volumetria);
			gedFinanceiro.setPercdesconto(percdesconto);
			gedFinanceiro.setUf(uf1);

			daoGedFinanceiro.Salvar(gedFinanceiro);
			FacesContext cont1 = FacesContext.getCurrentInstance();
			cont1.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Arquivos adcionados com sucesso no servido.", ""));
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

	public float getValorbruto() {
		return valorbruto;
	}

	public void setValorbruto(float valorbruto) {
		this.valorbruto = valorbruto;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public float getDesconto() {
		return desconto;
	}

	public void setDesconto(float desconto) {
		this.desconto = desconto;
	}

	public float getPercvolumetria() {
		return percvolumetria;
	}

	public void setPercvolumetria(float percvolumetria) {
		this.percvolumetria = percvolumetria;
	}

	public float getPercdesconto() {
		return percdesconto;
	}

	public void setPercdesconto(float percdesconto) {
		this.percdesconto = percdesconto;
	}

	public float getVolumetria() {
		return volumetria;
	}

	public void setVolumetria(float volumetria) {
		this.volumetria = volumetria;
	}

	public String getUf1() {
		return uf1;
	}

	public void setUf1(String uf1) {
		this.uf1 = uf1;
	}

	public String getNotadebito() {
		return notadebito;
	}

	public void setNotadebito(String notadebito) {
		this.notadebito = notadebito;
	}

	public Date getDatadoc() {
		return datadoc;
	}

	public void setDatadoc(Date datadoc) {
		this.datadoc = datadoc;
	}

}
