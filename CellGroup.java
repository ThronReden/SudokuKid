
/**
 * CellGroup objects represent one of the rows, columns
 * or squares of the sudoku as groups of 9 cells filled
 * with digits from 1 to 9 different from eachother.
 * 
 * @author TR 
 * @version 07/NOV/25
 */
public class CellGroup
{
    public Cell cells[];//the list of 9 cells composing the group.
    public boolean values[];//a list of 9 booleans representing if
    //the digits from 1 to 9 already exist in a cell of the group.
    //Usefull information for some of the solving algorithms.

    /**
     * Constructor for objects of class CellGroup
     */
    public CellGroup()
    {
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
    public CellGroup(Cell cellList[])
    {
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

    /**
     * Method isComplete cheks if the list is full and
     * (hopefully, if the methods for solving cells function correctly)
     * solved, containing all numbers 1 to 9 once.
     *  
     * @return true if the group is complete and false if else.
     */
    public boolean isComplete()
    {
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
    public void update()
    {
        int val = 0;
        for(int i = 0; i < this.cells.length; i++){
            val = this.cells[i].value; //we set val to the solved
            //cell value
            if(val != 0 & this.values[val-1] == false){
                this.update(val); //we update for that value
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
    public void update(int val)
    {
        if(val > 0 & val < 10){
            int pos = val-1; //we compensate for position nomenclature
            //in arrays
            this.values[pos] = true; //we mark the digits existence in
            //the group
            for(int i = 0; i < this.cells.length; i++){
                if(this.cells[i].value == 0){
                    this.cells[i].plausibleValues[pos] = false;//we
                    //rule that digit out from all empty cells left
                }
            }
        }
    }
    
    /**
     * Method numPlausCells gets us the number of Cells in this CellGroup
     * that could be filled with a given value.
     *
     * @param val the value we want to check for
     * @return the number of Cells that could be filled with it
     */
    public int numPlausCells(int val){
        int pos = val - 1; //we compensate for position nomenclature
        //in arrays
        int sum = 0; //the number of Cells that can fit the given value
        for(int i = 0; i < this.cells.length; i++){
            if(this.cells[i].plausibleValues[pos]){
                sum++;
            }
        }
        return sum;
    }
}