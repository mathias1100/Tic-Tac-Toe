package tictactoe.player;

import tictactoe.model.Board;
import tictactoe.model.Mark;
import tictactoe.model.Position;
import tictactoe.strategy.MoveStrategy;

public abstract class Player {
    private final String name;
    private final Mark mark;
    private final MoveStrategy strategy;

    protected Player(String name, Mark mark, MoveStrategy strategy) {
        this.name = name;
        this.mark = mark;
        this.strategy = strategy;
    }

    public Position makeMove(Board board) {
        return strategy.chooseMove(board, mark);
    }

    public String getName() {
        return name;
    }

    public Mark getMark() {
        return mark;
    }

    public MoveStrategy getStrategy() {
        return strategy;
    }

    public abstract boolean isAi();
}
