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
			try {
				if (!controller.isStopped()) {
					String string1;
					do {
						string1 = MainActivity.comReadWrite(new byte[] { 'q',
								'\r', '\n' });
						string1 = string1.replaceAll("\\p{C}", "");
						string1 = string1.replaceAll("command", "");
						string1 = string1.replaceAll("execution", "");
						string1 = string1.replaceAll("ecution", "");
						string1 = string1.replaceAll("marked", "");
						string1 = string1.replaceAll("sensor", "");
						string1 = string1.replaceAll(":", "");
						string1 = string1.replaceAll(" ", "");
					} while (string1.length() == 0);

					String[] arr = string1.split("0x");
					int[] sensor = new int[arr.length];

					for (int i = 1; i < arr.length; i++) {
						sensor[i - 1] = Integer.parseInt(arr[i], 16);
					}

					int mitte = sensor[6];
					int links = sensor[2];
					int rechts = sensor[3];

					if (mitte <= 25 || rechts <= 15 || links <= 15) {
						MainActivity
								.comReadWrite(new byte[] { 's', '\r', '\n' });
						controller.setStop(true);
					}

					Thread.sleep(100);
				}
				else{
					Thread.sleep(100);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
