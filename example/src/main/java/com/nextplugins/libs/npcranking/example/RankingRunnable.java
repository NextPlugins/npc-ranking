package com.nextplugins.libs.npcranking.example;

import com.nextplugins.libs.npcranking.Ranking;

import java.util.List;

public class RankingRunnable implements Runnable {

    private final Ranking ranking;
    private final List<Account> accounts;

    public RankingRunnable(Ranking ranking, List<Account> accounts) {
        this.ranking = ranking;
        this.accounts = accounts;
    }

    @Override
    public void run() {
        ranking.update(accounts, Account::getName);
    }

}
