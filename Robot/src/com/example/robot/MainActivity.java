package com.example.robot;

import java.util.concurrent.TimeUnit;

import jp.ksksue.driver.serial.FTDriver;
import android.app.Activity;
import android.graphics.Bitmap.Config;
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
	private Controller controller;
	public static TextView textLog;
	private Button buttonW;
	private Button buttonA;
	private Button buttonS;
	private Button buttonD;
	private Button buttonX;
	private Button buttonMinus;
	private Button buttonPlus;
	private Button buttonSensors;
	private Button disc;
	private Button trys;

	public static byte velocity = 20;
	public static byte neg_velocity = -20;
	public static double x_now = 0;
	public static double y_now = 0;
	public static double theta_now = 0;

//	public MainActivity(Controller controll) {
//		controller = controll;
//	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textLog = (TextView) findViewById(R.id.textView1);
		com = new FTDriver((UsbManager) getSystemService(USB_SERVICE));
		com.begin(9600);

		buttonW = (Button) findViewById(R.id.button1);
		buttonW.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				comWrite(new byte[] { 'w', '\r', '\n' });
			}
		});

		buttonA = (Button) findViewById(R.id.button2);
		buttonA.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				comWrite(new byte[] { 'a', '\r', '\n' });
			}
		});

		buttonS = (Button) findViewById(R.id.button3);
		buttonS.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				comWrite(new byte[] { 's', '\r', '\n' });
			}
		});

		buttonD = (Button) findViewById(R.id.button4);
		buttonD.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				comWrite(new byte[] { 'd', '\r', '\n' });
			}
		});

		buttonX = (Button) findViewById(R.id.button5);
		buttonX.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				comWrite(new byte[] { 'x', '\r', '\n' });
			}
		});

		buttonMinus = (Button) findViewById(R.id.button8);
		buttonMinus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				comWrite(new byte[] { '-', '\r', '\n' });
			}
		});

		buttonPlus = (Button) findViewById(R.id.button9);
		buttonPlus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				comWrite(new byte[] { '+', '\r', '\n' });
			}
		});

		buttonSensors = (Button) findViewById(R.id.button7);
		buttonSensors.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String x = comReadWrite(new byte[] { 'q', '\r', '\n' });
				textLog.append(x);
				System.out.println(x);

			}
		});

		disc = (Button) findViewById(R.id.button6);
		disc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				disconnect();
			}
		});

		trys = (Button) findViewById(R.id.button10);
		trys.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Controller contr = new Controller();
				contr.run();

				// Stopwatch stopwatch1 = new Stopwatch();
				// double time1 = stopwatch1.elapsedTime();
				// System.out.println("time1: " + time1);

				// driveFromTo(x_now,y_now,theta_now, -50, -50, 90);

			}
		});
	}

	// ***************************** END BUTTONS
	// **********************************************
	// ***************************** START BASIC FUNCTIONS
	// *************************************

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

	public static void robotSetVelocity(byte left, byte right) {
		comReadWrite(new byte[] { 'i', left, right, '\r', '\n' });
	}

	public void robotSetLeds(byte red, byte blue) {
		comReadWrite(new byte[] { 'u', red, blue, '\r', '\n' });
	}

	public void robotSetBar(byte value) {
		comReadWrite(new byte[] { 'o', value, '\r', '\n' });
	}

	public static void robotDrive(byte distance_cm) {
		comReadWrite(new byte[] { 'k', distance_cm, '\r', '\n' });
	}

	public static void robotTurn(byte degree) {
		comReadWrite(new byte[] { 'l', degree, '\r', '\n' });
	}

	public void disconnect() {
		com.end();
		if (!com.isConnected()) {
			textLog.append("disconnected");
		}

	}

	// ***************************** END BASIC FUNCTIONS
	// *************************************
	// ***************************** START SPECIFIC FUNCTIONS
	// *************************************

	public static void drive(int cm) {
		try {
			robotSetVelocity(velocity, velocity);
			int time = (int) ((double) cm * 1000 / 19.35);
			Thread.sleep(time);
			comWrite(new byte[] { 's', '\r', '\n' });

			double dX = cm * Math.cos(Math.toRadians(theta_now));
			double dY = cm * Math.sin(Math.toRadians(theta_now));
			x_now += dX;
			y_now += dY;
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Positon: X: " + x_now + "  Y: " + y_now + "  Theta: " + theta_now);
	}

	public static void turn(char dir, int degrees) {
		try {
			if (dir == 'r') {
				robotSetVelocity(velocity, neg_velocity);
				theta_now -= degrees;
			}
			if (dir == 'l') {
				robotSetVelocity(neg_velocity, velocity);
				theta_now += degrees;
			}
			int time = (int) ((double) degrees * 1000 / (118));
			Thread.sleep(time);
			comWrite(new byte[] { 's', '\r', '\n' });
			if (theta_now >= 360) {
				theta_now -= 360;
			}
			if (theta_now <= -360) {
				theta_now += 360;
			}
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Positon: X: " + x_now + "  Y: " + y_now + "  Theta: " + theta_now);

	}

	public static void driveFromTo(double fromX, double fromY, double fromAngle,
			double toX, double toY, double toAngle) {
		double c = Math.sqrt(Math.pow((fromX - toX), 2)
				+ Math.pow((fromY - toY), 2));
		double a = Math.abs(fromX - toX);
		double b = Math.abs(fromY - toY);
		double alpha = Math.round(Math.toDegrees(Math.acos(a / c)));
		double beta = 90 - alpha;
		double toTurn1 = alpha - fromAngle;
		double toTurn2 = toAngle - alpha;
		System.out.println("halo1");
		if (toTurn1 < 0) {
			turn('r', (int) toTurn1);
		} else {
			turn('l', (int) toTurn1);
		}
		System.out.println("halo2");
		drive((int) c);
		System.out.println("halo3");
		if (toTurn2 < 0) {
			turn('r', (int) toTurn1);
		} else {
			turn('r', (int) toTurn2);
		}
		System.out.println("halo4");
	}
	
	public static boolean ReadSensorsMain(){
		return false;}
}
