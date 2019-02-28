package net.ukr.andy777;

import java.io.File;
import java.io.IOException;

public class WriteFile implements Runnable {
	private FileOperation fo;

	public WriteFile(FileOperation fo) {
		super();
		this.fo = fo;
	}

	@Override
	public void run() {
		try {
			fo.writeFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
