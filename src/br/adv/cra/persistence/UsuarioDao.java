package br.adv.cra.persistence;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.adv.cra.entity.Usuario;

public class UsuarioDao implements Serializable {
	private static final long serialVersionUID = 1L;
	private Session session;
	private Query query;
	private Transaction transaction;
	private Integer idusuario;
	private List<Usuario> busca = null;

	public UsuarioDao() {

	}

	public Integer getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(Integer idusuario) {
		this.idusuario = idusuario;
	}

	public List<Usuario> getBusca() {
		return busca;
	}

	public void setBusca(List<Usuario> busca) {
		this.busca = busca;
	}

	/**
	 * Salvar usuario
	 * 
	 * @param usuario
	 * @return
	 */
	public Usuario Salvar(Usuario usuario) {
		try {
			// Zero a listagem de usuarios para ser carregada novamente no final
			// "listagem()"
			busca = null;
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.saveOrUpdate(usuario);
			this.transaction.commit();
			this.session.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
		// Pega a listagem
		listagem();
		return usuario;
	}

	/**
	 * Alterar usuario
	 * 
	 * @param usuario
	 * @return
	 */
	public Usuario Alterar(Usuario usuario) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.update(usuario);
			this.transaction.commit();
			this.session.flush();
			this.session.close();

		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
			// TODO: handle exception
		}
		return usuario;
	}

	/**
	 * Listagem de usuario
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Usuario> listagem() {

		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = session.createQuery("from Usuario where ativo=true order by nomecompleto");
			busca = this.query.list();
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}

		return busca;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<Usuario> listagemtodos() {

		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = session.createQuery("from Usuario where nomecompleto <> '' order by nomecompleto");
			busca = this.query.list();
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}

		return busca;
	}

	/*
	 * Traz somente os usuarios internos
	 */
	@SuppressWarnings("unchecked")
	public List<Usuario> listagemusuario() {
		List<Usuario> busca1 = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = session
					.createQuery("from Usuario U  where tipo in (1,2,4)and ativo=true order by U.nomecompleto");
			busca1 = this.query.list();
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return busca1;
	}

	/**
	 * Traz usuario pelo login
	 * 
	 * @param login
	 * @return
	 */
	public Usuario trazusuario(String login) {
		Usuario usuario = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session.createQuery("from Usuario where login=:login and ativo=true");
			this.query.setString("login", login);
			usuario = (Usuario) this.query.uniqueResult();
			this.transaction.commit();
			this.session.close();
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
			// TODO: handle exception
		}
		return usuario;
	}
	
	/**
	 * Pega o login do correspondenete atraves do id
	 * @param idcorrespondente
	 * @return
	 */
	public Usuario trazusuarioCorrespondente(Integer idcorrespondente) {
		Usuario usuario = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session.createQuery("from Usuario where correspondente.idcorrespondente=:idcorrespondente");
			this.query.setInteger("idcorrespondente", idcorrespondente);
			usuario = (Usuario) this.query.uniqueResult();
			this.transaction.commit();
			this.session.close();
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
			// TODO: handle exception
		}
		return usuario;
	}
	
	
	/**
	 * Traz usuario ja cadastrado
	 */
	
	public Usuario trazusuariocdastrado(String login) {
		Usuario usuario = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session.createQuery("from Usuario where login=:login");
			this.query.setString("login", login);
			usuario = (Usuario) this.query.uniqueResult();
			this.transaction.commit();
			this.session.close();
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
			// TODO: handle exception
		}
		return usuario;
	}
	
	

	/**
	 * Traz usuario pelo id
	 * 
	 * @param idusuario
	 * @return
	 */
	public Usuario trazusuario(Integer idusuario) throws Exception {
		Usuario usuario = null;

		this.session = HibernateUtil.getSessionFactory().openSession();
		this.transaction = this.session.beginTransaction();
		this.query = this.session.createQuery("from Usuario where idusuario=:idusuario");
		this.query.setInteger("idusuario", idusuario);
		usuario = (Usuario) this.query.uniqueResult();
		this.transaction.commit();
		this.session.close();

		return usuario;
	}

	/**
	 * Funcao para excluir o usuario
	 * 
	 * @param idusuario
	 */
	public void excluirusuario(Integer idusuario) {
		Usuario usuario = null;
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session.createQuery("from Usuario where idusuario=:idusuario");
			this.query.setInteger("idusuario", idusuario);
			usuario = (Usuario) this.query.uniqueResult();
			this.session.delete(usuario);
			this.transaction.commit();
			this.session.close();
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
			// TODO: Excessao maluca
		}
	}

	/**
	 * Inativa usuario
	 * 
	 * @param idusuario
	 */
	public void inativausuario(Integer idusuario) {

	}

	public void Fechar() {
		try {
			if (this.session.isConnected()) {
				this.session.disconnect();
				System.out.println("* * Sistema desconectado * *");
			}

		} catch (Exception e) {
			System.out.println("* * Erro ao desconectar na sessão* *"+e.getMessage());
			// TODO: handle exception
		}
	}
	

	public static void main(String[] args) {
		Usuario usu = new Usuario();
		UsuarioDao u = new UsuarioDao();
		usu=u.trazusuarioCorrespondente(78);
		System.out.print(usu.getLogin());
		
	}

}
