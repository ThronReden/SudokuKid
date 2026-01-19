package SudokuKid;
 
/**
 * Sudoku objects represent a whole sudoku containing 9 rows,
 * 9 columns and 9 sqares each containing 9 cells (for a total of
 * 81 cells in the sudoku) and different cell solving methods
 * based on the available information from the given numbers.
 * 
 * @author TR 
 * @version 27/NOV/25
 */
public class Sudoku {
    /* //////////////////////////////////////////////////////////////////////
     * ATTRIBUTES:
     * //////////////////////////////////////////////////////////////////////
     */
    private final CellGroup[] rows = new CellGroup[9];
    //will contain the 9 rows of the sudoku
    
    private final CellGroup[] cols = new CellGroup[9];
    //will contain the 9 columns
    
    private final CellGroup[] sqrs = new CellGroup[9];
    //will contain the 9 squares
    
    /* //////////////////////////////////////////////////////////////////////
     * CONSTRUCTOR:
     * builds objects of this class.
     * //////////////////////////////////////////////////////////////////////
     */
    /**
     * Constructor for objects of class Sudoku.
     * It initializes the lists of rows, columns and squares, creates all the
     * new cells for the sudoku and assigns them to the correct position in
     * all lists.
     */
    public Sudoku(){
        //honnestly, this is a messy one, I'm considering encapsulating
        //some of the algorithms it uses but I don't think I'll be using
        //them any other place so yup, IDK, give me a break (:

        //ROWS
        for(int i = 0; i < this.rows.length; i++){
            this.rows[i] = new CellGroup(); //For rows we'll just
            //create new cells by using the standard CellGroup
            //constructor.
            //This will create all the cells of our sudoku,
            //we won't need any more new cells.

            /* Some code I used for testing:
             * 
            System.out.println(i+" es "+this.rows[i]);
            for(int j = 0; j < this.rows.length; j++){
            System.out.println(j+" es "+this.rows[i].cells[j]);
            }
             */          
        }
        
        //COLUMNS
        Cell[] cellList; //now we declare the list we'll send to the
        //overloaded constructor with the correct cells
        for(int i = 0; i < this.cols.length; i++){
            cellList = new Cell[9]; //We initialize it in the for loop
            //so it's cleared with every iteration.
            //Just as all other lists we're using (I'm saying lists
            //for simplicity but it's actually arrays), it's length is
            //9 as it's 9 cells in every row, column and or square.
            for(int j = 0; j < this.rows.length; j++){
                cellList[j] = this.rows[j].cells[i]; //now we add to
                //our auxiliary list all the cells in the position "i",
                //the column we're looking for, from each of the rows
                //list of cells. We will be adding a reference to that
                //same cell to the list, not a copy of it,
                //this is intended.

                //Some more code I used for testing:
                //System.out.println(j+" es "+this.rows[j].cells[i]);
            }
            this.cols[i] = new CellGroup(cellList); //for every column,
            //"i", we create a CellGroup object containing as it's
            //cells the list of cells we've collected, "cellList".

            //Even more obsolete testing code:
            //System.out.println(i+" es "+this.cols[i]);
        }
        
        //SQUARES
        Cell[] cellList2; //We'll be needing two more aux lists
        Cell[] cellList3; //as we'll be filling 3 squares at the same
        //time.
        cellList = new Cell[9];  //We initialize them out of the loop
        cellList2 = new Cell[9]; //cause we don't want all iterations
        cellList3 = new Cell[9]; //to clear them, only some of them.
        //get ready now, this is some mindbreaking stuff (jk, jk):
        for(int i = 0; i < this.rows.length; i++){
            //for every of the rows in our sudoku
            for(int j = 0; j < this.rows[i].cells.length; j++){
                //we scan throug it's cells and
                if(j < 3){
                    //add the first 3 to the first aux list
                    cellList[getPosInSqr(i,j)] = this.rows[i].cells[j];
                }else if(j < 6){
                    //the next 3 to the second aux list
                    cellList2[getPosInSqr(i,j)] = this.rows[i].cells[j];
                }else{
                    //and the last 3 to the third list
                    cellList3[getPosInSqr(i,j)] = this.rows[i].cells[j];
                }
            }
            if(i%3 == 2){
                //For every 3 rows (i%3 == 2) we use our aux lists to
                //construct the respective squares with the correct
                //cells as arguments.
                this.sqrs[i-2] = new CellGroup(cellList);
                this.sqrs[i-1] = new CellGroup(cellList2);
                this.sqrs[i] = new CellGroup(cellList3);//sqrs 3, 6 & 9
                cellList = new Cell[9]; //Then, we promptly clear our
                cellList2 = new Cell[9];//auxiliary lists so there's no
                cellList3 = new Cell[9];//chance of a data bleed error.
            }
        }
        //That's it, we finaly have our sudoku with 9 rows, 9 columns
        //and 9 squares sharing cells for a total of 81 slots.
    }
    /**
     * Overloaded constructor for objects of class Sudoku that initializes the
     * sudoku with the main constructor and adds the numbers from a given
     * bidimentional array of integers to the grid. 
     * 
     * @param nums 
     */
    public Sudoku (int nums[][]){
        this();
        this.addNum(nums);
    }
    
    /* //////////////////////////////////////////////////////////////////////
     * METHODS FOR ADDING DIGITS:
     * this are not solving methods, this algorithms are suposed to add
     * the given numbers of the sudoku to the grid both one by one or
     * from a given matrix valid as a sudoku.
     * //////////////////////////////////////////////////////////////////////
     */
    /**
     * Method addNum sets the value of the cell in a given position
     * to the given value. This is not a solve, we're not checking if
     * it's right, we just add it. This method is intended for adding
     * the given digits of the puzzle to our grid.
     *
     * @param val, the value of the given digit
     * @param row, the row of the cell we want to put it into
     * @param col, the column of that cell, this gets us its position
     * in the grid
     */
    public void addNum(int val, int row, int col){
        this.rows[row].cells[col].setValue(val); //yup, simple as that.
        //If we're in a row, cell in the nth position belongs to the
        //nth column.
        this.updateAffected(val,row,col); //now we'll update the state
        //of the afected CellGroups (a row, a column and a square)
        //and Cells.
    }
    /**
     * Method addNum overload adds digits from a given 9 by 9 matrix to
     * our Sudoku. We have a specific method for ensuring the matrix is
     * valid.
     *
     * @param nums, a 9x9 matrix of integers.
     */
    public void addNum(int nums[][]){
        //we check if nums matrix is valid
        if(validSudokuMatrix(nums)){
            for(int i = 0; i < nums.length; i++){
                for(int j = 0; j < nums[i].length; j++){
                    //this next check is redundant
                    int val = nums[i][j]; //we get the digit
                    //we check if it's valid:
                    if(val > 0 & val < 10){
                        addNum(val,i,j); //we add valid digits
                    }
                }
            }
        }
    }
    
    /**
     * Method validSudokuMatrix cheks both the length of a given matrix
     * rows and columns and the digits it contains and returns true if
     * the matrix correctly represents a Sudoku: 9 rows, 9 columns and
     * all digits vary from 0 to 9, 0 representing an empty cell; and
     * there's no repeating numbers breaking basic sudoku rules.
     * It's static, as it doesn't depend on the existence of an
     * instance of Sudoku.
     *
     * @param nums, a bidimensional matrix of integers
     * @return false if the matrix isn't a valid Sudoku puzzle,
     * true otherwise.
     */
    public static boolean validSudokuMatrix(int nums[][]){
        boolean valid = true; //we'll update it according to the
        //results of some tests and retun it.
        //we check the matrix has 9 rows, no more no less.
        if(nums.length != 9){
            valid = false; //we return false if it doesn't
            return valid; //we end the execution
        }
        for(int i = 0; i < nums.length; i++){
            //we check every row has 9 columns, no more no less.
            if(nums[i].length != 9){
                valid = false;//the matrix isn't valid if any of them
                //doesn't.
                return valid; //this way we dont keep cheking, once
                //something is not valid the whole matrix is.
            }
            for(int j = 0; j < nums[i].length; j++){
                if(nums[i][j] < 0 | nums[i][j] > 9){
                    //if the digit aint valid the matrix isn't either
                    valid = false;
                    return valid; //we end the execution
                }
                else if(nums[i][j] != 0){
                    //if the digit is valid and not 0, so it's not an
                    //empty cell:
                    int val = nums[i][j]; //we store it's value
                    //we check its square:
                    int row1 = (i+1)%3+i/3*3;//we get the rows of the
                    int row2 = (i+2)%3+i/3*3;//sqr the digit's not at.
                    int col1 = (j+1)%3+j/3*3;//we get the cols of the
                    int col2 = (j+2)%3+j/3*3;//sqr the digit's not at.
                    if(nums[row1][col1] == val | nums[row1][col2] == val |
                       nums[row2][col1] == val | nums[row2][col2] == val){
                        //if there's an identic digit in the same
                        //square the matrix isn't valid.
                        valid = false;
                        return valid;
                    }
                    //and check its row and column:
                    for(int k = 0; k < nums.length; k++){
                        if(k != j & val == nums[i][k] |
                        k != i & val == nums[k][j]){
                            //if there's an identic digit in the same
                            //row or column the matrix isn't valid.
                            valid = false;
                            return valid;
                        }
                    }
                }
            }
        }
        return valid;
    }
    /*Example Valid Matrixs:
     * {{0,0,3,7,0,6,9,0,5},{7,5,4,9,0,8,1,3,6},{0,9,0,5,3,0,4,0,7},{5,2,0,0,6,0,8,7,4},{8,0,0,0,9,0,3,0,2},{3,0,6,0,7,2,5,1,9},{0,3,5,6,1,7,0,4,0},{2,0,0,3,0,0,7,9,1},{0,0,7,2,0,0,6,5,3}}
     * {{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0}}
     */
    /*Example Invalid Matrixs:
     * {{1,4,2},{3,6,8}}
     * {{0,0,3,7,0,6,9,0,5},{7,5,4,9,0,8,1,3,6},{0,9,0,5,3,0,4,0,7},{5,2,0,0,6,0,8,7,4},{8,0,0,0,9,0,3,0,2},{3,0,6,0,7,2,5,1,9},{0,3,5,6,1,7,0,4,0},{2,0,0,3,0,0,7,9,1}}
     * {{0,0,3,7,0,6,9,0,5},{7,5,4,9,0,8,1,3,6},{0,9,0,5,3,0,4,0,7},{5,2,0,0,6,0,8,7,4},{8,0,0,0,9,0,3,0,2},{3,0,6,0,7,2,5,1,9},{0,3,5,6,1,7,0,4,0},{2,0,0,3,0,0,7,9,1},{0,0,7,2,0,0,6}}
     * {{0,0,3,7,0,6,9,0,5},{7,5,4,9,8,1,3,6},{0,9,0,5,3,0,4,0,7},{5,2,0,0,6,0,8,7,4},{8,0,0,0,9,0,3,0,2},{3,0,6,0,7,2,5,1,9},{0,3,5,6,1,7,0,4,0},{2,0,0,3,0,0,7,9,1},{0,0,7,2,0,0,6,5,3}}
     */
    
    /* //////////////////////////////////////////////////////////////////////
     * COHERENCE AND DATA METHODS:
     * suposed to mantain data coherence, enure the data in every cell
     * and group of the sudoku is up to date to the latest change in
     * the grid (like a digit being filled into a cell) so that our
     * solving algorithms work on truthfull, accurate data.
     * //////////////////////////////////////////////////////////////////////
     */
    /**
     * Method updateAffected updates the state of the afected
     * CellGroups (a row, a column and a square) and Cells for a given
     * digit that's been filled into the grid.
     * In other words, we know a new digit and we're forwarding that
     * information for solving cells later on.
     *
     * @param val, the value of the given digit
     * @param row, the row of the cell it was added to
     * @param col, the column of that cell
     */
    public void updateAffected(int val, int row, int col){
        this.rows[row].update(val); //we cal the method for rows
        this.cols[col].update(val); //columns
        this.sqrs[getSqr(row,col)].update(val); //and squares
    }
    
    /**
     * Method getSqr finds the square a Cell in a given row and column
     * belongs to.
     * We encapsulated this as we may need to use it a bunch of times.
     *
     * @param row, the Cells row
     * @param col, its column
     * @return an integer from 0 to 8 corresponding to this Cells square
     * position in our sqrs array.
     * (sqrs[we get this number].cells[this Cells position in the square])
     */
    public int getSqr(int row, int col){
        //"maybe encapsulate row/3*3+col/3 as getSqr(row,col)?", we did hehe.
        int sqr = row/3*3+col/3; //the corresponding square
        return sqr; //we return it
    }
    
    /**
     * Method getPosInSqr finds the position a Cell in a given row and column
     * will be at in its square, but not the square it belongs to.
     * We encapsulated this as we may need to use it a bunch of times.
     *
     * @param row, the Cells row
     * @param col, its column
     * @return an integer from 0 to 8 corresponding to this Cells position
     * in its corresponding square.
     * (sqrs[this Cells square].cells[we get this number])
     */
    public int getPosInSqr(int row, int col){       
        int pos = row%3*3+col%3; //the poition in its square
        return pos; //we return it
        
        /* For those curious I'll explain in detail:
         * 
         * ABOUT i%3*3+j%3
         * 
         * i takes values from 0 to 8.
         * Now, i%3 is 0 for i=0, 1 for i=1, 2 for i=2 BUT
         * 0 for i=3, and 1 for i=4, and so on.
         * Then i%3*3 will loop through 0, 3 and 6.
         * 
         * We're going to be "adding cells" to 3 squares at a
         * time (actually to their asigned aux list, left to
         * right). This is due to rows having cells from 3
         * different squares (Ej: cells 1 to 3 from row 1
         * belong to sqr 1, 4 to 6 belong to sqr 2 and 7 to 9
         * belong to sqr 3. Squares are numbered from left to
         * right, from higher to lower).
         * 
         * Now, knowing i is our rows and we want cells from
         * row 1 to be the first 3 cells of our squares; cells
         * from row 2 to be 4th, 5th and 6th; cells from row
         * 3 to be 7th, 8th and 9th; and then back to 1st, 2nd
         * and 3rd for row 4 this makes sense, as we want our
         * first cell from each row to be added either to the
         * 1st [0], 4th [3] or 7th [6] position of the aux
         * lists.
         * 
         * Then j%3, also looping through 0, 1 and 2, lets us
         * add the cell to the desired column in the square.
         * 
         * This is how the operand i%3*3+j%3 lets us assign
         * every cell to the correct place in a square.
         * 
         * (I'm now thinking, ¿maybe there's no reason to have
         * cells in squares sorted? Anyways, ours are.)
         * ~months later~
         * (Thinking further, we very much need them sorted or at
         * the very least it makes our lifes easyer.)
         */
    }
    
    /**
     * Method getRow finds the row a Cell in a given position of a given
     * square belongs to.
     * 
     * @param sqr, the Cells square
     * @param pos, its position in the square
     * @return an integer from 0 to 8 corresponding to this Cells row
     * position in our rows array.
     */
    public int getRow(int sqr, int pos){
        int row = sqr/3*3+pos/3; //the corresponding row
        return row; //we return it
    }
    
    /**
     * Method sameRow checks if a group of cells from a given square and
     * their position in it are in the same row of the sudoku.
     * 
     * @param sqr, the square of origin of the cells
     * @param cells, a list with the positions of the cells in the square
     * @return true if the cells are in the same row, false otherwise
     */
    public boolean sameRow(int sqr, int[] cells){
        boolean same = true; //we'll be returning this variable
        for(int j = 1; j < cells.length; j++){
            //we asign with &= so it'll remain true if the cell is in the same
            //row and it was already true but turn false if any of the cells
            //is in a different row:
            same &=
            getRow(sqr,cells[j]) == getRow(sqr,cells[j-1]);
        }
        return same;
    }
    
    /**
     * Method getCol finds the column a Cell in a given position of a given
     * square belongs to.
     * 
     * @param sqr, the Cells square
     * @param pos, its position in the square
     * @return an integer from 0 to 8 corresponding to this Cells column
     * position in our cols array.
     */
    public int getCol(int sqr, int pos){
        int col = sqr%3*3+pos%3; //the corresponding column
        return col; //we return it
    }
    
    /**
     * Method sameCol checks if a group of cells from a given square and
     * their position in it are in the same column of the sudoku.
     * 
     * @param sqr, the square of origin of the cells
     * @param cells, a list with the positions of the cells in the square
     * @return true if the cells are in the same column, false otherwise
     */
    public boolean sameCol(int sqr, int[] cells){
        boolean same = true; //we'll be returning this variable
        for(int j = 1; j < cells.length; j++){
            //we asign with &= so it'll remain true if the cell is in the same
            //column and it was already true but turn false if any of the cells
            //is in a different column:
            same &=
            getCol(sqr,cells[j]) == getCol(sqr,cells[j-1]);
        }
        return same;
    }
    
    /**
     * Method isSolved checks weather the sudoku is solved or not, simple
     * enough. This is achieved by checking both if all cells are filled and
     * if sudoku rules are respected all throughout the grid, meaning we have
     * not made a mistake with any of the numbers added.
     * 
     * @return true if the sudoku is correctly solved, false otherwise
     */
    public boolean isSolved(){
        //we call the methods that check both conditions mentioned earlier:
        return this.isFilled() && this.isCorrect();
    }
    
    /**
     * Method isFilled checks if all cells of the sudoku are filled with a
     * digit.
     * It loops through the full sudoku and fails if any of its cells is
     * empty.
     * 
     * @return true if the sudoku is entirely filled, false otherwise
     */
    public boolean isFilled(){
        //the variable we'll return later, true by default:
        boolean filled = true;
        //we loop through the sudoku:   (i for rows and j for columns)
        for(int i = 0; i < this.rows.length; i++) {
            for(int j = 0; j < this.rows[i].cells.length; j++) {
                //we asign with &= so it'll remain true if the cell is filled
                //and it was already true but turn false if any of the cells
                //is empty:
                filled &= this.rows[i].cells[j].isFilled();
                //we can finish execution early if a single cell is not filled:
                if(!filled){
                    return filled;
                }
            }
        }
        //we return our checking variable:
        return filled;
    }
    
    /**
     * Method isCorrect checks if all filled cells respect basic sudoku rules.
     * This means every digit appears, if any, a single time in every row,
     * column and square. If it's solved every digit from 1 to 9 will appear
     * only once in each cell group and if it's not solved it may not appear
     * at all in some of them but there can never be multiple instances of the
     * same digit in the same group.
     * As we already have a method that does this with 9x9 integer matrixs
     * we'll use that.
     * 
     * @return true if sudoku rules are respected all throughout the grid,
     * false otherwise.
     */
    public boolean isCorrect(){
        //we convert the sudoku to a 9x9 matrix with .toMatrix() and call the
        //validSudokuMatrix with it:
        return Sudoku.validSudokuMatrix(this.toMatrix());
    }
    
    /* //////////////////////////////////////////////////////////////////////
     * SOLVING METHODS:
     * we use them to fill in digits into the grid based on the
     * available information. I'm gonna try to ordem them from most
     * simple to most complicated but the middlegrounds might be muddy.
     * //////////////////////////////////////////////////////////////////////
     */
    //DISCLAIMER: some of them have funny names so bare with me "O3O
    /**
     * Method solveAllSingles joins Naked Singles and Hidden Singles methods
     * in a single sweep of the sudoku.
     * It will try to solve for the whole sudoku, wont stop untill we've
     * looped throug all of its cells.
     * 
     * @return true if a solve was made, false else.
     */
    public boolean solveSimpleSingles(){
        boolean solve = false; //we'll be returning this
        //we loop through the list of rows:
        for(int i = 0; i < this.rows.length; i++){
            //if a row is full we wont try to fill it:
            if(!this.rows[i].isComplete()){
                //now we loop through the list of cells of this row looking
                //for empty Cells:
                for(int j = 0; j < this.rows[i].cells.length; j++){
                    //if the cell is empty: 
                    if(!this.rows[i].cells[j].isFilled()){
                        //System.out.println("Cell "+i+", "+j+" is empty.");
                        //we get the number of digits that fit in it:
                        int plausVals = 
                                this.rows[i].cells[j].numPlausibleValues();
                        //if it could only be filled with one digit:
                        if(plausVals == 1){
                            //we fill it in
                            addNum(this.rows[i].cells[j].getPlausVal(),i,j);
                            //System.out.println("Cell "+i+", "+j+" solved.");
                            solve = true; //we've solved a Cell
                        //else, if multiple digits fit:
                        } else {
                            //we check if it is the only Cell of its row,
                            //column or square that could contain that digit:
                            int val = 0;//we'll save the plausible digits here
                            //we check for each of the plausible digits:
                            for(int n = 1; n <= plausVals; n++){
                                //we get the digit we're cheking for:
                                val = this.rows[i].cells[j].getPlausVal(n);
                                //System.out.println("Seach groups for "+val+".");
                                
                                //if the number of cells that could be filled
                                //with that specific digit in the same row,
                                //column or square as the original cell we're
                                //trying to solve is 1:
                                if(this.rows[i].numPlausCells(val) == 1 ||
                                this.cols[j].numPlausCells(val) == 1 ||
                                this.sqrs[getSqr(i,j)].numPlausCells(val) == 1){
                                    //then it's the only cell that can fit that
                                    //digit and we fill it in:
                                    addNum(val,i,j); //we add the digit
                                    //System.out.println("Cell "+i+", "+j+" solved for "+val+".");
                                    solve = true; //we've solved a Cell
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
     * Method solveNakedSingles is the simplest solving method, based
     * solely on basic sudoku rules aplied to rows, columns and squares.
     * It will try to solve for the whole sudoku, wont stop untill we've
     * looped throug all of its cells.
     * 
     * TOLD YOU THEY HAD FUNNY NAMES!!
     * 
     * @return true if a solve was made, false else.
     */
    public boolean solveNakedSingles(){
        boolean solve = false; //we'll be returning this
        //we loop through the list of rows:
        for(int i = 0; i < this.rows.length; i++){
            //if a row is full we wont try to fill it:
            if(!this.rows[i].isComplete()){
                //now we loop through the list of cells of this row looking
                //for empty Cells:
                for(int j = 0; j < this.rows[i].cells.length; j++){
                    //if the cell is empty: 
                    if(!this.rows[i].cells[j].isFilled()){
                        //System.out.println("Cell "+i+", "+j+" is empty.");
                        //we get the number of digits that fit in it:
                        int plausVals = 
                                this.rows[i].cells[j].numPlausibleValues();
                        //if it could only be filled with one digit:
                        if(plausVals == 1){
                            //we fill it in
                            addNum(this.rows[i].cells[j].getPlausVal(),i,j);
                            //System.out.println("Cell "+i+", "+j+" solved.");
                            solve = true; //we've solved a Cell
                        }
                    }
                }
            }
        }
        return solve; //we return our boolean variable,
        //weather we solved or not.
    }
    /**
     * Method solveHiddenSingles is the second simplest solving method, it
     * finds if a cells is the only in the row, column or square that could
     * be filled with a ceirtain digit and fills it in, in case it is.
     * It will try to solve for the whole sudoku, wont stop untill we've
     * looped throug all of its cells.
     * 
     * @return true if a solve was made, false else.
     */
    public boolean solveHiddenSingles(){
        boolean solve = false; //we'll be returning this
        //we loop through the list of rows:
        for(int i = 0; i < this.rows.length; i++){
            //if a row is full we wont try to fill it:
            if(!this.rows[i].isComplete()){
                //now we loop through the list of cells of this row looking
                //for empty Cells:
                for(int j = 0; j < this.rows[i].cells.length; j++){
                    //if the cell is empty: 
                    if(!this.rows[i].cells[j].isFilled()){
                        //System.out.println("Cell "+i+", "+j+" is empty.");
                        //we get the number of digits that fit in it:
                        int plausVals = 
                                this.rows[i].cells[j].numPlausibleValues();
                        //if multiple digits fit:
                        if(plausVals > 1){
                            //we check if it is the only Cell of its row,
                            //column or square that could contain that digit:
                            int val = 0;//we'll save the plausible digits here
                            //we check for each of the plausible digits:
                            for(int n = 1; n <= plausVals; n++){
                                //we get the digit we're cheking for:
                                val = this.rows[i].cells[j].getPlausVal(n);
                                //System.out.println("Seach groups for "+val+".");
                                
                                //if the number of cells that could be filled
                                //with that specific digit in the same row,
                                //column or square as the original cell we're
                                //trying to solve is 1:
                                if(this.rows[i].numPlausCells(val) == 1 ||
                                this.cols[j].numPlausCells(val) == 1 ||
                                this.sqrs[getSqr(i,j)].numPlausCells(val) == 1){
                                    //then it's the only cell that can fit that
                                    //digit and we fill it in:
                                    addNum(val,i,j); //we add the digit
                                    //System.out.println("Cell "+i+", "+j+" solved for "+val+".");
                                    solve = true; //we've solved a Cell
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
        for(int i = 0; i < this.rows.length; i++){
            //we run each groups pair finding method:
            //(it'll internally check if the group is solved before begining)
            solve |= this.rows[i].findSimplePairs(); //rows
            solve |= this.cols[i].findSimplePairs(); //columns
            solve |= this.sqrs[i].findSimplePairs(); //squares
            //"solve |= " statement will cause our solve variable to become
            //true if we find a pair and therefore we've made progress in
            //solving the sudoku
        }
        return solve; //will be true if we're closer to solving the sudoku
    }
    /*Example Unsolvable Matrixs:
     * {{0,1,4,9,2,0,0,0,8},{7,0,6,0,0,0,0,0,0},{0,0,0,0,4,1,5,0,0},{6,8,0,0,0,4,0,1,0},{0,2,0,0,7,0,0,5,0},{0,0,0,0,6,0,0,0,7},{2,0,0,0,0,0,4,0,5},{0,0,8,0,0,0,0,0,0},{0,0,0,0,9,0,2,3,0}}
     */
    /**
     * Method solveNakedPairs searches for naked pair patterns in groups that
     * can eliminate plausible values from some cells in the group.
     * This methods role is mearly calling the method that does this for each
     * of the groups in our sudoku: all rows, columns and squares.
     * 
     * @return true if we're closer to solving the sudoku, false otherwise
     */
    public boolean solveNakedPairs(){
        boolean solve = false; //we'll be returning this
        //we loop through our sudoku rows, columns and squares:
        //(group lists lengths are equal, we use row's but could use whichever)
        for(int i = 0; i < this.rows.length; i++){
            //we run each groups pair finding method:
            //(it'll internally check if the group is solved before begining)
            solve |= this.rows[i].findNakedPairs(); //rows
            solve |= this.cols[i].findNakedPairs(); //columns
            solve |= this.sqrs[i].findNakedPairs(); //squares
            //"solve |= " statement will cause our solve variable to become
            //true if we find a pair and therefore we've made progress in
            //solving the sudoku
        }
        return solve; //will be true if we're closer to solving the sudoku
    }
    /**
     * Method solveHiddenPairs searches for hidden pair patterns in groups
     * that can eliminate plausible values from some cells in the group.
     * This methods role is mearly calling the method that does this for each
     * of the groups in our sudoku: all rows, columns and squares.
     * 
     * @return true if we're closer to solving the sudoku, false otherwise
     */
    public boolean solveHiddenPairs(){
        boolean solve = false; //we'll be returning this
        //we loop through our sudoku rows, columns and squares:
        //(group lists lengths are equal, we use row's but could use whichever)
        for(int i = 0; i < this.rows.length; i++){
            //we run each groups pair finding method:
            //(it'll internally check if the group is solved before begining)
            solve |= this.rows[i].findHiddenPairs(); //rows
            solve |= this.cols[i].findHiddenPairs(); //columns
            solve |= this.sqrs[i].findHiddenPairs(); //squares
            //"solve |= " statement will cause our solve variable to become
            //true if we find a pair and therefore we've made progress in
            //solving the sudoku
        }
        return solve; //will be true if we're closer to solving the sudoku
    }
    
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
        for(int i = 0; i < this.sqrs.length; i++){
            //we get how many digits are missing from the square:
            int numMissingVals = this.sqrs[i].numMissingValues();
            //we loop through each value:
            for(int n = 1; n <= numMissingVals; n++){
                //we store the value:
                int val = this.sqrs[i].getMissingVal(n);
                //we store in an array the cells of the group that could be
                //filled with it:
                int[] plausCells = this.sqrs[i].getPlausCellsIndex(val);
                //if it's only three or less cells:
                if(plausCells.length <= 3){
                    if(this.sameRow(i,plausCells)){
                        //if the cells are in the same row
                        //we get the row index:
                        int row = getRow(i,plausCells[0]);
                        //if there's any other cell in the row that seemingly
                        //could be filled with that digit:
                        if(plausCells.length
                        != this.rows[row].numPlausCells(val)){
                            //we've made progress:
                            solve = true;
                            //and we eliminate those incorrect notes:
                            for(int j = 0; j < this.rows[row].cells.length;
                            j++){
                                //for all cells in the same row but outside
                                //of the square:
                                if(i != getSqr(row,j)){
                                    this.rows[row].
                                        cells[j].removePlausible(val);
                                }
                            }
                        }
                    } else if(this.sameCol(i,plausCells)){
                        //else if the cells are in the same column
                        //we get the column index:
                        int col = getCol(i,plausCells[0]);
                        //if there's any other cell in the column that
                        //seemingly could be filled with that digit:
                        if(plausCells.length
                        != this.cols[col].numPlausCells(val)){
                            //we've made progress:
                            solve = true;
                            //and we eliminate those incorrect notes:
                            for(int j = 0; j < this.cols[col].cells.length;
                            j++){
                                //for all cells in the same column but outside
                                //of the square:
                                if(i != getSqr(j,col)){
                                    this.cols[col].
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
     * Method solvePointingPairs searches for patterns in which the only two
     * cells in a square of the sudoku that could allocate a ceirtain number
     * are in the same row or column, meaning there shouldn't be any other
     * plausible cells for that numnber in cells of that row or column outside
     * of the square.
     * This includes only pointing pairs patterns.
     * 
     * @return true if we've found a new pointing pair, false otherwise
     */
    public boolean solvePointingPairs(){
        //we create a variable to store weather we've made a solve or not:
        boolean solve = false; //false by default
        //we loop through the squares of the sudoku:
        for(int i = 0; i < this.sqrs.length; i++){
            //we get how many digits are missing from the square:
            int numMissingVals = this.sqrs[i].numMissingValues();
            //we loop through each value:
            for(int n = 1; n <= numMissingVals; n++){
                //we store the value:
                int val = this.sqrs[i].getMissingVal(n);
                //we store in an array the cells of the group that could be
                //filled with it:
                int[] plausCells = this.sqrs[i].getPlausCellsIndex(val);
                //if it's exactly two cells:
                if(plausCells.length == 2){
                    if(this.sameRow(i,plausCells)){
                        //if the cells are in the same row
                        //we get the row index:
                        int row = getRow(i,plausCells[0]);
                        //if there's any other cell in the row that seemingly
                        //could be filled with that digit:
                        if(plausCells.length
                        != this.rows[row].numPlausCells(val)){
                            //we've made progress:
                            solve = true;
                            //and we eliminate those incorrect notes:
                            for(int j = 0; j < this.rows[row].cells.length;
                            j++){
                                //for all cells in the same row but outside
                                //of the square:
                                if(i != getSqr(row,j)){
                                    this.rows[row].
                                        cells[j].removePlausible(val);
                                }
                            }
                        }
                    } else if(this.sameCol(i,plausCells)){
                        //else if the cells are in the same column
                        //we get the column index:
                        int col = getCol(i,plausCells[0]);
                        //if there's any other cell in the column that
                        //seemingly could be filled with that digit:
                        if(plausCells.length
                        != this.cols[col].numPlausCells(val)){
                            //we've made progress:
                            solve = true;
                            //and we eliminate those incorrect notes:
                            for(int j = 0; j < this.cols[col].cells.length;
                            j++){
                                //for all cells in the same column but outside
                                //of the square:
                                if(i != getSqr(j,col)){
                                    this.cols[col].
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
    /**
     * Method solvePointingTriplets searches for patterns in which the only
     * three cells in a square of the sudoku that could allocate a ceirtain
     * number are in the same row or column, meaning there shouldn't be any
     * other plausible cells for that numnber in cells of that row or column
     * outside of the square.
     * This includes only pointing triplets patterns.
     * 
     * @return true if we've found a new pointing triplet, false otherwise
     */
    public boolean solvePointingTriplets(){
        //we create a variable to store weather we've made a solve or not:
        boolean solve = false; //false by default
        //we loop through the squares of the sudoku:
        for(int i = 0; i < this.sqrs.length; i++){
            //we get how many digits are missing from the square:
            int numMissingVals = this.sqrs[i].numMissingValues();
            //we loop through each value:
            for(int n = 1; n <= numMissingVals; n++){
                //we store the value:
                int val = this.sqrs[i].getMissingVal(n);
                //we store in an array the cells of the group that could be
                //filled with it:
                int[] plausCells = this.sqrs[i].getPlausCellsIndex(val);
                //if it's exaclt three cells:
                if(plausCells.length == 3){
                    if(this.sameRow(i,plausCells)){
                        //if the cells are in the same row
                        //we get the row index:
                        int row = getRow(i,plausCells[0]);
                        //if there's any other cell in the row that seemingly
                        //could be filled with that digit:
                        if(plausCells.length
                        != this.rows[row].numPlausCells(val)){
                            //we've made progress:
                            solve = true;
                            //and we eliminate those incorrect notes:
                            for(int j = 0; j < this.rows[row].cells.length;
                            j++){
                                //for all cells in the same row but outside
                                //of the square:
                                if(i != getSqr(row,j)){
                                    this.rows[row].
                                        cells[j].removePlausible(val);
                                }
                            }
                        }
                    } else if(this.sameCol(i,plausCells)){
                        //else if the cells are in the same column
                        //we get the column index:
                        int col = getCol(i,plausCells[0]);
                        //if there's any other cell in the column that
                        //seemingly could be filled with that digit:
                        if(plausCells.length
                        != this.cols[col].numPlausCells(val)){
                            //we've made progress:
                            solve = true;
                            //and we eliminate those incorrect notes:
                            for(int j = 0; j < this.cols[col].cells.length;
                            j++){
                                //for all cells in the same column but outside
                                //of the square:
                                if(i != getSqr(j,col)){
                                    this.cols[col].
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
        for(int i = 0; i < this.rows.length; i++){
            //we run each groups triplet finding method:
            //(it'll internally check if the group is solved before begining)
            solve |= this.rows[i].findSimpleTriplets(); //rows
            solve |= this.cols[i].findSimpleTriplets(); //columns
            solve |= this.sqrs[i].findSimpleTriplets(); //squares
            //"solve |= " statement will cause our solve variable to become
            //true if we find a triplet and therefore we've made progress in
            //solving the sudoku
        }
        return solve; //will be true if we're closer to solving the sudoku
    }
    /*Example Solvable Matrixs:
     * {{0,0,0,0,0,0,8,0,0},{0,4,5,0,0,0,0,0,9},{0,9,0,8,0,0,0,0,0},{1,0,0,9,0,0,6,0,0},{0,2,0,0,6,0,0,9,7},{0,0,0,0,0,1,0,0,8},{0,0,0,3,0,7,0,0,2},{0,1,0,0,2,0,0,0,0},{0,0,6,0,0,0,3,0,4}}
     * menneske4813117
     */
    /*Example Unsolvable Matrixs:
     * There must be some...
     */
    /**
     * Method solveNakedTriplets searches only for naked triplet patterns in
     * groups that can eliminate plausible values from some cells in the group.
     * This methods role is mearly calling the method that does this for each
     * of the groups in our sudoku: all rows, columns and squares.
     * 
     * @return true if we found a new naked triplet pattern, false otherwise
     */
    public boolean solveNakedTriplets(){
        boolean solve = false; //we'll be returning this
        //we loop through our sudoku rows, columns and squares:
        //(group lists lengths are equal, we use row's but could use whichever)
        for(int i = 0; i < this.rows.length; i++){
            //we run each groups triplet finding method:
            //(it'll internally check if the group is solved before begining)
            solve |= this.rows[i].findNakedTriplets(); //rows
            solve |= this.cols[i].findNakedTriplets(); //columns
            solve |= this.sqrs[i].findNakedTriplets(); //squares
            //"solve |= " statement will cause our solve variable to become
            //true if we find a triplet and therefore we've made progress in
            //solving the sudoku
        }
        return solve; //will be true if we're closer to solving the sudoku
    }
    /**
     * Method solveNakedTriplets searches only for hidden triplet patterns in
     * groups that can eliminate plausible values from some cells in the group.
     * This methods role is mearly calling the method that does this for each
     * of the groups in our sudoku: all rows, columns and squares.
     * 
     * @return true if we found a new hidden triplet pattern, false otherwise
     */
    public boolean solveHiddenTriplets(){
        boolean solve = false; //we'll be returning this
        //we loop through our sudoku rows, columns and squares:
        //(group lists lengths are equal, we use row's but could use whichever)
        for(int i = 0; i < this.rows.length; i++){
            //we run each groups triplet finding method:
            //(it'll internally check if the group is solved before begining)
            solve |= this.rows[i].findHiddenTriplets(); //rows
            solve |= this.cols[i].findHiddenTriplets(); //columns
            solve |= this.sqrs[i].findHiddenTriplets(); //squares
            //"solve |= " statement will cause our solve variable to become
            //true if we find a triplet and therefore we've made progress in
            //solving the sudoku
        }
        return solve; //will be true if we're closer to solving the sudoku
    }
    
    /* //////////////////////////////////////////////////////////////////////
     * OTHER METHODS:
     * other functionalities such as toString methods.
     * //////////////////////////////////////////////////////////////////////
     */
    /**
     * Method toString converts our Sudoku into a String in the format of a
     * valid Sudoku matrix (a 9x9 java array matrix).
     *
     * @return text, a String that represents our Sudoku.
     */
    public String toString(){
        String text = "{"; //we open our 2d array
        for(int i = 0; i < this.rows.length; i++){
            if(i == 0){
                text += "{"; //we open the first contained array of numbers
            } else {
                text += ",{"; //we open the rest of them
            }
            //in each contained array of nubers
            for(int j = 0; j < this.rows[i].cells.length; j++){
                //we concatenate each digit
                text += this.rows[i].cells[j].value;
                if(j == this.rows[i].cells.length-1){
                    text += "}"; //if its the last of the array we close it
                } else {
                    text += ","; //else we add a coma between them
                }
            }
        }
        return text+"}"; //we close the main array
    }
    
    /**
     * Method toMatrix converts our Sudoku into an int[][] Sudoku matrix,
     * a 9x9 bidimentional java array of integers where the numbers correlate
     * to the value of the cell in that position in the Sudoku.
     *
     * @return matrix, an int[][] that represents our Sudoku.
     */
    public int[][] toMatrix(){
        //we create our 9x9 int matrix:
        int[][] matrix = new int[9][9];
        //we loop through the sudoku:
        for(int i = 0; i < this.rows.length; i++){
            for(int j = 0; j < this.rows[i].cells.length; j++){
                //we add each cells digit to it's cells position
                //of the matrix:
                matrix[i][j] = this.rows[i].cells[j].value;
            }
        }
        //we return our sudoku matrix:
        return matrix;
    }
    /**
     * Method toMatrix overload finds if a String contains a succession of
     * digits valid as a sudoku statement and converts it to the format of a
     * 9x9 bidimentional array of integers.
     * 
     * @param nums, the String of digits to convert, in case it's valid
     * @return a bidimentional array sudoku statement from the String
     */
    public static int[][] toMatrix(String nums){
        nums = nums.replaceAll(" ","");
        //we create our 9x9 int matrix:
        int[][] matrix = new int[9][9];
        if(nums.matches("^\\d{81}$")){
            //we loop through the String:
            for(int i = 0; i < nums.length(); i++){
                //this doesn't feel too clean but was the only way I could
                //make it work as intended:
                matrix[i/9][i%9] = Integer.parseInt(nums.charAt(i)+"");
            }
        }
        //we return our sudoku matrix:
        return matrix;
    }
    
    /**
     * Method showSudoku prints on terminal a visual representation of the
     * Sudoku. This lets us see the sudoku in the way it's usually depicted.
     */
    public void showGrid(){
        //we loop through each row of the sudoku:
        for(int i = 0; i < this.rows.length; i++){
            //we delimitate the rows:
            System.out.println("+---+---+---++---+---+---++---+---+---+");
            if(i % 3 == 0 && i != 0){
                //double delimitation inbetween rows of different squares:
                System.out.println("+---+---+---++---+---+---++---+---+---+");
            }
            //we initialize our row pattern:
            String pattern = "| ";
            //we loop through the rows columns:
            for(int j = 0; j < this.rows[i].cells.length; j++){
                String val = " "; //default blank space for empty cells
                //if it's not empty:
                if(this.rows[i].cells[j].value != 0){
                    //we change val to its value to string:
                    val = String.valueOf(this.rows[i].cells[j].value);
                }
                if(j % 3 == 2 && j != 8){
                    //double separation in between columns of different
                    //squares:
                    pattern += val + " || ";  
                } else {
                    //separation in between the rest of columns:
                    pattern += val + " | ";  
                }
            }
            //we print the pattern of the row:
            System.out.println(pattern);
        }
        //we print the last delimitation:
        System.out.println("+---+---+---++---+---+---++---+---+---+");
        //done!
    }
}