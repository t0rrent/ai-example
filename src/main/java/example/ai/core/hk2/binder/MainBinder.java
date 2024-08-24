package example.ai.core.hk2.binder;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.glassfish.hk2.utilities.Binder;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class MainBinder extends AbstractBinder {
	
	private final Collection<Supplier<Binder>> binders;

	public MainBinder(final Collection<Supplier<Binder>> binders) {
		this.binders = binders;
	}

	@Override
	protected void configure() {
		final Map<Class<? extends Binder>, Binder> binderMap = new HashMap<>();
		
		this.binders.stream()
				.map(Supplier::get)
				.collect(Collectors.toSet())
				.forEach((binder) -> binderMap.put(binder.getClass(), binder));
		
		binderMap.values()
				.forEach(this::install);
	}

}
