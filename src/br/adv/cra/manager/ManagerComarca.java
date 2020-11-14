package br.adv.cra.manager;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.adv.cra.entity.Comarca;
import br.adv.cra.entity.Uf;
import br.adv.cra.persistence.ComarcaDao;
import br.adv.cra.persistence.UfDao;

@ManagedBean(name = "comarcas")
@SessionScoped

public class ManagerComarca implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer codigocomarca;
	private Integer codigouf;
	private Comarca comarca;
	private ComarcaDao comarcaDao;
	private UfDao ufDao;
	private Uf uf;
	private boolean altera_comarca;
	public List<Comarca> comarcas;
	public List<Comarca> listaporestado;
	public List<Uf> ufs;
	private static String MSG_VALIDACAO_COMARCA = "Comarca cadastrada com sucesso.";

	public ManagerComarca() {
		comarca = new Comarca();
		comarcaDao = new ComarcaDao();
		ufDao = new UfDao();
		altera_comarca = false;
		codigouf=0;
	}

	public String NovaComarca() {
		comarca = new Comarca();
		return "";
	}

	public boolean isAltera_comarca() {
		return altera_comarca;
	}

	public void setAltera_comarca(boolean altera_comarca) {
		this.altera_comarca = altera_comarca;
	}

	public String SalvarComarca() {
		FacesContext context = FacesContext.getCurrentInstance();
		uf = ufDao.trazuf(codigouf);
		comarca.setUf(uf);
		if (altera_comarca == false) {
			comarcaDao.Salvar(comarca);
		} else if (altera_comarca == true) {
			comarcaDao.Alterar(comarca);
		}
		altera_comarca = false;
		comarca = new Comarca();
		getComarcas();
		FacesMessage msg = new FacesMessage(MSG_VALIDACAO_COMARCA);
		context.addMessage(null, msg);
		return "/comarcas";
	}

	public String ExlcuiComarca() {
		Comarca teste = new Comarca();
		teste = comarcaDao.trazcomarca(codigocomarca);
		comarcaDao.excluir(teste);
		return "/comarcas";
	}
	
	//Busca por estado e nome
	public String BuscaComarcaEstado(){
		getListaporestado();
		return "/comarcas";
	}
	
	

	public Comarca buscarunica() {
		comarca = comarcaDao.trazcomarca(codigocomarca);
		return comarca;
	}

	public String AlterarComarca() {
		comarca = comarcaDao.trazcomarca(codigocomarca);
		return "/comarcas";
	}

	public List<Comarca> getComarcas() {
		List<Comarca> comarcas;
		comarcas = comarcaDao.buscargeral();
		return comarcas;
	}

	public void setComarcas(List<Comarca> comarcas) {
		this.comarcas = comarcas;
	}

	public Integer getCodigocomarca() {
		return codigocomarca;
	}

	public void setCodigocomarca(Integer codigocomarca) {
		this.codigocomarca = codigocomarca;
	}

	public Integer getCodigouf() {
		return codigouf;
	}

	public void setCodigouf(Integer codigouf) {
		this.codigouf = codigouf;
	}

	public Comarca getComarca() {
		return comarca;
	}

	public void setComarca(Comarca comarca) {
		this.comarca = comarca;
	}

	public List<Uf> getUfs() {
		ufs = ufDao.listagem();
		return ufs;
	}

	public void setUfs(List<Uf> ufs) {
		this.ufs = ufs;
	}

	public Uf getUf() {
		return uf;
	}

	public void setUf(Uf uf) {
		this.uf = uf;
	}

	public List<Comarca> getListaporestado() {
		List<Comarca>teste=null;
	     teste=comarcaDao.Buscacom("" ,codigouf);
		return teste;
	}

	public void setListaporestado(List<Comarca> listaporestado) {
		this.listaporestado = listaporestado;
	}
	
	

}
