package br.adv.cra.utilitarios;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TesteArquivo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.print("Copiando os arquivos de origem...");
		//String teste ="\\\\192.168.0.20\\cra\Publico\\TREINAMENTO\28062018\\MOV00636.AVI";
		Path source = Paths.get("\\\\192.168.0.20\\cra\\Pulico\\TREINAMENTO\\28062018\\MOV00636.AVI");
		Path destination = Paths.get("C:\\temp\\MOV00636.AVI");
		try {
			Files.copy(source, destination);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		System.out.println("Copiou..");
		
	}

}
