package br.adv.cra.utilitarios;

import java.util.Date;
import java.util.Properties;

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

import br.adv.cra.entity.Solicitacao;

/**
 * Classe de envio de email do sistema de consulta de bordero Tem como
 * finalidade enviar emails para os clientes sobre o cadastrameto do bordero. E
 * outros afins
 */
public class EnviaEmail {

	public void Enviar(Solicitacao solicitacao, String origem, String destino,
		  String copia, String advresp, String nomedestino, String arquivo,
		  String texto,String assunto) throws Exception {
		  Properties props = System.getProperties();
		  props.put("mail.smtp.host", "172.16.48.7");
	      props.put("mail.smtp.auth", "true");
		  props.put("mail.smtp.port", "587");
		  props.put("mail.smtp.starttls.enable", "false");
		
		 // props.put("mail.smtp.host", "smtp.gmail.com");
		 // props.put("mail.smtp.auth", "true");
		 // props.put("mail.smtp.port", "587");
		 // props.put("mail.smtp.starttls.enable", "true");

		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				
				//return new PasswordAuthentication("solicitacao.cra@gmail.com", "nso196840");
				return new PasswordAuthentication("admin", "Coroa.rainha@951");
			}
		};

		Session session = Session.getInstance(props, auth);
		session.getTransport("smtp");

		MimeMessage message = new MimeMessage(session);
		message.setSentDate(new Date());
		message.setFrom(new InternetAddress(origem,
				"CRA - Cavalcante Ramos Advogados"));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(
				destino, nomedestino));
		/**
		 * Aqui verifica os emails alternativos para serem enviados
		 */
		if (advresp.length() > 3) {
			message.addRecipient(Message.RecipientType.CC, new InternetAddress(
					advresp, ""));
		}
		if (copia.length() > 3) {
			message.addRecipient(Message.RecipientType.CC, new InternetAddress(
					copia, ""));
		}

		if (origem.length() > 3) {

			message.addRecipient(Message.RecipientType.CC, new InternetAddress(
					origem, "CRA - Cavalcante Ramos Advogados"));
		}

		//message.setSubject("** AVISO DE SOLICITAÇÃO **");
		message.setSubject(assunto);
		MimeBodyPart mbp1 = new MimeBodyPart();
		// MimeBodyPart mbp2 = new MimeBodyPart();
		mbp1.setText(texto);
		// FileDataSource nomearq = new FileDataSource(arquivo);
		// mbp2.setDataHandler(new DataHandler(nomearq));
		// mbp2.setFileName(nomearq.getName());
		Multipart mp = new MimeMultipart();
		mp.addBodyPart(mbp1);
		// mp.addBodyPart(mbp2);
		message.setContent(mp);
		Transport.send(message);
	}

	public void EnviarNovamente(Solicitacao solicitacao, String origem,
			String destino, String copia, String advresp, String nomedestino,
			String arquivo, String texto,String assunto) throws Exception {
		Properties props = System.getProperties();
		props.put("mail.smtp.host", "172.16.48.7");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.starttls.enable", "false");

		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("admin", "Coroa.rainha@951");
			}
		};

		Session session = Session.getInstance(props, auth);
		MimeMessage message = new MimeMessage(session);
		message.setSentDate(new Date());
		message.setFrom(new InternetAddress(origem,
				"CRA - Cavalcante Ramos Advogados"));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(
				destino, nomedestino));
		
		/**
		 * Aqui verifica os emails alternativos para serem enviados
		 */
		
		if (advresp.length() > 3) {
			message.addRecipient(Message.RecipientType.CC, new InternetAddress(
					advresp, ""));
		}
		if (copia.length() > 3) {
			message.addRecipient(Message.RecipientType.CC, new InternetAddress(
					copia, ""));
		}

		message.addRecipient(Message.RecipientType.CC, new InternetAddress(
				origem, "CRA - Cavalcante Ramos Advogados"));
		message.setSubject(assunto);
		MimeBodyPart mbp1 = new MimeBodyPart();
		// MimeBodyPart mbp2 = new MimeBodyPart();
		mbp1.setText(texto);
		// FileDataSource nomearq = new FileDataSource(arquivo);
		// mbp2.setDataHandler(new DataHandler(nomearq));
		// mbp2.setFileName(nomearq.getName());
		Multipart mp = new MimeMultipart();
		mp.addBodyPart(mbp1);
		// mp.addBodyPart(mbp2);
		message.setContent(mp);
		Transport.send(message);
	}

	/*
	 * Envia de novo o email pelo smto cra
	 */
	public void EnviarDeNovo(Solicitacao solicitacao, String origem,
			String destino, String copia, String advresp, String nomedestino,
			String arquivo, String texto,String assunto) throws Exception {
		Properties props = System.getProperties();
		props.put("mail.smtp.host", "172.16.48.7");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.starttls.enable", "false");

		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("admin", "Coroa.rainha@951");
			}
		};

		Session session = Session.getInstance(props, auth);
		MimeMessage message = new MimeMessage(session);
		message.setSentDate(new Date());
		message.setFrom(new InternetAddress(origem,
				"CRA - Cavalcante Ramos Advogados"));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(
				destino, nomedestino));
		
		/**
		 * Aqui verifica os emails alternativos para serem enviados
		 */
		
		if (advresp.length() > 3) {
			message.addRecipient(Message.RecipientType.CC, new InternetAddress(
					advresp, ""));
		}
		if (copia.length() > 3) {
			message.addRecipient(Message.RecipientType.CC, new InternetAddress(
					copia, ""));
		}

		message.addRecipient(Message.RecipientType.CC, new InternetAddress(
				origem, "CRA - Cavalcante Ramos Advogados"));
		message.setSubject(assunto);
		MimeBodyPart mbp1 = new MimeBodyPart();
		// MimeBodyPart mbp2 = new MimeBodyPart();
		mbp1.setText(texto);
		// FileDataSource nomearq = new FileDataSource(arquivo);
		// mbp2.setDataHandler(new DataHandler(nomearq));
		// mbp2.setFileName(nomearq.getName());
		Multipart mp = new MimeMultipart();
		mp.addBodyPart(mbp1);
		// mp.addBodyPart(mbp2);
		message.setContent(mp);
		Transport.send(message);
	}

	
	/**
	 * Envia msg pra geral
	 * @param origem
	 * @param destino
	 * @param nomedestino
	 * @param arquivo
	 * @param texto
	 * @param assunto
	 * @throws Exception
	 */
	
	public void EnviarMsg(String origem, String destino, String nomedestino,
			String arquivo, String texto, String assunto) throws Exception {
		Properties props = System.getProperties();
		props.put("mail.smtp.host", "172.16.48.7");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.starttls.enable", "false");

		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("admin", "Coroa.rainha@951");
			}
		};

		Session session = Session.getInstance(props, auth);
		MimeMessage message = new MimeMessage(session);
		message.setSentDate(new Date());
		message.setFrom(new InternetAddress(origem, "CRA - Cavalcante Ramos Advogados"));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(destino, nomedestino));
		message.setSubject(assunto);
		MimeBodyPart mbp1 = new MimeBodyPart();
		//MimeBodyPart mbp2 = new MimeBodyPart();
		mbp1.setText(texto);
		//FileDataSource nomearq = new FileDataSource(arquivo);
		//mbp2.setDataHandler(new DataHandler(nomearq));
		//mbp2.setFileName(nomearq.getName());
		Multipart mp = new MimeMultipart();
		mp.addBodyPart(mbp1);
		// mp.addBodyPart(mbp2);
		message.setContent(mp);
		Transport.send(message);
	}
	/**
	 * Envia messagem pra banca
	 * @param origem
	 * @param destino
	 * @param nomedestino
	 * @param arquivo
	 * @param texto
	 * @param assunto
	 * @throws Exception
	 */
	public void EnviarMsgBanca(String origem, String destino,String emailgestor, String nomedestino,
			String arquivo, String texto, String assunto) throws Exception {
		Properties props = System.getProperties();
		props.put("mail.smtp.host", "172.16.48.7");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.starttls.enable", "false");

		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("admin", "Coroa.rainha@951");
			}
		};

		Session session = Session.getInstance(props, auth);
		MimeMessage message = new MimeMessage(session);
		message.setSentDate(new Date());
		//Verifica se tem o email do gestor se for maior que tres carateres adcina na copia
		if (emailgestor.length() > 3) {
			message.addRecipient(Message.RecipientType.CC, new InternetAddress(
					emailgestor, ""));
		}
		message.setFrom(new InternetAddress(origem, "CRA - Sisgecol"));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(destino, nomedestino));

		message.setSubject(assunto);
		MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText(texto);
		Multipart mp = new MimeMultipart();
		mp.addBodyPart(mbp1);
		message.setContent(mp);
		Transport.send(message);
	}
	
	
	
	
	

	/**
	 * Teste de envio do email
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		EnviaEmail ev = new EnviaEmail();
		Solicitacao solicitacao = new Solicitacao();
		try {
			ev.Enviar(solicitacao, "nelson.seixas@cra.adv.br",
					"nelsonrjbrazil@gmail.com", "",
					"", "Testee", "",
					"Isso e um teste..","** AVISO DE SOLICITAÇÃO **");
			System.out.println("Email enviado com sucesso!!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			try {
				ev.EnviarNovamente(solicitacao, "nelson.seixas@cra.adv.br",
						"nsouzarj@bol.com.br", "",
						"", "Testee", "",
						"Isso e um teste..","** AVISO DE SOLICITAÇÃO **");
				System.out.println("Email enviado com sucesso!!");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.out.print("Erro ao enviar o e-mail descrição do erro:"
						+ e.getMessage());
			}
			
			
		}
	}

}
