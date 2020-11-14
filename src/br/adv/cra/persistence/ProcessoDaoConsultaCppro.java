package br.adv.cra.persistence;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

import br.adv.cra.entity.ProcessoCpproConsulta;
import jdk.nashorn.internal.parser.JSONParser;

public class ProcessoDaoConsultaCppro extends DaoSqlServer {

	private String sqlconsulta;
	private ProcessoCpproConsulta processoCpproConsulta;
	private String recebe;
	public List<ProcessoCpproConsulta> busca = null;
	public List<ProcessoCpproConsulta> copia = new ArrayList<ProcessoCpproConsulta>();

	public List<ProcessoCpproConsulta> consulta(String numeroprocesso, String cliente, String parte, String acao,
			String codigo, String pasta) {
		List<ProcessoCpproConsulta> busca = new ArrayList<ProcessoCpproConsulta>();

		if (numeroprocesso != "") {
			sqlconsulta = "";
			sqlconsulta = "select  Numero_Inicial,Cliente_Principal,Orgao_Inicial,Contrario_Principal,Tipoacao_Rito,CodigoPasta,Processo_Eletronico,Pasta_Cliente "
					+ " from  CPPro.dbo.View_Processos_Informacoes where Numero_Inicial=" + "'" + numeroprocesso + "'";
		}
		if (cliente != "") {
			sqlconsulta = "";
			sqlconsulta = "select  Numero_Inicial,Cliente_Principal,Orgao_Inicial,Contrario_Principal,Tipoacao_Rito,CodigoPasta,Processo_Eletronico,Pasta_Cliente "
					+ "from CPPro.dbo.View_Processos_Informacoes where UPPER(Cliente_Principal)  like " + "'%"
					+ cliente.toUpperCase() + "%'";
		}

		if (parte != "") {
			sqlconsulta = "";
			sqlconsulta = "select  Numero_Inicial,Cliente_Principal,Orgao_Inicial,Contrario_Principal,Tipoacao_Rito,CodigoPasta,Processo_Eletronico,Pasta_Cliente "
					+ "from CPPro.dbo.View_Processos_Informacoes where UPPER(Contrario_Principal) like " + "'%"
					+ parte.toUpperCase() + "%'";
		}

		if (acao != "") {
			sqlconsulta = "";
			sqlconsulta = "select from Numero_Inicial,Cliente_Principal,Orgao_Inicial,Contrario_Principal,Tipoacao_Rito,CodigoPasta,Processo_Eletronico,Pasta_Cliente "
					+ "from CPPro.dbo.View_Processos_Informacoes where UPPER(Tipoacao_Rito) Tipoacao_Rito like " + "'%"
					+ acao.toUpperCase() + "%'";
		}

		if (pasta != "") {
			sqlconsulta = "";
			sqlconsulta = "select  Numero_Inicial,Cliente_Principal,Orgao_Inicial,Contrario_Principal,Tipoacao_Rito,CodigoPasta,Processo_Eletronico,Pasta_Cliente "
					+ "from CPPro.dbo.View_Processos_Informacoes where Pasta_Cliente like " + "'%" + pasta + "%'";
		}

		if (codigo != "") {
			sqlconsulta = "";
			sqlconsulta = "select Numero_Inicial,Cliente_Principal,Orgao_Inicial,Contrario_Principal,Tipoacao_Rito,CodigoPasta,Processo_Eletronico,Pasta_Cliente "
					+ "from CPPro.dbo.View_Processos_Informacoes  where CodigoPasta =" + "'" + codigo + "'";
		}
        
		//
		
		try {
			open();
			stmt = con.prepareStatement(sqlconsulta);
			rs = stmt.executeQuery();
			while (rs.next()) {
				processoCpproConsulta = new ProcessoCpproConsulta();
				processoCpproConsulta.setNumerinical(rs.getString("Numero_Inicial"));
				processoCpproConsulta.setClienteprincipal(rs.getString("Cliente_Principal").toUpperCase());
				processoCpproConsulta.setOrgaoinicial(rs.getString("Orgao_Inicial").toUpperCase());
				processoCpproConsulta.setContrarioprincipal(rs.getString("Contrario_Principal").toUpperCase());
				processoCpproConsulta.setTipoacaorito(rs.getString("Tipoacao_Rito").toUpperCase());
				processoCpproConsulta.setCodigopasta(rs.getString("CodigoPasta"));
				processoCpproConsulta.setProcessoeletronico(rs.getNString("Processo_Eletronico").toUpperCase());
				processoCpproConsulta.setPastacliente(rs.getString("Pasta_Cliente").toUpperCase());
				busca.add(processoCpproConsulta);
			}
			close();
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.print("Erro de formato de campo.");
		}
		return busca;
	}

	public ProcessoCpproConsulta BuscaUnico(String numeroprocesso) {
		ProcessoCpproConsulta processoCpproConsulta = new ProcessoCpproConsulta();
		String sqlconsulta1 = "select  Numero_Inicial,Cliente_Principal,Orgao_Inicial,Contrario_Principal,Tipoacao_Rito,CodigoPasta,Processo_Eletronico,Pasta_Cliente "
				+ " from CPPro.dbo.View_Processos_Informacoes where upper(CodigoPasta)=" + "'" + numeroprocesso.toUpperCase() + "'";
		try {
			open();
			stmt = con.prepareStatement(sqlconsulta1);
			rs = stmt.executeQuery();
			while (rs.next()) {
				processoCpproConsulta = new ProcessoCpproConsulta();
				processoCpproConsulta.setNumerinical(rs.getString("Numero_Inicial"));
				processoCpproConsulta.setClienteprincipal(rs.getString("Cliente_Principal").toUpperCase());
				processoCpproConsulta.setOrgaoinicial(rs.getString("Orgao_Inicial").toUpperCase());
				processoCpproConsulta.setContrarioprincipal(rs.getString("Contrario_Principal").toUpperCase());
				processoCpproConsulta.setTipoacaorito(rs.getString("Tipoacao_Rito").toUpperCase());
				processoCpproConsulta.setCodigopasta(rs.getString("CodigoPasta"));
				processoCpproConsulta.setProcessoeletronico(rs.getString("Processo_Eletronico").toUpperCase());
				processoCpproConsulta.setPastacliente(rs.getString("Pasta_Cliente").toUpperCase());
			}
			close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return processoCpproConsulta;
	}

	/**
	 * Pega os processo do Legal One
	 * Atraves de uma API feita pela Thomson Reuters
	 * Finalizados os teste ja em produção
	 * A API foi fornecida pela Legal One
	 * Exise uma documentação tecnica impresa sobre o uso da mesma. 
	 * @throws IOException
	 * 
	 */
	public List<ProcessoCpproConsulta> PegaProcessoLegalOne(String numeroprocesso, String cliente, String parte,
			String acao, String codigo, String pasta) throws IOException {
		busca = new ArrayList<ProcessoCpproConsulta>();
		if (pasta.length() == 0) {
			/// numeroprocesso.length() > 0 || cliente.length() > 0 ||
			/// parte.length() > 0 || acao.length() > 0 || pasta.length() > 0 &&

			try {
				String rsul = "";
				String recebe = "";
				String recebeurl = "";

				processoCpproConsulta = null;
				processoCpproConsulta = new ProcessoCpproConsulta();
				if (numeroprocesso != "") {
					numeroprocesso = numeroprocesso.replace("\"", "'");
					recebeurl = "http://localhost:85/api/LawsuitCase?$select=identifierNumber,courtId,actionTypeId,folder,participants&$expand=participants,customFields&$filter=contains(identifierNumber,"
							+ "'" + numeroprocesso + "'" + ")";
				}

				if (cliente != "") {
					cliente = cliente.replaceAll(" ", "%20");

					recebeurl = "http://localhost:85/api/LawsuitCase?$select=identifierNumber,courtId,actionTypeId,folder,participants&$expand=participants,customFields&$filter=contains(customerName,"
							+ "''" + cliente + "''" + ")";
				}
				// http://localhost:85/api/LawsuitCase?$select=identifierNumber,courtId,actionTypeId,folder,participants&$expand=participants,customFields&$filter=contains(customerName,'GARANTIA
				// REAL SEGURANÇA')

				if (parte != "") {
					parte = parte.replace(" ", "%20");
					recebeurl = "http://localhost:85/api/LawsuitCase?$select=identifierNumber,courtId,actionTypeId,folder,participants&$expand=participants,customFields&$filter=contains(otherPartyName,"
							+ "'" + parte + "'" + ")";
				}
				if (acao != "") {
					acao = acao.replace(" ", "%20");
					recebeurl = "http://localhost:85/api/LawsuitCase?$select=identifierNumber,courtId,actionTypeId,folder,participants&$expand=participants,customFields&$filter=contains(identifierNumber,"
							+ "'" + acao + "'" + ")";
				}
				if (pasta != "") {
					pasta = pasta.replace("\"", "'");
					recebeurl = "http://localhost:85/api/LawsuitCase?$select=identifierNumber,courtId,actionTypeId,folder,participants&$expand=participants,customFields&$filter=contains(folder,"
							+ "'" + pasta + "'" + ")";
				}

				Client c = null;
				c = Client.create();

				WebResource webResource = null;

				webResource = c.resource(recebeurl);

				/**
				 * 
				 * @SuppressWarnings("unused") URL url=null;
				 * 
				 * HttpURLConnection con = null; try { url = new URL(recebeurl);
				 * con = (HttpURLConnection)url.openConnection();
				 * con.setRequestMethod("GET"); con.connect();
				 * 
				 * } catch (MalformedURLException e) { // TODO Auto-generated
				 * catch block e.printStackTrace(); }
				 * 
				 */

				rsul = webResource.get(String.class);

				Integer teste2 = rsul.length();
				// String louco = rsul.replaceAll("\\\\", "");
				recebe = rsul.substring(1, teste2 - 1);
				JSONArray jsonarray = new JSONArray(rsul);
				JSONObject jsonObject = new JSONObject(recebe);
				// Integer uess = jsonObject.length();
				@SuppressWarnings("unchecked")
				// Iterator<String> iteratorEmpresas = jsonObject.keys();
				// Iterator<?> chave = jsonObject.keys();
				// Integer contador = jsonObject.keys().hashCode();
				Map<String, Object> teste = defaultsToMap(jsonObject);
				System.out.print(teste.toString());
				// JSONArray servico = jsonObject.getJSONArray("CustomFields");
				// Busca no laço for os objetos contidos nos arrays do JSON
				for (int i = 0; i < jsonarray.length(); i++) {
					JSONObject rec = jsonarray.getJSONObject(i);
					Iterator<?> chave1 = rec.keys();
					String recebe2 = (String) chave1.next();
					//Uso somente para mostrar a saida JSON
					//System.out.println(recebe2.toString());

					if (recebe2.equals("CourtName")) {
						processoCpproConsulta = null;
						processoCpproConsulta = new ProcessoCpproConsulta();
						processoCpproConsulta.setNumerinical(rec.getString("IdentifierNumber"));
						processoCpproConsulta.setClienteprincipal(rec.getString("CustomerName").toUpperCase());
						processoCpproConsulta.setCodigopasta(rec.getString("Folder").toUpperCase());
						processoCpproConsulta.setTipoacaorito(rec.getString("ActionTypeName").toUpperCase());
						processoCpproConsulta.setOrgaoinicial(rec.getString("CourtName").toUpperCase());
						processoCpproConsulta.setContrarioprincipal(rec.getString("OtherPartyName").toUpperCase());
						processoCpproConsulta.setResponsavel(rec.getString("PersonInChargeName").toUpperCase());
						JSONArray camposdoido = rec.getJSONArray("CustomFields");
						for (int a = 0; a < camposdoido.length(); a++) {
                            //Cria outro Objeto JSON para pegar na verredura do ARRAY
							JSONObject campocust = camposdoido.getJSONObject(a);
							    //Verifica a carteira 
								if (campocust.get("CustomFieldDisplayName").equals("Pasta Cliente")){
									processoCpproConsulta.setPastacliente(
											campocust.getString("LawsuitCaseCustomFieldValue").toUpperCase());
								}
                                //Verifica se o processo e eletrônico
								if (campocust.get("CustomFieldDisplayName").equals("Processo Eletrônico")) {
									processoCpproConsulta.setProcessoeletronico(campocust.getString("LawsuitCaseCustomFieldValue").toString());
								}
								
							/**
							 * if(a==22){
							 * //System.out.println(recmaluco.getString("LawsuitCaseCustomFieldValue").toString());
							 * processoCpproConsulta.setPastacliente(recmaluco.getString("LawsuitCaseCustomFieldValue").toUpperCase());
							 * }else if (a == 2) {
							 * processoCpproConsulta.setProcessoeletronico(
							 * recmaluco.getString("LawsuitCaseCustomFieldValue").toString());
							 * //System.out.println(recmaluco.getString("CustomFieldDisplayName").toString());
							 * 
							 * }
							 **/
						}
						

					}

					busca.add(processoCpproConsulta);

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				System.out.print("Erro " + e);

			}

		}

		return busca;
	}

	@SuppressWarnings({ "unused", "unchecked" })
	private static Map<String, Object> defaultsToMap(JSONObject object) throws JSONException {
		final Map<String, Object> map = new HashMap<String, Object>();

		for (Iterator<String> keys = object.keys(); keys.hasNext();) {
			String key = keys.next();
			Object value = object.get(key);

			if (value instanceof Integer) {
				// setDefaults() takes Longs
				value = Long.valueOf((Integer) value);
			}

			map.put(key, value);
		}
		return map;
	}

	/***
	 * Busca unico da lista do Legal One
	 * 
	 * @param args
	 */

	public ProcessoCpproConsulta getProcessoCpproConsulta() {
		return processoCpproConsulta;
	}

	public void setProcessoCpproConsulta(ProcessoCpproConsulta processoCpproConsulta) {
		this.processoCpproConsulta = processoCpproConsulta;
	}

	public List<ProcessoCpproConsulta> getBusca() {
		return busca;
	}

	public void setBusca(List<ProcessoCpproConsulta> busca) {
		this.busca = busca;
	}

	public List<ProcessoCpproConsulta> getCopia() {
		return copia;
	}

	public void setCopia(List<ProcessoCpproConsulta> copia) {
		this.copia = copia;
	}

	public static void main(String[] args) {
		ProcessoDaoConsultaCppro processoCpproConsulta = new ProcessoDaoConsultaCppro();
		System.out.print(processoCpproConsulta.consulta("", "", "Alan", "", "", ""));
	}

}
