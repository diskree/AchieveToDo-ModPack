# Function runs whenever a player gets hurt
execute if score @s bac_loser_hurt matches 1.. run scoreboard players add @s bac_loser_count 1
scoreboard players set @s bac_loser_hurt 0

# If it has been less than 10 seconds since the last death, the "Loser!" advancement is granted
execute if score @s bac_loser matches ..10 if score @s bac_loser_count matches 2.. run advancement grant @s only blazeandcave:weaponry/loser
execute if score @s bac_loser matches 11.. run scoreboard players set @s bac_loser 0
execute if score @s bac_loser matches 11.. run scoreboard players set @s bac_loser_count 0