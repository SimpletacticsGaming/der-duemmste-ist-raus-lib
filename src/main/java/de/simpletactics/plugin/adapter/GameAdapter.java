package de.simpletactics.plugin.adapter;

import de.simpletactics.domain.models.game.Game;
import de.simpletactics.domain.services.port.GamePort;
import de.simpletactics.plugin.database.DataBaseCon;
import java.sql.SQLException;
import java.sql.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class GameAdapter implements GamePort {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public void saveGame(Game game) {
    jdbcTemplate.execute(
        "INSERT INTO quiz_game (id,type,date) VALUES ('" + game.getGameId() + "', '1', (SELECT NOW()))");
  }

  @Override
  public void saveFinishedGame(Game game) {
      jdbcTemplate.execute(
          "INSERT INTO quiz_game_finished (id,winner,bank,turns,date) VALUES ('" + game.getGameId() + "', '"
              + game.getWinner().getName()
              + "', '" + game.getBankAmount() + "', '" + game.getTurns() + "')");
  }
}
