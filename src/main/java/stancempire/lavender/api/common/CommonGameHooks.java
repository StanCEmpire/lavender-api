package stancempire.lavender.api.common;

import net.minecraft.server.level.ServerPlayer;
import stancempire.lavender.api.event.events.common.PlaceNewPlayerEvent;
import stancempire.lavender.api.event.events.common.RemovePlayerFromWorldEvent;

public class CommonGameHooks
{
    public static void onRemovePlayerFromWorld(ServerPlayer serverPlayer)
    {
        RemovePlayerFromWorldEvent event = new RemovePlayerFromWorldEvent(serverPlayer);
        LavenderCommon.COMMON_EVENT_MANAGER.postEvent(event);
    }
    public static void onPlaceNewPlayer(ServerPlayer serverPlayer)
    {
        PlaceNewPlayerEvent event = new PlaceNewPlayerEvent(serverPlayer);
        LavenderCommon.COMMON_EVENT_MANAGER.postEvent(event);
    }
}
