
package br.adv.cra.manager;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import br.adv.cra.entity.ArquivoColaborador;
import br.adv.cra.entity.Correspondente;
import br.adv.cra.entity.Usuario;
import br.adv.cra.persistence.CorrespondenteDao;
import br.adv.cra.persistence.DaoArqColaborador;
import br.adv.cra.persistence.UsuarioDao;
import br.adv.cra.utilitarios.EnviaEmail;
import br.adv.cra.utilitarios.LoginConverte;

//IMPORTS DO METODO TELAMSN-CRA

/**
 * Bean que gerencia o usuario como mudanca de senha alterações de dados etc
 * Tanto e para o usuario interno do sistema como o externo
 * 
 * @author nelson
 * 
 */
@SuppressWarnings("deprecation")
@ManagedBean(name = "usuarios")
@SessionScoped
public class ManagerUsuario implements Serializable {
	@Inject
	private ManagerComarca comarca;
	private static final long serialVersionUID = 1L;
	private static String MSG_ERRO_ACESSO = "ERRO AO ACESSAR O SISTEMA SENHA ERRADA OU LOGIN BLOQUEADO OU NÃO CADASTRADO NO SISGECOL.";
	private UsuarioDao usuarioDao;
	private CorrespondenteDao correspondenteDao;
	private DaoArqColaborador arqColaborador;
	private ArquivoColaborador ged;
	private ManagerCorrespondente correspondente;
	private Usuario usuario;
	private String senhacompara;
	private Date dataentrada;
	private LoginConverte loginConverte;
	private List<Usuario> usuarios;
	private List<Correspondente> correspondentes;
	public List<ArquivoColaborador> aequivoged;
	private Boolean ativo;
	private String login;
	private String senha;
	private String loginacesso;
	private String senhacomp;
	private String senhacripto;
	private FacesMessage msg;
	private Integer nivelusuario;
	private HttpSession session;
	private Integer idcorrespondente;
	private Integer idusuario;
	private Integer nivelacesso;
	private Boolean altera_user;
	private FacesContext context;
	// Variaveis de usuario
	private String nome;
	private String cnpjcpf;
	private Usuario logado;
	private boolean ja;
	private EnviaEmail enviaEmail;
	private Usuario usuariocastrado;
	private String nomeusuario;
	private String descricaoarquivo;
	private boolean alterar;

	public ManagerUsuario() {
		usuarioDao = new UsuarioDao();
		usuario = new Usuario();
		loginConverte = new LoginConverte();
		dataentrada = new Date();
		logado = new Usuario();
		correspondenteDao = new CorrespondenteDao();
		usuarios = null;
		senha = "";
		senhacripto = "";
		idcorrespondente = 0;
		usuariocastrado = new Usuario();
		loginacesso = "";
		idusuario = 0;
		altera_user = false;
		comarca = new ManagerComarca();
		context = FacesContext.getCurrentInstance();
		session = (HttpSession) context.getExternalContext().getSession(true);
		idusuario = (Integer) session.getAttribute("IdUsuario");
		ja = false;
		alterar = false;
		nomeusuario = "";
		correspondente = new ManagerCorrespondente();
		descricaoarquivo = "";
		arqColaborador = new DaoArqColaborador();
		ged = new ArquivoColaborador();

	}

	/***
	 * Inativa usuario
	 * 
	 * @return
	 */
	public String InativaUsuario(Integer cod,String par) {
		try {
			String msg="";
			Usuario usu = new Usuario();
			usu = usuarioDao.trazusuario(cod);
			if (par.equals("Ativar")) {
			    usu.setAtivo(true);
			    msg="Usuário Ativado.";
			    
			}
			if (par.equals("Inativar")) {
			    usu.setAtivo(false);
			    msg="Usuário Inativado.";
			    
			}
			

			usuarioDao.Alterar(usu);
			getUsuarios();
			context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, msg, ""));
			return "";
		} catch (Exception e) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
					"Erro ao inativa usuario." + "\n" + e.getMessage(), ""));
			return "";

		}

	}

	@SuppressWarnings("static-access")
	/**
	 * Aqui pode salvar um novo usuario como alterar
	 * 
	 * @return
	 */
	public String SalvarUSuario() {
		// testa se ja esta cadastrado
		Usuario tete = usuarioDao.trazusuariocdastrado(usuario.getLogin());
		if (altera_user == false) {
			if (tete != null) {
				context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Login já cadastrado na base mas pode estar inativado.", ""));
				return "";

			}
		}

		String senha2 = "";
		senha2 = usuario.getSenha();
		if (usuario.getTipo() != 0) {
			if (senha.equals(senha2)) {

				if (altera_user == false) {
					ativo = true;
					CorrespondenteDao co = new CorrespondenteDao();
					Correspondente correspondente = co.trazcorrespondente(idcorrespondente);
					usuario.setSenha(loginConverte.converteSenhaParaMD5(usuario.getLogin(), usuario.getSenha()));
					usuario.setDataentrada(dataentrada);
					usuario.setAtivo(ativo);
					if (correspondente != null) {
						usuario.setCorrespondente(correspondente);
					}
					usuarioDao.Salvar(usuario);
					usuario = new Usuario();
					context = FacesContext.getCurrentInstance();
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Usuário cadastrado ou alterado com sucesso.", ""));
				} else if (altera_user == true) {
					usuario.setSenha(loginConverte.converteSenhaParaMD5(usuario.getLogin(), usuario.getSenha()));
					usuarioDao.Alterar(usuario);
					altera_user = false;
					context = FacesContext.getCurrentInstance();
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Usuário cadastrado ou alterado com sucesso.", ""));
				}

			} else {
				context = FacesContext.getCurrentInstance();
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "A senhas não combinam por favor corriga.", ""));
			}

		} else {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecione um tipo de usuário antes de salvar.", ""));
		}
		altera_user = false; // recebe falso para nao alterar acidentalmente
		return "";
	}

	public Usuario getUsuariocastrado() {
		return usuariocastrado;
	}

	public void setUsuariocastrado(Usuario usuariocastrado) {
		this.usuariocastrado = usuariocastrado;
	}

	public String getNomeusuario() {
		return nomeusuario;
	}

	public void setNomeusuario(String nomeusuario) {
		this.nomeusuario = nomeusuario;
	}

	public String getDescricaoarquivo() {
		return descricaoarquivo;
	}

	public void setDescricaoarquivo(String descricaoarquivo) {
		this.descricaoarquivo = descricaoarquivo;
	}

	public boolean isAlterar() {
		return alterar;
	}

	public void setAlterar(boolean alterar) {
		this.alterar = alterar;
	}

	@SuppressWarnings("static-access")
	/**
	 * Alterar senha
	 * 
	 * @return
	 */
	public String AlterarSenha() throws Exception {

		String senha2 = "";
		senha2 = logado.getSenha();

		if (senha.equals(senha2)) {
			logado.setSenha(loginConverte.converteSenhaParaMD5(logado.getLogin(), logado.getSenha()));
			String texto = "Sua senha foi alterada com sucesso sua nova senha é: " + senha2;
			usuarioDao.Alterar(logado);
			context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Senha alterada com sucesso com sucesso.", ""));
			enviaEmail = new EnviaEmail();

			if ((logado.getTipo().equals(1)) || (logado.getTipo().equals(2)) || (logado.getTipo().equals(4))) {
				enviaEmail.EnviarMsg("cra@cra.adv.br", logado.getEmailprincipal(), "Administrador", "", texto,
						"**ALTERAÇÂO DE SENHA **");
			} else if (logado.getTipo().equals(3)) {
				enviaEmail.EnviarMsg("cra@cra.adv.br", logado.getCorrespondente().getEmailprimario(), "Administrador",
						"", texto, "**ALTERAÇÂO DE SENHA **");
			}

		} else {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Senha não são iguais.", ""));
		}

		return "";
	}

	public String trazlogado() {
		try {
			context = FacesContext.getCurrentInstance();
			session = (HttpSession) context.getExternalContext().getSession(true);
			idusuario = (Integer) session.getAttribute("IdUsuario");
			logado = usuarioDao.trazusuario(idusuario);
			return "";

		} catch (Exception e) {
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao trazer usuaário." + "\n " + e.getMessage(), ""));
			return "";
		}

	}

	public Usuario getLogado() {
		if (ja == false) {
			trazlogado();
		}
		ja = true;
		return logado;
	}

	public void setLogado(Usuario logado) {
		this.logado = logado;
	}

	/**
	 * Exclui o usuario
	 * 
	 * @return
	 */
	public String ExcluirUsuario() {

		usuarioDao.excluirusuario(idusuario);
		return "";
	}

	public ManagerComarca getComarca() {
		return comarca;
	}

	public void setComarca(ManagerComarca comarca) {
		this.comarca = comarca;
	}

	public void AlterarUsuario(Integer idsuario1) {
		try {
			altera_user = true;
			usuario = usuarioDao.trazusuario(idsuario1);
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Busca realizada com sucesso.", ""));
		} catch (Exception e) {

			context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro ao acessa." + "\n" + e.getMessage(), ""));

		}

	}

	public Integer getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(Integer idusuario) {
		this.idusuario = idusuario;
	}

	public Date getDataentrada() {
		return dataentrada;
	}

	public void setDataentrada(Date dataentrada) {
		this.dataentrada = dataentrada;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public LoginConverte getLoginConverte() {
		return loginConverte;
	}

	public Boolean getAltera_user() {
		return altera_user;
	}

	public void setAltera_user(Boolean altera_user) {
		this.altera_user = altera_user;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpjcpf() {
		return cnpjcpf;
	}

	public void setCnpjcpf(String cnpjcpf) {
		this.cnpjcpf = cnpjcpf;
	}

	public Integer getNivelacesso() {
		return nivelacesso;
	}

	public void setNivelacesso(Integer nivelacesso) {
		this.nivelacesso = nivelacesso;
	}

	public String getLoginacesso() {
		return loginacesso;
	}

	public void setLoginacesso(String loginacesso) {
		this.loginacesso = loginacesso;
	}

	public void setLoginConverte(LoginConverte loginConverte) {
		this.loginConverte = loginConverte;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public String getSenhacripto() {
		return senhacripto;
	}

	public void setSenhacripto(String senhacripto) {
		this.senhacripto = senhacripto;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public UsuarioDao getUsuarioDao() {
		return usuarioDao;
	}

	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		usuarios = null;
		usuarios = usuarioDao.listagemtodos();
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public String getSenhacompara() {
		return senhacompara;
	}

	public void setSenhacompara(String senhacompara) {
		this.senhacompara = senhacompara;
	}

	public String getSenhacomp() {
		return senhacomp;
	}

	public void setSenhacomp(String senhacomp) {
		this.senhacomp = senhacomp;
	}

	public CorrespondenteDao getCorrespondenteDao() {
		return correspondenteDao;
	}

	public void setCorrespondenteDao(CorrespondenteDao correspondenteDao) {
		this.correspondenteDao = correspondenteDao;
	}

	public List<Correspondente> getCorrespondentes() {
		correspondentes = correspondenteDao.buscargeral();
		return correspondentes;
	}

	public void setCorrespondentes(List<Correspondente> correspondentes) {
		this.correspondentes = correspondentes;
	}

	/**
	 * Verifica se o usuario esta cadastrado
	 * 
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String PegaUsuario() {
		String teste;

		try {
			usuariocastrado = usuarioDao.trazusuario(loginacesso.toLowerCase());
			senhacomp = usuariocastrado.getSenha();
			senhacripto = loginConverte.converteSenhaParaMD5(loginacesso, senha);
			nivelusuario = usuariocastrado.getTipo();
			if ((senhacomp.equals(senhacripto)) && (usuariocastrado.isAtivo() == true)) {
				context = FacesContext.getCurrentInstance();
				session = (HttpSession) context.getExternalContext().getSession(true);
				session.setAttribute("Usuario", usuariocastrado.getNomecompleto());
				session.setAttribute("IdUsuario", usuariocastrado.getIdusuario());
				if (usuariocastrado.getTipo() != 3) {
					teste = "menu.jsf";
				} else {
					idcorrespondente = usuariocastrado.getCorrespondente().getIdcorrespondente();

					nome = usuariocastrado.getCorrespondente().getNome();

					cnpjcpf = usuariocastrado.getCorrespondente().getCpfcnpj();
					teste = "solicitacao/soliusuario.jsf";
				}

			} else {
				context = FacesContext.getCurrentInstance();
				msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSG_ERRO_ACESSO, "");
				System.out.println(MSG_ERRO_ACESSO);
				context.addMessage("Error", msg);
				teste = "login.jsf";
			}
		} catch (Exception e) {
			context = FacesContext.getCurrentInstance();
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSG_ERRO_ACESSO + "\n" + e.getMessage(), "");
			context.addMessage("Error", msg);
			teste = "login.jsf";
		}

		return teste;

	}

	/**
	 * Função feita para usar com o mobile para usuarios externos
	 * 
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String PegaUsuarioCel() {
		String teste;
		Usuario usuariocastrado = new Usuario();
		usuariocastrado = usuarioDao.trazusuario(loginacesso.toLowerCase());
		try {
			senhacomp = usuariocastrado.getSenha();
			senhacripto = loginConverte.converteSenhaParaMD5(loginacesso, senha);
			nivelusuario = usuariocastrado.getTipo();
			if ((senhacomp.equals(senhacripto)) && (usuariocastrado.isAtivo() == true)) {
				context = FacesContext.getCurrentInstance();
				session = (HttpSession) context.getExternalContext().getSession(true);
				session.setAttribute("Usuario", usuariocastrado.getNomecompleto());
				session.setAttribute("IdUsuario", usuariocastrado.getIdusuario());
				if (usuariocastrado.getTipo() != 3) {
					teste = "menu.jsf";
				} else {
					idcorrespondente = usuariocastrado.getCorrespondente().getIdcorrespondente();
					nome = usuariocastrado.getCorrespondente().getNome();
					cnpjcpf = usuariocastrado.getCorrespondente().getCpfcnpj();
					teste = "/mobile/soliusuariocel.jsf";
				}

			} else {
				context = FacesContext.getCurrentInstance();

				msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSG_ERRO_ACESSO, "");
				System.out.println(MSG_ERRO_ACESSO);

				context.addMessage("Error", msg);

				teste = "logincel.jsf";
			}
		} catch (Exception e) {
			context = FacesContext.getCurrentInstance();
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSG_ERRO_ACESSO, "");
			context.addMessage("Error", msg);
			teste = "logincel.jsf";
		}

		return teste;

	}

	/**
	 * Sai do sistema
	 * 
	 * @throws IOException
	 */
	public void SairSistema() {
		try {
			usuarioDao.Fechar();
			String teste = "";
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getSessionMap().remove("comarcas");
			context.getExternalContext().getSessionMap().remove("correspondente");
			context.getExternalContext().getSessionMap().remove("solicitacao");
			context.getExternalContext().getSessionMap().remove("orgaos");
			context.getExternalContext().getSessionMap().remove("processo");
			context.getExternalContext().getSessionMap().remove("usuario");
			context.getExternalContext().getSessionMap().remove("javax.faces.request.charset");
			context.getExternalContext().getSessionMap()
					.remove("com.sun.faces.renderkit.ServerSideStateHelper.LogicalViewMap");
			context.getExternalContext().getSessionMap().remove("facelets.ui.DebugOutput");
			context.getExternalContext().getSessionMap().remove("usuarios");
			context.getExternalContext().getSessionMap().remove("idUsuario");
			HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
			session.invalidate();
			session.getServletContext().getContextPath();
			teste = session.getServletContext().getContextPath();
			context.getExternalContext().redirect(teste + "/login.jsf");

		} catch (Exception e) {
			context = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
			session.invalidate();
			String teste1 = "";
			teste1 = session.getServletContext().getContextPath();
			try {
				context.getExternalContext().redirect(teste1 + "/saida.html");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				// e1.printStackTrace();
				System.out.printf("Saiu do Sistema !!!");
			}

		}

	}

	/**
	 * Sai do sistena via ceuluar
	 */

	public void SairSistemaCel() {
		try {
			usuarioDao.Fechar();
			String teste = "";
			context.getExternalContext().getSessionMap().remove("comarcas");
			context.getExternalContext().getSessionMap().remove("correspondente");
			context.getExternalContext().getSessionMap().remove("solicitacao");
			context.getExternalContext().getSessionMap().remove("orgaos");
			context.getExternalContext().getSessionMap().remove("processo");
			context.getExternalContext().getSessionMap().remove("usuario");
			context.getExternalContext().getSessionMap().remove("usuarios");
			context.getExternalContext().getSessionMap().remove("com.sun.faces.renderkit.ServerSideStateHelper.LogicalViewMap");
			context.getExternalContext().getSessionMap().remove("javax.faces.request.charset");
			context.getExternalContext().getSessionMap().remove("facelets.ui.DebugOutput");
			context.getExternalContext().getSessionMap().remove("usuarios");
			context.getExternalContext().getSessionMap().remove("idUsuario");
			context = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
			session.invalidate();
			session.getServletContext().getContextPath();
			teste = session.getServletContext().getContextPath();
			context.getExternalContext().redirect(teste + "/logincel.jsf");

		} catch (Exception e) {
			context = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
			session.invalidate();
			String teste1 = "";
			teste1 = session.getServletContext().getContextPath();
			try {
				context.getExternalContext().redirect(teste1 + "/saida.html");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				// e1.printStackTrace();
				System.out.printf("Saiu do Sistema !!!");
			}

		}

	}

	public Integer getNivelusuario() {
		return nivelusuario;
	}

	public void setNivelusuario(Integer nivelusuario) {
		this.nivelusuario = nivelusuario;
	}

	public Integer getIdcorrespondente() {
		return idcorrespondente;
	}

	public void setIdcorrespondente(Integer idcorrespondente) {
		this.idcorrespondente = idcorrespondente;
	}

	public void TrazCorrespondentes() {
		getCorrespondentes();
	}

	/**
	 * Sai para o menu
	 * 
	 * @return
	 */
	public String sair() {
		try {

		} catch (Exception e) {
			// TODO: handle exception
		}
		return "/menu.jf";
	}

	/**
	 * Ativa a msg da tela
	 */
	/**
	 * public void growlativar() { FacesContext cont10 =
	 * FacesContext.getCurrentInstance(); cont10.addMessage( null, new FacesMessage(
	 * FacesMessage.SEVERITY_INFO, "É ACONSELHÁVEL O USO DO NAVEGADOR MOZZILA
	 * FIREFOX OU GOOGLE CHROME.", "")); }
	 **/
	

	

	
}





