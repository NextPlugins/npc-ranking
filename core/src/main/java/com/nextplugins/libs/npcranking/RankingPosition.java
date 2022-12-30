package com.nextplugins.libs.npcranking;

import com.nextplugins.libs.npcranking.utils.LocationSerializer;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.*;

public class RankingPosition {

    private final Plugin plugin;
    private final File file;
    private final FileConfiguration configuration;

    public RankingPosition(Plugin plugin) {
        this.plugin = plugin;
        this.file = Objects.requireNonNull(createFile());
        this.configuration = getAsConfiguration();
    }

    private File createFile() {
        final File file = new File(plugin.getDataFolder(), "ranking-positions.yml");

        try {
            file.createNewFile();
        } catch (Exception exception) {
            exception.printStackTrace();

            return null;
        }

        return file;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getAsConfiguration() {
        return YamlConfiguration.loadConfiguration(file);
    }

    public void setPositions(List<String> stringList) {
        configuration.set("available", stringList);

        try {
            configuration.save(file);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void setPosition(int index, Location location) {
        final List<String> positions = configuration.getStringList("available");
        final String data = LocationSerializer.to(location);

        if (index > positions.size() - 1) positions.add(data);
        else positions.set(index, data);

        configuration.set("available", positions);

        try {
            configuration.save(file);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public Map<Integer, Location> getPositions() {
        final Map<Integer, Location> positions = new LinkedHashMap<>();
        final List<String> available = configuration.getStringList("available");

        for (int index = 0; index < available.size(); index++) {
            final String position = available.get(index);

            positions.put(index, LocationSerializer.from(position));
        }

        return positions;
    }

}
