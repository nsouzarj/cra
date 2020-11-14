package br.adv.cra.persistence;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.adv.cra.entity.ComarcaPossui;

public class PossuiComarcaDao implements Serializable {
	private static final long serialVersionUID = 1L;
	private Session session;
	private Query query;
	private Transaction transaction;

	/**
	 * 
	 * @param comarcaPossui
	 */
	public void Salvar(ComarcaPossui comarcaPossui) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.save(comarcaPossui);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	/**
	 * 
	 * @param comarcaPossui
	 */
	public void Alterar(ComarcaPossui comarcaPossui) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.update(comarcaPossui);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	/**
	 * 
	 * @param codigocorrespondente
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ComarcaPossui> buscargeral(Integer idcorrespoondente,
		String nome, String cpfcnpj, String oab, Integer comarca, Integer uf,boolean ativo) {
		List<ComarcaPossui> comarcaPossui = null;
		String teste = "from ComarcaPossui where comarcaCorrespondente.comarca.idcomarca=0";
		String complementa = "";

		if (idcorrespoondente != 0) {
			teste = "";
			teste = " from ComarcaPossui where  inativado=false";
			complementa = complementa
					+ " and comarcaCorrespondente.correspondente.ativo="+ativo+"  and  comarcaCorrespondente.correspondente.idcorrespondente="
					+ idcorrespoondente + "";
		}
		if (nome != "") {
			teste = "";
			teste = " from ComarcaPossui where  inativado=false";
			complementa = complementa
					+ " and comarcaCorrespondente.correspondente.ativo="+ativo+" and   comarcaCorrespondente.correspondente.nome like "
					+ "'" + nome + "%" + "'";
		}
		if (cpfcnpj != "") {
			teste = "";
			teste = " from ComarcaPossui where  inativado=false";
			complementa = complementa
					+ "  and comarcaCorrespondente.correspondente.ativo="+ativo+" and  comarcaCorrespondente.correspondente.cpfcnpj="
					+ "'" + cpfcnpj + "'";
		}
		if (oab != "") {
			teste = "";
			teste = " from ComarcaPossui where  inativado=false";
			complementa = complementa
					+ "  and comarcaCorrespondente.correspondente.ativo="+ativo+" and comarcaCorrespondente.correspondente.oab=" + "'"
					+ oab + "'";
		}
		if (comarca != 0) {
			teste = "";
			teste = " from ComarcaPossui where  inativado=false";
			complementa = complementa
					+ "  and comarcaCorrespondente.correspondente.ativo="+ativo+" and comarcaCorrespondente.comarca.idcomarca ="
					+ comarca + "";
		}
		if (uf != 0) {
			teste = "";
			teste = " from ComarcaPossui where  inativado=false";
			complementa = complementa
					+ " and comarcaCorrespondente.correspondente.ativo="+ativo+" and comarcaCorrespondente.comarca.uf =" + uf + "";
		}

		teste = teste + complementa + "  order by comarcaCorrespondente.correspondente.nome";

		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session.createQuery(teste);
			comarcaPossui = this.query.list();
			this.session.close();
		} catch (HibernateException e) {
			
		}
		return comarcaPossui;
		
	}

	@SuppressWarnings("unchecked")
	public List<ComarcaPossui> buscarcomarcar(Integer codigo) {
		List<ComarcaPossui> comarcaPossui1 = null;

		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session
					.createQuery("from ComarcaPossui where comarcaCorrespondente.correspondente.idcorrespondente=:codigo and comarcaCorrespondente.comarca.idcomarca <> 1 order by comarcaCorrespondente.comarca.nome");
			this.query.setInteger("codigo", codigo);
			comarcaPossui1 = this.query.list();
			this.session.close();
		} catch (HibernateException e) {
		}
		return comarcaPossui1;
	}

	/**
	 * Traz a comarca unica dos correspondenre
	 * 
	 * @param codigo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ComarcaPossui> buscarcomcorr(Integer idcomarca) {
		List<ComarcaPossui> comarcaPossui2 = null;

		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session
					.createQuery("from ComarcaPossui where  comarcaCorrespondente.comarca.idcomarca=:idcomarca and comarcaCorrespondente.correspondente.ativo=true  and inativado=false order by comarcaCorrespondente.correspondente.nome");
			this.query.setInteger("idcomarca", idcomarca);
			comarcaPossui2 = this.query.list();
			this.session.close();
		} catch (HibernateException e) {
		}
		if (idcomarca == 0) {
			comarcaPossui2 = null;
		}

		return comarcaPossui2;
	}
	
	/**
	 * Traz a comarca unica dos correspondenre inativado ou nao
	 * Feito em 04/02/2020
	 * @param codigo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ComarcaPossui> buscarcomcorrtodas(Integer idcomarca) {
		List<ComarcaPossui> comarcaPossui2 = null;

		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.query = this.session
					.createQuery("from ComarcaPossui where  comarcaCorrespondente.comarca.idcomarca=:idcomarca order by comarcaCorrespondente.correspondente.nome");
			this.query.setInteger("idcomarca", idcomarca);
			comarcaPossui2 = this.query.list();
			this.session.close();
		} catch (HibernateException e) {
		}
		if (idcomarca == 0) {
			comarcaPossui2 = null;
		}

		return comarcaPossui2;
	}
	
	

	public String Excluir(ComarcaPossui comarcaPossui) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.delete(comarcaPossui);
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			// TODO: handle exception
		}

		return "";

	}

	// Inativa a comarca do correspondente
	public String Inativar(Integer idcomarca, Integer idcorrespondente) {
		try {
			this.session = HibernateUtil.getSessionFactory().openSession();
			this.transaction = this.session.beginTransaction();
			this.session.createSQLQuery(
					"update comarcapossui set inativado=true where idcomarca="
							+ idcomarca + " and idcorrespondente="
							+ idcorrespondente + "").executeUpdate();
			this.transaction.commit();
			this.session.close();
		} catch (HibernateException e) {
			// TODO: handle exception
		}
		return "";
	}

	
	// Inativa a comarca do correspondente
		public String Ativar(Integer idcomarca, Integer idcorrespondente) {
			try {
				this.session = HibernateUtil.getSessionFactory().openSession();
				this.transaction = this.session.beginTransaction();
				this.session.createSQLQuery(
						"update comarcapossui set inativado=false where idcomarca="
								+ idcomarca + " and idcorrespondente="
								+ idcorrespondente + "").executeUpdate();
				this.transaction.commit();
				this.session.close();
			} catch (HibernateException e) {
				// TODO: handle exception
			}
			return "";
		}

	
	
	public static void main(String[] args) {
		PossuiComarcaDao dao;
		dao = new PossuiComarcaDao();
		List<ComarcaPossui> lista = null;
		lista = dao.buscarcomarcar(2);
		System.out.println(lista.toString());
	}
}
