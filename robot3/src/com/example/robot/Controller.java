package com.example.robot;

import android.widget.TextView;

public class Controller implements Runnable {

	public boolean stop = false;

	public boolean isStopped() {
		return stop;
	}

	public void setStop(boolean newStop) {
		stop = newStop;
	}

	public Controller() {
	}

	@Override
	public void run() {
		Drive driveThread = new Drive(this);
		Read stopThread = new Read(this);
		Thread t1 = new Thread(driveThread);
		Thread t2 = new Thread(stopThread);
		t1.start();
		t2.start();
	}
}
