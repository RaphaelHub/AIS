package com.example.robot;

public class Controller implements Runnable{

	@Override
	public void run() {
		Drive driveThread=new Drive();
		Read stopThread=new Read();
		Thread t1=new Thread(driveThread);
		Thread t2=new Thread(stopThread);
		t1.start();
		t2.start();
		
	}

}
