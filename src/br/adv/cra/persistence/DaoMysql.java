/**
 * Autor:Nelson Seixas de Souza
 * Permite conexao com banco de dados via DAo 
 * Independente
 */
package br.adv.cra.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import com.sun.xml.internal.ws.util.UtilException;

public class DaoMysql {

	private String logado;
	protected Connection con;
	protected PreparedStatement stmt;
	protected ResultSet rs;

	public void open() throws Exception {
		try {
			String url = "";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// dbcja - base de producao
			// dbteste - base de teste
			// demo - base de demostracao
			url = "jdbc:mysql://192.168.0.32:3306/cpjwcs";
			// url =
			// "jdbc:firebirdsql:localhost/3050:c:\\trab\\cpjarquivos\\cpjwcs.fdb";
			Properties props = new Properties();
			props.setProperty("user", "root");
			props.setProperty("password", "root");
			props.setProperty("characterEncoding", "UTF8");
			con = DriverManager.getConnection(url, props);
		} catch (ClassNotFoundException cnfe) {
			System.out.println("com.mysql.jdbc.Driver");
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
		DaoMysql d = new DaoMysql();
		try {
			d.open();
			System.out.println("Conectou");
		} catch (Exception e) {
			System.out.println("Não Conectou" + e);
		}
	}
}
