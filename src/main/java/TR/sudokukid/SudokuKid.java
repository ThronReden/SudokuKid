package TR.sudokukid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * REVISE ALL
 * 
 * @author TR
 * @date 06/OCT/26
 */
public class SudokuKid {
    
    //Boolean variables to store weather a certain methods use is enabled:
    private boolean NS = true; //weather Naked Singles use is enabled
    private boolean HS = true; //weather Hidden Singles use is enabled
    
    private boolean NP = true; //Naked Pairs
    private boolean HP = true; //Hidden Pairs
    
    private boolean PP = true; //Pointing Pairs
    private boolean PT = true; //Pointing Triplets
    
    private boolean NT = true; //Naked Triplets
    private boolean HT = true; //Hidden Triplets
    
    private Sudoku sudoku; //our Sudoku statement
    
    /* ##################################################################################################################################################
     * CONSTRUCTOR:
     * builds objects of this class.
     */
    /**
     * Mandatory empty constructor.
     */
    public SudokuKid(){}
    /**
     * Initializes this solvers sudoku attribute to a clone of a given
     * Sudoku object. This includes all plauible values information in cells.
     * 
     * @param aSudoku, a sudoku statement to solve
     */
    public SudokuKid(Sudoku aSudoku){
        //we clone the Sudoku object:
        this.sudoku = aSudoku.clone();
    }
    /**
     * Constructor overload that initializes this solvers sudoku attribute
     * to a new sudoku created from a given valid 9x9 matrix of digits.
     * Calls the Sudoku constructor that does this.
     * 
     * @param sudokuMatrix, the sudoku matrix we want to solve
     */
    public SudokuKid(int[][] sudokuMatrix){
        // We create a Sudoku object to manage the sudoku statement:
        this.sudoku = new Sudoku(sudokuMatrix);
    }
    /**
     * Constructor overload that initializes this solvers sudoku attribute
     * to a new sudoku created from a given valid String of 81 digits.
     * 
     * @param sudokuString 
     */
    public SudokuKid(String sudokuString){
        this.sudoku = new Sudoku(sudokuString);
    }
    
    /* ##################################################################################################################################################
     * MAIN SOLVING METHODS:
     * we use them to fill in digits into the grid based on the available
     * information and adding new information to the sudoku. I'm gonna try to
     * ordem them from most simple to most complicated but the middlegrounds
     * might be muddy.
     */
    //DISCLAIMER: some of them have funny names so bare with me "O3O
    /**
     * Searches both for Naked and Hidden Singles patterns, as long as they're
     * enabled in a single sweep of the sudoku.
     * It will try to solve for the whole sudoku, wont stop untill we've
     * looped throug all of its cells.
     * 
     * TOLD YOU THEY HAD FUNNY NAMES!!
     * 
     * @return true if a solve was made, false else.
     */
    public boolean solveSimpleSingles(){
        boolean solve = false; // We'll be returning this
        // We loop through the list of rows:
        for(int i = 0; i < sudoku.cells.length; i++){
            int row = i+1;
            // If a row is full we wont try to fill it:
            if(!sudoku.isRowFilled(row)){
                // Now we loop through the list of cells of this row looking
                // for empty Cells:
                for(int j = 0; j < sudoku.cells[i].length; j++){
                    int col = j+1;
                    // If the cell is empty:
                    if(!sudoku.getCell(row,col).isFilled()){
                        // We get the number of digits that fit in it:
                        int[] plausVals = 
                                sudoku.getCell(row,col).getPlausValsList();
                        // If it could only be filled with one digit:
                        if(NS && plausVals.length == 1){
                            // We fill it in
                            sudoku.fill(plausVals[0],row,col);
                            solve = true; // We've solved a Cell
                        // Else, if multiple digits fit:
                        } else if(HS && plausVals.length > 1){
                            // For each Val:
                            for(int n = 0; n <= plausVals.length; n++){
                                // We get the digit we're cheking for:
                                int val = plausVals[n];
                                // We check if it is the only Cell of its row,
                                // column or square that could contain that val:
                                if(isOnlyCellForThisValue(row,col,val)){
                                    // Then it's the only cell that can fit
                                    // that digit and we fill it in:
                                    sudoku.fill(val,row,col);
                                    solve = true; // We've solved a Cell
                                    break; // We stop this loop so no more
                                    // digits are filled in this cell, as it's
                                    // not empty anymore.
                                }
                            }
                        }
                    }
                }
            }
        }
        return solve; // We return weather we've solved or not.
    }
    /*Example Solvable Matrixs:
     * {{0,0,3,7,0,6,9,0,5},{7,5,4,9,0,8,1,3,6},{0,9,0,5,3,0,4,0,7},{5,2,0,0,6,0,8,7,4},{8,0,0,0,9,0,3,0,2},{3,0,6,0,7,2,5,1,9},{0,3,5,6,1,7,0,4,0},{2,0,0,3,0,0,7,9,1},{0,0,7,2,0,0,6,5,3}}
     * {{9,6,0,0,0,0,7,0,8},{8,0,0,0,0,4,3,0,0},{1,0,0,5,0,0,0,0,0},{0,0,0,0,0,0,1,7,6},{2,0,0,0,9,3,0,0,5},{7,0,8,0,0,0,0,0,0},{0,0,7,0,3,2,0,4,0},{3,8,2,1,0,5,6,0,0},{0,4,1,0,0,9,5,2,0}}
     * {{0,2,5,0,4,6,0,0,0},{0,0,0,0,0,0,7,0,0},{1,0,9,0,0,0,0,0,0},{0,0,0,2,9,0,0,7,4},{6,0,7,0,0,0,0,8,0},{0,0,0,0,0,0,0,0,1},{0,0,0,0,8,4,0,5,0},{0,6,8,0,0,0,2,0,0},{0,0,0,0,0,1,0,0,9}}
     */
    /*Example Unsolvable Matrixs:
     * {{0,1,4,9,2,0,0,0,8},{7,0,6,0,0,0,0,0,0},{0,0,0,0,4,1,5,0,0},{6,8,0,0,0,4,0,1,0},{0,2,0,0,7,0,0,5,0},{0,0,0,0,6,0,0,0,7},{2,0,0,0,0,0,4,0,5},{0,0,8,0,0,0,0,0,0},{0,0,0,0,9,0,2,3,0}}
     *
     */
    
    /**
     * Searches for naked pair and hidden pair patterns in groups that can
     * eliminate plausible values from some cells in the group.
     * This methods role is mearly calling the method that does this for each
     * of the groups in our sudoku: all rows, columns and squares.
     * 
     * @return true if we've added information to our sudoku, false otherwise
     */
    public boolean solveSimplePairs(){
        boolean solve = false; //we'll be returning this
        //we loop through our sudoku rows, columns and squares:
        //(group lists lengths are equal, we use row's but could use whichever)
        for(int i = 1; i <= this.sudoku.cells.length; i++){
            //we run each groups pair finding method:
            //(it'll internally check if the group is solved before begining)
            solve |= findSimplePairs(this.sudoku.getRowCells(i)); //rows
            solve |= findSimplePairs(this.sudoku.getColCells(i)); //columns
            solve |= findSimplePairs(this.sudoku.getSqrCells(i)); //squares
            //"solve |= " statement will cause our solve variable to become
            //true if we find a pair and therefore we've made progress in
            //solving the sudoku
        }
        return solve; //will be true if we're closer to solving the sudoku
    }
    /**
     * Method findSimplePairs searches for pair patterns in the group that
     * eliminate plausible values from some cells in the group.
     * Specifically, we'll search for cases in which there's a pair of cells
     * that can only be filled with the same pair, meaning the digits of the
     * pair can't be in any other cell in the group; or the only pair of cells
     * in the group that can be filled with a certain pair of numbers, meaning
     * one of the numbers goes in one and the other in the other and the rest
     * of seemingly plausible values for that pair of cells isn't really
     * plausible and can be removed.
     * 
     * @param Cell[], the group to seach in
     * @return true if we found a new pair and therefore made progress in
     * solving
     */
    private boolean findSimplePairs(Cell[] cellGroup){
        boolean solve = false; // We'll be returning this
        // If the group isn't solved:
        if(Sudoku.isGroupFilled(cellGroup)){
            return false;
        }
        int[] missingValues = getMissingDigits(cellGroup);
        // If the group is missing at least 2 digits:
        if(missingValues.length < 2){
            return false;
        }
        // For each pair of missing values:
        for(int i = 0; i < missingValues.length - 1; i++){
            for(int j = i + 1; j < missingValues.length; j++){
                int val1 = missingValues[i]; // First value of the pair
                int val2 = missingValues[j]; // second value of the pair
                // We get the cells of the group that have both values
                // as candidates:
                Cell[] plausCells = 
                        findCellsWithGivenCandidates(cellGroup, val1, val2);
                // If there's less than 2 plausible cells for this pair
                // we dont check any further:
                if(plausCells.length < 2){
                    continue;
                } 
                // We store the number of plausible cells for the first value:
                int nCellsVal1 = countCellsWithGivenCandidates(cellGroup,val1);
                // And the same for the second value:
                int nCellsVal2 = countCellsWithGivenCandidates(cellGroup,val2);
                // And also store the cells with only those two candidates:
                Cell[] cellsOnly = 
                            findCellsWithOnlyCandidates(cellGroup,val1,val2);
                // If there's other cells int the group that have
                // either of the values in the pair as candidates:
                if(NP && (nCellsVal1 > plausCells.length
                        || nCellsVal2 > plausCells.length)){
                    // but two of the cells that have both as candidates can
                    // only be filled with one or the other and not any other
                    // number (only those two candidates):
                    if(cellsOnly.length == 2){
                        // then we've found a pair and the rest of the cells in
                        // the group can't be filled with those numbers:
                        solve = true;
                        Cell[] restCells = getRestCells(cellGroup,cellsOnly);
                        for (Cell cell : restCells) {
                            cell.removeCandidates(val1, val2);
                        }
                    }
                //in the case we found only two cells and those are
                //the only cells in the group that can be filled
                //with any and both values of the pair:
                } else if(HP && plausCells.length == 2) {
                    // We check if the pair was already found before and,
                    // therefore, there's no progress in solving:
                    if(cellsOnly.length == 2){
                        continue;
                    }
                    // Then we rule out any other value we may have had
                    // stored as plausible for those two cells:
                    solve = true;
                    for (Cell cell : plausCells) {
                        cell.removeAllCandidatesBut(val1, val2);
                    }
                }
            }
        }
        return solve;
    }
    /*Example Unsolvable Matrixs:
     * {{0,1,4,9,2,0,0,0,8},{7,0,6,0,0,0,0,0,0},{0,0,0,0,4,1,5,0,0},{6,8,0,0,0,4,0,1,0},{0,2,0,0,7,0,0,5,0},{0,0,0,0,6,0,0,0,7},{2,0,0,0,0,0,4,0,5},{0,0,8,0,0,0,0,0,0},{0,0,0,0,9,0,2,3,0}}
     */
    
    /**
     * Method solvePointingNumbers searches for patterns in which the only
     * cells in a square of the sudoku that could allocate a ceirtain number
     * are in the same row or column, meaning there shouldn't be any other
     * plausible cells for that numnber in cells of that row or column outside
     * of the square.
     * This includes both pointing pairs and pointing triplets patterns.
     * 
     * @return true if we've found a new pointing pattern, false otherwise
     */
    public boolean solvePointingNumbers(){
        //we create a variable to store weather we've made a solve or not:
        boolean solve = false; //false by default
        //we loop through the squares of the sudoku:
        for(int i = 0; i < this.sudoku.sqrs.length; i++){
            //we get how many digits are missing from the square:
            int numMissingVals = this.sudoku.sqrs[i].numMissingValues();
            //we loop through each value:
            for(int n = 1; n <= numMissingVals; n++){
                //we store the value:
                int val = this.sudoku.sqrs[i].getMissingVal(n);
                //we store in an array the cells of the group that could be
                //filled with it:
                int[] plausCells = this.sudoku.sqrs[i].getPlausCellsIndex(val);
                //if it's only three or less cells:
                if((PP && plausCells.length == 2)
                || (PT && plausCells.length == 3)){
                    if(sudoku.sameRow(i,plausCells)){
                        //if the cells are in the same row
                        //we get the row index:
                        int row = sudoku.getRow(i,plausCells[0]);
                        //if there's any other cell in the row that seemingly
                        //could be filled with that digit:
                        if(plausCells.length !=
                            this.sudoku.rows[row].numPlausCells(val))
                        {
                            //we've made progress:
                            solve = true;
//                            if(plausCells.length == 2){
//                                System.out.println("\tFound Pointing Pair.\n");
//                            } else if (plausCells.length == 3){
//                                System.out.println("\tFound Pointing Triplet.\n");
//                            }
                            //and we eliminate those incorrect notes:
                            for(int j = 0; j < this.sudoku.
                                rows[row].cells.length; j++)
                            {
                                //for all cells in the same row but outside
                                //of the square:
                                if(i != sudoku.getSqr(row,j)){
                                    this.sudoku.rows[row].
                                        cells[j].removePlausible(val);
                                }
                            }
                        }
                    } else if(this.sudoku.sameCol(i,plausCells)){
                        //else if the cells are in the same column
                        //we get the column index:
                        int col = sudoku.getCol(i,plausCells[0]);
                        //if there's any other cell in the column that
                        //seemingly could be filled with that digit:
                        if(plausCells.length !=
                            this.sudoku.cols[col].numPlausCells(val))
                        {
                            //we've made progress:
                            solve = true;
                            //and we eliminate those incorrect notes:
                            for(int j = 0; j < this.sudoku.
                                cols[col].cells.length; j++)
                            {
                                //for all cells in the same column but outside
                                //of the square:
                                if(i != sudoku.getSqr(j,col)){
                                    this.sudoku.cols[col].
                                        cells[j].removePlausible(val);
                                }
                            }
                        }
                    }
                }
            }
        }
        return solve;
    }
    /*Example Solvable Matrixs:
     * {{0,1,4,9,2,0,0,0,8},{7,0,6,0,0,0,0,0,0},{0,0,0,0,4,1,5,0,0},{6,8,0,0,0,4,0,1,0},{0,2,0,0,7,0,0,5,0},{0,0,0,0,6,0,0,0,7},{2,0,0,0,0,0,4,0,5},{0,0,8,0,0,0,0,0,0},{0,0,0,0,9,0,2,3,0}}
     */
    /*Example Unsolvable Matrixs:
     * {{0,0,0,0,0,0,8,0,0},{0,4,5,0,0,0,0,0,9},{0,9,0,8,0,0,0,0,0},{1,0,0,9,0,0,6,0,0},{0,2,0,0,6,0,0,9,7},{0,0,0,0,0,1,0,0,8},{0,0,0,3,0,7,0,0,2},{0,1,0,0,2,0,0,0,0},{0,0,6,0,0,0,3,0,4}}
     */
    
    /**
     * Method solveSimpleTriplets searches for both naked and hidden triplet
     * patterns in groups that can eliminate plausible values from some cells
     * in the group.
     * This methods role is mearly calling the method that does this for each
     * of the groups in our sudoku: all rows, columns and squares.
     * 
     * @return true if we found a new triplet pattern, false otherwise
     */
    public boolean solveSimpleTriplets(){
        boolean solve = false; //we'll be returning this
        //we loop through our sudoku rows, columns and squares:
        //(group lists lengths are equal, we use row's but could use whichever)
        for(int i = 0; i < this.sudoku.rows.length; i++){
            //we run each groups triplet finding method:
            //(it'll internally check if the group is solved before begining)
            solve |= findSimpleTriplets(this.sudoku.rows[i]); //rows
            solve |= findSimpleTriplets(this.sudoku.cols[i]); //columns
            solve |= findSimpleTriplets(this.sudoku.sqrs[i]); //squares
            //"solve |= " statement will cause our solve variable to become
            //true if we find a triplet and therefore we've made progress in
            //solving the sudoku
        }
        return solve; //will be true if we're closer to solving the sudoku
    }
    /**
     * Method findSimpleTriplets searches for triplet patterns in the group
     * that eliminate plausible values from some cells in the group.
     * Specifically, we'll search for cases in which there's three cells that
     * can only be filled with the same triplet, meaning the digits of the
     * triplet can't be in any other cell in the group; or the only triplet of
     * cells in the group that can be filled with a certain triplet of numbers,
     * meaning the rest of seemingly plausible values for that triplet of cells
     * isn't really plausible and can be removed.
     * 
     * @return true if we found a new triplet and therefore made progress in
     * solving
     */
    private boolean findSimpleTriplets(CellGroup grup){
        boolean solve = false; //we'll be returning this
        //if the group isn't solved:
        if(!grup.isComplete()){
            //we get how many numbers are missing from the group:
            int n = grup.numMissingValues();
            //if it is at least 3:
            if(n > 2){
                //for each triplet of missing values:
                for(int i = 1; i < n-1; i++){
                    for(int j = i + 1; j < n; j++){
                        for(int k = j + 1; k <= n; k++){
                            int val1 = grup.getMissingVal(i);//first value of
                            //the triplet
                            int val2 = grup.getMissingVal(j);//second value of
                            //the triplet
                            int val3 = grup.getMissingVal(k);//third value of
                            //the triplet
                            //we store how many cells can be filled with the
                            //values of the triplet:
                            int numCells = grup.numPlausCells(val1,val2,val3);
                            //we store if there's cells with one plausible
                            //value but not the others:
                            boolean exAlone =
                                grup.valsExistAlone(val1,val2,val3);
                            ///if the cells that can be filled with the values
                            //of the triplet aren't the only cells in the group
                            //that can be filled with one of the values of the
                            //triplet:  
                            if(NT && numCells > 2 && exAlone){
                                //but three of the cells that can be filled
                                //with them can only be filled with them and
                                //not any other number:
                                if(grup.numCellsOnly(val1,val2,val3) == 3){
                                    //then we've found a triplet and the
                                    //rest of the cells in the group can't be
                                    //filled with those numbers:
                                    solve = true;
//                                    System.out.println("\tFound Naked Triplet.\n");
                                    //we create an array to retain the rest of
                                    //the cells:
                                    CellObsolete[] restCells =
                                        grup.getRestCells(val1, val2, val3);
                                    //we update their plausible values:
                                    for(int t = 0; t < restCells.length; t++){
                                        restCells[t].
                                        removePlausible(val1,val2,val3);
                                    }
                                }
                            //in the case we found only three cells and those
                            //are the only cells in the group that could be
                            //filled with any and all values of the triplet:
                            } else if(HT && numCells == 3 && !exAlone
                                && grup.numCellsOnly(val1,val2,val3) != 3)
                            {
                                //(second part of the condition checks if the
                                //triplet was already found before, therefore
                                //there's no progress in solving)
                                //we rule out any other value we may have had
                                //stored as plausible for those three cells:
                                solve = true;
//                                System.out.println("\tFound Hidden Triplet.\n");
                                //we create an array to retain the cells that
                                //can be filled with the values in the triplet:
                                CellObsolete[] foundCells =
                                    grup.getPlausCells(val1, val2, val3);
                                //we update their plausible values:
                                for(int t = 0; t < foundCells.length; t++){
                                    foundCells[t].
                                    removeAllPlausibleBut(val1,val2,val3);
                                }
                            }
                        }
                    }
                }
            }
        }
        return solve;
    }
    /*Example Solvable Matrixs:
     * {{0,0,0,0,0,0,8,0,0},{0,4,5,0,0,0,0,0,9},{0,9,0,8,0,0,0,0,0},{1,0,0,9,0,0,6,0,0},{0,2,0,0,6,0,0,9,7},{0,0,0,0,0,1,0,0,8},{0,0,0,3,0,7,0,0,2},{0,1,0,0,2,0,0,0,0},{0,0,6,0,0,0,3,0,4}}
     * menneske4813117
     */
    /*Example Unsolvable Matrixs:
     * There must be some...
     */
    
    public void solvingLoop(){
        //we create a Scanner object for basic user interaction:
        Scanner scanner = new Scanner(System.in);
        System.out.println("INITIAL SUDOKU STATEMENT:");
        sudoku.showGrid(); //this prints the sudoku to terminal
        scanner.nextLine(); //so there's a hold in the execution
        //we store weather a solve was made or not:
        boolean solve = true; //true by default so first iter of loop runs
        //we store weather a number was added to the grid or not:
        boolean numAdded = false; //false by default
        int superIter = 0; //we'll count how many iterations of the main loop we
        //execute
        int iter = 0; //we'll also count how many times we've looped through
        //the sudoku, or rather how many solving method calls we've made
        
        //MAIN SOLVING LOOP:
        //while the sudoku is not solved and a solve was made in the last
        //iteration, meaning we are able of solving further:
        while(!sudoku.isSolved() && solve){
            //if we added a number to the grid in the last iteration we
            //print the sudoku to terminal:
            if(numAdded){
                System.out.println("SUDOKU AT SOLVE ITER "+iter+" -- "+superIter+" main loop iterations");
                sudoku.showGrid();
                scanner.nextLine(); //we hold for user input
            }
            superIter++; //we add an iteration to the count
            solve = false; //we set solve back to false
            numAdded = false; //we set solve back to false
            boolean keepSolv; //a variable to track last solving try result
            
            /**
             * SOLVING LOOPS:
             * 
             * we check if the use of that method is enabled and, for all but
             * Simple Singles methods, if a solve was already made and then we
             * keep on using the solving algorithm until it fails and store
             * some results:
             */
            //Simple Singles:
                //(used if either Naked and Hidden Singles are enabled)
            if(isEnabledSS()){
                do{
                    iter++;
//                    System.out.println("Used solveSimpleSingles.\n");
                    keepSolv = this.solveSimpleSingles(); //we try solving
                    solve |= keepSolv; //we've solved something
                    numAdded |= keepSolv; //we've added a number to the grid
//                    if(!keepSolv){
//                        System.out.println("\tNo solve made.\n");
//                    }
                } while (keepSolv);
            }
            //Simple Pairs:
                //(used if either Naked and Hidden Pairs are enabled)
            if(isEnabledSP() && !solve){
                do{
                    iter++;
//                    System.out.println("Used solveSimplePairs.\n");
                    keepSolv = this.solveSimplePairs(); //we try solving
                    solve |= keepSolv; //we've found at least one new pair
//                    if(!keepSolv){
//                        System.out.println("\tNo solve made.\n");
//                    }
                } while (keepSolv);
            }
            //Pointing Numbers:
                //(used if either Pointing Pairs and Triplets are enabled)
            if(isEnabledPN() && !solve){
                do{
                    iter++;
//                    System.out.println("Used solvePointingNumbers.\n");
                    keepSolv = this.solvePointingNumbers(); //we try solving
                    solve |= keepSolv; //we've found at least one new pointing
                    //pair or triplet
//                    if(!keepSolv){
//                        System.out.println("\tNo solve made.\n");
//                    }
                } while (keepSolv);
            }
            //Simple Triplets:
                //(used if either Naked and Hidden Triplets are enabled)
            if(isEnabledST() && !solve){
                do{
                    iter++;
//                    System.out.println("Used solveSimpleTriplets.\n");
                    keepSolv = this.solveSimpleTriplets(); //we try solving
                    solve |= keepSolv; //we've found at least one new triplet
//                    if(!keepSolv){
//                        System.out.println("\tNo solve made.\n");
//                    }
                } while (keepSolv);
            }
        }
        //now that the solving loop is over we indicate weather the sudoku
        //is solved or not:
        String txt;
        if(sudoku.isSolved()){
            txt = "SUDOKU IS SOLVED";
        } else {
            txt = "CAN'T SOLVE ANY FURTHER";
        }
        System.out.println(txt+" -- "+iter+" solve iterations -- "+superIter+" main loop iterations");
        //and then print it on terminal:
        sudoku.show();
    }
    
    /* ##################################################################################################################################################
     * AUX METHODS:
     * Utils the main solving methods use but dont quite fit in Sudoku, as this
     * are not representation methods, this are solving methods.
     */
    
    /**
     * Checks if the cell in the given position is the only of its row, column
     * or square that can allocate a given value.
     * 
     * @param row, the cells row
     * @param col, the cells column
     * @param val, the cells square
     * @return true if it's the only cell for either its row, column or square,
     * false otherwise.
     */
    public boolean isOnlyCellForThisValue(int row, int col, int val) {
        if (this.sudoku.getCell(row, col).isFilled()) {
            return false;
        }
        // We check its row:
        if (getRestCellsInRow(row, col).stream()
                .noneMatch(c -> c.isPlausible(val))) {
            return true;
        // We check its column:
        } else if (getRestCellsInCol(row, col).stream()
                .noneMatch(c -> c.isPlausible(val))) {
            return true;
        // We check its square:
        } else if (getRestCellsInSqr(row, col).stream()
                .noneMatch(c -> c.isPlausible(val))) {
            return true;
        }
        return false;
    }
    /**
     * Returns the list of all other the cells in the row of the given cell.
     * 
     * @param row, the cells row
     * @param col, its column
     * @return a list of all the other cells in its row
     */
    private ArrayList<Cell> getRestCellsInRow(int row, int col) {
        return this.getRestCells(this.sudoku.getRowCells(row),row,col);
    }
    /**
     * Returns the list of all other the cells in the column of the given cell.
     * 
     * @param row, the cells row
     * @param col, its column
     * @return a list of all the other cells in its column
     */
    private ArrayList<Cell> getRestCellsInCol(int row, int col) {
        return this.getRestCells(this.sudoku.getColCells(col),row,col);
    }
    /**
     * Returns the list of all other the cells in the square of the given cell.
     * 
     * @param row, the cells row
     * @param col, its column
     * @return a list of all the other cells in its square 
     */
    private ArrayList<Cell> getRestCellsInSqr(int row, int col) {
        return this.getRestCells(this.sudoku.getSqrCells(row,col),row,col);
    }
    /**
     * Returns the list of all the cells in a group excluding the given cell,
     * if it exists in it.
     * 
     * @param group, the group we want
     * @param row, the cells row
     * @param col, its column
     * @return a list of all the other cells in its square 
     */
    private ArrayList<Cell> getRestCells(Cell[] group,int row, int col){
        ArrayList<Cell> restCells = new ArrayList<>();
        Collections.addAll(restCells,group);
        restCells.remove(this.sudoku.getCell(row, col));
        return restCells;
    }
    /**
     * Returns the given group with the given list of cells removed from it.
     * 
     * @param group, the initial group of cells
     * @param dontInclude, the list of cells we want to remove
     * @return a list including all other cells 
     */
    private Cell[] getRestCells(Cell[] group, Cell[] dontInclude){
        ArrayList<Cell> restCells = new ArrayList<>();
        Collections.addAll(restCells,group);
        restCells.removeAll(Arrays.asList(dontInclude));
        return restCells.toArray(Cell[]::new);
    }
    
    private Cell[] findCellsWithGivenCandidates(Cell[] group, int... vals){
        if (vals.length == 0) {
            throw new IllegalArgumentException("You must specify at least one candidate.");
        }
        ArrayList<Cell> cells = new ArrayList<>();
        Collections.addAll(cells,group);
        Cell[] cellsWithGivenCandidates = cells.stream()
                .filter(c -> c.isPlausible(vals)).toArray(Cell[]::new);
        return cellsWithGivenCandidates;
    }
    
    private int countCellsWithGivenCandidates(Cell[] group, int... vals){
        return findCellsWithGivenCandidates(group,vals).length;
    }
    
    /**
     * Finds the cells that can allocate only the given candidates in a given
     * group. 
     * 
     * @param group, the group of cells we want to search in
     * @param vals, the candidates we want to check for
     * @return a list with the found cells, if any.
     */
    private Cell[] findCellsWithOnlyCandidates(Cell[] group, int... vals){
        if (vals.length == 0) {
            throw new IllegalArgumentException("You must specify at least one candidate.");
        }
        ArrayList<Cell> cells = new ArrayList<>();
        Collections.addAll(cells,group);
        Cell[] cellsWithOnlyCandidates = cells.stream()
                .filter(c -> c.isOnlyCandidate(vals)).toArray(Cell[]::new);
        return cellsWithOnlyCandidates;
    }

    /**
     * Counts how many digits are missing from a given group of cells.
     * A valid group of cells is either a row, column or square of the sudoku.
     * 
     * @param cellGroup, the group we want to search in
     * @return the number of missing digits
     */
    private int getNumMissingDigits(Cell[] cellGroup) {
        if (cellGroup.length != 9) {
            throw new IllegalArgumentException("The given list is not a valid sudoku group.");
        }
        int count = 0;
        for (Cell cell : cellGroup) {
            if (cell.isEmpty()) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Returns a list with the missing digits for the given group of cells.
     * 
     * @param cellGroup, the group we want to search in
     * @return  a list containing its missing digits
     */
    private int[] getMissingDigits(Cell[] cellGroup) {
        if (cellGroup.length != 9) {
            throw new IllegalArgumentException("The given list is not a valid sudoku group.");
        }
        int[] missingVals = new int[getNumMissingDigits(cellGroup)];
        int index = 0;
        for (int val = 1; val <= 9; val++) {
            boolean isMissing = true;
            for (Cell cell : cellGroup) {
                isMissing &= cell.getValue() != val;
            }
            if (isMissing) {
                missingVals[index] = val;
                index++;
            }
        }
        return missingVals;
    }
    
    /* ##################################################################################################################################################
     * ACCESS METHODS:
     * Encapsulation and stuff...
     */
    
    /**
     * @return weather NS is enabled
     */
    public boolean isEnabledNS() {
        return NS;
    }

    /**
     * @param enabled, the state to set NS to
     */
    public void setNS(boolean enabled) {
        NS = enabled;
    }

    /**
     * @return weather HS is enabled
     */
    public boolean isEnabledHS() {
        return HS;
    }

    /**
     * @param enabled, the state to set HS to
     */
    public void setHS(boolean enabled) {
        HS = enabled;
    }

    /**
     * @return weather SS is enabled
     */
    public boolean isEnabledSS() {
        return NS || HS;
    }

    /**
     * @return weather NP is enabled
     */
    public boolean isEnabledNP() {
        return NP;
    }

    /**
     * @param enabled, the state to set NP to
     */
    public void setNP(boolean enabled) {
        NP = enabled;
    }

    /**
     * @return weather HP is enabled
     */
    public boolean isEnabledHP() {
        return HP;
    }

    /**
     * @param enabled, the state to set HP to
     */
    public void setHP(boolean enabled) {
        HP = enabled;
    }

    /**
     * @return weather SP is enabled
     */
    public boolean isEnabledSP() {
        return NP || HP;
    }

    /**
     * @return weather PP is enabled
     */
    public boolean isEnabledPP() {
        return PP;
    }

    /**
     * @param enabled, the state to set PP to
     */
    public void setPP(boolean enabled) {
        PP = enabled;
    }

    /**
     * @return weather PT is enabled
     */
    public boolean isEnabledPT() {
        return PT;
    }

    /**
     * @param enabled, the state to set PT to
     */
    public void setPT(boolean enabled) {
        PT = enabled;
    }

    /**
     * @return weather PN is enabled
     */
    public boolean isEnabledPN() {
        return PP || PT;
    }

    /**
     * @return weather NT is enabled
     */
    public boolean isEnabledNT() {
        return NT;
    }

    /**
     * @param enabled, the state to set NT to
     */
    public void setNT(boolean enabled) {
        NT = enabled;
    }

    /**
     * @return weather HT is enabled
     */
    public boolean isEnabledHT() {
        return HT;
    }

    /**
     * @param enabled, the state to set HT to
     */
    public void setHT(boolean enabled) {
        HT = enabled;
    }

    /**
     * @return weather ST is enabled
     */
    public boolean isEnabledST() {
        return NT || HT;
    }

    /**
     * @return our sudoku
     */
    public Sudoku getSudoku() {
        return sudoku;
    }

    /**
     * @param newSudoku the new sudoku to set
     */
    public void setSudoku(Sudoku newSudoku) {
        this.sudoku = newSudoku;
    }
    
}
