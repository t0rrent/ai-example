package example.ai.core.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;

import org.glassfish.hk2.utilities.Binder;

public class Pipeline {

	private Collection<Supplier<Binder>> binders;
	
	private String id;

	public Collection<Supplier<Binder>> getBinders() {
		return binders;
	}

	public void setBinders(final Collection<Supplier<Binder>> binders) {
		this.binders = binders;
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	@SafeVarargs
	public static Pipeline of(final String id, final Supplier<Binder>... binders) {
		final Pipeline pipeline = new Pipeline();
		pipeline.setId(id);
		pipeline.setBinders(Arrays.asList(binders));
		return pipeline;
	}

}
