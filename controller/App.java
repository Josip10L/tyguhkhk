package controller;

import controller.Controller;
import model.MemberManager;
import model.Time;
import view.View;

/**
 * Responsible for staring the application.
 */
public class App {
  /**
   * Application starting point.

   * @param args command line arguments.
   */
  public static void main(String[] args) {
    MemberManager memberManager = new MemberManager();
    Time time = new Time();

    memberManager.setTime(time); 

    View view = new View();
    Controller controller = new Controller(memberManager, time, view);
    controller.run();
  }
}
