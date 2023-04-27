package de.simpletactics.domain.services.port;

import de.simpletactics.domain.models.GameException;
import de.simpletactics.domain.models.questions.Question;
import java.util.Stack;

public interface QuestionPort {

  Stack<Question> loadQuestions(int minQuestionAge) throws GameException;

}
