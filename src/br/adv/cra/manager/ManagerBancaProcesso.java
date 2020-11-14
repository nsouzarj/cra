package br.adv.cra.manager;

import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import br.adv.cra.entity.BancaProcesso;
import br.adv.cra.persistence.BancaProcessoDao;
@ManagedBean(name = "bancas")
@SessionScoped
public class ManagerBancaProcesso implements Serializable {

	private static final long serialVersionUID = 1L;
	private BancaProcesso bancaProcesso;
	private BancaProcesso bancaProcesso2;
	private BancaProcessoDao bancaProcessoDao;
	private List<BancaProcesso> listabancas;
	private String nomebanca;
	private String descricaobanca;
	private String emaildabanca;
	private String emaildogestor;
	private boolean altera;
	private boolean btaltera=true;
	private Integer idbancabusca;
	private Integer idnovo;

	public ManagerBancaProcesso() {
		bancaProcessoDao = new BancaProcessoDao();
		bancaProcesso = new BancaProcesso();
		bancaProcesso2 = new BancaProcesso();
		descricaobanca = "";
		emaildogestor = "";
		emaildabanca = "";
		idbancabusca = 0;
		altera = true;
		NovaBanca();
	}

	public ManagerBancaProcesso(BancaProcesso bancaProcesso, BancaProcesso bancaProcesso2,
			BancaProcessoDao bancaProcessoDao, List<BancaProcesso> listabancas, String nomebanca, String descricaobanca,
			String emaildabanca, String emaildogestor, boolean altera, Integer idbancabusca) {
		this.bancaProcesso = bancaProcesso;
		this.bancaProcesso2 = bancaProcesso2;
		this.bancaProcessoDao = bancaProcessoDao;
		this.listabancas = listabancas;
		this.nomebanca = nomebanca;
		this.descricaobanca = descricaobanca;
		this.emaildabanca = emaildabanca;
		this.emaildogestor = emaildogestor;
		this.altera = altera;
		this.idbancabusca = idbancabusca;
	}

	
	public boolean isBtaltera() {
		return btaltera;
	}

	public void setBtaltera(boolean btaltera) {
		this.btaltera = btaltera;
	}

	public Integer getIdnovo() {
		return idnovo;
	}

	public void setIdnovo(Integer idnovo) {
		this.idnovo = idnovo;
	}

	public String getEmaildogestor() {
		return emaildogestor;
	}

	public void setEmaildogestor(String emaildogestor) {
		this.emaildogestor = emaildogestor;
	}

	public BancaProcesso getBancaProcesso2() {
		return bancaProcesso2;
	}

	public void setBancaProcesso2(BancaProcesso bancaProcesso2) {
		this.bancaProcesso2 = bancaProcesso2;
	}

	public ManagerBancaProcesso(BancaProcesso bancaProcesso, BancaProcessoDao bancaProcessoDao) {
		this.bancaProcesso = bancaProcesso;
		this.bancaProcessoDao = bancaProcessoDao;
	}

	public Integer getIdbancabusca() {
		return idbancabusca;
	}

	public void setIdbancabusca(Integer idbancabusca) {
		this.idbancabusca = idbancabusca;
	}

	public String getNomebanca() {
		return nomebanca;
	}

	public boolean isAltera() {
		return altera;
	}

	public void setAltera(boolean altera) {
		this.altera = altera;
	}

	public void setNomebanca(String nomebanca) {
		this.nomebanca = nomebanca;
	}

	public String getDescricaobanca() {
		return descricaobanca;
	}

	public void setDescricaobanca(String descricaobanca) {
		this.descricaobanca = descricaobanca;
	}

	public String getEmaildabanca() {
		return emaildabanca;
	}

	public void setEmaildabanca(String emaildabanca) {
		this.emaildabanca = emaildabanca;
	}

	/**
	 * Traz bancas cadastradas
	 * 
	 * @return
	 */
	public List<BancaProcesso> trazbancas() {

		return listabancas;
	}

	public List<BancaProcesso> getListabancas() {
		listabancas = bancaProcessoDao.buscargeral();
		return listabancas;
	}

	public void setListabancas(List<BancaProcesso> listabancas) {
		this.listabancas = listabancas;
	}
	
	public BancaProcesso getBancaProcesso() {
		return bancaProcesso;
	}

	public void setBancaProcesso(BancaProcesso bancaProcesso) {
		this.bancaProcesso = bancaProcesso;
	}

	public BancaProcessoDao getBancaProcessoDao() {
		return bancaProcessoDao;
	}

	public void setBancaProcessoDao(BancaProcessoDao bancaProcessoDao) {
		this.bancaProcessoDao = bancaProcessoDao;
	}

	/**
	 * Salva a banca
	 * 
	 * @return
	 */
	public String SalvarBanca() {
		bancaProcesso.setBanca(nomebanca);
		bancaProcesso.setDescricao(descricaobanca);
		bancaProcesso.setAtiva(true);
		bancaProcesso.setEmail(emaildabanca);
		bancaProcesso.setEmailgestordabanca(emaildogestor);
		try {
			bancaProcessoDao.Salvar(bancaProcesso);
			FacesContext context;
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Banca salva com sucesso", ""));
			altera = false;
			return "";
		} catch (Exception e) {
			FacesContext context;
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvara banca", ""));
			return "";
		}

	}

	/**
	 * Pega os dados da banca
	 * 
	 * @param idbanca
	 */
	public void AlterarBanca() {
		altera = true;
		
		try {
			bancaProcesso2 = bancaProcessoDao.trazunico(idbancabusca);
			idnovo=bancaProcesso2.getIdbanca();
			nomebanca = bancaProcesso2.getBanca();
			descricaobanca = bancaProcesso2.getDescricao();
			emaildabanca = bancaProcesso2.getEmail();
			emaildogestor =bancaProcesso2.getEmailgestordabanca();
     
		} catch (Exception e) {
			FacesContext context;
			context = FacesContext.getCurrentInstance();
		
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro ao busca a banca", ""));
		}

	}

	/**
	 * Salva a banca alterada no sistema
	 * 
	 * @param idbanca
	 */
	public void SalvaBancaAlterada() {
		
		bancaProcesso2=new BancaProcesso();
		bancaProcesso2.setIdbanca(idnovo);
		bancaProcesso2.setBanca(nomebanca);
		bancaProcesso2.setDescricao(descricaobanca);
		bancaProcesso2.setEmail(emaildabanca);
		bancaProcesso2.setEmailgestordabanca(emaildogestor);
		bancaProcesso2.setAtiva(true);
		btaltera=true;
		try {
			bancaProcessoDao.Alterar(bancaProcesso2);
			FacesContext context;
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Banca alterada com sucesso", ""));
			getListabancas();
		} catch (Exception e) {
			FacesContext context;
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro ao salvar banca", ""));

		}
	}

	/**
	 * Seta uma nova banca do processo
	 * 
	 * @return
	 */
	public String NovaBanca() {
		nomebanca = "";
		descricaobanca = "";
		emaildabanca = "";
		return "";
	}

}