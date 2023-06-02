package de.simpletactics.domain.services;

import java.util.List;

public class BankService {

  private static final List<Integer> points = List.of(0, 1, 2, 4, 6, 9, 12, 15, 18, 21, 25);
  private int pointCounter = 0;
  private int currentCounter = 0;
  private int savedBankCounter = 0;

  public int getSavedBankCounter() {
    return savedBankCounter;
  }

  public int getCurrentCounter() {
    return currentCounter;
  }

  public void riseBankCounter() {
    currentCounter = riseBankCount();
  }

  public void saveCurrentAddCounter() {
    savedBankCounter += currentCounter;
    resetCurrentCounter();
    resetBankCount();
  }

  public void resetBankCounter() {
    resetBankCount();
    resetCurrentCounter();
  }

  public static List<Integer> getPoints() {
    return points;
  }

  private void resetBankCount() {
    pointCounter = 0;
  }

  private void resetCurrentCounter() {
    currentCounter = 0;
  }

  private int riseBankCount() {
    int counter = pointCounter < points.size() ? points.get(pointCounter) : points.get(points.size() - 1);
    pointCounter++;
    return counter;
  }

}
