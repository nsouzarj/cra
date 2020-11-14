package br.adv.cra.utilitarios;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Conversor de senha
 * 
 * @author nelson
 * 
 */
public class LoginConverte implements Serializable {
	private static final long serialVersionUID = 1L;

	public static String converteSenhaParaMD5(String usuario, String senha) {
		String sessaoid = senha;
		byte[] defaultBytes = sessaoid.getBytes();
		try {
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(defaultBytes);
			byte messageDigest[] = algorithm.digest();
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
			}
			System.out.println("Login : " + usuario + " Senha  " + sessaoid
					+ " md5 versão é " + hexString.toString());
			sessaoid = hexString.toString();
		} catch (NoSuchAlgorithmException nsae) {
			System.out.println(">>>>>>> MD5 digest nao está disponivel nesta maquina, verifique a JVM se esta instalada");
			nsae.printStackTrace();
			// TODO: handle exception
		}
		return sessaoid;
	}

	public static void main(String[] args) {
		converteSenhaParaMD5("nelson", "teste");
	}

}
