package com.nextplugins.libs.npcranking.example;

import com.nextplugins.libs.npcranking.RankFormat;

import java.util.List;

public class AccountRankFormat extends RankFormat<Account> {

    public AccountRankFormat(List<String> format) {
        super(format);
    }

    @Override
    public String getSkinFrom(Account item) {
        return item.getName();
    }

    @Override
    public RankFormatReplacer<Account> getReplacer() {
        return ((item, position, line) ->
                line.replace("{position}", String.valueOf(position))
                    .replace("{name}", item.getName())
                    .replace("{balance}", item.getBalanceFormatted())
        );
    }

}
