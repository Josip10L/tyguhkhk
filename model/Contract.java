package model;

import java.util.List;

/**
 * Contract class.
 */
public class Contract {
  private String id;
  private Member lender;
  private Member borrower;
  private Item item;
  private int startDate;
  private int endDate;
  private double creditTran;
  private boolean isCancelled = false;

  /**
   * Contract method.
   */
  public Contract(Member lender, Member borrower, Item item, int startDate, int endDate) {
    this.lender = item.getOwner();
    this.borrower = borrower;
    this.item = item;
    this.startDate = startDate;
    this.endDate = endDate;
    this.creditTran = calculateCreditTran();

    RandomId randomIdGen = new RandomId();
    this.id = randomIdGen.genId();
  }

  public void cancelContract() {
    isCancelled = true;
    item = null;
  }

  public boolean isCancelled() {
    return isCancelled;
  }

  // Method to check if the contract period has ended
  public boolean isContractEnded(int currentDate) {
    return currentDate >= endDate;
  }

  /**
   * activateContract method.
   */
  public void activateContract() {
    borrower.removeCredits(creditTran);
    lender.addCredits(creditTran);
    lender.transferItem(item, borrower);
  }  

  // Method to handle the return of item after contract ends
  public void endContract() {
    borrower.returnItem(item, lender);
  }

  private double calculateCreditTran() {
    int days = endDate - startDate + 1;
    double price = item.getCost();
    return days * price;
  }

  private boolean borrowerEnoughCredits() {
    double requiredCredits = calculateCreditTran();
    return borrower.getCredits() >= requiredCredits;
  }

  /**
   * valid method.
   */
  public boolean valid(List<Contract> existingContracts, Time time) {
    if (!borrowerEnoughCredits()) {
      return false;
    }
    return itemAvailable(existingContracts, time);
  }

  private boolean itemAvailable(List<Contract> existingContracts, Time time) {
    for (Contract contract : existingContracts) {
      if (contract.getItem().equals(this.item)
          && ((startDate <= contract.getEndDate() && startDate >= contract.getStartDate()) 
          || (endDate <= contract.getEndDate() && endDate >= contract.getStartDate()))) {
        return false;
      }
    }
    return true;
  }

  /**
   * makeContract method.
   */
  public boolean makeContract(List<Contract> existingContracts, Time time) {
    if (valid(existingContracts, time)) {
      if (borrower.removeCredits(creditTran)) {
        lender.addCredits(creditTran);
        existingContracts.add(this);
        return true; // Contract creation successful
      }
    }
    return false; // Contract creation failed
  }

  public String getId() {
    return id;
  }

  public Member getLender() {
    return new Member(lender);
  }

  public Member getBorrower() {
    return new Member(borrower);
  }

  public Item getItem() {
    return new Item(item);
  }

  public double getCreditTran() {
    return creditTran;
  }

  public int getStartDate() {
    return startDate;
  }

  public int getEndDate() {
    return endDate;
  }

  public boolean getBorrowerEnoughCredits() {
    return borrowerEnoughCredits();
  }

  public boolean getItemAvailable(List<Contract> existingContracts, Time time) {
    return itemAvailable(existingContracts, time);
  }
}
