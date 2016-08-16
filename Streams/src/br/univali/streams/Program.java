package br.univali.streams;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
	
	private long runExperiment(FileCopier copier, File from, File to) throws IOException {
		long time = System.currentTimeMillis();
	
		copier.copy(from, to);
		
		return System.currentTimeMillis() - time;
	}
	
	public Program() {
		try {
			createFiles();
			
			File results = new File("results2.csv");
			results.createNewFile();
			BufferedWriter writer = Files.newBufferedWriter(results.toPath());
			writer.write("fileSize,single,buffer1k,buffer8k,buffer32k\n");
			
			for (int k = 0; k < 50; k++) {
				for (int i = 0; i < sizes.length; i++) {
					File origin = new File((i + 1) + ".test");
					File target = new File("target" + (i + 1) + ".test");
					target.createNewFile();
					
					long single = runExperiment(new StandardFileCopier(), origin, target);
					long buffer1 = runExperiment(new BufferedFileCopier(1024), origin, target);
					long buffer8 = runExperiment(new BufferedFileCopier(8192), origin, target);
					long buffer32 = runExperiment(new BufferedFileCopier(32768), origin, target);
					
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
