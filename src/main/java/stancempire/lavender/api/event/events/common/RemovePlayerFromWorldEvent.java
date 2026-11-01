package stancempire.lavender.api.event.events.server;

import net.minecraft.server.level.ServerPlayer;
import stancempire.lavender.api.event.Event;

public class RemovePlayerFromWorldEvent extends Event
{
    private final ServerPlayer serverPlayer;

    public RemovePlayerFromWorldEvent(ServerPlayer serverPlayer)
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
