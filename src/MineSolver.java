import java.util.ArrayList;
import java.util.Random;

/**
 * Solver automatically tries to solve 100,000 10x10 minesweeper puzzles, each with ten mines
 * General solving algorithm is to keep a list of known mines, and uncover cells that are bordering uncovered cells and known not to be mines.
 * 
 * @author Caitlin Henry
 * @version 1.0
*/
public class MineSolver {
	
	public static void main(String[] args){
		int wonGame = 0;
		int gameOver = 0;
		//number of mines / rows / columns
		int n = 10;
		
		long startTime = System.currentTimeMillis();
		System.out.println("Running tests...");
		
		//loop to solve each of 100,000 test boards
		for(int i = 0; i < 100000; i++){
			
			//create board
			MineBoard myBoard = new MineBoard(n,n);
			ArrayList<Mine> mines = new ArrayList<Mine>();
			
			//generate random starting point
			Random randomGenerator = new Random();
			int row = randomGenerator.nextInt(n);
			int column = randomGenerator.nextInt(n);
			int code = myBoard.uncoverCell(row,column);
			
			Mine nextCell = null;

			//code 0 will be generated when a mine is uncovered
			while(code == 0){
				
				nextCell = null;
				ArrayList<Mine> count;
				ArrayList<Mine> mineCount;
				ArrayList<Mine> emptyCount;
				
				//general procedure is to iterate over all the cells once to see if any can be marked as known mines
				//then, iterate over all cells again to determine any cells that have more uncovered cells than known mines
				//uncover cells that are not known to be mines
				//if no cells are found during this search, generate a random cell and uncover it
				for(int x = 0; x < n; x++){
					for(int y = 0; y < n; y++){
						if(!myBoard.board[x][y].hidden && myBoard.board[x][y].nearbyMines > 0){
							//iterate over each cell neighbors
							//mark it as as a mine if it the only uncovered cell(s) left by an uncovered number
							count = new ArrayList<Mine>();
							for(int q = x-1; q <= x+1; q++){
								for(int r = y - 1; r <= y+1; r++){
									if(q < n && q >= 0 && r < n && r >= 0 && !(q == x && r == y)){
										if(myBoard.board[q][r].hidden){
											count.add(myBoard.board[q][r]);
										}
									}
								}
							}
							
							//mark these cells as mines
							if(count.size() == myBoard.board[x][y].nearbyMines){
								for(int w = 0; w < count.size(); w++){
									if(!mines.contains(count.get(w))){
										mines.add(count.get(w));
									}
								}
							}
						}
					}
				}
				
				//iterate over cells again, this time to determine which nearbyCounts have known mines neighboring them
				//uncover cells that are nearby uncovered cells and not marked as known mines
				for(int x = 0; x < n; x++){
					for(int y = 0; y < n; y++){
						if(!myBoard.board[x][y].hidden && myBoard.board[x][y].nearbyMines > 0){
							emptyCount = new ArrayList<Mine>();
							mineCount = new ArrayList<Mine>();
							for(int q = x-1; q <= x+1; q++){
								for(int r = y - 1; r <= y+1; r++){
									if(q < n && q >= 0 && r < n && r >= 0 && !(q == x && r == y)){
										if(myBoard.board[q][r].hidden){
											//create a list of uncovered cells bordering current cell
											emptyCount.add(myBoard.board[q][r]);
											if(mines.contains(myBoard.board[q][r])){
												//create list of known uncovered mines bordering current cell
												mineCount.add(myBoard.board[q][r]);
											}
										}
										
									}
								}
							}
							
							//if there are more uncovered cells than nearbyMines, and if there are the same number of known mines as nearby mines
							//uncover the empty cells not known to be mines
							if(mineCount.size() == myBoard.board[x][y].nearbyMines && emptyCount.size() > myBoard.board[x][y].nearbyMines){
								for(int w = 0; w < emptyCount.size(); w++){
									if(!mines.contains(emptyCount.get(w))){
										//if not a known mine, then must be safe to uncover
										//uncover this element next, then iterate
										nextCell = emptyCount.get(w);
										break;
									}
								}
							}
						}
					}
				}
				
				//if no cell was found to be uncovered, randomly generate one
				if(nextCell == null){
					//Random randomGenerator = new Random();
					int row2 = randomGenerator.nextInt(n);
					int column2 = randomGenerator.nextInt(n);
					code = myBoard.uncoverCell(row2,column2);
				}
				
				//otherwise, uncover identified blank cell found in loo[ loop
				else{
					code = myBoard.uncoverCell(nextCell.row, nextCell.column);
				}
			}
			
			//User uncovered a mine. Game over.
			if(code == 1){
				gameOver++;
			}
			
			//all uncovered cells are mines. Game is won.
			if(code == 2){
				wonGame++;
			}
			
		}
		
		long endTime = System.currentTimeMillis();
		//System.out.println(endTime);
		
		System.out.println("Solver completed.");
		System.out.println("Successfully solved puzzles: " + wonGame + " out of 100,000 games.");
		System.out.println("Game over: " + gameOver + " out of 100,000 games.");
		System.out.println("Total execution time: " + (endTime - startTime)/1000 + " seconds.");
	}

}
