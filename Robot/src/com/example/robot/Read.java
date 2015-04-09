package com.example.robot;

import android.widget.TextView;

public class Read implements Runnable {

	private Controller controller;

	public Read(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void run() {
		// while(true){
		String string = MainActivity
				.comReadWrite(new byte[] { 'q', '\r', '\n' });
		string = string.trim();
		String[] arr = string.split(" ");
		int[] sensor = new int[arr.length];
		
		/* cast form hexa to dezimal 2 -> first two indices are ' '*/
		for (int i = 0; i < arr.length; i++) {
			System.out.println(sensor[i]);
			/*Substring because of 0x...*/
			try {
			sensor[i] = Integer.parseInt(arr[i].substring(2), 16);
			} catch (Exception e ) {
				
			}
		}
System.out.println(sensor[2]);
		
//	int links = sensor[2];
//	int rechts = sensor[4];
//	int mitte = sensor[3];
//	
//	System.out.println(links + " " + mitte +" " + rechts);
//
//		if (links != 0 || rechts != 0 || mitte != 0) {
//			/* stop vehicle */
//			//MainActivity.comReadWrite(new byte[] { 's', '\r', '\n' });
//			//controller.setStatus(true);
//			System.out.println("stop");
//		}

		/*
		 * try { Thread.sleep(5000); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 * MainActivity.comReadWrite(new byte[] { 's', '\r', '\n' });
		 * 
		 * controller.setStatus(true); // }
		 */

	}

}
