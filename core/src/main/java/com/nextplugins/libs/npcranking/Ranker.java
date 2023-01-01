package com.nextplugins.libs.npcranking;

import com.github.juliarn.npc.NPC;
import com.github.juliarn.npc.NPCPool;
import com.nextplugins.libs.hologramwrapper.Holograms;
import com.nextplugins.libs.npcranking.utils.NpcProfile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import java.util.*;

/**
 * The class with the responsibility to handle the NPC ranking
 *
 * @param <T> the item to use on ranking
 */
public class Ranker<T> {

    private final RankFormat<T> format;
    private final RankLocations locations;
    private final Holograms holograms;
    private final NPCPool npcPool;

    /**
     * @param format the rank format
     * @param npcPool the npc pool
     * @param plugin the plugin
     */
    public Ranker(RankFormat<T> format, NPCPool npcPool, Plugin plugin) {
        this.format = format;
        this.locations = new RankLocations(plugin);
        this.holograms = Holograms.get(plugin);
        this.npcPool = npcPool;

        Bukkit.getPluginManager().registerEvents(new RankListener(), plugin);
    }

    /**
     * Creates a Ranker instance using default NPCPool to plugin
     *
     * @param format the rank format
     * @param plugin the plugin
     */
    public Ranker(RankFormat<T> format, Plugin plugin) {
        this(format, NPCPool.createDefault(plugin), plugin);
    }

    public RankFormat<T> getFormat() {
        return format;
    }

    public RankLocations getLocations() {
        return locations;
    }

    public Holograms getHolograms() {
        return holograms;
    }

    public NPCPool getNpcPool() {
        return npcPool;
    }

    /**
     * Updates the NPCs, clearing it all and then respawning everything
     *
     * @param content the item list to use
     */
    public void update(List<T> content) {
        if (holograms == null) return;

        clear();

        for (int index = 0; index < content.size(); index++) {
            final T item = content.get(index);

            final int position = index + 1;

            final Location location = locations.getLocation(position);
            if (location == null) return;

            NPC.builder()
               .location(location)
               .imitatePlayer(false)
               .profile(NpcProfile.generateWithSkin(format.getSkinFrom(item)))
               .build(npcPool);

            final List<String> hologramLines = format.replaceWith(item, position);
            final Location hologramBaseLocation = location.clone().add(0, 2 + (hologramLines.size() * 0.3), 0);

            holograms.create(hologramBaseLocation, hologramLines);
        }
    }

    /**
     * Clear holograms and NPCs used in the process
     */
    public void clear() {
        if (holograms != null) holograms.clear();

        for (NPC npc : npcPool.getNPCs()) {
            npcPool.removeNPC(npc.getEntityId());
        }
    }

}
