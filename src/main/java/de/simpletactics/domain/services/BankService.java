package de.simpletactics.domain.services;

public class BankService {

  private final int[] points = {1, 2, 4, 6, 9, 12, 15, 18, 21, 25};
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

  private void resetBankCount() {
    pointCounter = 0;
  }

  private void resetCurrentCounter() {
    currentCounter = 0;
  }

  private int riseBankCount() {
    int counter = pointCounter < points.length ? points[pointCounter] : points[points.length - 1];
    pointCounter++;
    return counter;
  }

}
