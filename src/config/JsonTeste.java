package config;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import br.adv.cra.utilitarios.TesteJson;
import jdk.nashorn.internal.parser.JSONParser;

public class JsonTeste {

	private String jsondados = "http://localhost:85/api/LawsuitCase?$select=identifierNumber,courtId,actionTypeId,folder,participants&$expand=participants,customFields&$filter=customFieldProcessoEletronico%20eq%20%27Sim%27";

	public void TesteJason() throws JSONException {

		Client c = Client.create();
		WebResource webResource = c.resource(jsondados);
		String rsul = webResource.get(String.class);

		Integer teste2 = rsul.length();
		// String louco = rsul.replaceAll("\\\\", "");
		String recebe = rsul.substring(1, teste2 - 1);

		JSONObject jsonObject = new JSONObject(recebe);
		JSONArray arr = new JSONArray(rsul);

		Integer uess = jsonObject.length();

		@SuppressWarnings("unchecked")
		Iterator<String> iteratorEmpresas = jsonObject.keys();
		Iterator<?> chave = jsonObject.keys();
		Integer contador = jsonObject.keys().hashCode();
		
		for (int i = 0; i < arr.length(); i++) {
			JSONObject rec = arr.getJSONObject(i);
			JSONArray camposdoido = rec. getJSONArray("CustomFields");
			Iterator<?> chave1 = rec.keys();
			String recebe2 = (String) chave1.next();
			System.out.println(recebe2.toString());

		
				System.out.println(rec.getString("CourtName"));
				System.out.println(rec.getString("Folder"));
				System.out.println(rec.getString("Type"));
				System.out.println(rec.getString("IdentifierNumber"));

				
				JSONObject recmaluco = camposdoido.getJSONObject(2);
				System.out.println(camposdoido.getString(2).toString());
				System.out.println(recmaluco.getString("LawsuitCaseCustomFieldValue").toString());

				JSONObject recmaluco1 = camposdoido.getJSONObject(22);
				System.out.println(recmaluco1.getString("CustomFieldDisplayName").toString());
				System.out.println(recmaluco1.getString("LawsuitCaseCustomFieldValue").toString());

		

		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JsonTeste teste = new JsonTeste();
		try {
			teste.TesteJason();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
