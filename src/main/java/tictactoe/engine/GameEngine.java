package tictactoe.engine;

import tictactoe.model.Board;
import tictactoe.model.Position;
import tictactoe.player.Player;
import tictactoe.state.GameState;
import tictactoe.state.InProgressState;
import tictactoe.observer.GameObserver;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {

    private Board board;
    private Player currentPlayer;
    private Player player1;
    private Player player2;
    private GameState state;
    private List<GameObserver> observers = new ArrayList<>();

    public GameEngine(Player p1, Player p2) {
        this.board = new Board();
        this.player1 = p1;
        this.player2 = p2;
        this.currentPlayer = p1;
        this.state = new InProgressState();
    }

    public void attach(GameObserver obs) {
        observers.add(obs);
    }

    private void notifyObservers(String msg) {
        for (GameObserver obs : observers) {
            obs.update(msg);
        }
    }

    public void playTurn() {
        Position move = currentPlayer.makeMove(board);

        if (board.placeMark(move, currentPlayer.getMark())) {
            notifyObservers("Player " + currentPlayer.getMark() + " played " + move.getRow() + "," + move.getCol());
            switchPlayer();
        } else {
            notifyObservers("Invalid move!");
        }
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }
}