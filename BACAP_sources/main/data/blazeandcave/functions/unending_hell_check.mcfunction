# The end component resets if the player is in the Overworld or if they die
execute if entity @s[predicate=blazeandcave:in_the_overworld] run advancement revoke @s only blazeandcave:technical/unending_hell_end
execute if score @s bac_unending_death matches 1.. run advancement revoke @s only blazeandcave:technical/unending_hell_end
scoreboard players set @s bac_unending_death 0

# If the player goes straigh to the Nether without dying, Unending Hell will trigger using the location trigger with the unending_hell_end advancement condition