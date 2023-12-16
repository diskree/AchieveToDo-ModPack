package com.diskree.achievetodo.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface CreateWorldScreenImpl {
    boolean achieveToDo$datapacksLoaded();
}
