package stancempire.lavender.api.event;

import net.minecraft.server.level.ServerPlayer;

public class PlayerLeaveWorldEvent extends Event
{
    private ServerPlayer serverPlayer;

    public PlayerLeaveWorldEvent(ServerPlayer serverPlayer)
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
