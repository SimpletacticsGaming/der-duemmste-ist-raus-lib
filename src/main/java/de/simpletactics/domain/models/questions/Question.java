package de.simpletactics.domain.models.questions;

import lombok.Getter;

@Getter
public class Question {

  private int id;
  private String question;
  private String answer;

  public Question(int id, String question, String answer) {
    this.id = id;
    this.question = question;
    this.answer = answer;
  }

  @Override
  public String toString() {
    return "Question{" +
        "id=" + id +
        ", question='" + question + '\'' +
        ", answer='" + answer + '\'' +
        '}';
  }
}
