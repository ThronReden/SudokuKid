package TR.sudokukid;

import static TR.sudokukid.SudokuParamValidation.*;
import java.util.Arrays;

/**
 *
 * @author jsanchez
 */
public record Position(int row, int col) implements HasPosition {
    public Position {
        checkValidRowNumber(row);
        checkValidColNumber(col);
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
    public static int sqr(int row, int col){
        //"maybe encapsulate row/3*3+col/3 as getSqrCells(row,col)?", we did hehe:
        int sqr = ((row-1)/3*3+(col-1)/3)+1; //the corresponding square
        return sqr; //we return it
    }
    
    public static boolean sameRow(HasPosition... positions){
        if (positions.length == 0) {
            throw new IllegalArgumentException("can't check if 0 cells are in the same row. List must include at least one cell.");
        }
        return Arrays.stream(positions).allMatch(p -> p.sameRow(positions[0]));
    }
    public static boolean sameCol(HasPosition... positions){
        if (positions.length == 0) {
            throw new IllegalArgumentException("can't check if 0 cells are in the same column. List must include at least one cell.");
        }
        return Arrays.stream(positions).allMatch(p -> p.sameCol(positions[0]));
    }
    public static boolean sameSqr(HasPosition... positions){
        if (positions.length == 0) {
            throw new IllegalArgumentException("can't check if 0 cells are in the same square. List must include at least one cell.");
        }
        return Arrays.stream(positions).allMatch(p -> p.sameSqr(positions[0]));
    }
    
    static Position of(int row, int col) {
        return new Position(row, col);
    }
    static Position of(Cell cell) {
        return new Position(cell.row(), cell.col());
    }
}
