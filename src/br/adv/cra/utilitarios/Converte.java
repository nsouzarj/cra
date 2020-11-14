package br.adv.cra.utilitarios;

public class Converte {

	private String recebe;
	private String numero;
	private Long recebe3;

	public String getRecebe() {
		return recebe;
	}

	public String traz(String recebe1) {
		String var5 = "";
		try {
			// tira ponto e espacos em branco
			String var1 = recebe1.replace("-", "").trim();
			String var2 = var1.replace(".", "").trim();
			String var3 = var2.replace("(", "").trim();
			String var4 = var3.replace(")", "").trim();
			
			var5 = var4.replace(" ", "").trim();
			
			recebe3 = Long.parseLong(var5);
			recebe = recebe3.toString();
		} catch (Exception e) {
			recebe = var5.trim();
		}
		return recebe;
	}

	public void setRecebe(String recebe) {
		this.recebe = recebe;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

}
