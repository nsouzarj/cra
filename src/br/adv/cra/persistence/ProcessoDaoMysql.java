package br.adv.cra.persistence;

import java.util.ArrayList;
import java.util.List;
import br.adv.cra.entity.ProcessoCPJ;
import br.adv.cra.utilitarios.Converte;

public class ProcessoDaoMysql extends DaoMysql {

	private String sqlpesquisa;
	private ProcessoCPJ processoCPJ;

	/**
	 * Busca a lista de processos
	 * 
	 * @param nome
	 * @param reu
	 * @param processo
	 * @return
	 */
	public List<ProcessoCPJ> Busca(String nome, String reu, String processo,
			String numerointegracao) {
		List<ProcessoCPJ> busca = new ArrayList<ProcessoCPJ>();
		String sqlcompleta = "";
		setSqlpesquisa("select distinct p.numero_processo,p.ficha,p.acao, p.loc_posicao, p.numero_processo_pesq,p.juizo,p.oj_numero,p.oj_sigla, pe.nome as autor, re.nome as reu,p.assunto,p.loc_sigla,p.loc_numero, p.sigla_integracao,p.numero_integracao"
				+ " from cad_processo p, cad_pessoa pe,cad_pessoa re"
				+ " where  p.primeiro_autor=pe.codigo"
				+ " and   p.numero_processo='0' "
				+ sqlcompleta
				+ " and p.primeiro_reu=re.codigo	order by pe.nome ");

		if (nome != "") {
			sqlcompleta = " and pe.nome like " + "'%" + nome + "%'";
			setSqlpesquisa("select distinct p.numero_processo, p.ficha, p.acao, p.loc_posicao, p.numero_processo_pesq,p.juizo,p.oj_numero,p.oj_sigla,upper( pe.nome) as autor, upper(re.nome) as reu,p.assunto,p.loc_sigla,p.loc_numero, p.sigla_integracao,p.numero_integracao"
					+ " from cad_processo p, cad_pessoa pe,cad_pessoa re"
					+ " where  p.primeiro_autor=pe.codigo  "
					+ sqlcompleta
					+ " and p.primeiro_reu=re.codigo	order by pe.nome ");

		}

		if (reu != "") {
			sqlcompleta = " and re.nome like " + "'" + reu + "%'";
			setSqlpesquisa("select distinct p.numero_processo, p.ficha,p.acao,p.loc_posicao,p.numero_processo_pesq,p.juizo,p.oj_numero,p.oj_sigla, upper(pe.nome) as autor, upper(re.nome) as reu,p.assunto,p.loc_sigla,p.loc_numero, p.sigla_integracao,p.numero_integracao"
					+ " from cad_processo p, cad_pessoa pe,cad_pessoa re"
					+ " where  p.primeiro_autor=pe.codigo "
					+ sqlcompleta
					+ " and p.primeiro_reu=re.codigo	order by pe.nome ");
		}

		if (processo != "") {
			Converte con = new Converte();

			sqlcompleta = " and p.numero_processo_pesq like " + "'%"
					+ con.traz(processo) + "%'";

			setSqlpesquisa("select distinct p.numero_processo, p.ficha,p.acao,p.loc_posicao,p.numero_processo_pesq,p.juizo,p.oj_numero,p.oj_sigla,upper(pe.nome) as autor, upper(re.nome) as reu,p.assunto,p.loc_sigla,p.loc_numero, p.sigla_integracao,p.numero_integracao"
					+ " from cad_processo p, cad_pessoa pe,cad_pessoa re"
					+ " where  p.primeiro_autor=pe.codigo  "
					+ sqlcompleta
					+ " and p.primeiro_reu=re.codigo	order by pe.nome ");
		}

		if (numerointegracao != "") {

			sqlcompleta = " and p.numero_integracao like " + "'"
					+ numerointegracao + "%'";
			setSqlpesquisa("select distinct p.numero_processo, p.ficha,p.acao,p.loc_posicao,p.numero_processo_pesq,p.juizo,p.oj_numero,p.oj_sigla,upper(pe.nome) as autor, upper(re.nome) as reu,p.assunto,p.loc_sigla,p.loc_numero, p.sigla_integracao,p.numero_integracao"
					+ " from cad_processo p, cad_pessoa pe,cad_pessoa re"
					+ " where  p.primeiro_autor=pe.codigo"
					+ sqlcompleta
					+ " and p.primeiro_reu=re.codigo	order by pe.nome ");
		}

		try {
			open();
			stmt = con.prepareStatement(sqlpesquisa);
			rs = stmt.executeQuery();
			while (rs.next()) {
				processoCPJ = new ProcessoCPJ();
				processoCPJ.setNumprocesso(rs.getString("numero_processo"));
				processoCPJ.setNumprocessopes(rs
						.getString("numero_processo_pesq"));
				processoCPJ.setNomeautor(rs.getString("autor"));
				processoCPJ.setNomereu(rs.getString("reu"));
				processoCPJ.setSiglaloc(rs.getString("loc_sigla"));
				processoCPJ.setLocnumero(rs.getString("loc_numero"));
				processoCPJ
						.setSiglaintegracao(rs.getString("sigla_integracao"));
				processoCPJ.setNumerointegracao(rs
						.getString("numero_integracao"));
				processoCPJ.setJuizo(rs.getString("juizo"));
				processoCPJ.setOjnumero(rs.getString("oj_numero"));
				processoCPJ.setOjsigla(rs.getString("oj_sigla"));
				// Seta o assunto para string se vier nulo se não mantem o valor
				if (rs.getString("assunto") == null) {
					processoCPJ.setAssuntodoprocesso("");
				} else {
					processoCPJ.setAssuntodoprocesso(rs.getString("assunto")
							.trim());
				}
				processoCPJ.setAcao(rs.getString("acao"));
				processoCPJ.setNumficha(rs.getString("ficha"));
				// Seta a posicao se vier nula se não mantém o valor
				if (rs.getString("loc_posicao") == null) {
					processoCPJ.setProceletronico("");
				} else {
					processoCPJ.setProceletronico(rs.getString("loc_posicao"));
				}

				busca.add(processoCPJ);
			}
			close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return busca;
	}

	/*
	 * Busca um unico processo do CPJ
	 */
	public ProcessoCPJ BuscaUnico(String numprocesso) {
		ProcessoCPJ processobusca;
		processobusca = new ProcessoCPJ();

		String sqlbusbusunico = "select distinct p.numero_processo,   p.ficha,p.acao, p.loc_posicao,p.numero_processo_pesq,p.juizo,p.oj_numero,p.oj_sigla, pe.nome as autor, re.nome as reu,p.assunto,p.loc_sigla,p.loc_numero, p.sigla_integracao,p.numero_integracao"
				+ " from cad_processo p, cad_pessoa pe,cad_pessoa re"
				+ " where  p.primeiro_autor=pe.codigo"
				+ " and   p.numero_processo="
				+ "'"
				+ numprocesso
				+ "'"
				+ " and p.primeiro_reu=re.codigo	order by pe.nome    ";
		try {
			open();
			Converte faz = new Converte();
			String numerotraz = faz.traz(numprocesso);
			stmt = con.prepareStatement(sqlbusbusunico);
			rs = stmt.executeQuery();
			while (rs.next()) {

				processobusca.setNumprocesso(rs.getString("numero_processo"));
				processobusca.setNumprocessopes(numerotraz);
				processobusca.setNomeautor(rs.getString("autor"));
				processobusca.setNomereu(rs.getString("reu"));
				processobusca.setSiglaloc(rs.getString("loc_sigla"));
				processobusca.setLocnumero(rs.getString("loc_numero"));
				processobusca.setSiglaintegracao(rs
						.getString("sigla_integracao"));
				processobusca.setNumerointegracao(rs
						.getString("numero_integracao"));
				processobusca.setJuizo(rs.getString("juizo"));
				processobusca.setOjnumero(rs.getString("oj_numero"));
				processobusca.setOjsigla(rs.getString("oj_sigla"));
				processobusca.setAssuntodoprocesso(rs.getString("assunto"));
				processobusca.setAcao(rs.getString("acao"));
				processobusca.setNumficha(rs.getString("ficha"));
				processobusca.setProceletronico(rs.getString("loc_posicao"));
			}
			
			close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return processobusca;

	}

	public String getSqlpesquisa() {
		return sqlpesquisa;
	}

	public void setSqlpesquisa(String sqlpesquisa) {
		this.sqlpesquisa = sqlpesquisa;
	}
	
	/***
	 * Busca processo repetido
	 * 
	 */
	public String buscaproc(String numprocesso){
		
		
		
		return "";
	}

}
