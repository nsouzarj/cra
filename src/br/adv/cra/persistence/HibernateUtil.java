package br.adv.cra.persistence;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Classe usada para fazer conexao com hibernate criando factory de conexao
 * 
 * @author nelson
 * 
 */
@SuppressWarnings("deprecation")
public class HibernateUtil {
	private static final SessionFactory sessionFactory;
	private static final ServiceRegistry serviceRegistry;
	static {
		try {
			/**
			 * Nova configuracao modificada para trabalhar com hibernate 4 pra
			 * cima Nelson Seixas de Souza - B-Boy Nfly
			 */

			Configuration configuration = new Configuration();
			configuration.configure("config/hibernate.cfg.xml");
			serviceRegistry = new ServiceRegistryBuilder().applySettings(
					configuration.getProperties()).buildServiceRegistry(); // applySettings(configuration.getProperties()).buildServiceRegistry();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);

		} catch (Throwable ex) {
			
			FacesContext msg1 = FacesContext.getCurrentInstance();
			msg1.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Erro acessar o banco de dados do SISGECOL."+"\n"+ex.getMessage(), ""));

			System.out.println("A sessão inicial falhou." + ex.getMessage());
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
