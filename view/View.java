package view;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import model.Contract;
import model.Item;
import model.Member;

/**
 * View class.
 */
public class View {

  private Scanner scanner;

  public View() {
    this.scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
  }

  // Method to get integer input from user
  public int getIntInput() {
    return scanner.nextInt();
  }

  /**
   * getStringInput method.
   */
  public String getStringInput() {
    // Check if the next token is available
    while (!scanner.hasNext()) {
        // Wait for the input
    }
    String input = scanner.nextLine();
    
    // Check for accidental empty input (like pressing Enter too soon)
    if (input.isEmpty() && scanner.hasNextLine()) {
      input = scanner.nextLine();
    }
    return input;
  }



  public double getDoubleInput() {
    return scanner.nextDouble();
  }

  /**
   * closeScan method.
   */
  public void closeScan() {
    if (scanner != null) {
      scanner.close();
    }
  }

  /**
    * menu method.
    */
  public void menu() {
    System.out.println("Main Menu:");
    System.out.println("1. Member");
    System.out.println("2. Item");
    System.out.println("3. Advance day");
    System.out.println("4. Create contract");
    System.out.println("5. Exit");
    System.out.print("Enter a number (1-5): " + "\n");
  }

  /**
   * memberMenu class.
   */
  public void memberMenu() {
    System.out.println("Member menu:");
    System.out.println("1. Create a member");
    System.out.println("2. Delete a member");
    System.out.println("3. Change members information");
    System.out.println("4. View members full information");
    System.out.println("5. List all members (simple)");
    System.out.println("6. List all members (verbose)");
    System.out.println("7. Back to main menu");
    System.out.print("Enter a number (1-7): " + "\n");
  }

  /**
   * itemMenu class.
   */
  public void itemMenu() {
    System.out.println("Item menu:");
    System.out.println("1. Create an item");
    System.out.println("2. Delete an item");
    System.out.println("3. Change items information");
    System.out.println("4. View items information");
    System.out.println("5. Back to main menu");
    System.out.print("Enter a number (1-5): ");
  }

  /**
   * contractInfo method.
   */
  public void contractInfo(Contract contract) {
    System.out.println("Contract Information:");
    System.out.println("Contract ID: " + contract.getId());
    System.out.println("Lender: " + contract.getLender().getName());
    System.out.println("Borrower: " + contract.getBorrower().getName());
    System.out.println("Item: " + contract.getItem().getName());
    System.out.println("Start Date: " + contract.getStartDate());
    System.out.println("End Date: " + contract.getEndDate());
    System.out.println("Credit Transfer: " + contract.getCreditTran());
  }

  /**
   * memberInfo method.
   */
  public void memberInfo(Member member) {
    System.out.println("\nMember Information:");
    System.out.println("Name: " + member.getName());
    System.out.println("Email: " + member.getEmail());
    System.out.println("Number: " + member.getNumber());
    System.out.println("ID: " + member.getId());
    System.out.println("Credits: " + member.getCredits()); 
  }

  /**
   * simpleMember method.
   */
  public void simpleMember(List<Member> members) {
    System.out.println("\nList of Members (Simple):");
    for (Member member : members) {
      System.out.println("Name: " + member.getName()); 
      System.out.println("Email: " + member.getEmail()); 
      System.out.println("Current Credits: " + member.getCredits()); 
      System.out.println("Number of Owned Items: " + member.getOwnedItems().size());
    }
  }

  /**
   * verboseMember method.
   */
  public void verboseMember(List<Member> members) {
    System.out.println("\nList of Members (Verbose):");
    for (Member member : members) {
      StringBuilder memberInfo = new StringBuilder("Name: " + member.getName() 
          + ", Email: " + member.getEmail() 
          + "\nOwned Items:\n");

      for (Item item : member.getOwnedItems()) {
        memberInfo.append("   - ").append(item.getName());
        if (item.getOwner() != null) {
          memberInfo.append(" (Owned by: ").append(item.getOwner().getName()).append(")");
        }
        memberInfo.append("\n");
      }
      System.out.println(memberInfo.toString());
    }
  }

  /**
   * itemInfo.
   */
  public void itemInfo(Item item) {
    System.out.println("\nItem Information:");
    System.out.println("Name: " + item.getName());
    System.out.println("Category: " + item.getCategory());
    System.out.println("Description: " + item.getDescription());
    System.out.println("Cost: " + item.getCost());
    System.out.println("Owner: " + item.getOwner().getName());
    System.out.println("Creation day: " + item.getDayOfCreation());

    List<Contract> contracts = item.getContracts();
    if (contracts.isEmpty()) {
      System.out.println("No contracts for this item.");
    } else {
      System.out.println("Contracts for this item:");
      for (Contract contract : contracts) {
        System.out.println("Contract ID: " + contract.getId() + ", Borrower: " 
            + contract.getBorrower().getName() + ", Day: " + contract.getStartDate() 
            + " to " + contract.getEndDate());
      }
    }
  }

  public void message(String message) {
    System.out.println(message);
  }

  public void msg1() {
    System.out.println("Enter borrower's name: ");
  }

  public void msg2() {
    System.out.println("Enter Item name: ");
  }

  public void msg3() {
    System.out.println("Item does not have a valid owner. Contract creation failed.");
  }

  public void msg4() {
    System.out.println("Invalid member or item name. Contract creation failed");
  }

  public void msg5() {
    System.out.println("Enter start date: ");
  }

  public void msg6() {
    System.out.println("Enter end date: ");
  }

  public void msg7() {
    System.out.println("End date must be after start date. Contract creation failed.");
  }

  public void msg8() {
    System.out.println("Item is not available for the requested period.");
  }

  public void msg9() {
    System.out.println("Contract creation failed: insufficient borrower credits.");
  }

  public void msg10() {
    System.out.println("Contract creation failed: item not available.");
  }

  public void msg11(String message) {
    System.out.println("Contract created successfully with ID: " + message);
  }

  public void msg12() {
    System.out.println("Contract creation failed due to unknown error.");
  }

  public void msg13() {
    System.out.println("No members available.");
  }

  public void msg14() {
    System.out.println("Available Members:");
  }

  public void msg15() {
    System.out.println("Enter member ID to set as the owner: ");
  }

  public void msg16() {
    System.out.println("Member not found.");
  }
}
