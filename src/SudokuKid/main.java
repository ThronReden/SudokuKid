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
    
    private static boolean NT = true; //Naked Triplets
    private static boolean HT = true; //Hidden Triplets
    private static boolean ST = NT && HT; //Simple Triplets (both previous)
    
    //Stored sudoku statements (presumably free to use):
    //SOLVABLE:
//    private static final int[][] EL_PAIS_experto_2025_12_05 = {{0,1,4,9,2,0,0,0,8},{7,0,6,0,0,0,0,0,0},{0,0,0,0,4,1,5,0,0},{6,8,0,0,0,4,0,1,0},{0,2,0,0,7,0,0,5,0},{0,0,0,0,6,0,0,0,7},{2,0,0,0,0,0,4,0,5},{0,0,8,0,0,0,0,0,0},{0,0,0,0,9,0,2,3,0}};
//    private static final int[][] EL_PAIS_medio_2026_01_09 = {{0,2,5,0,4,6,0,0,0},{0,0,0,0,0,0,7,0,0},{1,0,9,0,0,0,0,0,0},{0,0,0,2,9,0,0,7,4},{6,0,7,0,0,0,0,8,0},{0,0,0,0,0,0,0,0,1},{0,0,0,0,8,4,0,5,0},{0,6,8,0,0,0,2,0,0},{0,0,0,0,0,1,0,0,9}};
//    private static final int[][] EL_PAIS_dificil_2026_01_09 = {{0,0,0,0,0,0,8,0,0},{0,4,5,0,0,0,0,0,9},{0,9,0,8,0,0,0,0,0},{1,0,0,9,0,0,6,0,0},{0,2,0,0,6,0,0,9,7},{0,0,0,0,0,1,0,0,8},{0,0,0,3,0,7,0,0,2},{0,1,0,0,2,0,0,0,0},{0,0,6,0,0,0,3,0,4}};
//    private static final int[][] EL_PAIS_experto_2026_01_16 = {{0,0,0,0,0,4,0,7,0},{0,1,0,0,0,0,2,5,0},{7,9,2,0,0,8,0,0,1},{0,4,0,1,0,0,0,0,6},{0,0,0,4,0,7,0,0,0},{1,0,0,0,0,6,0,2,0},{6,0,0,9,0,0,7,1,2},{0,7,3,0,0,0,0,9,0},{0,2,0,7,0,0,0,0,0}};
    //UNSOLVABLE:
    
    public static void main (String[] args){
        //the sudoku statement we want to solve:
        int[][] sudokuMatrix = Sudoku.toMatrix(menneske4813117);
        //we create a Sudoku object to manage it:
        Sudoku sudoku = new Sudoku(sudokuMatrix);
        //we create a Scanner object for basic user interaction:
        Scanner scanner = new Scanner(System.in);
        System.out.println("INITIAL SUDOKU STATEMENT:");
        sudoku.showGrid(); //this prints the sudoku to terminal
        scanner.nextLine(); //so there's a hold in the execution
        //we store weather a solve was made or not:
        boolean solve = true; //true by default so first iter of loop runs
        //we store weather a number was added to the grid or not:
        boolean numAdded = false; //false by default
        int iter = 0; //we'll count how many iterations of the main loop we
        //execute
        
        //MAIN SOLVING LOOP:
        //while the sudoku is not solved and a solve was made in the last
        //iteration, meaning we are able of solving further:
        while(!sudoku.isSolved() && solve){
            //if we added a number to the grid in the last iteration we
            //print the sudoku to terminal:
            if(numAdded){
                System.out.println("SUDOKU AT ITER "+iter+":");
                sudoku.showGrid();
                scanner.nextLine(); //we hold for user input
            }
            iter++; //we add an iteration to the count
            solve = false; //we set solve back to false
            numAdded = false; //we set solve back to false
            
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
                System.out.println("Used solveSimpleSingles.\n");
                solve = true; //we've solved something
                numAdded = true; //we've added a number to the grid
            }
            //Naked Pairs:
                //(used only if Hidden Pairs isn't enabled)
            while(!SP && NP && !solve && sudoku.solveNakedPairs()){
//                System.out.println("Used solveNakedPairs.\n");
                solve = true; //we've found at least one new naked pair
            }
            //Hidden Pairs:
                //(used only if Hidden Pairs isn't enabled)
            while(!SP && HP && !solve && sudoku.solveHiddenPairs()){
//                System.out.println("Used solveHiddenPairs.\n");
                solve = true; //we've found at least one new hidden pair
            }
            //Simple Pairs:
                //(used if both Naked and Hidden Pairs are enabled)
            while(SP && !solve && sudoku.solveSimplePairs()){
                System.out.println("Used solveSimplePairs.\n");
                solve = true; //we've found at least one new pair
            }
            //Pointing Pairs:
                //(used only if Pointing Triplets isn't enabled)
            while(!PN && PP && !solve && sudoku.solvePointingPairs()){
//                System.out.println("Used solvePointingPairs.\n");
                solve = true; //we've found at least one new pointing pair
            }
            //Pointing Triplets:
                //(used only if Pointing Pairs isn't enabled)
            while(!PN && PT && !solve && sudoku.solvePointingTriplets()){
//                System.out.println("Used solvePointingTriplets.\n");
                solve = true; //we've found at least one new pointing triplet
            }
            //Pointing Numbers:
                //(used if both Pointing Pairs and Triplets are enabled)
            while(PN && !solve && sudoku.solvePointingNumbers()){
                System.out.println("Used solvePointingNumbers.\n");
                solve = true; //we've found at least one new pointing
                //pair or triplet
            }
            //Naked Triplets:
                //(used only if Hidden Triplets isn't enabled)
            while(!ST && NT && !solve && sudoku.solveNakedTriplets()){
//                System.out.println("Used solveNakedTriplets.\n");
                solve = true; //we've found at least one new naked triplet
            }
            //Hidden Triplets:
                //(used only if Hidden Triplets isn't enabled)
            while(!ST && HT && !solve && sudoku.solveHiddenTriplets()){
//                System.out.println("Used solveHiddenTriplets.\n");
                solve = true; //we've found at least one new hidden triplet
            }
            //Simple Triplets:
                //(used if both Naked and Hidden Triplets are enabled)
            while(ST && !solve && sudoku.solveSimpleTriplets()){
                System.out.println("Used solveSimpleTriplets.\n");
                solve = true; //we've found at least one new triplet
            }
        }
        //now that the solving loop is over we indicate weather the sudoku
        //is solved or not:
        if(sudoku.isSolved()){
            System.out.println("SUDOKU IS SOLVED -- "+iter+" iterations");
        } else {
            System.out.println("CAN'T SOLVE ANY FURTHER -- "+iter+" iterations");
        }
        //and then print it on terminal:
        sudoku.showGrid();
    }
    
    //OTHER STORED SUDOKU STATEMENTS (some may be copyright sensible):
    //SOLVABLE:
//    private static final int[][] claudeSudoku = {{5,3,0,0,7,0,0,0,0},{6,0,0,1,9,5,0,0,0},{0,9,8,0,0,0,0,6,0},{8,0,0,0,6,0,0,0,3},{4,0,0,8,0,3,0,0,1},{7,0,0,0,2,0,0,0,6},{0,6,0,0,0,0,2,8,0},{0,0,0,4,1,9,0,0,5},{0,0,0,0,8,0,0,7,9}};
//    private static final int[][] claudeSudoku2 = {{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,3,0,8,5},{0,0,1,0,2,0,0,0,0},{0,0,0,5,0,7,0,0,0},{0,0,4,0,0,0,1,0,0},{0,9,0,0,0,0,0,0,0},{5,0,0,0,0,0,0,7,3},{0,0,2,0,1,0,0,0,0},{0,0,0,0,4,0,0,0,9}};
//    private static final int[][] claudeSudoku4 = {{0,0,0,0,0,0,0,1,0},{0,0,0,0,0,2,0,0,3},{0,0,0,4,0,0,0,0,0},{0,0,0,0,0,0,5,0,0},{4,0,1,6,0,0,0,0,0},{0,0,7,1,0,0,0,0,0},{0,5,0,0,0,0,2,0,0},{0,0,0,0,8,0,0,4,0},{0,3,0,9,1,0,0,0,0}};
//    private static final String vopani1 = "070000043040009610800634900094052000358460020000800530080070091902100005007040802";
//    private static final String vopani2 = "301086504046521070500000001400800002080347900009050038004090200008734090007208103";
//    private static final String vopani3 = "000598004009100268807000509985703002000000005304005600200070900401906000000201040";
//    private static final String menneske843211 = "016030900000010004840200000000090000068301250001080700000004086900060000002070490";
    private static final String menneske4813117 = "300000008010500070096073210009040030007109800080030700058310920040006080900000007";
    //UNSOLVABLE:
//    private static final int[][] claudeSudoku3 = {{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,1},{0,0,0,0,0,2,0,3,0},{0,0,0,0,4,0,0,0,0},{0,0,0,5,0,0,0,0,0},{0,0,6,0,0,0,0,0,0},{0,7,0,0,0,0,0,0,0},{0,0,0,0,0,0,8,0,0},{9,0,0,0,0,0,0,0,0}};
//    private static final String menneske259058 = "000009031090803000006010200000400098060000000037050000000000700000100046201060000";
    
}