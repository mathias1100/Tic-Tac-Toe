package tictactoe;

import org.junit.jupiter.api.Test;
import tictactoe.factory.PlayerFactory;
import tictactoe.model.Mark;
import tictactoe.player.Player;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    @Test
    void factoryCreatesHumanAndComputerPlayers() {
        PlayerFactory factory = new PlayerFactory();
        Player human = factory.createHuman("Human", Mark.X);
        Player ai = factory.createComputer("Computer", Mark.O);

        assertFalse(human.isAi());
        assertTrue(ai.isAi());
        assertEquals(Mark.X, human.getMark());
        assertEquals(Mark.O, ai.getMark());
    }
}
