tellraw @s {"text":"                                             ","color":"dark_gray","strikethrough":true}
tellraw @s {"color":"gray","bold":"false","text":" ","extra":[{"color":"gray","bold":"false","translate":"How to add players to teams"}]}

tellraw @s {"text":"                                             ","color":"dark_gray","strikethrough":true}

# How
tellraw @s ["",{"translate":"This datapack adds 16 teams, one of each colour, that this datapack can work with."}]
tellraw @s ["",{"translate":"For Team Cooperative Mode and team-based scoreboards to work properly, players MUST be added to these teams using the following command:"}]
tellraw @s ["",{"color":"gray","translate":"/team join bac_team_<color> <player>","clickEvent":{"action":"suggest_command","value":"/team join bac_team_"}}]
tellraw @s ["",{"translate":"You may freely change the team's display name, prefix and suffix using their respective commands as well"}]

# Go back
tellraw @s {"text":"                                             ","color":"dark_gray","strikethrough":true}
tellraw @s ["",{"text":"[ «« ]","color":"red","clickEvent":{"action":"run_command","value":"/function blazeandcave:config/help_menu"},"hoverEvent":{"action":"show_text","contents":["",{"translate":"Click to go back","color":"gold"}]}}," ",{"translate":"Go back"}]

tellraw @s {"text":"                                             ","color":"dark_gray","strikethrough":true}
