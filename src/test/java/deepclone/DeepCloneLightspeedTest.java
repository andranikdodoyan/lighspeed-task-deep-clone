package deepclone;

import static deepclone.DeepClone.deepClone;
import static utils.Generator.generateAccount;
import static utils.Generator.generateMan;

import java.time.Instant;
import java.util.List;
import model.Account;
import model.Man;
import org.junit.Assert;
import org.junit.Test;
import utils.Generator;

public class DeepCloneLightspeedTest {

  @Test
  public void deepClone_allArgsConstructor_primitive_string_listOfString() {
    Man man1 = generateMan();
    Man man2 = deepClone(man1);

    assertionsForClonedMan(man1, man2);
  }

  @Test
  public void deepClone_noConstructor_objects_nullList() {
    Account account = Generator.generateAccount(generateMan());
    Account account2 = deepClone(account);

    assertionsForClonedAccount(account, account2);
    Assert.assertEquals(account.getFriends(), account2.getFriends());
  }

  /**
   * The Idea of this test case is to test how the algorithm
   * behaves whenever it needs to repeat copying of an object which has been already copied.
   */
  @Test
  public void deepClone_noConstructor_objectList() {
    Man man1 = generateMan();
    Man man2 = generateMan("Dave", 28, List.of("The art of War"));
    Man man3 = generateMan("George", 21, List.of());

    Account account1 = generateAccount(man1);
    Account account2 = generateAccount(man2, Instant.now().minusSeconds(30000));
    Account account3 = generateAccount(man3, Instant.now().minusSeconds(24000));

    account1.setFriends(List.of(account2, account3));
    account2.setFriends(List.of(account1));
    account3.setFriends(List.of(account1));

    Account clonedAccount1 = deepClone(account1);
    Account clonedAccount2 = clonedAccount1.getFriends().getFirst();
    Account clonedAccount3 = clonedAccount1.getFriends().getLast();

    assertionsForClonedAccount(account1, clonedAccount1);
    assertionsForClonedAccount(account2, clonedAccount2);
    assertionsForClonedAccount(account3, clonedAccount3);

    Account account1FromClonedAccount2FriendList = clonedAccount2.getFriends().getFirst();
    assertionsForClonedAccount(account1, account1FromClonedAccount2FriendList);


  }

  private static void assertionsForClonedMan(Man original, Man cloned) {
    //Logical Equivalence
    Assert.assertEquals(original.getName(), cloned.getName());
    Assert.assertEquals(original.getFavoriteBooks(), cloned.getFavoriteBooks());
    Assert.assertEquals(original.getAge(), cloned.getAge());

    //References
    Assert.assertNotSame(original, cloned);
    Assert.assertNotSame(original.getFavoriteBooks(), cloned.getFavoriteBooks());
  }

  private static void assertionsForClonedAccount(Account account, Account account2) {
    Assert.assertNotSame(account, account2);

    assertionsForClonedMan(account.getMan(), account2.getMan());

    Assert.assertEquals(account.getDateOfRegistration(), account2.getDateOfRegistration());
    //This is because the Instant has no default Constructor and the declared one is private
    Assert.assertSame(account.getDateOfRegistration(), account2.getDateOfRegistration());
  }
}
