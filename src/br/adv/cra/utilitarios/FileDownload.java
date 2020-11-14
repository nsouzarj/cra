package br.adv.cra.utilitarios;

import java.io.InputStream;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

public class FileDownload {
	private StreamedContent file;

	public FileDownload(String caminho) {
		InputStream stream = this.getClass().getResourceAsStream(caminho);
		file = new DefaultStreamedContent(stream, "application/pdf",
				"downloaded_file.pdf");
	}

	public StreamedContent getFile() {
		return this.file;
	}

}
