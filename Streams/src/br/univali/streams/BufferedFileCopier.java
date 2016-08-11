package br.univali.streams;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BufferedFileCopier implements FileCopier {
	private int bufferSize;

	public BufferedFileCopier(int bufferSize) {
		this.bufferSize = bufferSize;
		
	}
	
	@Override
	public void copy(File from, File to) throws IOException {
		InputStream in = new BufferedInputStream(new FileInputStream(from), bufferSize);
		OutputStream out = new BufferedOutputStream(new FileOutputStream(to), bufferSize);
		
		byte[] buffer = new byte[bufferSize];
		int bytesRead;
		
		do {
			bytesRead = in.read(buffer, 0, bufferSize); //Leitura
			if (bytesRead > 0) {
				out.write(buffer, 0, bytesRead); //Escrita
			}
		} while (bytesRead != -1);
		
		in.close();
		out.close();
	}
}