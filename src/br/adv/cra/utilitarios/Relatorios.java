package br.adv.cra.utilitarios;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Classe de Relatorios usada na emissão de relatorios
 * 
 * @author sandra
 * 
 */

@SuppressWarnings("deprecation")
public class Relatorios {

	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	/**
	 * Imprime o relatorio passando os paramentros a conexao os arquivos do ireport 
	 */
	public String imprimir(HashMap parametrosRela, String nomerelatorio,
			Connection con, String nomejasper, String nomexml, String formato)
			throws FileNotFoundException {
		String nomearquivo = null;

		try {
			String REAL_PATH1 = getRealPath(nomexml);
			String REAL_PATH2 = getRealPath(nomejasper);
			String arquivoxml = REAL_PATH1;
			String arquivoJasper = REAL_PATH2;
			// InputStream isJasper =
			// getClass().getResourceAsStream(arquivoJasper);
			// InputStream isJapser =
			// Report.class.getResourceAsStream(arquivoJasper);
			JRExporter tipoarquivogerado = null;
			JasperCompileManager.compileReportToFile(arquivoxml, arquivoJasper);
			JasperReport relJasperReport = (JasperReport) JRLoader.loadObjectFromFile(arquivoJasper);
			JasperPrint impressora = JasperFillManager.fillReport(
					relJasperReport, parametrosRela, con);
			// JasperPrint impressora= JasperFillManager.fillReport(isJasper,
			// parametrosRela,con);
			// JasperPrint xx= JasperFillManager.fillReport(isJasper,
			// parametrosRela, con);
			if (formato.equals(".pdf")) {
				tipoarquivogerado = new JRPdfExporter();
			} else if (formato.equals(".xls")) {
				
				tipoarquivogerado = new JRXlsExporter();
				tipoarquivogerado.setParameter(JRXlsExporterParameter.MAXIMUM_ROWS_PER_SHEET,20000);
			}

			File arquivogerado = null;
			arquivogerado = new java.io.File("c:/relaweb/" + nomerelatorio
					+ formato);
			tipoarquivogerado.setParameter(JRExporterParameter.JASPER_PRINT,
					impressora);
			tipoarquivogerado.setParameter(JRExporterParameter.OUTPUT_FILE,
					arquivogerado);
			tipoarquivogerado.exportReport();
			nomearquivo = arquivogerado.getName();
		} catch (JRException e) {
			e.printStackTrace();
		}

		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nomearquivo;

	}

	/**
	 * Traz o caminho fisico do arquivo
	 * 
	 * @param pathReport
	 * @return
	 */
	public static String getRealPath(String pathReport) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServletContext context = (ServletContext) facesContext
				.getExternalContext().getContext();
		return context.getRealPath(pathReport);
	}

}
