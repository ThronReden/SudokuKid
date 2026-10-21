package TR.sudokukid;

/**
 * Collection of methods for checking if group numbers received through
 * parameters is valid and throwing a custom exception message for every
 * case needed.
 * 
 * @author jsanchez
 * @date 20/OCT/26
 */
public class SudokuParamValidation {
    /**
     * Checks if the given number is in the range from 1 to 9, included.
     * 
     * @param num, the number we want to check
     * @return {@code true} if it's in range, {@code false} otherwise.
     */
    protected static boolean isInValidRange(int num) {
        return num >= 1 && num <= 9;
    }
    /**
     * Checks if the given number is NOT in the range from 1 to 9, included.
     * @param num, the number we want to check
     * @return {@code true} if it's NOT in range, {@code false} otherwise.
     */
    protected static boolean isNotInValidRange(int num) {
        return num < 1 || num > 9;
    }
    
    /**
     * Checks if a given value is in range and throws an exception in case
     * it's not.
     * 
     * @param val, the value we want to check
     * @throws IllegalArgumentException 
     */
    protected static void checkValidValue(int val) throws IllegalArgumentException {
        if (val != Cell.EMPTY && isNotInValidRange(val)) {
            throw new IllegalArgumentException("Invalid value! Must be a number from 0 to 9.");
        }
    }
    
    /**
     * Checks if a given row number is in range and throws an exception in case
     * it's not.
     * 
     * @param row, the row number we want to check
     * @throws IllegalArgumentException 
     */
    protected static void checkValidRowNumber(int row) throws IllegalArgumentException {
        if(isNotInValidRange(row)){
            throw new IllegalArgumentException("Invalid row! Must be a number from 1 to 9.");
        }
    }
    /**
     * Checks if a given column number is in range and throws an exception in
     * case it's not.
     * 
     * @param col, the column number we want to check
     * @throws IllegalArgumentException 
     */
    protected static void checkValidColNumber(int col) throws IllegalArgumentException {
        if(isNotInValidRange(col)){
            throw new IllegalArgumentException("Invalid column! Must be a number from 1 to 9.");
        }
    }
    /**
     * Checks if a given square number is in range and throws an exception in
     * case it's not.
     * 
     * @param sqr, the square number we want to check
     * @throws IllegalArgumentException 
     */
    protected static void checkValidSqrNumber(int sqr) throws IllegalArgumentException {
        if(isNotInValidRange(sqr)){
            throw new IllegalArgumentException("Invalid square! Must be a number from 1 to 9.");
        }
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
    protected static boolean isValidMatrix(int[][] matrix){
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
     * Returns true if the given matrix is NOT valid. 
     * 
     * @param matrix, the matrix we want to check
     * @return {@code true} if the given matrix is NOT valid, {@code false}
     * otherwise.
     * @see #isValidMatrix(int[][]) 
     */
    protected static boolean isNotValidMatrix(int[][] matrix){
        return !isValidMatrix(matrix);
    }
    
    /**
     * Checks if a given matrix contains a valid sudoku and throws an exception
     * in case it's not.
     * 
     * @param matrix, the matrix we want to check
     */
    protected static void checkValidMatrix(int[][] matrix) throws IllegalArgumentException {
        if (isNotValidMatrix(matrix)) {
            throw new IllegalArgumentException("The matrix doesn't contain a valid sudoku!");
        }
    }
}
