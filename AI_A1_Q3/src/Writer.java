import java.lang.Math;
import java.util.Random;

public class Writer {
	public static void main (String args[]){
		//hill-climbing
		double roundX0;
		double roundXD;
		//X0 = {0, 1, ...., 9, 10}
		//xD = {0.01, 0.02, ...., 0.10}
		for (double x = 0.00; x <=10.00; x = x + 1.00){
			for (double xD = 0.01; xD <= 0.10; xD = xD + 0.01){
				roundX0 = Math.round(x * 100.0) / 100.0;
				roundXD = Math.round(xD * 100.0) / 100.0;
				System.out.println("For x0 = "+ roundX0 +", and xD = "+ roundXD + ", the local max is: " + HillClimbing(roundX0, roundXD));
			}
		}
		//Hill climbing test
		//System.out.println("For x0 = "+ 2 +", and xD = "+ 0.02 + ", the local max is: " + HillClimbing(2, 0.02));

		//Simulated Annealing xD = 0.03


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
		double result = 0.0;
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

	public static double randomDouble(double min , double max) {
		Random r = new Random();
		double d = min + r.nextDouble() * (max - min);
		return d;
	}

	static double SimulatedAnnealing(){
		double rand = randomDouble(0.0, 1.0);
		double result = 0.0;
//		if (Utility.acceptanceProbability(currentDistance, neighbourDistance, temp) > rand) {
//			currentSolution = new Tour(newSolution.getTour());
//		}
//
//		// Keep track of the best solution found
//		if (currentSolution.getTotalDistance() < best.getTotalDistance()) {
//			best = new Tour(currentSolution.getTour());
//		}
//
//		// Cool system
//		temp *= 1 - coolingRate;
		return result;
	}
}
