package br.adv.cra.utilitarios;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringTokenizer;

@SuppressWarnings("unused")
public class CopiaArqDestino {

	   
	@SuppressWarnings("resource")
	public static void CopiaFromDestino(File source, File destination) throws IOException {
		    source.getAbsoluteFile();
	        if (destination.exists())
	            destination.delete();
	        FileChannel sourceChannel = null;
	        FileChannel destinationChannel = null;
	        try {
	            sourceChannel = new FileInputStream(source).getChannel();
	            destinationChannel = new FileOutputStream(destination).getChannel();
	            sourceChannel.transferTo(0, sourceChannel.size(),
	                    destinationChannel);
	        } finally {
	            if (sourceChannel != null && sourceChannel.isOpen())
	                sourceChannel.close();
	            if (destinationChannel != null && destinationChannel.isOpen())
	                destinationChannel.close();
	       }
	   }
	   
}