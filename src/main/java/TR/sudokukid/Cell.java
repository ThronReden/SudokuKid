package TR.sudokukid;

/**
 * Cell objects represent a slot in the sudoku.
 * 
 * @author TR 
 * @date 01/OCT/26
 */
public class Cell {
    /* ##################################################################################################################################################
     * ATTRIBUTES:
     */
    /**
     * The digit contained by the cell, a number from 1 to 9.
     * If value is 0 then the Cell is "empty".
     * It's 0 by default.
     */
    private int value = 0;
    /**
     * A list indicating whitch values could fill this cell based on the
     * current state of the sudoku and any asumptions we might have made
     * while solving.
     * The plausibli1ity of 1 will be determined by the first index,
     * plausibleValues[0], the plausibility of 2 by the second,
     * plausibleValues[1], etc.
     * If the boolean in the index is true this digit may fill this cell,
     * and if it's false it means we know for sure it can't.
     */
    private boolean[] plausibleValues = new boolean[9];
    /**
     * The row the cell is at, a number from 1 to 9.
     */
    private final int row;
    /**
     * The column the cell is at, a number from 1 to 9.
     */
    private final int col; 
    
    /* ##################################################################################################################################################
     * CONSTRUCTORS:
     * & also the clone() method
     */
    /**
     * Builds objects of class Cell.
     * 
     * @param aRow, its row, from 1 to 9
     * @param aCol, its column, from 1 to 9
     */
    public Cell(int aRow, int aCol){
        // We initialize the attributes with the given parameters:
        this.row = aRow;
        this.col = aCol;
        // and set the plausibility of all digits to true:
        for(boolean plausibilityOfDigit : this.plausibleValues){
            plausibilityOfDigit = true;
        }
    }
    /**
     * Cloning constructor.
     * 
     * @param aCell, the cell we want to clone.
     */
    private Cell(Cell aCell){
        this.value = aCell.getValue();
        this.row = aCell.getRow();
        this.col = aCell.getCol();
        this.plausibleValues = aCell.getPlausVals().clone();
    }
    /**
     * Clones the current cell.
     * 
     * @return a clone of this cell
     */
    @Override
    public Cell clone(){
        return new Cell(this);
    }
    
    /* ##################################################################################################################################################
     * GETTERS, SETTERS & BOOLS:
     * they do things ~~
     */
    /**
     * Returns true if the digit filling this cell of the sudoku is 0,
     * meaning it's empty.
     * 
     * @return true if the value attribute of this cell is equal to 0
     * and false if it is any other digit, from 1 to 9, therefore being filled.
     * @see #isFilled() 
     */
    public boolean isEmpty(){
        return this.value == 0; // If it's 0 then it's empty.
    }
    
    /**
     * Returns true if a digit from 1 to 9 exists in this cell of
     * the sudoku.
     * 
     * @return true if the value attribute of this cell is diferent from 0
     * and false if it is 0, therefore being empty.
     * @see #isEmpty() 
     */
    public boolean isFilled(){
        return this.value != 0; // If it's not 0 then it's filled.
    }
    
    /**
     * Returns the number filled in this instance of Cell.
     * 
     * @return this Cell's value
     * @see #value
     */
    public int getValue() {
        return this.value;
    }
    
    /**
     * Sets the number filled in this instance of Cell to a given digit.
     * 
     * @param val, the digit you want to fill into the Cell
     * @see #value
     */
    protected void setValue(int val){
        if(0 < val & val < 10){ // We make sure the digit is valid.
            this.value = val; // We fill in the digit if it is.
        }
    }

    /**
     * Returns the row of this instance of Cell.
     * 
     * @return this Cell's row
     * @see #row
     */
    public int getRow() {
        return row;
    }
    /**
     * Returns the row of this instance of Cell in array index format,
     * aka, substracting 1 (0 to 8).
     * 
     * @return this Cell's row index
     * @see #row
     */
    public int getRowIndex() {
        return row-1;
    }

    /**
     * Returns the column of this instance of Cell.
     * 
     * @return this Cell's column
     * @see #col
     */
    public int getCol() {
        return col;
    }
    /**
     * Returns the column of this instance of Cell in array index format,
     * aka, substracting 1 (0 to 8).
     * 
     * @return this Cell's column index
     * @see #col
     */
    public int getColIndex() {
        return col -1;
    }

    /**
     * Returns the square of this instance of Cell.
     * It's not an attribute, we calculate it.
     * 
     * @return this Cell's square
     * @see #getSqr(int, int) 
     */
    public int getSqr() {
        return getSqr(this.row,this.col);
    }
    /**
     * Method getSqr finds the square a Cell in a given row and column
     * belongs to.
     * We encapsulated this as we may need to use it a bunch of times.
     *
     * @param row, the Cell's row
     * @param col, its column
     * @return an integer from 1 to 9 corresponding to this Cell's square
     */
    public static int getSqr(int row, int col){
        //"maybe encapsulate row/3*3+col/3 as getSqrCells(row,col)?", we did hehe:
        int sqr = ((row-1)/3*3+(col-1)/3)+1; //the corresponding square
        return sqr; //we return it
    }
    /**
     * Method getSqrCells returns the square of this instance 
     * of Cell in array index format, aka, substracting 1 (0 to 8).
     * 
     * @return this Cell's square index
     */
    public int getSqrIndex() {
        return getSqr()-1;
    }
    
    /**
     * Method getPlausVals returns the plausibleValues attribute of
     * this instance of Cell.
     * 
     * @return this Cells plausible values list
     * @see #plausibleValues
     */
    public boolean[] getPlausVals(){
        return this.plausibleValues;
    }

    /**
     * Method isPlausible returns if a given value could fill this Cell or not.
     * 
     * @param val, the value we want to check for
     * @return true if it could fill the cell, false otherwise
     * @see #isPlausible(int[])
     * @see #plausibleValues
     */
    public boolean isPlausible(int val) {
        // It has to be emty and have that digit marked as plausible in our
        // plausibleValues list:
        return !this.isFilled() && this.plausibleValues[val-1];
    }
    /**
     * Method isPlausible overload returns if all values from a given list of
     * values could fill this Cell or not.
     * 
     * @param vals, the list of values we want to check for
     * @return true if all could fill the cell, false otherwise
     * @see #isPlausible(int) 
     * @see #plausibleValues
     */
    public boolean isPlausible(int[] vals) {
        // It has to be emty and have all digits marked as plausible in our
        // plausibleValues list:
        boolean isPlausible = true;
        for(int val : vals){
            isPlausible &= isPlausible(val);
        }
        return isPlausible;
    }
    
    /**
     * Marks a given value as not plausible for this Cell.
     * 
     * @param val the value we want to set to not plausible for this Cell
     * @see #removePlausible(int[])
     * @see #plausibleValues
     */
    protected void removePlausible(int val) {
        // Only if the cell is empty:
        if(this.isEmpty()){
            this.plausibleValues[val-1] = false; // We mark it as not plausible
        }
    }
    /**
     * Marks each value in the given list as not plausible for this Cell.
     * 
     * @param vals, the list of values we want to set to not plausible
     * @see #removePlausible(int)
     * @see #plausibleValues
     */
    protected void removePlausible(int[] vals) {
        for(int val : vals){
            this.removePlausible(val);
        }
    }

    /**
     * Sets all numbers but those in the given list to not plausible for
     * this cell.
     * 
     * @param vals, the list of values that will stay plausible
     * @see #plausibleValues
     */
    protected void removeAllPlausibleBut(int[] vals) {
        // We set all indexes to false
        // (booleans get initialized to false by default):
        this.plausibleValues = new boolean[9];
        // We set those values in the list as plausible:
        for(int val : vals){
            this.plausibleValues[val-1] = true;
        }
    }
    
}