package org.opencv.samples.colorblobdetect;



public class Read implements Runnable {

	@Override
	public void run() {
		while (!(ColorBlobDetectionActivity.x_now == 130 && ColorBlobDetectionActivity.y_now == 250 && ColorBlobDetectionActivity.theta_now == 0)) {
			try {
				if (!ColorBlobDetectionActivity.isStopped()) {
					Thread.sleep(400);
					String string1;
					do {
						string1 = ColorBlobDetectionActivity.comReadWrite(new byte[] { 'q',
								'\r', '\n' });
						string1 = string1.replaceAll("\\p{C}", "");
						string1 = string1.replaceAll("command", "");
						string1 = string1.replaceAll("execution", "");
						string1 = string1.replaceAll("ecution", "");
						string1 = string1.replaceAll("marked", "");
						string1 = string1.replaceAll("sensor", "");
						string1 = string1.replaceAll(":", "");
						string1 = string1.replaceAll(" ", "");
					} while (string1.length() == 0 && string1.contains("0x"));

					String[] arr = string1.split("0x");
					int[] sensor = new int[arr.length];

					for (int i = 1; i < arr.length; i++) {
						sensor[i - 1] = Integer.parseInt(arr[i], 16);
					}

					//int mitte = sensor[6];
					int links = sensor[5];
					int rechts = sensor[6];

					if ((rechts <= 50 || links <= 30)/* && (rechts >= 35 || links >= 5)*/) {
						for (int i : sensor) {
							System.out.print(i + " ");
						}
						System.out.println();
						ColorBlobDetectionActivity
								.comReadWrite(new byte[] { 's', '\r', '\n' });
						ColorBlobDetectionActivity.setStop(true);
						ColorBlobDetectionActivity.stopStoptime(ColorBlobDetectionActivity.stopwatch);
					}

					Thread.sleep(500);
					//System.out.println("success");
				}
				else{
					Thread.sleep(100);
				}
			} catch (Exception e) {
				//e.printStackTrace();
				//System.out.println(e);
			}
		}
	}
}
