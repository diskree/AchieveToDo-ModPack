give @s minecraft:golden_horse_armor 1
give @s minecraft:enchanted_book{StoredEnchantments:[{id:"minecraft:power",lvl:3}]} 1
tellraw @s {"color":"green","text":" +1 ","extra":[{"translate":"item.minecraft.golden_horse_armor"}]}
tellraw @s {"color":"green","text":" +1 ","extra":[{"translate":"enchantment.minecraft.power"},{"text":" "},{"translate":"enchantment.level.3"},{"text":" "},{"translate":"item.minecraft.enchanted_book"}]}
