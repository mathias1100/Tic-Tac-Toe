package tictactoe.state;

public class InProgressState implements GameState {
    @Override
    public boolean isTerminal() {
        return false;
    }

    @Override
    public String getStatusMessage() {
        return "Game in progress";
    }
}
