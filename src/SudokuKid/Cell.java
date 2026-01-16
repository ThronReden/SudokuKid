package SudokuKid;

/**
 * Cell objects represent a slot in the sudoku.
 * 
 * @author TR 
 * @version 27/NOV/25
 */
public class Cell {
    /* //////////////////////////////////////////////////////////////////////
     * ATTRIBUTES:
     * //////////////////////////////////////////////////////////////////////
     */
    public int value; //the digit contained by the cell, 1 to 9.
    //if value is 0 then the Cell is "empty".
    private boolean[] plausibleValues; //a list representing if each
    //of the 9 valid digits could be placed in the cell or not.
    
    /* //////////////////////////////////////////////////////////////////////
     * CONSTRUCTOR:
     * builds objects of this class.
     * //////////////////////////////////////////////////////////////////////
     */
    /**
     * Constructor for objects of class Cell
     */
    public Cell(){
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

    /* //////////////////////////////////////////////////////////////////////
     * METHODS:
     * they do things ~~
     * //////////////////////////////////////////////////////////////////////
     */
    /**
     * Method setValue sets the "value" attribute of that instance
     * of Cell to a given digit.
     * 
     * @param val, the digit you want to fill into the Cell
     */
    public void setValue(int val){
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
    public boolean isFilled(){
        return this.value != 0; //if it's 0 then it's empty
    }
    
    /**
     * Method numPlausibleValues gets us the number of digits that could
     * fill this Cell with the curren information of the Sudoku. Example:
     * if only a 5, a 6 or a 9 could fill this Cell the method will return
     * the number 3.
     *
     * @return sum, the number of plausible values
     */
    public int numPlausibleValues(){
        int sum = 0; //a variable to contain the number of values
        if(!this.isFilled()){
            for(int i = 0; i < this.plausibleValues.length; i++){
                if(plausibleValues[i]){
                    sum++; //if a position of the array is true we add 1 to the
                    //sum of plausible numbers
                }
            }
        }
        return sum;
    }
    
    /**
     * Method getPlausVal gets us the "n"th plausible value that could fill
     * this Cell. Example: if only a 5, a 6 or a 9 could fill this Cell and
     * we introduce 3 as n, the method will return 9. If we introduce 1 as n
     * instead, it'll return 3. If we introduce 0 or less or any other value
     * for n that doesnt match a plausible value (like 4 or more in the given
     * example) the method will return 0.
     *
     * @param n the number plausible value we want
     * @return val, the value of that plausible value
     */
    public int getPlausVal(int n){
        int val = 0; //a value to be returned, 0 in case of invalid n
        if(n > 0 && n <= 9){
            //if valid n
            int sum = 0; //the sum of plausible values
            for(int i = 0; i < this.plausibleValues.length; i++){
                if(plausibleValues[i]){
                    sum++; //we count the plausible values
                }
                if(sum == n){
                    //if the plausible val number is n
                    val = i+1; //we return it (its position + 1)
                    return val;
                }
            }
        }
        return val;
    }
    /**
     * Method getPlausVal overload gets us the lowest plausible value that
     * could fill this Cell with the current Sudoku information. We can use
     * this simpler method if we've already confirmed there's a single
     * plausible digit for the Cell to get said digit.
     *
     * @return val, the lowest plausible value
     */
    public int getPlausVal(){
        int val = 0; //we initialze val in case there's no plausible value
        for(int i = 0; i < this.plausibleValues.length; i++){
            if(plausibleValues[i]){
                //as soon as we get a plausible value
                val = i+1; //we return it (its position + 1)
                return val;
            }
        }
        return val; //we return the first plausible value we find, the lowest.
    }

    /**
     * Method isPlausible returns if a given value could fill this Cell or not.
     * 
     * @param val, the value we want to check for
     * @return true if it can fill the cell, false otherwise
     */
    public boolean isPlausible(int val) {
        //it has to be emty and have that digit marked as plausible in our
        //plausibleValues list:
        return !this.isFilled() && this.plausibleValues[val-1];
    }
    /**
     * Method isPlausible overload returns if two given values could fill this
     * Cell or not.
     * 
     * @param val1, the first value we want to check for
     * @param val2, the second value we want to check for
     * @return true if they can fill the cell, false otherwise
     */
    public boolean isPlausible(int val1, int val2) {
        //it has to be emty and have both digits marked as plausible in our
        //plausibleValues list:
        return this.isPlausible(val1) && this.isPlausible(val2);
    }
    /**
     * Method isPlausible overload 2 returns if three given values could fill
     * this Cell or not.
     * 
     * @param val1, the first value we want to check for
     * @param val2, the second value we want to check for
     * @param val3, the third value we want to check for
     * @return true if they can fill the cell, false otherwise
     */
    public boolean isPlausible(int val1, int val2, int val3) {
        //it has to be emty and have the three digits marked as plausible in
        //our plausibleValues list:
        return this.isPlausible(val1) && this.isPlausible(val2)
        && this.isPlausible(val3);
    }
    
    /**
     * Method removePlausible sets the plausibility for a given value to fill
     * this Cell to false.
     * 
     * @param val the value we want to set to not plausible for this Cell
     */
    public void removePlausible(int val) {
        //if the cell is empty:
        if(!this.isFilled()){
            this.plausibleValues[val-1] = false; //we mark it as not plausible
        }
    }
    /**
     * Method removePlausible overload sets the plausibility for two given
     * values to fill this Cell to false.
     * 
     * @param val1, the first value we want to set to not plausible
     * @param val2, the second
     */
    public void removePlausible(int val1, int val2) {
        this.removePlausible(val1);
        this.removePlausible(val2);
    }
    /**
     * Method removePlausible overload 2 sets the plausibility for three given
     * values to fill this Cell to false.
     * 
     * @param val1, the first value we want to set to not plausible
     * @param val2, the second
     * @param val3, the third
     */
    public void removePlausible(int val1, int val2, int val3) {
        this.removePlausible(val1);
        this.removePlausible(val2);
        this.removePlausible(val3);
    }

    /**
     * Method removeAllPlausibleBut sets all numbers but the two given to
     * unplausible for filling this cell.
     * 
     * @param val1, the first value that will stay plausible
     * @param val2, the second
     */
    public void removeAllPlausibleBut(int val1, int val2) {
        //we loop through this cells list of plausible values:
        for(int i = 0; i < this.plausibleValues.length; i++) {
            //we mark as not plausible all but the two given digits:
            if(i != val1-1 && i != val2-1){
                this.plausibleValues[i] = false;
            }
        }
    }
    /**
     * Method removeAllPlausibleBut overload sets all numbers but the three
     * given to unplausible for filling this cell.
     * 
     * @param val1, the first value that will stay plausible
     * @param val2, the second
     * @param val3, the third
     */
    public void removeAllPlausibleBut(int val1, int val2, int val3) {
        //we loop through this cells list of plausible values:
        for(int i = 0; i < this.plausibleValues.length; i++) {
            //we mark as not plausible all but the three given digits:
            if(i != val1-1 && i != val2-1 && i != val3-1){
                this.plausibleValues[i] = false;
            }
        }
    }
}