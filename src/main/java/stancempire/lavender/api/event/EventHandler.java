package stancempire.lavender.api.event;

import java.util.function.Consumer;

public class EventHandler
{
    private final Consumer<Event> eventConsumer;

    public EventHandler(Consumer<Event> eventConsumer)
    {
        this.eventConsumer = eventConsumer;
    }

    public Event handle(Event event)
    {
        this.eventConsumer.accept(event);
        return event;
    }

}
