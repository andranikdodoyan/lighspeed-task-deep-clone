package deepclone;

import java.lang.reflect.Constructor;

public class DeepCloneConstructorUtils {
  private DeepCloneConstructorUtils() {}

  @SuppressWarnings("unchecked")
  protected static <T> T initializeTarget(Class<?> classOfSource) {
    Constructor<?> minArgsConstructor = getMinArgsConstructor(classOfSource);

    if (minArgsConstructor == null) {
      return null;
    }
    Class<?>[] minArgsConstructorParameters = minArgsConstructor.getParameterTypes();

    Object[] defaultValues = new Object[minArgsConstructorParameters.length];
    for (int i = 0; i < minArgsConstructor.getParameterTypes().length; i++) {
      defaultValues[i] = getDefaultValue(minArgsConstructorParameters[i]);
    }

    try {
      return (T) minArgsConstructor.newInstance(defaultValues);
    } catch (Exception e) {
      return null;
    }
  }

  private static Constructor<?> getMinArgsConstructor(Class<?> classOfSource) {
    Constructor<?>[] constructors = classOfSource.getConstructors();

    if (constructors.length == 0) {
      return null;
    }

    Constructor<?> minArgsConstructor = constructors[0];

    for (Constructor<?> constructor : constructors) {
      if (constructor.getParameterTypes().length < minArgsConstructor.getParameterTypes().length) {
        minArgsConstructor = constructor;
      }
    }

    return minArgsConstructor;
  }

  private static Object getDefaultValue(Class<?> type) {
    if (type == boolean.class) {
      return false;
    }
    if (type == byte.class) {
      return (byte) 0;
    }
    if (type == short.class) {
      return (short) 0;
    }
    if (type == int.class) {
      return 0;
    }
    if (type == long.class) {
      return 0L;
    }
    if (type == float.class) {
      return 0.0f;
    }
    if (type == double.class) {
      return 0.0d;
    }
    if (type == char.class) {
      return '\u0000';
    }
    return null;
  }
}
