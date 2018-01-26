import java.util.*;

public class MainFile {
	static int [][]space = new int[2][3];
	static int [][]destination = new int[2][3];
	static ArrayList<CurrentSpace> queue = new ArrayList<>();
	static ArrayList<Object> explored = new ArrayList<>();
	static ArrayList<CurrentSpace> stack = new ArrayList<>();
	static int steps = 0;
	//the preset matrix size
	static int row = 2;
	static int col= 3;
	//the coordinates of the empty puzzle
	static int fx = 0;
	static int fy = 0;
	static boolean goalFound = false;
	 //the 4 adjacent coordinates of the empty puzzle
	/*	  2
	 * 1   3
	 *   4
	 */
	static int[] xAd = new int[4];
	static int[] yAd = new int[4];
	static int[] xP = new int[5];
	static int[] yP = new int[5];
	
	public static void main (String args[]){
		space = initSpace(space);
		initDestination();
		CurrentSpace newSpace = new CurrentSpace(space);
		System.out.println("BFS finished at the explored size of: " + BFS(newSpace));
		if (goalFound){
			int index = explored.size()-1;
			System.out.println("The Following are the routine from GOAL to the STARTING state:");
//			while (true){
//				int [][]temp = stack.get(index).curSpace;
//				System.out.println(temp[0][0]+ " " + temp[0][1] + " " + temp[0][2]);
//				System.out.println(temp[1][0]+ " " + temp[1][1] + " " + temp[1][2]);
//				System.out.println("So far cost: "+stack.get(index).cost + " Previous ID: "+stack.get(index).preID + " Current ID: "+ stack.get(index).ID);
//				if (stack.get(index).preID < 0){
//					break;
//				} else {
//					index = stack.get(index).preID;
//				}
//			}
			for (int i = 0; i<= index; i++){
				System.out.println(stack.get(i).curSpace[0][0]+ " " + stack.get(i).curSpace[0][1] + " " + stack.get(i).curSpace[0][2]);
				System.out.println(stack.get(i).curSpace[1][0]+ " " + stack.get(i).curSpace[1][1] + " " + stack.get(i).curSpace[1][2]);
				System.out.println("cost: "+stack.get(i).cost+ " ID: " +stack.get(i).ID  +" preID: "+ stack.get(i).preID);
			}
		} else {
			System.out.println("The goal state is not found!");
		}
	
	}
	
	public static int[][] setSpace(int [][]temp){
		//initialize the matrix first
		for (int x = 0; x < row; x++){
			for (int y = 0; y < col; y++ ){
				temp[x][y] = 0;
			}
		}
		return temp;
	}
	
	public static int[][] initSpace(int [][]temp){
		//initial state
		//row 0
		temp[0][0] = 1;
		temp[0][1] = 4;
		temp[0][2] = 2;
		//row 1
		temp[1][0] = 5;
		temp[1][1] = 3;
		temp[1][2] = 0;
		return temp;
	}
	
	public static void initDestination(){
		//row 0
		destination[0][0] = 0;
		destination[0][1] = 1;
		destination[0][2] = 2;
		//row 1
		destination[1][0] = 5;
		destination[1][1] = 4;
		destination[1][2] = 3;
	}
	
	public static boolean checkEqual(int [][]temp1, int [][]temp2){
		for (int x = 0; x < row; x++){
			for (int y = 0; y < col; y++ ){
//				System.out.println("checking: " + temp1[x][y]  + " and  "+ temp2[x][y] );
				if (temp1[x][y] != temp2[x][y]){
					return false;
				}
			}
		}
		return true;
	}
	
	public static boolean checkExplored(int [][]tempSpace, ArrayList<Object> tempExplored){
		for (int i=0; i < tempExplored.size(); i++){
			int [][]temp = (int[][]) tempExplored.get(i);
			if (checkEqual(tempSpace, temp)){
				return true;
			}
		}
		return false;
	}
	
	//find the empty puzzle's coordinate and save the coordinates
	public static void findZero(int [][]tempSpace){
		boolean found = false;
		for (int x = 0; x < row; x++){
			for (int y = 0; y < col; y++ ){
				if (tempSpace[x][y] == 0){
					found = true;
					fx = x;
					fy = y;
				}
				if (found == true) break;
			}
			if (found == true) break;
		}
		if (found == true){
			System.out.println("Current Empty Puzzle: " + fx + " " + fy);
		} else {
			System.out.println("Error Found: No Empty Puzzle");
		}
	}
	
	//find the coordinates of the adjacent points
	//the 4 adjacent coordinates of the empty puzzle
	/*	  2
	 * 1   3
	 *   4
	 */
	public static void findAdjacent(){
		if ((fx-1)>= 0){
			xAd[0] = fx - 1;
			yAd[0]= fy;
		} else {
			xAd[0] = -1;
			yAd[0] = -1;
		}
		
		if ((fy-1)>=0){
			xAd[1] = fx;
			yAd[1] = fy - 1;
		} else {
			xAd[1] = -1;
			yAd[1] = -1;
		}
		
		if ((fx+1)<row){
			xAd[2] = fx + 1;
			yAd[2] = fy;
		} else {
			xAd[2] = -1;
			yAd[2] = -1;
		}
		
		if ((fy+1)<col){
			xAd[3] = fx;
			yAd[3] = fy + 1;
		} else {
			xAd[3] = -1;
			yAd[3] = -1;
		}
	}
	
	//the lowest coordinate value gets the highest priority
	public static void findOrderBFS(int [][] tempSpace){
		for (int n=0; n<=4; n++){
			xP[n] = -1; yP[n] = -1;//initialize the priority value
		}
		for (int n=0; n<4; n++){
			if (xAd[n]!=-1 && yAd[n]!= -1){
				if (tempSpace[xAd[n]][yAd[n]] == 1){
					xP[0] = xAd[n]; yP[0] = yAd[n];
				} else if ((tempSpace[xAd[n]][yAd[n]] == 2)){
					xP[1] = xAd[n]; yP[1] = yAd[n];
				} else if ((tempSpace[xAd[n]][yAd[n]] == 3)){
					xP[2] = xAd[n]; yP[2] = yAd[n];
				} else if ((tempSpace[xAd[n]][yAd[n]] == 4)){
					xP[3] = xAd[n]; yP[3] = yAd[n];
				} else if ((tempSpace[xAd[n]][yAd[n]] == 5)){
					xP[4] = xAd[n]; yP[4] = yAd[n];
				} else {
					System.out.println("ERROR occured on adjacent values!");
				}
			}
		}
		for (int n=0; n<=4; n++){
			System.out.println("Ordered Adjacent Points: ("+ xP[n] +" ,"+ yP[n] +")");
		}
	}
	
	//swap the puzzle
	public static CurrentSpace Swap(int  x1, int y1, int x2, int y2, CurrentSpace tempSpace){
		System.out.println("Swaping ("+x1 + ", " + y1 +") with (" + x2 + ", " + y2 +")" );
		CurrentSpace currentSpace = new CurrentSpace(tempSpace.curSpace);
		int temp1 = tempSpace.curSpace[x1][y1];
		int temp2 = tempSpace.curSpace[x2][y2];
		currentSpace.curSpace[x1][y1] = temp2;
		currentSpace.curSpace[x2][y2] = temp1;
		steps++;
		currentSpace.cost = steps;
		currentSpace.preID = tempSpace.ID;//add the address of the previous state
		return currentSpace;
	}
	
	public static int BFS(CurrentSpace tempSpace){
		if (checkEqual(tempSpace.curSpace, destination)){
			System.out.println("The input state is the goal state");
			goalFound = true;
			explored.add(space);
			stack.add(tempSpace);
			tempSpace.ID = explored.size()-1;
			tempSpace.preID = -1;
			return 1;
		}
		explored.add(space);
		stack.add(tempSpace);
		queue.add(tempSpace);
		//Initialize the ID and PreID of the first queued element
		tempSpace.ID = explored.size()-1;
		tempSpace.preID = -1;
		
		while (queue.size() != 0){
			CurrentSpace currentSpace = new CurrentSpace(queue.get(0).curSpace, queue.get(0).ID, queue.get(0).preID);
			findZero(currentSpace.curSpace);
			System.out.println("Unqueued!");
			System.out.println(currentSpace.curSpace[0][0]+ " " + currentSpace.curSpace[0][1] + " " + currentSpace.curSpace[0][2]);
			System.out.println(currentSpace.curSpace[1][0]+ " " + currentSpace.curSpace[1][1] + " " + currentSpace.curSpace[1][2]);
			findAdjacent();
			findOrderBFS(currentSpace.curSpace);
			queue.remove(0);//pop the earliest one
			//now check and add the adjacent if it is now explored
			for (int n=0; n<=4; n++){
				if (xP[n]!=-1 && yP[n]!= -1){
					CurrentSpace newSpace = Swap(xP[n],yP[n],fx,fy,currentSpace);
					if (checkEqual(newSpace.curSpace, destination)){
						System.out.println("BFS Goal State Acquired!");
						System.out.println(newSpace.curSpace[0][0]+ " " + newSpace.curSpace[0][1] + " " + newSpace.curSpace[0][2]);
						System.out.println(newSpace.curSpace[1][0]+ " " + newSpace.curSpace[1][1] + " " + newSpace.curSpace[1][2]);
						goalFound = true;
						explored.add(newSpace.curSpace);
						stack.add(newSpace);
						newSpace.preID = currentSpace.ID;//add the address of the previous state
						newSpace.ID = explored.size()-1;
						return explored.size();
					}
					if (checkExplored(newSpace.curSpace, explored) == false){
						queue.add(newSpace);
						explored.add(newSpace.curSpace);
						stack.add(newSpace);
						newSpace.preID = currentSpace.ID;//add the address of the previous state
						newSpace.ID = explored.size()-1;
						System.out.println("Queued!");
						System.out.println(newSpace.curSpace[0][0]+ " " + newSpace.curSpace[0][1] + " " + newSpace.curSpace[0][2]);
						System.out.println(newSpace.curSpace[1][0]+ " " + newSpace.curSpace[1][1] + " " + newSpace.curSpace[1][2]);
						if (checkEqual(newSpace.curSpace, destination)) {
							System.out.println("BFS Goal State Acquired!");
							System.out.println(newSpace.curSpace[0][0]+ " " + newSpace.curSpace[0][1] + " " + newSpace.curSpace[0][2]);
							System.out.println(newSpace.curSpace[1][0]+ " " + newSpace.curSpace[1][1] + " " + newSpace.curSpace[1][2]);
							goalFound = true;
							return explored.size();
						}
					}
				}
			}
		}
		return explored.size();
	}
	
	public static void UCS(){
		
	}
	
	public static void DFS(){
		
	}
	
	public static void ID(){
		
	}
	
}
