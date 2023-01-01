package com.nextplugins.libs.npcranking;

import java.util.ArrayList;
import java.util.List;

/**
 * The class which handle the hologram format for each NPC
 *
 * @param <T> the rankeable item
 */
public abstract class RankFormat<T> {

    public interface RankFormatReplacer<T> {

        /**
         * Replace format line
         *
         * @param item the item
         * @param position the item position
         * @param line the format line
         * @return the line replaced with item data
         */
        String replace(T item, int position, String line);

    }

    private final List<String> format;

    /**
     * @param format the hologram format
     */
    public RankFormat(List<String> format) {
        this.format = format;
    }

    public List<String> getFormat() {
        return format;
    }

    /**
     * Get the skin name from item
     *
     * @param item the item
     * @return the skin name to use on NPCs
     */
    public abstract String getSkinFrom(T item);

    /**
     * Get the replacer
     *
     * @return the format replacer that will be used to replace each line of format
     */
    public abstract RankFormatReplacer<T> getReplacer();

    /**
     * Uses item and position to replace the format using the RankFormatReplacer defined on getReplacer()
     *
     * @param item the item
     * @param position the item position
     * @return the format with the lines replaced
     */
    public List<String> replaceWith(T item, int position) {
        final List<String> lines = new ArrayList<>();

        for (String line : format) {
            lines.add(getReplacer().replace(item, position, line));
        }

        return lines;
    }

}
