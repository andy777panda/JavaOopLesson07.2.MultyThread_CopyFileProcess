package net.ukr.andy777;

import java.io.File;
import java.io.IOException;

public class ReadFile implements Runnable {
	private FileOperation fo;

	public ReadFile(FileOperation fo) {
		super();
		this.fo = fo;
	}

	@Override
	public void run() {
		try {
			fo.readFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
