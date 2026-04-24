package tictactoe;

import org.junit.jupiter.api.Test;
import tictactoe.engine.GameEngine;
import tictactoe.factory.PlayerFactory;
import tictactoe.model.Mark;
import tictactoe.model.MatchResult;
import tictactoe.model.Position;
import tictactoe.player.Player;
import tictactoe.repository.MatchHistoryRepository;
import tictactoe.service.GameMode;
import tictactoe.state.WinState;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameEngineTest {
    @Test
    void humanVsHumanCanReachWinState() {
        PlayerFactory factory = new PlayerFactory();
        Player playerX = factory.createHuman("A", Mark.X);
        Player playerO = factory.createHuman("B", Mark.O);
        FakeRepository repository = new FakeRepository();
        GameEngine engine = new GameEngine(playerX, playerO, repository, GameMode.HUMAN_VS_HUMAN);

        assertTrue(engine.submitHumanMove(new Position(0, 0)));
        assertTrue(engine.submitHumanMove(new Position(1, 0)));
        assertTrue(engine.submitHumanMove(new Position(0, 1)));
        assertTrue(engine.submitHumanMove(new Position(1, 1)));
        assertTrue(engine.submitHumanMove(new Position(0, 2)));

        assertTrue(engine.getState() instanceof WinState);
        assertEquals(1, repository.results.size());
        assertEquals("X", repository.results.get(0).winner());
    }

    private static class FakeRepository implements MatchHistoryRepository {
        private final List<MatchResult> results = new ArrayList<>();

        @Override
        public void save(MatchResult result) {
            results.add(result);
        }

        @Override
        public List<MatchResult> findRecent(int limit) {
            return results;
        }
    }
}
