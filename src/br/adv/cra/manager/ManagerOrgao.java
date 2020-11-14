package br.adv.cra.manager;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.adv.cra.entity.Orgao;
import br.adv.cra.persistence.OrgaoDao;

@ManagedBean(name = "orgaos")
@SessionScoped
public class ManagerOrgao {
	private Orgao orgao;
	private OrgaoDao orgaoDao;
	private Boolean altera_orgao;
	private List<Orgao> orgaos;
	private Integer codorgao;

	
	public ManagerOrgao() {
		orgao = new Orgao();
		orgaoDao = new OrgaoDao();
		altera_orgao = false;
	}

	public Integer getCodorgao() {
		return codorgao;
	}

	public void setCodorgao(Integer codorgao) {
		this.codorgao = codorgao;
	}

	/**
	 * Salva os orgaos
	 * 
	 * @return
	 */
	public String Salvarorgao() {
		if (altera_orgao == false) {
			orgaoDao.Salvar(orgao);
		} else if (altera_orgao == true) {
			orgaoDao.Alterar(orgao);
		}
		orgaos = null;

		return "/orgao";
	}

	public Orgao getOrgao() {
		return orgao;
	}

	public void setOrgao(Orgao orgao) {
		this.orgao = orgao;
	}

	public OrgaoDao getOrgaoDao() {
		return orgaoDao;
	}

	public void setOrgaoDao(OrgaoDao orgaoDao) {
		this.orgaoDao = orgaoDao;
	}

	public Boolean getAltera_orgao() {
		return altera_orgao;
	}

	public void setAltera_orgao(Boolean altera_orgao) {
		this.altera_orgao = altera_orgao;
	}

	/**
	 * Lista os orgao
	 * 
	 * @return
	 */
	public List<Orgao> getOrgaos() {
		if (orgaos == null) {
			orgaos = orgaoDao.listar();
		}
		return orgaos;
	}

	public void setOrgaos(List<Orgao> orgaos) {
		this.orgaos = orgaos;
	}

	/**
	 * Traz o orgao
	 * 
	 * @return
	 */
	public String Trazorgao() {
		altera_orgao = true;
		orgao = orgaoDao.trazorgao(codorgao);
		return "/orgao";
	}

	/**
	 * Limpa a classe orgao
	 * 
	 * @return
	 */
	public String Limpar() {
		orgao = null;
		orgao = new Orgao();
		altera_orgao = false;
		return "/orgao";
	}

	/**
	 * Excluir os orgaos da tabela do banco de dados
	 * 
	 * @return
	 */
	public String Excluir() {
		orgao = orgaoDao.trazorgao(codorgao);
		orgaoDao.Excluir(orgao);
		orgao = null;
		orgao = new Orgao();
		orgaos = null;
		altera_orgao = false;
		return "/orgao";
	}
}
