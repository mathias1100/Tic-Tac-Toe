package tictactoe.model;

public class Cell {

    private final Position position;
    private Mark mark;

    public Cell(Position position) {
        this.position = position;
        this.mark = Mark.EMPTY;
    }

    public Position getPosition() {
        return position;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    public boolean isEmpty() {
        return mark == Mark.EMPTY;
    }
}