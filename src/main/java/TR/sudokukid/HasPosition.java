package TR.sudokukid;

/**
 *
 * @author jsanchez
 */
public interface HasPosition {
    int row();
    int col();
    
    /**
     * Returns the row of this instance of Cell in array index format,
     * aka, substracting 1 (0 to 8).
     * 
     * @return this Cell's row index
     * @see #row
     */
    default int rowIndex() {
        return row()-1;
    }
    
    default boolean sameRow(HasPosition other) {
        return row() == other.row();
    }
    
    /**
     * Returns the column of this instance of Cell in array index format,
     * aka, substracting 1 (0 to 8).
     * 
     * @return this Cell's column index
     * @see #col
     */
    default int colIndex() {
        return col()-1;
    }
    
    default boolean sameCol(HasPosition other) {
        return col() == other.col();
    }

    /**
     * Returns the square of this instance of Cell.
     * It's not an attribute, we calculate it.
     * 
     * @return this Cell's square
     * @see #sqr(int, int) 
     */
    default int sqr() {
        return Position.sqr(row(),col());
    }
    /**
     * Method getSqrCells returns the square of this instance 
     * of Cell in array index format, aka, substracting 1 (0 to 8).
     * 
     * @return this Cell's square index
     */
    default int sqrIndex() {
        return sqr()-1;
    }
    
    default boolean sameSqr(HasPosition other) {
        return sqr() == other.sqr();
    }
}
