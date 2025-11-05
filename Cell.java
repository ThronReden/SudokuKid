
/**
 * Cell objects represent a slot in the sudoku.
 * 
 * @author TR 
 * @version 05/NOV/25
 */
public class Cell
{
    public int value; //the digit contained by the cell, 1 to 9.
    //if value is 0 then the Cell is "empty".
    public boolean plausibleValues[]; //a list representing if each
    //of the 9 valid digits could be placed in the cell or not.
    
    /**
     * Constructor for objects of class Cell
     */
    public Cell()
    {
        //the "value" attribute is always initialized to 0.
        this.value = 0;
        //all the booleans in the "plausibleValues" list attribute
        //are initialized to true, as every digit fits in a cell
        //of an empty sudoku.
        this.plausibleValues = new boolean[9];
        for(int i = 0; i < this.plausibleValues.length; i++){
            this.plausibleValues[i] = true;
            //System.out.println(i+" es "+plausibleValues[i]);
        }
    }

    /**
     * Method setValue sets the "value" attribute of that instance
     * of Cell to a given digit.
     * 
     * @param val, the digit you want to fill into the Cell
     */
    public void setValue(int val)
    {
        if(0 < val & val < 10){ //we make sure the digit is valid.
            this.value = val; //we fill in the digit if it is.
        }
    }
    
    /**
     * Method isFilled is true if a digit from 1 to 9 exists in this cell of
     * the sudoku.
     * 
     * @return true if the value attribute of this cell is diferent from 0
     * and false if it is 0, therefore being empty.
     */
    public boolean isFilled()
    {
        return this.value != 0;
    }
}