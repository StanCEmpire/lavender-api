package stancempire.lavender.api.event.events;

import net.minecraft.server.level.ServerPlayer;

public class RemovePlayerFromWorldEvent extends Event<RemovePlayerFromWorldEvent>
{
    private ServerPlayer serverPlayer;
    private static void foo(RemovePlayerFromWorldEvent event)
    {

    }

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
