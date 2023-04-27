package de.simpletactics.plugin.adapter;

import de.simpletactics.domain.services.port.LinkingPort;
import de.simpletactics.plugin.database.DataBaseCon;
import java.sql.SQLException;
import java.sql.Statement;

public class LinkingAdapter implements LinkingPort {

  private DataBaseCon con;

  public LinkingAdapter(DataBaseCon con) {
    this.con = con;
  }

  @Override
  public void linkQuestionToGame(String gameId, int questionId) {
    try {
      Statement state = con.getCon().createStatement();
      state.executeUpdate("DELETE FROM quiz_connection WHERE frage_id = " + questionId + "");
      state.executeUpdate(
          "INSERT INTO quiz_connection (game_id, frage_id) VALUES ('" + gameId + "', '" + questionId + "')");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }
}
