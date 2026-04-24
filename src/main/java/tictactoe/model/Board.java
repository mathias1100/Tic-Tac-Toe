package tictactoe.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    public static final int SIZE = 3;
    private final Cell[][] cells;

    public Board() {
        cells = new Cell[SIZE][SIZE];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col] = new Cell(new Position(row, col));
            }
        }
    }

    public void clear() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col].setMark(Mark.EMPTY);
            }
        }
    }

    public boolean isValidPosition(Position position) {
        return position != null
                && position.getRow() >= 0 && position.getRow() < SIZE
                && position.getCol() >= 0 && position.getCol() < SIZE;
    }

    public Cell getCell(Position position) {
        if (!isValidPosition(position)) {
            throw new IllegalArgumentException("Invalid board position.");
        }
        return cells[position.getRow()][position.getCol()];
    }

    public Mark getMarkAt(Position position) {
        return getCell(position).getMark();
    }

    public boolean isEmptyAt(Position position) {
        return getCell(position).isEmpty();
    }

    public void placeMark(Position position, Mark mark) {
        if (!isValidPosition(position)) {
            throw new IllegalArgumentException("Invalid board position.");
        }
        if (mark == null || mark == Mark.EMPTY) {
            throw new IllegalArgumentException("A real player mark is required.");
        }
        if (!isEmptyAt(position)) {
            throw new IllegalStateException("Cell is already occupied.");
        }
        getCell(position).setMark(mark);
    }

    public boolean isFull() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (cells[row][col].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean hasWinner(Mark mark) {
        for (int row = 0; row < SIZE; row++) {
            if (cells[row][0].getMark() == mark && cells[row][1].getMark() == mark && cells[row][2].getMark() == mark) {
                return true;
            }
        }
        for (int col = 0; col < SIZE; col++) {
            if (cells[0][col].getMark() == mark && cells[1][col].getMark() == mark && cells[2][col].getMark() == mark) {
                return true;
            }
        }
        return (cells[0][0].getMark() == mark && cells[1][1].getMark() == mark && cells[2][2].getMark() == mark)
                || (cells[0][2].getMark() == mark && cells[1][1].getMark() == mark && cells[2][0].getMark() == mark);
    }

    public List<Position> getAvailablePositions() {
        List<Position> available = new ArrayList<>();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Position position = new Position(row, col);
                if (isEmptyAt(position)) {
                    available.add(position);
                }
            }
        }
        return available;
    }

    public Board copy() {
        Board copy = new Board();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Mark mark = cells[row][col].getMark();
                if (mark != Mark.EMPTY) {
                    copy.cells[row][col].setMark(mark);
                }
            }
        }
        return copy;
    }
}
