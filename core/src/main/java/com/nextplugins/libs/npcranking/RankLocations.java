package com.nextplugins.libs.npcranking;

import com.nextplugins.libs.npcranking.utils.LocationSerializer;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.*;

public class RankLocations {

    private final Plugin plugin;
    private final File file;
    private final FileConfiguration configuration;

    public RankLocations(Plugin plugin) {
        this.plugin = plugin;
        this.file = Objects.requireNonNull(createFile());
        this.configuration = getAsConfiguration();
    }

    private File createFile() {
        final File file = new File(plugin.getDataFolder(), "ranking-location.yml");

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

    public void setLocation(int position, Location location) {
        final List<String> locations = configuration.getStringList("available");

        final int index = position <= 0 ? position : position - 1;
        final String data = LocationSerializer.to(location);

        if (index > locations.size() - 1) locations.add(data);
        else locations.set(index, data);

        configuration.set("available", locations);

        try {
            configuration.save(file);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public Location getLocation(int position) {
        return getLocations().getOrDefault((position - 1), null);
    }

    public Map<Integer, Location> getLocations() {
        final Map<Integer, Location> positions = new LinkedHashMap<>();
        final List<String> available = configuration.getStringList("available");

        for (int index = 0; index < available.size(); index++) {
            final String location = available.get(index);

            positions.put(index, LocationSerializer.from(location));
        }

        return positions;
    }

}
