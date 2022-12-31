package com.nextplugins.libs.npcranking;

import com.github.juliarn.npc.event.PlayerNPCShowEvent;
import com.github.juliarn.npc.modifier.MetadataModifier;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

final class RankListener implements Listener {

    @EventHandler
    private void update(PlayerNPCShowEvent event) {
        event.send(
                event
                .getNPC()
                .metadata()
                .queue(MetadataModifier.EntityMetadata.SKIN_LAYERS, true)
        );
    }

}
