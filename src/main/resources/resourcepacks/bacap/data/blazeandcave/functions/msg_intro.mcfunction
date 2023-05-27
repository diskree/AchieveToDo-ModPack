tellraw @s {"color":"dark_gray","text":"-|-|-|- ","extra":[{"color":"green","bold":"true","translate":"Welcome!"},{"color":"dark_gray","text":" -|-|-|-"}]}
tellraw @s {"color":"#D1FFFD","bold":"false","translate":"Thank you for downloading","extra":[{"text":"\n"},{"color":"yellow","bold":"true","translate":"Blaze"},{"color":"gray","bold":"true","translate":"and"},{"color":"aqua","bold":"true","translate":"Cave"},{"color":"gray","bold":"true","translate":"'s Advancements Pack!"}]}
tellraw @s {"text":" "}
tellraw @s {"color":"#D1FFFD","italic":"false","translate":"If you wish to change settings, use the following command:"}
tellraw @s {"color":"aqua","italic":"false","translate":"","extra":[{"text":" "},{"text":"/function blazeandcave:config_menu","clickEvent":{"action":"suggest_command","value":"/function blazeandcave:config_menu"}}]}
tellraw @s {"text":" "}
tellraw @s {"color":"#D1FFFD","italic":"false","translate":"Want to keep up with updates to the Advancements Pack? Join my","extra":[{"text":" "},{"color":"blue","italic":"false","underlined":"true","translate":"Discord","clickEvent":{"action":"open_url","value":"https://discord.gg/GBMSmWg"}},{"text":" "},{"translate":"and give yourself the Advancement Pack Squad in #roles!"}]}

scoreboard players set introduced bac_settings 1