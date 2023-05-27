tellraw @s {"text":"                                             ","color":"dark_gray","strikethrough":true}
tellraw @s {"color":"gray","bold":"false","text":" ","extra":[{"color":"gray","bold":"false","translate":"What is the name of the mod that lets you see progress on advancements?"}]}

tellraw @s {"text":"                                             ","color":"dark_gray","strikethrough":true}

# How
tellraw @s ["",{"translate":"The mod in question is named Advancement Info, which displays the requirements that have been completed on each advancement."}]
tellraw @s ["",{"translate":"It is extremely useful, especially for Super Challenges with many requirements such as 'All the Blocks'."}]
tellraw @s ["",{"translate":"It can be downloaded here:"}]
tellraw @s ["",{"color":"aqua","underline":"true","text":"https://www.curseforge.com/minecraft/mc-mods/advancementinfo ","clickEvent":{"action":"open_url","value":"https://bit.ly/3T1qzV7"},"hoverEvent":{"action":"show_text","contents":["",{"translate":"Click to follow link","color":"gold"}]}}]

# Go back
tellraw @s {"text":"                                             ","color":"dark_gray","strikethrough":true}
tellraw @s ["",{"text":"[ «« ]","color":"red","clickEvent":{"action":"run_command","value":"/function blazeandcave:config/help_faq"},"hoverEvent":{"action":"show_text","contents":["",{"translate":"Click to go back","color":"gold"}]}}," ",{"translate":"Go back"}]

tellraw @s {"text":"                                             ","color":"dark_gray","strikethrough":true}
