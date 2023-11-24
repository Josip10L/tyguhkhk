package controller;

import java.util.List;
import model.Contract;
import model.Item;
import model.Member;
import model.MemberManager;
import model.Time;
import view.View;

/**
 * Controller class.
 */
public class Controller {
  private MemberManager memberManager;
  private View view;  
  private Time time;
  
  /**
   * Controller class.
   */
  public Controller(MemberManager memberManager, Time time, View view) {
    this.memberManager = memberManager;
    this.time = time;
    this.view = view;
  }

  /**
   * run method.
   */
  public void run() {
    memberManager.initializeDefaultMembersAndItems();

    int choice;
    do {
      view.menu();
      choice = view.getIntInput();

      switch (choice) {
        case 1: 
          handleMemberMenu();
          break;
        case 2: 
          handleItemMenu(); 
          break;
        case 3: 
          time.advanceDay();
          view.message("Advanced day. Current day: " + time.getCurrentDay());
          break;
        case 4:
          createContract();
          break;
        case 5:
          view.message("Exiting the program");
          break;
        default:
          view.message("Invalid choice. Please try again");
      }
    } while (choice != 5);

    view.closeScan();
  }


  private void handleMemberMenu() {
    int memberChoice;
    String memberId = "";
    Member member = null;
  
    do {
      view.memberMenu();
      memberChoice = view.getIntInput();


      switch (memberChoice) {
        case 1: // Add a member
          view.message("Enter member details:"); 
          view.message("Name: ");
          String name = view.getStringInput();
          view.message("Email: ");
          String email = view.getStringInput();
          view.message("Number: ");
          int number = view.getIntInput();
  
          try {
            Member newMember = memberManager.createMember(name, email, number);
            String newMemberId = newMember.getId();
            view.message("Member created with ID: " + newMemberId);
          } catch (IllegalArgumentException e) {
            view.message("Error: " + e.getMessage());
          }
          
          break;
        case 2: // Delete a member
          view.message("\nEnter member ID to delete: ");
          memberId = view.getStringInput();
          member = memberManager.findMemberById(memberId);
          if (member != null) {
            memberManager.deleteMember(member);
            view.message("Member deleted successfully");
          } else {
            view.message("Member not found");
          }

          break;
        case 3: // Change member's information
          view.message("Enter member ID to change information: ");
          memberId = view.getStringInput();
          member = memberManager.findMemberById(memberId);
          if (member != null) {
            view.message("Enter name: ");
            String newName = view.getStringInput();
            view.message("Enter email: ");
            String newEmail = view.getStringInput();
            view.message("Enter number: ");
            int newNumber = view.getIntInput();  

            memberManager.updateMemberDetails(memberId, newName, newEmail, newNumber);

            view.message("Member information updated successfully");
          } else {
            view.message("Member not found");
          }

          break;
        case 4: // View member's full information
          view.message("Enter member ID to view full information: ");
          memberId = view.getStringInput();
          member = memberManager.findMemberById(memberId);
          if (member != null) {
            view.memberInfo(member);
          } else {
            view.message("Member not found");
          }

          break;
        case 5: // List all members (simple)
          List<Member> members = memberManager.getAllMembers();
          view.simpleMember(members);
          break;
        case 6: // List all members (verbose)
          List<Member> verboseMembers = memberManager.getAllMembers();
          view.verboseMember(verboseMembers);
          break;
        case 7: // Back to main menu
          break;
        default:
          view.message("Invalid choice. Please try again");
      }
    } while (memberChoice != 7);
  }

  private void handleItemMenu() {
    String itemName;

    int itemChoice;
    do {
      view.itemMenu();
      itemChoice = view.getIntInput();


      switch (itemChoice) {
        case 1: // Create an item
          view.message("Enter item details:");
          view.message("Name: ");
          String name = view.getStringInput();
          view.message("Category: ");
          String category = view.getStringInput();
          view.message("Description: ");
          String description = view.getStringInput();
          view.message("Cost: ");
          double cost = view.getDoubleInput();

          int currentDay = time.getCurrentDay();

          Item newItem = memberManager.createNewItem(name, category, description, cost, currentDay);

          Member owner = chooseOwnerForMember(newItem);
          if (owner != null) {
            newItem.setOwner(owner);
            owner.chooseItem(newItem);
            memberManager.addItem(newItem);
            view.message("Item created with ID: " + newItem.getId());
          } else {
            view.message("Item creation failed. Owner not found.");
          }
          break;      

        case 2: // Delete an item
          view.message("\nEnter item name to delete: ");
          itemName = view.getStringInput();
          Item item = memberManager.findItemByName(itemName);
          if (item != null) {
            memberManager.deleteItem(item);
            view.message("Item deleted successfully");
          } else {
            view.message("Item not found");
          }
          break;
        case 3: // Change item's information
          view.message("Enter item ID to change information: ");
          String itemId = view.getStringInput();
          item = memberManager.findItemById(itemId);
          if (item != null) {
            view.message("Enter name: ");
            final String newName = view.getStringInput();
            view.message("Enter category: ");
            String newCategory = view.getStringInput();
            view.message("Enter description: ");
            String newDescription = view.getStringInput();
            view.message("Enter cost: ");
            double newCost = view.getDoubleInput();

            memberManager.updateItemDetails(itemId, newName, newCategory, newDescription, newCost);

            view.message("Item information updated successfully");
          } else {
            view.message("Item not found");
          }
          break;
        case 4: // View item's information
          view.message("Enter item name to view information: ");
          itemName = view.getStringInput();
          item = memberManager.findItemByName(itemName);
          if (item != null) {
            view.itemInfo(item);
          } else {
            view.message("Item not found");
          }
          break;
        case 5: // Back to main menu
          break;
        default:
          view.message("Invalid choice. Please try again");
      }
    } while (itemChoice != 5);
  }

  /**
 * createContract method.
 */
  public void createContract() {
    view.msg1();
    String borrowerName = view.getStringInput();
    Member borrower = memberManager.findMemberByName(borrowerName);

    view.msg2();
    String itemName = view.getStringInput();
    Item item = memberManager.findItemByName(itemName);

    if (borrower == null || item == null) {
      view.msg4();
      return;
    }

    Member lender = item.getOwner();

    if (lender == null) {
      view.msg3();
      return;
    }

    view.msg5();
    int startDate = view.getIntInput();
    view.msg6();
    int endDate = view.getIntInput();
  

    if (!item.isAvailableForPeriod(startDate, endDate)) {
      view.msg8();
      return;
    }

    Contract contract = new Contract(lender, borrower, item, startDate, endDate);

    item.addContract(contract);
    time.addContract(contract);

    List<Contract> existingContracts = memberManager.getAllContracts();

    if (!contract.getBorrowerEnoughCredits()) {
      view.msg9();
      return;
    }

    if (!contract.getItemAvailable(existingContracts, time)) {
      view.msg10();
      return;
    }

    if (contract.makeContract(existingContracts, time)) {
      view.msg11(contract.getId());
      view.contractInfo(contract);
    } else {
      view.msg12();
    }
  }

  /**
   * chooseOwnerForMember method.
   */
  public Member chooseOwnerForMember(Item item) {
    List<Member> members = memberManager.getAllMembers();
    if (members.isEmpty()) {
      view.msg13();
      return null;
    }

    view.msg14();
    for (Member member : members) {
      view.message("ID: " + member.getId() + ", Name: " + member.getName());
    }

    view.msg15();
    String memberId = view.getStringInput();
    Member owner = memberManager.findMemberById(memberId);

    if (owner != null) {
      return owner;
    } else {
      view.msg16();
      return null;
    }
  } 
}
