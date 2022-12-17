execute store result score @s bac_warden_count run execute if entity @e[type=warden,distance=..16]

execute if score @s bac_warden_count matches 5.. run advancement grant @s only blazeandcave:monsters/house_of_freaks