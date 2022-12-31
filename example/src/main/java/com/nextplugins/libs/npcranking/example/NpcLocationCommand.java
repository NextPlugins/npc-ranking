package com.nextplugins.libs.npcranking.example;

import com.nextplugins.libs.npcranking.RankLocations;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NpcLocationCommand implements CommandExecutor {

    private final RankLocations locations;

    public NpcLocationCommand(RankLocations locations) {
        this.locations = locations;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;

        final Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("Usage: /npcposition [1, 2, 3, ...]");

            return false;
        }

        final int position;

        try {
            position = Integer.parseInt(args[0]);
        } catch (NumberFormatException exception) {
            player.sendMessage("Position must be a number!");

            return false;
        }

        locations.setLocation(position, player.getLocation());

        return false;
    }
}
