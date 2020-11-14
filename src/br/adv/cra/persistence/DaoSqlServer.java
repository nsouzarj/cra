/**
 * Autor:Nelson Seixas de Souza
 * Permite conexao com banco de dados via DAo no sql server
 * Independente
 */
package br.adv.cra.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import com.sun.xml.internal.ws.util.UtilException;

public class DaoSqlServer {

	private String logado;
	protected Connection con;
	protected PreparedStatement stmt;
	protected ResultSet rs;

	public void open() throws Exception {
		try {
			String url = "";
			//String query = "select name , id from sysobjects";
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			url="jdbc:sqlserver://crrj01vs003:58037";
			Properties props = new Properties();
			props.setProperty("databaseName", "CPPro");
			props.setProperty("user", "sa");
			props.setProperty("instanceName","PLK12");
			props.setProperty("password", "plkadmin2002@");
			//props.setProperty("characterEncoding", "UTF-8");
			con = DriverManager.getConnection(url,props);
			
		} catch (ClassNotFoundException cnfe) {
			System.out.println("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			return;
		}
	}

	public Connection getConexao() throws UtilException {
		return con;
	}

	public void close() throws Exception {
		try {
			con.close();
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	public String getLogado() {
		return logado;
	}

	public void setLogado(String logado) {
		this.logado = logado;
	}

	/**
	 * Testa a conexão do banco de dados
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		DaoSqlServer d = new DaoSqlServer();
		try {
			
			d.open();
			System.out.println("Conectou");
		
		} catch (Exception e) {
			System.out.println("Não Conectou " + e);
		}
	}
}
