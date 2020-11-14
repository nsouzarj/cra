package br.adv.cra.persistence;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;

public class Main {
	// Gerar a tabela
	public static void main(String[] args) {
		Configuration cfg = new Configuration()
				.configure("config/hibernate.cfg.xml");
		// chama o arquivo que possui a configuracao do banco
		SchemaUpdate cf = new SchemaUpdate(cfg);
		cf.execute(true, true);
		// SchemaExport cf= new SchemaExport(cfg);
		// new SchemaExport(cfg).create(true, true);
		// cria a tabela
	}
}
