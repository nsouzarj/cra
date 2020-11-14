package br.adv.cra.utilitarios;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.model.StreamedContent;

/**
 * Classe de Donwload de arquivo
 * 
 * @author Nelson Seixas de Souza
 * 
 */
public class DownloadArquivo {
	private StreamedContent arquivo;

	public StreamedContent getArquivo() {
	    
		return this.arquivo;
	}

	public void setArquivo(StreamedContent arquivo) {
		this.arquivo = arquivo;
	}

	// Abre o arquivo nos formatos especificados
	public void Abrir(String filename, String tipo, boolean excluir)
			throws FileNotFoundException {
		File arquivo1;
	
		FacesContext facesContext = FacesContext.getCurrentInstance();
	    HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
	
		String arq = filename;
		FileInputStream is = new FileInputStream(arq);
		arquivo1= new File(filename);
        arquivo1.getAbsoluteFile(); //Pega o nome somente do arquivo
        filename=arquivo1.getName();
       
		try {
			/**
			if (tipo.equals(".pdf")) {// Arquivo pdf
				response.setContentType("application/pdf");
				response.addHeader("Content-disposition",
						"attachament; filename=\"" + filename); // Era
																// attachement

			} else if (tipo.equals(".xls")) {// Planilha eletronica
				response.setContentType("application/xls");
				response.addHeader("Content-disposition",
						"attachament; filename=\"" + filename);

			} else if (tipo.equals(".jpg")) {// Imagem jpg
				response.setContentType("image/jpeg");
				response.addHeader("Content-disposition",
						"attachament; filename=\"" + filename);

			} else if (tipo.equals(".jpeg")) {// Imagem jpg
				response.setContentType("image/jpeg");
				response.addHeader("Content-disposition",
						"attachament; filename=\"" + filename);

			} else if (tipo.equals("")) { // Imprime padrao em pdf
				response.setContentType("application/octet-stream");
				response.addHeader("Content-disposition",
						"attachament; filename=\"" + filename);// Era attachement
			}
            */
			response.reset();
			response.setContentType("application/octet-stream");
			response.addHeader("Content-disposition",
					"attachament; filename=\"" + filename);// Era attachement
			OutputStream os = response.getOutputStream();
			byte[] buffer = new byte[8192]; //Era 2048
		    int bytesRead = -1;

		    while ((bytesRead = is.read(buffer)) > 0) {
		       os.write(buffer, 0, bytesRead);
		    }

		 //   facesContext.getResponseComplete();

		
			
		    os.flush();
			is.close();
		    os.close();
				
		
			if (excluir == true) {
				arquivo1 = new File(arq);
			     
				arquivo1.delete();
			}
			
		
			 facesContext.responseComplete();
		
		} catch (Exception e) {
			try {
				e.printStackTrace();
				is.close();
			} catch (Exception ex) {
				//ex.printStackTrace();
				System.out.print("Erro de paginação...");
			}
		}
	}
}
