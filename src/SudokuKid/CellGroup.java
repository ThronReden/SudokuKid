package SudokuKid;

/**
 * CellGroup objects represent one of the rows, columns
 * or squares of the sudoku as groups of 9 cells filled
 * with digits from 1 to 9 different from eachother.
 * 
 * @author TR 
 * @version 27/NOV/25
 */
public class CellGroup {
    /* //////////////////////////////////////////////////////////////////////
     * ATTRIBUTES:
     * //////////////////////////////////////////////////////////////////////
     */
    public Cell[] cells;//the list of 9 cells composing the group.
    public boolean[] values;//a list of 9 booleans representing if
    //the digits from 1 to 9 already exist in a cell of the group.
    //Usefull information for some of the solving algorithms.

    /* //////////////////////////////////////////////////////////////////////
     * CONSTRUCTOR:
     * builds objects of this class.
     * //////////////////////////////////////////////////////////////////////
     */
    /**
     * Constructor for objects of class CellGroup
     */
    public CellGroup(){
        //we create 9 new instances of Cell and add them to our
        //cells list. This will be the cells of this row, column
        //or square.
        this.cells = new Cell[9];
        for(int i = 0; i < this.cells.length; i++){
            this.cells[i] = new Cell(); //new instances of Cell!!
            //System.out.println(i+" es "+this.group[i]);
        }
        //we initialize all booleans of the values list to
        //false, as the cells are alwais initialized empty.
        //we'll update it as digits are filled in.
        this.values = new boolean[9];
        for(int i = 0; i < this.values.length; i++){
            this.values[i] = false;
        }
    }
    /**
     * Constructor for objects of class CellGroup from a list of
     * predefined Cell objects
     * 
     * @param cellList[], the list of the cells we want to conform
     * the group
     */
    public CellGroup(Cell cellList[]){
        this.cells = new Cell[9];//we initialize an empty list for
        //our cells
        for(int i = 0; i < this.cells.length; i++){
            this.cells[i] = cellList[i]; //we don't create new
            //instances of Cell now, we add the references to already
            //initialized cells that we had stored in the given list.
            //System.out.println(i+" es "+this.group[i]);
        }
        //we initialize all booleans of the values list to
        //false, as the cells are alwais initialized empty.
        //we'll update it as digits are filled in.
        this.values = new boolean[9];
        for(int i = 0; i < this.values.length; i++){
            this.values[i] = false;
        }
    }
    
    /* //////////////////////////////////////////////////////////////////////
     * METHODS:
     * they do things ~~
     * //////////////////////////////////////////////////////////////////////
     */
    /**
     * Method isComplete cheks if the list is full and
     * solved, containing all numbers 1 to 9 once.
     * (hopefully, if the methods for solving cells function correctly)
     *  
     * @return true if the group is complete and false if else.
     */
    public boolean isComplete(){
        boolean complete = true;
        for(int i = 0; i < this.values.length; i++){
            if(this.values[i] == false)
                complete = false;
        }
        return complete;
    }
    
    /**
     * Method update checks every cell of the group for a filled in
     * digit and records its existence in the grid for the group and
     * all empty cells in it.
     * In other words, we search for existing digits in the group we
     * didn't know about and forward that information.
     */
    public void update(){
        int val = 0;
        for(int i = 0; i < this.cells.length; i++){
            if(!this.cells[i].isFilled()){
                val = this.cells[i].value; //we set val to the solved
                //cell value
                int pos = val - 1; //we adjust for array index nomenclature
                if(this.values[pos] == false){
                    this.update(val); //we update for that value
                }
            }
        }
    }
    /**
     * Method update overload records the existence of a given digit
     * in the group for itself and all empty cells in it.
     * In other words, we have a digit we know exists in the group
     * and we're forwarding that information for later use and keeping
     * coherence.
     *
     * @param val the digit we know already exists in the group
     */
    public void update(int val){
        if(val > 0 & val < 10){
            this.values[val-1] = true; //we mark the digits existence in
            //the group
            for(int i = 0; i < this.cells.length; i++){
                if(!this.cells[i].isFilled()){
                    this.cells[i].removePlausible(val);//we rule that digit
                    //out from all empty cells left
                }
            }
        }
    }
    
    /**
     * Method numPlausCells gets us the number of Cells in this CellGroup
     * that could be filled with a given value. It only counts empty Cells.
     *
     * @param val the value we want to check for
     * @return the number of Cells that could be filled with it
     */
    public int numPlausCells(int val){
        int sum = 0; //the number of Cells that can fit the given value
        //we loop through the cells of this group:
        for(int i = 0; i < this.cells.length; i++){
            if(!this.cells[i].isFilled() && this.cells[i].isPlausible(val)){
                sum++;
            }
            //maybe encapsulate the content of the if parenthesis in a method
            //in Cell.
        }
        return sum; //we return how many cells could fit that digit
    }
    /**
     * Method numPlausCells overload gets us the number of Cells in this
     * CellGroup that could be filled with a given pair of values.
     * 
     * @param val1 the firs value of the pair we want to check for
     * @param val2 the second value of the pair
     * @return the number of Cells that could be filled with the pair
     */
    public int numPlausCells(int val1, int val2){
        int sum = 0; //the number of Cells that can fit the given pair
        //we loop through the cells of this group:
        for(int i = 0; i < this.cells.length; i++){
            if(!this.cells[i].isFilled()
            && this.cells[i].isPlausible(val1)
            && this.cells[i].isPlausible(val2)){
                sum++;
            }
        }
        return sum; //we return how many cells could fit that pair of digits
    }
    
    /**
     * Method getPlausCellsIndex gets us the indexes in this.cells for the
     * cells that could be filled with a given value.
     * 
     * @param val, the value we want to check for
     * @return a list with the corresponding indexes
     */
    public int[] getPlausCellsIndex(int val){
        int[] indexs = new int[this.numPlausCells(val)];
        int j = 0;
        for(int i = 0; i < this.cells.length; i++){
            if(this.cells[i].isPlausible(val)){
                indexs[j] = i;
                j++;
            }
        }
        return indexs;
    }
    
    /**
     * Method getPlausCells gets us the list of cells in this group, in case
     * any exists, that could be filled with both given numbers.
     * 
     * @param val1, the first value we want to check for 
     * @param val2, the second
     * @return an array with the corresponding cells
     */
    private Cell[] getPlausCells(int val1, int val2) {
        Cell[] plausCells = new Cell[this.numPlausCells(val1, val2)];
        int j = 0;
        for(int i = 0; i < this.cells.length; i++){
            if(this.cells[i].isPlausible(val1)
            && this.cells[i].isPlausible(val2)){
                plausCells[j] = this.cells[i];
                j++;
            }
        }
        return plausCells;
    }
    
    /**
     * Method getRestCells is the counterpart to getPlausCells, it gets us
     * the cells from this group that couldn't be filled with both given
     * numbers. This includes both those that couldn't be filled with any and
     * those that could only be filled with one of the pair.
     * 
     * @param val1, the first value we want to check for 
     * @param val2, the second
     * @return an array with the corresponding cells
     */
    private Cell[] getRestCells(int val1, int val2) {
        Cell[] restCells = new Cell[9-this.numPlausCells(val1, val2)];
        int j = 0;
        for(int i = 0; i < this.cells.length; i++){
            if(!this.cells[i].isPlausible(val1)
            || !this.cells[i].isPlausible(val2)){
                restCells[j] = this.cells[i];
                j++;
            }
        }
        return restCells;
    }
    
    /**
     * Method valsExistAlone checks if there is any cells in this group that
     * could be filled with one of two given numbers but not the other.
     * 
     * @param val1, the first value we want to check for 
     * @param val2, the second
     * @return true if there's such a cell, false otherwise
     */
    private boolean valsExistAlone(int val1, int val2) {
        boolean exists = false;
        for(int i = 0; i < this.cells.length; i++){
            exists |=
            (this.cells[i].isPlausible(val1)
                && !this.cells[i].isPlausible(val2))
            || (!this.cells[i].isPlausible(val1)
                && this.cells[i].isPlausible(val2));
        }
        return exists;
    }

    /**
     * Method numCellsOnlyPair checks how many cells in the group could only
     * be filled with a given pair of numbers and not any other digit.
     * 
     * @param val1, the first value we want to check for 
     * @param val2, the second
     * @return the number of cells that satisfy this
     */
    private int numCellsOnlyPair(int val1, int val2) {
        int count = 0;
        Cell[] plausCells = this.getPlausCells(val1, val2);
        for(int i = 0; i < plausCells.length; i++) {
            if(plausCells[i].numPlausibleValues() == 2){
                count++;
            }
        }
        if(count > 2){
            System.out.println("SOMTHING WENT HORRIBLY WRONG");
            System.exit(0);
        }
        return count;
    }
    /**
     * Method numCellsOnlyPair overload does the same but for a given list of
     * cells we've previously checked could be filled with a certain pair of
     * numbers.
     * This is comes in handy when we've already got a list from
     * getPlausCells for a pair of numbers and don't want to call that method
     * again, as it'd be redundant.
     * 
     * @param plausCells, the list of cells previously obtained from
     * getPlausCells
     * @return 
     */
    private int numCellsOnlyPair(Cell[] plausCells) {
        int count = 0;
        for(int i = 0; i < plausCells.length; i++) {
            if(plausCells[i].numPlausibleValues() == 2){
                count++;
            }
        }
        if(count > 2){
            System.out.println("SOMETHING WENT HORRIBLY WRONG");
            System.exit(0);
        }
        return count;
    }
    
    /**
     * Method numMissingValues counts how many values are still missing
     * for the group to be complete.
     * 
     * @return the number of missing values in the group.
     */
    public int numMissingValues(){
        int count = 0; //we'll count the missing values
        //we loop through our list of values in the group:
        for(int i = 0; i < this.values.length; i++){
            //if a value is missing (false) we count it:
            if(!this.values[i]){
                count++;
            }
        }
        return count; //we return the count of missing values
    }
    
    /**
     * Method getMissingVal gets the nth missing value of the group in case
     * it esists and returns 0 otherwise.
     * 
     * @param n, the number value we want
     * @return the missing value (a number from 1 to 9)
     */
    public int getMissingVal(int n){
        int val = 0; //a value to be returned, 0 in case of invalid n
        if(n > 0 && n <= 9){
            //if valid n
            int count = 0; //we'll count the missing values here
            for(int i = 0; i < this.values.length; i++){
                if(!this.values[i]){
                    count++; //we count the missing values
                }
                //if the count of missing vals is n:
                if(count == n){
                    val = i+1; //we return it (its position + 1)
                    return val;
                }
            }
        }
        return val;
    }
    /**
     * Method getMissingVal overload gets the lowest missing value of the
     * group. The first it finds looping through the list of existing values.
     * 
     * @return the missing value (a number from 1 to 9)
     */
    public int getMissingVal(){
        int val = 0; //a value to be returned
        for(int i = 0; i < this.values.length; i++){
            if(!this.values[i]){
                val = i+1; //we return it (its position + 1)
                return val;
            }
        }
        return val;
    }
    
    /**
     * Method findNakedPairs sarches for pair patterns in the group that
     * eliminate plausible values from some cells in the group.
     * Specifically, we'll search for cases in which there's a pair of cells
     * that can only be filled with the same pair, meaning none of the digits
     * of the pair can fill any other cell in the group; or a pair of cells in
     * the group that is the only that can be filled with a certain pair of
     * numbers, meaning the rest of seeingly plausible values for that pair
     * of cells isn't really plausible and can be removed.
     * 
     * @return true if we found a pair and therefore made progress in solving
     */
    public boolean findNakedPairs(){
        boolean solve = false; //we'll be returning this
        //if the group isn't solved:
        if(!this.isComplete()){
            int n = this.numMissingValues(); //we get how many numbers are
            //missing
            //if it is at least 2:
            if(n > 1){
                //for each pair of missing values:
                for(int i = 1; i < n; i++){
                    for(int j = i + 1; j <= n; j++){
                        int val1 = this.getMissingVal(i); //first value of the
                        //pair
                        int val2 = this.getMissingVal(j); //second value of the
                        //pair
                        //we create an array to retain the cells that can be
                        //filled with both values in the pair:
                        Cell[] foundCells = this.getPlausCells(val1, val2);
                        //we create an array to retain the rest of the cells:
                        Cell[] restCells = this.getRestCells(val1, val2);
                        //we store how many cells can be filled with both
                        //values of the pair:
                        if(foundCells.length+restCells.length != 9){
                            System.out.println("SOMETHING WENT HORRIBLY WRONG");
                            System.exit(0);
                        }
                        int numCells = foundCells.length;
                        //we store how many cells can only be filled by the
                        //pair of numbers:
                        int numCellsOnlyPair =
                        this.numCellsOnlyPair(foundCells);
                        //if the cells that can be filled with both values of
                        //the pair aren't the only cells in the group that can
                        //be filled with one of the values in the pair:
                        if(numCells > 1 && this.valsExistAlone(val1,val2)){
                            //but two of the cells that can be filled with
                            //both can only be filled with one or the other
                            //and not any other number:
                            if(numCellsOnlyPair == 2){
                                //then we've found a pair and the
                                //rest of the cells in the group can't be
                                //filled with those numbers:
                                solve = true;
                                for(int k = 0; k < restCells.length; k++){
                                    restCells[k].removePlausible(val1);
                                    restCells[k].removePlausible(val2);
                                }
                                //This pattern applied to a row or a column is
                                //what we call a pointing pair.
                                //Not sure if it has a name for squares.
                            }
                        //in the case we found only two cells and those are
                        //the only cells in the group that can be filled
                        //with any and both values of the pair:
                        } else if(numCells == 2 && numCellsOnlyPair != 2){
                            //(second part of the condition checks if the pair
                            //was already found before, therefore there's no
                            //progress in solving)
                            //we rule out any other value we may have had
                            //stored as plausible for those two cells:
                            solve = true;
                            for(int k = 0; k < foundCells.length; k++){
                                foundCells[k].
                                removeAllPlausibleBut(val1,val2);
                            }
                            //This is a hidden pair pattern.
                        }
                    }
                }
            }
        }
        return solve;
    }
}