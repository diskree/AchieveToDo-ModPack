tellraw @s {"text":"                                             ","color":"dark_gray","strikethrough":true}
tellraw @s {"color":"gray","bold":"false","text":" ","extra":[{"color":"gray","bold":"false","translate":"Help Menu"}]}

tellraw @s {"text":"                                             ","color":"dark_gray","strikethrough":true}

# Link to Config Menu Documentation
tellraw @s ["",{"text":"[ »» ]","color":"green","clickEvent":{"action":"open_url","value":"http://bit.ly/3HXkft2"},"hoverEvent":{"action":"show_text","contents":["",{"translate":"Click to follow link","color":"gold"}]}}," ",{"translate":"Full documentation on how to use the Config Menu"}]

# How to update to newer versions of the datapack
tellraw @s ["",{"text":"[ »» ]","color":"yellow","clickEvent":{"action":"run_command","value":"/function blazeandcave:config/help_new_version_update"},"hoverEvent":{"action":"show_text","contents":["",{"translate":"Click to view","color":"gold"}]}}," ",{"translate":"How to update to new datapack versions"}]

# How to add players to teams
tellraw @s ["",{"text":"[ »» ]","color":"aqua","clickEvent":{"action":"run_command","value":"/function blazeandcave:config/help_teams"},"hoverEvent":{"action":"show_text","contents":["",{"translate":"Click to view","color":"gold"}]}}," ",{"translate":"How to add players to teams"}]

# Link to more links
## Specifically: Language Pack, Hardcore and Terralith versions, Advancements Documentation, Trophy List, Discord and Patreon
tellraw @s ["",{"text":"[ »» ]","color":"dark_green","clickEvent":{"action":"run_command","value":"/function blazeandcave:config/help_links"},"hoverEvent":{"action":"show_text","contents":["",{"translate":"Click to view","color":"gold"}]}}," ",{"translate":"Important Links"}]

# Known troubleshooting issues and FAQ's
## Specifically: Use the right version of BACAP for the right version of MC, Paper/Spigot server issues, Advancement Info
tellraw @s ["",{"text":"[ »» ]","color":"red","clickEvent":{"action":"run_command","value":"/function blazeandcave:config/help_troubleshooting"},"hoverEvent":{"action":"show_text","contents":["",{"translate":"Click to view","color":"gold"}]}}," ",{"translate":"Troubleshooting"}]
tellraw @s ["",{"text":"[ »» ]","color":"blue","clickEvent":{"action":"run_command","value":"/function blazeandcave:config/help_faq"},"hoverEvent":{"action":"show_text","contents":["",{"translate":"Click to view","color":"gold"}]}}," ",{"translate":"Frequently Asked Questions"}]


# Go back
tellraw @s {"text":"                                             ","color":"dark_gray","strikethrough":true}
tellraw @s ["",{"text":"[ «« ]","color":"red","clickEvent":{"action":"run_command","value":"/function blazeandcave:config"},"hoverEvent":{"action":"show_text","contents":["",{"translate":"Click to go back","color":"gold"}]}}," ",{"translate":"Go back to main config menu"}]

tellraw @s {"text":"                                             ","color":"dark_gray","strikethrough":true}
