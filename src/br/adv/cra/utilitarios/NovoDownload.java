package br.adv.cra.utilitarios;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.model.StreamedContent;

public class NovoDownload {
	private StreamedContent file;
	    
	public StreamedContent getFile() {
		return file;
	}

	public void setFile(StreamedContent file) {
		this.file = file;
	}
	

	

	public void Abrir(String filename, String tipo, boolean excluir) {
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
	    HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
       
		//URL url;
		try {
			//url = new URL(filename);

			File arq1 = new File(filename);
			arq1.getAbsoluteFile();
			String teste = arq1.getName();
			File file1 = new File("C:\\temp\\" + teste);

			//InputStream is = url.openStream();
			FileInputStream is = new FileInputStream(filename);
			
			FileOutputStream fos = new FileOutputStream(file1);
			//response.reset();
			response.setContentType("application/octet-stream");
			response.addHeader("Content-disposition",
					"attachament; filename=\"" + file.getName());// Era attachement

			int bytes = 0;

			while ((bytes = is.read()) != -1) {
				fos.write(bytes);
			}

			is.close();
            fos.flush();
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		NovoDownload teste = new NovoDownload();
		String filename = "C:\\Financeiro\\Teste.xls";
		teste.Abrir(filename, "", false);
	}

}
