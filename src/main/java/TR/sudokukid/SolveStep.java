package TR.sudokukid;

import TR.sudokukid.methods.SolvingMethodName;
import static TR.sudokukid.SudokuParamValidation.*;
import java.util.Objects;

public record SolveStep(
        SolvingMethodName method, SolveAction solveType, Position targetCell, 
        int candidate, SolveCause cause) {
    
    public SolveStep {
        Objects.requireNonNull(method);
        Objects.requireNonNull(solveType);
        Objects.requireNonNull(targetCell);
        checkValidCandidate(candidate);
        Objects.requireNonNull(cause);
    }
    
    public enum SolveAction {CELL_FILLED, CANDIDATE_REMOVED}
    
}