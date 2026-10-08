package stancempire.lavender.api.event;

import net.minecraft.server.level.ServerPlayer;

public class PlayerJoinWorldEvent implements Event
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
    public boolean cancellable() {
        return false;
    }
}
