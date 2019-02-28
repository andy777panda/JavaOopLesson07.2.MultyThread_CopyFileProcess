package net.ukr.andy777;

import java.io.IOException;

public class PercentCompute implements Runnable {
	private FileOperation fo;

	public PercentCompute(FileOperation fo) {
		super();
		this.fo = fo;
	}

	@Override
	public void run() {
		try {
			fo.percentCompute();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
