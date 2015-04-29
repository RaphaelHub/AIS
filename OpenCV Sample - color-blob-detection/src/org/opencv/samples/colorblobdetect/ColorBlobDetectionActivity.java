package org.opencv.samples.colorblobdetect;

import java.util.List;

import jp.ksksue.driver.serial.FTDriver;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.imgproc.Imgproc;

import android.view.Menu;
import android.app.Activity;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

public class ColorBlobDetectionActivity extends Activity implements
		CvCameraViewListener2 {
	private static final String TAG = "Ball finder::Activity";

	private boolean mIsColorSelected = false;
	private Mat mRgba;
	private Scalar mBlobColorRgba;
	private Scalar mBlobColorHsv;
	private ColorBlobDetector mDetector;
	private Mat mSpectrum;
	private Size SPECTRUM_SIZE;
	private Scalar CONTOUR_COLOR;

	private static FTDriver com;
	public static Tracker tracker;

	public static byte velocity = 20;
	public static byte neg_velocity = -20;
	public static double x_now = 0;
	public static double y_now = 0;
	public static double theta_now = 0;

	public static boolean stopped = false;
	public static Stopwatch stopwatch = new Stopwatch();
	public static double stoptime = 0;

	private CameraBridgeViewBase mOpenCvCameraView;

	private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
		@Override
		public void onManagerConnected(int status) {
			switch (status) {
			case LoaderCallbackInterface.SUCCESS: {
				Log.i(TAG, "OpenCV loaded successfully");
				mOpenCvCameraView.enableView();
			}
				break;
			default: {
				super.onManagerConnected(status);
			}
				break;
			}
		}
	};

	public ColorBlobDetectionActivity() {
		Log.i(TAG, "Instantiated new " + this.getClass());
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "called onCreate");
		super.onCreate(savedInstanceState);

		com = new FTDriver((UsbManager) getSystemService(USB_SERVICE));
		com.begin(9600);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.color_blob_detection_surface_view);

		mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.color_blob_detection_activity_surface_view);
		mOpenCvCameraView.setCvCameraViewListener(this);

		tracker = new Tracker();
		Thread t = new Thread(tracker);
		t.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.disconnect:
			System.out.println("disconnected");
			disconnect();
			return true;
		case R.id.red:
			mBlobColorHsv = new Scalar(252, 198, 181, 0);
			System.out.println("Color: red");
			break;
		case R.id.green:
			mBlobColorHsv = new Scalar(103, 255, 93, 0);
			System.out.println("Color: green");
			break;
		case R.id.yellow:
			mBlobColorHsv = new Scalar(39, 255, 207, 0);
			System.out.println("Color: yellow");
			break;
		default:
			return super.onOptionsItemSelected(item);
		}

		beginTracking();
		robotMove move = new robotMove();
		Thread t2 = new Thread(move);
		t2.start();
		return true;
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mOpenCvCameraView != null)
			mOpenCvCameraView.disableView();
	}

	@Override
	public void onResume() {
		super.onResume();
		OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this,
				mLoaderCallback);
	}

	public void onDestroy() {
		super.onDestroy();
		if (mOpenCvCameraView != null)
			mOpenCvCameraView.disableView();
	}

	public void onCameraViewStarted(int width, int height) {
		mRgba = new Mat(height, width, CvType.CV_8UC4);
		mDetector = new ColorBlobDetector();
		mSpectrum = new Mat();
		mBlobColorRgba = new Scalar(255);
		mBlobColorHsv = new Scalar(255);
		SPECTRUM_SIZE = new Size(200, 64);
		CONTOUR_COLOR = new Scalar(255, 0, 0, 255);
	}

	public void onCameraViewStopped() {
		mRgba.release();
	}

	public boolean beginTracking() {

		mDetector.setHsvColor(mBlobColorHsv);

		Imgproc.resize(mDetector.getSpectrum(), mSpectrum, SPECTRUM_SIZE);

		tracker.setDetector(mDetector);

		mIsColorSelected = true;

		// oberster punkte y = 1, unterster y = 178 -> mitte 119
		// rechts x = 238; links x = 1 ->

		return false; // don't need subsequent touch events
	}

	public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
		mRgba = inputFrame.rgba();

		if (mIsColorSelected) {
			mDetector.process(mRgba);
			List<MatOfPoint> contours = mDetector.getContours();
			Log.e(TAG, "Contours count: " + contours.size());
			Imgproc.drawContours(mRgba, contours, -1, CONTOUR_COLOR);

			Mat colorLabel = mRgba.submat(4, 68, 4, 68);
			colorLabel.setTo(mBlobColorRgba);

			Mat spectrumLabel = mRgba.submat(4, 4 + mSpectrum.rows(), 70,
					70 + mSpectrum.cols());
			mSpectrum.copyTo(spectrumLabel);
		}

		return mRgba;
	}

	public static void comWrite(byte[] data) {
		// System.out.println("comwrite");
		if (com.isConnected()) {
			com.write(data);
		} else {
			System.out.println("not connected\n");
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
		// System.out.println("comreadwrite " + (char)data[0]);

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
			System.out.println("disconnected");
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
//		x_now = 0;
//		y_now = 0;
//		theta_now = 0;
//		stopped = false;
//
//		Read stopThread = new Read();
//		Thread t2 = new Thread(stopThread);
//		t2.start();
//		while (!(x_now == 130 && y_now == 250 && theta_now == 0)) {
//			if (!isStopped()) {
//				System.out.println("in drive if, Positon: X: " + x_now
//						+ "  Y: " + y_now + "  Theta: " + theta_now);
//				driveFromTo(x_now, y_now, theta_now, 130, 250, 0);
//			} else {
//				do {
//					turn(-90);
//					drive(20, true);
//					turn(90);
//				} while (ReadSensorsMain());
//				setStop(false);
//			}
//		}
//		System.out.println(x_now + " " + y_now + " " + theta_now);
	}


	public static void printPosition() {
		System.out.println("Positon: X: " + x_now + "  Y: " + y_now
				+ "  Theta: " + theta_now);
	}



	public static boolean ReadSensorsMain() {
		String string1;
		try {
			do {
				string1 = comReadWrite(new byte[] { 'q', '\r', '\n' });
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

	

}
