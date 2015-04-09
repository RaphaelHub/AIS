package com.example.robot;

import java.util.concurrent.TimeUnit;

import jp.ksksue.driver.serial.FTDriver;
import android.app.Activity;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static FTDriver com;
	public static TextView textLog;
	private Button buttonW;
	private Button buttonS;
	private Button buttonA;
	private Button buttonD;
	private Button buttonX;
	private Button buttonMinus;
	private Button buttonPlus;
	private Button buttonRectangle;
	private Button disc;
	private Button buttonDrive100;
	private Button buttonTurn90;
	private Button buttonSensors;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		buttonW = (Button) findViewById(R.id.button1);
		buttonW.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// robotSetVelocity(b,b);
				comWrite(new byte[] { 'w', '\r', '\n' });
			}
		});
		
		disc=(Button) findViewById(R.id.button9);
		disc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				disconnect();
			}
		});

		buttonS = (Button) findViewById(R.id.button4);
		buttonS.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				comWrite(new byte[] { 's', '\r', '\n' });
			}
		});

		buttonA = (Button) findViewById(R.id.button2);
		buttonA.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				comWrite(new byte[] { 'a', '\r', '\n' });
			}
		});

		buttonD = (Button) findViewById(R.id.button3);
		buttonD.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				comWrite(new byte[] { 'd', '\r', '\n' });
			}
		});

		buttonMinus = (Button) findViewById(R.id.button7);
		buttonMinus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				comWrite(new byte[] { '-', '\r', '\n' });
			}
		});

		buttonPlus = (Button) findViewById(R.id.button6);
		buttonPlus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				comWrite(new byte[] { '+', '\r', '\n' });
			}
		});

		buttonX = (Button) findViewById(R.id.button5);
		buttonX.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				comWrite(new byte[] { 'x', '\r', '\n' });
			}
		});
		
		
		buttonDrive100 = (Button) findViewById(R.id.button10);
		buttonDrive100.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				try {
					textLog.append("drive100");
					driveCM(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		buttonTurn90 = (Button) findViewById(R.id.button11);
		buttonTurn90.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				try {
					driveANGLE(90);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		buttonSensors = (Button) findViewById(R.id.button12);
		buttonSensors.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				textLog.append(comReadWrite(new byte[] { 'q', '\r', '\n' }));
			}
		});

		buttonRectangle = (Button) findViewById(R.id.button8);
		buttonRectangle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Controller con=new Controller();
				Thread c=new Thread(con);
				c.start();
			}
		});

		textLog = (TextView) findViewById(R.id.textView1);
		com = new FTDriver((UsbManager) getSystemService(USB_SERVICE));
		com.begin(9600);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public static void comWrite(byte[] data) {
		if (com.isConnected()) {
			com.write(data);
		} else {
			textLog.append("not connected\n");
		}
	}

	public static String comRead() {
		String s = "";
		int i = 0;
		int n = 0;
		while (i < 3 || n > 0) {
			byte[] buffer = new byte[256];
			n = com.read(buffer);
			s += new String(buffer, 0, n);
			i++;
		}
		return s;
	}

	public static String comReadWrite(byte[] data) {
		com.write(data);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) { // ignore
		}
		return comRead();
	}

	public void robotSetVelocity(byte left, byte right) {
		comReadWrite(new byte[] { 'i', left, right, '\r', '\n' });
	}

	public void robotSetLeds(byte red, byte blue) {
		comReadWrite(new byte[] { 'u', red, blue, '\r', '\n' });
	}

	public void robotSetBar(byte value) {
		comReadWrite(new byte[] { 'o', value, '\r', '\n' });
	}
	
	public static void robotDrive(byte distance_cm) {
		comReadWrite(
				new byte[] { 'k', distance_cm, '\r', '\n' }
				);
	}

	public static void robotTurn(byte degree) {
		comReadWrite(
				new byte[] { 'l', degree, '\r', '\n' }
				);
	}
	
	public void disconnect() {
	       com.end();
	       if (!com.isConnected()) {
	           textLog.append("disconnected");
	       }

	   }
	
	public static void driveFromTo(double fromX, double fromY, double fromAngle, double toX, double toY, double toAngle) throws InterruptedException{
		double c = Math.sqrt(Math.pow((fromX-toX), 2)+Math.pow((fromY-toY),2));
		double a = Math.abs(fromX-toX);
		double b = Math.abs(fromY-toY);
		double alpha = Math.round(Math.toDegrees(Math.acos(a/c)));
		double beta = 90-alpha;
		double toTurn1 = alpha-fromAngle;
		double toTurn2 = toAngle-alpha;
		
		if(toTurn1<0){
			toTurn1=360+toTurn1;
		}
		
		if(toTurn2<0){
			toTurn2=360+toTurn1;
		}
		
		turnLongDegree((int)toTurn1);
		driveLongCm((int)c);
		turnLongDegree((int)toTurn2);
	}
	
	public static void driveLongCm(int cm) throws InterruptedException{
		double percent= (double)100/72;//replace 72 with degrees
		double newCM=cm*percent;
		double millisecondsPerCM=0.0799*1000;//stop time and replace
		
		int togo=(int) newCM;
		while(cm>=127){
			driveCm2(127,(int)(127*millisecondsPerCM));
			togo-=127;
		}
		driveCm2(togo,(int)(togo*millisecondsPerCM));
	}
	
	
	public static void turnLongDegree(int degree) throws InterruptedException{
		double percent= (double)90/78;//replace 105 with degrees
		double newAngle=degree*percent;
		double millisecondsPerDegree=(1.6/90)*1000;//stop time and replace
		
		int togo=(int) newAngle;
		while(newAngle>=127){
			turnDegree2(127,(int)(127*millisecondsPerDegree));
			togo-=127;
		}
		driveCm2(togo,(int)(togo*millisecondsPerDegree));
	}
	
	public static void driveCm2(int cm, int time)throws InterruptedException{
		robotDrive((byte) cm);
		Thread.sleep((int)time);
	}
	
	public static void turnDegree2(int degree, int time)throws InterruptedException{
		robotTurn((byte) degree);
		Thread.sleep((int)time);
	}
	
	public static void driveCM(int cm) throws InterruptedException{
		double percent= (double)100/72;//replace 104 with degrees
		double newCM=cm*percent;
		double millisecondsPerCM=0.0799*1000;//stop time and replace
		double time=cm*millisecondsPerCM;
		robotDrive((byte) newCM);
		Thread.sleep((int)time);
	}
	
	public static void driveANGLE(int degrees) throws InterruptedException{
		double percent= (double)90/78;//replace 105 with degrees
		double newANGLE=degrees*percent;
		double millisecondsPerDegree=(1.6/90)*1000;//stop time and replace
		double time=degrees*millisecondsPerDegree;
		robotTurn((byte) newANGLE);
		Thread.sleep((int)time);
	}

}
