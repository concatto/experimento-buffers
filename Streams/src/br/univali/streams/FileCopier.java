package br.univali.streams;

import java.io.File;
import java.io.IOException;

public interface FileCopier {
	void copy(File from, File to) throws IOException;
}
