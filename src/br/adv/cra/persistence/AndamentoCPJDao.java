package br.adv.cra.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.adv.cra.entity.AndamentoCPJ;

public class AndamentoCPJDao extends DaoMysql implements Serializable {
	private static final long serialVersionUID = 1L;
	private String sqlpesquisa;
	private AndamentoCPJ andamentoCPJ;

	/**
	 * Busca os andamento no cpj
	 * 
	 * @param ficha
	 * @return
	 */
	public List<AndamentoCPJ> Busca(String ficha) {
		List<AndamentoCPJ> busca = new ArrayList<AndamentoCPJ>();
		sqlpesquisa = "select ficha,data_hora_lan,texto from cad_tramitacao where ficha="
				+ ficha + "   order by data_hora_lan desc";

		try {
			open();
			stmt = con.prepareStatement(sqlpesquisa);
			rs = stmt.executeQuery();
			while (rs.next()) {
				andamentoCPJ = new AndamentoCPJ();
				andamentoCPJ.setDatahora(rs.getDate("data_hora_lan"));
				andamentoCPJ.setFicha(rs.getString("ficha"));
				andamentoCPJ.setAndamento(rs.getString("texto"));
				if (andamentoCPJ.getAndamento().length() > 1) {
					busca.add(andamentoCPJ);
				}
			}
			close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return busca;
	}

	public String getSqlpesquisa() {
		return sqlpesquisa;
	}

	public void setSqlpesquisa(String sqlpesquisa) {
		this.sqlpesquisa = sqlpesquisa;
	}

	public AndamentoCPJ getAndamentoCPJ() {
		return andamentoCPJ;
	}

	public void setAndamentoCPJ(AndamentoCPJ andamentoCPJ) {
		this.andamentoCPJ = andamentoCPJ;
	}

}
