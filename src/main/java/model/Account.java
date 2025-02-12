package model;

import java.time.Instant;
import java.util.List;

public class Account {
  private Man man;
  private List<Account> friends;
  private Instant dateOfRegistration;

  public void setDateOfRegistration(Instant dateOfRegistration) {
    this.dateOfRegistration = dateOfRegistration;
  }

  public void setFriends(List<Account> friends) {
    this.friends = friends;
  }

  public void setMan(Man man) {
    this.man = man;
  }

  public List<Account> getFriends() {
    return friends;
  }

  public Man getMan() {
    return man;
  }

  public Instant getDateOfRegistration() {
    return dateOfRegistration;
  }
}
