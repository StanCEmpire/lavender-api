package stancempire.lavender.api.event;

import net.minecraft.server.level.ServerPlayer;

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
