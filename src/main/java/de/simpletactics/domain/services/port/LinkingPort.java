package de.simpletactics.domain.services.port;

public interface LinkingPort {

  void linkQuestionToGame(String gameId, int questionId);

}
