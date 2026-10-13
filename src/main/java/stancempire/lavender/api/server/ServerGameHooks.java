package stancempire.lavender.api.server;

import net.minecraft.server.level.ServerPlayer;
import stancempire.lavender.api.event.RemovePlayerFromWorldEvent;

public class ServerGameHooks
{
    public static void onRemovePlayerFromWorld(ServerPlayer serverPlayer)
    {
        RemovePlayerFromWorldEvent event = new RemovePlayerFromWorldEvent(serverPlayer);
        LavenderServer.SERVER_EVENT_MANAGER.postEvent(event);
    }
}
