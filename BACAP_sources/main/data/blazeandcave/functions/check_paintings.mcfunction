# Function runs whenever a player places a painting and checks all paintings close by to count towards the advancement
execute if entity @e[type=painting,distance=..8,nbt={variant:"minecraft:kebab"}] run advancement grant @s only blazeandcave:building/art_gallery kebab
execute if entity @e[type=painting,distance=..8,nbt={variant:"minecraft:aztec"}] run advancement grant @s only blazeandcave:building/art_gallery aztec
execute if entity @e[type=painting,distance=..8,nbt={variant:"minecraft:alban"}] run advancement grant @s only blazeandcave:building/art_gallery alban
execute if entity @e[type=painting,distance=..8,nbt={variant:"minecraft:aztec2"}] run advancement grant @s only blazeandcave:building/art_gallery aztec2
execute if entity @e[type=painting,distance=..8,nbt={variant:"minecraft:bomb"}] run advancement grant @s only blazeandcave:building/art_gallery bomb
execute if entity @e[type=painting,distance=..8,nbt={variant:"minecraft:plant"}] run advancement grant @s only blazeandcave:building/art_gallery plant
execute if entity @e[type=painting,distance=..8,nbt={variant:"minecraft:wasteland"}] run advancement grant @s only blazeandcave:building/art_gallery wasteland
execute if entity @e[type=painting,distance=..8,nbt={variant:"minecraft:wanderer"}] run advancement grant @s only blazeandcave:building/art_gallery wanderer
execute if entity @e[type=painting,distance=..8,nbt={variant:"minecraft:graham"}] run advancement grant @s only blazeandcave:building/art_gallery graham
execute if entity @e[type=painting,distance=..8,nbt={variant:"minecraft:pool"}] run advancement grant @s only blazeandcave:building/art_gallery pool
execute if entity @e[type=painting,distance=..8,nbt={variant:"minecraft:courbet"}] run advancement grant @s only blazeandcave:building/art_gallery courbet
execute if entity @e[type=painting,distance=..8,nbt={variant:"minecraft:sunset"}] run advancement grant @s only blazeandcave:building/art_gallery sunset
execute if entity @e[type=painting,distance=..8,nbt={variant:"minecraft:sea"}] run advancement grant @s only blazeandcave:building/art_gallery sea
execute if entity @e[type=painting,distance=..8,nbt={variant:"minecraft:creebet"}] run advancement grant @s only blazeandcave:building/art_gallery creebet
execute if entity @e[type=painting,distance=..8,nbt={variant:"minecraft:match"}] run advancement grant @s only blazeandcave:building/art_gallery match
execute if entity @e[type=painting,distance=..8,nbt={variant:"minecraft:bust"}] run advancement grant @s only blazeandcave:building/art_gallery bust
execute if entity @e[type=painting,distance=..8,nbt={variant:"minecraft:stage"}] run advancement grant @s only blazeandcave:building/art_gallery stage
execute if entity @e[type=painting,distance=..8,nbt={variant:"minecraft:void"}] run advancement grant @s only blazeandcave:building/art_gallery void
execute if entity @e[type=painting,distance=..8,nbt={variant:"minecraft:skull_and_roses"}] run advancement grant @s only blazeandcave:building/art_gallery skull_and_roses
execute if entity @e[type=painting,distance=..8,nbt={variant:"minecraft:wither"}] run advancement grant @s only blazeandcave:building/art_gallery wither
execute if entity @e[type=painting,distance=..8,nbt={variant:"minecraft:fighters"}] run advancement grant @s only blazeandcave:building/art_gallery fighters
execute if entity @e[type=painting,distance=..8,nbt={variant:"minecraft:skeleton"}] run advancement grant @s only blazeandcave:building/art_gallery skeleton
execute if entity @e[type=painting,distance=..8,nbt={variant:"minecraft:donkey_kong"}] run advancement grant @s only blazeandcave:building/art_gallery donkey_kong
execute if entity @e[type=painting,distance=..8,nbt={variant:"minecraft:pointer"}] run advancement grant @s only blazeandcave:building/art_gallery pointer
execute if entity @e[type=painting,distance=..8,nbt={variant:"minecraft:pigscene"}] run advancement grant @s only blazeandcave:building/art_gallery pigscene
execute if entity @e[type=painting,distance=..8,nbt={variant:"minecraft:burning_skull"}] run advancement grant @s only blazeandcave:building/art_gallery burning_skull

advancement revoke @s only blazeandcave:technical/place_painting
scoreboard players set @s bac_painting 0