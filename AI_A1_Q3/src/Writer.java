import java.lang.Math;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class Writer {
	static ArrayList<Double> records = new ArrayList<>();//visited locations of x
	static int steps = 0;
	static double xFinal1=0.0;
	static double xFinal2=0.0;
	public static void main (String args[]){
		//hill-climbing
		double roundX0;
		double roundXD;
		//X0 = {0, 1, ...., 9, 10}
		//xD = {0.01, 0.02, ...., 0.10}
		for (double x = 0.00; x <=10.00; x = x + 1.00){
			System.out.println("  x0   StepSize    max(X, Y)       Steps  ");
			for (double xD = 0.01; xD <= 0.10; xD = xD + 0.01){
				steps = 0;
				roundX0 = Math.round(x * 100.0) / 100.0;
				roundXD = Math.round(xD * 100.0) / 100.0;
				DecimalFormat sample1 = new DecimalFormat("00");
				DecimalFormat sample2 = new DecimalFormat(".00");
				String s0 = sample1.format(roundX0);
				String sd = sample2.format(roundXD);
				double tempo = HillClimbing(roundX0, roundXD);
				DecimalFormat sample3 = new DecimalFormat("0.000");
				DecimalFormat sample4 = new DecimalFormat("0.00");
				String sr = sample3.format(tempo);
				String sx = sample4.format(xFinal1);
				if (tempo >= 0.0){
					sr = "+" + sr;
				}
				System.out.println("  "+ s0 +"      "+ sd + "     (" + sx + ", " + sr + ")" + "    " + steps);
			}
			System.out.println("     ");
		}
		//Hill climbing test
		//System.out.println("For x0 = "+ 2 +", and xD = "+ 0.02 + ", the local max is: " + HillClimbing(2, 0.02));

		//Simulated Annealing test SimulatedAnnealing(double x0, double xD, double T, double a)
		double step = 0.10;
		double bigT = 4000.0;
		double a = 0.95;
		System.out.println("Simulated Annealing");
		System.out.println( "T= " + bigT +", alpha = " + a);
		System.out.println("Total steps: 252, step size: " + step);

		System.out.println("  x0     max(x, y)    ");
		for (double x = 0.0; x <= 10.0; x = x + 1.0){
			steps = 0;
			double tempo = SimulatedAnnealing(x, step, bigT, a);
			String s0, s1, sr;
			DecimalFormat sample1 = new DecimalFormat("00");
			DecimalFormat sample2 = new DecimalFormat(".00");
			s0 = sample1.format(x);
			s1 = sample2.format(step);

			DecimalFormat sample3 = new DecimalFormat("0.000");
			DecimalFormat sample4 = new DecimalFormat("0.00");
			sr = sample3.format(tempo);
			String sx = sample4.format(xFinal2);
			System.out.println("  "+ s0 + "    (" + sx + ", " + sr + ")" );
			//System.out.println("   " + steps);
		}


		//Test Function
		//System.out.println(Math.sin((Math.pow(10.0, 2.0))/2.0)/(log((10.0 + 4.0),2)));

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
		xFinal1 = x0;
		double result = E(x0);
		boolean maxReached = false;
		int marker = 0;
		double temp1, temp2, temp3;
		double xo = x0;

		while (maxReached != true){
			temp1 = E(xo - xD);
			temp2 = E(xo + xD);

			//setup the marker and the temp max
			if ((xo - xD)<0.0){
				marker = 2;
				temp3 = temp2;
			} else if ((xo + xD) > 10.0){
				marker = 1;
				temp3 = temp1;
			} else {
				if (temp1 >= temp2){
					marker = 1;
					temp3 = temp1;
				} else {
					marker = 2;
					temp3 = temp2;
				}

			}

			if (temp3 > result){
				result = temp3;
				if (marker == 1){
					xo = xo - xD;
					xFinal1 = xo;
					//System.out.println("Moving Left");
				} else if (marker == 2){
					xo = xo + xD;
					xFinal1 = xo;
					//System.out.println("Moving Right");
				} else {
					System.out.println("Marker Error!!");
				}
			} else {//Temp3 <= result, we are at optimum
				maxReached = true;
			}
			steps++;

		}

		return Math.round(result * 1000.0) / 1000.0; //keep 3 decimals
	}

	static double SimulatedAnnealing(double x0, double xD, double T, double a){
		xFinal2 = x0;
		double randP;
		double times = 1.0;//random width
		double xo = x0;
		double x1;
		double result = E(x0);
		double max = E(x0);
		double bigT = T;
		records.add(x0);

		while (bigT > 0.01) {
			randP = randomDouble(0.0, 1.0);//random probability
			double temp1 = randomDouble(xo - times*xD, xo + times*xD);
			x1 = Math.round(temp1 * 1000.0) / 1000.0;//keep 3 decimals
			//System.out.println("random x1: "+x1);
			//avoid check the same x
			while (true){
				if (checkExplored(x1) || x1 < 0.0 || x1 > 10.0){
					//re-random is found used
					temp1 = randomDouble(xo - times*xD, xo + times*xD);
					x1 = Math.round(temp1 * 1000.0) / 1000.0;
					//System.out.println("Need to choose new number");
				} else {
					break;
				}
			}

			double temp = E(x1);
			//System.out.println(temp);
			if (temp > result){
				result = temp;
				xo = x1;
				xFinal2 = xo;
				if (temp > max){
					max = temp;
				}
			} else {
				double p = Math.exp(-(result - temp)/bigT);
				//System.out.println("Using P: "+p);
				if (p >= randP){
					result = temp;
					xo = x1;
					xFinal2 = xo;
				}
			}
			//System.out.println(result);
			records.add(x1);
			bigT = bigT * a;
			steps++;
			//System.out.println(bigT);
			//System.out.println(result);

		}

		return Math.round(max * 1000.0) / 1000.0;//keep 3 decimals
	}

	public static boolean checkExplored(double x){
		for (int i=0; i<records.size(); i++){
			double temp = records.get(i);
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
