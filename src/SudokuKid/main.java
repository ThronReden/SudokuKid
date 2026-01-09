package SudokuKid;

import java.util.Scanner;

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
        Scanner scanner = new Scanner(System.in);
        System.out.println("INITIAL SUDOKU STATEMENT:");
        sudoku.showGrid();
        scanner.nextLine();
        boolean solve = true; //weather a solve was made or not
        boolean numAdded; //weather a number was added to the grid or not
        int count = 0;
        while(!sudoku.isSolved() && solve){
            count++;
            solve = false;
            numAdded = false;
            while(sudoku.solveNakedSingles()){
                solve = true; //we've solved something
                numAdded = true; //we've added a number to the grid
            }
            while(NP && !solve && sudoku.solveNakedPairs()){
                solve = true; //we've solved something
            }
            while(PS && !solve && sudoku.solvePointingSingles()){
                solve = true; //we've solved something
            }
            if(numAdded){
                System.out.println("SUDOKU AT ITER "+count+":");
                sudoku.showGrid();
                scanner.nextLine();
            }
        }
        if(sudoku.isSolved()){
            System.out.println("SUDOKU IS SOLVED -- "+count+" iterations");
        } else {
            System.out.println("CAN'T SOLVE ANY FURTHER -- "+count+" iterations");
        }
        sudoku.showGrid();
    }
}
