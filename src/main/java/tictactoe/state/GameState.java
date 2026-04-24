package tictactoe.state;

public interface GameState {
    boolean isTerminal();
    String getStatusMessage();
}
