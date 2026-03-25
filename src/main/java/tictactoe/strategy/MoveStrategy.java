package tictactoe.strategy;

import tictactoe.model.Board;
import tictactoe.model.Position;

public interface MoveStrategy {
    Position chooseMove(Board board);
}