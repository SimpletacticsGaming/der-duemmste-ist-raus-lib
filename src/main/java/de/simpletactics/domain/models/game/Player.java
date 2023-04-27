package de.simpletactics.domain.models.game;

import lombok.Data;

@Data
public class Player {

  private String name;
  private int correctQuestionsInRound;
  private int wrongQuestionsInRound;
  private int correctQuestionsInGame;
  private int wrongQuestionsInGame;
  private PlayerState state;

  public Player() {
  }

  public Player(String name, PlayerState state) {
    this.name = name;
    this.correctQuestionsInRound = 0;
    this.wrongQuestionsInRound = 0;
    this.correctQuestionsInGame = 0;
    this.wrongQuestionsInGame = 0;
    this.state = state;
  }

  public void addCorrectAnwser() {
    correctQuestionsInRound++;
    correctQuestionsInGame++;
  }

  public void addWrongAnwser() {
    wrongQuestionsInRound++;
    wrongQuestionsInGame++;
  }

  public void resetCounterForNextRound() {
    correctQuestionsInRound = 0;
    wrongQuestionsInRound = 0;
  }

}
