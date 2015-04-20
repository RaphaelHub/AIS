
public class Test {

	public static void main(String[] args) {
	driveFromTo(100,100,0,0,0, 0);

	}
	public static void driveFromTo(double fromX, double fromY, double fromAngle,
			double toX, double toY, double toAngle) {
		int add=0;
		if ((fromX - toX)>0&&(fromY - toY)<0){
			add=270;
		}
		if ((fromX - toX)<0&&(fromY - toY)<0){
			add=180;
		}
		if ((fromX - toX)<0&&(fromY - toY)>0){
			add=90;
		}
		System.out.println(add);
		double c = Math.sqrt(Math.pow((fromX - toX), 2)
				+ Math.pow((fromY - toY), 2));
		double a = Math.abs(fromX - toX);
		double b = Math.abs(fromY - toY);
		double alpha = add+Math.round(Math.toDegrees(Math.acos(a / c)));
		double beta = 90 - alpha;
		double toTurn1 = alpha - fromAngle;
		double toTurn2 = toAngle - alpha;
		if (toTurn1 < 0) {
			//turn('r', (int) toTurn1);
		} else {
			//turn('l', (int) toTurn1);
		}
		//drive((int) c);
		if (toTurn2 < 0) {
			//turn('r', (int) toTurn1);
		} else {
			//turn('r', (int) toTurn2);
		}
		System.out.println("alpha:  "+ alpha +"    turn :"+toTurn1+"   turn2 :"+toTurn2);
	}

}
