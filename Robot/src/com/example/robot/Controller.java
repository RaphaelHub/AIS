package com.example.robot;

import android.widget.TextView;

public class Controller implements Runnable{

	public boolean stop = false;
	public boolean turnLock = false;

	public int left;
	public int right;
	public int middle;
	
	public boolean isStopped() {
		return stop;
	}
	
	public void setStop(boolean newStop) {
		stop = newStop;
	}
	
	public boolean isTurnLock() {
		return turnLock;
	}

	public void setTurnLock(boolean turnLock) {
		this.turnLock = turnLock;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}

	public int getMiddle() {
		return middle;
	}

	public void setMiddle(int middle) {
		this.middle = middle;
	}

	
	public Controller() {
	}

	@Override
	public void run() {
		Drive driveThread=new Drive(this);
		Read stopThread=new Read(this);
		Thread t1=new Thread(driveThread);
		Thread t2=new Thread(stopThread);
		t1.start();
		t2.start();
		while(true) {
		System.out.println("hallo  " +  isStopped()+ "   " + isTurnLock());
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
}
