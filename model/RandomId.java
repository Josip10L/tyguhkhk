package model;

import java.util.Random;

/**
 * RandomId class.
 */
public class RandomId {

  private final Random rd = new Random();
    
  /**
   * genId.
   */
  public String genId() {
    String chars = "ABCDEFGHIJKLMNOPQRSTVWXYZ0123456789";
    StringBuilder id = new StringBuilder();

    for (int x = 0; x < 6; x++) {
      int i = rd.nextInt(chars.length());
      char rndChar = chars.charAt(i);
      id.append(rndChar);
    }
    return id.toString();
  }
}
