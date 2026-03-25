package tictactoe.model;

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

    public boolean isValidPosition(Position position) {
        int row = position.getRow();
        int col = position.getCol();

        return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
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
}