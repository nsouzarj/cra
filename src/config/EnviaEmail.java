package config;

import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Classe de envio de email do sistema de consulta de bordero Tem como
 * finalidade enviar emails para os clientes sobre o cadastrameto do bordero. E
 * outros afins
 */
public class EnviaEmail {

	public void Enviar(String origem, String destino, String nomedestino,
			String arquivo) throws Exception {
		Properties props = System.getProperties();
		props.put("mail.smtp.host", "smtp.bol.com.br");
		props.put("mail.smtp.auth", "true");
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("dac@cja.com.br", "12345");
			}
		};

		Session session = Session.getInstance(props, auth);
		MimeMessage message = new MimeMessage(session);
		message.setSentDate(new Date());
		message.setFrom(new InternetAddress(origem, "CJA - Sistema Web"));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(
				destino, nomedestino));
		message.setSubject("** AVISO DE SOLICITAÇÃO/AUDIENCIA **");
		MimeBodyPart mbp1 = new MimeBodyPart();
		MimeBodyPart mbp2 = new MimeBodyPart();
		mbp1.setText("Listagem de bordero cadastrado no dia.");
		FileDataSource nomearq = new FileDataSource(arquivo);
		mbp2.setDataHandler(new DataHandler(nomearq));
		mbp2.setFileName(nomearq.getName());
		Multipart mp = new MimeMultipart();
		mp.addBodyPart(mbp1);
		mp.addBodyPart(mbp2);
		message.setContent(mp);
		Transport.send(message);
	}

	/**
	 * Teste de envio do email
	 * 
	 * @param args
	 */
	

}
