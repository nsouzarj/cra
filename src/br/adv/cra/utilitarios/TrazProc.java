package br.adv.cra.utilitarios;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import br.adv.cra.entity.ProcessoCPPRO;

public class TrazProc {

	private String recebe;

	public List<ProcessoCPPRO> traz() {

		try {


			Client c = Client.create();
			WebResource wr = c.resource("http://localhost:85/api/LawsuitCase?$select=identifierNumber,"
					+ "courtId,actionTypeId,folder," + "participants&$expand="
					+ "participants&$filter=contains(identifierNumber,%270061571-62.2014.8.19.0038%27)");
			String rsul = wr.get(String.class);
			recebe = rsul.substring(2, 729).replaceAll("\\\\", "");

			
			JSONObject jsonObject = new JSONObject(recebe);
			String pasta = jsonObject.getString("folder");
			String processo = jsonObject.getString("identifierNumber");
			String nome = jsonObject.getString("customerName");
			String tipoacao = jsonObject.getString("actionTypeName");
			String juizado = jsonObject.getString("courtName");
			String outraparte = jsonObject.getString("otherPartyName");
			System.out.println("DADOS DO PROCESSO\n\n");
			System.out.println("PASTA DO PROCESSO: " + pasta.toUpperCase());
			System.out.println("PROCESSO: " + processo);
			System.out.println("NOME: " + nome.toUpperCase());
			System.out.println("OUTRA PARTE: " + outraparte.toUpperCase());
			System.out.println("ACAO: " + tipoacao.toUpperCase());
			System.out.println("JUIZADO: " + juizado.toUpperCase());

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ProcessoJSON[]placelist = gson.fromJson(json, ProcessoJSON[].class);

		return null; // gson.fromJson(json, new
						// TypeToken<List<ProcessoJSON>>(){}.getType());

	}

}
