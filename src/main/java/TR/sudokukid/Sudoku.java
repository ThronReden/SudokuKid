package TR.sudokukid;

/**
 * Sudoku objects represent a whole sudoku containing 9 rows,
 * 9 columns and 9 sqares each containing 9 cells (for a total of
 * 81 cells in the sudoku) and different cell solving methods
 * based on the available information from the given numbers.
 * 
 * @author TR 
 * @date 06/OCT/26
 */
public class Sudoku {
    /* ##################################################################################################################################################
     * ATTRIBUTES:
     */
    /**
     * A bidimentional array representing our sudoku grid.
     */
    protected final Cell[][] cells = new Cell[9][9];
    
    /* ##################################################################################################################################################
     * CONSTRUCTORS:
     * & also the clone() method
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
                this.cells[i][j] = new Cell(row,col);
            }
        }
    }
    /**
     * Constructor for objects of class Sudoku that also adds a string of
     * digits to the grid.
     * 
     * @param string, a string of 81 ints we'll fill the grid with
     */
    public Sudoku(String string){
        this(); // We call the standard constructor.
        this.fill(string); // We add the digits on the martix to our grid.
    }
    /**
     * Constructor for objects of class Sudoku that also adds a matrix of
     * digits to the grid.
     * 
     * @param matrix, a bidimentional array of ints we want for the grid
     */
    public Sudoku(int[][] matrix){
        this(); // We call the standard constructor.
        this.fill(matrix); // We add the digits on the martix to our grid.
    }
    /**
     * Cloning constructor.
     * 
     * @param aSudoku, the sudoku we want to clone.
     */
    private Sudoku(Sudoku aSudoku){
        for(int i = 0; i < this.cells.length; i++) {
            for(int j = 0; j < this.cells[i].length; j++) {
                int row = i+1; int col = j+1;
                Cell cell = aSudoku.getCell(row,col);
                this.cells[i][j] = cell.clone();
            }
        }
    }
    /**
     * Clones the current sudoku.
     * 
     * @return a clone of this sudoku
     */
    @Override
    public Sudoku clone(){
        return new Sudoku(this);
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
        for (Cell[] row : this.cells) {
            for(Cell cell : row) {
                if(cell.isFilled()){
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * Checks if all cells of the sudoku are filled with a digit.
     * It loops through the full sudoku and fails if any of its cells is
     * empty.
     * 
     * @return {@code true} if the sudoku is entirely filled, {@code false}
     * otherwise
     */
    public boolean isFilled(){
        // We loop through the sudoku:
        for (Cell[] row : this.cells) {
            for (Cell cell : row) {
                // We can finish execution early if
                // a single cell is not filled:
                if (cell.isEmpty()) {
                    return false;
                }
            }
        }
        //we return our checking variable:
        return true;
    }
    /**
     * Checks if all cells of the desired row are filled with a digit.
     * It loops through the row and fails if any of its cells is empty.
     * 
     * @param rowNumber, the row we want to check
     * @return {@code true} if it's full, {@code false} otherwise
     */
    public boolean isRowFilled(int rowNumber){
        return isGroupFilled(this.getRowCells(rowNumber));
    }
    /**
     * Checks if all cells of the group are filled with a digit.
     * 
     * @param group, the group we want to check
     * @return {@code true} if it's full, {@code false} otherwise
     */
    protected static boolean isGroupFilled(Cell[] group){
        for (Cell cell : group) {
            if (cell.isEmpty()) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Gets the cell at the given position.
     * Parameters expetc the actual row & column number, not array index.
     * 
     * @param row, the cells row number
     * @param col, the cells column number
     * @return the cell in this position
     */
    public Cell getCell(int row, int col){
        return this.cells[row-1][col-1];
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
     * Returns the number given row as a list of int, so
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
     * Returns the number given column as a list of int, so
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
     * Returns the number given square as a list of cells.
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
     * Returns the square the given cell position belongs in as a list of
     * cells.
     * Calls the main method for the correct square.
     * 
     * @param row, one of the rows of the square
     * @param col, one of the columns of the square
     * @return the list of cells in that square
     * @see #getSqrCells(int) 
     */
    public Cell[] getSqrCells(int row, int col) {
        return getSqrCells(Cell.getSqr(row, col));
    }
    /**
     * Returns the number given square as a list of int, so 
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
    
    /**
     * Cheks both the length of a given matrix rows and columns and the digits
     * it contains and returns true if the matrix correctly represents a
     * Sudoku:9 rows, 9 columns and all digits vary from 0 to 9, 0 representing
     * an empty cell; and there's no repeating numbers breaking basic sudoku
     * rules.
     * It's static, as it doesn't depend on the existence of an
     * instance of Sudoku.
     *
     * @param matrix, the bidimensional matrix of integers we want to check
     * @return false if the matrix isn't a valid Sudoku puzzle, true otherwise.
     */
    public static boolean isValid(int[][] matrix){
        // We check the matrix has 9 rows, no more no less.
        if(matrix.length != 9){
            return false; // We end the execution
        }
        for(int i = 0; i < matrix.length; i++){
            // We check every row has 9 columns, no more no less.
            if(matrix[i].length != 9){
                return false;
                // This way we don't keep checking once something is not valid.
            }
            for(int j = 0; j < matrix[i].length; j++){
                // We check the value in [i][j]
                int val = matrix[i][j]; // We store it's value
                if(val < 0 | val > 9){
                    return false; // The digit isn't valid, execution ends.
                } else if(val == 0){
                    continue; // The cell is empty, it's valid.
                    // This way false invalid due to a repeated empty cell
                    // doesn't trigger.
                }
                // We check the value isn't repeated in this cell's row or
                // column:
                for(int k = 0; k < matrix.length; k++){
                    // We loop through the row, excluding this cell:
                    if(k != j && matrix[i][k] == val){
                        return false;
                    }
                    // We loop through the column, excluding this cell:
                    if(k != i && matrix[k][j] == val){
                        return false;
                    }
                }
                // We check the value isn't repeated in this cell's square:
                // there's now only 4 cells we haven't checked, those in the
                // same square that aren't also in the same row or column,
                // so we get the rows of the sqr this cell's not at:
                int row1 = (i + 1) % 3 + i / 3 * 3;
                int row2 = (i + 2) % 3 + i / 3 * 3;
                // and also the columns of the sqr the cell's not at:
                int col1 = (j + 1) % 3 + j / 3 * 3;
                int col2 = (j + 2) % 3 + j / 3 * 3;
                // then we check the four cells obtained by combining them:
                if(matrix[row1][col1] == val || matrix[row1][col2] == val 
                    || matrix[row2][col1] == val || matrix[row2][col2] == val){
                    // If there's an identic digit in any of this cells the
                    // matrix is not a valid sudoku statement:
                    return false;
                }
            }
        }
        // If none of this checks fail, the matrix is valid as a sudoku:
        return true;
    }
    
    /**
     * Checks if all filled cells respect basic sudoku rules.
     * This means every digit appears, if any, a single time in every row,
     * column and square. If it's solved every digit from 1 to 9 will appear
     * only once in each cell group and if it's not solved it may not appear
     * at all in some of them but there can never be multiple instances of the
     * same digit in the same group.
     * As we already have a method that does this with 9x9 integer matrixs
     * we'll use that.
     * 
     * @return {@code true} if sudoku rules are respected all throughout the
     * grid, {@code false} otherwise.
     */
    public boolean isCorrect(){
        //we convert the sudoku to a 9x9 matrix with .toMatrix() and call the
        //validSudokuMatrix with it:
        return SudokuObsolete.validSudokuMatrix(this.toMatrix());
    }
    
    
    
    /**
     * Checks weather the sudoku is solved or not, simple enough.
     * This is achieved by checking both if all cells are filled and if sudoku
     * rules are respected all throughout the grid, meaning we have not made a
     * mistake with any of the numbers added.
     * 
     * @return true if the sudoku is correctly solved, false otherwise
     */
    public boolean isSolved(){
        //we call the methods that check both conditions mentioned earlier:
        return this.isFilled() && this.isCorrect();
    }
    
    /* ##################################################################################################################################################
     * METHODS FOR ADDING DIGITS:
     */
    /**
     * Fills the given value into the specified cell.
     * 
     * @param val, the value to be filled in
     * @param row, the row of the cell we want to fill
     * @param col, the column of the cell we want to fill
     */
    public void fill(int val, int row, int col){
        if(row < 1 || row > 9){
            throw new IllegalArgumentException("Invalid row! Must be a number from 1 to 9.");
        } else if(col < 1 || col > 9){
            throw new IllegalArgumentException("Invalid column! Must be a number from 1 to 9.");
        } else {
            int rowIndex = row - 1;
            int colIndex = col - 1;
            this.cells[rowIndex][colIndex].setValue(val);
        }
    }
    /**
     * Mirrors the given grid if it's a valid sudoku.
     * 
     * @param matrix, the matrix of digits we will fill into the grid.
     * 
     * @see #isValid(int[][]) 
     * @see #fill(int, int, int) 
     */
    private void fill(int[][] matrix){
        if (isValid(matrix)) {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    int val = matrix[i][j]; // We store the digit.
                    int row = i+1; // Our fill method works with actual row
                    int col = j+1; // and column numbers, not indexes, so we
                    // take that into account and then
                    // we fill the value in the cell:
                    fill(val, row, col);
                }
            }
        }
    }
    /**
     * Adds the given string of ints if it's a valid sudoku.
     * 
     * @param string, the string of digits we will fill into the grid.
     * 
     * @see #fill(int[][]) 
     * @see #isValid(int[][]) 
     * @see #fill(int, int, int)
     */
    private void fill(String string){
        this.fill(toMatrix(string));
    }
    
    /* ##################################################################################################################################################
     * VISUALIZATION METHODS:
     * show methods
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
    
    /* ##################################################################################################################################################
     * REFACTOR / TRANSFORMING METHODS:
     * Functionalities such as toString and toMatrix.
     */
    /**
     * Converts our Sudoku into an int[][] Sudoku matrix, a 9x9 bidimentional
     * java array of integers in which each number is the value of the cell
     * in that position of the Sudoku.
     *
     * @return matrix, an int[][] that represents our Sudoku.
     */
    public int[][] toMatrix(){
        int[][] matrix = new int[9][9]; // We create our 9x9 int matrix
        // We loop through the sudoku:
        for(int i = 0; i < this.cells.length; i++){
            for(int j = 0; j < this.cells[i].length; j++){
                // We add each cells digit to the matrix:
                matrix[i][j] = this.cells[i][j].getValue();
            }
        }
        return matrix; // We return our sudoku matrix
    }
    /**
     * Finds if a String contains a succession of digits valid as a sudoku
     * statement and converts it to the format of a 9x9 bidimentional array
     * of integers.
     * 
     * @param nums, the String of digits to convert, in case it's valid
     * @return a bidimentional array sudoku statement from the String
     */
    public static int[][] toMatrix(String nums){
        nums = nums.replaceAll(" ","");
        nums = nums.replaceAll("\\.","0");
        //we create our 9x9 int matrix:
        int[][] matrix = new int[9][9];
        if(nums.matches("^\\d{81}$")){
            //we loop through the String:
            for(int i = 0; i < nums.length(); i++){
                //this doesn't feel too clean but was the only way I could
                //make it work as intended:
                matrix[i/9][i%9] = Integer.parseInt(nums.charAt(i)+"");
            }
        } else {
            throw new IllegalArgumentException("The string does not contain a sudoku statement.");
        }
        //we return our sudoku matrix:
        return matrix;
    }
    
    /**
     * Converts our Sudoku into a String representing our Sudoku in
     * bidimentional int java array format.
     * 
     * @return text, a String that represents our Sudoku in int matrix format.
     */
    public String toStringMatrix(){
        String string = "{"; // We open our 2d array
        for(int i = 0; i < this.cells.length; i++){
            if(i == 0){
                string += "{"; // We open the first contained array of numbers.
            } else {
                string += ", {"; // We open the rest of them.
            }
            // In each contained array of nubers:
            for(int j = 0; j < this.cells[i].length; j++){
                // we concatenate each digit,
                string += this.cells[i][j].getValue();
                if(j == this.cells[i].length-1){
                    string += "}"; // if its the last of the array we close it
                } else {
                    string += ","; // else we add a coma between them.
                }
            }
        }
        return string+"}"; // We close the main array and return it
    }
    /**
     * Converts our Sudoku into a String of it's digits all in a row.
     *
     * @return text, a String that represents our Sudoku.
     */
    @Override
    public String toString(){
        String string = "";
        for(Cell[] row : this.cells) {
            for(Cell cell : row){
                string += String.valueOf(cell.getValue());
            }
        }
        return string;
    }
    
}