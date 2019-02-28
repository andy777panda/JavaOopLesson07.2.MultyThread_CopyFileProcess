package net.ukr.andy777;

/*
 Lesson07.2
 Реализуйте программу многопоточного копирования файла блоками с выводом прогресса на экран. 
 1й поток - считывает из файла
 2й поток - записывает в файл
 3й поток - считает % выполнения 
 */

import java.io.File;

public class Main {

	public static void main(String[] args) {
		long tstart, tend;

		tstart = System.currentTimeMillis();

		File fileIn = new File("sample.pdf");
		File fileOut = new File("sampleC.pdf");
		FileOperation fo = new FileOperation(fileIn, fileOut);

		Thread thrRead = new Thread(new ReadFile(fo));
		Thread thrCompute = new Thread(new PercentCompute(fo));
		Thread thrWrite = new Thread(new WriteFile(fo));

		thrRead.start();
		thrCompute.start();
		thrWrite.start();

		try {
			thrRead.join();
			thrCompute.join();
			thrWrite.join();
		} catch (InterruptedException e) {
			System.out.println(e);
		}

		tend = System.currentTimeMillis();
		System.out.println(" -- " + (tend - tstart) + " ms");
	}
}
