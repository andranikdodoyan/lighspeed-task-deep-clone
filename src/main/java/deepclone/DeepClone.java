package deepclone;

import static deepclone.DeepCloneCollectionUtils.createCollectionInstance;
import static java.lang.reflect.Modifier.isFinal;
import static java.lang.reflect.Modifier.isStatic;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;


public class DeepClone {

  private static final HashMap<String, Object> cache = new HashMap<>();

  private DeepClone() {}

  @SuppressWarnings("unchecked")
  public static <T> T deepClone(T source){
    if (source == null || source instanceof String) {
      return source;
    }

    Class<?> classOfSource = source.getClass();
    Field[] declaredFields = getDeclaredFields(classOfSource);

    T clonedObject = validate(DeepCloneConstructorUtils.initializeTarget(classOfSource), source);

    if (cache.containsKey(source.toString())) {
      return (T) cache.get(source.toString());
    } else {
      cache.put(source.toString(), clonedObject);
    }

    try {
      for (Field field : declaredFields) {
        if (isStatic(field.getModifiers()) || isFinal(field.getModifiers())) {
          continue;
        }
        Class<?> fieldType = field.getType();

        field.trySetAccessible();

        if (fieldType.isPrimitive() || fieldType.isAssignableFrom(String.class)) {
          Object value = field.get(source);
          field.set(clonedObject, value);
        }
        else if (Collection.class.isAssignableFrom(fieldType)) {
          ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
          Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
          cloneCollectionFields(actualTypeArguments, field, fieldType, clonedObject, source);
        }
        else {
          Object value = field.get(source);
          field.set(clonedObject, deepClone(value));
        }
      }
    } catch (Exception e) {
      return null;
    }

    return clonedObject;
  }

  private static <T> void cloneCollectionFields(Type[] actualTypeArguments, Field field, Class<?> fieldType, T clonedObject, T source) throws IllegalAccessException {
    if (actualTypeArguments.length == 1) {
      Collection<Object> collection = createCollectionInstance(fieldType);
      Collection<?> values = (Collection<?>) field.get(source);
      if (values != null) {
        values.forEach(value -> collection.add(deepClone(value)));
        field.set(clonedObject, collection);
      } else {
        field.set(clonedObject, null);
      }
    }

    //Skipped the case when type arguments are not 1

  }

  private static <T> T validate(T target, T source) {
    if (target == null) {
      return source;
    }
    return target;
  }

  private static Field[] getDeclaredFields(Class<?> classOfSource) {
    if (classOfSource.getSuperclass() != Object.class) {
      return classOfSource.getFields();
    } else {
      return classOfSource.getDeclaredFields();
    }
  }
}
