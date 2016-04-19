import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Minesweeper game implementation. Play a minesweeper game on a NxN board with X number of mines, based on user input
 * Game will show user the board as cells are uncovered, and alert the user when the game is won (only uncovered cells left are mines),
 * or when a mine is uncovered (game over).
 * 
 * @author Caitlin Henry
 * @version 1.0
*/
public class MineBoard {
	public Mine[][] board;		//2D array for game board
	public boolean gameover;	//false if a mine is clicked
	
	public int hiddenCells;		//uncovered cells
	
	public int totalMines;		//total number of mines on board
	public int size;			//n x n - number of cells on board
	
	public MineBoard(int n, int mines){
		
		gameover = false;
		
		totalMines = mines;
		size = n * n;
		hiddenCells = size;
		board = new Mine[n][n];
		
		//get random positions for mines
		Random randomGenerator = new Random();
		ArrayList<Integer> bombs = new ArrayList<Integer>();
		int num = 0;
		
		//insert x number of mines into list
		while(num < mines){
			int rand = randomGenerator.nextInt(size);
			if(!bombs.contains(rand)){
				bombs.add(rand);
				num++;
			}
		}
		
		//initialtize each cell on the board
		int index = 0;
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				if(bombs.contains(index)){
					board[i][j] = new Mine(index, true, true, 0, i, j);
				}
				else{
					board[i][j] = new Mine(index, false, true, 0, i , j);
				}
				index++;
			}
		}
		
		//iterate over board again to get number of neighboring mines
		int count;
		for(int x = 0; x < n; x++){
			for(int y = 0; y < n; y++){
				count = 0;
				for(int q = x-1; q <= x+1; q++){
					for(int r = y - 1; r <= y+1; r++){
						if(q < n && q >= 0 && r < n && r >= 0){
							if(board[q][r].bomb){
								count++;
							}
						}
					}
				}
				board[x][y].nearbyMines = count;
			}
		}
	}
	
	//print out game board in readable format
	public void printBoard(){
		if(size > 0){
			String line = "";
			for(int x = 0; x < board.length; x++){
				for(int y = 0; y < board[0].length; y++){
					if(board[x][y].hidden){
						line += "X";
					}
					else if(board[x][y].bomb){
						line += "M";
					}
					else if (board[x][y].nearbyMines > 0){
						line += board[x][y].nearbyMines;
					}
					else {
						line += ".";
					}
				}
				System.out.println(line);
				line = "";
			}
		}
	}
	
	//uncover selected cell, iterate over its neighbors to uncover them as well.
	//if the selected cell's neighbor has no nearby mines, 
	//recursively call uncoverCell on the neighbor mine to uncover its neighbors
	public int uncoverCell(int row, int column){
		
		//uncover select mine and decrement hidden cell count
		if(board[row][column].hidden){
			board[row][column].hidden = false;
			hiddenCells--;
		}
		
		//return code 1 if a bomb is uncovered
		if (board[row][column].bomb){
			//gameover exit code
			return 1;
		}
		
		//return code 2 if there uncovered cells equals total mine count - game is won
		else if(hiddenCells <= totalMines){
			//game won exit code
			return 2;
		}
		
		//otherwise, iterate over cell's neighbors, uncover each
		else{
			if(board[row][column].nearbyMines == 0){
				//iterate over the neighboring mines
				int n = board.length;

					for(int q = row-1; q <= row+1; q++){
						for(int r = column - 1; r <= column+1; r++){
							if(q < n && q >= 0 && r < n && r >= 0){
								
								//recursively call uncoverCell on neighbor cell if it has no nearby bombs
								if(board[q][r].nearbyMines == 0 && !(q == row && r == column) && board[q][r].hidden){
									//count++;
									uncoverCell(q,r);
								}
								
								//if cell has nearby bombs, decrement hidden counter and uncover cell
								else{
									if(board[q][r].hidden){
										board[q][r].hidden = false;
										hiddenCells--;
									}
								}
							}
						}
					}
			}	
			
			//no game won or game over, return code 0
			return 0;
		}

	}
	
	//reveal all cells at end of game4
	public void uncoverAll(){
		if(size > 0){
			String line = "";
			for(int x = 0; x < board.length; x++){
				for(int y = 0; y < board[0].length; y++){
					if(board[x][y].bomb){
						line += "M";
					}
					else if (board[x][y].nearbyMines > 0){
						line += board[x][y].nearbyMines;
					}
					else {
						line += ".";
					}
				}
				System.out.println(line);
				line = "";
			}
		}
	}
	
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		System.out.println("Welcome to Minesweeper. \nEnter the number of rows and columns for the game: ");
		int n = in.nextInt();
		System.out.println("Enter the number of mines for the game: ");
		int mines = in.nextInt();
		
		//check to make sure number of mines is less than total cells
		while(mines > n*n){
			System.out.println("Number of mines cannot be more than total number of cells on board.");
			System.out.println("Enter the number of mines for the game: ");
			mines = in.nextInt();
		}
		
		//construct and print board
		MineBoard myBoard = new MineBoard(n, mines);
		
		//continuously read in row and column, uncover, print out new board
		int code = 0;
		while(code == 0){
		
			System.out.println("Your board: ");
			myBoard.printBoard();
			
			//continuously read in new row and column
			int row;
			int column;
			
			System.out.print("Enter a cell row location:");
			row = in.nextInt();
			while(row >= n || row < 0){
				System.out.println("Row out of bounds");
				System.out.print("Enter a cell row location:");
				row = in.nextInt();
			}
			System.out.print("Enter a cell column location:");
			column = in.nextInt();
			while(column >= n || column < 0){
				System.out.println("Column out of bounds");
				System.out.print("Enter a cell column location:");
				column = in.nextInt();
			}
			
			//uncover selected cell, reprint board
			code = myBoard.uncoverCell(row, column);
			//myBoard.printBoard();
		}
		
		//User uncovered a mine. Game over.
		if(code == 1){
			System.out.println("Mine uncovered. Game over.");
			myBoard.uncoverAll();
		}
		
		//all uncovered cells are mines. Game is won.
		if(code == 2){
			System.out.println("Winner!");
			myBoard.uncoverAll();
		}
	}
}
