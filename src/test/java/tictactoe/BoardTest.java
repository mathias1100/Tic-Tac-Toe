package tictactoe;

import org.junit.jupiter.api.Test;
import tictactoe.model.Board;
import tictactoe.model.Mark;
import tictactoe.model.Position;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    @Test
    void boardStartsEmpty() {
        Board board = new Board();
        assertEquals(Mark.EMPTY, board.getMarkAt(new Position(0, 0)));
        assertEquals(Mark.EMPTY, board.getMarkAt(new Position(1, 1)));
        assertEquals(Mark.EMPTY, board.getMarkAt(new Position(2, 2)));
    }

    @Test
    void canPlaceMarkOnEmptyCell() {
        Board board = new Board();
        Position position = new Position(1, 1);
        board.placeMark(position, Mark.X);
        assertEquals(Mark.X, board.getMarkAt(position));
    }

    @Test
    void cannotPlaceMarkOnOccupiedCell() {
        Board board = new Board();
        Position position = new Position(0, 0);
        board.placeMark(position, Mark.X);
        assertThrows(IllegalStateException.class, () -> board.placeMark(position, Mark.O));
    }

    @Test
    void invalidPositionThrowsException() {
        Board board = new Board();
        assertThrows(IllegalArgumentException.class, () -> board.getMarkAt(new Position(3, 3)));
    }

    @Test
    void boardBecomesFullAfterAllCellsFilled() {
        Board board = new Board();
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                board.placeMark(new Position(row, col), row % 2 == 0 ? Mark.X : Mark.O);
            }
        }
        assertTrue(board.isFull());
    }

    @Test
    void detectsWinnerAcrossRow() {
        Board board = new Board();
        board.placeMark(new Position(0, 0), Mark.X);
        board.placeMark(new Position(0, 1), Mark.X);
        board.placeMark(new Position(0, 2), Mark.X);
        assertTrue(board.hasWinner(Mark.X));
    }
}
