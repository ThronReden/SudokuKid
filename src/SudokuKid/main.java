package SudokuKid;

import java.util.Scanner;

/**
 *
 * @author jsanchez
 */
public class main {
    
    private static boolean NS = true; //weather to use Naked Singles
    private static boolean HS = true; //weather to use Hidden Singles
    private static boolean AS = NS && HS; //weather to use All Singles
    private static boolean NP = true; //weather to use Naked Pairs
    private static boolean PP = true; //weather to use Pointing Pairs
    private static boolean PT = true; //weather to use Pointing Triplets
    private static boolean PN = PP && PT; //weather to use Pointing Numbers
    
    //SOLVABLE:
    private static final int[][] EL_PAIS_experto_2025_12_05 = {{0,1,4,9,2,0,0,0,8},{7,0,6,0,0,0,0,0,0},{0,0,0,0,4,1,5,0,0},{6,8,0,0,0,4,0,1,0},{0,2,0,0,7,0,0,5,0},{0,0,0,0,6,0,0,0,7},{2,0,0,0,0,0,4,0,5},{0,0,8,0,0,0,0,0,0},{0,0,0,0,9,0,2,3,0}};
    private static final int[][] EL_PAIS_medio_2026_01_09 = {{0,2,5,0,4,6,0,0,0},{0,0,0,0,0,0,7,0,0},{1,0,9,0,0,0,0,0,0},{0,0,0,2,9,0,0,7,4},{6,0,7,0,0,0,0,8,0},{0,0,0,0,0,0,0,0,1},{0,0,0,0,8,4,0,5,0},{0,6,8,0,0,0,2,0,0},{0,0,0,0,0,1,0,0,9}};
    //UNSOLVABLE:
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
            while(!AS && NS && sudoku.solveNakedSingles()){
//                System.out.println("used solveNakedSingles");
                solve = true; //we've solved something
                numAdded = true; //we've added a number to the grid
            }
            while(!AS && HS && sudoku.solveHiddenSingles()){
//                System.out.println("used solveHiddenSingles");
                solve = true; //we've solved something
                numAdded = true; //we've added a number to the grid
            }
            while(AS && sudoku.solveAllSingles()){
//                System.out.println("used solveAllSingles");
                solve = true; //we've solved something
                numAdded = true; //we've added a number to the grid
            }
            while(NP && !solve && sudoku.solveNakedPairs()){
//                System.out.println("used solveNakedPairs");
                solve = true; //we've found at least one new pair
            }
            while(!PN && PP && !solve && sudoku.solvePointingPairs()){
//                System.out.println("used solvePointingPairs");
                solve = true; //we've found at least one new pointing pair
            }
            while(!PN && PT && !solve && sudoku.solvePointingTriplets()){
//                System.out.println("used solvePointingTriplets");
                solve = true; //we've found at least one new pointing triplet
            }
            while(PN && !solve && sudoku.solvePointingNumbers()){
//                System.out.println("used solvePointingNumbers");
                solve = true; //we've found at least one new pointing
                //pair or triplet
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
