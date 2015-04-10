package com.example.robot;

import android.widget.TextView;

public class Read implements Runnable {

	private Controller controller;

	public Read(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void run() {
		while (true) {
			String string;
			do {
				string = MainActivity
						.comReadWrite(new byte[] { 'q', '\r', '\n' });
				string = string.trim();

				string = string.replaceAll("\\p{C}", "");
				string = string.replaceAll("command", "");
				string = string.replaceAll("execution", "");
				string = string.replaceAll("marked", "");
				
			
			} while (!string.contains("sensor:"));
			System.out.println("sensor: string: " + string + " end");
			String[] arr = string.split(" ");
			int[] sensor = new int[arr.length];

			/* cast form hexa to dezimal 2 -> first two indices are ' ' */
			for (int i = 3; i < arr.length; i++) {
				//System.out.println("sensor: " + i + " " + arr[i]);
				/* Substring because of 0x... */

				sensor[i] = Integer.parseInt(arr[i].substring(2), 16);
				//System.out.println("sensor: dez: " + i + " " + sensor[i]);

			}
			System.out.println("sensor: " + sensor[9]);

			int links = sensor[2];
			int rechts = sensor[4];
			int mitte = sensor[9];

		//	System.out.println("sensor: " + links + " " + mitte + " " + rechts);

			if (mitte < 35) {
				/* stop vehicle */
				 MainActivity.comReadWrite(new byte[] { 's', '\r', '\n' });
				 controller.setStatus(true);
				System.out.println("sensor: stop");
				return;
			}

//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//			}

			/*
			 * try { Thread.sleep(5000); } catch (InterruptedException e) { //
			 * TODO Auto-generated catch block e.printStackTrace(); }
			 * MainActivity.comReadWrite(new byte[] { 's', '\r', '\n' });
			 * 
			 * controller.setStatus(true); // }
			 */
		}
	}

}
