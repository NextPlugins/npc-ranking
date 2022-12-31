package com.nextplugins.libs.npcranking;

import java.util.List;

public interface RankPlaceholder<T> {

    void apply(int position, T item, List<String> format);

}
