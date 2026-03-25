package tictactoe.strategy;

import tictactoe.model.Board;
import tictactoe.model.Position;
public class HumanMoveStrategy implements MoveStrategy {

    private Position nextMove;

    public void setMove(Position pos) {
        this.nextMove = pos;
    }

    @Override
    public Position chooseMove(Board board) {
        return nextMove;
    }
}