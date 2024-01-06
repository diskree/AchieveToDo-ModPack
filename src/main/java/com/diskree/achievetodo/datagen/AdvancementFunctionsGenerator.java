package com.diskree.achievetodo.datagen;

import com.diskree.achievetodo.action.BlockedActionType;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

import java.util.function.Consumer;

// TODO Refactoring
public class AdvancementFunctionsGenerator extends FunctionProvider {

    protected AdvancementFunctionsGenerator(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateFunctions(Consumer<Function> consumer) {
        for (BlockedActionType blockedAction : BlockedActionType.values()) {
            String titleKey = "blocked." + blockedAction.getName() + ".title";
            String descriptionKey = "blocked." + blockedAction.getName() + ".description";
            String command = "tellraw @a {\"translate\":\"%1$s has unblocked %2$s%3$s%4$s\",\"with\":[{\"selector\":\"@s\"},{\"color\":\"yellow\",\"text\":\"[\"},{\"color\":\"yellow\",\"translate\":\"" + titleKey + "\",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"color\":\"yellow\",\"translate\":\"" + titleKey + "\",\"extra\":[{\"text\":\"\\n\"},{\"color\":\"yellow\",\"translate\":\"" + descriptionKey + "\"},{\"text\":\"\\n\\n\"},{\"color\":\"gray\",\"italic\":true,\"translate\":\"%1$s tab\",\"with\":[{\"translate\":\"advancement.root.blocked_actions.title\"}]}]}}},{\"color\":\"yellow\",\"text\":\"]\"}]}";
            consumer.accept(new Function(blockedAction.getName(), command));
        }
    }
}
