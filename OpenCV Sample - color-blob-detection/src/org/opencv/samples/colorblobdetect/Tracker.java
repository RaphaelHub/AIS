package org.opencv.samples.colorblobdetect;

import org.opencv.core.Point;

public class Tracker implements Runnable{

	private ColorBlobDetector mDetector;
	private boolean isStarted = false;
	private Point coordinates = null;

	
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (isStarted && mDetector.getCoordinates() != null) {
				System.out.println("Color: " + mDetector.getCoordinates());
				coordinates = mDetector.getCoordinates();
				mDetector.resetCoordinates();
			}
		}
	}


	public ColorBlobDetector getmDetector() {
		return mDetector;
	}

	public void setDetector(ColorBlobDetector mDetector) {
		this.mDetector = mDetector;
		isStarted = true;
		
	}


	public Point getCoordinates() {
		return coordinates;
	}
	

}
