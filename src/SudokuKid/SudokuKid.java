package SudokuKid;

import java.util.Scanner;

/**
 * REVISE ALL COMMENTARY AND DOCUMENTATION
 * 
 * @author jsanchez
 */
public class SudokuKid {
    
    //Boolean variables to store weather a certain methods use is enabled:
    private static boolean NS = true; //weather Naked Singles use is enabled
    private static boolean HS = true; //weather Hidden Singles use is enabled
    private static final boolean SS = NS || HS; //Simple Singles
    
    private static boolean NP = true; //Naked Pairs
    private static boolean HP = true; //Hidden Pairs
    private static final boolean SP = NP || HP; //Simple Pairs
    
    private static boolean PP = true; //Pointing Pairs
    private static boolean PT = true; //Pointing Triplets
    private static final boolean PN = PP || PT; //Pointing Numbers
    
    private static boolean NT = true; //Naked Triplets
    private static boolean HT = true; //Hidden Triplets
    private static final boolean ST = NT || HT; //Simple Triplets
    
    private Sudoku sudoku; //our Sudoku statement
    
    /* //////////////////////////////////////////////////////////////////////
     * SOLVING METHODS:
     * we use them to fill in digits into the grid based on the
     * available information. I'm gonna try to ordem them from most
     * simple to most complicated but the middlegrounds might be muddy.
     *///////////////////////////////////////////////////////////////////////
    //DISCLAIMER: some of them have funny names so bare with me "O3O
    /**
     * Method solveAllSingles searches both for Naked and Hidden Singles
     * patterns, as long as they're enabled in a single sweep of the sudoku.
     * It will try to solve for the whole sudoku, wont stop untill we've
     * looped throug all of its cells.
     * 
     * TOLD YOU THEY HAD FUNNY NAMES!!
     * 
     * @return true if a solve was made, false else.
     */
    public boolean solveSimpleSingles(){
        boolean solve = false; //we'll be returning this
        //we loop through the list of rows:
        for(int i = 0; i < this.sudoku.rows.length; i++){
            //if a row is full we wont try to fill it:
            if(!this.sudoku.rows[i].isComplete()){
                //now we loop through the list of cells of this row looking
                //for empty Cells:
                for(int j = 0; j < this.sudoku.rows[i].cells.length; j++){
                    //if the cell is empty: 
                    if(!this.sudoku.rows[i].cells[j].isFilled()){
//                        System.out.println("Cell "+i+", "+j+" is empty.");
                        //we get the number of digits that fit in it:
                        int plausVals = 
                                this.sudoku.rows[i].
                                    cells[j].numPlausibleValues();
                        //if it could only be filled with one digit:
                        if(NS && plausVals == 1){
                            //we fill it in
                            sudoku.addNum(this.sudoku.rows[i].
                                            cells[j].getPlausVal(),i,j);
                            solve = true; //we've solved a Cell
//                            System.out.println("\tFound Naked Single.\n");
//                            System.out.println("Cell "+i+", "+j+" solved.");
                        //else, if multiple digits fit:
                        } else if(HS && plausVals > 1){
                            //we check if it is the only Cell of its row,
                            //column or square that could contain that digit:
                            int val = 0;//we'll save the plausible digits here
                            //we check for each of the plausible digits:
                            for(int n = 1; n <= plausVals; n++){
                                //we get the digit we're cheking for:
                                val = this.sudoku.rows[i].
                                        cells[j].getPlausVal(n);
//                                System.out.println("Seach groups for "+val+".");
                                
                                //if the number of cells that could be filled
                                //with that specific digit in the same row,
                                //column or square as the original cell we're
                                //trying to solve is 1:
                                if(this.sudoku.
                                    rows[i].numPlausCells(val) == 1 ||
                                this.sudoku.
                                    cols[j].numPlausCells(val) == 1 ||
                                this.sudoku.
                                    sqrs[sudoku.getSqr(i,j)].
                                        numPlausCells(val) == 1){
                                    //then it's the only cell that can fit that
                                    //digit and we fill it in:
                                    sudoku.addNum(val,i,j); //we add the digit
                                    solve = true; //we've solved a Cell
//                                    System.out.println("\tFound Hidden Single.\n");
//                                    System.out.println("Cell "+i+", "+j+" solved for "+val+".");
                                    break; //we stop this for so no more digits
                                    //are filled in this cell, as it's not
                                    //empty anymore.
                                }
                            }
                        }
                    }
                }
            }
        }
        return solve; //we return our boolean variable,
        //weather we solved or not.
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
     * Method solveSimplePairs searches for naked pair and hidden pair
     * patterns in groups that can eliminate plausible values from some cells
     * in the group.
     * This methods role is mearly calling the method that does this for each
     * of the groups in our sudoku: all rows, columns and squares.
     * 
     * @return true if we're closer to solving the sudoku, false otherwise
     */
    public boolean solveSimplePairs(){
        boolean solve = false; //we'll be returning this
        //we loop through our sudoku rows, columns and squares:
        //(group lists lengths are equal, we use row's but could use whichever)
        for(int i = 0; i < this.sudoku.rows.length; i++){
            //we run each groups pair finding method:
            //(it'll internally check if the group is solved before begining)
            solve |= findSimplePairs(this.sudoku.rows[i]); //rows
            solve |= findSimplePairs(this.sudoku.cols[i]); //columns
            solve |= findSimplePairs(this.sudoku.sqrs[i]); //squares
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
     * @param grup, the group to seach in
     * @return true if we found a new pair and therefore made progress in
     * solving
     */
    private boolean findSimplePairs(CellGroup grup){
        boolean solve = false; //we'll be returning this
        //if the group isn't solved:
        if(!grup.isComplete()){
            int n = grup.numMissingValues(); //we get how many numbers are
            //missing
            //if it is at least 2:
            if(n > 1){
                //for each pair of missing values:
                for(int i = 1; i < n; i++){
                    for(int j = i + 1; j <= n; j++){
                        int val1 = grup.getMissingVal(i); //first value of the
                        //pair
                        int val2 = grup.getMissingVal(j); //second value of the
                        //pair
                        int numCells = grup.numPlausCells(val1, val2);
                        //if the cells that can be filled with both values of
                        //the pair aren't the only cells in the group that can
                        //be filled with one of the values in the pair:
                        if(numCells > 1 && grup.valsExistAlone(val1,val2)){
                            //but two of the cells that can be filled with
                            //both can only be filled with one or the other
                            //and not any other number:
                            if(NP && grup.numCellsOnly(val1,val2) == 2){
                                //then we've found a pair and the
                                //rest of the cells in the group can't be
                                //filled with those numbers:
                                solve = true;
//                                System.out.println("\tFound Naked Pair.\n");
                                //we create an array to retain the rest of the
                                //cells:
                                Cell[] restCells =
                                    grup.getRestCells(val1, val2);
                                for(int k = 0; k < restCells.length; k++){
                                    restCells[k].removePlausible(val1,val2);
                                }
                            }
                        //in the case we found only two cells and those are
                        //the only cells in the group that can be filled
                        //with any and both values of the pair:
                        } else if(HP && numCells == 2
                            && grup.numCellsOnly(val1,val2) != 2)
                        {
                            //(second part of the condition checks if the pair
                            //was already found before, therefore there's no
                            //progress in solving)
                            //we rule out any other value we may have had
                            //stored as plausible for those two cells:
                            solve = true;
//                            System.out.println("\tFound Hidden Pair.\n");
                            //we create an array to retain the cells that can
                            //be filled with both values in the pair:
                            Cell[] foundCells =
                                grup.getPlausCells(val1, val2);
                            for(int k = 0; k < foundCells.length; k++){
                                foundCells[k].
                                removeAllPlausibleBut(val1,val2);
                            }
                        }
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
                                    Cell[] restCells =
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
                                Cell[] foundCells =
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
    
    //Stored sudoku statements (presumably free to use):
    //SOLVABLE:
//    private static final int[][] EL_PAIS_experto_2025_12_05 = {{0,1,4,9,2,0,0,0,8},{7,0,6,0,0,0,0,0,0},{0,0,0,0,4,1,5,0,0},{6,8,0,0,0,4,0,1,0},{0,2,0,0,7,0,0,5,0},{0,0,0,0,6,0,0,0,7},{2,0,0,0,0,0,4,0,5},{0,0,8,0,0,0,0,0,0},{0,0,0,0,9,0,2,3,0}};
//    private static final int[][] EL_PAIS_medio_2026_01_09 = {{0,2,5,0,4,6,0,0,0},{0,0,0,0,0,0,7,0,0},{1,0,9,0,0,0,0,0,0},{0,0,0,2,9,0,0,7,4},{6,0,7,0,0,0,0,8,0},{0,0,0,0,0,0,0,0,1},{0,0,0,0,8,4,0,5,0},{0,6,8,0,0,0,2,0,0},{0,0,0,0,0,1,0,0,9}};
//    private static final int[][] EL_PAIS_dificil_2026_01_09 = {{0,0,0,0,0,0,8,0,0},{0,4,5,0,0,0,0,0,9},{0,9,0,8,0,0,0,0,0},{1,0,0,9,0,0,6,0,0},{0,2,0,0,6,0,0,9,7},{0,0,0,0,0,1,0,0,8},{0,0,0,3,0,7,0,0,2},{0,1,0,0,2,0,0,0,0},{0,0,6,0,0,0,3,0,4}};
//    private static final int[][] EL_PAIS_experto_2026_01_16 = {{0,0,0,0,0,4,0,7,0},{0,1,0,0,0,0,2,5,0},{7,9,2,0,0,8,0,0,1},{0,4,0,1,0,0,0,0,6},{0,0,0,4,0,7,0,0,0},{1,0,0,0,0,6,0,2,0},{6,0,0,9,0,0,7,1,2},{0,7,3,0,0,0,0,9,0},{0,2,0,7,0,0,0,0,0}};
    //UNSOLVABLE:
    
    public void solvingLoop(){
        //the sudoku statement we want to solve:
        int[][] sudokuMatrix = Sudoku.toMatrix(menneske4813117);
        //we create a Sudoku object to manage it:
        this.sudoku = new Sudoku(sudokuMatrix);
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
            if(SS){
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
            if(SP && !solve){
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
            if(PN && !solve){
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
            if(ST && !solve){
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
