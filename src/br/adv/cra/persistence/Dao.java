/**
 * Autor:Nelson Seixas de Souza
 * Permite conexao com banco de dados 
 * Independente
 */
package br.adv.cra.persistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import com.mysql.jdbc.Statement;
import com.sun.xml.internal.ws.util.UtilException;
/**
 * Classe de Dao de conexao com o banco de dados
 * 
 * @author Nelson Seixas de Souza
 * 
 */
public class Dao {
	private String logado;
	protected Connection con;
	protected PreparedStatement stmt;
	protected ResultSet rs;
	protected java.sql.Statement stat;
	public void open() throws Exception {
		try {
			String url = "";
			Class.forName("org.postgresql.Driver");
			// url = "jdbc:postgresql://192.168.0.141:5432/dbcja";
			url = "jdbc:postgresql://172.16.48.11:5432/dbsolic";
			//url = "jdbc:postgresql://192.168.0.32:5432/dbsolic";
			Properties props = new Properties();
			props.setProperty("user", "postgres");
			props.setProperty("password", "nso1810");//
			con = DriverManager.getConnection(url, props);
		} catch (ClassNotFoundException cnfe) {
			System.out.println("org.postgresql.Driver");
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
		Dao d = new Dao();
		try {
			d.open();
			System.out.println("Conectou");
		} catch (Exception e) {
			System.out.println("Não Conectou"+e.getMessage());
		}

	}
}
