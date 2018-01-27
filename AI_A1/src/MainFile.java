import java.util.*;

public class MainFile {
	static int [][]space = new int[2][3];
	static int [][]destination = new int[2][3];
    static Stack<CurrentSpace> tempStack = new Stack<>();
	static ArrayList<Object> explored = new ArrayList<>();
	static ArrayList<CurrentSpace> sequence = new ArrayList<>();
	static int steps = 0;
	//the preset matrix size
	static final int row = 2;
	static final int col= 3;
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
//		System.out.println("BFS finished at the explored size of: " + BFS(newSpace));
//		if (goalFound){
//			int index = explored.size()-1;
//			System.out.println("The Following are the routine from GOAL to the STARTING state:");
////			while (true){
////				int [][]temp = stack.get(index).curSpace;
////				System.out.println(temp[0][0]+ " " + temp[0][1] + " " + temp[0][2]);
////				System.out.println(temp[1][0]+ " " + temp[1][1] + " " + temp[1][2]);
////				System.out.println("So far cost: "+stack.get(index).cost + " Previous ID: "+stack.get(index).preID + " Current ID: "+ stack.get(index).ID);
////				if (stack.get(index).preID < 0){
////					break;
////				} else {
////					index = stack.get(index).preID;
////				}
////			}
//			for (int i = 0; i<= index; i++){
//				System.out.println(sequence.get(i).curSpace[0][0]+ " " + sequence.get(i).curSpace[0][1] + " " + sequence.get(i).curSpace[0][2]);
//				System.out.println(sequence.get(i).curSpace[1][0]+ " " + sequence.get(i).curSpace[1][1] + " " + sequence.get(i).curSpace[1][2]);
//				System.out.println("cost: "+sequence.get(i).cost+ " ID: " +sequence.get(i).ID  +" preID: "+ sequence.get(i).preID);
//			}
//		} else {
//			System.out.println("The goal state is not found!");
//		}
//		// BFS Finished
//        initAll();
//        newSpace = new CurrentSpace(space, 0, 0, 0);
//        System.out.println("UCS finished at the explored size of: " + UCS(newSpace));
        //Start DFS
        initAll();
        newSpace = new CurrentSpace(space, 0, 0, 0);
        tempStack.push(newSpace);
        explored.add(space);
        sequence.add(newSpace);
        System.out.println("DFS finished at the explored size of: " + DFS());
        if (goalFound){
            for (int i = 0; i<= sequence.size()-1; i++){
                System.out.println(sequence.get(i).curSpace[0][0]+ " " + sequence.get(i).curSpace[0][1] + " " + sequence.get(i).curSpace[0][2]);
                System.out.println(sequence.get(i).curSpace[1][0]+ " " + sequence.get(i).curSpace[1][1] + " " + sequence.get(i).curSpace[1][2]);
                System.out.println("cost: "+sequence.get(i).cost+ " ID: " +sequence.get(i).ID  +" preID: "+ sequence.get(i).preID);
            }
        }
	}

	//init all of the variables need to execute the search
	public static void initAll(){
        explored.clear();
        sequence.clear();
        tempStack.clear();
        space = initSpace(space);
        steps = 0;
        fx = 0;
        fy = 0;
        goalFound = false;
    }
	
//	public static int[][] setSpace(int [][]temp){
//		//initialize the matrix first
//		for (int x = 0; x < row; x++){
//			for (int y = 0; y < col; y++ ){
//				temp[x][y] = 0;
//			}
//		}
//		return temp;
//	}
	
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
        ArrayList<CurrentSpace> queue = new ArrayList<>();
		if (checkEqual(tempSpace.curSpace, destination)){
			System.out.println("The input state is the goal state");
			goalFound = true;
			explored.add(space);
            sequence.add(tempSpace);
			tempSpace.ID = explored.size()-1;
			tempSpace.preID = -1;
			return 1;
		}
		explored.add(space);
        sequence.add(tempSpace);
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
                        sequence.add(newSpace);
						newSpace.preID = currentSpace.ID;//add the address of the previous state
						newSpace.ID = explored.size()-1;
						return explored.size();
					}
					if (checkExplored(newSpace.curSpace, explored) == false){
						queue.add(newSpace);
						explored.add(newSpace.curSpace);
                        sequence.add(newSpace);
						newSpace.preID = currentSpace.ID;//add the address of the previous state
						newSpace.ID = explored.size()-1;
						System.out.println("Queued!");
						System.out.println(newSpace.curSpace[0][0]+ " " + newSpace.curSpace[0][1] + " " + newSpace.curSpace[0][2]);
						System.out.println(newSpace.curSpace[1][0]+ " " + newSpace.curSpace[1][1] + " " + newSpace.curSpace[1][2]);
//						if (checkEqual(newSpace.curSpace, destination)) {
//							System.out.println("BFS Goal State Acquired!");
//							System.out.println(newSpace.curSpace[0][0]+ " " + newSpace.curSpace[0][1] + " " + newSpace.curSpace[0][2]);
//							System.out.println(newSpace.curSpace[1][0]+ " " + newSpace.curSpace[1][1] + " " + newSpace.curSpace[1][2]);
//							goalFound = true;
//							return explored.size();
//						}
					}
				}
			}
		}
		return explored.size();
	}
	/*
	function UNIFORM-COST-SEARCH(problem) returns a solution, or failure
 node ← a node with STATE = problem.INITIAL-STATE, PATH-COST = 0
 frontier ← a priority queue ordered by PATH-COST, with node as the only element
 explored ← an empty set
 loop do
   if EMPTY?(frontier) then return failure
   node ← POP(frontier) // chooses the lowest-cost node in frontier
   if problem.GOAL-TEST(node.STATE) then return SOLUTION(node)
   add node.STATE to explored
   for each action in problem.ACTIONS(node.STATE) do
                  child ← CHILD-NODE(problem,node,action)
      if child.STATE is not in explored or frontier then
         frontier ← INSERT(child,frontier)
      else if child.STATE is in frontier with higher PATH-COST then
         replace that frontier node with child
	 */
	public static int UCS(CurrentSpace tempSpace){
        //TODO
		return explored.size();
	}
	
	public static int DFS(){
	    boolean newFound = false;
        while (!tempStack.isEmpty()){
            CurrentSpace temp = tempStack.pop();//get it
            tempStack.push(temp);
            CurrentSpace currentSpace = new CurrentSpace(temp.curSpace, temp.ID, temp.preID);
            findZero(currentSpace.curSpace);
            System.out.println("Popped!");
            System.out.println(currentSpace.curSpace[0][0]+ " " + currentSpace.curSpace[0][1] + " " + currentSpace.curSpace[0][2]);
            System.out.println(currentSpace.curSpace[1][0]+ " " + currentSpace.curSpace[1][1] + " " + currentSpace.curSpace[1][2]);
            findAdjacent();
            findOrderBFS(currentSpace.curSpace);
            for (int n=0; n<=4; n++) {
                if (xP[n] != -1 && yP[n] != -1) {
                    CurrentSpace newSpace = Swap(xP[n],yP[n],fx,fy,currentSpace);//swap to get a potential new state
                    //First Check if this is the destination
                    if (checkEqual(newSpace.curSpace, destination)){
                        System.out.println("DFS Goal State Acquired!");
                        System.out.println(newSpace.curSpace[0][0]+ " " + newSpace.curSpace[0][1] + " " + newSpace.curSpace[0][2]);
                        System.out.println(newSpace.curSpace[1][0]+ " " + newSpace.curSpace[1][1] + " " + newSpace.curSpace[1][2]);
                        goalFound = true;
                        explored.add(newSpace.curSpace);
                        sequence.add(newSpace);
                        newSpace.preID = currentSpace.ID;//add the address of the previous state
                        newSpace.ID = explored.size()-1;
                        return explored.size();
                    } else {
                        //Not found in the explored queue, need to start a new DFS search from here and add this into tempStack
                        if (checkExplored(newSpace.curSpace, explored) == false){
                            newFound = true;
                            tempStack.push(newSpace);//add as record stack
                            explored.add(newSpace.curSpace);//add to visited
                            sequence.add(newSpace);//add to the sequence record
                            newSpace.preID = currentSpace.ID;//add the address of the previous state
                            newSpace.ID = explored.size()-1;
                            System.out.println("Stacked!");
                            System.out.println(newSpace.curSpace[0][0]+ " " + newSpace.curSpace[0][1] + " " + newSpace.curSpace[0][2]);
                            System.out.println(newSpace.curSpace[1][0]+ " " + newSpace.curSpace[1][1] + " " + newSpace.curSpace[1][2]);
                            //At this stage, this state is confirmed a potential state and is not the desired answer,
                            //need to break the loop and continue the DFS
                            break;
                        } else {
                            newFound = false;
                        }
                    }
                }
            }
            if (!newFound){
                System.out.println("Need To back 1 step");
                tempStack.pop();
                newFound = false;
            }
        }


        return explored.size();
	}
	
	public static int ID(CurrentSpace tempSpace){
	    //TODO
        return explored.size();
    }
	
}
