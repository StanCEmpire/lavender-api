package stancempire.lavender.api.event.events.common;

import net.minecraft.server.level.ServerPlayer;
import stancempire.lavender.api.event.Event;

// TODO 2026-10-29: Find relevant spot to call this event
// There is some ambiguity RE when this should be called
// since the "join world" logic is not as concrete.
public class PlayerJoinWorldEvent extends Event
{
    private ServerPlayer serverPlayer;

    public PlayerJoinWorldEvent(ServerPlayer serverPlayer)
    {
        this.serverPlayer = serverPlayer;
    }

    public ServerPlayer getServerPlayer()
    {
        return this.serverPlayer;
    }

    @Override
    public boolean isCancellable()
    {
        return false;
    }
}
