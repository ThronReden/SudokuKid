package TR.sudokukid;
 
/**
 * Sudoku objects represent a whole sudoku containing 9 rows,
 * 9 columns and 9 sqares each containing 9 cells (for a total of
 * 81 cells in the sudoku) and different cell solving methods
 * based on the available information from the given numbers.
 * 
 * @author TR 
 * @date 28/SEP/26
 */
public class Sudoku {
    /* ##################################################################################################################################################
     * ATTRIBUTES:
     */
    /**
     * A bidimentional array representing our sudoku grid.
     */
    public final Cell[][] cells = new Cell[9][9];
    
    /* ##################################################################################################################################################
     * CONSTRUCTORS:
     */
    /**
     * Constructor for objects of class Sudoku.
     * It initializes an empty sudoku grid.
     */
    public Sudoku(){
        // We initialize all cells:
        for(int i = 0; i < this.cells.length; i++) {
            for(int j = 0; j < this.cells[i].length; j++) {
                int row = i+1; // We take notice of array index offset
                int col = j+1; // and define the cell's actual row and column.
                this.cells[i][j] = new Cell(row,col,this);
            }
        }
    }
    
    /* ##################################################################################################################################################
     * GETTERS & BOOLS:
     * various data methods intended to retrieve information from the sudoku.
     */
    /**
     * Checks weather the full sudoku grid is empty.
     * 
     * @return {@code true} if the sudoku is empty, {@code false} if one or
     * more cells are filled.
     */
    public boolean isEmpty(){
        boolean isEmpty = true;
        for (Cell[] row : this.cells) {
            for(Cell cell : row) {
                isEmpty &= cell.isEmpty();
            }
        }
        return isEmpty;
    }
    
    /**
     * Returns the number given row as a list of cells.
     * 
     * @param rowNumber, the desired row number
     * @return the list of cells in that row
     */
    public Cell[] getRowCells(int rowNumber) {
        int rowIndex = rowNumber-1; // We adapt to array index format.
        Cell[] rowCells = new Cell[9];
        // We clone that row of the bidimentional array:
        System.arraycopy(this.cells[rowIndex], 0, rowCells, 0, this.cells[rowIndex].length);
        return rowCells;
    }
    
    /**
     * Returns a the number given row as a list of int, so
     * we dont have to deal with the double wrapping in Cell.
     * 
     * @param rowNumber, the desired row number
     * @return the list of numbers in that row, plain and simple
     */
    public int[] getRowVals(int rowNumber) {
        int[] rowVals = new int[9];
        Cell[] rowCells = this.getRowCells(rowNumber);
        for(int i = 0; i < rowCells.length; i++) {
            rowVals[i] = rowCells[i].getValue();
        }
        return rowVals;
    }
    
    /**
     * Returns the number given column as a list of cells.
     * 
     * @param colNumber, the desired column number
     * @return the list of cells in that column
     */
    public Cell[] getColCells(int colNumber) {
        int colIndex = colNumber-1; // We adapt to array index format.
        Cell[] colCells = new Cell[9];
        for(int i = 0; i < this.cells.length; i++) {
            colCells[i] = this.cells[i][colIndex];
        }
        return colCells;
    }
    
    /**
     * Returns a the number given column as a list of int, so
     * we dont have to deal with the double wrapping in Cell.
     * 
     * @param colNumber, the desired column number
     * @return the list of numbers in that column, plain and simple
     */
    public int[] getColVals(int colNumber) {
        int[] colVals = new int[9];
        Cell[] colCells = this.getColCells(colNumber);
        for(int i = 0; i < colCells.length; i++) {
            colVals[i] = colCells[i].getValue();
        }
        return colVals;
    }
    
    /**
     * Returns a the number given square as a list of cells.
     * 
     * @param sqrNumber, the desired square number
     * @return the list of cells in that square
     */
    public Cell[] getSqrCells(int sqrNumber) {
        int sqrIndex = sqrNumber-1; // We adapt to array index format.
        // We find this square's first row and column indexes (the row and
        // column indexes of its top-left cell):
        int firstRowIndex = sqrIndex/3*3;
        int firstColIndex = sqrIndex%3*3;
        Cell[] sqrCells = new Cell[9];
        // For each row, we copy the 3 cells that exist both in the row
        // and the square:
        for(int i = 0; i < 3; i++) {
            // So, we copy 3 cells from each row across 3 rows in total.
            // We access a different row for each iteration of this loop.
            System.arraycopy(this.cells[firstRowIndex+i], firstColIndex, sqrCells, i*3, 3);
        }
        return sqrCells;
    }
    /**
     * Returns a the number given square as a list of int, so 
     * we dont have to deal with the double wrapping in Cell.
     * 
     * @param sqrNumber, the desired square number
     * @return the list of numbers in that square, plain and simple
     */
    public int[] getSqrVals(int sqrNumber) {
        int[] sqrVals = new int[9];
        Cell[] sqrCells = this.getSqrCells(sqrNumber);
        for(int i = 0; i < sqrCells.length; i++) {
            sqrVals[i] = sqrCells[i].getValue();
        }
        return sqrVals;
    }
    
    
    /* ##################################################################################################################################################
     * VISUALIZATION METHODS:
     * Functionalities such as toString and show methods.
     */
    /**
     * Prints on terminal a visual representation of the Sudoku.
     * This lets us see the sudoku in the way it's usually depicted.
     */
    public void show(){
        // We loop through each row of the sudoku:
        for(int i = 0; i < this.cells.length; i++){
            // We delimitate the rows:
            System.out.println("+---+---+---++---+---+---++---+---+---+");
            if(i % 3 == 0 && i != 0){
                // Double delimitation inbetween rows of different squares:
                System.out.println("+---+---+---++---+---+---++---+---+---+");
            }
            // We initialize our row pattern:
            String pattern = "| ";
            // We loop through the rows columns:
            for(int j = 0; j < this.cells[i].length; j++){
                String val = " "; // Default blank space for empty cells.
                // If it's not empty:
                if(this.cells[i][j].getValue() != 0){
                    // We change val to its value to string:
                    val = String.valueOf(this.cells[i][j].getValue());
                }
                if(j % 3 == 2 && j != 8){
                    // Double separation in between columns of different
                    // squares:
                    pattern += val + " || ";  
                } else {
                    // Separation in between the rest of columns:
                    pattern += val + " | ";  
                }
            }
            // We print the pattern of the row:
            System.out.println(pattern);
        }
        // We print the last delimitation:
        System.out.println("+---+---+---++---+---+---++---+---+---+");
        // Done!
    }
    
}