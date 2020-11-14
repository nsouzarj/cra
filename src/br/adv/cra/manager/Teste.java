package br.adv.cra.manager;

import br.adv.cra.entity.Orgao;
import br.adv.cra.persistence.OrgaoIDao;

public class Teste {

	public static void main(String[] args) {
		Orgao orgao = new Orgao();
		OrgaoIDao orgaoIDao = new OrgaoIDao();
		orgao.setDescricao("Porrrraaaaa");
		orgaoIDao.Salvar(orgao);

	}

}
