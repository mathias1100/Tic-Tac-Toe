package tictactoe.state;

public class DrawState implements GameState {
    @Override
    public boolean isTerminal() {
        return true;
    }

    @Override
    public String getStatusMessage() {
        return "Draw game";
    }
}
