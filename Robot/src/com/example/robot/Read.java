package com.example.robot;

import android.widget.SlidingDrawer;
import android.widget.TextView;

public class Read implements Runnable {

	private Controller controller;

	public Read(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void run() {
		// while(true){
		MainActivity
				.comReadWrite(new byte[] { 'q', '\r', '\n' });
		//String[] arr = string.split(" ");
		// MainActivity.textLog.append(arr.length+ "+");
		// textLog.append(string);
		//String links = arr[2];
		//String rechts = arr[4];
		//String mitte = arr[3];

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MainActivity.comReadWrite(new byte[] { 's', '\r', '\n' });

		controller.setStatus(true);
		// }

	}

}
