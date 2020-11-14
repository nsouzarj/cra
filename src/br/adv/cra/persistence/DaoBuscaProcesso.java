package br.adv.cra.persistence;
/**
 * Classe feita para buscar total de processos feito com processo e correspondente;
 * @author Nelson
 *
 */
public class DaoBuscaProcesso extends Dao {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Integer quantidadefeita(String numeroprocesso, Integer idcolaborador) {

		Integer recebe=0;

		String sqlbusca = "SELECT  count(processo.idprocesso) as total FROM   public.processo,   public.solicitacao,   public.correspondente,   public.renumeracao,"
				+ " public.tiposolicitacao,   public.envio,   public.statussolicitacao WHERE  processo.idprocesso = solicitacao.idprocesso AND  solicitacao.idrenumeracao = renumeracao.idrenumeracao AND "
				+ " solicitacao.idstatus = statussolicitacao.idstatus AND  renumeracao.idcorrespondente = correspondente.idcorrespondente AND  renumeracao.idsolicitacao = tiposolicitacao.idtiposolicitacao AND "
				+ "tiposolicitacao.especie='AUDIENCIA' AND  envio.idenvio = renumeracao.idenvio AND  processo.numeroprocesso="+"'"+numeroprocesso+"'"+" AND  correspondente.idcorrespondente="+idcolaborador;

		try {
			open();
			stmt = con.prepareStatement(sqlbusca);
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				 recebe=rs.getInt("total");
			}
		 
			close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return recebe;		
	}	

	public static void main(String[] args) {
		DaoBuscaProcesso poc=new DaoBuscaProcesso();
		Integer teste=poc.quantidadefeita("0001133-82.2016.8.05.0105", 60);
		System.out.print("Quantidade de Audiências feitas: "+teste);
		
	}

}
