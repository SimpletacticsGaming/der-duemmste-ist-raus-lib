package de.simpletactics.plugin.adapter;

import de.simpletactics.domain.services.port.LinkingPort;
import de.simpletactics.plugin.database.DataBaseCon;
import java.sql.SQLException;
import java.sql.Statement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class LinkingAdapter implements LinkingPort {

  private JdbcTemplate jdbcTemplate;

  @Override
  public void linkQuestionToGame(String gameId, int questionId) {
      jdbcTemplate.execute("DELETE FROM quiz_connection WHERE frage_id = " + questionId + "");
      jdbcTemplate.execute(
          "INSERT INTO quiz_connection (game_id, frage_id) VALUES ('" + gameId + "', '" + questionId + "')");
  }
}
