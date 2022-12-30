package com.nextplugins.libs.npcranking;

import org.bukkit.plugin.Plugin;

import java.io.File;

public class RankingLocationStorage {

    private final Plugin plugin;

    public RankingLocationStorage(Plugin plugin) {
        this.plugin = plugin;
    }

    public void createFile() {
        final File file = new File(plugin.getDataFolder(), "ranking-positions.yml");

        file.mkdirs();

    }

}
