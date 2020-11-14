package br.adv.cra.utilitarios;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Html {
	public static void main(String[] args) throws IOException {

		// Variaveis que receberao os dados do processo;

		String sourceLine;
		String content = "";
		String numprocesso = "";
		String autor = "";
		String reu = "";
		String comarca = "";
		String juizado = "";
		String assunto = "";

		// The URL address of the page to open.
		URL address = new URL(
				"http://www4.tjrj.jus.br/consultaProcessoWebV2/consultaProc.do?v=2&FLAGNOME=&back=1&tipoConsulta=publica&numProcesso=2012.001.183163-3");

		// Open the address and create a BufferedReader with the source code.
		InputStreamReader pageInput = new InputStreamReader(
				address.openStream());
		BufferedReader source = new BufferedReader(pageInput);

		// Append each new HTML line into one string. Add a tab character.
		while ((sourceLine = source.readLine()) != null)
			content += sourceLine + "\t";

		// Remove style tags & inclusive content
		Pattern style = Pattern.compile("<style.*?>.*?</style>");
		Matcher mstyle = style.matcher(content);
		while (mstyle.find())
			content = mstyle.replaceAll("");

		// Remove script tags & inclusive content
		Pattern script = Pattern.compile("<script.*?>.*?</script>");
		Matcher mscript = script.matcher(content);
		while (mscript.find())
			content = mscript.replaceAll("");

		// Remove primary HTML tags
		Pattern tag = Pattern.compile("<.*?>");
		Matcher mtag = tag.matcher(content);
		while (mtag.find())
			content = mtag.replaceAll("");

		// Remove comment tags & inclusive content
		Pattern comment = Pattern.compile("<!--.*?-->");
		Matcher mcomment = comment.matcher(content);
		while (mcomment.find())
			content = mcomment.replaceAll("");

		// Remove special characters, such as &nbsp;
		Pattern sChar = Pattern.compile("&.*?;");
		Matcher msChar = sChar.matcher(content);
		while (msChar.find())
			content = msChar.replaceAll("");

		// Remove the tab characters. Replace with new line characters.

		Pattern nProcesso = Pattern.compile("<br/><h2>Processo N<sup>o</sup>");
		Matcher mnProcesso = nProcesso.matcher(content);
		while (mnProcesso.find())
			content = mnProcesso.replaceAll("\n");
		numprocesso = content.trim();

		Pattern nAutor = Pattern.compile("Autor");
		Matcher mnAutor = nAutor.matcher(content);
		while (mnAutor.find())
			content = mnProcesso.replaceAll("\n");
		autor = content.trim();

		Pattern nLineChar = Pattern.compile("\t+");
		Matcher mnLine = nLineChar.matcher(content);
		while (mnLine.find())
			content = mnLine.replaceAll("\n");

		// Print the clean content & close the Readers
		System.out.println(content);

		FileWriter fw = new FileWriter("c:\\processo.txt");
		StringWriter sw = new StringWriter();
		sw.write(content);
		fw.write(sw.toString());
		fw.close();

		pageInput.close();
		source.close();
	}
}