package SudokuKid;

import java.util.Scanner;

/**
 *
 * @author jsanchez
 */
public class main {
    
    private static boolean NP = true; //weather to use Naked Pairs
    private static boolean PP = true; //weather to use Pointing Pairs
    
    private static final int[][] EL_PAIS_experto_2025_12_05 = {{0,1,4,9,2,0,0,0,8},{7,0,6,0,0,0,0,0,0},{0,0,0,0,4,1,5,0,0},{6,8,0,0,0,4,0,1,0},{0,2,0,0,7,0,0,5,0},{0,0,0,0,6,0,0,0,7},{2,0,0,0,0,0,4,0,5},{0,0,8,0,0,0,0,0,0},{0,0,0,0,9,0,2,3,0}};
    private static final int[][] EL_PAIS_medio_2026_01_09 = {{0,2,5,0,4,6,0,0,0},{0,0,0,0,0,0,7,0,0},{1,0,9,0,0,0,0,0,0},{0,0,0,2,9,0,0,7,4},{6,0,7,0,0,0,0,8,0},{0,0,0,0,0,0,0,0,1},{0,0,0,0,8,4,0,5,0},{0,6,8,0,0,0,2,0,0},{0,0,0,0,0,1,0,0,9}};
    private static final int[][] EL_PAIS_dificil_2026_01_09 = {{0,0,0,0,0,0,8,0,0},{0,4,5,0,0,0,0,0,9},{0,9,0,8,0,0,0,0,0},{1,0,0,9,0,0,6,0,0},{0,2,0,0,6,0,0,9,7},{0,0,0,0,0,1,0,0,8},{0,0,0,3,0,7,0,0,2},{0,1,0,0,2,0,0,0,0},{0,0,6,0,0,0,3,0,4}};
    
    public static void main (String[] args){
        //the sudoku statement we want to solve:
        int[][] sudokuMatrix = EL_PAIS_dificil_2026_01_09;
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
            while(PP && !solve && sudoku.solvePointingPairs()){
//                System.out.println("solvePointingSingles SUCCES");
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
