package com.nextplugins.libs.npcranking;

import com.github.juliarn.npc.NPC;
import com.github.juliarn.npc.NPCPool;
import com.github.juliarn.npc.event.PlayerNPCShowEvent;
import com.github.juliarn.npc.modifier.MetadataModifier;
import com.github.juliarn.npc.profile.Profile;
import com.nextplugins.libs.hologramwrapper.HologramController;
import com.nextplugins.libs.hologramwrapper.utils.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Ranking implements Listener {

    private final Plugin plugin;
    private final NPCPool pool;
    private final RankingPosition rankingPosition;
    private final HologramController hologramController;
    private final List<String> format;
    private final Random random;

    public Ranking(Plugin plugin, NPCPool pool, List<String> format) {
        this.plugin = plugin;
        this.pool = pool;
        this.rankingPosition = new RankingPosition(plugin);
        this.hologramController = Objects.requireNonNull(HologramController.get(plugin));
        this.format = format;
        this.random = new Random();

        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public Ranking(Plugin plugin, List<String> format) {
        this(plugin, NPCPool.createDefault(plugin), format);
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public NPCPool getPool() {
        return pool;
    }

    public RankingPosition getRankingPosition() {
        return rankingPosition;
    }

    public HologramController getHologramController() {
        return hologramController;
    }

    public <T> void update(List<T> content, Function<T, String> function, Function<T, List<String>> misc) {
        destroy();

        for (int index = 0; index < content.size(); index++) {
            final T item = content.get(index);
            final Location location = rankingPosition.getPositions().getOrDefault(index, null);

            final String name = function.apply(item);
            final int position = index + 1;

            final List<String> format =

            this.format.stream().map(
                    line -> line.replace("{position}", String.valueOf(position))
                                .replace("{name}", name)
            ).collect(Collectors.toList());

            format.addAll(misc.apply(item));

            if (location == null) continue;
            // if (!location.isWorldLoaded()) continue;

            final Profile profile = new Profile(name);
            profile.complete();

            profile.setName("").setUniqueId(new UUID(random.nextLong(), 0));

            NPC.builder()
                    .location(location.clone().add(0.5, 0, 0.5))
                    .imitatePlayer(false)
                    .profile(profile)
                    .build(pool);

            hologramController.create(location.clone().add(0.5, (2 + (format.size() * 0.3)), 0.5), format);
        }
    }

    public <T> void update(List<T> content, Function<T, String> function) {
        update(content, function, ($) -> new ArrayList<>());
    }

    public void destroy() {
        hologramController.clear();
        pool.getNPCs().forEach(npc -> pool.removeNPC(npc.getEntityId()));
    }

    @EventHandler
    private void handle(PlayerNPCShowEvent event) {
        final NPC npc = event.getNPC();

        if (!pool.getNPCs().contains(npc)) return;

        event.send(npc.metadata().queue(MetadataModifier.EntityMetadata.SKIN_LAYERS, true));
        // hideNpcName(event.getPlayer(), npc);
    }

    private void hideNpcName(Player player, NPC npc) {
        final Scoreboard scoreboard = player.getScoreboard();
        final String teamName = ("NPCRanking-" + player.getName()).substring(0, 16);

        Team team = scoreboard.getTeam(teamName);

        if (team == null) team = scoreboard.registerNewTeam(teamName);

        team.addEntry(npc.getProfile().getName());
        team.setNameTagVisibility(NameTagVisibility.NEVER);
    }

}
