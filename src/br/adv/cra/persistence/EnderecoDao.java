package br.adv.cra.persistence;

import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import br.adv.cra.entity.Endereco;

public class EnderecoDao implements Serializable {
	private static final long serialVersionUID = 1L;
	private Session session;
	private Query query;
	private Transaction transaction;

	public EnderecoDao() {
	}

	/**
	 * Salva o endereco
	 * 
	 * @param endereco
	 * @return
	 */
	public String Salvar(Endereco endereco) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.saveOrUpdate(endereco);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return "";
	}

	/**
	 * Altera o endereco
	 * 
	 * @param endereco
	 * @return
	 */
	public String Alterar(Endereco endereco) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.update(endereco);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return "";
	}

	/**
	 * Traz todos os enderecos
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Endereco> buscargeral() {
		List<Endereco> busca = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = session.createQuery("from Endereco  order by nome");
			busca = this.query.list();
			this.transaction.commit();
			this.session.close();

		} catch (HibernateException e) {
		}
		return busca;
	}

	/**
	 * Traz um unico endereco
	 * 
	 * @param codigo
	 * @return
	 */
	public Endereco trazendereco(Integer codigo) {
		Endereco endereco = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session
					.createQuery("from Endereco where idendereco=:codigo");
			this.query.setInteger("codigo", codigo);
			this.transaction.commit();
			endereco = (Endereco) this.query.uniqueResult();
			this.session.close();
		} catch (HibernateException e) {

		}
		return endereco;
	}

}
