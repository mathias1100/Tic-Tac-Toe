package tictactoe.state;

import tictactoe.model.Mark;

public class WinState implements GameState {
    private final Mark winner;

    public WinState(Mark winner) {
        this.winner = winner;
    }

    public Mark getWinner() {
        return winner;
    }

    @Override
    public boolean isTerminal() {
        return true;
    }

    @Override
    public String getStatusMessage() {
        return "Winner: " + winner;
    }
}
