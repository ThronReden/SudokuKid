package SudokuKid;

import java.util.Scanner;

/**
 *
 * @author jsanchez
 */
public class main {
    
    //Boolean variables to store weather a certain methods use is enabled:
    private static boolean NS = true; //weather Naked Singles use is enabled
    private static boolean HS = true; //weather Hidden Singles use is enabled
    private static boolean SS = NS && HS; //Simple Singles (both previous)
    
    private static boolean NP = true; //Naked Pairs
    private static boolean HP = true; //Hidden Pairs
    private static boolean SP = NP && HP; //Simple Pairs (both previous)
    
    private static boolean PP = true; //Pointing Pairs
    private static boolean PT = true; //Pointing Triplets
    private static boolean PN = PP && PT; //Pointing Numbers (both previous)
    
    //Stored sudoku statements:
    //SOLVABLE:
    private static final int[][] EL_PAIS_experto_2025_12_05 = {{0,1,4,9,2,0,0,0,8},{7,0,6,0,0,0,0,0,0},{0,0,0,0,4,1,5,0,0},{6,8,0,0,0,4,0,1,0},{0,2,0,0,7,0,0,5,0},{0,0,0,0,6,0,0,0,7},{2,0,0,0,0,0,4,0,5},{0,0,8,0,0,0,0,0,0},{0,0,0,0,9,0,2,3,0}};
    private static final int[][] EL_PAIS_medio_2026_01_09 = {{0,2,5,0,4,6,0,0,0},{0,0,0,0,0,0,7,0,0},{1,0,9,0,0,0,0,0,0},{0,0,0,2,9,0,0,7,4},{6,0,7,0,0,0,0,8,0},{0,0,0,0,0,0,0,0,1},{0,0,0,0,8,4,0,5,0},{0,6,8,0,0,0,2,0,0},{0,0,0,0,0,1,0,0,9}};
    //UNSOLVABLE:
    private static final int[][] EL_PAIS_dificil_2026_01_09 = {{0,0,0,0,0,0,8,0,0},{0,4,5,0,0,0,0,0,9},{0,9,0,8,0,0,0,0,0},{1,0,0,9,0,0,6,0,0},{0,2,0,0,6,0,0,9,7},{0,0,0,0,0,1,0,0,8},{0,0,0,3,0,7,0,0,2},{0,1,0,0,2,0,0,0,0},{0,0,6,0,0,0,3,0,4}};
    
    public static void main (String[] args){
        //the sudoku statement we want to solve:
        int[][] sudokuMatrix = EL_PAIS_experto_2025_12_05;
        //we create a Sudoku object to manage it:
        Sudoku sudoku = new Sudoku(sudokuMatrix);
        //we create a Scanner object for basic user interaction:
        Scanner scanner = new Scanner(System.in);
        System.out.println("INITIAL SUDOKU STATEMENT:");
        sudoku.showGrid(); //this prints the sudoku to terminal
        scanner.nextLine(); //so there's a hold in the execution
        //we store weather a solve was made or not:
        boolean solve = true;
        //we store weather a number was added to the grid or not:
        boolean numAdded = false;
        int count = 0; //we'll count how many iterations we execute
        
        //MAIN SOLVING LOOP:
        //while the sudoku is not solved and a solve was made in the last
        //iteration, meaning we are able of solving further:
        while(!sudoku.isSolved() && solve){
            count++; //we add an iteration to the count
            solve = false; //we set solve to false by default
            if(numAdded){
                //if we added a number to the grid in the last iteration we
                //print the sudoku to terminal:
                System.out.println("SUDOKU AT ITER "+count+":");
                sudoku.showGrid();
                scanner.nextLine(); //we hold for user input
            }
            numAdded = false; //we set solve to false by default
            
            /**
             * SOLVING LOOPS:
             * 
             * we check if the use of that method is enabled and, for all but
             * Simple Singles methods, if a solve was already made and then we
             * keep on using the solving algorithm until it fails and store
             * some results:
             */
            //Naked Singles:
                //(used only if Hidden Singles isn't enabled)
            while(!SS && NS && sudoku.solveNakedSingles()){
//                System.out.println("used solveNakedSingles");
                solve = true; //we've solved something
                numAdded = true; //we've added a number to the grid
            }
            //Hidden Singles:
                //(used only if Naked Singles isn't enabled)
            while(!SS && HS && sudoku.solveHiddenSingles()){
//                System.out.println("used solveHiddenSingles");
                solve = true; //we've solved something
                numAdded = true; //we've added a number to the grid
            }
            //Simple Singles:
                //(used if both Naked and Hidden Singles are enabled)
            while(SS && sudoku.solveSimpleSingles()){
//                System.out.println("used solveSimpleSingles");
                solve = true; //we've solved something
                numAdded = true; //we've added a number to the grid
            }
            //Naked Pairs:
                //(used only if Hidden Pairs isn't enabled)
            while(!SP && NP && !solve && sudoku.solveNakedPairs()){
//                System.out.println("used solveNakedPairs");
                solve = true; //we've found at least one new naked pair
            }
            //Hidden Pairs:
                //(used only if Hidden Pairs isn't enabled)
            while(!SP && HP && !solve && sudoku.solveHiddenPairs()){
//                System.out.println("used solveHiddenPairs");
                solve = true; //we've found at least one new naked pair
            }
            //Simple Pairs:
                //(used if both Naked and Hidden Pairs are enabled)
            while(SP && !solve && sudoku.solveSimplePairs()){
//                System.out.println("used solveSimplePairs");
                solve = true; //we've found at least one new naked pair
            }
            //Pointing Pairs:
                //(used only if Pointing Triplets isn't enabled)
            while(!PN && PP && !solve && sudoku.solvePointingPairs()){
//                System.out.println("used solvePointingPairs");
                solve = true; //we've found at least one new pointing pair
            }
            //Pointing Triplets:
                //(used only if Pointing Pairs isn't enabled)
            while(!PN && PT && !solve && sudoku.solvePointingTriplets()){
//                System.out.println("used solvePointingTriplets");
                solve = true; //we've found at least one new pointing triplet
            }
            //Pointing Numbers:
                //(used if both Pointing Pairs and Triplets are enabled)
            while(PN && !solve && sudoku.solvePointingNumbers()){
//                System.out.println("used solvePointingNumbers");
                solve = true; //we've found at least one new pointing
                //pair or triplet
            }
        }
        //now that the solving loop is over we indicate weather the sudoku
        //is solved or not:
        if(sudoku.isSolved()){
            System.out.println("SUDOKU IS SOLVED -- "+count+" iterations");
        } else {
            System.out.println("CAN'T SOLVE ANY FURTHER -- "+count+" iterations");
        }
        //and then print it on terminal:
        sudoku.showGrid();
    }
}
