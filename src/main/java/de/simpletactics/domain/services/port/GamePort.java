package de.simpletactics.domain.services.port;

import de.simpletactics.domain.models.game.Game;

public interface GamePort {

  void saveGame(Game game);

  void saveFinishedGame(Game game);

}
