package config;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.human.gateway.client.bean.Response;
import com.human.gateway.client.bean.SimpleMessage;
import com.human.gateway.client.exception.ClientHumanException;
import com.human.gateway.client.service.SimpleMessageService;

public class EnviaTeste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SimpleMessageService cliente = new SimpleMessageService("cavalcanteramos","cra.2014");
		SimpleMessage mensagem1 = new SimpleMessage();
		mensagem1.setTo("21982751433");
		mensagem1.setMessage("Prezado colaborador, realizamos contratações."+"\n"+"Favor acusar recebimento via SIGECOL - CRA - Msg Automática.");
		// mensagem1.setSchedule(new Date());
		System.out.print("Foi");

		try {
			List<Response> retornos = cliente.send(mensagem1);
			System.out.print(retornos);
			if (retornos.get(1).isError()==true){
			// TODO Auto-generated catch block
			}
			
		} catch (ClientHumanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
