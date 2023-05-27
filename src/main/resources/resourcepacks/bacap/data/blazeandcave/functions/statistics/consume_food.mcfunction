# Increases scoreboard only if the food consumed wasn't milk, a water bottle or a potion
scoreboard players add @s[advancements={blazeandcave:technical/consume_food_null=false}] bac_stat_food 1
advancement revoke @s only blazeandcave:technical/consume_food
advancement revoke @s only blazeandcave:technical/consume_food_null

# If the player has reached the requirement for a statistic advancement, they get it
execute if score @s bac_stat_food matches 200.. run advancement grant @s only blazeandcave:statistics/om_nom_nom
execute if score @s bac_stat_food matches 1000.. run advancement grant @s only blazeandcave:statistics/yum_yum_yummo
execute if score @s bac_stat_food matches 5000.. run advancement grant @s only blazeandcave:statistics/food_glorious_food