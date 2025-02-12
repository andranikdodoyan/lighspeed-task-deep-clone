package utils;

import java.time.Instant;
import java.util.List;
import model.Account;
import model.Man;

public class Generator {
  private Generator() {

  }

  public static Man generateMan() {
    List<String> books = List.of("War and Peace", "Pride & Prejudice");
    return new Man("John", 22, books);
  }

  public static Man generateMan(String name, int age, List<String> books) {
    return new Man(name, age, books);
  }

  public static Account generateAccount(Man man) {
    Account account = new Account();

    account.setMan(man);
    account.setDateOfRegistration(Instant.now());
    account.setFriends(null);

    return account;
  }

  public static Account generateAccount(Man man, Instant date) {
    Account account = generateAccount(man);
    account.setDateOfRegistration(date);
    return account;
  }
}
