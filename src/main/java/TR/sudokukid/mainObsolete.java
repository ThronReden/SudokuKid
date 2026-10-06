package TR.sudokukid;

import static TR.sudokukid.matrixLibrary.*;

/**
 *
 * @author jsanchez
 */
public class mainObsolete {
    
    public static void main (String[] args){        
        SudokuKidObsolete SK = new SudokuKidObsolete(SudokuObsolete.toMatrix(menneske4813117));
        SK.solvingLoop();
    }
    
}