package org.opencv.samples.colorblobdetect;

public class Stopwatch {

	private/* final */long start;

	// public Stopwatch() {
	// start = System.currentTimeMillis();
	// }

	public void start() {
		start = System.currentTimeMillis();
	}

	public double elapsedTime() {
		long now = System.currentTimeMillis();
		return (now - start) / 1000.0;
	}

}
