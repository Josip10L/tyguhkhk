package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * MemberManager class.
 */
public class MemberManager {
  private Time time;
    
  private List<Member> members = new ArrayList<>();
  private List<Item> items = new ArrayList<>();
  private List<Contract> contracts = new ArrayList<>();

  private Set<String> activeEmails = new HashSet<>();
  private Set<Integer> activeNumbers = new HashSet<>();
  
  public void setTime(Time time) {
    this.time = time;
  }

  /**
 * createMember method.
 */
  public Member createMember(String name, String email, int number) {
    // First, check if email and number are unique
    if (activeEmails.contains(email) || activeNumbers.contains(number)) {
      throw new IllegalArgumentException("Email or number is not unique, enter new ones");
    }

    // Generate a unique ID
    String memberId;
    RandomId randomIdGen = new RandomId();
    do {
      memberId = randomIdGen.genId();
    } while (isMemberIdExists(memberId));

    // Create new member with unique ID
    Member newMember = new Member(name, email, number, memberId);
    addMember(newMember);
    return newMember;
  }

  // Helper method to check if a member ID already exists
  private boolean isMemberIdExists(String memberId) {
    for (Member member : members) {
      if (member.getId().equals(memberId)) {
        return true;
      }
    }
    return false;
  }


  /**
   * updateMemberDetails method.
   */
  public void updateMemberDetails(String memberId, String newName, String newEmail, int newNumber) {
    Member member = findMemberById(memberId);
    if (member != null) {
      member.setName(newName);
      member.setEmail(newEmail);
      member.setNumber(newNumber);
    } else {
      throw new IllegalArgumentException("Member not found with ID: " + memberId);
    }
  }

  /**
   * createNewItem ethod.
   */
  public Item createNewItem(String name, String category, String description, double cost, 
      int currentDay) {
    Item newItem = new Item(name, category, description, cost, "", currentDay);
    addItem(newItem);
    return newItem;
  }

  /**
   * updateItemDetails method.
   */
  public void updateItemDetails(String itemId, String newName, String newCategory, 
      String newDescription, double newCost) {
    Item item = findItemById(itemId);
    if (item != null) {
      item.updateItem(newName, newCategory, newDescription, newCost);
    }
  }

  /**
   * addMember method.
   */
  public void addMember(Member member) {
    members.add(member);
    activeEmails.add(member.getEmail());
    activeNumbers.add(member.getNumber()); 
  }

  /**
   * deleteMember method.
   */
  public void deleteMember(Member member) {
    members.remove(member);
    activeEmails.remove(member.getEmail());
    activeNumbers.remove(member.getNumber()); 
  }  


  /**
   * findMemberById method.
   */
  public Member findMemberById(String memberId) {
    for (Member member : members) {
      if (member.getId().equals(memberId)) {
        return member;
      }
    }
    return null;
  }

  public void addItem(Item item) {
    items.add(item);
  }


  /**
 * deleteItem method.
 */
  public void deleteItem(Item item) {
    List<Contract> contractsToRemove = new ArrayList<>();
    for (Contract contract : contracts) {
      if (contract.getItem().equals(item)) {
        contractsToRemove.add(contract);
        contract.cancelContract();
      }
    }
    contracts.removeAll(contractsToRemove);

    items.remove(item);

    for (Member member : members) {
      member.removeOwnedItem(item);
    }
  }

  /**
   * finditemById method.
   */
  public Item findItemById(String itemId) {
    for (Item item : items) {
      if (item.getId().equals(itemId)) {
        return item;
      }
    }
    return null;
  }

  /**
   * findMemberByName method.
   */
  public Member findMemberByName(String memberName) {
    for (Member member : members) {
      if (member.getName().equalsIgnoreCase(memberName)) {
        return member;
      }
    }
    return null;
  }

  /**
   * findItemByName.
   */
  public Item findItemByName(String itemName) {
    for (Item item : items) {
      if (item.getName().equalsIgnoreCase(itemName)) {
        return item;
      }
    }
    return null;
  }

  public List<Member> getAllMembers() {
    return new ArrayList<>(members);
  }

  public List<Item> getAllItems() {
    return new ArrayList<>(items);
  }

  public List<Member> getSimpleMember() {
    return new ArrayList<>(members);
  }

  public List<Member> getVerboseMember() {
    return new ArrayList<>(members);
  }

  public void addContract(Contract contract) {
    contracts.add(contract);
  }

  public void deleteContract(Contract contract) {
    contracts.remove(contract);
  }

  public List<Contract> getAllContracts() {
    return new ArrayList<>(contracts);
  }

  /**
   * initializeDefaultMembersAndItems method.
   */
  public void initializeDefaultMembersAndItems() {
    RandomId randomId = new RandomId();

    // Initialize members
    Member member1 = new Member("M1", "M1@gmail.com", 12345, randomId.genId());
    Member member2 = new Member("M2", "M2@gmail.com", 54321, randomId.genId());
    Member member3 = new Member("M3", "M3@gmail.com", 98765, randomId.genId());

    addMember(member1);
    addMember(member2);
    addMember(member3);

    // Initialize items
    Item item1 = new Item("audi", "bil", "snabb", 50.0, randomId.genId(), 0);
    Item item2 = new Item("fotboll", "sport", "rund", 10.0, randomId.genId(), 0);
    Item item3 = new Item("dator", "elektronik", "ny", 20.0, randomId.genId(), 0);

    addItem(item1);
    addItem(item2);
    addItem(item3);

    item1.setOwner(member1);
    item2.setOwner(member1);
    item3.setOwner(member2);

    member1.chooseItem(item1);
    member1.chooseItem(item2);
    member2.chooseItem(item3);

    //300 instead of 500 since for the two items added/created it adds 100 credits each extra.
    // Meaning that 300 + 200 from the two created items = 500
    member1.addCredits(300);
    // 0 instead of 100 since for the one item added/created it adds 100 credits.
    // Meaning that 0 + 100 from the one created item = 100
    member2.addCredits(0);
    member3.addCredits(100);

    Contract contract = new Contract(member1, member3, item2, 5, 7);
    this.contracts.add(contract);
    item2.addContract(contract);

    if (this.time != null) {
      this.time.addContract(contract);
    }
  }
}
