import java.lang.Math;
import java.util.ArrayList;
import java.util.Random;

public class Writer {
	static ArrayList<Double> records = new ArrayList<>();//visited locations of x
	public static void main (String args[]){
		//hill-climbing
		double roundX0;
		double roundXD;
		//X0 = {0, 1, ...., 9, 10}
		//xD = {0.01, 0.02, ...., 0.10}
//		for (double x = 0.00; x <=10.00; x = x + 1.00){
//			for (double xD = 0.01; xD <= 0.10; xD = xD + 0.01){
//				roundX0 = Math.round(x * 100.0) / 100.0;
//				roundXD = Math.round(xD * 100.0) / 100.0;
//				System.out.println("For x0 = "+ roundX0 +", and xD = "+ roundXD + ", the local max is: " + HillClimbing(roundX0, roundXD));
//			}
//		}
		//Hill climbing test
		//System.out.println("For x0 = "+ 2 +", and xD = "+ 0.02 + ", the local max is: " + HillClimbing(2, 0.02));

		//Simulated Annealing test SimulatedAnnealing(double x0, double xD, double T, double a)

		System.out.println("For x0 = "+ 2 +", and xD = "+ 0.03 + ", the max is: " + SimulatedAnnealing(2, 0.03, 20, 0.95));

	}

	static double log(double x, double base)
	{
		return (Math.log(x) / Math.log(base));
	}

	static double E(double x){
		double y = Math.sin((Math.pow(x, 2.0))/2.0)/(log((x + 4.0),2));
		return y;
	}

	static double HillClimbing(double x0, double xD){
		double result = E(x0);
		boolean maxReached = false;
		int marker = 0;
		double temp1, temp2, temp3, x1, x2, x3;
		double xo = x0;

		while (maxReached != true){
			temp1 = E(xo - xD);
			temp2 = E(xo + xD);
			//setup the marker and the temp max
			if (temp1 >= temp2){
				marker = 1;
				temp3 = temp1;
			} else {
				marker = 2;
				temp3 = temp2;
			}

			if (temp3 > result){
				result = temp3;
				if (marker == 1){
					xo = xo - xD;
					//System.out.println("Moving Left");
				} else if (marker == 2){
					xo = xo + xD;
					//System.out.println("Moving Right");
				} else {
					System.out.println("Marker Error!!");
				}
			} else {//Temp3 <= result, we are at optimum
				maxReached = true;
			}


		}

		return result;
	}

	static double SimulatedAnnealing(double x0, double xD, double T, double a){
		double randP;
		double times = 20.0;//random width
		double xo = x0;
		double x1;
		double result = E(x0);
		double bigT = T;
		records.add(x0);

		while (bigT > 1.0) {
			randP = randomDouble(0.0, 1.0);//random probability
			double temp1 = randomDouble(xo - times*xD, xo + times*xD);
			x1 = Math.round(temp1 * 100.0) / 100.0;//keep 2 decimals
			System.out.println("random x1: "+x1);
			//avoid check the same x
			while (true){
				if (checkExplored(x1)){
					//re-random is found used
					temp1 = randomDouble(xo - times*xD, x1 + times*xD);
					x1 = Math.round(temp1 * 100.0) / 100.0;
					System.out.println("Need to choose new number");
				} else {
					break;
				}
			}

			double temp = E(x1);
			if (temp > result){
				result = temp;
				xo = x1;
			} else {
				double p = Math.exp(-(result - temp)/bigT);
				if (p >= randP){
					result = temp;
					xo = x1;
				}
			}

			records.add(x1);
			bigT = bigT * a;

		}

		return result;
	}

	public static boolean checkExplored(double x){
		double dx = 0.001;
		for (int i=0; i<records.size(); i++){
			double temp = records.get(i);
			System.out.println("temp is "+temp);
//			double bL = temp - dx;
//			double bR = temp + dx;
//			if (x<bR && x>bL){
//				return true;
//			}
			if (x==temp){
				return true;
			}
		}
		return false;
	}

	public static double randomDouble(double min , double max) {
		Random r = new Random();
		double d = min + r.nextDouble() * (max - min);
		return d;
	}
}
