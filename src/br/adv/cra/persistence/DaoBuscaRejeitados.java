package br.adv.cra.persistence;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.adv.cra.entity.Rejeitado;

/**
 * Classe que faz a busca nos arquivos que foram rejeitados pelo colaborador
 * interno doCPRPRO;
 * 
 * @author Nelson
 *
 */

public class DaoBuscaRejeitados extends Dao {

	public List<Rejeitado> CosultaTodos(Date datainicial, Date datafinal, String CodigoBusca) throws Exception {
		List<Rejeitado> rejeitados = new ArrayList<Rejeitado>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		String sqlbusca = "SELECT   anexocprosalvo.idsolicitacao,  anexocprosalvo.nomedocumento,  processo.numeroprocesso,   upper(processo.localizacao) as pasta,   upper(anexocprosalvo.anexadopor) as anexadopor, anexocprosalvo.anexadoem,  anexocprosalvo.motivodarejeicao,"
				+ "  histarqcpprorej.rejeitadoem,   solicitacao.dataconclusao,   usuario.nomecompleto,   solicitacao.datasolictacao FROM "
				+ "  public.anexocprosalvo,   public.histarqcpprorej,   public.processo,   public.usuario,   public.solicitacao WHERE"
				+ "  anexocprosalvo.idarqcpprosalvo = histarqcpprorej.idarquivocppro AND  anexocprosalvo.idsolicitacao = solicitacao.idsolicitacao AND"
				+ "  upper(processo.localizacao) = upper(anexocprosalvo.pastadoprocesso) AND  usuario.idusuario = solicitacao.idusuario";

		if (datainicial != null || datafinal != null) {

			sqlbusca = sqlbusca + " and to_date(to_char(rejeitadoem,'DD/MM/YYYY'),'DD/MM/YYYY') >=" + "'"
					+ sdf.format(datainicial) + "'"
					+ "   and to_date(to_char(rejeitadoem,'DD/MM/YYYY'),'DD/MM/YYYY') <=" + "'" + sdf.format(datafinal)
					+ "'";

		}

		sqlbusca = sqlbusca+ "  order by  histarqcpprorej.rejeitadoem desc";

		open();
		stmt = con.prepareStatement(sqlbusca);
		rs = stmt.executeQuery();

		while (rs.next()) {
			Rejeitado recebe = new Rejeitado();
			recebe.setIdsolicitacao(rs.getInt("idsolicitacao"));
			recebe.setNumeroprocesso(rs.getString("numeroprocesso"));
			recebe.setPasta(rs.getString("pasta"));
			recebe.setAnexadopor(rs.getString("anexadopor"));
			recebe.setDocumento(rs.getString("nomedocumento"));
			recebe.setQuemrejeitou(rs.getString("nomecompleto"));
			recebe.setRejeitadoem(rs.getTimestamp("rejeitadoem"));
			recebe.setMotivo(rs.getString("motivodarejeicao"));
			recebe.setDatasolicitacao(rs.getTimestamp("datasolictacao"));
			recebe.setDatanexado(rs.getTimestamp("anexadoem"));
			rejeitados.add(recebe);

		}

		close();

		return rejeitados;
	}

	public static void main(String[] args) {
		DaoBuscaRejeitados teste = new DaoBuscaRejeitados();
		try {
			System.out.print(teste.CosultaTodos(null,null,"0").toString());
		} catch (Exception e) {
			System.out.print("Erro na busca" + e.getMessage());
		}

	}

}
