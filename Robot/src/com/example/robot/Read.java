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
			while (controller.isTurnLock() == false) {
				String string1;
				do {
					string1 = MainActivity.comReadWrite(new byte[] { 'q', '\r',
							'\n' });
					// System.out.println(string1);
					string1 = string1.replaceAll("\\p{C}", "");
					string1 = string1.replaceAll("command", "");
					string1 = string1.replaceAll("execution", "");
					string1 = string1.replaceAll("ecution", "");
					string1 = string1.replaceAll("marked", "");
					string1 = string1.replaceAll("sensor", "");
					string1 = string1.replaceAll(":", "");
					string1 = string1.replaceAll(" ", "");
				} while (string1.length() == 0);
				// System.out.println(string1);
				String[] arr = string1.split("0x");
				int[] sensor = new int[arr.length];
				for (int i = 1; i < arr.length; i++) {
					// System.out.println(arr[i]);
					sensor[i - 1] = Integer.parseInt(arr[i], 16);
				}

				System.out.println("2(links): " + sensor[2]);
				System.out.println("3(rechts): 		" + sensor[3]);
				System.out.println("6(mitte): 				" + sensor[6]);

				int mitte = sensor[6];
				int links = sensor[2];
				int rechts = sensor[3];
				if ((mitte <= 20 || rechts <= 10 || links <= 9) && !controller.isTurnLock()) {
					/* stop vehicle */
					MainActivity.comReadWrite(new byte[] { 's', '\r', '\n' });
					controller.setStop(true);
					System.out.println("sensor: stop");
//					try {
//						Thread.sleep(5000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
				}
//				try {
//					Thread.sleep(200);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
		}
	}

}
