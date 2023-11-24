package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Time class.
 */
public class Time {

  private int currentDay;
  private List<Contract> contracts;

  public Time() {
    this.currentDay = 0;
    this.contracts = new ArrayList<>();
  }

  public void addContract(Contract contract) {
    contracts.add(contract);
  }

  /**
   * advanceDay method.
   */
  public void advanceDay() {
    currentDay++;
  
    for (Contract contract : contracts) {
      if (contract.getStartDate() == currentDay) {
        contract.activateContract();
      }
    }

    List<Contract> contractsToEnd = new ArrayList<>();

    for (Contract contract : contracts) {
      if (contract.isContractEnded(currentDay)) {
        contractsToEnd.add(contract);
      }
    }

    for (Contract contract : contractsToEnd) {
      contract.endContract();
      contracts.remove(contract);
    }
  }
  

  public int getCurrentDay() {
    return currentDay;
  }
}
