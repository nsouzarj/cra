package br.adv.cra.intefaces;

import java.util.List;

/**
 * @category Interfade
 * @author nelson
 * 
 */
public interface IPersitenciaDao {
	public List<Object> listargeral();

	public void Salvar(Object object);

	public void Excluir(Object object);

	public void Alterar(Object object);

}
