package TR.obsolete;

/**
 *
 * @author jsanchez
 */
public class mainObsolete {
    
    protected static final String menneske4813117 = "300000008010500070096073210009040030007109800080030700058310920040006080900000007";
    
    public static void main (String[] args){        
        SudokuKidObsolete SK = new SudokuKidObsolete(SudokuObsolete.toMatrix(menneske4813117));
        SK.solvingLoop();
    }
    
}