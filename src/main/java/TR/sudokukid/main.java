package TR.sudokukid;

import static TR.sudokukid.matrixLibrary.*;

/**
 *
 * @author jsanchez
 */
public class main {
    
    public static void main (String[] args){        
        SudokuKid SK = new SudokuKid(claudeSudoku2);
        SK.solvingLoop();
    }
    
}
