package SudokuKid;

/**
 *
 * @author jsanchez
 */
public class main {
    
    public static void main (String[] args){
        SudokuKid SK = new SudokuKid(Sudoku.toMatrix(SudokuKid.menneske4813117));
        SK.solvingLoop();
    }
    
}