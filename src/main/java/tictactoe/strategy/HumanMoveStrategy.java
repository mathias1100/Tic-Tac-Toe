package tictactoe.strategy;

import tictactoe.model.Board;
import tictactoe.model.Mark;
import tictactoe.model.Position;

public class HumanMoveStrategy implements MoveStrategy {
    private Position nextMove;

    public void setMove(Position nextMove) {
        this.nextMove = nextMove;
    }

    @Override
    public Position chooseMove(Board board, Mark mark) {
        if (nextMove == null) {
            throw new IllegalStateException("Human move was not set before chooseMove().");
        }
        Position chosen = nextMove;
        nextMove = null;
        return chosen;
    }
}
