package com.nextplugins.libs.npcranking.example;

import com.nextplugins.libs.npcranking.Ranking;

import java.text.DecimalFormat;
import java.util.List;

public class RankingRunnable implements Runnable {

    private final Ranking ranking;
    private final List<Account> accounts;
    private final DecimalFormat decimalFormat;

    public RankingRunnable(Ranking ranking, List<Account> accounts) {
        this.ranking = ranking;
        this.accounts = accounts;
        this.decimalFormat = new DecimalFormat("#,###.###");
    }

    @Override
    public void run() {
        ranking.update(
                accounts,
                Account::getName,
                (account) -> List.of("&2$ &f" + decimalFormat.format(account.getBalance()))
        );
    }

}
