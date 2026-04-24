package tictactoe.player;

import tictactoe.model.Mark;
import tictactoe.strategy.HumanMoveStrategy;

public class HumanPlayer extends Player {
    public HumanPlayer(String name, Mark mark, HumanMoveStrategy strategy) {
        super(name, mark, strategy);
    }

    @Override
    public boolean isAi() {
        return false;
    }

    public HumanMoveStrategy getHumanStrategy() {
        return (HumanMoveStrategy) getStrategy();
    }
}
