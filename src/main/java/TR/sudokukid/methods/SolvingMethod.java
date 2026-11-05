package TR.sudokukid.methods;

import TR.sudokukid.SolveStep;
import TR.sudokukid.Sudoku;
import java.util.List;

/**
 *
 * @author jsanchez
 */
public interface SolvingMethod {
    
    public SolvingMethodName name();
    
    public List<SolveStep> find(Sudoku sudoku);
    
}
