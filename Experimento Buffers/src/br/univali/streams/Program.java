package br.univali.streams;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

public class Program {
	private static final int[] sizes = {64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768};
	
	/**
	 * Cria 8 arquivos de teste, iniciando com tamanho de 64kB
	 * e dobrando a cada novo arquivo. 
	 */
	private void createFiles() {
		try {
			for (int i = 0; i < sizes.length; i++) {
				File f = new File((i + 1) + ".test");
				f.createNewFile();
				
				FileOutputStream writer = new FileOutputStream(f);

				for (int j = 0; j < sizes[i] * 1024; j++) {
					writer.write((int) (Math.random() * 255));
				}
				
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private long copy(InputStream in, OutputStream out) throws IOException {
		long time = System.currentTimeMillis();
		
		int value;
		do {
			value = in.read();
			if (value != -1) out.write(value);
		} while (value != -1);
		
		in.close();
		out.close();
		
		return System.currentTimeMillis() - time;
	}
	
	private long runStandardExperiment(File from, File to) throws IOException {
		InputStream in = new FileInputStream(from);
		OutputStream out = new FileOutputStream(to);
		return copy(in, out);
	}
	
	private long runBufferedExperiment(File from, File to, int bufferSize) throws IOException {
		InputStream in = new BufferedInputStream(new FileInputStream(from), bufferSize);
		OutputStream out = new BufferedOutputStream(new FileOutputStream(to), bufferSize);
		return copy(in, out);
	}
	
	public Program() {
		try {
			//createFiles();
			
			File results = new File("results.csv");
			results.createNewFile();
			BufferedWriter writer = Files.newBufferedWriter(results.toPath());
			writer.write("fileSize,single,buffer1k,buffer8k,buffer32k\n");
			
			for (int k = 0; k < 50; k++) {
				for (int i = 0; i < sizes.length; i++) {
					File origin = new File((i + 1) + ".test");
					File target = new File("target" + (i + 1) + ".test");
					target.createNewFile();
					
					long single = runStandardExperiment(origin, target);
					long buffer1 = runBufferedExperiment(origin, target, 1024);
					long buffer8 = runBufferedExperiment(origin, target, 8192);
					long buffer32 = runBufferedExperiment(origin, target, 32768);
					
					writer.write(String.format("%d,%d,%d,%d,%d\n", sizes[i], single, buffer1, buffer8, buffer32));
					writer.flush();
				}
			}
			
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		new Program();
	}

}
