package com.example.robot;

import java.util.concurrent.TimeUnit;

import jp.ksksue.driver.serial.FTDriver;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static FTDriver com;
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
	public static EditText xyz;
	

	public static byte velocity = 20;
	public static byte neg_velocity = -20;
	public static double x_now = 0;
	public static double y_now = 0;
	public static double theta_now = 0;

	public static boolean stopped = false;
	public static Stopwatch stopwatch = new Stopwatch();
	public static double stoptime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textLog = (TextView) findViewById(R.id.textView1);
		com = new FTDriver((UsbManager) getSystemService(USB_SERVICE));
		com.begin(9600);
		
		
		//xyz = (EditText) findViewById(R.id.editText1);
		
		
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
				setUp();
				//ReadSensorsMain();
				
//				robotSetVelocity(velocity, neg_velocity);
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				String x = comReadWrite(new byte[] { 's', '\r', '\n' });
				//drive(100, false);
				// MainActivity.drive(25);
				// MainActivity.turn(45);
				// MainActivity.drive(25);
				// MainActivity.turn(45);
				// MainActivity.drive(25);
				// MainActivity.turn(45);
				// MainActivity.drive(25);
				// MainActivity.turn(45);
				// MainActivity.drive(25);
				// MainActivity.turn(45);
				// MainActivity.drive(25);
				// MainActivity.turn(45);
				// MainActivity.drive(25);
				// MainActivity.turn(45);
				// MainActivity.drive(25);
				// MainActivity.turn(45);

				// driveFromTo(x_now,y_now,theta_now, 35, 0, 0);
				// driveFromTo(x_now,y_now,theta_now, 50, 50, 40);
				// driveFromTo(x_now,y_now,theta_now, 80, 0, 0);
				// driveFromTo(x_now,y_now,theta_now, 0, -50, 0);
				// driveFromTo(x_now,y_now,theta_now, 0, 0, 0);
				// driveFromTo(x_now,y_now,theta_now, -50, 10, 10);
				// driveFromTo(x_now,y_now,theta_now, 50, 50, -30);
				// driveFromTo(x_now,y_now,theta_now, -25, 0, 0);
				// driveFromTo(x_now,y_now,theta_now, 40, -50, 0);
				// driveFromTo(x_now,y_now,theta_now, 0, 0, 0);
				// driveFromTo(x_now,y_now,theta_now, 1, 0, 0);
				// driveFromTo(x_now,y_now,theta_now, 1, 5, 0);
				// driveFromTo(x_now,y_now,theta_now, 0, 0, 0);
				// driveFromTo(x_now,y_now,theta_now, 35, 0, 0);
				// driveFromTo(x_now,y_now,theta_now, 50, 50, 40);
				// driveFromTo(x_now,y_now,theta_now, 80, 0, 0);
				// driveFromTo(x_now,y_now,theta_now, 0, -50, 0);
				// driveFromTo(x_now,y_now,theta_now, 0, 0, 0);
				// driveFromTo(x_now,y_now,theta_now, -50, 10, 10);
				// driveFromTo(x_now,y_now,theta_now, 50, 50, -30);
				// driveFromTo(x_now,y_now,theta_now, -25, 0, 0);
				// driveFromTo(x_now,y_now,theta_now, 40, -50, 0);
				// driveFromTo(x_now,y_now,theta_now, 0, 0, 0);
				// driveFromTo(x_now,y_now,theta_now, 1, 0, 0);
				// driveFromTo(x_now,y_now,theta_now, 1, 5, 0);
				// driveFromTo(x_now,y_now,theta_now, 0, 0, 0);

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
		//System.out.println("comwrite");
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
		//System.out.println("comreadwrite " + (char)data[0]);

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

	public static boolean isStopped() {
		return stopped;
	}

	public static void setStop(boolean bool) {
		stopped = bool;
	}

	public static double getStopime() {
		return stoptime;
	}

	public static void stopStoptime(Stopwatch stoptime1) {
		stoptime = stopwatch.elapsedTime();
	}

	// ***************************** END BASIC FUNCTIONS
	// *************************************
	// ***************************** START SPECIFIC FUNCTIONS
	// *************************************

	public static void setUp() {
		x_now = 0;
		y_now = 0;
		theta_now = 0;
		stopped=false;

		Read stopThread = new Read();
		Thread t2 = new Thread(stopThread);
		t2.start();
		while (!(MainActivity.x_now == 130 && MainActivity.y_now == 250 && MainActivity.theta_now == 0)) {
			if (!MainActivity.isStopped()) {
				System.out.println("in drive if, Positon: X: " + x_now + "  Y: " + y_now
						+ "  Theta: " + theta_now);
				MainActivity.driveFromTo(MainActivity.x_now,
						MainActivity.y_now, MainActivity.theta_now, 130, 250, 0);
			} else {
				do {
					MainActivity.turn(-90);
					MainActivity.drive(20, true);
					MainActivity.turn(90);
				} while (MainActivity.ReadSensorsMain());
				MainActivity.setStop(false);
			}
		}
		System.out.println(MainActivity.x_now + " " + MainActivity.y_now + " "
				+ MainActivity.theta_now);
	}

	public static void printPosition() {
		System.out.println("Positon: X: " + x_now + "  Y: " + y_now
				+ "  Theta: " + theta_now);
	}

	public static void drive(double cm, boolean forced) {
		System.out.println("b4: Driven, Positon: X: " + x_now + "  Y: " + y_now
				+ "  Theta: " + theta_now);
		try {
			System.out.println("drive func");
			comReadWrite(new byte[] { 'w', '\r', '\n' });
			stopwatch.start();
			int sleeptime = (int) (cm * 1000 / 28.7);

			while (sleeptime > 500 && (!stopped || forced)) {
				Thread.sleep(500);
				sleeptime -= 500;
				System.out.println("in sleep while");
			}
			if (!stopped || forced) {
				Thread.sleep(sleeptime);
			}

			comWrite(new byte[] { 's', '\r', '\n' });
			// System.out.println("stoptime: " + );
			if (!stopped || forced) {
				double dX = cm * Math.cos(Math.toRadians(theta_now));
				double dY = cm * Math.sin(Math.toRadians(theta_now));
				x_now += Math.round(dX);
				y_now += Math.round(dY);
				System.out.println("if drive");
			} else {
				System.out.println(stoptime);
				double drivencm = stoptime * 28.85;
				double dX = drivencm * Math.cos(Math.toRadians(theta_now));
				double dY = drivencm * Math.sin(Math.toRadians(theta_now));
				x_now += Math.round(dX);
				y_now += Math.round(dY);
				System.out.println("else drive");
			}
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Driven, Positon: X: " + x_now + "  Y: " + y_now
				+ "  Theta: " + theta_now);
	}

	public static void turn(char dir, double d) {
		try {
			System.out.println("turn funct");
			if (d == 0) {
				System.out.println("dont turn");
				return;
			}
			if (dir == 'r') {
				robotSetVelocity(velocity, neg_velocity);
				theta_now -= d;
			}
			if (dir == 'l') {
				robotSetVelocity(neg_velocity, velocity);
				theta_now += d;
			}
			int time = (int) (d * 1000 / (116));
			System.out.println(time);
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

		System.out.println("Turned, Positon: X: " + x_now + "  Y: " + y_now
				+ "  Theta: " + theta_now);
	}

	public static boolean ReadSensorsMain() {
		String string1;
		try {
			do {
				string1 = MainActivity.comReadWrite(new byte[] { 'q', '\r',
						'\n' });
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

			for (int i : sensor) {
				System.out.print(i + " ");
			}
			int links = sensor[5];
			int rechts = sensor[6];

			if (rechts <= 50 || links <= 30) {
				System.out.println(" " + links + " " + rechts);
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		return false;
	}

	public static void driveFromTo(double fromX, double fromY,
			double fromAngle, double toX, double toY, double toAngle) {
		double a = Math.abs(fromX - toX);
		double b = Math.abs(fromY - toY);
		double c = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
		double alpha = Math.toDegrees(Math.acos(a / c));
		double beta = 90 - alpha;
		// double beta = 90 - alpha;
		double toTurn1 = 0;
		double toTurn2 = 0;

		if (fromX == toX && fromY == toY) {
			System.out.println("return from drivefromto");
			return;
		}

		else if (fromX < toX && fromY == toY) { // nach rechts
			System.out.println("rechts");
			toTurn1 = -fromAngle;
			toTurn2 = toAngle;
		} else if (fromX == toX && fromY < toY) { // nach oben
			System.out.println("oben");
			toTurn1 = 90 - fromAngle;
			toTurn2 = toAngle - 90;
		} else if (fromX > toX && fromY == toY) { // nach links
			System.out.println("links");
			toTurn1 = 180 - fromAngle;
			toTurn2 = toAngle - 180;
		} else if (fromX == toX && fromY > toY) { // nach unten
			System.out.println("unten");
			toTurn1 = 270 - fromAngle;
			toTurn2 = toAngle - 270;
		}

		// -------------------------------------------------

		else if (fromX < toX && fromY < toY) { // nach oben rechts
			System.out.println("oben rechts, " + alpha);
			toTurn1 = alpha - fromAngle;
			toTurn2 = toAngle - alpha;
			System.out.println(toTurn1 + " " + toTurn2);
		} else if (fromX > toX && fromY < toY) { // nach oben links
			System.out.println("oben links, " + alpha);
			toTurn1 = (beta + 90) - fromAngle;
			toTurn2 = toAngle - (beta + 90);
			System.out.println(toTurn1 + " " + toTurn2);
		} else if (fromX > toX && fromY > toY) { // nach unten links
			System.out.println("unten links, " + alpha);
			toTurn1 = (alpha + 180) - fromAngle;
			toTurn2 = toAngle - (alpha + 180);
			System.out.println(toTurn1 + " " + toTurn2);
		} else if (fromX < toX && fromY > toY) { // nach unten rechts
			System.out.println("unten rechts , " + alpha);
			toTurn1 = (beta + 270) - fromAngle;
			toTurn2 = toAngle - (beta + 270);
			System.out.println(toTurn1 + " " + toTurn2);
		}

		turn(toTurn1);
		drive(c, false);

		if (!isStopped()) {
			System.out.println("2nd drive");
			turn(toTurn2);
		}

		System.out
				.println("-----------------------------------------------------------------------");
	}

	public static void turn(double degree) {
		if (degree > 180) {
			turn('r', Math.abs(360 - degree));
		} else if (degree < -180) {
			turn('l', Math.abs(360 + degree));
		} else if (degree < 0) {
			turn('r', Math.abs(degree));
		} else {
			turn('l', Math.abs(degree));
		}
	}
}