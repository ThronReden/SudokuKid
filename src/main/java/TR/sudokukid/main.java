package TR.sudokukid;

import static TR.sudokukid.matrixLibrary.*;
import java.util.Arrays;

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
//        String cadena = Arrays.deepToString(SK.getSudoku().toMatrix());
//        cadena = cadena.replaceAll("\\[", "\\{");
//        cadena = cadena.replaceAll("\\]", "\\}");
//        System.out.println(cadena);
    }
    
}
