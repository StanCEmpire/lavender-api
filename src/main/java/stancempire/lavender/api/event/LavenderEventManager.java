package stancempire.lavender.api.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class LavenderEventManager
{

    // NOTE 2026-10-13: This should not be static since each event manager
    // instance should have its own list of handlers.
    private final HashMap<Class<?>, List<EventHandler>> eventHandlers;

    public LavenderEventManager()
    {
        eventHandlers = new HashMap<>();
    }

    // NOTE 2026-10-13: This cast here is okay to ignore since this
    // is the only public 'setter' for the event handler map, and
    // the generic signature here provides type safety that the
    // class and input parameter for the consumer are the same type
    @SuppressWarnings("unchecked")
    public <T extends Event> void addEventHandler(Class<T> eventClass, Consumer<T> eventHandler)
    {
        List<EventHandler> eventHandlerList = eventHandlers.computeIfAbsent(
            eventClass,
            _ -> new ArrayList<>()
        );
        eventHandlerList.add((new EventHandler((Consumer<Event>)eventHandler)));
    }

    private <T extends Event> EventHandler[] getEventHandlers(Class<T> eventClass)
    {
        List<EventHandler> eventHandlerList = eventHandlers.get(eventClass);
        if(eventHandlerList == null)
        {
            return new EventHandler[0];
        }
        EventHandler[] eventHandlerArray = new EventHandler[eventHandlerList.size()];
        return eventHandlerList.toArray(eventHandlerArray);
    }

    public <T extends Event> T postEvent(T event)
    {
        EventHandler[] eventHandlerList = getEventHandlers(event.getClass());

        for(EventHandler eventHandler : eventHandlerList)
        {
            if(event.isCancelled())
            {
                break;
            }
            eventHandler.handle(event);
        }

        return event;
    }

}
