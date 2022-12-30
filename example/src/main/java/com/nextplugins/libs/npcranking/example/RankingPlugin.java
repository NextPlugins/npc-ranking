package com.nextplugins.libs.npcranking.example;

import com.nextplugins.libs.npcranking.Ranking;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class RankingPlugin extends JavaPlugin {

    private static final List<Account> accounts;

    static {
        accounts = new ArrayList<>() {{
            add(new Account("Eike", 10D));
            add(new Account("Yuhtin", 9D));
            add(new Account("Gabriel", 8D));
            add(new Account("Henry", 7D));
        }};
    }

    private Ranking ranking;

    @Override
    public void onEnable() {
        getDataFolder().mkdirs();

        this.ranking = new Ranking(this, Arrays.asList(
                "&a{position}º",
                "&f{name}"
        ));

        getCommand("pos").setExecutor(new RankingPositionCommand(ranking.getRankingPosition()));
        Bukkit.getScheduler().runTaskTimer(this, new RankingRunnable(ranking, accounts), 20, 20 * 30);
    }

}
