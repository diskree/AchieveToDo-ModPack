tellraw @s {"text":"                                             ","color":"dark_gray","strikethrough":true}
tellraw @s {"color":"gray","bold":"false","text":" ","extra":[{"color":"gray","bold":"false","translate":"How to update to new datapack versions"}]}

tellraw @s {"text":"                                             ","color":"dark_gray","strikethrough":true}

# How
tellraw @s ["",{"translate":"1) Make a backup of your world for safety if you mess anything up"}]
tellraw @s ["",{"translate":"2) Leave your world for more safety and don't go in during the process"}]
tellraw @s ["",{"translate":"3) Delete the old datapacks COMPLETELY from your world"}]
tellraw @s ["",{"translate":"4) Copy and paste in the new updated datapacks"}]
tellraw @s ["",{"translate":"5) Go into your world. If the new datapacks are for a newer version of Minecraft make sure you are now using that version"}]
tellraw @s ["",{"translate":"6) If you messed up, go to your backup, make a backup of your backup, then repeat steps 2-6 on the backup"}]

# Go back
tellraw @s {"text":"                                             ","color":"dark_gray","strikethrough":true}
tellraw @s ["",{"text":"[ «« ]","color":"red","clickEvent":{"action":"run_command","value":"/function blazeandcave:config/help_menu"},"hoverEvent":{"action":"show_text","contents":["",{"translate":"Click to go back","color":"gold"}]}}," ",{"translate":"Go back"}]

tellraw @s {"text":"                                             ","color":"dark_gray","strikethrough":true}
