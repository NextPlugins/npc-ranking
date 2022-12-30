package com.nextplugins.libs.npcranking.example;

import com.nextplugins.libs.npcranking.RankingPosition;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RankingPositionCommand implements CommandExecutor {

    private final RankingPosition position;

    public RankingPositionCommand(RankingPosition position) {
        this.position = position;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;

        final Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("Usage: /pos [1, 2, 3, ...]");

            return false;
        }

        final int pos;

        try {
            pos = Integer.parseInt(args[0]);
        } catch (NumberFormatException exception) {
            player.sendMessage("Position must be a number!");

            return false;
        }

        position.setPosition(pos - 1, player.getLocation());

        return false;
    }
}
