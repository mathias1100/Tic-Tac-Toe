package tictactoe.player;

import tictactoe.model.Mark;
import tictactoe.strategy.MoveStrategy;

public class CompPlayer extends Player {
    public CompPlayer(String name, Mark mark, MoveStrategy strategy) {
        super(name, mark, strategy);
    }

    @Override
    public boolean isAi() {
        return true;
    }
}
