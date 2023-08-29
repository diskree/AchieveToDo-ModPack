import os
import re

translation_files = [
    os.path.join(os.path.dirname(os.path.realpath(__file__)), 'BACAP/base_language_file.json'),
    os.path.join(os.path.dirname(os.path.realpath(__file__)), 'BACAP/mojang_localization.json')
]
folder_path = os.path.join(os.path.dirname(os.path.realpath(__file__)), 'BACAP/datapack')

translated_texts = set()

for translation_file_path in translation_files:
    with open(translation_file_path, 'r', encoding='utf-8') as f:
        translations = f.read()
        translated_texts.update(re.findall(r'"(.*?)"\s*:', translations))

untranslated_texts = set()
for dirpath, dirnames, filenames in os.walk(folder_path):
    for filename in filenames:
        if not (filename.endswith('.json') or filename.endswith('.mcfunction')):
            continue
        with open(os.path.join(dirpath, filename), 'r', encoding='utf-8') as f:
            content = f.read()
            texts = re.findall(r'"translate":"(.*?)"', content)
            for text in texts:
                if text not in translated_texts:
                    untranslated_texts.add(text)

for text in untranslated_texts:
    print(text)
