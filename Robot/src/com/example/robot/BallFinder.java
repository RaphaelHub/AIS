package com.example.robot;

import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;


public class BallFinder /*implements Runnable*/{

	private ColorBlobDetector mDetector;
	private double hue;
	private double saturation;
	private double value;
	private Mat mSpectrum;
	private Size SPECTRUM_SIZE;
	
	
	public BallFinder(int ball) {
		switch(ball) {
		//Red ball
		case 0 : 	this.hue = 252; this.saturation = 198; this.value = 181; 
				break;
		//Green ball		
		case 1: 	this.hue = 103; this.saturation = 255; this.value = 95; 
		break;		
		}
		SPECTRUM_SIZE = new Size(200, 64);
		mSpectrum = new Mat();

	}

	public Point getPoint() {
		return mDetector.getCoordinates();
	}

	//@Override
	public void run() {
		mDetector = new ColorBlobDetector();
		mDetector.setHsvColor(new Scalar(value, saturation, hue, 0));
		Imgproc.resize(mDetector.getSpectrum(), mSpectrum, SPECTRUM_SIZE);
		
	}
	
	
	
}
