tellraw @s {"text":"                                             ","color":"dark_gray","strikethrough":true}
tellraw @s {"color":"gray","bold":"false","text":" ","extra":[{"color":"gray","bold":"false","translate":"Why aren't all tabs visible at the start?"}]}

tellraw @s {"text":"                                             ","color":"dark_gray","strikethrough":true}

# How
tellraw @s ["",{"translate":"A tab will not show up if you haven't gained any advancements in that tab yet."}]
tellraw @s ["",{"translate":"Most root advancements are easy to obtain, but some are further along in progression."}]
tellraw @s ["",{"translate":"Notably, the Super Challenges tab is not unlocked until after defeating the Ender Dragon."}]

# Go back
tellraw @s {"text":"                                             ","color":"dark_gray","strikethrough":true}
tellraw @s ["",{"text":"[ «« ]","color":"red","clickEvent":{"action":"run_command","value":"/function blazeandcave:config/help_faq"},"hoverEvent":{"action":"show_text","contents":["",{"translate":"Click to go back","color":"gold"}]}}," ",{"translate":"Go back"}]

tellraw @s {"text":"                                             ","color":"dark_gray","strikethrough":true}
