package deepclone;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class DeepCloneCollectionUtils {

  private DeepCloneCollectionUtils() {}

  @SuppressWarnings("unchecked")
  protected static <E> Collection<E> createCollectionInstance(Class<?> collectionType) {
    try {
      return (Collection<E>) collectionType.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      if (List.class.isAssignableFrom(collectionType)) {
        return new ArrayList<>();
      } else if (Set.class.isAssignableFrom(collectionType)) {
        return new HashSet<>();
      } else if (Queue.class.isAssignableFrom(collectionType)) {
        return new LinkedList<>();
      }
      throw new RuntimeException("Cannot instantiate collection type: " + collectionType.getSimpleName(), e);

    }
  }
}
