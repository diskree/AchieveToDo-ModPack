package com.diskree.achievetodo;

import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class Utils {

    @Nullable
    public static Text translateOrNull(String key) {
        Text text = Text.translatable(key);
        return text.getString().equals(key) ? null : text;
    }
}
