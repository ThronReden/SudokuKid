
/**
 * Sudoku objects represent a whole sudoku containing 9 rows,
 * 9 columns and 9 sqares each containing 9 cells (for a total of
 * 81 cells in the sudoku) and different cell solving methods
 * based on the available information from the given numbers.
 * 
 * @author TR 
 * @version 30/OCT/25
 */
public class Sudoku
{
    /* //////////////////////////////////////////////////////////////////////
     * ATTRIBUTES:
     * //////////////////////////////////////////////////////////////////////
     */
    private CellGroup rows[] = new CellGroup [9];
    //will contain the 9 rows of the sudoku
    private CellGroup cols[] = new CellGroup [9];
    //will contain the 9 columns
    private CellGroup sqrs[] = new CellGroup [9];
    //will contain the 9 squares
    
    /* //////////////////////////////////////////////////////////////////////
     * CONSTRUCTOR:
     * //////////////////////////////////////////////////////////////////////
     */
    /**
     * Constructor for objects of class Sudoku
     * 
     * honnestly, this is a messy one, I'm considering encapsulating
     * some of the algorithms it uses but I don't think I'll be using
     * them any other place so yup, IDK, give me a break (:
     */
    public Sudoku()
    {
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
        Cell cellList[]; //now we declare the list we'll send to the
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
        Cell cellList2[]; //We'll be needing two more aux lists
        Cell cellList3[]; //as we'll be filling 3 squares at the same
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
    public void addNum(int val, int row, int col)
    {
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
    public void addNum(int nums[][])
    {
        //we check if nums matrix is valid
        if(validSudokuMatrix(nums)){
            for(int i = 0; i < nums.length; i++){
                for(int j = 0; j < nums[i].length; j++){
                    //this next check is redundant
                    if(nums[i][j] > 0 & nums[i][j] < 10){
                        addNum(nums[i][j],i,j); //we add valid digits
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
    public static boolean validSudokuMatrix(int nums[][])
    {
        boolean valid = true; //we'll update it according to the
        //results of some tests and retun it.
        //we check the matrix has 9 rows, no more no less.
        if(nums.length != 9){
            valid = false; //we return false if it doesn't
            return valid;
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
                    return valid;
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
                    if(nums[row1][col1] == val |
                       nums[row1][col2] == val |
                       nums[row2][col1] == val |
                       nums[row2][col2] == val){
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
    //Example Valid Matrixs:
    //{{0,0,3,7,0,6,9,0,5},{7,5,4,9,0,8,1,3,6},{0,9,0,5,3,0,4,0,7},{5,2,0,0,6,0,8,7,4},{8,0,0,0,9,0,3,0,2},{3,0,6,0,7,2,5,1,9},{0,3,5,6,1,7,0,4,0},{2,0,0,3,0,0,7,9,1},{0,0,7,2,0,0,6,5,3}}
    //{{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0}}
    //Example Invalid Matrixs:
    //{{1,4,2},{3,6,8}}
    //{{0,0,3,7,0,6,9,0,5},{7,5,4,9,0,8,1,3,6},{0,9,0,5,3,0,4,0,7},{5,2,0,0,6,0,8,7,4},{8,0,0,0,9,0,3,0,2},{3,0,6,0,7,2,5,1,9},{0,3,5,6,1,7,0,4,0},{2,0,0,3,0,0,7,9,1}}
    //{{0,0,3,7,0,6,9,0,5},{7,5,4,9,0,8,1,3,6},{0,9,0,5,3,0,4,0,7},{5,2,0,0,6,0,8,7,4},{8,0,0,0,9,0,3,0,2},{3,0,6,0,7,2,5,1,9},{0,3,5,6,1,7,0,4,0},{2,0,0,3,0,0,7,9,1},{0,0,7,2,0,0,6}}
    //{{0,0,3,7,0,6,9,0,5},{7,5,4,9,8,1,3,6},{0,9,0,5,3,0,4,0,7},{5,2,0,0,6,0,8,7,4},{8,0,0,0,9,0,3,0,2},{3,0,6,0,7,2,5,1,9},{0,3,5,6,1,7,0,4,0},{2,0,0,3,0,0,7,9,1},{0,0,7,2,0,0,6,5,3}}
    
    /*
     * COHERENCE AND DATA METHODS:
     * suposed to mantain data coherence, enure the data in every cell
     * and group of the sudoku is up to date to the latest change in
     * the grid (like a digit being filled into a cell) so that our
     * solving algorithms work on truthfull, accurate data.
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
    public void updateAffected(int val, int row, int col)
    {
        this.rows[row].update(val);
        this.cols[col].update(val);
        this.sqrs[row/3*3+col/3].update(val);
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
    public int getSqr(int row, int col) {
        //"maybe encapsulate row/3*3+col/3 as getSqr(row,col)?", we did hehe.
        return row/3*3+col/3;
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
        
        return row%3*3+col%3;
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
    
    /* //////////////////////////////////////////////////////////////////////
     * SOLVING METHODS:
     * we use them to fill in digits into the grid based on the
     * available information. I'm gonna try to ordem them from most
     * simple to most complicated but the middlegrounds might be muddy.
     * //////////////////////////////////////////////////////////////////////
     */
    //DISCLAIMER: some of them have funny names so bare with me "O3O
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
        boolean solve = false;
        for(int i = 0; i < this.sqrs.length; i++){
            if(!this.sqrs[i].isComplete()){
                for(int j = 0; j < this.sqrs[i].cells.length; j++){
                    if(!this.sqrs[i].cells[j].isFilled()){

                    }
                }
            }
        }
        return solve;
    }
}