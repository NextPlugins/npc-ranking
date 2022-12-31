package com.nextplugins.libs.npcranking.example;

import com.nextplugins.libs.npcranking.Ranker;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class RankUpdater extends BukkitRunnable {

    private final List<Account> accounts;
    private final Ranker<Account> accountRanker;

    public RankUpdater(List<Account> accounts, Ranker<Account> accountRanker) {
        this.accounts = accounts;
        this.accountRanker = accountRanker;
    }

    @Override
    public void run() {
        accountRanker.update(accounts);
    }

}
