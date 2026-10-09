package stancempire.lavender.api.event;

public abstract class Event
{
    private boolean cancelled = false;
    public abstract boolean isCancellable();
    public void setCancelled()
    {
        if(isCancellable())
        {
            cancelled = true;
        }
    }
    public boolean isCancelled()
    {
        return cancelled;
    }
}
