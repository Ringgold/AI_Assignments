
public class CurrentSpace {
	public int [][]curSpace = new int[2][3];;
	public int cost = 0;//so far cost to explore to this state
	public int ID = 0;//The ID of each explored state
	public int preID = 0;//The previous state ID of this state
	public CurrentSpace(int [][]tempCurSpace){
		for (int x = 0; x < MainFile.row; x++){
			for (int y = 0; y < MainFile.col; y++ ){
				curSpace[x][y] = tempCurSpace[x][y];
			}
		}
	}
	
	public CurrentSpace(int [][]tempCurSpace, int ID, int preID){
		for (int x = 0; x < MainFile.row; x++){
			for (int y = 0; y < MainFile.col; y++ ){
				curSpace[x][y] = tempCurSpace[x][y];
			}
		}
		this.ID = ID;
		this.preID = preID;
	}

	public CurrentSpace(int [][]tempCurSpace, int ID, int preID, int cost){
		for (int x = 0; x < MainFile.row; x++){
			for (int y = 0; y < MainFile.col; y++ ){
				curSpace[x][y] = tempCurSpace[x][y];
			}
		}
		this.ID = ID;
		this.preID = preID;
		this.cost = cost;
	}
}
