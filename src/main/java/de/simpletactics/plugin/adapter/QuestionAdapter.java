package de.simpletactics.plugin.adapter;

import de.simpletactics.domain.models.GameException;
import de.simpletactics.domain.models.questions.Question;
import de.simpletactics.domain.services.port.QuestionPort;
import de.simpletactics.plugin.database.DataBaseCon;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Stack;

public class QuestionAdapter implements QuestionPort {

  private final int DEFAULT_AMOUNT_OF_QUESTIONS = 20;
  private DataBaseCon con;

  public QuestionAdapter(DataBaseCon con) {
    this.con = con;
  }

  @Override
  public Stack<Question> loadQuestions(int minQuestionAge) throws GameException {
    Stack<Question> questions = new Stack<>();
    try {
      Statement state = con.getCon().createStatement();
      if (areEnoughQuestionsInDatabase(state, minQuestionAge)) {
        ResultSet rs = state.executeQuery(
            "SELECT f.* FROM quiz_fragen AS f LEFT JOIN quiz_connection AS c ON f.id = c.frage_id "
                + "LEFT JOIN quiz_game AS g ON c.game_id = g.id WHERE g.date is NULL OR DATEDIFF(NOW(),g.date) >= "
                + minQuestionAge + " ORDER BY RAND() LIMIT " + DEFAULT_AMOUNT_OF_QUESTIONS);
        rs.next();
        do {
          int id = rs.getInt("id");
          String question = rs.getString("frage");
          String answer = rs.getString("antwort");
          questions.add(new Question(id, question, answer));
        } while (rs.next());

        rs.close();
        state.close();
        return questions;
      } else {
        state.close();
        // TODO: Decrease minQuestionAge in proper order
        return loadQuestions(0);
      }
    } catch (SQLException e) {
      throw new GameException(e.getMessage());
    }
  }

  private boolean areEnoughQuestionsInDatabase(Statement statement, int minQuestionAge) throws SQLException {
    return size(statement, minQuestionAge) >= DEFAULT_AMOUNT_OF_QUESTIONS;
  }

  private int size(Statement state, int minQuestionAge) throws SQLException {
    ResultSet rs = state.executeQuery(
        "SELECT COUNT(f.id) AS count FROM quiz_fragen AS f LEFT JOIN quiz_connection AS c ON f.id = c.frage_id "
            + "LEFT JOIN quiz_game AS g ON c.game_id = g.id WHERE g.date is NULL OR DATEDIFF(NOW(),g.date) >= "
            + minQuestionAge + ";");
    rs.next();
    return rs.getInt("count");
  }

}
