package utils.logging;

import java.util.Objects;
import java.util.function.Supplier;

public abstract class TopicLoggerAppender implements Supplier<String> {

	private String topic = null;
	private String formatted = null;

	public TopicLoggerAppender(String topic) {
		super();
		Objects.requireNonNull(topic);
		this.topic = topic;
	}

	public Supplier<String> message(String message) {
		formatted = String.format("[%20s] - [%s] - ", topic, message);
		return this;
	}

	@Override
	public String get() {
		Objects.requireNonNull(formatted);
		return formatted;
	}

}
