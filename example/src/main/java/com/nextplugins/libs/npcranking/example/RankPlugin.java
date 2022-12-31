package com.nextplugins.libs.npcranking.example;

import com.nextplugins.libs.npcranking.Ranker;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class RankPlugin extends JavaPlugin {

    private static final List<Account> ACCOUNTS;
    private static final List<String> FORMAT;

    static {
        ACCOUNTS = new ArrayList<>() {{
            add(new Account("Eike", 10_000));
            add(new Account("Yuhtin", 9_000));
            add(new Account("Gabriel", 8_000));
            add(new Account("Henry", 7_000));
        }};

        FORMAT = List.of(
                "&a{position}º",
                "&f{name}",
                "&2$ &f{balance}"
        );
    }

    @Override
    public void onEnable() {
        getDataFolder().mkdirs();
        Ranker<Account> accountRanker = new Ranker<>(new AccountRankFormat(FORMAT), this);

        getCommand("npcposition").setExecutor(
                new NpcLocationCommand(accountRanker.getLocations())
        );

        new RankUpdater(ACCOUNTS, accountRanker).runTaskTimer(this, 20, 20*60);
    }

}
