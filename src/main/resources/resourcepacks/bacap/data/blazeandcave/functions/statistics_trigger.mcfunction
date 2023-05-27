tellraw @s {"text":"                                             ","color":"dark_gray","strikethrough":true}
tellraw @s {"color":"gray","translate":"Custom Statistics"}
tellraw @s {"text":"                                             ","color":"dark_gray","strikethrough":true}

execute if score @s bac_day_count matches 0.. run tellraw @s {"color":"white","translate":"Days played:","extra":[{"text":" "},{"color":"gold","score":{"name":"@s","objective":"bac_day_count"}}]}
execute if score @s bac_stat_food matches 0.. run tellraw @s {"color":"white","translate":"Food items eaten:","extra":[{"text":" "},{"color":"gold","score":{"name":"@s","objective":"bac_stat_food"}}]}
execute if score @s bac_stat_loot_chest matches 0.. run tellraw @s {"color":"white","translate":"Loot chests opened:","extra":[{"text":" "},{"color":"gold","score":{"name":"@s","objective":"bac_stat_loot_chest"}}]}
execute if score @s bac_pigling matches 0.. run tellraw @s {"color":"white","translate":"Gold Bartered to Piglins:","extra":[{"text":" "},{"color":"gold","score":{"name":"@s","objective":"bac_pigling"}}]}

tellraw @s {"text":"                                             ","color":"dark_gray","strikethrough":true}

scoreboard players set @s bac_statistics 0