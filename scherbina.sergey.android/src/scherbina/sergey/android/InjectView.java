package scherbina.sergey.android;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import scherbina.sergey.meta.MetaInjector;
import scherbina.sergey.meta.MetaUtil.AssignException;

import android.app.Activity;

@Retention(RetentionPolicy.RUNTIME)
public @interface InjectView {

	int value();

	class Injector {
		private static final MetaInjector<InjectView> injector = MetaInjector
				.injector(InjectView.class);

		public static void inject(final Activity activity)
				throws AssignException {
			injector.inject(activity, new MetaInjector.Injection<InjectView>() {
				public Object getInjection(final InjectView annotation) {
					return activity.findViewById(annotation.value());
				}
			});
		}

	}

}
