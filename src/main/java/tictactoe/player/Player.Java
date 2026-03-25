package tictactoe.player;

import tictactoe.model.Board;
import tictactoe.model.Mark;
import tictactoe.model.Position;
import tictactoe.strategy.MoveStrategy;

public class Player {

    private Mark mark;
    private MoveStrategy strategy;

    public Player(Mark mark, MoveStrategy strategy) {
        this.mark = mark;
        this.strategy = strategy;
    }

    public Position makeMove(Board board) {
        return strategy.chooseMove(board);
    }

    public Mark getMark() {
        return mark;
    }
}