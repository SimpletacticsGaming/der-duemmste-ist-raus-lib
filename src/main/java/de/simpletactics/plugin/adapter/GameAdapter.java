package de.simpletactics.plugin.adapter;

import de.simpletactics.domain.models.game.Game;
import de.simpletactics.domain.services.port.GamePort;
import de.simpletactics.plugin.database.DataBaseCon;
import java.sql.SQLException;
import java.sql.Statement;

public class GameAdapter implements GamePort {

  private DataBaseCon con;

  public GameAdapter(DataBaseCon con) {
    this.con = con;
  }

  @Override
  public void saveGame(Game game) {
    try {
      Statement state = con.getCon().createStatement();
      state.executeUpdate(
          "INSERT INTO quiz_game (id,type,date) VALUES ('" + game.getGameId() + "', '1', (SELECT NOW()))");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public void saveFinishedGame(Game game) {
    try {
      Statement state = con.getCon().createStatement();
      state.executeUpdate(
          "INSERT INTO quiz_game_finished (id,winner,bank,turns,date) VALUES (" + game.getGameId() + ", '"
              + game.getWinner()
              + "', '" + game.getBankAmount() + "', '" + game.getTurns() + "')");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }
}
