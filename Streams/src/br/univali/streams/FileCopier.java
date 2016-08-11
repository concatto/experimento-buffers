package br.univali.streams;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileCopier {
	public FileCopier() {
		
	}
	
	public long copy(File from, File to) throws IOException {
		long start = System.currentTimeMillis();
		
		InputStream in = new FileInputStream(from);
		OutputStream out = new FileOutputStream(to);
		
		int value;
		do {
			value = in.read();
			out.write(value);
		} while (value != -1);
		
		in.close();
		out.close();
		
		return System.currentTimeMillis() - start;
	}
	
	public long bufferedCopy(File from, File to, int bufferSize) throws IOException {
		long start = System.currentTimeMillis();
		
		InputStream in = new BufferedInputStream(new FileInputStream(from), bufferSize);
		OutputStream out = new BufferedOutputStream(new FileOutputStream(to), bufferSize);
		
		byte[] buffer = new byte[bufferSize];
		int bytesRead;
		
		do {
			bytesRead = in.read(buffer, 0, bufferSize); //Leitura
			out.write(buffer, 0, bytesRead); //Escrita
		} while (bytesRead != -1);
		
		in.close();
		out.close();
		
		return System.currentTimeMillis() - start;		
	}
}
