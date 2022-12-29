execute as @a[scores={bac_apple_eaten=1..99}] run scoreboard players add @s bac_apple_days 1
execute as @a[scores={bac_apple_eaten=1..}] run scoreboard players set @s bac_apple_eaten 100

tellraw @s {"text":"                                             ","color":"dark_gray","strikethrough":true}

execute if score @s bac_apple_days matches 0.. run tellraw @s {"translate":"You have eaten an apple every day for ","extra":[{"color":"gold","score":{"name":"@s","objective":"bac_apple_days"}},{"translate":" days."}]}

execute if score @s bac_apple_eaten matches 1.. run tellraw @s {"color":"green","text":"You have eaten an apple today."}
execute unless score @s bac_apple_eaten matches 1.. run tellraw @s {"color":"red","text":"You have not eaten an apple today."}
tellraw @s {"color":"gray","italic":"true","text":""}
tellraw @s {"color":"gray","italic":"true","text":"If you have just slept, and it says you have eaten an apple when you actually haven't yet, try running this trigger again in 10 seconds."}

tellraw @s {"text":"                                             ","color":"dark_gray","strikethrough":true}

scoreboard players set @s bac_apple_a_day 0