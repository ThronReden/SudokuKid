package TR.sudokukid;

import static TR.sudokukid.matrixLibrary.*;

/**
 *
 * @author jsanchez
 */
public class main {
    
    public static void main (String[] args) {        
        SudokuKid SK = new SudokuKid(menneske259058);
        SK.basicSudokuRules();
//        SK.getSudoku().show();
        SK.solvingLoop();
    }
    
}
