package stancempire.lavender.api.common;

import net.minecraft.server.level.ServerPlayer;
import stancempire.lavender.api.event.events.common.PlayerJoinWorldEvent;
import stancempire.lavender.api.event.events.common.RemovePlayerFromWorldEvent;

public class CommonGameHooks
{
    public static void onRemovePlayerFromWorld(ServerPlayer serverPlayer)
    {
        RemovePlayerFromWorldEvent event = new RemovePlayerFromWorldEvent(serverPlayer);
        LavenderCommon.COMMON_EVENT_MANAGER.postEvent(event);
    }
    public static void onPlayerJoinWorld(ServerPlayer serverPlayer)
    {
        PlayerJoinWorldEvent event = new PlayerJoinWorldEvent(serverPlayer);
        LavenderCommon.COMMON_EVENT_MANAGER.postEvent(event);
    }
}
