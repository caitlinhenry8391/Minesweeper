
public class Mine {
	public int id;
	public boolean bomb;
	public boolean hidden;
	public int nearbyMines;
	public int row;
	public int column;
	
	public Mine(int newId, boolean newBomb, boolean newHidden, int newNearbyMines, int newRow, int newColumn){
		id = newId;
		row = newRow;
		column = newColumn;
		bomb = newBomb;
		hidden = newHidden;
		nearbyMines = newNearbyMines;
	}
}
