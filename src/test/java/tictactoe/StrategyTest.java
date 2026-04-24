package tictactoe;

import org.junit.jupiter.api.Test;
import tictactoe.model.Board;
import tictactoe.model.Mark;
import tictactoe.model.Position;
import tictactoe.strategy.HumanMoveStrategy;
import tictactoe.strategy.RandomAIMoveStrategy;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class StrategyTest {
    @Test
    void humanStrategyReturnsSelectedMove() {
        HumanMoveStrategy strategy = new HumanMoveStrategy();
        Position position = new Position(2, 2);
        strategy.setMove(position);
        assertEquals(position, strategy.chooseMove(new Board(), Mark.X));
    }

    @Test
    void randomAiAlwaysReturnsValidAvailableMove() {
        Board board = new Board();
        board.placeMark(new Position(0, 0), Mark.X);
        RandomAIMoveStrategy strategy = new RandomAIMoveStrategy(new Random(3));
        Position move = strategy.chooseMove(board, Mark.O);
        assertTrue(board.isValidPosition(move));
        assertTrue(board.isEmptyAt(move));
    }
}
