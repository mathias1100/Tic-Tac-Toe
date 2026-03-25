
package tictactoe.strategy;

import tictactoe.model.Board;
import tictactoe.model.Position;
import java.util.Random;
public class RandomAIMoveStrategy implements MoveStrategy {
    private Random rand = new Random();

    @Override
    public Position chooseMove(Board board) {
        return new Position(rand.nextInt(3), rand.nextInt(3));
    }
}