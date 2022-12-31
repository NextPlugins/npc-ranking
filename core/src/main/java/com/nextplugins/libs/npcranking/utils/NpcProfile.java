package com.nextplugins.libs.npcranking.utils;

import com.github.juliarn.npc.profile.Profile;

import java.util.Random;
import java.util.UUID;

public class NpcProfile {

    private static final Random RANDOM = new Random();

    private NpcProfile() {}

    public static Profile generateWithSkin(String skin) {
        final Profile profile = new Profile(skin);
        profile.complete();

        profile.setName("")
               .setUniqueId(new UUID(RANDOM.nextLong(), 0));

        return profile;
    }

}
