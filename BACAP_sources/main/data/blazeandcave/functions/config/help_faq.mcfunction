tellraw @s {"text":"                                             ","color":"dark_gray","strikethrough":true}
tellraw @s {"color":"gray","bold":"false","text":" ","extra":[{"color":"gray","bold":"false","translate":"Frequently Asked Questions"}]}

tellraw @s {"text":"                                             ","color":"dark_gray","strikethrough":true}

# Why aren't all tabs visible at the start?
tellraw @s ["",{"text":"[ »» ]","color":"yellow","clickEvent":{"action":"run_command","value":"/function blazeandcave:config/help_faq_not_all_tabs_visible"},"hoverEvent":{"action":"show_text","contents":["",{"translate":"Click to view","color":"gold"}]}}," ",{"translate":"Why aren't all tabs visible at the start?"}]

# How do I install the Language Pack?
tellraw @s ["",{"text":"[ »» ]","color":"aqua","clickEvent":{"action":"run_command","value":"/function blazeandcave:config/help_faq_language_install"},"hoverEvent":{"action":"show_text","contents":["",{"translate":"Click to view","color":"gold"}]}}," ",{"translate":"How do I install the Language Pack?"}]

# How can I translate the advancements into a new language?
tellraw @s ["",{"text":"[ »» ]","color":"dark_aqua","clickEvent":{"action":"run_command","value":"/function blazeandcave:config/help_faq_language_translate"},"hoverEvent":{"action":"show_text","contents":["",{"translate":"Click to view","color":"gold"}]}}," ",{"translate":"How can I translate the advancements into a new language?"}]

# What is the name of the mod that lets you see progress on advancements? (Advancement Info)
tellraw @s ["",{"text":"[ »» ]","color":"gold","clickEvent":{"action":"run_command","value":"/function blazeandcave:config/help_faq_advinfo"},"hoverEvent":{"action":"show_text","contents":["",{"translate":"Click to view","color":"gold"}]}}," ",{"translate":"What is the name of the mod that lets you see progress on advancements?"}]


# Go back
tellraw @s {"text":"                                             ","color":"dark_gray","strikethrough":true}
tellraw @s ["",{"text":"[ «« ]","color":"red","clickEvent":{"action":"run_command","value":"/function blazeandcave:config/help_menu"},"hoverEvent":{"action":"show_text","contents":["",{"translate":"Click to go back","color":"gold"}]}}," ",{"translate":"Go back to main config menu"}]

tellraw @s {"text":"                                             ","color":"dark_gray","strikethrough":true}
