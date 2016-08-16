package br.univali.streams;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StandardFileCopier implements FileCopier {

	@Override
	public void copy(File from, File to) throws IOException {
		InputStream in = new FileInputStream(from);
		OutputStream out = new FileOutputStream(to);
		
		int value;
		do {
			value = in.read();
			if (value != -1) out.write(value);
		} while (value != -1);
		
		in.close();
		out.close();
	}

}
