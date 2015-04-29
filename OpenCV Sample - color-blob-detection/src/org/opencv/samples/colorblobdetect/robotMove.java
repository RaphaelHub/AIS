package org.opencv.samples.colorblobdetect;

import org.opencv.core.Point;

public class robotMove implements Runnable {

	@Override
	public void run() {
		turnTracking(360);
		turnTracking(360);
		turnTracking(360);
		turnTracking(360);
		turnTracking(360);
		//ballFinder();
	}

	public void ballFinder() {
		try {
			ColorBlobDetectionActivity.x_now = 0;
			ColorBlobDetectionActivity.y_now = 0;
			ColorBlobDetectionActivity.theta_now = 0;

			long sleepTime = 800;

			System.out.println("Color: ball finder"
					+ ColorBlobDetectionActivity.tracker.getCoordinates());

			turnTracking(360);

			turnTracking(45);

			if (seeBall()) {
				drive(53, true);
				Thread.sleep(sleepTime);
			}
			if (seeBall()) {
				drive(53, true);
				Thread.sleep(sleepTime);
			}
			if (seeBall()) {
				turnTracking(360);
				Thread.sleep(sleepTime);
			}
			if (seeBall()) {
				turn(135);
				Thread.sleep(sleepTime);
			}

			while (seeBall()) {

				if (seeBall()) {
					drive(75, true);
					Thread.sleep(sleepTime);
				}

				if (seeBall()) {
					turnTracking(360);
					Thread.sleep(sleepTime);
				}

				if (seeBall()) {
					drive(75, true);
					Thread.sleep(sleepTime);
				}
				if (seeBall()) {
					turnTracking(90);
					Thread.sleep(sleepTime);
				}
				if (seeBall()) {
					turnTracking(360);
					Thread.sleep(sleepTime);

				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	private boolean seeBall() {
		return ColorBlobDetectionActivity.tracker.getCoordinates() == null
				|| ColorBlobDetectionActivity.tracker.getCoordinates().equals(
						new Point(0.0, 0.0));
	}

	private void turnTracking(double toTurn) {
		System.out.println("Color: turn");
		while (toTurn >= 45
				&& (seeBall())) {
			toTurn -= 45;
			turn(45);
			System.out.println("Color: " + toTurn);
			try {
				Thread.sleep(800);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public static void drive(double cm, boolean forced) {
		System.out.println("b4: Driven, Positon: X: "
				+ ColorBlobDetectionActivity.x_now + "  Y: "
				+ ColorBlobDetectionActivity.y_now + "  Theta: "
				+ ColorBlobDetectionActivity.theta_now);
		try {
			System.out.println("drive func");
			ColorBlobDetectionActivity.comReadWrite(new byte[] { 'w', '\r',
					'\n' });
			ColorBlobDetectionActivity.stopwatch.start();
			int sleeptime = (int) (cm * 1000 / 28.7);

			while (sleeptime > 500
					&& (!ColorBlobDetectionActivity.stopped || forced)) {
				Thread.sleep(500);
				sleeptime -= 500;
				System.out.println("in sleep while");
			}
			if (!ColorBlobDetectionActivity.stopped || forced) {
				Thread.sleep(sleeptime);
			}

			ColorBlobDetectionActivity.comWrite(new byte[] { 's', '\r', '\n' });
			// System.out.println("stoptime: " + );
			if (!ColorBlobDetectionActivity.stopped || forced) {
				double dX = cm
						* Math.cos(Math
								.toRadians(ColorBlobDetectionActivity.theta_now));
				double dY = cm
						* Math.sin(Math
								.toRadians(ColorBlobDetectionActivity.theta_now));
				ColorBlobDetectionActivity.x_now += Math.round(dX);
				ColorBlobDetectionActivity.y_now += Math.round(dY);
				System.out.println("if drive");
			} else {
				System.out.println(ColorBlobDetectionActivity.stoptime);
				double drivencm = ColorBlobDetectionActivity.stoptime * 28.7;
				double dX = drivencm
						* Math.cos(Math
								.toRadians(ColorBlobDetectionActivity.theta_now));
				double dY = drivencm
						* Math.sin(Math
								.toRadians(ColorBlobDetectionActivity.theta_now));
				ColorBlobDetectionActivity.x_now += Math.round(dX);
				ColorBlobDetectionActivity.y_now += Math.round(dY);
				System.out.println("else drive");
			}
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Driven, Positon: X: "
				+ ColorBlobDetectionActivity.x_now + "  Y: "
				+ ColorBlobDetectionActivity.y_now + "  Theta: "
				+ ColorBlobDetectionActivity.theta_now);
	}

	public static void turn(char dir, double d) {
		try {
			System.out.println("turn funct");
			if (d == 0) {
				System.out.println("dont turn");
				return;
			}
			if (dir == 'r') {
				ColorBlobDetectionActivity.robotSetVelocity(
						ColorBlobDetectionActivity.velocity,
						ColorBlobDetectionActivity.neg_velocity);
				ColorBlobDetectionActivity.theta_now -= d;
			}
			if (dir == 'l') {
				ColorBlobDetectionActivity.robotSetVelocity(
						ColorBlobDetectionActivity.neg_velocity,
						ColorBlobDetectionActivity.velocity);
				ColorBlobDetectionActivity.theta_now += d;
			}
			int time = (int) (d * 1000 / (122));
			System.out.println(time);
			Thread.sleep(time);
			ColorBlobDetectionActivity.comWrite(new byte[] { 's', '\r', '\n' });
			if (ColorBlobDetectionActivity.theta_now >= 360) {
				ColorBlobDetectionActivity.theta_now -= 360;
			}
			if (ColorBlobDetectionActivity.theta_now <= -360) {
				ColorBlobDetectionActivity.theta_now += 360;
			}
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Turned, Positon: X: "
				+ ColorBlobDetectionActivity.x_now + "  Y: "
				+ ColorBlobDetectionActivity.y_now + "  Theta: "
				+ ColorBlobDetectionActivity.theta_now);
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

		if (!ColorBlobDetectionActivity.isStopped()) {
			System.out.println("2nd drive");
			turn(toTurn2);
		}

		System.out
				.println("-----------------------------------------------------------------------");
	}

}
