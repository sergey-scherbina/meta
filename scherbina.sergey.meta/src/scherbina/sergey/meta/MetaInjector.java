package scherbina.sergey.meta;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import scherbina.sergey.meta.MetaUtil.AssignException;

public class MetaInjector<A extends Annotation> {

	public static <A extends Annotation> MetaInjector<A> injector(
			final Class<A> annotation) {
		return new MetaInjector<A>(annotation);
	}

	public interface Injection<A extends Annotation> {
		Object getInjection(A annotation);
	}

	private final Class<A> annotation;

	public MetaInjector(final Class<A> annotation) {
		this.annotation = annotation;
	}

	public void inject(final Object target, final Injection<A> injection)
			throws AssignException {
		for (final Field f : target.getClass().getDeclaredFields()) {
			if (f.isAnnotationPresent(annotation)) {
				final Object v = injection.getInjection(f
						.getAnnotation(annotation));
				MetaUtil.assignNotNull(target, f, v);
			}
		}
	}
}
