package com.diskree.achievetodo;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public enum InternalDatapack {

    BACAP_OVERRIDE,
    BACAP_OVERRIDE_HARDCORE,
    BACAP_REWARDS_ITEM,
    BACAP_REWARDS_EXPERIENCE,
    BACAP_REWARDS_TROPHY,
    BACAP_COOPERATIVE_MODE;

    public @NotNull Identifier toDatapackId() {
        return new Identifier(BuildConfig.MOD_ID, name().toLowerCase(Locale.ROOT));
    }
}
