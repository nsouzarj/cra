package br.adv.cra.utilitarios;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Datateste {

	@SuppressWarnings({ "unused", "deprecation" })
	public void DescontaAtrazoSolicitacao() {
		long diafinalsemana = 0;
		long diferencaHoras = 0;
		long diferenca = 0;
		Calendar dataInicial = Calendar.getInstance();
		Calendar finaldesemana = Calendar.getInstance();
		Calendar novadata = Calendar.getInstance();
		Float unicasolicitacao = (float) 300.0;

		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

		float recebenovovalor = 0; // Seta para zero

		Date data1Prazo = new Date(); // Data de prazo
		Date dataAtual = new Date(); // Data ataulizada
		Date data2Conclusao = new Date(); // Dadta de conclusao
		
		String datadosistema = formato.format(new Date());

		try {
			data1Prazo = (Date) formato.parse("14/03/2019");

			data2Conclusao = (Date) formato.parse("17/03/2019");

			dataAtual = (Date) formato.parse("17/03/2019");
			

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// long diferenca = System.currentTimeMillis() - dataInicial.getTimeInMillis();

		// Verifica se cai em fim de semana
		// finaldesemana.setTimeInMillis(System.currentTimeMillis());
		finaldesemana.setTimeInMillis(data2Conclusao.getTime());
		diferenca = (data2Conclusao.getTime() - data1Prazo.getTime());
		
		if (finaldesemana.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
				
		    diferencaHoras = diferenca / (60 * 60 * 1000); // DIFERENCA EM HORAS
			diferencaHoras = (diferencaHoras + 48);
			novadata.add(Calendar.DATE,+2);
			novadata.add(Calendar.HOUR, +48);
			dataAtual=novadata.getTime();
		} else if (finaldesemana.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				
			diferencaHoras = diferenca / (60 * 60 * 1000); // DIFERENCA EM HORAS
			diferencaHoras = (diferencaHoras + 24);	
			novadata.add(Calendar.DATE,+2);
			novadata.add(Calendar.HOUR,+48);
			dataAtual=novadata.getTime();
	
		}else {
			
			diferenca = (data2Conclusao.getTime() - data1Prazo.getTime());
			diferencaHoras = diferenca / (60 * 60 * 1000); // DIFERENCA EM HORAS
			
		}
		
		diferencaHoras=(diferencaHoras-24);
		
	

		// Desconta no prazo de 65 horas 15%
		if (diferencaHoras > 65 && diferencaHoras < 88) {
			Float teste = (unicasolicitacao / 100);
			Float desconto = (teste * 15);
			recebenovovalor = (unicasolicitacao - desconto);
		}
		// Desconta no prazo de 88 horas 30%
		if (diferencaHoras > 88 && diferencaHoras < 109) {
			Float teste = (unicasolicitacao / 100);
			Float desconto = (teste * 30);
			recebenovovalor = (unicasolicitacao - desconto);
		}
		// Desconta no prazo de 109 horas 50%
		if (diferencaHoras > 109) {
			Float teste = (unicasolicitacao / 100);
			Float desconto = (teste * 50);
			recebenovovalor = (unicasolicitacao - desconto);
		}

		if (recebenovovalor == 0) {
			recebenovovalor = unicasolicitacao;
		}

		String dataFormatada1 = formato.format(data1Prazo);
		String dataFormatada2 = formato.format(data2Conclusao);
		String dataFormatada3 = formato.format(dataAtual);
		// IMprime o resultado
		System.out.println("Data do prazo: " + dataFormatada1);
		System.out.println("Data da conclusao: " + dataFormatada2);
		System.out.println("Diferença de Horas: " + diferencaHoras);
		System.out.println("Data atual computadada: " + dataFormatada3);
		System.out.println("Data atual do sistema: " +  datadosistema);
		System.out.println("Valor da Solicitacao: " + unicasolicitacao);
		System.out.println("Valor Descontado: " + recebenovovalor);

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Datateste teste = new Datateste();
		teste.DescontaAtrazoSolicitacao();
	}

}
