package de.simpletactics.plugin.adapter;

import de.simpletactics.domain.models.GameException;
import de.simpletactics.domain.models.questions.Question;
import de.simpletactics.domain.services.port.QuestionPort;
import de.simpletactics.plugin.database.DataBaseCon;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class QuestionAdapter implements QuestionPort {

  private final int DEFAULT_AMOUNT_OF_QUESTIONS = 20;
  private JdbcTemplate jdbcTemplate;

  @Override
  public Stack<Question> loadQuestions(int minQuestionAge) {
    Stack<Question> questions = new Stack<>();
      if (areEnoughQuestionsInDatabase(minQuestionAge)) {
        List<Map<String, Object>> rs = jdbcTemplate.queryForList(
            "SELECT f.* FROM quiz_fragen AS f LEFT JOIN quiz_connection AS c ON f.id = c.frage_id "
                + "LEFT JOIN quiz_game AS g ON c.game_id = g.id WHERE g.date is NULL OR DATEDIFF(NOW(),g.date) >= "
                + minQuestionAge + " ORDER BY RAND() LIMIT " + DEFAULT_AMOUNT_OF_QUESTIONS);

        rs.forEach(entryMap -> {
          int id = Integer.parseInt(String.valueOf(entryMap.get("id")));
          String question = String.valueOf(entryMap.get("frage"));
          String answer = String.valueOf(entryMap.get("antwort"));
          questions.add(new Question(id, question, answer));
        });

        return questions;
      } else {
        // TODO: Decrease minQuestionAge in proper order
        return loadQuestions(0);
      }
  }

  private boolean areEnoughQuestionsInDatabase(int minQuestionAge) {
    return size(minQuestionAge) >= DEFAULT_AMOUNT_OF_QUESTIONS;
  }

  private int size(int minQuestionAge) {
    List<Map<String, Object>> result = jdbcTemplate.queryForList(
        "SELECT COUNT(f.id) AS count FROM quiz_fragen AS f LEFT JOIN quiz_connection AS c ON f.id = c.frage_id "
            + "LEFT JOIN quiz_game AS g ON c.game_id = g.id WHERE g.date is NULL OR DATEDIFF(NOW(),g.date) >= "
            + minQuestionAge + ";");
    return Integer.parseInt(String.valueOf(result.get(0).get("count")));
  }

}
