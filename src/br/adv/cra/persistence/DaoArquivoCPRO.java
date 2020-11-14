package br.adv.cra.persistence;

import java.util.ArrayList;
import java.util.List;

import br.adv.cra.entity.ArquivoAnexoCPRO;
import br.adv.cra.entity.ArquivoAnexoCPROSalvo;
import br.adv.cra.entity.HistArqCpro;
import br.adv.cra.entity.HistArqCproRejeitado;

/**
 * Dao construida para trazer os documento da pasta do processo CPRO (GED)
 * 
 * @author Nelson oroa.rainha@951
 *
 */

public class DaoArquivoCPRO extends DaoSqlServer {

	private String sqlconsulta;
	private ArquivoAnexoCPRO arquivoAnexoCPRO;
	private ArquivoAnexoCPRO unico;
	private SoliHistArqCproDao soliHistArqCproDao;
	private SoliHistArqCproRejDao soliHistArqCproRejDao;
	private SoliArqCproDaoSalvo soliArqCproDaoSalvo;
	private ArquivoAnexoCPROSalvo arquivoAnexoCPROSalvo;
	private boolean jacarregou;
	private List<ArquivoAnexoCPRO> busca = new ArrayList<ArquivoAnexoCPRO>();

	/*
	 * Aqui busArcaca os documento para a busca do arquivo FAzendo a verificação
	 * se ja existe arquvo na base
	 */

	public DaoArquivoCPRO() {

	}

	public boolean isJacarregou() {
		return jacarregou;
	}

	public void setJacarregou(boolean jacarregou) {
		this.jacarregou = jacarregou;
	}

	public List<ArquivoAnexoCPRO> Consulta(String CodigoBusca) {
		List<ArquivoAnexoCPRO> busca = new ArrayList<ArquivoAnexoCPRO>();
	 if (jacarregou==false){
	 busca=null;
	 busca = new ArrayList<ArquivoAnexoCPRO>();
	 }
   busca = new ArrayList<ArquivoAnexoCPRO>();
   //Ip onde esta o ged atual é 192.168.0.74  adcionei o  nome do host
		sqlconsulta = "SELECT Id_Arquivo,Id_Contexto, Codigo, nome as nomearquivo,'\\\\crrj01vs004\\F\\CPPro\\GED Server Files\\'+caminho+nome As PathDoArquivo,Anexado_Por,Anexado_Em,[CPPro].[dbo].GED_Arquivos.Descricao as descricaoarq,FORMAT(Anexado_Em  , 'dd/MM/yyyy HH:mm:ss') as Anexador_Em "
				+ " from [CPPro].[dbo].FullProcesso, [CPPro].[dbo].GED_Arquivos"
				+ "	WHERE idPasta=GED_Arquivos.Id_Contexto and IsModelo_Documento >=1 AND UPPER(replace(Codigo,' ','')) like " + "'%"
				+ CodigoBusca.toUpperCase().replaceAll(" ", "") + "%'";

		// and IsModelo_Documento >=1

		soliArqCproDaoSalvo = new SoliArqCproDaoSalvo();

		try {

			open();
			stmt = con.prepareStatement(sqlconsulta);
			//
			rs = stmt.executeQuery();
			close();
			
			
				while (rs.next()) {
                   
					arquivoAnexoCPRO = new ArquivoAnexoCPRO();
					arquivoAnexoCPRO.setIdArquivo(rs.getInt("Id_Arquivo"));
					arquivoAnexoCPRO.setIdContexto(rs.getInt("Id_Contexto"));
					arquivoAnexoCPRO.setPastaDoProcesso(rs.getString("Codigo").toUpperCase());
					arquivoAnexoCPRO.setCaminhoGed(rs.getString("PathDoArquivo"));
					arquivoAnexoCPRO.setNomeDocumento(rs.getString("nomearquivo"));
					arquivoAnexoCPRO.setDescricao(rs.getString("descricaoarq"));
					arquivoAnexoCPRO.setAnexadoPor(rs.getString("Anexado_Por"));
					arquivoAnexoCPRO.setAnexadoEm(rs.getTimestamp("Anexado_Em"));
					arquivoAnexoCPRO.setTipoDocumento("SISGECOL");

					/**
					 * Aqui busca no historico de arquvos se tiver na podera
					 * mais ser usado na solicitação nova Assim fica bom
					 */

					soliHistArqCproDao = new SoliHistArqCproDao();
					//DOcumentei aqui 
					//HistArqCpro hist = new HistArqCpro();
					// hist = null;
					// hist = soliHistArqCproDao.BuscaUnico(arquivoAnexoCPRO.getIdArquivo());
					/**
					 * Aqui busca os rejeitados (Documentos)
					 */
              
					
					
					HistArqCproRejeitado histArqCproRejeitado = new HistArqCproRejeitado();
					histArqCproRejeitado = null;
					histArqCproRejeitado = soliHistArqCproRejDao.BuscaUnico(arquivoAnexoCPRO.getIdArquivo());

					arquivoAnexoCPROSalvo = new ArquivoAnexoCPROSalvo();

					if (histArqCproRejeitado != null) {
						arquivoAnexoCPRO.setRejeitado(true);
						arquivoAnexoCPRO
								.setMotivodarejeicao("MOTIVO DA REIJEIÇÃO:" + "\n" + histArqCproRejeitado.getMotivo());
						arquivoAnexoCPROSalvo.setRejeitado(arquivoAnexoCPRO.isRejeitado());
						arquivoAnexoCPROSalvo.setMotivodarejeicao(arquivoAnexoCPRO.getMotivodarejeicao());
					} else {
						arquivoAnexoCPRO.setRejeitado(false);
						arquivoAnexoCPRO.setMotivodarejeicao("");
						arquivoAnexoCPROSalvo.setRejeitado(arquivoAnexoCPRO.isRejeitado());
						arquivoAnexoCPROSalvo.setMotivodarejeicao(arquivoAnexoCPRO.getMotivodarejeicao());
					}

					arquivoAnexoCPROSalvo.setIdArquivo(arquivoAnexoCPRO.getIdArquivo());
					arquivoAnexoCPROSalvo.setPastaDoProcesso(arquivoAnexoCPRO.getPastaDoProcesso());
					arquivoAnexoCPROSalvo.setAnexadoEm(arquivoAnexoCPRO.getAnexadoEm());
					arquivoAnexoCPROSalvo.setIdContexto(arquivoAnexoCPRO.getIdContexto());

					arquivoAnexoCPROSalvo.setDescricao(arquivoAnexoCPRO.getDescricao());
					arquivoAnexoCPROSalvo.setAnexadoPor(arquivoAnexoCPRO.getAnexadoPor());
					arquivoAnexoCPROSalvo.setNomeDocumento(arquivoAnexoCPRO.getNomeDocumento());
					arquivoAnexoCPROSalvo.setTipoDocumento("SISGECOL");
					arquivoAnexoCPROSalvo.setCaminhoGed(arquivoAnexoCPRO.getCaminhoGed());

					soliArqCproDaoSalvo.Salvar(arquivoAnexoCPROSalvo);
				
					busca.add(arquivoAnexoCPRO);
					
				}
				
			close();

		} catch (Exception e) {
			e.printStackTrace();
			// System.out.print("Erro de formato de
			// campo."+e.printStackTrace());
		}

		return busca;
	}

	/*
	 * Aqui busca os documento para a busca do arquivo no cpro trazendo todos
	 * Mesmp ja anexado no sisgecol dando oportunidade de aproveitar os mesmos
	 * Arquivos para a nova solicitação data 02/08/2017
	 */
	public List<ArquivoAnexoCPRO> CosultaTodos(String CodigoBusca) {
		 //Ip onde esta o ged atual é 192.168.0.74  adcionei o  nome do host
		List<ArquivoAnexoCPRO> busca = new ArrayList<ArquivoAnexoCPRO>();

		sqlconsulta = "SELECT Id_Arquivo,Id_Contexto, Codigo, nome as nomearquivo,'\\\\crrj01vs004\\F\\CPPro\\GED Server Files\\'+caminho+nome As PathDoArquivo,Anexado_Por,Anexado_Em,[CPPro].[dbo].GED_Arquivos.Descricao as descricaoarq,FORMAT(Anexado_Em  , 'dd/MM/yyyy HH:mm:ss') as Anexador_Em "
				+ " from [CPPro].[dbo].FullProcesso, [CPPro].[dbo].GED_Arquivos"
				+ "	WHERE idPasta=GED_Arquivos.Id_Contexto" + "	and IsModelo_Documento >=1 AND UPPER(replace(Codigo,' ','')) like "
				+ "'%" + CodigoBusca.toUpperCase().replaceAll(" ", "") + "%'" + " order by Id_Arquivo";

		try {
			open();
			
			stmt = con.prepareStatement(sqlconsulta);
			rs = stmt.executeQuery();
			while (rs.next()) {
				arquivoAnexoCPRO = null;
				arquivoAnexoCPRO = new ArquivoAnexoCPRO();
				arquivoAnexoCPRO.setIdArquivo(rs.getInt("Id_Arquivo"));
				arquivoAnexoCPRO.setIdContexto(rs.getInt("Id_Contexto"));
				arquivoAnexoCPRO.setPastaDoProcesso(rs.getString("Codigo").toUpperCase());
				arquivoAnexoCPRO.setCaminhoGed(rs.getString("PathDoArquivo"));
				arquivoAnexoCPRO.setNomeDocumento(rs.getString("nomearquivo"));
				arquivoAnexoCPRO.setDescricao(rs.getString("descricaoarq"));
				arquivoAnexoCPRO.setAnexadoPor(rs.getString("Anexado_Por"));
				arquivoAnexoCPRO.setAnexadoEm(rs.getTimestamp("Anexado_Em"));
				arquivoAnexoCPRO.setTipoDocumento("SISGECOL");
				// Adciona na listagem paraser mostradro na nova solicitação

				busca.add(arquivoAnexoCPRO);
             
			}
			close();
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.print("Erro de formato de campo.");
		}

		return busca;
	}

	/*
	 * Busca pelo id do arquivo do GED do cpprpo
	 */

	public ArquivoAnexoCPRO busca(Integer idarq) {
		 //Ip onde esta o ged atual é 192.168.0.74  adcionei o  nome do host
		String sqlconsulta1 = "SELECT Id_Arquivo,Id_Contexto, Codigo, nome as nomearquivo,'\\\\crrj01vs004\\F\\CPPro\\GED Server Files\\'+caminho+nome As PathDoArquivo,Anexado_Por,Anexado_Em,[CPPro].[dbo].GED_Arquivos.Descricao as descricaoarq,FORMAT(Anexado_Em  , 'dd/MM/yyyy HH:mm:ss') as Anexador_Em "
				+ " from [CPPro].[dbo].FullProcesso, [CPPro].[dbo].GED_Arquivos"
				+ "	WHERE idPasta=GED_Arquivos.Id_Contexto" + "	and IsModelo_Documento >=1 AND Id_Arquivo=" + idarq;
		unico = new ArquivoAnexoCPRO();
		try {
			
			open();
			stmt = con.prepareStatement(sqlconsulta1);
			rs = stmt.executeQuery();
			while (rs.next()) {
				unico = new ArquivoAnexoCPRO();
				unico.setIdArquivo(rs.getInt("Id_Arquivo"));
				unico.setIdContexto(rs.getInt("Id_Contexto"));
				unico.setPastaDoProcesso(rs.getString("Codigo").toUpperCase());
				unico.setCaminhoGed(rs.getString("PathDoArquivo"));
				unico.setNomeDocumento(rs.getString("nomearquivo"));
				unico.setDescricao(rs.getString("descricaoarq"));
				unico.setAnexadoPor(rs.getString("Anexado_Por"));
				unico.setAnexadoEm(rs.getTimestamp("Anexado_Em"));
				unico.setTipoDocumento("SISGECOL");

			}
			close();
			
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.print("Erro de formato de campo.");
		}

		return unico;
	}

	public void AtualizaArquivos(String CodigoBusca, Integer idsolicitacao) {
       //Ip onde esta o ged atual é 192.168.0.74  adcionei o  nome do host
		sqlconsulta = "SELECT Id_Arquivo,Id_Contexto, Codigo, nome as nomearquivo,'\\\\crrj01vs004\\F\\CPPro\\GED Server Files\\'+caminho+nome As PathDoArquivo,Anexado_Por,Anexado_Em,[CPPro].[dbo].GED_Arquivos.Descricao as descricaoarq,FORMAT(Anexado_Em  , 'dd/MM/yyyy HH:mm:ss') as Anexador_Em "
				+ " from [CPPro].[dbo].FullProcesso, [CPPro].[dbo].GED_Arquivos"
				+ "	WHERE idPasta=GED_Arquivos.Id_Contexto" + "	and IsModelo_Documento >=1 AND UPPER(replace(Codigo,' ','')) like "
				+ "'%" + CodigoBusca.toUpperCase().replaceAll(" ", "") + "%'" + " order by Id_Arquivo";

		try {
			
			open();
			stmt = con.prepareStatement(sqlconsulta);
			rs = stmt.executeQuery();
			while (rs.next()) {
				arquivoAnexoCPRO = new ArquivoAnexoCPRO();
				arquivoAnexoCPRO.setIdArquivo(rs.getInt("Id_Arquivo"));
				arquivoAnexoCPRO.setIdContexto(rs.getInt("Id_Contexto"));
				arquivoAnexoCPRO.setPastaDoProcesso(rs.getString("Codigo").toUpperCase());
				arquivoAnexoCPRO.setCaminhoGed(rs.getString("PathDoArquivo"));
				arquivoAnexoCPRO.setNomeDocumento(rs.getString("nomearquivo"));
				arquivoAnexoCPRO.setDescricao(rs.getString("descricaoarq"));
				arquivoAnexoCPRO.setAnexadoPor(rs.getString("Anexado_Por"));
				arquivoAnexoCPRO.setAnexadoEm(rs.getTimestamp("Anexado_Em"));
				arquivoAnexoCPRO.setTipoDocumento("SISGECOL");
				// Adciona na listagem paraser mostradro na nova solicitação
				arquivoAnexoCPRO.setRejeitado(false);
				arquivoAnexoCPRO.setMotivodarejeicao("");

				arquivoAnexoCPROSalvo = new ArquivoAnexoCPROSalvo();
				soliArqCproDaoSalvo = new SoliArqCproDaoSalvo();

				arquivoAnexoCPROSalvo = soliArqCproDaoSalvo.BuscaUnicoPorId(arquivoAnexoCPRO.getIdArquivo(),idsolicitacao);

				if (arquivoAnexoCPROSalvo == null) {
					arquivoAnexoCPROSalvo = new ArquivoAnexoCPROSalvo();
					arquivoAnexoCPROSalvo.setRejeitado(arquivoAnexoCPRO.isRejeitado());
					arquivoAnexoCPROSalvo.setMotivodarejeicao(arquivoAnexoCPRO.getMotivodarejeicao());
					arquivoAnexoCPROSalvo.setIdArquivo(arquivoAnexoCPRO.getIdArquivo());
					arquivoAnexoCPROSalvo.setPastaDoProcesso(arquivoAnexoCPRO.getPastaDoProcesso());
					arquivoAnexoCPROSalvo.setAnexadoEm(arquivoAnexoCPRO.getAnexadoEm());
					arquivoAnexoCPROSalvo.setIdContexto(arquivoAnexoCPRO.getIdContexto());
					arquivoAnexoCPROSalvo.setDescricao(arquivoAnexoCPRO.getDescricao());
					arquivoAnexoCPROSalvo.setAnexadoPor(arquivoAnexoCPRO.getAnexadoPor());
					arquivoAnexoCPROSalvo.setNomeDocumento(arquivoAnexoCPRO.getNomeDocumento());
					arquivoAnexoCPROSalvo.setTipoDocumento("SISGECOL");
					arquivoAnexoCPROSalvo.setCaminhoGed(arquivoAnexoCPRO.getCaminhoGed());
					arquivoAnexoCPROSalvo.setBaixado(false);
					arquivoAnexoCPROSalvo.setBaixadoEm(null);
					arquivoAnexoCPROSalvo.setIdsolicitacao(idsolicitacao);
					soliArqCproDaoSalvo.Salvar(arquivoAnexoCPROSalvo);
				}

			}
			close();
			
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.print("Erro de formato de campo.");
		}

	}

	public void AtualizaArquivoUnico(Integer idarquivo, Integer idsolicitacao) {
		 //Ip onde esta o ged atual é 192.168.0.74  adcionei o  nome do host
		sqlconsulta = "SELECT Id_Arquivo,Id_Contexto, Codigo, nome as nomearquivo,'\\\\crrj01vs004\\F\\CPPro\\GED Server Files\\'+caminho+nome As PathDoArquivo,Anexado_Por,Anexado_Em,[CPPro].[dbo].GED_Arquivos.Descricao as descricaoarq,FORMAT(Anexado_Em  , 'dd/MM/yyyy HH:mm:ss') as Anexador_Em "
				+ " from [CPPro].[dbo].FullProcesso, [CPPro].[dbo].GED_Arquivos"
				+ "	WHERE idPasta=GED_Arquivos.Id_Contexto" + "	and IsModelo_Documento >=1 AND Id_Arquivo=" + idarquivo;

		try {
			open();
			stmt = con.prepareStatement(sqlconsulta);
			rs = stmt.executeQuery();
			while (rs.next()) {
				arquivoAnexoCPRO = new ArquivoAnexoCPRO();
				arquivoAnexoCPRO.setIdArquivo(rs.getInt("Id_Arquivo"));
				arquivoAnexoCPRO.setIdContexto(rs.getInt("Id_Contexto"));
				arquivoAnexoCPRO.setPastaDoProcesso(rs.getString("Codigo").toUpperCase());
				arquivoAnexoCPRO.setCaminhoGed(rs.getString("PathDoArquivo"));
				arquivoAnexoCPRO.setNomeDocumento(rs.getString("nomearquivo"));
				arquivoAnexoCPRO.setDescricao(rs.getString("descricaoarq"));
				arquivoAnexoCPRO.setAnexadoPor(rs.getString("Anexado_Por"));
				arquivoAnexoCPRO.setAnexadoEm(rs.getTimestamp("Anexado_Em"));
				arquivoAnexoCPRO.setTipoDocumento("SISGECOL");
				// Adciona na listagem paraser mostradro na nova solicitação
				arquivoAnexoCPRO.setRejeitado(false);
				arquivoAnexoCPRO.setMotivodarejeicao("");

				arquivoAnexoCPROSalvo = new ArquivoAnexoCPROSalvo();
				soliArqCproDaoSalvo = new SoliArqCproDaoSalvo();
		
				//Verifica na base se esta 
				arquivoAnexoCPROSalvo.setRejeitado(arquivoAnexoCPRO.isRejeitado());
				arquivoAnexoCPROSalvo.setMotivodarejeicao(arquivoAnexoCPRO.getMotivodarejeicao());

				arquivoAnexoCPROSalvo.setIdArquivo(arquivoAnexoCPRO.getIdArquivo());
				arquivoAnexoCPROSalvo.setPastaDoProcesso(arquivoAnexoCPRO.getPastaDoProcesso());
				arquivoAnexoCPROSalvo.setAnexadoEm(arquivoAnexoCPRO.getAnexadoEm());
				arquivoAnexoCPROSalvo.setIdContexto(arquivoAnexoCPRO.getIdContexto());

				arquivoAnexoCPROSalvo.setDescricao(arquivoAnexoCPRO.getDescricao());
				arquivoAnexoCPROSalvo.setAnexadoPor(arquivoAnexoCPRO.getAnexadoPor());
				arquivoAnexoCPROSalvo.setNomeDocumento(arquivoAnexoCPRO.getNomeDocumento());
				arquivoAnexoCPROSalvo.setTipoDocumento("SISGECOL");
				arquivoAnexoCPROSalvo.setCaminhoGed(arquivoAnexoCPRO.getCaminhoGed());
				arquivoAnexoCPROSalvo.setBaixado(false);
				arquivoAnexoCPROSalvo.setBaixadoEm(null);
				arquivoAnexoCPROSalvo.setIdsolicitacao(idsolicitacao);
				
				soliArqCproDaoSalvo.Salvar(arquivoAnexoCPROSalvo);
	

			}
			close();
		} catch (

		Exception e) {
			// e.printStackTrace();
			System.out.print("Erro de formato de campo.");
		}

	}

	public static void main(String[] args) {
		DaoArquivoCPRO ArquivoAnexoCPRO = new DaoArquivoCPRO();
		ArquivoAnexoCPRO teste = new ArquivoAnexoCPRO();
		teste = ArquivoAnexoCPRO.busca(1471758);
		System.out.print(teste.toString());

	}

}
