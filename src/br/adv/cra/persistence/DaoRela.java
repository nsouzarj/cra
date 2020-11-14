package br.adv.cra.persistence;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mysql.jdbc.Statement;

public class DaoRela extends Dao {

	@SuppressWarnings("null")
	public void Atualiza(String data_inicial,String data_final) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		Date data1 = null;
		Date data2 = null;
		try {
			data1 = dateFormat.parse(data_inicial);
			data2 =  dateFormat.parse(data_final);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		String sqldelta = "delete from tbrela ";
		String sql1 = " insert into tbrela (idcorrespondente,nome) select correspondente.idcorrespondente, correspondente.nome from correspondente where correspondente.ativo=true\r\n"
				+ "";
		
		String sqlatualizavalores ="update tbrela set quantidade=0, rejeitada=0";
		
		String sqlatualiza=" update tbrela set quantidade=atu.total\r\n" + 
	    		" \r\n" + 
	    		" from (\r\n" + 
	    		" SELECT \r\n" + 
	    		"   correspondente.idcorrespondente, \r\n" + 
	    		"   correspondente.nome,\r\n" + 
	    		"   count(solicitacao.idsolicitacao) total\r\n" + 
	    		// "   statussolicitacao.status\r\n" + 
	    		" FROM \r\n" + 
	    		"   public.correspondente, \r\n" + 
	    		"   public.comarca, \r\n" + 
	    		"   public.renumeracao, \r\n" + 	
	    		"   public.solicitacao, \r\n" + 
	    		"   public.tiposolicitacao,\r\n" + 
	    		"   public.statussolicitacao,\r\n" + 
	    		"   public.uf\r\n" + 
	    		" WHERE \r\n" + 
	    		"   correspondente.idcorrespondente = renumeracao.idcorrespondente AND\r\n" + 
	    		"   renumeracao.idrenumeracao = solicitacao.idrenumeracao AND\r\n" + 
	    		"   tiposolicitacao.idtiposolicitacao=renumeracao.idsolicitacao AND\r\n" + 
	    		"   tiposolicitacao.especie  IN ('DILIGENCIA','AUDIENCIA') AND\r\n" + 
	         	"   statussolicitacao.idstatus=solicitacao.idstatus AND\r\n" + 
	    	    "   solicitacao.idstatus <> 6 AND\r\n" + 
	    		"   solicitacao.idcomarca =comarca.idcomarca AND\r\n" + 
	    		"   uf.iduf=comarca.uf_iduf AND\r\n" + 
	    		"   to_date(to_char(solicitacao.datasolictacao,'DD/MM/YYYY'),'DD/MM/YYYY') >= "+ "'"+data_inicial+"'" +" and\r\n" + 
	    		"   to_date(to_char(solicitacao.datasolictacao,'DD/MM/YYYY'),'DD/MM/YYYY') <= "+ "'"+data_final+"'"  + " and\r\n" + 
	    		"   correspondente.idcorrespondente not in (226,78,77,229) and\r\n" + 
	    		"   correspondente.ativo=true \r\n" + 
	    		" GROUP BY \r\n" + 
	    		"   correspondente.idcorrespondente, \r\n" + 
	    		"   correspondente.nome) As atu\r\n" + 
	    		//"   statussolicitacao.status) AS atu\r\n" + 
	    		"   where tbrela.idcorrespondente=atu.idcorrespondente\r\n" + 
	    		"   \r\n" + 
	    		"";
//Testee
		
	    String sqlatualizarejeitada=" update tbrela set rejeitada=atu.total\r\n" + 
	    		" \r\n" + 
	    		" from (\r\n" + 
	    		" SELECT \r\n" + 		
	    		"   correspondente.idcorrespondente, \r\n" + 
	    		"   correspondente.nome,\r\n" + 
	    		"   count(solicitacao.idsolicitacao) total\r\n" + 
	    		//"   statussolicitacao.status\r\n" + 
	    		" FROM \r\n" + 
	    		"   public.correspondente, \r\n" + 
	    		"   public.comarca, \r\n" + 
	    		"   public.renumeracao, \r\n" + 
	    		"   public.solicitacao, \r\n" + 
	    		"   public.tiposolicitacao,\r\n" + 
	    		"   public.statussolicitacao,\r\n" + 
	    		"   public.uf\r\n" + 
	    		" WHERE \r\n" + 
	    		"   correspondente.idcorrespondente = renumeracao.idcorrespondente AND\r\n" + 
	    		"   renumeracao.idrenumeracao = solicitacao.idrenumeracao AND\r\n" + 
	    		"   tiposolicitacao.idtiposolicitacao=renumeracao.idsolicitacao AND\r\n" + 
	    		"   tiposolicitacao.especie  IN ('DILIGENCIA','AUDIENCIA') AND\r\n" + 
	    		"   statussolicitacao.idstatus=solicitacao.idstatus AND\r\n" + 
	    		"   solicitacao.idstatus =6 AND\r\n" + 
	    		"   solicitacao.idcomarca =comarca.idcomarca AND\r\n" + 
	    		"   uf.iduf=comarca.uf_iduf AND\r\n" + 
	    		"   to_date(to_char(solicitacao.datasolictacao,'DD/MM/YYYY'),'DD/MM/YYYY') >= "+ "'"+data_inicial+"'"+" and\r\n" + 
	    		"   to_date(to_char(solicitacao.datasolictacao,'DD/MM/YYYY'),'DD/MM/YYYY') <= "+ "'"+data_final+"'"+ " and\r\n" + 
	    		"   correspondente.idcorrespondente not in (226,78,77,229) and\r\n" + 
	    		"   correspondente.ativo=true \r\n" + 
	    		" GROUP BY \r\n" + 
	    		"   correspondente.idcorrespondente, \r\n" + 
	    		"   correspondente.nome) AS atu \r\n" + 
	    		//"   statussolicitacao.status) AS atu\r\n" + 
	    		"   where tbrela.idcorrespondente=atu.idcorrespondente\r\n" + 
	    		"   \r\n" + 
	    		"";
		/**
		 * Exclui as linhas da tabela
		 */
		try {
			open();
			stat = con.createStatement();
			stat.execute(sqldelta);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/**
		 * Atualiza a tabela
		 */
		try {
			open();
			stat = con.createStatement();
			stat.execute(sql1);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			open();
			stat = con.createStatement();
			stat.execute(sqlatualizavalores);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			open();
			stat = con.createStatement();
			stat.execute(sqlatualiza);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        /**
         * Atualiza a tabela
         */
		try {
			open();
			stat = con.createStatement();
			stat.execute(sqlatualizarejeitada);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public static void main(String[] args) {
		DaoRela d= new DaoRela();
		d.Atualiza("01/01/2019", "26/02/2019");
	}
}
