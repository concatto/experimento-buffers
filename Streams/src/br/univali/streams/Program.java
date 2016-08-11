package br.univali.streams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Program {
	/**
	 * Cria 8 arquivos de teste, iniciando com tamanho de 64kB
	 * e dobrando a cada novo arquivo. 
	 */
	private void createFiles() {
		try {
			for (int i = 0; i < 8; i++) {
				File f = new File((i + 1) + ".test");
				f.createNewFile();
				
				FileOutputStream writer = new FileOutputStream(f);

				for (int j = 0; j < Math.pow(2, 6 + i) * 1024; j++) {
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
		File origin = new File("2.test");
		File target = new File("target2.test");
		
		try {
			target.createNewFile();
			
			System.out.println(runExperiment(new StandardFileCopier(), origin, target));
			System.out.println(runExperiment(new BufferedFileCopier(8192), origin, target));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		new Program();
	}

}
