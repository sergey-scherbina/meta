package scherbina.sergey.meta;

import java.lang.reflect.Field;

public class MetaUtil {

	protected MetaUtil() {
	}

	public static class AssignException extends RuntimeException {

		private static final long serialVersionUID = -4545383615028187739L;
		private static final String message = "Cannot assign '%3$s' value to '%2$s' field in '%1$s' class";

		private final Object target;
		private final Field field;
		private final Object value;

		public AssignException(final Object target, final Field field,
				final Object value) {
			this.target = target;
			this.field = field;
			this.value = value;
		}

		public String getTargetClass() {
			return target == null ? null : target.getClass().getCanonicalName();
		}

		public String getFieldName() {
			return field == null ? null : field.getName();
		}

		public String getValueClass() {
			return value == null ? null : value.getClass().getCanonicalName();
		}

		@Override
		public String getMessage() {
			return String.format(message, getTargetClass(), getFieldName(),
					getValueClass());
		}

	}

	public static boolean assignNotNull(final Object target, final Field field,
			final Object value) throws AssignException {
		if (value != null) {
			return assign(target, field, value);
		} else {
			throw new AssignException(target, field, value);
		}
	}

	public static boolean assign(final Object target, final Field field,
			final Object value) throws AssignException {
		final boolean accessible = field.isAccessible();
		if (!accessible) {
			field.setAccessible(true);
		}
		try {
			field.set(target, value);
			return true;
		} catch (IllegalArgumentException e) {
			throw new AssignException(target, field, value);
		} catch (final IllegalAccessException e) {
			return false;
		} finally {
			if (!accessible) {
				field.setAccessible(false);
			}
		}
	}

}
