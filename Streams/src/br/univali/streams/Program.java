package br.univali.streams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Program {
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
	
	public Program() {
		
	}
	
	
	public static void main(String[] args) {
		new Program();
	}

}
