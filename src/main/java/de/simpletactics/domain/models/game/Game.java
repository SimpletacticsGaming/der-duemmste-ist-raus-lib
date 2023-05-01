package de.simpletactics.domain.models.game;

import de.simpletactics.domain.models.GameException;
import de.simpletactics.domain.models.questions.Question;
import de.simpletactics.domain.services.BankService;
import de.simpletactics.domain.services.port.GamePort;
import de.simpletactics.domain.services.port.LinkingPort;
import de.simpletactics.domain.services.port.QuestionPort;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.UUID;

public class Game {

  private final QuestionPort questionPort;
  private final LinkingPort linkingPort;
  private final GamePort gamePort;
  private final BankService bankService = new BankService();
  private String gameId;
  private Stack<Question> questions = new Stack<>();
  private Question currQuestion;
  private int bankAmount;
  private int bankCounter;
  private int turns;
  private List<Player> players = new ArrayList<>();
  private Player currPlayer;
  private Player winner;
  private int currPlayerIndex = 1;


  public Game(Player moderator, QuestionPort questionPort, LinkingPort linkingPort, GamePort gamePort) {
    this.gameId = UUID.randomUUID().toString();
    this.bankAmount = 0;
    this.turns = 0;

    this.players.add(moderator);
    this.questionPort = questionPort;
    this.linkingPort = linkingPort;
    this.gamePort = gamePort;
  }

  public String getGameId() {
    return gameId;
  }

  public int getBankAmount() {
    return bankAmount;
  }

  public int getTurns() {
    return turns;
  }

  public Player getWinner() {
    return winner;
  }

  public static Game createGame(Player moderator, QuestionPort questionPort, LinkingPort linkingPort,
      GamePort gamePort) {
    return new Game(moderator, questionPort, linkingPort, gamePort);
  }

  public Game saveBank() {
    bankService.saveCurrentAddCounter();
    updateBankCounters();
    return this;
  }

  public Game startGame(List<Player> players) throws GameException {
    this.players.addAll(players);
    gamePort.saveGame(this);
    loadQuestionsForGame();
    nextTurn();
    return this;
  }

  public Game correctAnswer(boolean activeRound) throws GameException {
    this.currPlayer.addCorrectAnwser();
    bankService.riseBankCounter();
    updateBankCounters();
    if (activeRound) {
      nextTurn();
    }
    return this;
  }

  public Game wrongAnswer(boolean activeRound) throws GameException {
    this.currPlayer.addWrongAnwser();
    bankService.resetBankCounter();
    updateBankCounters();
    if (activeRound) {
      nextTurn();
    }
    return this;
  }

  public Game startRound(boolean activeRound) throws GameException {
    resetPlayerCounts();
    bankService.resetBankCounter();
    updateBankCounters();
    if (activeRound) {
      nextTurn();
    }
    return this;
  }

  public Game kickPlayer(String playerName) {
    this.players.stream().filter(player -> player.getName().equalsIgnoreCase(playerName)).findFirst()
        .ifPresent(player -> player.setState(PlayerState.KICKED));
    return this;
  }

  public Game setGameWinner(String playerName) {
    this.players.stream().filter(player -> player.getName().equalsIgnoreCase(playerName)).findFirst()
        .ifPresent(player -> this.winner = player);
    return this;
  }

  public void endGame() {
    gamePort.saveFinishedGame(this);
  }

  private void nextTurn() throws GameException {
    if (questions.size() > 0) {
      this.turns++;
      Question nextQuestion = questions.pop();
      this.currQuestion = nextQuestion;
      linkQuestion(nextQuestion.getId());
      selectNextPlayer();
      if (questions.size() < 2) {
        loadQuestionsForGame();
      }
    } else {
      throw new GameException("No questions");
    }
  }

  private void loadQuestionsForGame() throws GameException {
    this.questions.addAll(questionPort.loadQuestions(90));
  }

  private void selectNextPlayer() throws GameException {
    for (int i = 0; i < this.players.size(); i++) {
      Player currPlayer = this.players.get(currPlayerIndex % this.players.size());
      if (currPlayer.getState() == PlayerState.ACTIVE) {
        this.currPlayer = currPlayer;
        currPlayerIndex++;
        break;
      }
      currPlayerIndex++;
    }
  }

  private void resetPlayerCounts() {
    this.players.forEach(Player::resetCounterForNextRound);
  }

  private void linkQuestion(int questionId) {
    linkingPort.linkQuestionToGame(gameId, questionId);
  }

  private void updateBankCounters() {
    bankAmount = bankService.getSavedBankCounter();
    bankCounter = bankService.getCurrentCounter();
  }

}
