package com.nextplugins.libs.npcranking;

import java.util.ArrayList;
import java.util.List;

public abstract class RankFormat<T> {

    public interface RankFormatReplacer<T> {

        String replace(T item, int position, String line);

    }

    private final List<String> format;

    public RankFormat(List<String> format) {
        this.format = format;
    }

    public List<String> getFormat() {
        return format;
    }

    public abstract String getSkinFrom(T item);
    public abstract RankFormatReplacer<T> getReplacer();

    public List<String> replaceWith(T item, int position) {
        final List<String> lines = new ArrayList<>();

        for (String line : format) {
            lines.add(getReplacer().replace(item, position, line));
        }

        return lines;
    }

}
