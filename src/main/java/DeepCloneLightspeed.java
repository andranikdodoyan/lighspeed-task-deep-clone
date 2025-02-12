import deepclone.DeepClone;
import java.util.List;
import model.Man;


public class DeepCloneLightspeed {

  public static void main(String[] args) {
    List<String> books = List.of("War and Peace", "Pride & Prejudice");

    Man man1 = new Man("John", 22, books);
    Man man2 = DeepClone.deepClone(man1);

    System.out.println("The two variables reference the same object: " + (man1 == man2));
    System.out.println("Name of the original object: " + man1.getName());
    System.out.println("Name of the cloned object: " + man2.getName());
    System.out.println("Age of the original object: " + man1.getAge());
    System.out.println("Age of the cloned object: " + man2.getAge());
    System.out.println("Favourite books of the original object: " + man1.getFavoriteBooks());
    System.out.println("Favourite books of the cloned object: " + man2.getFavoriteBooks());
    System.out.println("Does the list of original object's favorite books reference the "
        + "list of the cloned object's favourite books?: "
        + (man1.getFavoriteBooks() == man2.getFavoriteBooks()));

  }

}
