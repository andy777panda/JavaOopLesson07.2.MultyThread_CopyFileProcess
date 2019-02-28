package net.ukr.andy777;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileOperation {
	private File fileIn;
	private File fileOut;
	private byte[][] bufer = { null, null };
	private int[] readByte = { 0, 0 };
	private long copyBytes = 0;
	private boolean[] isComplite = { false, false };
	private final int BUFER_SIZE = 1024 * 1024;

	public FileOperation(File fileIn, File fileOut) {
		super();
		this.fileIn = fileIn;
		this.fileOut = fileOut;
	}

	public FileOperation() {
		super();
	}

	public synchronized void readFile() throws IOException {
		if (fileIn.exists() == false) {
			throw new FileNotFoundException();
		} else {
			// jdk1.6
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(fileIn);
				for (; !isComplite[0];) {
					for (; bufer[0] != null;) {
						try {
							wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					readByte[0] = fis.read(bufer[0] = new byte[BUFER_SIZE]);
					notifyAll();
					if (readByte[0] == -1)
						isComplite[0] = true;
				}
				System.out.println("1.read file - stop");
			} catch (IOException e) {
				throw e;
			} finally {
				try {
					if (fis != null)
						fis.close();
				} catch (IOException e) {
					System.out.println("Error close file" + fileIn.getName());
				}
			}

			// jdk1.7
			// try (FileInputStream fis = new FileInputStream(in);
			// FileOutputStream fos = new FileOutputStream(out)) {
			// for (; (readByte = fis.read(bufer)) > 0;) {
			// fos.write(bufer, 0, readByte);
			// }
			// } catch (IOException e) {
			// throw e;
			// }
		}
	}

	
	public synchronized void writeFile() throws IOException {
		// jdk1.6
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fileOut);
			for (; readByte[0] != -1;) {
				for (; bufer[0] == null;) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (!isComplite[0]) {
					bufer[1] = bufer[0].clone();
					readByte[1] = readByte[0];
					bufer[0] = null;
					readByte[0] = 0;
					notifyAll();
					fos.write(bufer[1], 0, readByte[1]);
				}
			}
			System.out.println("2.write file - stop");
			isComplite[1] = true;
			notify();
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				System.out.println("Error close file" + fileOut.getName());
			}
		}
	}

	
	public synchronized void percentCompute() throws IOException {
		for (; readByte[0] != -1;) {
			for (; bufer[1] == null && !isComplite[1];) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (!isComplite[1]) {
				copyBytes += readByte[1];
				readByte[1] = 0;
				bufer[1] = null;
				notifyAll();
				System.out
						.println(String.format("%.2f", (double) (copyBytes) / (double) (fileIn.length()) * 100) + "%");
			}
		}
		System.out.println("3.compute - stop");
	}
}