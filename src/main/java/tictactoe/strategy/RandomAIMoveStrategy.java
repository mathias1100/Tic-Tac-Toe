package tictactoe.strategy;

import tictactoe.model.Board;
import tictactoe.model.Mark;
import tictactoe.model.Position;

import java.util.List;
import java.util.Random;

public class RandomAIMoveStrategy implements MoveStrategy {
    private final Random random;

    public RandomAIMoveStrategy() {
        this(new Random());
    }

    public RandomAIMoveStrategy(Random random) {
        this.random = random;
    }

    @Override
    public Position chooseMove(Board board, Mark mark) {
        List<Position> available = board.getAvailablePositions();
        if (available.isEmpty()) {
            throw new IllegalStateException("No moves available.");
        }
        return available.get(random.nextInt(available.size()));
    }
}
