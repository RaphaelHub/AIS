package com.example.robot;

public class Read implements Runnable{

	@Override
	public void run() {
		if(true){
			String string= MainActivity.comReadWrite(new byte[]{'q','\r', '\n'});
			String[] arr=string.split(" ");
			String links=arr[2];
			String rechts=arr[4];		
			String mitte=arr[3];
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			MainActivity.comWrite(new byte[] { 's', '\r', '\n' });
		}
		
	}

}
