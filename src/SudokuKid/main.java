package SudokuKid;

/**
 *
 * @author jsanchez
 */
public class main {
    
    private static boolean NP = true; //use Naked Pairs
    private static boolean PS = true; //use Pointing Singles
    
    public static void main (String[] args){
        int[][] sudokuMatrix = {{0,1,4,9,2,0,0,0,8},{7,0,6,0,0,0,0,0,0},{0,0,0,0,4,1,5,0,0},{6,8,0,0,0,4,0,1,0},{0,2,0,0,7,0,0,5,0},{0,0,0,0,6,0,0,0,7},{2,0,0,0,0,0,4,0,5},{0,0,8,0,0,0,0,0,0},{0,0,0,0,9,0,2,3,0}};
        Sudoku sudoku = new Sudoku(sudokuMatrix);
        sudoku.showGrid();
        boolean solve; //weather a solve was made or not
        while(!sudoku.isSolved()){
            solve = false;
            while(sudoku.solveNakedSingles()){
                solve = true; //we've solved something
            }
            while(NP && !solve && sudoku.solveNakedPairs()){
                solve = true; //we've solved something
            }
            while(PS && !solve && sudoku.solvePointingSingles()){
                solve = true; //we've solved something
            }
        }
    }
}
