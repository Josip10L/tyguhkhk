package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Item class.
 */
public class Item {
  private String id;
  private String name;
  private String category;
  private String description;
  private double cost;
  private Member owner;
  private int dayOfCreation;
  private List<Contract> contracts;

  /**
   * Item method.
   */
  public Item(String name, String category, String description, double cost, String id, 
      int dayOfCreation) {
    this.name = name;
    this.category = category;
    this.description = description;
    this.cost = cost;
    this.owner = null;
    this.dayOfCreation = dayOfCreation;
    this.contracts = new ArrayList<>();

    RandomId randomIdGen = new RandomId();
    this.id = randomIdGen.genId();
  }

  /**
   * This is a copy of the constructor.
   */
  public Item(Item other) {
    this.name = other.name;
    this.category = other.category;
    this.description = other.description;
    this.cost = other.cost;
    this.owner = null;
  }

  public void setOwner(Member newOwner) {
    this.owner = newOwner;
    newOwner.addCredits(100); // Add 100 credits to the new owner
  }

  /**
   * updateItem method.
   */
  public void updateItem(String name, String category, String description, double cost) {
    this.name = name;
    this.category = category;
    this.description = description;
    this.cost = cost;
  }

  public void addContract(Contract contract) {
    contracts.add(contract);
  }

  public void removeContract(Contract contract) {
    contracts.remove(contract);
  }

  public boolean available() {
    return owner == null;
  }

  /**
   * isAvailableforperiod method.
   */
  public boolean isAvailableForPeriod(int startDate, int endDate) {
    for (Contract contract : contracts) {
      boolean startsDuringAnotherContract = startDate <= contract.getEndDate() 
          && startDate >= contract.getStartDate();
      boolean endsDuringAnotherContract = endDate >= contract.getStartDate() 
          && endDate <= contract.getEndDate();
      boolean envelopsAnotherContract = startDate < contract.getStartDate() 
          && endDate > contract.getEndDate();
      boolean isWithinAnotherContract = startDate > contract.getStartDate() 
          && endDate < contract.getEndDate();

      if (startsDuringAnotherContract || endsDuringAnotherContract 
          || envelopsAnotherContract || isWithinAnotherContract) {
        return false; // The item is already lent out in this period
      }
    }
    return true; // The item is available for this period
  }


  public String getId() {
    return id;
  }

  public Member getOwner() {
    return owner;
  }

  public String getCategory() {
    return category;
  }


  public String getName() {
    return name;
  }


  public String getDescription() {
    return description;
  }


  public double getCost() {
    return cost;
  }

  public int getDayOfCreation() {
    return dayOfCreation;
  }

  public List<Contract> getContracts() {
    return new ArrayList<>(contracts);
  }
}
