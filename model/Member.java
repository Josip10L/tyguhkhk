package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Member class.
 */
public class Member {

  private String name;
  private String email;
  private int number;
  private String id;
  private double credits;

  private List<Item> items = new ArrayList<>();

  /**
   * Member method.
   */
  public Member(String name, String email, int number, String id) {
    this.name = name;

    RandomId randomIdGen = new RandomId();
    this.id = randomIdGen.genId();

    this.email = email;
    this.number = number;
  }

  /**
   * This is a copy of the constructor.
   */
  public Member(Member other) {
    this.name = other.name;
    this.email = other.email;
    this.number = other.number;
    this.id = other.id;
  }

  public void removeItem(Item item) {
    items.remove(item);
  }

  public void removeOwnedItem(Item item) {
    items.remove(item);
  }

  /**
   * chooseItem method.
   */
  public void chooseItem(Item item) {
    items.add(item);
  }

  public boolean addCredits(double amount) {
    credits += amount;
    return true;
  }

  /**
   * removeCredits method.
   */
  public boolean removeCredits(double amount) {
    if (credits >= amount) {
      credits -= amount;
      return true;
    }
    return false;
  }

  public void transferItem(Item item, Member newOwner) {
    this.items.remove(item);
    newOwner.receiveItem(item);
  }

  // New method for a member to receive an item
  public void receiveItem(Item item) {
    this.items.add(item);
  }

  // New method to return the item after lending period
  public void returnItem(Item item, Member originalOwner) {
    this.items.remove(item);
    originalOwner.receiveItem(item);
  }


  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public int getNumber() {
    return number;
  }

  public String getId() {
    return id;
  }

  public double getCredits() {
    return credits;
  }

  public List<Item> getOwnedItems() {
    return new ArrayList<>(items);
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setNumber(int number) {
    this.number = number;
  }
}
