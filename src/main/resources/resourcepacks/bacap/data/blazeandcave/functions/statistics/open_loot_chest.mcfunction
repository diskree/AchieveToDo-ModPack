scoreboard players add @s bac_stat_loot_chest 1
advancement revoke @s only blazeandcave:technical/open_loot_chest

# If the player has reached the requirement for a statistic advancement, they get it
execute if score @s bac_stat_loot_chest matches 10.. run advancement grant @s only blazeandcave:statistics/loot_em
execute if score @s bac_stat_loot_chest matches 100.. run advancement grant @s only blazeandcave:statistics/more_for_me
execute if score @s bac_stat_loot_chest matches 500.. run advancement grant @s only blazeandcave:statistics/i_heart_chests