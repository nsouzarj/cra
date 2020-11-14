package br.adv.cra.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.adv.cra.intefaces.IPersitenciaDao;

public class OrgaoIDao implements IPersitenciaDao {
	@PersistenceContext
	EntityManager entityManager;

	public OrgaoIDao() {
	}

	public List<Object> listargeral() {
		// TODO Auto-generated method stub
		return null;
	}

	public void Salvar(Object object) {
		// TODO Auto-generated method stub
		entityManager.persist(object);

	}

	public void Excluir(Object object) {
		entityManager.remove(object);
		// TODO Auto-generated method stub

	}

	public void Alterar(Object object) {
		// TODO Auto-generated method stub
		entityManager.merge(object);
	}

}
